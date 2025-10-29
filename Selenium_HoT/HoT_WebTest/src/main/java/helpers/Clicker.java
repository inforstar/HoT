package helpers;

import com.aventstack.extentreports.Status;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static helpers.TestFrame.test;

public class Clicker {

    public static void ExpectAndClick(WebDriver driver, Duration timeout, By element, boolean waitForInvisible) {
        // 1. Wait for the modal to be visible
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(element));

        // 2. The modal is present and visible, now find and click the close button
        System.out.println("Element " + element.toString() + " is present. Attempting to click it.");
        driver.findElement(element).click();
        test.log(Status.PASS, "Clicked: " + element.toString());

        // Optional: Wait for the modal to become invisible (good practice)
        if (waitForInvisible)
            wait.until(ExpectedConditions.invisibilityOf(modal));
    }
}
