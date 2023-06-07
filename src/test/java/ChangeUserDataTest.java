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

public class ChangeUserDataTest {
    private UserSteps userSteps;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        user = UserGenerator.getRandomUser();
        ValidatableResponse createResponse = userSteps.createUser(user);
        accessToken = createResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Изменение данных пользователя с авторизацией")
    @Description("Проверяется, что можно изменить данные авторизованного пользователя")
    public void editUserDataWithAuthTest() {
        ValidatableResponse editResponse = userSteps.updateUserData(accessToken, UserCredentials.from(new User(user.getEmail() + "new", user.getPassword() + "new", user.getName() + "new")));
        editResponse
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Изменение данных пользователя без авторизации")
    @Description("Проверяется, что невозможно изменить данные авторизованного пользователя")
    public void editUserDataWithoutAuthTest() {
        ValidatableResponse editResponse = userSteps.updateUserData("", UserCredentials.from(user));
        editResponse
                .statusCode(401)
                .body("success", equalTo(false))
                .and()
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void cleanUp() {
        userSteps.deleteUser(accessToken);
    }

}
