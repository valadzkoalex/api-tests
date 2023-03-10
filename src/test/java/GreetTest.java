import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.http.HttpStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GreetTest {
    private static final String BASE_URI = "http://localhost/";
    private static final Integer PORT = 8080;
    private static final String NAME_PARAMETER = "name";
    private static final String GREET_PATH = "/greet";
    private static final String EXPECTED_GREET_BODY_FORMAT = "Hello %s!";

    @ParameterizedTest
    @ValueSource(strings = {"Alex", "123Name", "cAmEl", "$pec*", ""})
    public void greetWithNameParameterTest(String name) {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        given()
                .port(PORT)
                .baseUri(BASE_URI)
                .basePath(GREET_PATH)
                .param(NAME_PARAMETER, name)
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo(String.format(EXPECTED_GREET_BODY_FORMAT, name)));
    }
}
