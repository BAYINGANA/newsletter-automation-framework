package com.newsletter.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * DriverManager
 *
 * Centralises WebDriver creation and teardown.
 * Reads system properties set by Maven / CI to decide
 * whether to run headless (CI) or headed (local).
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {}

    /**
     * Returns the WebDriver for the current thread,
     * creating it if it does not yet exist.
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            driverThreadLocal.set(createDriver());
        }
        return driverThreadLocal.get();
    }

    /**
     * Quits the driver and removes it from the thread-local store.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    // ── private helpers ──────────────────────────────────────────────────────

    private static WebDriver createDriver() {
        // WebDriverManager automatically downloads the correct ChromeDriver version
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
            // Required for GitHub Actions (no display)
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        // Suppress automation info bar and unnecessary logs
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.setExperimentalOption("excludeSwitches",
                java.util.List.of("enable-automation"));

        return options;
    }
}