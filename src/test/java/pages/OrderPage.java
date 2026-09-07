package pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {

        private static final Logger logger = LogManager.getLogger(OrderPage.class);

        private WebDriver driver;
        private WebDriverWait wait;

        private By newOrderButton = By.xpath(
                "//a[@data-testid='order-list-new-button']" +
                " | //button[.//span[contains(@class,'p-button-label') and contains(text(),'Create Order')]]" +
                " | //button[contains(@aria-label,'Create Order')]" +
                " | //button[contains(@class,'rygen-button-primary') and contains(.,'Create Order')]"
        );

        By ClickOriginAddressBook = By.xpath("//div[@id='stop-1']//button[.//span[normalize-space()='Address Book']]");

        By SelectOriginAddressBook = By.xpath("//div[contains(text(), '15 MCDONALD SACRAMENTO')]");

        By OriginAddressName = By.xpath("//input[@id='stop-1-content-location-name']");
        By DestAddressName = By.xpath("//input[@id='stop-2-content-location-name']");

        By ListItemdes = By.xpath("//input[@id='description-0']");

        By HandlingItem = By.xpath("//input[@placeholder='Handling']");

        private By referenceNumberDropdown = By.xpath(
                        "//*[contains(@id, 'reference-number') and contains(@id, 'type')]//div[contains(@class,'dropdown')] " +
                        "| //*[contains(@id, 'reference-number') and contains(@id, 'type')]/following-sibling::div[contains(@class,'dropdown')] " +
                        "| //*[contains(@id, 'reference-number') and contains(@id, 'type')]");

        private By orderNumberOption = By.xpath("//li[@role='option' and @aria-label='Order Number'] | //li[contains(text(), 'Order Number')] | //span[contains(text(), 'Order Number')]");

        private By referenceNumberType = By.xpath("//*[contains(@id, 'reference-number') and contains(@id, 'type')]");

        private By referenceNumberValue = By
                        .xpath("//input[contains(@id,'reference-number_') and contains(@id,'-value')]");

        private By outboundOption = By.xpath(
                        "//li[@role='option' and @aria-label='Outbound'] | " +
                        "//li[@role='option' and normalize-space(.)='Outbound'] | " +
                        "//li[@role='option']//span[normalize-space(text())='Outbound']");

        // --- New Locators based on DOM ---
        By locationLine2 = By.id("stop-1-content-location-line2");
        By locationLine3 = By.id("stop-1-content-location-line3");
        By locationGroupsInput = By.xpath("//div[@id='stop-1-content-location-location-groups']//input");

        By contactName = By.id("stop-1-content-contact-contact-name");
        By contactPhone = By.id("stop-1-content-contact-contact-phone");
        By contactEmail = By.id("stop-1-content-contact-contact-email");
        By contactCompany = By.id("stop-1-content-contact-contact-company");

        By timezoneDropdown = By.id("stop-1-content-tz-PICKUP");
        
        By internalNotes = By.id("stop-1-content-internal-notes");
        By carrierSpecialInstructions = By.id("stop-1-content-carrier-special-instructions");
        By createOrderBtn = By.xpath("//button[contains(@aria-label, 'Create Order')]");
        By weightInput = By.id("weight-0");
        By salesOrderNumberInput = By.xpath("//label[contains(text(),'Sales Order Number')]/following::input[1]");

        public OrderPage(WebDriver driver) {
                this.driver = driver;
                this.wait = new WebDriverWait(
                                driver,
                                Duration.ofSeconds(15));
        }

        private void safeClick(By locator) {
                WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
                try {
                        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                } catch (org.openqa.selenium.ElementClickInterceptedException
                                | org.openqa.selenium.StaleElementReferenceException e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                }
        }

        public void selectDomain(String domainName) {
                By domainOption = dialogScopedDomainLocator(domainName);
                safeClick(domainOption);
        }

        public void handleDomainPopupIfPresent(String domainName) {
                By dialogMask = By.cssSelector("div.p-dialog-mask");
                try {
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                                .until(ExpectedConditions.visibilityOfElementLocated(dialogMask));

                        safeClick(dialogScopedDomainLocator(domainName));
                        logger.info("Domain popup handled — selected '" + domainName + "'");
                } catch (org.openqa.selenium.TimeoutException e) {
                        // Dialog not present — this is normal, continue
                        logger.info("No domain popup detected, continuing.");
                }
        }

        public void cancelDomainPopupIfPresent() {
                By dialogMask = By.cssSelector("div.p-dialog-mask");
                try {
                        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                                .until(ExpectedConditions.visibilityOfElementLocated(dialogMask));
                        
                        By cancelButton = By.xpath("//div[contains(@class,'p-dialog-mask')]//button[.//span[normalize-space(text())='Cancel']]");
                        safeClick(cancelButton);
                        logger.info("Domain popup handled — clicked 'Cancel'");
                } catch (org.openqa.selenium.TimeoutException e) {
                        logger.info("No domain popup detected to cancel, continuing.");
                }
        }

        private By dialogScopedDomainLocator(String domainName) {
                return By.xpath(
                        "//div[contains(@class,'p-dialog-mask')]//span[@data-pc-section='nodelabel' and normalize-space(text())='" + domainName + "']" +
                        " | //div[contains(@class,'p-dialog-mask')]//span[contains(@class,'p-tree-node-label') and normalize-space(text())='" + domainName + "']" +
                        " | //div[contains(@class,'p-dialog-mask')]//span[normalize-space(text())='" + domainName + "']"
                );
        }

        public void clickNewOrder() {
                safeClick(newOrderButton);
        }

        public void clickAddressBook() {
                safeClick(ClickOriginAddressBook);
        }

        public void selectFromAddressBook(String addressName) {
                By addressLocator = By.xpath("//*[contains(normalize-space(), '" + addressName + "')]");
                safeClick(addressLocator);
        }

        public void enterOriginAddress(String value) {

                WebElement originAddress = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(OriginAddressName));

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", originAddress);

                originAddress.clear();
                originAddress.sendKeys(value);

                try { Thread.sleep(1000); } catch (Exception e) {}

                By suggestion = By.xpath(
                                "//li[@role='option' and @aria-label='" + value + "']");

                safeClick(suggestion);
        }

        public void enterDestinationAddress(String value) {

                WebElement destinationAddress = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(DestAddressName));
                
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", destinationAddress);

                destinationAddress.clear();
                destinationAddress.sendKeys(value);

                try { Thread.sleep(1000); } catch (Exception e) {}

                By suggestion = By.xpath(
                                "//li[@role='option' and @aria-label='" + value + "']");

                safeClick(suggestion);
        }

        public void enterDescription(String value) {

                WebElement description = wait.until(
                                ExpectedConditions.elementToBeClickable(ListItemdes));

                description.clear();
                description.sendKeys(value);

                By descriptionSuggestion = By.xpath(
                                "//li[@role='option' and @aria-label='" + value + "']");

                safeClick(descriptionSuggestion);
        }

        public void enterHandling(int value) {

                WebElement handling = wait.until(
                                ExpectedConditions.elementToBeClickable(HandlingItem));

                handling.clear();
                handling.sendKeys(String.valueOf(value));
        }

        public void selectReferenceNumberType() {
                try {
                    WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(referenceNumberDropdown));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdown);
                    Thread.sleep(500);
                } catch (Exception e) {
                    logger.info("Failed to scroll reference dropdown: " + e.getMessage());
                }
                
                safeClick(referenceNumberDropdown);
                
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
                
                safeClick(orderNumberOption);
        }

        public void enterReferenceNumber(String value) {
                WebElement valueField = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(referenceNumberValue));
                valueField.clear();
                valueField.sendKeys(value);
        }


        public void selectDirection(String direction) {
                By directionDropdown = By.xpath(
                                "//*[@id='direction' or contains(@id,'direction')]//div[contains(@class,'dropdown')] | //*[@id='direction']");
                safeClick(directionDropdown);

                By filterInput = By.xpath(
                                "//input[contains(@class,'p-select-filter') or @role='searchbox' or @placeholder='Search' or @aria-label='Search' or @data-pc-name='pcfilter' or @data-p-name='pcfilter' or contains(@class,'p-inputtext')]");

                WebElement filter = wait.until(ExpectedConditions.visibilityOfElementLocated(filterInput));
                filter.clear();
                filter.sendKeys(direction);

                try { Thread.sleep(700); } catch (Exception e) {}

                By option = By.xpath(
                                "//li[@role='option' and normalize-space(@aria-label)='" + direction + "'] | " +
                                "//li[@role='option' and normalize-space(.)='" + direction + "'] | " +
                                "//li[@role='option' and contains(normalize-space(.), '" + direction + "')] | " +
                                "//li//span[normalize-space(.)='" + direction + "'] | " +
                                "//li//span[contains(normalize-space(text()), '" + direction + "')]");

                List<WebElement> options = driver.findElements(option);
                if (options.isEmpty()) {
                        throw new RuntimeException("Direction suggestion not found for: " + direction);
                }

                WebElement suggestedOption = options.get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", suggestedOption);
                safeClick(By.xpath("//li[@role='option' and normalize-space(.)='" + direction + "'] | " +
                                "//li[@role='option' and normalize-space(@aria-label)='" + direction + "'] | " +
                                "//li//span[normalize-space(.)='" + direction + "'] | " +
                                "//li//span[contains(normalize-space(text()), '" + direction + "')]") );
                logger.info("Direction selected: " + direction);
        }

        public void selectInbound() {
                selectDirection("Inbound");
        }

        public void selectOutbound() {
                selectDirection("Outbound");
        }

        public void enterValueOfGoods(String value) {
                WebElement valueOfGoodsInput = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(By.id("value-of-goods")));
                valueOfGoodsInput.clear();
                valueOfGoodsInput.sendKeys(value);
        }

        public void selectBillingTerms(String term) {
                By billingTermsDropdown = By.xpath("//*[@id='billing-terms' or contains(@id,'billing-terms')]//div[contains(@class,'dropdown')] | //*[@id='billing-terms']");
                safeClick(billingTermsDropdown);
                try { Thread.sleep(800); } catch (Exception e) {}
                By termOption = By.xpath(
                        "//li[@role='option' and @aria-label='" + term + "'] | " +
                        "//li[@role='option' and normalize-space(.)='" + term + "'] | " +
                        "//li[@role='option']//span[normalize-space(text())='" + term + "']");
                safeClick(termOption);
        }

        public void setEarliestPickupDateTime(int day, int hour, int minute, String amPm) {
                By pickupDateTrigger = By.xpath("//input[@id='stop-1-content-earliest-PICKUP']/following-sibling::button");
                openDatePicker(pickupDateTrigger);
                selectDate(day);
                setHour(hour);
                setMinute(minute);
                selectAmPm(amPm);

                try {
                    ((JavascriptExecutor) driver).executeScript("document.body.click();");
                    Thread.sleep(500);
                } catch (Exception e) {
                    logger.info("Failed to close datepicker explicitly: " + e.getMessage());
                }
        }

        // --- New Methods ---
        public void enterLocationLine2(String value) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locationLine2));
                element.clear();
                element.sendKeys(value);
        }

        public void enterLocationLine3(String value) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locationLine3));
                element.clear();
                element.sendKeys(value);
        }

        public void enterLocationGroup(String groupName) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locationGroupsInput));
                element.clear();
                element.sendKeys(groupName);
                By suggestion = By.xpath("//li[@role='option' and contains(@aria-label, '" + groupName + "')]");
                safeClick(suggestion);
        }

        public void enterContactName(String value) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(contactName));
                element.clear();
                element.sendKeys(value);
        }

        public void enterContactPhone(String value) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(contactPhone));
                element.sendKeys(value);
        }

        public void enterContactEmail(String value) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(contactEmail));
                element.clear();
                element.sendKeys(value);
        }

        public void enterContactCompany(String value) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(contactCompany));
                element.clear();
                element.sendKeys(value);
        }

        public void selectTimezone(String tzOption) {
                safeClick(timezoneDropdown);
                By optionLocator = By.xpath("//li[@role='option' and contains(@aria-label, '" + tzOption + "')]");
                safeClick(optionLocator);
        }

        public void enterInternalNotes(String value) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(internalNotes));
                element.clear();
                element.sendKeys(value);
        }

        public void enterCarrierSpecialInstructions(String value) {
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(carrierSpecialInstructions));
                element.clear();
                element.sendKeys(value);
        }

        public void openDatePicker(By triggerLocator) {
                safeClick(triggerLocator);

                wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.cssSelector("div.p-datepicker-panel")));
        }

        public void selectDate(int dayNumber) {
                By dayLocator = By.xpath(
                                "//td[contains(@class,'p-datepicker-day-cell')" +
                                                " and not(contains(@class,'p-datepicker-other-month'))" +
                                                " and @aria-label='" + dayNumber + "']" +
                                                "//span[contains(@class,'p-datepicker-day')]");
                safeClick(dayLocator);
        }

        public void setHour(int targetHour) {
                By hourLabel = By.cssSelector("span[data-pc-section='hour']");
                By incrementBtn = By.cssSelector("button[aria-label='Next Hour']");
                By decrementBtn = By.cssSelector("button[aria-label='Previous Hour']");
                adjustTimePicker(hourLabel, incrementBtn, decrementBtn, targetHour);
        }

        public void setMinute(int targetMinute) {
                By minuteLabel = By.cssSelector("span[data-pc-section='minute']");
                By incrementBtn = By.cssSelector("button[aria-label='Next Minute']");
                By decrementBtn = By.cssSelector("button[aria-label='Previous Minute']");
                adjustTimePicker(minuteLabel, incrementBtn, decrementBtn, targetMinute);
        }

        public void selectAmPm(String amPm) {
                By amPmLabel = By.cssSelector("span[data-pc-section='ampm']");
                By toggleBtn = By.cssSelector("button[aria-label='AM/PM']");

                WebElement label = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(amPmLabel));
                String current = label.getText().trim();
                if (!current.equalsIgnoreCase(amPm)) {
                        safeClick(toggleBtn);
                }
        }

        private void adjustTimePicker(By labelLocator, By incrementBtn, By decrementBtn, int targetValue) {
                int maxAttempts = 60;
                for (int i = 0; i < maxAttempts; i++) {
                        WebElement label = wait.until(
                                        ExpectedConditions.visibilityOfElementLocated(labelLocator));
                        int current = Integer.parseInt(label.getText().trim());
                        if (current == targetValue) break;
                        if (current < targetValue) {
                                safeClick(incrementBtn);
                        } else {
                                safeClick(decrementBtn);
                        }
                }
        }

        public void enterWeight(String weight) {
                WebElement weightField = wait.until(ExpectedConditions.elementToBeClickable(weightInput));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", weightField);
                weightField.clear();
                weightField.sendKeys(weight);
        }

        public void clickCreateOrder() {
                safeClick(createOrderBtn);
        }

        public void waitForOrderEntryPage() {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(30))
                        .until(driver -> driver.getCurrentUrl().contains("/corsair/order/entry"));
                logger.info("Navigated to order entry page: " + driver.getCurrentUrl());
        }

        public void enterSalesOrderNumber(String value) {
                waitForOrderEntryPage();
                try { Thread.sleep(1500); } catch (Exception e) {} // Let the page settle

                WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(salesOrderNumberInput));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", field);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", field);
                field.sendKeys(value);
                logger.info("Sales Order Number entered: " + value);
        }
}