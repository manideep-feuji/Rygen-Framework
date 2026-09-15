package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import utils.AssertionUtils;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() throws InterruptedException {

        String username = "3PLAdminUser";
        String password = "3plAdmin@2026";
        String domain = "JCB";

        System.out.println("STEP 1: Starting login");

        DashboardPage dashboardPage =
                login(username, password, domain);

        System.out.println("STEP 2: Login completed");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Current Title: " + driver.getTitle());

        System.out.println("STEP 3: Checking dashboard");

        AssertionUtils.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard is not displayed"
        );

        System.out.println("STEP 4: Dashboard displayed");
    }
}
