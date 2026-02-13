package com.newsletter.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static WebDriver driver;
    public static WebDriver getDriver(){
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();

            if (Boolean.parseBoolean(System.getProperty("headless"))) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disagree-dev-shm-usage");
            }
            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
        }
            return driver;
        }
        public static void quitDriver(){
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
