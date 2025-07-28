import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GroupRemovalTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        if (driver == null) {
            driver = new ChromeDriver();
            Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));
            driver.get("http://localhost/addressbook/index.php");
            driver.manage().window().setSize(new Dimension(2360, 1321));
            driver.findElement(By.name("user")).sendKeys("admin");
            driver.findElement(By.name("pass")).sendKeys("secret");
            driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
        }
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    @Test
    public void canRemoveGroup() throws InterruptedException {
        if (!isElementPresent(By.name("new"))) {
            driver.findElement(By.linkText("groups")).click();
        }
        if (!isElementPresent(By.name("selected[]"))) {
            driver.findElement(By.name("new")).click();
            Thread.sleep(300);
            driver.findElement(By.name("group_name")).sendKeys("group name");
            driver.findElement(By.name("group_header")).sendKeys("group header");
            driver.findElement(By.name("group_footer")).sendKeys("group footer");
            driver.findElement(By.name("submit")).click();
            driver.findElement(By.linkText("groups")).click();
        }
        driver.findElement(By.name("selected[]")).click();
        driver.findElement(By.xpath("(//input[@name=\'delete\'])[2]")).click();
        driver.findElement(By.linkText("group page")).click();
        driver.findElement(By.linkText("Logout")).click();
    }
}
