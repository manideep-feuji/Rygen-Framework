package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

//    private driver;
//    private wait;

    private WebDriver driver;
    private WebDriverWait wait;

    private By usernameField = By.id("signInName");
    private By continueButton = By.id("continue");

    private By passwordField = By.id("password");
    private By signInButton = By.id("next");

    private By searchBox =
            By.cssSelector("input[placeholder='Search Domains']");

    private By dashboard = By.className("dashboard-container");

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(60)
        );
    }

    public void enterUsername(String username) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        usernameField
                )
        ).sendKeys(username);
    }

    public void clickContinue() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueButton
                )
        ).click();
    }

    public void enterPassword(String password) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        passwordField
                )
        ).sendKeys(password);
    }

    public void clickSignIn() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        signInButton
                )
        ).click();
    }

    public void selectDomain(String domain) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        searchBox
                )
        ).sendKeys(domain);

        By domainOption = By.xpath(
                "//*[normalize-space()='" + domain + "']"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        domainOption
                )
        ).click();

    }

    public boolean isDashboardDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        dashboard
                )
        ).isDisplayed();
    }


}