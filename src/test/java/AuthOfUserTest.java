import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.hamcrest.Matchers;

public class AuthOfUserTest {

    Steps step = new Steps();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.URL;
    }


    @Test
    @DisplayName("Проверка аутентификации со статичным пользователем")
    public void successLogin() {
        step.authOfUser(step.staticUser).then().statusCode(200)
                .and().body("success", Matchers.is(true))
                .and().body("accessToken", Matchers.notNullValue())
                .and().body("refreshToken", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Проверка аутентификации с помощью не существующего пользователя")
    public void failedLogin() {
        step.authOfUser(step.user).then().statusCode(401)
                .and().body("success", Matchers.is(false))
                .and().body("message", Matchers.equalTo("email or password are incorrect"));
    }
}
