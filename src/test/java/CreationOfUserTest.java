import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class CreationOfUserTest {

    Steps step = new Steps();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.URL;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Успешное создание юзера с помощью библиотеки faker")
    public void successCreationOfUser() {
        step.creationOfUser(step.user).then().statusCode(200)
                .and().body("accessToken", Matchers.notNullValue())
                .and().body("success", Matchers.is(true))
                .and().body("user.email", Matchers.notNullValue())
                .and().body("user.name", Matchers.notNullValue())
                .and().body("refreshToken", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Проверка на создание дубликата")
    public void duplicateCheckOfUserCreation() {
        step.creationOfUser(step.staticUser);
        step.creationOfUser(step.staticUser).then().statusCode(403)
                .and().body("success", Matchers.is(false))
                .and().body("message", Matchers.equalTo("User already exists"));
    }

    @Test
    @DisplayName("Проверка на создание юзера с пустым именем")
    public void emptyFieldNameCheckOfUserCreation() {
        step.creationOfUser(step.emptyFieldName).then().statusCode(403)
                .and().body("success", Matchers.is(false))
                .and().body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Проверка на создание юзера с пустым паролем")
    public void emptyFieldPasswordCheckOfUserCreation() {
        step.creationOfUser(step.emptyFieldPassword).then().statusCode(403)
                .and().body("success", Matchers.is(false))
                .and().body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Проверка на создание юзера с пустым email")
    public void emptyFieldEmailCheckOfUserCreation() {
        step.creationOfUser(step.emptyFieldEmail).then().statusCode(403)
                .and().body("success", Matchers.is(false))
                .and().body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

}
