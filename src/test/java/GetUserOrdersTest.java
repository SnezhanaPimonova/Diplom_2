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

public class GetUserOrdersTest {
    private Orders orders;
    private OrdersSteps ordersSteps;
    private UserSteps userSteps;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
        ValidatableResponse registerResponse = userSteps.createUser(user);
        accessToken = registerResponse.extract().path("accessToken");
        ordersSteps = new OrdersSteps();
        orders = OrdersGenerator.getRandomOrder();
        ordersSteps.createOrder(accessToken, orders);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя c авторизацией")
    @Description("Проверяется, что можно получить заказы пользователя с авторизацией")
    public void getOrdersUserWithAuthTest() {
        ValidatableResponse ordersClientResponse = ordersSteps.getOrders(accessToken);
        ordersClientResponse
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя без авторизации")
    @Description("Проверяется, что невозможно получить заказы пользователя без авторизации")
    public void getOrdersUserWithoutAuthTest() {
        ValidatableResponse ordersClientResponse = ordersSteps.getOrders("");
        ordersClientResponse
                .statusCode(401)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}
