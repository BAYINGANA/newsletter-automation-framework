package com.newsletter.tests;

import com.newsletter.pages.NewsletterPage;
import com.newsletter.pages.SuccessPage;
import com.newsletter.utils.TestBase;
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
    @Story("User subscribes with valid email")
    @Description("Verify user can subscribe successfully with valid email")
    void shouldSubscribeSuccessfully() {
        NewsletterPage newsletterPage = new NewsletterPage(driver);
        // Page is opened provided by TestBase

        SuccessPage successPage = newsletterPage.submitValidEmail("didi@example.com");

        assertTrue(successPage.isSuccessCardVisible(), "Success card should be visible");
        assertEquals("Thanks for subscribing!", successPage.getSuccessHeadingText(), "Success heading should match");
    }
}
