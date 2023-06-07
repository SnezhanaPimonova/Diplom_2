package user;

import client.RestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserSteps extends RestClient {
    private static final String USER_REGISTER_PATH = "/api/auth/register";
    private static final String USER_LOGIN_PATH = "/api/auth/login";
    private static final String USER_UPDATE_AND_DELETE_PATH = "/api/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_REGISTER_PATH)
                .then();
    }

    @Step("Логин юзера в системе")
    public ValidatableResponse loginUser(UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(userCredentials)
                .when()
                .post(USER_LOGIN_PATH)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_UPDATE_AND_DELETE_PATH)
                .then();
    }

    @Step("Обновление данных пользователя")
    public ValidatableResponse updateUserData(String accessToken, UserCredentials userCredentials) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(userCredentials)
                .when()
                .patch(USER_UPDATE_AND_DELETE_PATH)
                .then();
    }

}
