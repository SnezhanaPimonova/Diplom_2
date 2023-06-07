import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserCredentials;
import user.UserGenerator;
import user.UserSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest {
    private UserSteps userSteps;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
        ValidatableResponse responseCreate = userSteps.createUser(user);
        accessToken = responseCreate.extract().path("accessToken");
    }


    @Test
    @DisplayName("Успешная авторизация")
    @Description("Проверяется авторизации с валидными данными")
    public void successLoginUserTest() {
        ValidatableResponse loginResponse = userSteps.loginUser(UserCredentials.from(user));
        loginResponse
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин с неверным email")
    @Description("Проверяется, что нельзя авторизоваться с некорректным email")
    public void loginUserWithInvalidEmailTest() {
        ValidatableResponse loginResponse = userSteps.loginUser(UserCredentials.from(new User(user.getEmail() + ".ru.ru", user.getPassword(), null)));
        loginResponse
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    @Description("роверяется, что нельзя авторизоваться с некорректным паролем")
    public void loginUserWithInvalidPasswordTest() {
        ValidatableResponse loginResponse = userSteps.loginUser(UserCredentials.from(new User(user.getEmail(), user.getPassword() + "invalid", null)));
        loginResponse
                .statusCode(401)
                .assertThat()
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userSteps.deleteUser(accessToken);
        }
    }

}
