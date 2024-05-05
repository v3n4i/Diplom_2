import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class Steps {

    UserPODJ staticUser = new UserPODJ(Constants.email_static, Constants.password_static, Constants.name_static);

    UserPODJ user = new UserPODJ(Constants.email, Constants.password, Constants.name);

    UserPODJ userUpdated = new UserPODJ(Constants.email, Constants.password, Constants.name);
    UserPODJ emptyFieldName = new UserPODJ(Constants.email, Constants.password, null);
    UserPODJ emptyFieldPassword = new UserPODJ(Constants.email, null, Constants.name);
    UserPODJ emptyFieldEmail = new UserPODJ(null, Constants.password, Constants.name);

    @Step("Создание пользователя")
    public Response creationOfUser(UserPODJ user) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(user)
                        .when()
                        .post("/api/auth/register");
    }

    @Step("Авторизация пользователя")
    public Response authOfUser(UserPODJ user) {
        return
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(user)
                        .when()
                        .post("/api/auth/login");
    }


@Step("Создание юзера и получение accessToken")
    public ValidatableResponse getToken(UserPODJ user) {
         return RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/register").then();

    }

      @Step("Логин статичного юзера и получение токена")
    public String successLogin() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(staticUser)
                .when()
                .post("/api/auth/login").as(Tokens.class).getAccessToken().replaceAll("Bearer ", "");
    }

    @Step("Получение списка ингредиентов")
    public Response getListOfIngredients() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .get("/api/ingredients");
    }


}