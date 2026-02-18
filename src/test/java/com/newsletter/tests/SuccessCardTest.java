package com.newsletter.tests;

import com.newsletter.pages.NewsletterPage;
import com.newsletter.pages.SuccessPage;
import com.newsletter.utils.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SuccessCardTest
 *
 * Tests the success card state and dismiss flow:
 * - Success card visibility after valid submission
 * - Confirmed email displayed correctly
 * - Dismiss button resets the form
 * - Newsletter re-appears after dismiss
 */
@DisplayName("Success Card Tests")
class SuccessCardTest extends TestBase {

    private static final String VALID_EMAIL = "divi@gmail.com";

    private NewsletterPage newsletterPage;
    private SuccessPage successPage;

    @BeforeEach
    void initAndSubscribe() {
        newsletterPage = new NewsletterPage(driver);
        successPage = newsletterPage.submitValidEmail(VALID_EMAIL);
    }

    // ── Success card visibility ───────────────────────────────────────────────

    @Test
    @DisplayName("Verify that success card is visible after valid submission")
    void verifyThatSuccessCardIsVisible() {
        assertTrue(successPage.isSuccessCardVisible(),
                "Success card should be visible after valid submission");
    }

    @Test
    @DisplayName("Verify that newsletter card is hidden after valid submission")
    void verifyThatNewsletterCardIsHidden() {
        assertFalse(newsletterPage.isNewsletterCardVisible(),
                "Newsletter card should be hidden after successful submission");
    }

    // ── Content checks ────────────────────────────────────────────────────────

    @Test
    @DisplayName("Verify that success heading reads 'Thanks for subscribing!'")
    void verifyThatSuccessHeadingIsCorrect() {
        assertEquals("Thanks for subscribing!", successPage.getSuccessHeadingText(),
                "Success heading text should match");
    }

    @Test
    @DisplayName("Verify that submitted email is displayed in the success message")
    void verifyThatConfirmedEmailMatchesSubmittedEmail() {
        assertEquals(VALID_EMAIL, successPage.getConfirmedEmail(),
                "Confirmed email should match the submitted email");
    }

    @Test
    @DisplayName("Verify that success message contains correct confirmation text")
    void verifyThatSuccessMessageContainsConfirmationText() {
        String message = successPage.getSuccessMessageText();
        assertTrue(message.contains("A confirmation email has been sent to"),
                "Success message should contain confirmation text");
        assertTrue(message.contains("Please open it and click the button inside"),
                "Success message should contain action instructions");
    }

    @Test
    @DisplayName("Verify that check icon is visible on the success card")
    void verifyThatCheckIconIsVisible() {
        assertTrue(successPage.isCheckIconVisible(),
                "Check/success icon should be visible");
    }

    @Test
    @DisplayName("Verify that dismiss button is visible with correct label")
    void verifyThatDismissButtonIsVisibleWithCorrectText() {
        assertTrue(successPage.isDismissButtonVisible(),
                "Dismiss button should be visible");
        assertEquals("Dismiss message", successPage.getDismissButtonText(),
                "Dismiss button should read 'Dismiss message'");
    }

    // ── Dismiss flow ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Verify that dismiss returns to newsletter card")
    void verifyThatDismissShowsNewsletterCard() {
        NewsletterPage returnedPage = successPage.clickDismiss();
        assertTrue(returnedPage.isNewsletterCardVisible(),
                "Newsletter card should reappear after dismiss");
    }

    @Test
    @DisplayName("Verify that email input is cleared after dismiss")
    void verifyThatDismissClearsEmailInput() {
        NewsletterPage returnedPage = successPage.clickDismiss();
        String inputValue = driver.findElement(
                org.openqa.selenium.By.id("email")).getAttribute("value");
        assertEquals("", inputValue,
                "Email input should be empty after dismiss");
    }

    @Test
    @DisplayName("Verify that user can re-subscribe after dismissing")
    void verifyThatDismissAllowsResubmission() {
        NewsletterPage returnedPage = successPage.clickDismiss();
        SuccessPage newSuccess = returnedPage.submitValidEmail("another@example.com");
        assertTrue(newSuccess.isSuccessCardVisible(),
                "User should be able to subscribe again after dismissing");
        assertEquals("another@example.com", newSuccess.getConfirmedEmail(),
                "New email should be reflected in success card");
    }
}