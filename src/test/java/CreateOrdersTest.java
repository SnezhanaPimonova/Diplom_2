import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import orders.Orders;
import orders.OrdersGenerator;
import orders.OrdersSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import user.UserSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateOrdersTest {
    private UserSteps userSteps;
    private User user;
    private String accessToken;
    private Orders orders;
    private OrdersSteps ordersSteps;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
        ValidatableResponse createUserResponse = userSteps.createUser(user);
        accessToken = createUserResponse.extract().path("accessToken");
        ordersSteps = new OrdersSteps();
    }

    @Test
    @DisplayName("Cоздание заказа c авторизацией и с ингредиентами")
    @Description("Проверяется, что заказ можно создать автрозиванному пользователю")
    public void createOrdersSuccessTest() {
        orders = OrdersGenerator.getRandomOrder();
        ValidatableResponse createOrderResponse = ordersSteps.createOrder(accessToken, orders);
        createOrderResponse
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверяется, что заказ можно создать без авторизации")
    public void createOrdersWithoutAuthTest() {
        orders = OrdersGenerator.getRandomOrder();
        ValidatableResponse createOrdersResponse = ordersSteps.createOrderWithoutAuth(orders);
        createOrdersResponse
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверяется, что заказ нельзя создать без ингредиентов")
    public void createOrdersWithoutIngredientsTest() {
        orders = OrdersGenerator.getRandomOrder();
        ValidatableResponse createOrdersResponse = ordersSteps.createOrderWithoutIngredients(accessToken);
        createOrdersResponse
                .statusCode(400)
                .and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Cоздание заказа с неверным хешем ингредиентов")
    @Description("Проверяется, что заказ нельзя создать с некорректными ингредиентами")
    public void createOrdersWithIncorrectIngredientsTest() {
        orders = OrdersGenerator.getIncorrectOrder();
        ValidatableResponse createOrdersResponse = ordersSteps.createOrder(accessToken, orders);
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


