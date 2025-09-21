package tests;

import common.CommonFunctions;
import manager.MailHelper;
import manager.UserHelper;
import manager.model.MailMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UserRegistrationTests extends TestBase {

    // создать адрес на почтовом сервере (JamesHelper)
// заполнить форму создания  и отправляем (в браузере)
// ждем и получаем почту по var email (MailHelper)
// извлекаем ссылку из письма
// проходим по ссылке и завершаем регистрацию пользователя (в браузере)
// проверяем что пользователь может залогиниться (HttpSessionHelper)
    @Test
    void canRegisterUser() {

        var username = CommonFunctions.randomString(5);
        var email = String.format("%s@localhost", username);
        var password = "password";

        // Шаг 1: создать адрес на почтовом сервере
        app.jamesCli().addUser(email, password);

        // Шаг 2: заполнить форму создания и отправить (в браузере)
        app.driver().get(app.property("web.baseUrl") + "/signup_page.php");

        // Заполняем форму регистрации
        app.driver().findElement(org.openqa.selenium.By.name("username")).sendKeys(username);
        app.driver().findElement(org.openqa.selenium.By.name("email")).sendKeys(email);
        app.driver().findElement(org.openqa.selenium.By.cssSelector("input[type='submit']")).click();


        // Шаг 3: ждем и получаем почту
        List<MailMessage> messages = app.mail().receive(email, password, Duration.ofSeconds(60));
        Assertions.assertEquals(1, messages.size(), "Должно прийти 1 письмо");

        // Шаг 4: извлекаем ссылку из письма
        String text = messages.get(0).content();
        Pattern pattern = Pattern.compile("http://\\S+");
        var matcher = pattern.matcher(text);
        Assertions.assertTrue(matcher.find(), "В письме должна быть ссылка");

        String url = text.substring(matcher.start(), matcher.end());
        System.out.println("Извлеченная ссылка: " + url);

        // Шаг 5: проходим по ссылке и завершаем регистрацию
        app.driver().get(url);

        // Заполняем форму завершения регистрации
        app.driver().findElement(org.openqa.selenium.By.id("realname")).sendKeys(username);
        app.driver().findElement(org.openqa.selenium.By.id("password")).sendKeys(password);
        app.driver().findElement(org.openqa.selenium.By.id("password-confirm")).sendKeys(password);
        app.driver().findElement(org.openqa.selenium.By.cssSelector("button[type=\"submit\"].btn-success")).click();

        // Шаг 6: проверяем что пользователь может залогиниться
        app.http().login(username, "password");
        Assertions.assertTrue(app.http().isLoggedIn(), "Пользователь должен быть залогинен");
    }

    public static Stream<String> randomUser() {
        return Stream.of(CommonFunctions.randomString(8));
    }

    @ParameterizedTest
    @MethodSource("randomUser")
    void canCreateUser(String user) {
        var username = CommonFunctions.randomString(5);
        var email = String.format("%s@localhost", username);
        var password = "password";
        // Шаг 1: создать адрес на почтовом сервере
        app.jamesApi().addUser(email, password);

        // Шаг 2: заполнить форму создания и отправить (в браузере)
        UserHelper.startCreation(username, email);

        // Шаг 3: ждем и получаем почту
        List<MailMessage> messages = MailHelper.getMailMessages(email, password);
        Assertions.assertEquals(1, messages.size(), "Должно прийти 1 письмо");

        // Шаг 4: извлекаем ссылку из письма
        String url = MailHelper.getUrl(messages);

        // Шаг 5: проходим по ссылке и завершаем регистрацию
        app.driver().get(url);
        UserHelper.finishCreation(username, password);

        // Шаг 6: проверяем что пользователь может залогиниться
        app.http().login(username, password);
        Assertions.assertTrue(app.http().isLoggedIn(), "Пользователь должен быть залогинен");
    }

    @Test
    void canRegisterUserApi() {
        // Генерируем уникальные данные для пользователя
        String username = CommonFunctions.randomString(8);
        String email = username + "@localhost";
        String password = "password";

        // 1. Регистрируем почтовый ящик на сервере James через REST API
        app.jamesApi().addUser(email, password);

        // 2. Очищаем почтовый ящик от возможных предыдущих писем
        app.mail().drain(email, password);

        // 3. Начинаем регистрацию пользователя в Mantis через REST API
        app.rest().startUserRegistration(username, email);

        // 4. Ждем письмо с подтверждением
        List<MailMessage> messages = app.mail().receive(email, password, Duration.ofSeconds(60));

        // 5. Извлекаем ссылку для подтверждения из письма
        String confirmationUrl = app.mail().extractConfirmationUrl(messages);

        // 6. Переходим по ссылке для подтверждения регистрации
        app.driver().get(confirmationUrl);

        // 7. Завершаем регистрацию - устанавливаем пароль
        app.user().finishRegistration(password);

        // 8. Проверяем, что пользователь может войти в систему через HTTP
        app.http().login(username, password);

        // 9. Проверяем, что пользователь действительно аутентифицирован
        app.http().isLoggedIn();
    }

}

