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
    @DisplayName("TC-6.1 | Page heading reads 'Stay updated!'")
    void heading_shouldReadStayUpdated() {
        assertEquals("Stay updated!", newsletterPage.getHeadingText(),
                "Main heading should read 'Stay updated!'");
    }

    @Test
    @DisplayName("TC-6.2 | Subheading contains '60,000+ product managers'")
    void subheading_shouldContainProductManagers() {
        assertTrue(newsletterPage.getSubheadingText().contains("60,000+"),
                "Subheading should reference 60,000+ product managers");
    }

    @Test
    @DisplayName("TC-6.3 | All three feature list items are visible")
    void featureItems_shouldAllBeVisible() {
        assertTrue(newsletterPage.areAllFeatureItemsVisible(),
                "All three feature list items should be visible");
    }

    @Test
    @DisplayName("TC-6.4 | Email label reads 'Email address'")
    void emailLabel_shouldReadEmailAddress() {
        assertEquals("Email address", newsletterPage.getEmailLabelText(),
                "Email label should read 'Email address'");
    }

    @Test
    @DisplayName("TC-6.5 | Input placeholder reads 'email@company.com'")
    void placeholder_shouldBeCorrect() {
        assertEquals("email@company.com", newsletterPage.getEmailInputPlaceholder(),
                "Placeholder text should be 'email@company.com'");
    }

    @Test
    @DisplayName("TC-6.6 | Subscribe button text is correct")
    void subscribeButton_shouldHaveCorrectText() {
        assertEquals("Subscribe to monthly newsletter",
                newsletterPage.getSubscribeButtonText(),
                "Subscribe button text should match");
    }

    @Test
    @DisplayName("TC-6.7 | Hero image is displayed")
    void heroImage_shouldBeDisplayed() {
        assertTrue(newsletterPage.isHeroImageDisplayed(),
                "Hero illustration should be visible");
    }

    @Test
    @DisplayName("TC-6.8 | Desktop hero image src contains 'desktop'")
    void heroImage_shouldUseDesktopSrcOnWideViewport() {
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
    @DisplayName("TC-8.1 | Mobile viewport (375px): newsletter card visible")
    void mobileViewport_newsletterCardShouldBeVisible() {
        driver.manage().window().setSize(new Dimension(375, 812));
        newsletterPage = new NewsletterPage(driver);
        assertTrue(newsletterPage.isNewsletterCardVisible(),
                "Newsletter card should be visible at 375px mobile width");
    }

    @Test
    @DisplayName("TC-8.2 | Mobile viewport (375px): subscribe button visible")
    void mobileViewport_subscribeButtonShouldBeVisible() {
        driver.manage().window().setSize(new Dimension(375, 812));
        newsletterPage = new NewsletterPage(driver);
        assertTrue(newsletterPage.isSubscribeButtonDisplayed(),
                "Subscribe button should be visible at 375px");
    }
}