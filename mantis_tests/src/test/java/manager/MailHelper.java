package manager;

import jakarta.mail.*;
import manager.model.MailMessage;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import tests.TestBase;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class MailHelper extends HelperBase {
    public MailHelper(ApplicationManager manager) {
        super(manager);
    }

    @NotNull
    public static String getUrl(List<MailMessage> messages) {
        String text = messages.get(0).content();
        Pattern pattern = Pattern.compile("http://\\S+");
        var matcher = pattern.matcher(text);
        Assertions.assertTrue(matcher.find(), "В письме должна быть ссылка");
        String url = text.substring(matcher.start(), matcher.end());
        return url;
    }

    public static List<MailMessage> getMailMessages(String email, String password) {
        List<MailMessage> messages = TestBase.app.mail().receive(email, password, Duration.ofSeconds(60));
        return messages;
    }

    public List<MailMessage> receive(String username, String password, Duration duration) {
        var start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + duration.toMillis()) {
            try {
                var inbox = getInbox(username, password);
                inbox.open(Folder.READ_ONLY);
                var messages = inbox.getMessages();

                var result = Arrays.stream(messages)
                        .map(m -> {
                            try {
                                return new MailMessage()
                                        .withFrom(m.getFrom()[0].toString())
                                        .withContent((String) m.getContent());
                            } catch (MessagingException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList();
                inbox.close();
                inbox.getStore().close();
                if (result.size() > 0) {
                    return result;
                }
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("NO MAIL");
    }


    public void drain(String username, String password) {
        var inbox = getInbox(username, password);
        try {
            inbox.open(Folder.READ_WRITE);
            Arrays.stream(inbox.getMessages()).forEach(m -> {
                try {
                    m.setFlag(Flags.Flag.DELETED, true);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
            inbox.close();
            inbox.getStore().close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private static Folder getInbox(String username, String password) {

        try {
            var session = Session.getInstance(new Properties());
            Store store = session.getStore("pop3");
            store.connect("localhost", username, password);
            return store.getFolder("INBOX");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static String extractConfirmationUrl(List<MailMessage> messages) {
        String text = messages.get(0).content();
        Pattern pattern = Pattern.compile("http://[^\\s]+");
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            return text.substring(matcher.start(), matcher.end());
        }
        throw new RuntimeException("Confirmation URL not found in email");
    }
}
