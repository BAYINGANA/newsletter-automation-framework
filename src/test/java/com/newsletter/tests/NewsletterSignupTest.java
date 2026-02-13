package com.newsletter.tests;

import com.newsletter.pages.NewsletterPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Newsletter Feature")
@Feature("Signup")

public class NewsletterSignupTest extends BaseTest {

    @Test
    @Story("User subscribes with valid email")
    @Description("Verify user can subscribe successfully with valid email")
    void shouldSubscribeSuccessfully() {
        NewsletterPage newsletterPage = new NewsletterPage(driver);
        newsletterPage.open("https://bayingana.github.io/NEWSLETTER/");
        newsletterPage.enterEmail("didi@example.com");
        newsletterPage.clickSubmit();

        assertEquals("Thanks for subscribing!", newsletterPage.getSuccessMessage());
    }
}
