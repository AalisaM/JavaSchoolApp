package jschool.configs;

import org.openqa.selenium.WebDriver;

public class SeleniumInitializer {
  private SeleniumConfig config;
  private String url = "http://localhost:8086/";

  public SeleniumInitializer() {
    config = new SeleniumConfig();
    config.getWebDriver().get(url);
  }

  public void closeWindow() {
    this.config.getWebDriver().close();
  }

  public String getTitle() {
    return this.config.getWebDriver().getTitle();
  }

  public WebDriver getDriver(){
    return config.getWebDriver();
  }
}
