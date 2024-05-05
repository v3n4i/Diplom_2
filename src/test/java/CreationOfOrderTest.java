import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreationOfOrderTest {
    Steps step = new Steps();
    static Faker faker = new Faker();

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.URL;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    }


    @Test
    @DisplayName("Проверка добавления ордера на юзера с авторизацией")
    public void successCreationOfOrderTestWithAuth() {
        List<String> listOfOrders = step.getListOfIngredients().getBody().path("data._id");
        IngredientsPODJ jsonOne = new IngredientsPODJ(listOfOrders.subList(1, 3));
        UserPODJ usr = new UserPODJ(faker.name().firstName() + "@ya.ru", faker.name().username() + "pass", faker.name().username() + "va");
        String str = step.getToken(usr).extract().path("accessToken").toString();
        given()
                .header("Authorization", str
                )
                .header("Content-type", "application/json")
                .body(jsonOne)
                .when()
                .post("/api/orders")
                .then().statusCode(200)
                .body("success", Matchers.is(true));
        given().header("Authorization",
                str).delete("/api/auth/user");
    }

    @Test
    @DisplayName("Проверка добавления заказа на юзера без авторизации")
    public void failedCreationOfOrderTestWithoutAuth() {
        List<String> listOfOrders = step.getListOfIngredients().getBody().path("data._id");
        IngredientsPODJ jsonOne = new IngredientsPODJ(listOfOrders.subList(2, 4));
        given()
                .header("Content-type", "application/json")
                .body(jsonOne)
                .when()
                .post("/api/orders")
                .then().statusCode(200)
                .body("success", Matchers.is(true));
    }

    @Test
    @DisplayName("Проверка добавления заказа с пустыми ингредиентами на пользователя")
    public void creationOfOrderTestWithAuthAndWithEmptyIngredients() {
        UserPODJ usn = new UserPODJ(faker.name().firstName() + "@gmail.ru", faker.name().username() + "passw", faker.name().username() + "vad");
        String auth = step.getToken(usn).extract().path("accessToken").toString();
        IngredientsPODJ jsonOne = new IngredientsPODJ();
        given()
                .header("Authorization",
                        auth)
                .header("Content-type", "application/json")
                .body(jsonOne)
                .when()
                .post("/api/orders")
                .then().statusCode(400)
                .body("success", Matchers.is(false));
        given().header("Authorization",
                auth).delete("/api/auth/user");
    }


    @Test
    @DisplayName("Проверка добавления заказа с неправильными ингредиентами на пользователя")
    public void creationOfOrderTestWithAuthAndWithWrongIngredients() {
        UserPODJ usz = new UserPODJ(faker.name().firstName() + "@rmb.ru", faker.name().username() + "passt", faker.name().username() + "var");
        String authToken = step.getToken(usz).extract().path("accessToken").toString();
        List<String> ingredients = new ArrayList<>();
        ingredients.add("61c0c5a71d1f82001bdaaa6d1");
        IngredientsPODJ json = new IngredientsPODJ(ingredients);
        given()
                .header("Authorization",
                        authToken.replaceAll("Bearer ", ""))
                .header("Content-type", "application/json")
                .when()
                .body(json)
                .when()
                .post("/api/orders")
                .then().statusCode(500);
        given().header("Authorization",
                authToken).delete("/api/auth/user");
    }
}
