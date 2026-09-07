package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(ConfigReader.getProperty("baseUrl"));
    }

    public DashboardPage login(
            String username,
            String password,
            String domain) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.clickContinue();

        loginPage.enterPassword(password);
        loginPage.clickSignIn();

        loginPage.selectDomain(domain);

        return new DashboardPage(driver);
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}