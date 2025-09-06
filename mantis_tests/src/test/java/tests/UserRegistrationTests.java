package tests;

import common.CommonFunctions;
import manager.model.MailMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

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
}

