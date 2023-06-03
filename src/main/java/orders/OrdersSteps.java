package orders;

import client.RestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersSteps extends RestClient {
    private static final String ORDER_PATH = "api/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(String accessToken, Orders orders) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(orders)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Получение заказов конкретного пользователя")
    public ValidatableResponse getOrders(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("Создание заказа без ингредиентов")
    public ValidatableResponse createOrderWithoutIngredients(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuth(Orders orders) {
        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(ORDER_PATH)
                .then();
    }

}
