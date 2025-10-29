package helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class ScrollingUtils {

    /**
     * Scrolls the web page to the very bottom using JavaScript.
     * @param driver The WebDriver instance.
     */
    public static void scrollToBottom(WebDriver driver) {
        // Cast the driver to JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("Scrolling to the bottom of the page...");

        // Executes JavaScript to scroll vertically to the maximum height of the page
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Optional: Add a small sleep to see the scroll effect
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Scrolls the web page back to the very top (coordinate 0, 0).
     * @param driver The WebDriver instance.
     */
    public static void scrollToTop(WebDriver driver) {
        // Cast the driver to JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        System.out.println("Scrolling to the top of the page...");

        // Executes JavaScript to scroll to coordinates (0, 0)
        js.executeScript("window.scrollTo(0, 0)");

        // Optional: Add a small sleep to see the scroll effect
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // --- MOUSE-SIMULATED SCROLLING METHODS (Smooth and incremental) ---

    /**
     * Scrolls the page down incrementally to simulate a smooth mouse wheel scroll.
     * This is useful for testing lazy loading content.
     * @param driver The WebDriver instance.
     * @param increments The number of times to scroll down (each scroll is 100 pixels).
     */
    public static void scrollDownIncrementally(WebDriver driver, int increments) {
        System.out.println("Scrolling down incrementally (" + increments + " steps)...");
        Actions actions = new Actions(driver);
        while (!isAtBottomOfPage(driver)) {
            // scrollByAmount(0, 100) scrolls down 100 pixels vertically
            actions.scrollByAmount(0, increments * 100).perform();

            // Add a small delay for smoother, more realistic simulation and to allow content to load
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break; // Exit loop if interrupted
            }
        }
    }

    public static boolean isAtBottomOfPage(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // The JavaScript checks if (Visible Viewport Height + Current Scroll Position)
        // is greater than or equal to the total document height.
        return (Boolean) js.executeScript(
                "return (window.innerHeight + window.scrollY) >= document.body.scrollHeight;"
        );
    }

    /**
     * Scrolls the page up incrementally to simulate a smooth mouse wheel scroll.
     * @param driver The WebDriver instance.
     * @param increments The number of times to scroll up (each scroll is 100 pixels).
     */
    public static void scrollUpIncrementally(WebDriver driver, int increments) {
        System.out.println("Scrolling up incrementally (" + increments + " steps)...");
        Actions actions = new Actions(driver);
        for (int i = 0; i < increments; i++) {
            // scrollByAmount(0, -100) scrolls up 100 pixels vertically
            actions.scrollByAmount(0, -100).perform();
            // Add a small delay for smoother, more realistic simulation
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
