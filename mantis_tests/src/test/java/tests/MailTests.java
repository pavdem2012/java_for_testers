package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MailTests extends TestBase{
    @Test
    void canReceiveMail() {
        var messages = app.mail().receive("user1@localhost", "password");
        System.out.println(messages);
        Assertions.assertEquals(1,messages.size());
    }
}
