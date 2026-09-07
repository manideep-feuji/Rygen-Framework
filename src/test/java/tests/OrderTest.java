package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.OrderPage;

public class OrderTest extends BaseTest {

        private static final Logger logger = LogManager.getLogger(OrderTest.class);

        @Test(dataProvider = "orderData", dataProviderClass = utils.TestDataProvider.class)
        public void orderTest(java.util.Map<String, Object> data) throws InterruptedException {

                String username = (String) data.get("username");
                String password = (String) data.get("password");
                String domain = (String) data.get("domain");

                String origin = (String) data.get("origin");
                String locationLine2 = (String) data.get("locationLine2");
                String locationLine3 = (String) data.get("locationLine3");
                String contactName = (String) data.get("contactName");
                String contactPhone = (String) data.get("contactPhone");
                String contactEmail = (String) data.get("contactEmail");
                String contactCompany = (String) data.get("contactCompany");
                String timezone = (String) data.get("timezone");
                String internalNotes = (String) data.get("internalNotes");
                String carrierSpecialInstructions = (String) data.get("carrierSpecialInstructions");

                int pickupDay = data.get("pickupDay") instanceof Number ? ((Number) data.get("pickupDay")).intValue()
                                : Integer.parseInt(data.get("pickupDay").toString());
                int pickupHour = data.get("pickupHour") instanceof Number ? ((Number) data.get("pickupHour")).intValue()
                                : Integer.parseInt(data.get("pickupHour").toString());
                int pickupMinute = data.get("pickupMinute") instanceof Number
                                ? ((Number) data.get("pickupMinute")).intValue()
                                : Integer.parseInt(data.get("pickupMinute").toString());

                String pickupAmPm = (String) data.get("pickupAmPm");
                String destination = (String) data.get("destination");
                String description = (String) data.get("description");

                int handling = data.get("handling") instanceof Number ? ((Number) data.get("handling")).intValue()
                                : Integer.parseInt(data.get("handling").toString());

                String referenceNumber = data.get("referenceNumber").toString();
                String valueOfGoods = data.get("valueOfGoods").toString();
                String billingTerms = (String) data.get("billingTerms");


                logger.info("STEP 1: Starting login");
                DashboardPage dashboardPage = login(username, password, domain);
                logger.info("STEP 2: Login completed");
                logger.info("Current URL: " + driver.getCurrentUrl());
                logger.info("Current Title: " + driver.getTitle());

                logger.info("STEP 3: Checking dashboard");
                Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard is not displayed");
                logger.info("STEP 4: Dashboard displayed");

                logger.info("STEP 5: Clicking Orders");
                OrderPage orderPage = dashboardPage.clickOrders();
                logger.info("STEP 6: Orders page opened");

                logger.info("STEP 7: Clicking New Order");
                orderPage.clickNewOrder();
                logger.info("STEP 8: New Order clicked");

                logger.info("STEP 8.5: Cancelling domain popup");
                orderPage.cancelDomainPopupIfPresent();

                logger.info("STEP 8.6: Clicking New Order again");
                orderPage.clickNewOrder();

                logger.info("STEP 9: Handling domain popup for: " + domain);
                orderPage.handleDomainPopupIfPresent(domain);
                logger.info("STEP 10: Domain handled");

                logger.info("STEP 11: Entering Origin Address and Details");

                orderPage.enterOriginAddress(origin);
                orderPage.enterLocationLine2(locationLine2);
                orderPage.enterLocationLine3(locationLine3);

                orderPage.enterContactName(contactName);
                orderPage.enterContactPhone(contactPhone);
                orderPage.enterContactEmail(contactEmail);
                orderPage.enterContactCompany(contactCompany);
                orderPage.selectTimezone(timezone);
                orderPage.enterInternalNotes(internalNotes);
                orderPage.enterCarrierSpecialInstructions(carrierSpecialInstructions);

                logger.info("STEP 11: Setting Earliest Pickup Date and Time");
                orderPage.setEarliestPickupDateTime(pickupDay, pickupHour, pickupMinute, pickupAmPm);

                logger.info("STEP 12: Entering Destination Address");
                orderPage.enterDestinationAddress(destination);
                logger.info("STEP 13: Destination Address entered");

                orderPage.enterDescription(description);
                orderPage.enterHandling(handling);

                orderPage.selectInbound();
                orderPage.enterValueOfGoods(valueOfGoods);
                orderPage.selectBillingTerms(billingTerms);
                orderPage.enterWeight("1000");

                logger.info("STEP 14: Clicking Create Order");
                orderPage.clickCreateOrder();
                orderPage.waitForOrderEntryPage();

                logger.info("STEP 14.5: Entering Sales Order Number: " + referenceNumber);
                orderPage.enterSalesOrderNumber(referenceNumber);
                logger.info("STEP 14.5: Sales Order Number entered");

                logger.info("STEP 14: Clicking Create Order");
                orderPage.clickCreateOrder();
                orderPage.waitForOrderEntryPage();

                String currentUrl = driver.getCurrentUrl();
                String currentTitle = driver.getTitle();
                logger.info("STEP 15: Current URL after Create Order: " + currentUrl);
                logger.info("STEP 15: Current Page Title: " + currentTitle);

                Assert.assertTrue(currentUrl.contains("/corsair/order/entry"),
                                "Expected navigation to /corsair/order/entry after Create Order, but still on: " + currentUrl);

                logger.info("Order test completed successfully! Order created at: " + currentUrl);

                Thread.sleep(50000);
        }
}
