import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class UpdateUserInfoTest {
    Steps step = new Steps();

    static Faker faker = new Faker();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.URL;
    }

    @Test
    @DisplayName("Проверка успешного обновления юзера(email, pass, name)")
        public void successUpdateInfoTest() {
        UserPODJ userr = new UserPODJ(faker.name().firstName()+"@rbm.ru", faker.name().username()+"past", faker.name().username()+"var");
        UserPODJ userrUpd = new UserPODJ(faker.name().firstName()+"@rym.ru", faker.name().username()+"pst", faker.name().username()+"var");
        String auth = step.getToken(userr).extract().path("accessToken").toString();
        given()
                .header("Content-type", "application/json")
                .header("Authorization",
                        auth)
                .body(userrUpd)
                .when()
                .patch("/api/auth/user")
                .then().statusCode(200)
                .body("success", Matchers.is(true));
        given().header("Authorization",
                auth).delete("/api/auth/user");
    }

    @Test
    @DisplayName("Проверка обновления пользователя без авторизации")
    public void failedUpdateInfoTest() {
        given()
                .header("Content-type", "application/json")
                .body(step.userUpdated)
                .when()
                .patch("/api/auth/user")
                .then().statusCode(401)
                .body("success", Matchers.is(false))
                .body("message", Matchers.equalTo("You should be authorised"));
    }
}
