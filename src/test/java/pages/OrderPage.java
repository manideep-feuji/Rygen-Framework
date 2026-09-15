package pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitUtils;

public class OrderPage {

        private static final Logger logger = LogManager.getLogger(OrderPage.class);

        private WaitUtils waitUtils;
        private WebDriver driver;
//        private WebDriverWait wait;

        //Locators:

        private By newOrderButton = By.xpath(
                "//a[@data-testid='order-list-new-button']" +
                " | //button[.//span[contains(@class,'p-button-label') and contains(text(),'Create Order')]]" +
                " | //button[contains(@aria-label,'Create Order')]" +
                " | //button[contains(@class,'rygen-button-primary') and contains(.,'Create Order')]"
        );
        private By ClickOriginAddressBook = By.xpath("//div[@id='stop-1']//button[.//span[normalize-space()='Address Book']]");
        private By SelectOriginAddressBook = By.xpath("//div[contains(text(), '15 MCDONALD SACRAMENTO')]");
        private By originAddressName = By.xpath("//input[@id='stop-1-content-location-name']");
        private By destinationAddressName = By.xpath("//input[@id='stop-2-content-location-name']");
        private By descriptionInput = By.xpath("//input[@id='description-0']");
        private By handlingInput = By.xpath("//input[@placeholder='Handling']");
        private By referenceNumberDropdown = By.xpath(
                        "//*[contains(@id, 'reference-number') and contains(@id, 'type')]//div[contains(@class,'dropdown')] " +
                        "| //*[contains(@id, 'reference-number') and contains(@id, 'type')]/following-sibling::div[contains(@class,'dropdown')] " +
                        "| //*[contains(@id, 'reference-number') and contains(@id, 'type')]");
        private By orderNumberOption = By.xpath("//li[@role='option' and @aria-label='Order Number'] | //li[contains(text(), 'Order Number')] | //span[contains(text(), 'Order Number')]");
        private By referenceNumberType = By.xpath("//*[contains(@id, 'reference-number') and contains(@id, 'type')]");
        private By referenceNumberValue = By.xpath("//input[contains(@id,'reference-number_') and contains(@id,'-value')]");
        private By outboundOption = By.xpath(
                        "//li[@role='option' and @aria-label='Outbound'] | " +
                        "//li[@role='option' and normalize-space(.)='Outbound'] | " +
                        "//li[@role='option']//span[normalize-space(text())='Outbound']");

        // --- New Locators based on DOM ---
        private By locationLine2 = By.id("stop-1-content-location-line2");
        private By locationLine3 = By.id("stop-1-content-location-line3");
        private By locationGroupsInput = By.xpath("//div[@id='stop-1-content-location-location-groups']//input");
        private By contactName = By.id("stop-1-content-contact-contact-name");
        private By contactPhone = By.id("stop-1-content-contact-contact-phone");
        private By contactEmail = By.id("stop-1-content-contact-contact-email");
        private By contactCompany = By.id("stop-1-content-contact-contact-company");
        private By timezoneDropdown = By.id("stop-1-content-tz-PICKUP");
        private By internalNotes = By.id("stop-1-content-internal-notes");
        private By carrierSpecialInstructions = By.id("stop-1-content-carrier-special-instructions");
        private By createOrderBtn = By.xpath("//button[contains(@aria-label, 'Create Order')]");
        private By weightInput = By.id("weight-0");
//        private By salesOrderNumberInput = By.xpath("//label[contains(text(),'Sales Order Number')]/following::input[1]");
        private By salesOrderNumberInput = By.xpath("//label[contains(normalize-space(.),'Sales Order Number')]/following::input[1]");

        public OrderPage(WebDriver driver) {
                this.driver = driver;
                this.waitUtils = new WaitUtils(driver);
        }

        public By getOriginAddressLocator() {
                return originAddressName;
        }

        public By getDestinationAddressLocator() {
                return destinationAddressName;
        }

        public By getLocationLine2Locator() {
                return locationLine2;
        }

        public By getLocationLine3Locator() {
                return locationLine3;
        }

        public By getContactNameLocator() {
                return contactName;
        }

        public By getContactPhoneLocator() {
                return contactPhone;
        }

        public By getContactEmailLocator() {
                return contactEmail;
        }

        public By getContactCompanyLocator() {
                return contactCompany;
        }

        public By getTimezoneLocator() {
                return timezoneDropdown;
        }

        public By getInternalNotesLocator() {
                return internalNotes;
        }

        public By getCarrierSpecialInstructionsLocator() {
                return carrierSpecialInstructions;
        }

