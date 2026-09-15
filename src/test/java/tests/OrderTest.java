package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.OrderPage;
import utils.AssertionUtils;

public class OrderTest extends BaseTest {
        private static final Logger logger = LogManager.getLogger(OrderTest.class);

        @Test(
                dataProvider = "orderData",
                dataProviderClass = utils.TestDataProvider.class
        )
        public void orderTest(Map<String, Object> data) {
                // 1. Test Data
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
                String carrierSpecialInstructions =
                        (String) data.get("carrierSpecialInstructions");

                int pickupDay = data.get("pickupDay") instanceof Number
                        ? ((Number) data.get("pickupDay")).intValue()
                        : Integer.parseInt(data.get("pickupDay").toString());

                int pickupHour = data.get("pickupHour") instanceof Number
                        ? ((Number) data.get("pickupHour")).intValue()
                        : Integer.parseInt(data.get("pickupHour").toString());

                int pickupMinute = data.get("pickupMinute") instanceof Number
                        ? ((Number) data.get("pickupMinute")).intValue()
                        : Integer.parseInt(data.get("pickupMinute").toString());

                String pickupAmPm = (String) data.get("pickupAmPm");

                String destination = (String) data.get("destination");
                String description = (String) data.get("description");

                int handling = data.get("handling") instanceof Number
                        ? ((Number) data.get("handling")).intValue()
                        : Integer.parseInt(data.get("handling").toString());

                String referenceNumber = data.get("referenceNumber").toString();
                String valueOfGoods = data.get("valueOfGoods").toString();
                String billingTerms = (String) data.get("billingTerms");

                logger.info("STEP 1: Starting login");
                DashboardPage dashboardPage = login(username, password, domain);

                logger.info("STEP 2: Login completed");
                logger.info("Current URL: {}", driver.getCurrentUrl());
                logger.info("Current Title: {}", driver.getTitle());

                logger.info("STEP 3: Checking dashboard");
                AssertionUtils.assertTrue(dashboardPage.isDashboardDisplayed(),"Dashboard is not displayed");
                logger.info("STEP 4: Dashboard displayed");

                logger.info("STEP 5: Clicking Orders");
                OrderPage orderPage = dashboardPage.clickOrders();

                logger.info("STEP 6: Orders page opened");

                logger.info("STEP 7: Clicking New Order");
                orderPage.clickNewOrder();
                logger.info("STEP 8: New Order clicked");

                logger.info("STEP 8.5: Cancelling domain popup if present");
                orderPage.cancelDomainPopupIfPresent();
                logger.info("STEP 8.6: Clicking New Order again");
                orderPage.clickNewOrder();

                AssertionUtils.assertTrue(orderPage.isOrderFormReady(),"New Order form is not ready");
                logger.info("STEP 8.7: Order form is ready");
                logger.info("STEP 9: Handling domain popup for: {}", domain);
                orderPage.handleDomainPopupIfPresent(domain);
                logger.info("STEP 10: Domain handled");

                logger.info("STEP 11: Entering Origin Address and Details");
                orderPage.enterOriginAddress(origin);
                AssertionUtils.assertTrue(orderPage.isOriginAddressEntered(origin),"Origin address was not entered correctly");
                logger.info("Origin address entered successfully");

                orderPage.enterLocationLine2(locationLine2);
                orderPage.enterLocationLine3(locationLine3);

                AssertionUtils.assertFieldHasValue(driver, orderPage.getLocationLine2Locator(), locationLine2,
                        "Location line 2 was not entered correctly");
                AssertionUtils.assertFieldHasValue(driver, orderPage.getLocationLine3Locator(), locationLine3,
                        "Location line 3 was not entered correctly");

                orderPage.enterContactName(contactName);
                orderPage.enterContactPhone(contactPhone);
                orderPage.enterContactEmail(contactEmail);
                orderPage.enterContactCompany(contactCompany);

                AssertionUtils.assertFieldHasValue(driver, orderPage.getContactNameLocator(), contactName,
                        "Contact name was not entered correctly");
                AssertionUtils.assertFieldDigitsEqual(driver, orderPage.getContactPhoneLocator(), contactPhone,
                        "Contact phone was not entered correctly");
                AssertionUtils.assertFieldHasValue(driver, orderPage.getContactEmailLocator(), contactEmail,
                        "Contact email was not entered correctly");
                AssertionUtils.assertFieldHasValue(driver, orderPage.getContactCompanyLocator(), contactCompany,
                        "Contact company was not entered correctly");

                orderPage.selectTimezone(timezone);

                AssertionUtils.assertFieldNotEmpty(driver, orderPage.getTimezoneLocator(),
                        "Timezone was not selected");

                orderPage.enterInternalNotes(internalNotes);

                orderPage.enterCarrierSpecialInstructions(
                        carrierSpecialInstructions
                );

                AssertionUtils.assertFieldHasValue(driver, orderPage.getInternalNotesLocator(), internalNotes,
                        "Internal notes were not entered correctly");
                AssertionUtils.assertFieldHasValue(driver, orderPage.getCarrierSpecialInstructionsLocator(),
                        carrierSpecialInstructions, "Carrier instructions were not entered correctly");

                // 10. Earliest Pickup Date & Time

                logger.info(
                        "STEP 11.5: Setting Earliest Pickup Date and Time"
                );

                logger.info(
                        "Pickup Date: {} | Time: {}:{} {}",
                        pickupDay,
                        pickupHour,
                        pickupMinute,
                        pickupAmPm
                );

                orderPage.setEarliestPickupDateTime(
                        pickupDay,
                        pickupHour,
                        pickupMinute,
                        pickupAmPm
                );

                String expectedPickupDateTime = LocalDate.now().withDayOfMonth(pickupDay)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE)
                        + "T"
                        + String.format("%02d:%02d", normalizedHour(pickupHour, pickupAmPm), pickupMinute);
                AssertionUtils.assertFieldHasValue(driver, orderPage.getPickupDateTimeLocator(),
                        expectedPickupDateTime, "Pickup date and time was not selected correctly");

