package com.newsletter.tests;

import com.newsletter.pages.NewsletterPage;
import com.newsletter.pages.SuccessPage;
import com.newsletter.utils.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FormValidationTest
 *
 * Tests all email validation scenarios:
 * - Empty submission
 * - Invalid email formats
 * - Valid email formats
 * - Error state styling
 * - Error message text
 */
@DisplayName("Form Validation Tests")
class FormValidationTest extends TestBase {

    private NewsletterPage newsletterPage;

    @BeforeEach
    void initPage() {
        newsletterPage = new NewsletterPage(driver);
    }

    // ── Empty / blank submission ──────────────────────────────────────────────

    @Test
    @DisplayName("Verify that submitting empty field shows error message")
    void verifyThatSubmitEmptyFieldShowsError() {
        newsletterPage.submitForm();
        assertTrue(newsletterPage.isErrorVisible(),
                "Error message should be visible after empty submission");
    }

    @Test
    @DisplayName("Verify that error message text is 'Valid email required'")
    void verifyThatErrorMessageTextIsCorrect() {
        newsletterPage.submitForm();
        assertEquals("Valid email required", newsletterPage.getErrorText(),
                "Error text should read 'Valid email required'");
    }

    @Test
    @DisplayName("Verify that input has error CSS class on empty submission")
    void verifyThatEmptySubmitInputHasErrorClass() {
        newsletterPage.submitForm();
        assertTrue(newsletterPage.isEmailInputInErrorState(),
                "Email input should have 'error' CSS class");
    }

    // ── Invalid email formats ─────────────────────────────────────────────────

    @ParameterizedTest(name = "Verify that invalid email [{0}] shows error")
    @ValueSource(strings = {
            "plaintext",
            "missing@dot",
            "@nodomain.com",
            "no@.com",
            "spaces in@email.com",
            "double@@domain.com",
            "nodot@domaincom"
    })
    @DisplayName("Verify that invalid email formats show error")
    void verifyThatInvalidEmailsShowError(String invalidEmail) {
        newsletterPage.enterEmail(invalidEmail).submitForm();
        assertTrue(newsletterPage.isErrorVisible(),
                "Error should show for invalid email: " + invalidEmail);
        assertTrue(newsletterPage.isEmailInputInErrorState(),
                "Input should have error class for: " + invalidEmail);
    }

    // ── Valid email formats ───────────────────────────────────────────────────

    @ParameterizedTest(name = "Verify that valid email [{0}] succeeds")
    @ValueSource(strings = {
            "user@example.com",
            "USER@EXAMPLE.COM",
            "test123@domain.org"
    })
    @DisplayName("Verify that valid email formats submit successfully")
    void verifyThatValidEmailsSubmitSuccessfully(String validEmail) {
        SuccessPage successPage = newsletterPage.submitValidEmail(validEmail);
        assertTrue(successPage.isSuccessCardVisible(),
                "Success card should appear for valid email: " + validEmail);
    }

    // ── Error clears after valid submission ───────────────────────────────────

    @Test
    @DisplayName("Verify that error clears after valid email is submitted")
    void verifyThatErrorClearsAfterValidSubmission() {
        // Trigger error first
        newsletterPage.submitForm();
        assertTrue(newsletterPage.isErrorVisible(), "Error should be visible initially");

        // Now submit with valid email
        SuccessPage successPage = newsletterPage.submitValidEmail("user@example.com");
        assertTrue(successPage.isSuccessCardVisible(),
                "Success card should show after valid email");
    }

    // ── Regex limitation test (known edge case) ───────────────────────────────

    @Test
    @DisplayName("Verify that regex rejects valid email with dot in local part (Known Limitation)")
    void verifyThatKnownLimitationDotInLocalPart() {
        // user.name@example.com is RFC-valid but rejected by current regex
        // This test documents the known limitation
        newsletterPage.enterEmail("user.name@example.com").submitForm();
        // Assert based on CURRENT behaviour (error shown = regex limitation present)
        assertTrue(newsletterPage.isErrorVisible(),
                "Known limitation: current regex rejects dots in local part");
    }
}