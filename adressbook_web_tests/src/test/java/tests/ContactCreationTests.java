package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class ContactCreationTests {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void canCreateContactWAllFields() throws InterruptedException {
        driver.get("http://localhost/addressbook/index.php");
        driver.manage().window().setSize(new Dimension(2544, 1332));
        driver.findElement(By.name("user")).sendKeys("admin");
        driver.findElement(By.name("pass")).sendKeys("secret");
        driver.findElement(By.xpath("//input[@value=\'Login\']")).click();

        //Thread.sleep(3000);

        driver.findElement(By.linkText("add new")).click();
        {
            WebElement element = driver.findElement(By.linkText("add new"));
            Actions builder = new Actions(driver);
            builder.doubleClick(element).perform();
        }
        driver.findElement(By.name("firstname")).click();
        driver.findElement(By.name("firstname")).sendKeys("First");
        driver.findElement(By.name("middlename")).click();
        driver.findElement(By.name("middlename")).sendKeys("Middle");
        driver.findElement(By.name("lastname")).click();
        driver.findElement(By.name("lastname")).sendKeys("Last");
        driver.findElement(By.name("nickname")).click();
        driver.findElement(By.name("nickname")).sendKeys("Nick");
        String projectDir = System.getProperty("user.dir");
        String absolutePath = projectDir + "/src/test/resources/хакатон.jpg";
        driver.findElement(By.name("photo")).sendKeys(absolutePath);
        driver.findElement(By.name("title")).click();
        driver.findElement(By.name("title")).sendKeys("Title");
        driver.findElement(By.name("company")).click();
        driver.findElement(By.name("company")).sendKeys("Company");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).sendKeys("Address");
        driver.findElement(By.name("home")).click();
        driver.findElement(By.name("home")).sendKeys("TelHome");
        driver.findElement(By.name("mobile")).click();
        driver.findElement(By.name("mobile")).sendKeys("TelMobile");
        driver.findElement(By.name("work")).click();
        driver.findElement(By.name("work")).sendKeys("TelWork");
        driver.findElement(By.name("fax")).click();
        driver.findElement(By.name("fax")).sendKeys("Fax");
        driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys("email1@emaill.email");
        driver.findElement(By.name("email2")).click();
        driver.findElement(By.name("email2")).sendKeys("email2@email.email");
        driver.findElement(By.name("email3")).click();
        driver.findElement(By.name("email3")).sendKeys("email3@email.email");
        driver.findElement(By.name("homepage")).click();
        driver.findElement(By.name("homepage")).sendKeys("www.homepage.page");
        driver.findElement(By.name("bday")).click();
        {
            WebElement dropdown = driver.findElement(By.name("bday"));
            dropdown.findElement(By.xpath("//option[. = '16']")).click();
        }
        driver.findElement(By.name("bmonth")).click();
        {
            WebElement dropdown = driver.findElement(By.name("bmonth"));
            dropdown.findElement(By.xpath("//option[. = 'March']")).click();
        }
        driver.findElement(By.name("byear")).click();
        driver.findElement(By.name("byear")).sendKeys("1975");
        driver.findElement(By.name("aday")).click();
        {
            WebElement dropdown = driver.findElement(By.name("aday"));
            dropdown.findElement(By.xpath("//option[. = '16']")).click();
        }
        driver.findElement(By.name("amonth")).click();
        {
            WebElement dropdown = driver.findElement(By.name("amonth"));
            dropdown.findElement(By.xpath("//option[. = 'March']")).click();
        }
        driver.findElement(By.name("ayear")).click();
        driver.findElement(By.name("ayear")).sendKeys("2025");
        driver.findElement(By.name("new_group")).click();
        driver.findElement(By.cssSelector("input:nth-child(75)")).click();
        driver.findElement(By.linkText("home page")).click();
    }
}