        public By getPickupDateTimeLocator() {
                return By.id("stop-1-content-earliest-PICKUP");
        }

        public By getDescriptionLocator() {
                return descriptionInput;
        }

        public By getHandlingLocator() {
                return handlingInput;
        }

        public By getValueOfGoodsLocator() {
                return By.id("value-of-goods");
        }

        public By getDirectionLocator() {
                return By.xpath("//*[contains(@class,'p-select-label') or contains(@class,'p-dropdown-label')][normalize-space()='Inbound']");
        }

        public By getBillingTermsLocator() {
                return By.xpath("//*[contains(@class,'p-select-label') or contains(@class,'p-dropdown-label')][normalize-space()='Prepaid']");
        }

        public By getWeightLocator() {
                return weightInput;
        }

        public By getSalesOrderNumberLocator() {
                return salesOrderNumberInput;
        }

        public By getCreateOrderButtonLocator() {
                return createOrderBtn;
        }

        public boolean isOrderFormReady() {
                return waitUtils.waitForVisibility(originAddressName).isDisplayed();
        }

        public boolean isOriginAddressEntered(String expectedValue) {
                String actualValue = waitUtils.waitForVisibility(originAddressName).getAttribute("value");
                return expectedValue.equals(actualValue);
        }

        public boolean isDestinationAddressEntered(String expectedValue) {
                String actualValue = waitUtils.waitForVisibility(destinationAddressName).getAttribute("value");
                return expectedValue.equals(actualValue);
        }

        public boolean isSalesOrderNumberEntered(String expectedValue) {
                WebElement field = waitUtils.waitForVisibility(salesOrderNumberInput);
                String actualValue = field.getDomProperty("value");
                String normalizedExpected = expectedValue.replace(",", "").trim();
                String normalizedActual = actualValue.replace(",", "").trim();
                logger.info("Expected Sales Order Number: [{}]", expectedValue);
                logger.info("Actual Sales Order Number: [{}]", actualValue);
                logger.info("Normalized Expected: [{}]", normalizedExpected);
                logger.info("Normalized Actual: [{}]", normalizedActual);
                return normalizedExpected.equals(normalizedActual);
        }

        private void safeClick(By locator) {
                WebElement element = waitUtils.waitForPresence(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrol" +
                        "lIntoView({block: 'center'});", element);
                try {
                        waitUtils.waitForClickable(locator).click();
                } catch (org.openqa.selenium.ElementClickInterceptedException | org.openqa.selenium.StaleElementReferenceException e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                }
        }

        private void enterText(By locator, String value) {
                WebElement element = waitUtils.waitForVisibility(locator);
                element.clear();
                element.sendKeys(value);
        }

        private void enterTextAndSelectSuggestion(
                By inputLocator,
                String value,
                By suggestionLocator) {
                enterText(inputLocator, value);
                safeClick(suggestionLocator);
        }

        private By dialogScopedDomainLocator(String domainName) {
                return By.xpath("//div[contains(@class,'p-dialog-mask')]" +
                                "//span[@data-pc-section='nodelabel' " +
                                "and normalize-space(text())='" + domainName + "']" +

                                " | //div[contains(@class,'p-dialog-mask')]" +
                                "//span[contains(@class,'p-tree-node-label') " +
                                "and normalize-space(text())='" + domainName + "']" +

                                " | //div[contains(@class,'p-dialog-mask')]" +
                                "//span[normalize-space(text())='" + domainName + "']"
                );
        }

        public void selectDomain(String domainName) {
                By domainOption = dialogScopedDomainLocator(domainName);
                safeClick(domainOption);
        }

        public void handleDomainPopupIfPresent(String domainName) {
                By dialogMask = By.cssSelector("div.p-dialog-mask");
                try {
                        waitUtils.waitForVisibility(dialogMask, 5);
                        safeClick(dialogScopedDomainLocator(domainName));
                        logger.info("Domain popup handled — selected '" + domainName + "'");
                } catch (org.openqa.selenium.TimeoutException e) {
                        logger.info("No domain popup detected, continuing.");
                }
        }

        public void cancelDomainPopupIfPresent() {
                By dialogMask = By.cssSelector("div.p-dialog-mask");
                try {
                        waitUtils.waitForVisibility(dialogMask, 5);
                        By cancelButton = By.xpath(
                                "//div[contains(@class,'p-dialog-mask')]//button[.//span[normalize-space(text())='Cancel']]"
                        );
                        safeClick(cancelButton);
                        logger.info("Domain popup handled — clicked 'Cancel'");
                } catch (org.openqa.selenium.TimeoutException e) {
                        logger.info("No domain popup detected to cancel, continuing.");
                }
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

                WebElement originAddress = waitUtils.waitForVisibility(originAddressName);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", originAddress);
                originAddress.clear();
                originAddress.sendKeys(value);
                By suggestion = By.xpath(
                        "//li[@role='option' and @aria-label='" + value + "']");
                waitUtils.waitForVisibility(suggestion);
                safeClick(suggestion);
        }

