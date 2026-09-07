# The Rygen Selenium Automation Framework: A Story

Welcome to the documentation for the Rygen Selenium Framework! This document is written like a story to help you easily understand exactly how this project works, what tools we used, and why we used them. Let's dive in.

---

## 1. The Big Picture: What is this project?

Imagine you have to test a web application every single day. You have to log in, click buttons, fill out long forms, and make sure everything works perfectly. Doing this manually is exhausting, time-consuming, and prone to human error.

This project is our **Automated Robot**. It is designed to automatically open a Google Chrome browser, log into the Rygen application, navigate to the Dashboard, create a brand new Order, fill out a massive address and delivery form, and submit it—all without a human ever touching the keyboard. 

We built this using a concept called the **Page Object Model (POM)**. Instead of writing one giant, messy script, we created a "blueprint" (a Java Class) for every page in the application. If the "Login Page" changes in the future, we only have to update the `LoginPage.java` file, keeping our code clean and easy to maintain.

---

## 2. The Cast of Characters: What did we use?

To bring this robot to life, we needed a few powerful tools. Here is what we used and why:

*   **Java (Version 17):** The core programming language we used to write the instructions for our robot. Java is strongly typed, incredibly reliable, and the industry standard for enterprise automation.
*   **Maven:** Our "Project Manager." It handles all of our project dependencies. Instead of downloading `.jar` files manually from the internet, we just tell Maven (in the `pom.xml` file) what tools we need, and it downloads them automatically.
*   **Selenium WebDriver:** The hands and eyes of our robot. Selenium is the library that actually talks to the Google Chrome browser. It tells the browser to "click here," "type this text," and "wait for this button to appear."
*   **TestNG:** Our "Test Director." It controls the flow of the tests. It decides when to start the browser, when to run the actual test steps, when to pass or fail a test, and when to close the browser.
*   **Jackson (JSON Parser):** Our data translator. When we want to test 50 different orders, we don't want to copy-paste our code 50 times. Jackson reads our test data from a simple JSON file and translates it into Java objects that our tests can use.
*   **Log4j2:** Our "Diary." While the robot is working, Log4j2 writes down everything it does in a neat log file. If something goes wrong, we can read the diary (`test-execution.log`) to find out exactly where the robot failed.

---

## 3. The Flow of the Story: How does it work?

Let's walk through exactly what happens when you press "Run" (or type `mvn clean test`). 

### Chapter 1: The Setup (`BaseTest.java`)
Before the robot can do anything, it needs to get ready. TestNG looks at a file called `BaseTest.java`. This file has a special `@BeforeMethod` instruction.
1. The robot reads the `config.properties` file using our **ConfigReader** utility to find out the secret `baseUrl` of the application.
2. It opens a fresh, maximized Google Chrome window.
3. It navigates to the Rygen login page.

### Chapter 2: Gathering the Data (`TestDataProvider.java`)
The robot is on the login page, but it doesn't know what to type yet. 
1. TestNG calls upon the **TestDataProvider** utility. 
2. This utility sneaks into the `src/test/resources/testdata` folder and reads the `OrderTestData.json` file using Jackson.
3. It packages all this data (usernames, passwords, addresses, dates) and hands it directly to our main test script.

### Chapter 3: The Main Event (`OrderTest.java`)
Now that the robot has the browser open and the data in its hands, the main test begins.
1. **Login:** It uses the `LoginPage` blueprint to type in the username and password, clicks "Sign In," and selects the "JCB" domain.
2. **Dashboard:** It lands on the `DashboardPage` and quickly verifies that the dashboard actually loaded successfully.
3. **Navigating:** It clicks the "Orders" button to navigate to the order creation screen.
4. **Creating the Order:** It clicks "New Order." Sometimes, a tricky pop-up appears asking for the domain again. Our robot is smart enough to handle this pop-up dynamically using a defensive strategy called `handleDomainPopupIfPresent()`.
5. **Filling the Form:** It switches to the `OrderPage` blueprint. It types in the origin address, destination address, and contact details. It even clicks open a complex calendar widget and adjusts the hours and minutes for the pickup time!
6. **Bypassing Obstacles:** Web pages can be tricky. Sometimes, pop-ups or footers physically overlap the buttons we want to click. Our robot uses a special `safeClick()` method. If a normal Selenium click gets blocked by an overlapping element, it falls back to a "JavaScript Click," which bypasses the screen visuals and forces the click to happen behind the scenes.

### Chapter 4: Writing the Diary (`log4j2.xml`)
Throughout this entire process, instead of just printing messy text to the console, the robot uses **Log4j2**. It writes beautiful, time-stamped messages like:
`2026-09-06 13:58:31 [main] INFO  tests.OrderTest - STEP 7: Clicking New Order`
This helps us trace the exact steps the robot took.

### Chapter 5: The Cleanup (`BaseTest.java`)
Once the test finishes—whether it succeeded or failed—TestNG goes back to the `BaseTest.java` file and looks at the `@AfterMethod`. 
1. The robot politely says goodbye.
2. It forcefully shuts down and quits the Google Chrome browser (`driver.quit()`), ensuring we don't leave zombie browsers running in the background and eating up computer memory.

---

## 4. The Project Structure Explained

If you look at the folders in this project, here is exactly what they do:

*   **`src/main/java` & `src/main/resources`:** Usually used for application code. Because this is a purely testing project, we do most of our work in the `test` folder.
*   **`src/test/java/base`:** Contains `BaseTest.java`. This is the parent class that handles starting up and shutting down the browser. All other tests inherit from this.
*   **`src/test/java/pages`:** Contains our Page Object Model blueprints (`LoginPage.java`, `DashboardPage.java`, `OrderPage.java`). This is where all the complex XPath locators and clicking logic live.
*   **`src/test/java/tests`:** Contains the actual TestNG test scripts (`OrderTest.java`). These scripts read like simple English (e.g., `orderPage.clickNewOrder()`) because the complex logic is hidden inside the `pages` folder.
*   **`src/test/java/utils`:** Contains helper tools. `ConfigReader.java` helps read `.properties` files. `TestDataProvider.java` helps read `.json` files.
*   **`src/test/resources`:** Contains non-code configuration files. `config.properties` holds environment variables. `log4j2.xml` tells the logger how to behave.
*   **`src/test/resources/testdata`:** Contains `OrderTestData.json`, which holds the actual data used to fill out the forms.

## Summary
By separating our **Data** (JSON), our **Page Logic** (POM), and our **Test Scripts** (TestNG), we have created a framework that is incredibly easy to read, maintain, and scale for the future!
