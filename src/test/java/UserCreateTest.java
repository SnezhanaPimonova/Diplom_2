import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import user.UserSteps;

import static org.hamcrest.CoreMatchers.is;

public class UserCreateTest {

    private UserSteps userSteps;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
    }


    @Test
    @DisplayName("Cоздание пользователя с валидными данными")
    @Description("Проверяется, что пользователя можно создать")
    public void userCanBeCreatedTest() {
        ValidatableResponse createResponse = userSteps.createUser(user);
        createResponse
                .statusCode(200)
                .body("success", is(true));
        accessToken = createResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Cоздание пользователя, который уже зарегистрирован")
    @Description("Проверяется, что невозможно создать пользователя, который уже зарегистрирован")
    public void createDuplicateUserTest() {
        accessToken = userSteps.createUser(user).extract().path("accessToken");
        ValidatableResponse createResponse = userSteps.createUser(user);
        createResponse
                .statusCode(403)
                .assertThat()
                .body("success", is(false));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }
}



