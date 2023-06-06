import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.Order;
import orders.OrderGenerator;
import orders.OrderSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import user.UserSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrderTest {
    private UserSteps userSteps;
    private User user;
    private String accessToken;
    private Order order;
    private OrderSteps orderSteps;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
        ValidatableResponse createUserResponse = userSteps.createUser(user);
        accessToken = createUserResponse.extract().path("accessToken");
        orderSteps = new OrderSteps();
    }

    @Test
    @DisplayName("Cоздание заказа c авторизацией и с ингредиентами")
    @Description("Проверяется, что заказ можно создать автрозиванному пользователю")
    public void createOrdersSuccessTest() {
        order = OrderGenerator.getOrderData();
        ValidatableResponse createOrderResponse = orderSteps.createOrder(accessToken, order);
        createOrderResponse
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверяется, что заказ можно создать без авторизации")
    public void createOrdersWithoutAuthTest() {
        order = OrderGenerator.getOrderData();
        ValidatableResponse createOrdersResponse = orderSteps.createOrderWithoutAuth(order);
        createOrdersResponse
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверяется, что заказ нельзя создать без ингредиентов")
    public void createOrdersWithoutIngredientsTest() {
        order = OrderGenerator.getOrderData();
        ValidatableResponse createOrdersResponse = orderSteps.createOrderWithoutIngredients(accessToken);
        createOrdersResponse
                .statusCode(400)
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Cоздание заказа с неверным хешем ингредиентов")
    @Description("Проверяется, что заказ нельзя создать с некорректными ингредиентами")
    public void createOrdersWithIncorrectIngredientsTest() {
        order = OrderGenerator.getIncorrectOrder();
        ValidatableResponse createOrdersResponse = orderSteps.createOrder(accessToken, order);
        createOrdersResponse
                .statusCode(500);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}


