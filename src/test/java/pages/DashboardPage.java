package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By dashboardContainer =
            By.cssSelector("div.dashboard-container");

    private By ordersLink =
            By.xpath("//span[contains(text(),'Order')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    public boolean isDashboardDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        dashboardContainer
                )
        ).isDisplayed();
    }

    public OrderPage clickOrders() {
        wait.until(
                ExpectedConditions.elementToBeClickable(
                        ordersLink
                )
        ).click();

        return new OrderPage(driver);
    }
}