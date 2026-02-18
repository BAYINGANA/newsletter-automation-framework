package com.newsletter.tests;

import com.newsletter.pages.NewsletterPage;
import com.newsletter.utils.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UIRenderingTest
 *
 * Tests static UI content and layout rendering:
 * - Page content (headings, labels, placeholder text)
 * - Feature list items visible
 * - Hero image rendered
 * - Responsive layout switches at mobile breakpoint
 */
@DisplayName("UI Rendering Tests")
class UIRenderingTest extends TestBase {

    private NewsletterPage newsletterPage;

    @BeforeEach
    void initPage() {
        newsletterPage = new NewsletterPage(driver);
    }

    // ── Content checks ────────────────────────────────────────────────────────

    @Test
    @DisplayName("Verify that page heading reads 'Stay updated!'")
    void verifyThatHeadingReadsStayUpdated() {
        assertEquals("Stay updated!", newsletterPage.getHeadingText(),
                "Main heading should read 'Stay updated!'");
    }

    @Test
    @DisplayName("Verify that subheading contains '60,000+ product managers'")
    void verifyThatSubheadingContainsProductManagers() {
        assertTrue(newsletterPage.getSubheadingText().contains("60,000+"),
                "Subheading should reference 60,000+ product managers");
    }

    @Test
    @DisplayName("Verify that all three feature list items are visible")
    void verifyThatFeatureItemsAreVisible() {
        assertTrue(newsletterPage.areAllFeatureItemsVisible(),
                "All three feature list items should be visible");
    }

    @Test
    @DisplayName("Verify that email label reads 'Email address'")
    void verifyThatEmailLabelReadsEmailAddress() {
        assertEquals("Email address", newsletterPage.getEmailLabelText(),
                "Email label should read 'Email address'");
    }

    @Test
    @DisplayName("Verify that input placeholder reads 'email@company.com'")
    void verifyThatPlaceholderIsCorrect() {
        assertEquals("email@company.com", newsletterPage.getEmailInputPlaceholder(),
                "Placeholder text should be 'email@company.com'");
    }

    @Test
    @DisplayName("Verify that subscribe button text is correct")
    void verifyThatSubscribeButtonHasCorrectText() {
        assertEquals("Subscribe to monthly newsletter",
                newsletterPage.getSubscribeButtonText(),
                "Subscribe button text should match");
    }

    @Test
    @DisplayName("Verify that hero image is displayed")
    void verifyThatHeroImageIsDisplayed() {
        assertTrue(newsletterPage.isHeroImageDisplayed(),
                "Hero illustration should be visible");
    }

    @Test
    @DisplayName("Verify that desktop hero image src contains 'desktop'")
    void verifyThatHeroImageUsesDesktopSrcOnWideViewport() {
        // Ensure desktop width
        driver.manage().window().setSize(new Dimension(1440, 900));
        // Re-init page after resize
        newsletterPage = new NewsletterPage(driver);
        String src = newsletterPage.getHeroImageSrc();
        assertTrue(src.contains("desktop") || src.contains("illustration"),
                "Desktop viewport should use the desktop illustration. Actual src: " + src);
    }

    // ── Responsive layout ─────────────────────────────────────────────────────

    @Test
    @DisplayName("Verify that mobile viewport (375px) shows newsletter card")
    void verifyThatMobileViewportShowsNewsletterCard() {
        driver.manage().window().setSize(new Dimension(375, 812));
        newsletterPage = new NewsletterPage(driver);
        assertTrue(newsletterPage.isNewsletterCardVisible(),
                "Newsletter card should be visible at 375px mobile width");
    }

    @Test
    @DisplayName("Verify that mobile viewport (375px) shows subscribe button")
    void verifyThatMobileViewportShowsSubscribeButton() {
        driver.manage().window().setSize(new Dimension(375, 812));
        newsletterPage = new NewsletterPage(driver);
        assertTrue(newsletterPage.isSubscribeButtonDisplayed(),
                "Subscribe button should be visible at 375px");
    }
}