                // 11. Destination Address

                logger.info("STEP 12: Entering Destination Address");
                orderPage.enterDestinationAddress(destination);
                AssertionUtils.assertTrue(orderPage.isDestinationAddressEntered(destination),"Destination address was not entered correctly");
                logger.info("STEP 13: Destination Address entered");

                // 12. Order Details

                logger.info("Entering order description");
                orderPage.enterDescription(description);
                AssertionUtils.assertFieldHasValue(driver, orderPage.getDescriptionLocator(), description,
                        "Description was not entered correctly");
                logger.info("Entering handling quantity");
                orderPage.enterHandling(handling);
                AssertionUtils.assertFieldHasValue(driver, orderPage.getHandlingLocator(), String.valueOf(handling),
                        "Handling quantity was not entered correctly");





 AssertionUtils.assertTrue(false, "Temporary screenshot test");




                

                // 13. Direction
                logger.info("Selecting Inbound direction");
                orderPage.selectInbound();
                AssertionUtils.assertLocatorVisible(driver, orderPage.getDirectionLocator(),
                        "Direction control is not visible after selection");

                // 14. Value of Goods & Billing
                logger.info("Entering value of goods: {}", valueOfGoods);
                orderPage.enterValueOfGoods(valueOfGoods);
                logger.info("Selecting billing terms: {}", billingTerms);
                orderPage.selectBillingTerms(billingTerms);
                AssertionUtils.assertLocatorVisible(driver, orderPage.getBillingTermsLocator(),
                        "Billing terms control is not visible after selection");

                // 15. Weight

                logger.info("Entering weight");
                orderPage.enterWeight("1000");
                
                // 16. Sales Order Number
                logger.info("STEP 14: Entering Sales Order Number: {}",referenceNumber);
                orderPage.enterSalesOrderNumber(referenceNumber);
                
                // 17. Verify Sales Order Number
                logger.info("STEP 14.5: Verifying Sales Order Number: {}", referenceNumber);
                AssertionUtils.assertTrue(orderPage.isSalesOrderNumberEntered(referenceNumber), "Sales Order Number was not entered correctly");
                logger.info("STEP 14.6: Sales Order Number verified successfully: {}", referenceNumber);

