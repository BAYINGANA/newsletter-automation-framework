package com.newsletter.tests;

import com.newsletter.pages.NewsletterPage;
import com.newsletter.pages.SuccessPage;
import com.newsletter.base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Newsletter Feature")
@Feature("Signup")
public class NewsletterSignupTest extends TestBase {

    @Test
    @Story("Verify that user subscribes with valid email")
    @Description("Verify that user can subscribe successfully with valid email")
    void verifyThatUserSubscribesSuccessfully() {
        NewsletterPage newsletterPage = new NewsletterPage(driver);

        SuccessPage successPage = newsletterPage.submitValidEmail("didi@example.com");

        assertTrue(successPage.isSuccessCardVisible(), "Success card should be visible");
        assertEquals("Thanks for subscribing!", successPage.getSuccessHeadingText(), "Success heading should match");
    }
}
