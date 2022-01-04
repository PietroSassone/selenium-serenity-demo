@WebTablesPageWithProxy
Feature: DemoQa Web Tables page test scenarios
  As a test automation engineer
  I want to test the DemoQa Web Tables page
  To demonstrate Serenity usage on tables with Steps classes and BrowserMob plugin

  Background:
    Given the web tables page is opened
    Then there should be 3 rows in the table on the current page

    Scenario: Deleting an item from the Web Table on the page
      When the delete button for item number 1 is clicked
      Then there should be 2 rows in the table on the current page

    Scenario: Deleting all items from the Web Table on the page
      When the delete button for item number 1 is clicked
        And the delete button for item number 1 is clicked
        And the delete button for item number 1 is clicked
      Then there should be 0 rows in the table on the current page
        And the 'No rows found' text should be visible in the table