                // 18. Create Order
                logger.info("STEP 15: Clicking Create Order");
                AssertionUtils.assertButtonClickable(driver, orderPage.getCreateOrderButtonLocator(), "Create Order button is not clickable");
                orderPage.clickCreateOrder();
                logger.info("Waiting for Order Entry page");
                orderPage.waitForOrderEntryPage();

                // 19. Verify Navigation
                String currentUrl = driver.getCurrentUrl();
                String currentTitle = driver.getTitle();

                logger.info("STEP 16: Current URL after Create Order: {}", currentUrl);
                logger.info("STEP 16: Current Page Title: {}", currentTitle);

                AssertionUtils.assertUrlContains(driver, "/corsair/order/entry",
                        "Expected navigation to /corsair/order/entry after Create Order, but still on: " + currentUrl);
                AssertionUtils.assertTitleContains(driver, "New Order", "Order entry page title is incorrect");

                // 20. Test Completed
                logger.info("STEP 17: Order test completed successfully! " + "Order created at: {}", currentUrl);
                try {
                        Thread.sleep(20000); // Optional: Wait for 20 seconds to observe the result
                } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        logger.warn("Interrupted while waiting to observe the result", e);
                }
        }

        private static int normalizedHour(int hour, String amPm) {
                if ("PM".equalsIgnoreCase(amPm) && hour < 12) {
                        return hour + 12;
                }
                if ("AM".equalsIgnoreCase(amPm) && hour == 12) {
                        return 0;
                }
                return hour;
        }
}




