package HoT;

import com.aventstack.extentreports.Status;
import helpers.ClosePopups;
import helpers.ScrollingUtils;
import helpers.TestFrame;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class BrokenImagesCheckerTest extends TestFrame {

    @Test
    void checkPageResourcesForBrokenElements() {
        test.log(Status.INFO, "Navigating to: " + TEST_URL);
        driver.get(TEST_URL);
        test.log(Status.PASS, "Successfully navigated to target URL: <a href='" + TEST_URL + "'>" + TEST_URL + "</a>");

        ClosePopups.ClosePopups(driver, Duration.ofSeconds(30));

        ScrollingUtils.scrollDownIncrementally(driver,3);
        ScrollingUtils.scrollToTop(driver);

        // 1. Collect and log all links
        collectAndLogLinks();

        // 2. Check for broken images
        checkBrokenImages();
    }


    // --- Helper Method 1: Link Collection (Logging Updated) ---

    /**
     * Finds all anchor tags (links) and logs their href attribute.
     */
    private void collectAndLogLinks() {
        List<WebElement> links = driver.findElements(By.tagName("a"));

        test.log(Status.INFO, "--- Link Collection Started ---");
        test.log(Status.INFO, "Found " + links.size() + " total links.");

        int count = 1;
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                // Log all links at DEBUG level for detailed report view
                test.log(Status.DEBUG, count++ + ". Link Href: " + href);
            }
        }
        test.log(Status.INFO, "Link collection complete. Total valid hrefs logged.");
    }

    // --- Helper Method 2: Broken Image Check (Logging Updated) ---

    /**
     * Finds all image tags and verifies if they loaded correctly using Javascript's naturalWidth property.
     */
    private void checkBrokenImages() {
        List<WebElement> images = driver.findElements(By.tagName("img"));

        test.log(Status.INFO, "--- Broken Image Check Started ---");
        test.log(Status.INFO, "Found " + images.size() + " total images to check.");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        int brokenImagesCount = 0;

        for (WebElement image : images) {
            String src = image.getAttribute("src");

            if (src == null || src.isEmpty()) {
                test.log(Status.DEBUG, "Skipping image element with empty or null SRC.");
                continue;
            }

            // Execute JavaScript to check if the image has a width greater than 0 (i.e., loaded).
            Boolean isLoaded = (Boolean) js.executeScript(
                    "return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
                    image
            );

            if (isLoaded == null || !isLoaded) {
                // Log failure with error level
                test.log(Status.FAIL, "BROKEN IMAGE DETECTED !!! -> SRC: " + src);
                brokenImagesCount++;
            } else {
                // Log success with debug level
                test.log(Status.DEBUG, "Image Loaded Successfully -> SRC: " + src);
            }
        }

        test.log(Status.INFO, "Summary: " + brokenImagesCount + " broken images found.");

        // Assert and log the final outcome
        if (brokenImagesCount > 0) {
            test.log(Status.FAIL, brokenImagesCount + " broken image(s) found on the page. Test FAILED.");
            fail(brokenImagesCount + " broken image(s) found on the page.");
        } else {
            test.log(Status.PASS, "No broken images detected. Page quality is good. Test PASSED.");
        }
    }
}

