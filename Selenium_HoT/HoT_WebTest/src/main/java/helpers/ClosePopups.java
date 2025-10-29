package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class ClosePopups {
        // Locator for the modal container and the close button
        private static final By BUTTON_CLOSE_SIGNUP = By.xpath("//button[@type='button' and @aria-label='Close']"); // Replace with actual modal ID/XPath/CSS
        private static final By BUTTON_ACCEPT_ALL = By.xpath("//a[contains(., 'Accept All Cookies')]"); // Replace with actual close button locator


        public static boolean ClosePopups(WebDriver driver, Duration timeout) {
            try {
                Clicker.ExpectAndClick(driver, timeout, BUTTON_ACCEPT_ALL, true);
                Clicker.ExpectAndClick(driver, timeout, BUTTON_CLOSE_SIGNUP, true);
                return true; // Modal was present and closed
            } catch (TimeoutException e) {
                // If the modal is not visible within the timeout
                System.out.println("Modal not present or not visible within timeout.");
                return false;
            } catch (Exception e) {
                // Catch other exceptions, e.g., if the close button can't be clicked
                System.out.println("Error interacting with modal: " + e.getMessage());
                return false;
            }
        }
    }


