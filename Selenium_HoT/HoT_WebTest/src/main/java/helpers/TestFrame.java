package helpers;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class TestFrame {
        public WebDriver driver;
        public String TEST_URL = "https://houseoftest.ch/our-team";

        // NEW EXTENT REPORTS VARIABLES
        private ExtentReports extent;
        public static ExtentTest test;

        /**
         * Setup method to initialize the WebDriver AND Extent Reports before each test.
         */
        @BeforeEach
        public void setUp(TestInfo testInfo) {

                String className = testInfo.getTestClass().get().getSimpleName();

                // Test Method Name (The actual test name): myFirstSeleniumTest
                String methodName = testInfo.getTestMethod().get().getName();

                // Display Name (e.g., from @DisplayName): My First Selenium Test
                String displayName = testInfo.getDisplayName();


                // --- 1. Extent Reports Initialization (Done once) ---
                if (extent == null) {
                        String reportPath = "target/Report" + className + "_" + methodName + "_" + Instant.now().toEpochMilli() + ".html";
                        System.out.println("REPORT: " + reportPath);

                        // Specify file path for the report
                        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
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

}


