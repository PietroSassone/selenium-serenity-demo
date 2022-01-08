@HomePage
Feature: DemoQa home page test scenarios with Screenplay
  As a test automation engineer
  I want to test the DemoQa home page
  To demonstrate Serenity usage with Screenplay pattern

  Scenario: Simple test to verify that all UI elements are visible and correct on the home page
    Given Morpheus is on the demo QA homepage
    Then he should see the header image
      And he should see that the header link is correct
      And he should see the certification training image
      And he should see that the join now link is correct
      And he should see 6 category widgets on the page
      And he should see the following category widgets in order:
        | Elements                |
        | Forms                   |
        | Alerts, Frame & Windows |
        | Widgets                 |
        | Interactions            |
        | Book Store Application  |
      And he should see that the footer is visible and correct
