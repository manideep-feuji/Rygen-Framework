package base;

import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.qameta.allure.Allure;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.ScreenshotUtil;

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
    public void tearDown(ITestResult testResult) {
        // if (testResult.getStatus() == ITestResult.FAILURE) {
        //     String screenshotName = testResult.getTestClass().getName()
        //             + "_" + testResult.getName();
        //     ScreenshotUtil.capture(driver, screenshotName);
        // }
        // if (driver != null) {
        //     driver.quit();
        // }
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String screenshotName = testResult.getTestClass().getName() + "_" + testResult.getName();
            ScreenshotUtil.capture(driver, screenshotName);

            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Allure.addAttachment(screenshotName,"image/png", new ByteArrayInputStream(screenshot),".png");
            }
        }

        if (driver != null) {
            driver.quit();
        }
    }
}