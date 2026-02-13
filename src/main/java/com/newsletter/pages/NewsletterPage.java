package com.newsletter.pages;

import com.newsletter.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class NewsletterPage {

    private final WebDriver driver;

    public NewsletterPage(WebDriver driver){
        this.driver = driver;
        WaitUtils waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "email")
    private WebElement emailField;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = ".success-card h1")
    private WebElement successMessage;

    public void open (String url){
        driver.get(url);
    }

    public void enterEmail(String email){
        emailField.sendKeys(email);
    }

    public void clickSubmit(){
        submitButton.click();
    }

    public String getSuccessMessage(){
        return successMessage.getText();
    }
}