//package tests;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import utils.AssertionUtils;
//import org.testng.annotations.Test;
//
//import base.BaseTest;
//import pages.DashboardPage;
//import pages.OrderPage;
//
//public class OrderTest extends BaseTest {
//
//        private static final Logger logger = LogManager.getLogger(OrderTest.class);
//
//
//
//        @Test(dataProvider = "orderData", dataProviderClass = utils.TestDataProvider.class)
//        public void orderTest(java.util.Map<String, Object> data) throws InterruptedException {
//
//                String username = (String) data.get("username");
//                String password = (String) data.get("password");
//                String domain = (String) data.get("domain");
//
//                String origin = (String) data.get("origin");
//                String locationLine2 = (String) data.get("locationLine2");
//                String locationLine3 = (String) data.get("locationLine3");
//                String contactName = (String) data.get("contactName");
//                String contactPhone = (String) data.get("contactPhone");
//                String contactEmail = (String) data.get("contactEmail");
//                String contactCompany = (String) data.get("contactCompany");
//                String timezone = (String) data.get("timezone");
//                String internalNotes = (String) data.get("internalNotes");
//                String carrierSpecialInstructions = (String) data.get("carrierSpecialInstructions");
//
//                int pickupDay = data.get("pickupDay") instanceof Number ? ((Number) data.get("pickupDay")).intValue()
//                                : Integer.parseInt(data.get("pickupDay").toString());
//                int pickupHour = data.get("pickupHour") instanceof Number ? ((Number) data.get("pickupHour")).intValue()
//                                : Integer.parseInt(data.get("pickupHour").toString());
//                int pickupMinute = data.get("pickupMinute") instanceof Number
//                                ? ((Number) data.get("pickupMinute")).intValue()
//                                : Integer.parseInt(data.get("pickupMinute").toString());
//
//                String pickupAmPm = (String) data.get("pickupAmPm");
//                String destination = (String) data.get("destination");
//                String description = (String) data.get("description");
//
//                int handling = data.get("handling") instanceof Number ? ((Number) data.get("handling")).intValue()
//                                : Integer.parseInt(data.get("handling").toString());
//
//                String referenceNumber = data.get("referenceNumber").toString();
//                String valueOfGoods = data.get("valueOfGoods").toString();
//                String billingTerms = (String) data.get("billingTerms");
//
//
//                logger.info("STEP 1: Starting login");
//                DashboardPage dashboardPage = login(username, password, domain);
//                logger.info("STEP 2: Login completed");
//                logger.info("Current URL: " + driver.getCurrentUrl());
//                logger.info("Current Title: " + driver.getTitle());
//
//                logger.info("STEP 3: Checking dashboard");
//                AssertionUtils.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard is not displayed");
//
//
//                logger.info("STEP 4: Dashboard displayed");
//
//                logger.info("STEP 5: Clicking Orders");
//                OrderPage orderPage = dashboardPage.clickOrders();
//
//                logger.info("STEP 6: Orders page opened");
//
//                logger.info("STEP 7: Clicking New Order");
//                orderPage.clickNewOrder();
//
//                logger.info("STEP 8: New Order clicked");
//
//                logger.info("STEP 8.5: Cancelling domain popup");
//                orderPage.cancelDomainPopupIfPresent();
//
//                logger.info("STEP 8.6: Clicking New Order again");
//                orderPage.clickNewOrder();
//
//                AssertionUtils.assertTrue(
//                        orderPage.isOrderFormReady(),
//                        "New Order form is not ready"
//                );
//
//                logger.info("STEP 9: Handling domain popup for: " + domain);
//                orderPage.handleDomainPopupIfPresent(domain);
//                logger.info("STEP 10: Domain handled");
//
//                logger.info("STEP 11: Entering Origin Address and Details");
//
//                orderPage.enterOriginAddress(origin);
//
//                AssertionUtils.assertTrue(
//                        orderPage.isOriginAddressEntered(origin), "Origin address was not entered correctly");
//
//                orderPage.enterLocationLine2(locationLine2);
//                orderPage.enterLocationLine3(locationLine3);
//
//                orderPage.enterContactName(contactName);
//                orderPage.enterContactPhone(contactPhone);
//                orderPage.enterContactEmail(contactEmail);
//                orderPage.enterContactCompany(contactCompany);
//                orderPage.selectTimezone(timezone);
//                orderPage.enterInternalNotes(internalNotes);
//                orderPage.enterCarrierSpecialInstructions(carrierSpecialInstructions);
//
//                logger.info("STEP 11: Setting Earliest Pickup Date and Time");
//                orderPage.setEarliestPickupDateTime(pickupDay, pickupHour, pickupMinute, pickupAmPm);
//
//                logger.info("STEP 12: Entering Destination Address");
//                orderPage.enterDestinationAddress(destination);
//                AssertionUtils.assertTrue(orderPage.isDestinationAddressEntered(destination), "Destination address was not entered correctly");
//
//                logger.info("STEP 13: Destination Address entered");
//
//                orderPage.enterDescription(description);
//                orderPage.enterHandling(handling);
//
//                orderPage.selectInbound();
//                orderPage.enterValueOfGoods(valueOfGoods);
//                orderPage.selectBillingTerms(billingTerms);
//                orderPage.enterWeight("1000");
//
//                logger.info("STEP 14: Clicking Create Order");
////                orderPage.clickCreateOrder();
////                orderPage.waitForOrderEntryPage();
//
//                logger.info("STEP 14.5: Entering Sales Order Number: " + referenceNumber);
//
//                orderPage.enterSalesOrderNumber(referenceNumber);
//
//                AssertionUtils.assertTrue(
//                        orderPage.isSalesOrderNumberEntered(referenceNumber),
//                        "Sales Order Number was not entered correctly"
//                );
//
//                logger.info("STEP 14.5: Sales Order Number entered");
//
//                logger.info("STEP 14: Clicking Create Order");
//
//                orderPage.clickCreateOrder();
//                orderPage.waitForOrderEntryPage();
//
//                String currentUrl = driver.getCurrentUrl();
//                String currentTitle = driver.getTitle();
//
//                logger.info("STEP 15: Current URL after Create Order: " + currentUrl);
//                logger.info("STEP 15: Current Page Title: " + currentTitle);
//
//                AssertionUtils.assertTrue(
//                        currentUrl.contains("/corsair/order/entry"),
//                        "Expected navigation to /corsair/order/entry after Create Order, but still on: " + currentUrl
//                );
//
//                logger.info("Order test completed successfully! Order created at: " + currentUrl);
//                Thread.sleep(20000);
//        }
//}