        public void enterDestinationAddress(String value) {
                WebElement destinationAddress = waitUtils.waitForVisibility(destinationAddressName);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", destinationAddress);
                destinationAddress.clear();
                destinationAddress.sendKeys(value);
                By suggestion = By.xpath("//li[@role='option' and @aria-label='" + value + "']");
                waitUtils.waitForVisibility(suggestion);
                safeClick(suggestion);
        }

        public void enterDescription(String value) {
                By descriptionSuggestion = By.xpath("//li[@role='option' and @aria-label='" + value + "']");
                enterTextAndSelectSuggestion(descriptionInput, value, descriptionSuggestion);
        }

        public void enterHandling(int value) {
                enterText(handlingInput, String.valueOf(value));
        }

        public void selectReferenceNumberType() {
                safeClick(referenceNumberDropdown);
                waitUtils.waitForVisibility(referenceNumberType);
                safeClick(referenceNumberType);
                waitUtils.waitForVisibility(orderNumberOption);
                safeClick(orderNumberOption);
        }

        public void enterReferenceNumber(String value) {
                enterText(referenceNumberValue, value);
        }

        public void selectDirection(String direction) {
                By directionDropdown = By.xpath("//*[@id='direction' or contains(@id,'direction')]//div[contains(@class,'dropdown')] | //*[@id='direction']");
                safeClick(directionDropdown);
                By filterInput = By.xpath("//input[contains(@class,'p-select-filter') or @role='searchbox' or @placeholder='Search' or @aria-label='Search' or @data-pc-name='pcfilter' or @data-p-name='pcfilter' or contains(@class,'p-inputtext')]");
                WebElement filter = waitUtils.waitForVisibility(filterInput);
                filter.clear();
                filter.sendKeys(direction);
                By option = By.xpath("//li[@role='option' and normalize-space(@aria-label)='" + direction + "'] | " +
                                "//li[@role='option' and normalize-space(.)='" + direction + "'] | " +
                                "//li[@role='option' and contains(normalize-space(.), '" + direction + "')] | " +
                                "//li//span[normalize-space(.)='" + direction + "'] | " +
                                "//li//span[contains(normalize-space(text()), '" + direction + "')]");
                waitUtils.waitForVisibility(option);
                List<WebElement> options = driver.findElements(option);
                if (options.isEmpty()) {
                        throw new RuntimeException("Direction suggestion not found for: " + direction);
                }
                WebElement suggestedOption = options.get(0);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", suggestedOption);
                safeClick(option);
                logger.info("Direction selected: " + direction);
        }

        public void selectInbound() {
                selectDirection("Inbound");
        }

        public void selectOutbound() {
                selectDirection("Outbound");
        }

        public void enterValueOfGoods(String value) {
                enterText(By.id("value-of-goods"), value);
        }

        public void selectBillingTerms(String term) {
                By billingTermsDropdown = By.xpath("//*[@id='billing-terms' or contains(@id,'billing-terms')]//div[contains(@class,'dropdown')] | //*[@id='billing-terms']");
                safeClick(billingTermsDropdown);
                By termOption = By.xpath("//li[@role='option' and @aria-label='" + term + "'] | " +
                                "//li[@role='option' and normalize-space(.)='" + term + "'] | " +
                                "//li[@role='option']//span[normalize-space(text())='" + term + "']");
                waitUtils.waitForVisibility(termOption);
                safeClick(termOption);
        }

