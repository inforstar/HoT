package HoT;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.fail;

public class BrokenImagesCheckerTest {

    private WebDriver driver;
    private final String TEST_URL = "https://houseoftest.ch/";

    // NEW EXTENT REPORTS VARIABLES
    private ExtentReports extent;
    private ExtentTest test;

    /**
     * Setup method to initialize the WebDriver AND Extent Reports before each test.
     */
    @BeforeEach
    public void setUp() {
        // --- 1. Extent Reports Initialization (Done once) ---
        if (extent == null) {
            // Specify file path for the report
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("target/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            extent.setSystemInfo("Tester", "Automated QA");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }

        // --- 2. Create Test Entry in Report ---
        test = extent.createTest("BrokenResourceTest - " + getClass().getSimpleName(),
                "Checks for broken links and images on the target page.");
        test.log(Status.INFO, "Starting test setup...");


        // --- 3. WebDriver Initialization ---
        // Selenium 4 automatically handles the driver executable (e.g., Chromedriver)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        test.log(Status.INFO, "WebDriver initialized successfully.");
    }

    /**
     * Teardown method to close the browser and flush the report after each test.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // --- Flush the Report to write all logs to the HTML file ---
        if (extent != null) {
            extent.flush();
        }
    }

    // --- Main Test Method ---

    @Test
    void checkPageResourcesForBrokenElements() {
        test.log(Status.INFO, "Navigating to: " + TEST_URL);
        driver.get(TEST_URL);
        test.log(Status.PASS, "Successfully navigated to target URL: <a href='" + TEST_URL + "'>" + TEST_URL + "</a>");

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

