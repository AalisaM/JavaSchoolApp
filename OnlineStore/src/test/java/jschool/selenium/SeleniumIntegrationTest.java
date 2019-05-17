package jschool.selenium;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import jschool.configs.SeleniumInitializer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumIntegrationTest {

  private static SeleniumInitializer seleniumInitializer;
  private static WebDriver webDriver;
  private static int userId;
  private static String emailInput;

  @BeforeClass
  public static void setUp() {
    seleniumInitializer = new SeleniumInitializer();
    webDriver = seleniumInitializer.getDriver();
    webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    Random randomGenerator = new Random();
    userId = randomGenerator.nextInt(1000);
    emailInput = "username" + userId + "@gmail.com";
  }

  @AfterClass
  public static void tearDown() {
    seleniumInitializer.closeWindow();
  }

  @Test
  public void seleniumIsUp() {
    String title = seleniumInitializer.getTitle();
    assertNotNull(title);
  }

  @Test
  public void testRegistration() throws InterruptedException {
    webDriver.get("http://localhost:8086/Root/registration");

    WebElement email = webDriver.findElement(By.id("email"));
    email.sendKeys(emailInput);

    WebElement fullName = webDriver.findElement(By.id("fullName"));
    fullName.sendKeys("Test Testov");

    WebElement birthDate = webDriver.findElement(By.id("birth"));
    birthDate.sendKeys("01-01-1980");

    WebElement phone = webDriver.findElement(By.id("phone"));
    phone.sendKeys("+78005553535");

    WebElement password = webDriver.findElement(By.id("password"));
    WebElement confirmPassword = webDriver.findElement(By.id("passConfirm"));
    confirmPassword.sendKeys("password");
    password.sendKeys("password");

    webDriver.findElement(By.xpath("//button[text()='Sign Up']")).click();

    Thread.sleep(2000);
    assertTrue(webDriver.getCurrentUrl().equals("http://localhost:8086/Root/"));
  }

  @Test
  public void testRegistrationFail() throws InterruptedException {
    webDriver.get("http://localhost:8086/Root/registration");

    WebElement email = webDriver.findElement(By.id("email"));
    email.sendKeys("miragge8.al@gmail.com");

    WebElement fullName = webDriver.findElement(By.id("fullName"));
    fullName.sendKeys("Test Testov");

    WebElement birthDate = webDriver.findElement(By.id("birth"));
    birthDate.sendKeys("01-01-1980");

    WebElement phone = webDriver.findElement(By.id("phone"));
    phone.sendKeys("+78005553535");

    WebElement password = webDriver.findElement(By.id("password"));
    WebElement confirmPassword = webDriver.findElement(By.id("passConfirm"));
    confirmPassword.sendKeys("password");
    password.sendKeys("password");

    webDriver.findElement(By.xpath("//button[text()='Sign Up']")).click();

    Thread.sleep(2000);
    assertTrue(webDriver.getCurrentUrl().contains("http://localhost:8086/Root/registration"));
  }

  @Test
  public void testLogin_successful() throws InterruptedException {
    webDriver.get("http://localhost:8086/Root/login");
    WebElement email = webDriver.findElement(By.id("username"));
    email.sendKeys("miragge8.al@gmail.com");

    WebElement password = webDriver.findElement(By.id("password"));
    password.sendKeys("1234");

    webDriver.findElement(By.xpath("//input[@id='Login']")).click();
    Thread.sleep(2000);

    assertTrue(webDriver.getCurrentUrl().equals("http://localhost:8086/Root/"));
  }

  @Test
  public void testLogin_unSuccessful() throws InterruptedException {
    webDriver.get("http://localhost:8086/Root/login");
    WebElement email = webDriver.findElement(By.id("username"));
    email.sendKeys("wrong@emOil.comp");

    WebElement password = webDriver.findElement(By.id("password"));
    password.sendKeys("passworddd");

    webDriver.findElement(By.xpath("//input[@id='Login']")).click();
    Thread.sleep(2000);

    assertTrue(webDriver.getCurrentUrl().contains("?error"));
  }


}
