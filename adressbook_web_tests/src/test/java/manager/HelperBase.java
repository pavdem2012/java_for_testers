package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.nio.file.Paths;

public class HelperBase {
    protected final ApplicationManager manager;
    public static WebDriver driver = null;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
        this.driver = manager.driver;
    }

    protected static void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void type(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = driver.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
        }
    }

    protected static boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    protected void attach(By locator, String file) {
        driver.findElement(locator).sendKeys(Paths.get(file).toAbsolutePath().toString());
    }

    protected void select(By locator, String value) {
        if (value != null) {
            new Select(driver.findElement(locator)).selectByVisibleText(value);
        }
    }


}