package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class WaitUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public WebElement waitForVisibility(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public WebElement waitForClickable(By locator) {
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    public WebElement waitForPresence(By locator) {
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );
    }

    public WebElement waitForVisibility(By locator, int timeoutInSeconds) {
        WebDriverWait customWait =
                new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));

        return customWait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public boolean waitForUrlContains(String urlPart) {
        return wait.until(
                ExpectedConditions.urlContains(urlPart)
        );
    }
}
//public class WaitUtils {
//    private WebDriverWait wait;
//    private WebDriver driver;
//    public WaitUtils(WebDriver driver) {
//        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    }
//
//    public WaitUtils(WebDriver driver) {
//        this.driver = driver;
//        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//    }
//
//    public WebElement waitForVisibility(By locator) {
//        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }
//
//    public WebElement waitForClickable(By locator) {
//        return wait.until(ExpectedConditions.elementToBeClickable(locator));
//    }
//
//    public WebElement waitForPresence(By locator) {
//        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
//    }
//
//    public WebElement waitForVisibility(By locator, int timeoutInSeconds) {
//        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
//
//        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }
//}