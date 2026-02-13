package com.newsletter.tests;

import com.newsletter.core.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    void setUp(){
        driver = DriverFactory.getDriver();
    }

    @AfterEach
    void tearDown(){
        DriverFactory.quitDriver();
    }
}
