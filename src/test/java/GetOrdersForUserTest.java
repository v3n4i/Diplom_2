import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class GetOrdersForUserTest {
    Steps step = new Steps();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.URL;
    }

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void getOrdersWithAuth() {
        given()
                .header("Content-type", "application/json")
                .auth().oauth2(step.successLogin())
                .when()
                .get("/api/orders")
                .then().statusCode(200)
                .body("success", Matchers.is(true))
                .body("total", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void getOrdersWithOutAuth() {
        given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/orders")
                .then().statusCode(401)
                .body("success", Matchers.is(false))
                .body("message", Matchers.equalTo("You should be authorised"));
    }
}
