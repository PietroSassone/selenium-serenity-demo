package com.example.selenium.serenity.demo.stepdefs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.springframework.test.context.ContextConfiguration;

import com.example.selenium.serenity.demo.config.UITestSpringConfig;
import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablePagination;
import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTableRow;
import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage;
import com.example.selenium.serenity.demo.steps.ClickOn;
import com.example.selenium.serenity.demo.steps.InputData;
import com.example.selenium.serenity.demo.steps.NavigateTo;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;

@ContextConfiguration(classes = UITestSpringConfig.class)
public class WebTablesStepDefinitions {

    private static final String PREVIOUS = "previous";
    private static final String ENABLED = "enabled";

    private WebTablesPage webTablesPage;

    @Steps
    private NavigateTo navigateTo;

    @Steps
    private ClickOn clickOn;

    @Steps
    private InputData inputData;

    @After("@WebTablesPage")
    public void resetWebTablesTestData() {
        inputData.resetGeneratedInputData();
    }

    @Given("^the web tables page is opened$")
    public void theWebTablesPageIsOpened() {
        navigateTo.theWebTablesPage();
    }

    @When("^the add new record button is clicked$")
    public void theAddNewRecordButtonIsClicked() {
        clickOn.theAddNewRecordButton();
    }

    @When("^the submit button is clicked$")
    public void theSubmitButtonIsClicked() {
        clickOn.theSubmitButton();
    }

    @When("^the prepared test data is added to the table (\\d+) times$")
    public void theAddNewRecordOrSubmitButtonIsClicked(final int numberOfTimesToAddRow) {
        inputData.addTheInputDataToTheWebTableNumberOfTimes(numberOfTimesToAddRow);
    }

    @When("^the (previous|next) page pagination button is clicked$")
    public void thePaginationButtonIsClicked(final String button) {
        if (PREVIOUS.equals(button)) {
            clickOn.thePreviousPageButton();
        } else {
            clickOn.theNextPageButton();
        }
    }

    @When("valid input is prepared for the add table row form")
    public void validInputIsPreparedForTheAddTableRowForm() {
        inputData.generateInputDataForNewWebTableRecord();
    }

    @When("^all(?: other)? input fields are filled with the prepared valid input$")
    public void allInputFieldsAreFilledWithValidInput() {
        inputData.inputTheNewRowsOnTheWebTableForm();
    }

    @When("^the display results per page dropdown is set to (5|10|20|25|50|100) rows$")
    public void theDisplayResultsPerPageDropdownIsSetTo(final String numberOfRowsToDisplay) {
        webTablesPage.getWebTablePagination().getResultsPerPageDropdown().selectByValue(numberOfRowsToDisplay);
    }

    @When("^the current page index is set to (.*)$")
    public void theCurrentPageIndexIsSetTo(final String pageIndexToSet) {
        inputData.setPaginationToIndex(pageIndexToSet);
    }

    @Then("^(\\d) new row(?:s)? should be present with the prepared values$")
    public void aNewRowShouldBePresentWithValues(final int expectedNumberOfNewRows) {
        final WebTableRow expectedRow = inputData.buildWebTableRowFromPreparedTestInput();

        final List<WebTableRow> matchingWebTableRows = inputData.getRowsOfTheTableMatchingExpectedInput(expectedRow);

        assertThat(
            String.format(
                "There should be %s rows matching the submitted input: %s, but the rows were: %s.",
                expectedNumberOfNewRows,
                expectedRow,
                matchingWebTableRows
            ),
            matchingWebTableRows,
            hasSize(expectedNumberOfNewRows)
        );
    }

    @Then("^there should be (\\d+) row(?:s)? in the table on the current page$")
    public void thereShouldBeGivenNumberOfRowsInTheTable(final int expectedNumberOfRows) {
        assertThat(
            String.format("There should be exactly %s rows in the table.", expectedNumberOfRows),
            webTablesPage.getFilledTableRows(),
            hasSize(expectedNumberOfRows)
        );
    }

    @Then("^the total number of pages in the table should be (\\d)$")
    public void theTotalNumberOfPagesInTheTableShouldBe(final int expectedNumberOfPages) {
        webTablesPage.getWebTablePagination().getTotalNumberOfTablePages().shouldContainText(String.valueOf(expectedNumberOfPages));
    }

    @Then("^the page index input field of the pagination should contain (\\d)$")
    public void thePageIndexPaginationFieldShouldContain(final int expectedCurrentPageIndex) {
        assertThat(
            String.format("The current page index should be %s.", expectedCurrentPageIndex),
            webTablesPage.getWebTablePagination().getPageJumpField().getAttribute("value"),
            equalTo(String.valueOf(expectedCurrentPageIndex))
        );
    }

    @Then("^the (previous|next) page pagination button should be (enabled|disabled)$")
    public void thePaginationButtonShouldBeEnabledOrDisabled(final String buttonName, final String expectedState) {
        final WebTablePagination paginationBar = webTablesPage.getWebTablePagination();

        final WebElementFacade paginationButton = PREVIOUS.equals(buttonName)
            ? paginationBar.getPreviousPageButton()
            : paginationBar.getNextPageButton();

        if (ENABLED.equals(expectedState)) {
            paginationButton.shouldBeEnabled();
        } else {
            paginationButton.shouldNotBeEnabled();
        }
    }
}
