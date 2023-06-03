import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserGenerator;
import user.UserSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreateWithoutRequiredFieldTest {
    private UserSteps userSteps;
    private User user;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
    }

    @Test
    @DisplayName("Cоздание пользователя без email")
    @Description("Проверется невозможность создания пользователя без email")
    public void createUserWithoutEmailFieldTest() {
        user = UserGenerator.getRandomUserWithoutEmailField();
        ValidatableResponse createResponse = userSteps.createUser(user);
        createResponse
                .statusCode(403)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Cоздание пользователя без пароля")
    @Description("Проверется невозможность создания пользователя без пароля")
    public void createUserWithoutPasswordFieldTest() {
        user = UserGenerator.getRandomUserWithoutPasswordField();
        ValidatableResponse createResponse = userSteps.createUser(user);
        createResponse
                .statusCode(403)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Cоздание пользователя без имени")
    @Description("Проверется невозможность создания пользователя без поля name")
    public void createUserWithoutNameField() {
        user = UserGenerator.getRandomUserWithoutNameField();
        ValidatableResponse createResponse = userSteps.createUser(user);
        createResponse
                .statusCode(403)
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"));
    }
}
