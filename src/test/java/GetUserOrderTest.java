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

public class GetUserOrderTest {
    private Order order;
    private OrderSteps orderSteps;
    private UserSteps userSteps;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
        ValidatableResponse registerResponse = userSteps.createUser(user);
        accessToken = registerResponse.extract().path("accessToken");
        orderSteps = new OrderSteps();
        order = OrderGenerator.getOrderData();
        orderSteps.createOrder(accessToken, order);
    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя c авторизацией")
    @Description("Проверяется, что можно получить заказы пользователя с авторизацией")
    public void getOrdersUserWithAuthTest() {
        ValidatableResponse ordersClientResponse = orderSteps.getOrders(accessToken);
        ordersClientResponse
                .statusCode(200)
                .body("success", equalTo(true));

    }

    @Test
    @DisplayName("Получение заказов конкретного пользователя без авторизации")
    @Description("Проверяется, что невозможно получить заказы пользователя без авторизации")
    public void getOrdersUserWithoutAuthTest() {
        ValidatableResponse ordersClientResponse = orderSteps.getOrders("");
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
