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
    @DisplayName("TC-2.1 | Success card is visible after valid submission")
    void successCard_shouldBeVisible() {
        assertTrue(successPage.isSuccessCardVisible(),
                "Success card should be visible after valid submission");
    }

    @Test
    @DisplayName("TC-2.2 | Newsletter card is hidden after valid submission")
    void newsletterCard_shouldBeHidden() {
        assertFalse(newsletterPage.isNewsletterCardVisible(),
                "Newsletter card should be hidden after successful submission");
    }

    // ── Content checks ────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC-2.3 | Success heading reads 'Thanks for subscribing!'")
    void successHeading_shouldBeCorrect() {
        assertEquals("Thanks for subscribing!", successPage.getSuccessHeadingText(),
                "Success heading text should match");
    }

    @Test
    @DisplayName("TC-2.4 | Submitted email is displayed in the success message")
    void confirmedEmail_shouldMatchSubmittedEmail() {
        assertEquals(VALID_EMAIL, successPage.getConfirmedEmail(),
                "Confirmed email should match the submitted email");
    }

    @Test
    @DisplayName("TC-2.5 | Success message contains correct confirmation text")
    void successMessage_shouldContainConfirmationText() {
        String message = successPage.getSuccessMessageText();
        assertTrue(message.contains("A confirmation email has been sent to"),
                "Success message should contain confirmation text");
        assertTrue(message.contains("Please open it and click the button inside"),
                "Success message should contain action instructions");
    }

    @Test
    @DisplayName("TC-2.6 | Check icon is visible on the success card")
    void checkIcon_shouldBeVisible() {
        assertTrue(successPage.isCheckIconVisible(),
                "Check/success icon should be visible");
    }

    @Test
    @DisplayName("TC-2.7 | Dismiss button is visible with correct label")
    void dismissButton_shouldBeVisibleWithCorrectText() {
        assertTrue(successPage.isDismissButtonVisible(),
                "Dismiss button should be visible");
        assertEquals("Dismiss message", successPage.getDismissButtonText(),
                "Dismiss button should read 'Dismiss message'");
    }

    // ── Dismiss flow ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("TC-3.1 | Dismiss returns to newsletter card")
    void dismiss_shouldShowNewsletterCard() {
        NewsletterPage returnedPage = successPage.clickDismiss();
        assertTrue(returnedPage.isNewsletterCardVisible(),
                "Newsletter card should reappear after dismiss");
    }

    @Test
    @DisplayName("TC-3.2 | Email input is cleared after dismiss")
    void dismiss_shouldClearEmailInput() {
        NewsletterPage returnedPage = successPage.clickDismiss();
        String inputValue = driver.findElement(
                org.openqa.selenium.By.id("email")).getAttribute("value");
        assertEquals("", inputValue,
                "Email input should be empty after dismiss");
    }

    @Test
    @DisplayName("TC-3.3 | User can re-subscribe after dismissing")
    void dismiss_shouldAllowResubmission() {
        NewsletterPage returnedPage = successPage.clickDismiss();
        SuccessPage newSuccess = returnedPage.submitValidEmail("another@example.com");
        assertTrue(newSuccess.isSuccessCardVisible(),
                "User should be able to subscribe again after dismissing");
        assertEquals("another@example.com", newSuccess.getConfirmedEmail(),
                "New email should be reflected in success card");
    }
}