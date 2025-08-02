package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ContactRemovalTests {
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
  public void canRemoveContact() {
    driver.get("http://localhost//addressbook/index.php");
    driver.manage().window().setSize(new Dimension(2544, 1332));
    driver.findElement(By.name("user")).sendKeys("admin");
    driver.findElement(By.name("pass")).sendKeys("secret");
    driver.findElement(By.xpath("//input[@value=\'Login\']")).click();
    driver.findElement(By.name("selected[]")).click();
    driver.findElement(By.xpath("//input[@value=\'Delete\']")).click();
  }
}