        public void setEarliestPickupDateTime(int day, int hour, int minute, String amPm) {
                By pickupInput = By.id("stop-1-content-earliest-PICKUP");
                WebElement pickupField = waitUtils.waitForVisibility(pickupInput);

                int resolvedDay = day > 0 ? day : java.time.LocalDate.now().getDayOfMonth();
                openDatePicker(pickupInput);
                selectDate(resolvedDay);

                String normalizedHour = normalizeHour(hour, amPm);
                String desiredValue = buildDateTimeValue(resolvedDay, normalizedHour, minute);

                logger.info("Setting pickup datetime directly to: {}", desiredValue);

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].setAttribute('value', arguments[1]); " +
                        "arguments[0].value = arguments[1]; " +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        pickupField,
                        desiredValue
                );

                try {
                        ((JavascriptExecutor) driver).executeScript("document.body.click();");
                } catch (Exception e) {
                        logger.info("Failed to close datepicker explicitly: " + e.getMessage());
                }
        }

        private String normalizeHour(int hour, String amPm) {
                int normalizedHour = hour;
                String upperAmPm = amPm == null ? "" : amPm.trim().toUpperCase();

                if ("PM".equals(upperAmPm) && hour < 12) {
                        normalizedHour = hour + 12;
                } else if ("AM".equals(upperAmPm) && hour == 12) {
                        normalizedHour = 0;
                }

                return String.format("%02d", normalizedHour);
        }

        private String buildDateTimeValue(int day, String hour, int minute) {
                String month = String.format("%02d", java.time.LocalDate.now().getMonthValue());
                String year = String.valueOf(java.time.LocalDate.now().getYear());
                String dayString = String.format("%02d", day);
                String minuteString = String.format("%02d", minute);
                return year + "-" + month + "-" + dayString + "T" + hour + ":" + minuteString;
        }

        // --- New Methods ---
        public void enterLocationLine2(String value) {
                enterText(locationLine2, value);
        }

        public void enterLocationLine3(String value) {
                enterText(locationLine3, value);
        }

        public void enterLocationGroup(String groupName) {
                By suggestion = By.xpath("//li[@role='option' and contains(@aria-label, '" + groupName + "')]");
                enterTextAndSelectSuggestion(
                        locationGroupsInput,
                        groupName,
                        suggestion
                );
        }

        public void enterContactName(String value) {
                enterText(contactName, value);
        }

        public void enterContactPhone(String value) {
                WebElement phoneField = waitUtils.waitForVisibility(contactPhone);
                phoneField.clear();
                phoneField.sendKeys(value);
                if (!value.equals(phoneField.getDomProperty("value"))) {
                        ((JavascriptExecutor) driver).executeScript(
                                        "arguments[0].value = arguments[1]; " +
                                        "arguments[0].setAttribute('value', arguments[1]); " +
                                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                                        phoneField,
                                        value
                        );
                }
        }

        public void enterContactEmail(String value) {
                enterText(contactEmail, value);
        }

        public void enterContactCompany(String value) {
                enterText(contactCompany, value);
        }

        public void selectTimezone(String tzOption) {
                WebElement timezoneField = waitUtils.waitForClickable(timezoneDropdown);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", timezoneField);

                String normalizedTz = tzOption == null ? "" : tzOption.trim();
                boolean selected = false;

                try {
                        timezoneField.click();
                        List<By> optionLocators = List.of(
                                        By.xpath("//li[@role='option' and (contains(normalize-space(@aria-label), '" + normalizedTz + "') or contains(normalize-space(.), '" + normalizedTz + "'))]"),
                                        By.xpath("//div[@role='option' and (contains(normalize-space(@aria-label), '" + normalizedTz + "') or contains(normalize-space(.), '" + normalizedTz + "'))]"),
                                        By.xpath("//*[contains(normalize-space(@aria-label), '" + normalizedTz + "') or contains(normalize-space(.), '" + normalizedTz + "')] [self::li or self::div or self::span]"),
                                        By.xpath("//*[contains(normalize-space(text()), '" + normalizedTz + "') and (self::li or self::div or self::span)]")
                        );

                        for (By optionLocator : optionLocators) {
                                List<WebElement> options = driver.findElements(optionLocator);
                                if (!options.isEmpty()) {
                                        WebElement option = options.get(0);
                                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                                        try {
                                                option.click();
                                                selected = true;
                                                break;
                                        } catch (Exception ignored) {
                                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", option);
                                                selected = true;
                                                break;
                                        }
                                }
                        }
                } catch (Exception ignored) {
                        logger.warn("Timezone dropdown click failed for {}: {}", normalizedTz, ignored.getMessage());
                }

                if (!selected) {
                        logger.warn("Timezone option not found by UI selector: {}. Falling back to direct value assignment.", normalizedTz);
                        ((JavascriptExecutor) driver).executeScript(
                                        "arguments[0].value = arguments[1]; " +
                                        "arguments[0].setAttribute('value', arguments[1]); " +
                                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true })); " +
                                        "try { var overlay = document.querySelector('.p-overlaypanel, .p-dropdown-panel, .p-datepicker-panel'); if (overlay) { overlay.style.display = 'none'; overlay.remove(); } } catch (e) {} " +
                                        "try { document.body.dispatchEvent(new MouseEvent('click', { bubbles: true })); } catch (e) {} " +
                                        "try { document.activeElement.blur(); } catch (e) {}",
                                        timezoneField,
                                        normalizedTz
                        );
                } else {
                        ((JavascriptExecutor) driver).executeScript(
                                        "try { var overlay = document.querySelector('.p-overlaypanel, .p-dropdown-panel, .p-datepicker-panel'); if (overlay) { overlay.style.display = 'none'; overlay.remove(); } } catch (e) {} " +
                                        "try { document.body.dispatchEvent(new MouseEvent('click', { bubbles: true })); } catch (e) {}",
                                        timezoneField
                        );
                }
        }

        public void enterInternalNotes(String value) {
                enterText(internalNotes, value);
        }

        public void enterCarrierSpecialInstructions(String value) {
                enterText(carrierSpecialInstructions, value);
        }

        public void openDatePicker(By triggerLocator) {
                WebElement dateTrigger = waitUtils.waitForClickable(triggerLocator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", dateTrigger);
                try {
                        dateTrigger.click();
                } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dateTrigger);
                }
                waitUtils.waitForVisibility(By.cssSelector("div.p-datepicker-panel"));
        }

        public void selectDate(int dayNumber) {
                By dayLocator = By.xpath("//td[contains(@class,'p-datepicker-day-cell')" +
                                                " and not(contains(@class,'p-datepicker-other-month'))" +
                                                " and @aria-label='" + dayNumber + "']" +
                                                "//span[contains(@class,'p-datepicker-day')]");
                List<WebElement> dayCells = driver.findElements(dayLocator);
                if (dayCells.isEmpty()) {
                        By fallbackDayLocator = By.xpath("//td[contains(@class,'p-datepicker-day-cell')" +
                                                        " and not(contains(@class,'p-datepicker-other-month'))" +
                                                        " and .//span[normalize-space(.)='" + dayNumber + "']]" );
                        dayCells = driver.findElements(fallbackDayLocator);
                }
                if (dayCells.isEmpty()) {
                        throw new RuntimeException("Date picker day not found for day: " + dayNumber);
                }
                safeClick(dayLocator);
        }

        public void setHour(int targetHour) {
                logger.info("setHour() is deprecated for this page; using direct datetime assignment instead.");
        }

        public void setMinute(int targetMinute) {
                logger.info("setMinute() is deprecated for this page; using direct datetime assignment instead.");
        }

