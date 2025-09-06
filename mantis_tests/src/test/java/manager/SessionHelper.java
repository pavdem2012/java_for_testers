package manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {
    public SessionHelper(ApplicationManager manager) {
        super(manager);
    }

    public void login(String user, String password) {
        type(By.name("username"), user);
        click(By.cssSelector("input[type='submit']"));
        type(By.name("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }

    public boolean isLoggedIn() {
        return isElementsPresent(By.cssSelector("span.user-info"));
    }

    public void logout() {
        if (isLoggedIn()) {
            click(By.cssSelector("a[href*='logout']"));
        }
    }

    public void ensureLoggedOut() {
        if (isLoggedIn()) {
            logout();
        }
    }
}
