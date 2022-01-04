@WebTablesPage
Feature: DemoQa Web Tables page test scenarios with Screenplay
  As a test automation engineer
  I want to test the DemoQa Web Tables page
  To demonstrate Serenity usage on tables along with the Screenplay pattern

#@test
  Scenario: Adding an item to the Web Table on the page
    Given Morpheus opens the webtables page
    When Morpheus clicks on the add new record button
    And valid input is prepared for the add table row form
    And all input fields are filled with the prepared valid input
    When Morpheus clicks on the submit button
    Then Morpheus should see 4 rows in the table on the current page
    And 1 new row should be present with the prepared values