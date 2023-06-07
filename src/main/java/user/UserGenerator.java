package user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User getRandomUser(){
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@mail.ru",
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphabetic(10));
    }

    public static User getRandomUserWithoutEmailField() {
        return new User(
                "",
                RandomStringUtils.randomAlphanumeric(10),
                RandomStringUtils.randomAlphabetic(10));
    }

    public static User getRandomUserWithoutPasswordField() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@mail.ru",
                "",
                RandomStringUtils.randomAlphabetic(10));
    }

    public static User getRandomUserWithoutNameField() {
        return new User(
                RandomStringUtils.randomAlphanumeric(10) + "@mail.ru",
                RandomStringUtils.randomAlphabetic(10),
                "");
    }
}
