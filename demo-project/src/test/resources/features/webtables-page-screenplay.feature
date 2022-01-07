@WebTablesPage @test
Feature: DemoQa Web Tables page test scenarios with Screenplay
  As a test automation engineer
  I want to test the DemoQa Web Tables page
  To demonstrate Serenity usage on tables along with the Screenplay pattern

  Background:
    Given Morpheus opens the webtables page
    Then Morpheus should see 3 rows in the table on the current page

  Scenario: Adding an item to the Web Table on the page
    When Morpheus clicks on the add new record button
      And he prepares valid input for the add table row form
      And Morpheus fills all input fields with the prepared valid input
    When he clicks on the submit button
    Then Morpheus should see 4 rows in the table on the current page
      And he should see 1 new row present with the prepared values

  Scenario: Adding the same item multiple times and checking pagination of the Web Table on the page
    Given Morpheus sets the display results per page dropdown to 5
    Then he should see 1 total number of pages in the table
      And he should see the page index input field of the pagination containing 1
      And he should see that the previous page pagination button is disabled
      And he should see that the next page pagination button is disabled
    When he prepares valid input for the add table row form
      And Morpheus adds the prepared test data to the table 3 times
    Then Morpheus should see 5 rows in the table on the current page
      And Morpheus should see 2 new rows present with the prepared values
      And he should see 2 total number of pages in the table
      And he should see that the previous page pagination button is disabled
      And he should see that the next page pagination button is enabled
    When he clicks the next page pagination button
    Then Morpheus should see 1 row in the table on the current page
      And he should see the page index input field of the pagination containing 2
      And he should see 1 new row present with the prepared values
      And he should see that the previous page pagination button is enabled
      And he should see that the next page pagination button is disabled

  Rule: The current page displayed can be changed by setting the page index input. But it cannot jump to a bigger page index than the total page number.
    Scenario Outline: Modifying the page index of the pagination of the Web Table on the page
      Given Morpheus sets the display results per page dropdown to <rowsPerPage>
      Then he should see 1 total number of pages in the table
        And he should see the page index input field of the pagination containing 1
      When he prepares valid input for the add table row form
        And Morpheus adds the prepared test data to the table <numberOfItemsToAdd> times
        And he should see <expectedTotalPageNumber> total number of pages in the table
      When he sets the current page index to <pageIndexSet>
      Then he should see the page index input field of the pagination containing <expectedPageIndex>
        And Morpheus should see <expectedNumberOfRows> rows in the table on the current page

      Examples:
        | rowsPerPage | numberOfItemsToAdd | expectedTotalPageNumber | pageIndexSet | expectedPageIndex | expectedNumberOfRows |
        | 5           | 3                  | 2                       | 2            | 2                 | 1                    |
        | 5           | 8                  | 3                       | 0            | 3                 | 1                    |
        | 5           | 8                  | 3                       | -1           | 1                 | 5                    |
        | 10          | 1                  | 1                       | 2            | 1                 | 4                    |