package jschool.configs;

import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumConfig {

  private WebDriver webDriver;

  public SeleniumConfig() {
    Capabilities capabilities = DesiredCapabilities.chrome();
    webDriver = new ChromeDriver(capabilities);
    webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  static {
    System.setProperty("webdriver.chrome.driver", findFile("chromedriver.exe"));
  }

  private static String findFile(String filename) {
    String [] paths = {"", "bin/", "target/classes","lib/"};
    for (String path : paths) {
      if (new File(path + filename).exists())
        return path + filename;
    }
    return "";
  }

  public WebDriver getWebDriver(){
    return webDriver;
  }
}
