package com.newsletter.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * TestBase
 *
 * All test classes extend this.
 * Handles driver lifecycle and base URL resolution.
 */
public class TestBase {

    protected WebDriver driver;

    protected static final String BASE_URL = System.getProperty(
            "base.url",
            "https://bayingana.github.io/NEWSLETTER/"
    );

    @BeforeEach
    public void setUp() {
        driver = DriverManager.getDriver();
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        DriverManager.quitDriver();
    }
}