package tests;

import org.junit.jupiter.api.Test;

public class UserRegistrationTests extends TestBase {
    @Test
    void canRegisterUser(String username) {
        var email = String.format("%s@localhost", username);
        // создать адрес на почтовом сервере (JamesHelper)
        // заполнить форму создания  и отправляем (в браузере)
        // ждем и получаем почту по var email (MailHelper)
        // извлекаем ссылку из письма
        // проходим по ссылке и завершаем регистрацию пользователя (в браузере)
        // проверяем что пользователь может залогиниться (HttpSessionHelper)
    }
}
