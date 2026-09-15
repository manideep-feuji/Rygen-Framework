package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public final class AssertionUtils {

    public static void assertTrue(
            boolean condition,
            String message) {

        Assert.assertTrue(condition, message);
    }

    public static void assertEquals(
            Object actual,
            Object expected,
            String message) {

        Assert.assertEquals(actual, expected, message);
    }

    public static void assertNotNull(Object actual, String message) {
        Assert.assertNotNull(actual, message);
    }

    public static void assertElementDisplayed(
            WebElement element,
            String message) {

        assertNotNull(element, message + " - element is null");
        assertTrue(element.isDisplayed(), message);
    }

    public static void assertElementEnabled(
            WebElement element,
            String message) {

        assertNotNull(element, message + " - element is null");
        assertTrue(element.isEnabled(), message);
    }

    public static void assertElementExists(
            WebDriver driver,
            By locator,
            String message) {

        assertNotNull(driver, message + " - driver is null");
        assertNotNull(locator, message + " - locator is null");
        assertTrue(driver.findElements(locator).size() > 0, message);
    }

    public static void assertElementNotEmpty(
            WebElement element,
            String message) {

        assertNotNull(element, message + " - element is null");
        String value = element.getText() == null ? "" : element.getText().trim();
        String attributeValue = element.getAttribute("value") == null ? "" : element.getAttribute("value").trim();
        String actualValue = attributeValue.isEmpty() ? value : attributeValue;
        assertTrue(actualValue.length() > 0, message + " - Field is empty");
    }

    public static void assertFieldHasValue(
            WebElement element,
            String expectedValue,
            String message) {

        assertNotNull(element, message + " - element is null");
        String actualValue = element.getDomProperty("value");
        if (actualValue == null || actualValue.isEmpty()) {
            actualValue = element.getAttribute("value");
        }
        if (actualValue == null || actualValue.isEmpty()) {
            actualValue = element.getText();
        }
        actualValue = actualValue == null ? "" : actualValue.trim();
        assertEquals(actualValue, expectedValue.trim(), message);
    }

    public static void assertFieldDigitsEqual(
            WebDriver driver,
            By locator,
            String expectedDigits,
            String message) {

        assertElementExists(driver, locator, message + " - locator not found");
        WebElement element = driver.findElement(locator);
        String actualValue = element.getDomProperty("value");
        if (actualValue == null || actualValue.isEmpty()) {
            actualValue = element.getAttribute("value");
        }
        actualValue = actualValue == null ? "" : actualValue;
        assertEquals(actualValue.replaceAll("\\D", ""), expectedDigits.replaceAll("\\D", ""), message);
    }

    public static void assertFieldHasValue(
            WebDriver driver,
            By locator,
            String expectedValue,
            String message) {

        assertElementExists(driver, locator, message + " - locator not found");
        WebElement element = driver.findElement(locator);
        assertFieldHasValue(element, expectedValue, message);
    }

    public static void assertButtonClickable(
            WebDriver driver,
            By locator,
            String message) {

        assertElementExists(driver, locator, message + " - locator not found");
        WebElement element = driver.findElement(locator);
        assertElementDisplayed(element, message + " - button not displayed");
        assertElementEnabled(element, message + " - button not enabled");
    }

    public static void assertLocatorVisible(
            WebDriver driver,
            By locator,
            String message) {

        assertElementExists(driver, locator, message + " - locator not found");
        WebElement element = driver.findElement(locator);
        assertElementDisplayed(element, message + " - element not visible");
    }

    public static void assertFieldNotEmpty(
            WebDriver driver,
            By locator,
            String message) {

        assertElementExists(driver, locator, message + " - locator not found");
        WebElement element = driver.findElement(locator);
        assertElementNotEmpty(element, message);
    }

    public static void assertUrlContains(
            WebDriver driver,
            String expectedText,
            String message) {

        assertNotNull(driver, message + " - driver is null");
        assertTrue(driver.getCurrentUrl().contains(expectedText), message + " - Current URL: " + driver.getCurrentUrl());
    }

    public static void assertTitleContains(
            WebDriver driver,
            String expectedText,
            String message) {

        assertNotNull(driver, message + " - driver is null");
        assertTrue(driver.getTitle().contains(expectedText), message + " - Current title: " + driver.getTitle());
    }

    private AssertionUtils() {

    }
}