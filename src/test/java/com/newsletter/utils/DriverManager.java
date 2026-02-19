package com.newsletter.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            driverThreadLocal.set(createDriver());
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) return;

        try {
            driver.quit();
        } catch (Exception e) {
            System.err.println("[DriverManager] Warning during quit: " + e.getMessage());
        } finally {
            driverThreadLocal.remove();
        }
    }

    private static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = buildChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }

    private static ChromeOptions buildChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("excludeSwitches",
                java.util.List.of("enable-automation"));

        return options;
    }
}