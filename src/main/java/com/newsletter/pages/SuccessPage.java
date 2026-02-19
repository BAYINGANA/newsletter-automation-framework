package com.newsletter.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuccessPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ─────────────────────────────────────────────────────────────

    @FindBy(id = "success")
    private WebElement successCard;

    @FindBy(css = ".success-card h1")
    private WebElement successHeading;

    @FindBy(css = ".success-card p")
    private WebElement successMessage;

    @FindBy(id = "success-email")
    private WebElement successEmail;

    @FindBy(id = "dismiss")
    private WebElement dismissButton;

    @FindBy(css = ".check img")
    private WebElement checkIcon;

    // ── Constructor ───────────────────────────────────────────────────────────

    public SuccessPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public NewsletterPage clickDismiss() {
        wait.until(ExpectedConditions.elementToBeClickable(dismissButton));
        dismissButton.click();
        return new NewsletterPage(driver);
    }

    // ── Assertions / Getters ──────────────────────────────────────────────────

    public boolean isSuccessCardVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successCard));
            return successCard.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessHeadingText() {
        wait.until(ExpectedConditions.visibilityOf(successHeading));
        return successHeading.getText();
    }

    public String getConfirmedEmail() {
        wait.until(ExpectedConditions.visibilityOf(successEmail));
        return successEmail.getText();
    }

    public String getSuccessMessageText() {
        return successMessage.getText();
    }

    public boolean isDismissButtonVisible() {
        return dismissButton.isDisplayed();
    }

    public String getDismissButtonText() {
        return dismissButton.getText();
    }

    public boolean isCheckIconVisible() {
        return checkIcon.isDisplayed();
    }
}