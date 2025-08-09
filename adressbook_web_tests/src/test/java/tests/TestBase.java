package tests;

import manager.ApplicationManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

import static manager.HelperBase.driver;

public class TestBase {
    protected static ApplicationManager app;

    @BeforeEach
    public void setUp() {
        if (app == null){
            app = new ApplicationManager();
        }
        app.init(System.getProperty("browser","chrome"));
    }
    public static String randomString(int n){
        var rnd = new Random();
        var result = "";
        for (int i =0; i<n; i++){
            result = result + (char)('a' + rnd.nextInt(26));
        }
        return result;
    }
    public void waitForDomLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(driver ->
                ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete")
        );
    }

}
