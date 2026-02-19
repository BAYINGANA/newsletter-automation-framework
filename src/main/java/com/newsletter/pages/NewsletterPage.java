package com.newsletter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class NewsletterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Locators ──────────────────────────────────────────────────────────────

    @FindBy(id = "newsletter")
    private WebElement newsletterCard;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "button[type='submit']")
    private WebElement subscribeButton;

    @FindBy(id = "error")
    private WebElement errorText;

    @FindBy(id = "hero-img")
    private WebElement heroImage;

    @FindBy(css = ".text-section h1")
    private WebElement heading;

    @FindBy(css = ".text-section > p")
    private WebElement subheading;

    @FindBy(css = "ul li:nth-child(1)")
    private WebElement featureItem1;

    @FindBy(css = "ul li:nth-child(2)")
    private WebElement featureItem2;

    @FindBy(css = "ul li:nth-child(3)")
    private WebElement featureItem3;

    @FindBy(css = "label[for='email']")
    private WebElement emailLabel;

    // ── Constructor ───────────────────────────────────────────────────────────

    public NewsletterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    public NewsletterPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    /**
     * Clicks the Subscribe button without entering any email.
     * Used to test empty/invalid submission behaviour.
     */
    public NewsletterPage submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));
        subscribeButton.click();
        return this;
    }

    public SuccessPage submitValidEmail(String email) {
        enterEmail(email);
        wait.until(ExpectedConditions.elementToBeClickable(subscribeButton));
        subscribeButton.click();
        return new SuccessPage(driver);
    }

    // ── Visibility & State ────────────────────────────────────────────────────

    public boolean isNewsletterCardVisible() {
        try {
            return driver.findElement(By.id("newsletter")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorText));
            return errorText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorText() {
        wait.until(ExpectedConditions.visibilityOf(errorText));
        return errorText.getText();
    }

    public boolean isEmailInputInErrorState() {
        String classes = emailInput.getAttribute("class");
        return classes != null && classes.contains("error");
    }


    public boolean isSubscribeButtonDisplayed() {
        try {
            return subscribeButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isHeroImageDisplayed() {
        try {
            return heroImage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ── Text / Attribute Getters ──────────────────────────────────────────────

    public String getHeadingText() {
        wait.until(ExpectedConditions.visibilityOf(heading));
        return heading.getText();
    }

    public String getSubheadingText() {
        wait.until(ExpectedConditions.visibilityOf(subheading));
        return subheading.getText();
    }

    public boolean areAllFeatureItemsVisible() {
        try {
            return featureItem1.isDisplayed()
                    && featureItem2.isDisplayed()
                    && featureItem3.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailLabelText() {
        wait.until(ExpectedConditions.visibilityOf(emailLabel));
        return emailLabel.getText();
    }

    public String getEmailInputPlaceholder() {
        return emailInput.getAttribute("placeholder");
    }

    public String getSubscribeButtonText() {
        wait.until(ExpectedConditions.visibilityOf(subscribeButton));
        return subscribeButton.getText();
    }

    public String getHeroImageSrc() {
        wait.until(ExpectedConditions.visibilityOf(heroImage));
        return heroImage.getAttribute("src");
    }
}