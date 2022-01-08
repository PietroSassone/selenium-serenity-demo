 # Demo project to demonstrate test automation framework development for UI testing with Serenity BDD and Java
 
 **Test automation UI framework demo project.**
 
 UI test automation in Java 11, demonstrating how to implement a scalable, flexible framework for running UI tests in parallel.
 Supporting different browsers.
 Plus saving useful visual test reports and web HTTP traffic records.
 
 *Note:* The test cases implemented are not extensive. Just a selection of all possible scenarios.
 For a small demo.
 
 This is project was created to demonstrate differences between framework implementation using plain Selenium vs Serenity BDD.
 I have a plain Selenium [project](https://github.com/PietroSassone/selenium-ta-demo) in Java. 
 Plus a Selenide Java [project](https://github.com/PietroSassone/java-selenide-demo). 
 This one contains similar features plus the same tests as the other two.
 
 **1. Technologies used**
 - Serenity BDD 3 for UI tests and reporting
 - Serenity BDD BrowserMob Plugin for capturing web traffic
 - Cucumber 7 for Behaviour Specification and Data Driven Testing
 - Junit 5 with setting the tests to retry failed ones
 - Maven Failsafe plugin to run the test suite with parallelization
 - Logback for logging
 - Spring Core for dependency injection
 - Lombok to eliminate a lot of code
 - Java Faker for test data generation
 - Maven Checkstyle for enforcing coding conventions
 - Jackson for deserializing JSON configuration into a Java class
 
 **2. Design patterns used:**
 - Behaviour Specification
 - Builder (Lombok)
 - Page Object Model
 - Screenplay
 
 **3. Some aspects of UI testing being demonstrated:**
 - Using Selenium Data tables
 - Checking for the visibility of web elements
 - Checking the correctness of links
 - Checking the enabled/disabled state of web elements
 - Adding data to a web table
 - Deleting data from a web table
 - Interacting with pagination
 - Taking and saving screenshots -> build in with Serenity
 - Capturing HTTP web traffic
 - Emulating mobile browsers for testing
 
 **4. about the modules**
 The project contains 2 modules.
 The first is named browsermob-plugin-example. 
     This one has:
     - demonstration of using the Serenity Browsermob plugin
     - this module can't run tests in parallel due to issues with port binding with the Browsermob plugin
     - only supports Chrome out of the box, because of the proxy + chrome options set in the serenity config file
     - page objects with Serenity's WebElementFacade instead of Selenium WebElements
     - Serenity @Steps 
 
 The second is named demo-project. 
     This one has:
     - demonstration of using the Serenity Screenplay with Actors
     - support for parallel runs
     - support for mobile device emulation for Chromium based browsers via custom Serenity driver provider
     - Serenity @Steps 
 
 **5. Reporting and logging**
 - The framework saves reports and logs in the target folder after a test run finishes.
 1. Logs are saved in target/logs inside both modules
 1. Detailed Serenity reports are generated with screenshots in failed scenarios. Saved in both separate module's target/site/serenity.
 1. HTTP Archive is also saved in target/webtraffic for each scenario. Only in the browsermob-plugin-example module.
    
 The reports create a visualized overview of the test results. Can be viewed in a browser.
 In case of failed scenarios a screenshot of the browser is saved.
 
 **6. Pre-requirements for running the tests**
 - Have Maven installed.
 - Have Java installed, at lest version 11.
 - Have the latest version of the browser installed that you want to run the tests with.
 
 **6. Launching the tests**
 Open a terminal in one of the modules and type to run all tests:
     ```
     mvn clean verify
     ```
 Supported arguments in the browsermob-plugin-example module:
 The [parameters](https://serenity-bdd.github.io/theserenitybook/latest/serenity-system-properties.html) supported by Serenity out of the box.
 
 Supported arguments in the demo/project module:
 The [parameters](https://serenity-bdd.github.io/theserenitybook/latest/serenity-system-properties.html) supported by Serenity out of the box.
 Plus:
 | argument name                                                 | supported values             | default value      | description                                                |
 | ------------------------------------------------------------- | ---------------------------- | ------------------ | ---------------------------------------------------------- |
 | rerun.tests.count                                             | any positive integer         | 1                  | sets how many times to try rerunning each failed test case |
 | platformToSet (only for Edge and Chrome, in the demo-project) | desktop, iPhoneX, nexus7     | desktop            | sets the platform/device to be emulated by the webDriver   |
 
 The framework supports the browsers supported by Serenity for testing.
 
 *Notes about the mobile device emulation in the demo-project module:* 
 - For Cromium  browsers: Edge & Chrome, you can use platformToSet as described in the table above. It was implemented by me. Both in headless and standard mode.
 - Currently, the framework only runs tests in other browsers, like Firefox on Desktop platform.
 - At the moment, desktop, iPhone X and Nexus 7 tablet views are added to the framework for demo purposes.
 - New platforms for Edge tests can be added by creating a JSON config file in the "test/resources/browserplatform" directory.
 - The format of a new config added must conform to the same format as the 2 JSON files already present.
 - Settings for devices can be copied from: [Chromium devtools devices](https://chromium.googlesource.com/chromium/src/+/167a7f5e03f8b9bd297d2663ec35affa0edd5076/third_party/WebKit/Source/devtools/front_end/emulated_devices/module.json)
 - After adding a config for a new device, the tests can be immediately run with this platform.
 - When supplying the '-DplatformToSet' param, the value must be the exact same as the JSON file's name.
 Minus the .json extension.
 
 Setting which tests should be run based on cucumber tags can be done via the ```-Dcucumber.filter.tags option```.
 
 *Commands to run tests from a terminal opened in the browsermob-plugin-example module:*
 Example command to run the tests with default browser settings (chrome) for only the Web Table page:
     ```
     mvn clean verify -Dcucumber.filter.tags=@WebTablesPage
     ```
 *Commands to run tests from a terminal opened in the demo-project module:*
 Example command to run the tests with MS Edge Driver in headless mode:
     ```
     mvn clean verify -Dwebdriver.driver=edge -Dheadless=true
     ```
 
 For mobile emulation, we have to set the provided driver as well. See below.
 Example command to run the tests with MS Edge Driver while emulating the Nexus 7 tablet browser:
     ```
     mvn clean verify -DplatformToSet=nexus7 -Dwebdriver.driver=provided -Dprovided.driver=edge 
     ```
    
Example command to run the tests with MS Edge Driver while emulating the iPhoneX tablet browser:
     ```
     mvn clean verify -DplatformToSet=iPhoneX -Dwebdriver.driver=provided 
     ```