//        public void selectAmPm(String amPm) {
//                By amPmLabel = By.cssSelector("span[data-pc-section='ampm']");
//                By toggleBtn = By.cssSelector("button[aria-label='AM/PM']");
//
////                WebElement label = wait.until(
////                                ExpectedConditions.visibilityOfElementLocated(amPmLabel));
//                WebElement label = waitUtils.waitForVisibility(amPmLabel);
//                String current = label.getText().trim();
//                if (!current.equalsIgnoreCase(amPm)) {
//                        safeClick(toggleBtn);
//                }
//        }

public void selectAmPm(String amPm) {
        By amPmButton = By.cssSelector(".p-datepicker-ampm-picker button[aria-label='" + amPm.toLowerCase() + "']");
        safeClick(amPmButton);
}

        private void adjustTimePicker(By labelLocator, By incrementBtn, By decrementBtn, int targetValue) {
                logger.info("adjustTimePicker() is deprecated for this page; using direct datetime assignment instead.");
        }

        public void enterWeight(String weight) {
                WebElement weightField = waitUtils.waitForClickable(weightInput);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", weightField);
                enterText(weightInput, weight);
        }

        public void clickCreateOrder() {
                WebElement createButton = waitUtils.waitForClickable(createOrderBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", createButton);
                try {
                        createButton.click();
                } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createButton);
                }
        }

        public void waitForOrderEntryPage() {
                waitUtils.waitForUrlContains("/order/entry");
        }

        public void enterSalesOrderNumber(String value) {
                WebElement field = waitUtils.waitForVisibility(salesOrderNumberInput);
                ((JavascriptExecutor) driver).executeScript(
                                "arguments[0].scrollIntoView({block: 'center'}); " +
                                "arguments[0].focus(); " +
                                "arguments[0].value = ''; " +
                                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                                field);
                try {
                        field.sendKeys(value);
                } catch (Exception e) {
                        ((JavascriptExecutor) driver).executeScript(
                                        "arguments[0].value = arguments[1]; " +
                                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));",
                                        field,
                                        value
                        );
                }
                String actualValue = field.getDomProperty("value");
                logger.info("Sales Order Number entered. Expected: [{}], Actual: [{}]", value, actualValue);
        }
}