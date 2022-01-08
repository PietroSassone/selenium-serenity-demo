package com.example.selenium.serenity.demo.stepdefs;

import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.ADD_NEW_RECORD_BUTTON_SELECTOR;
import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.FILLED_ROWS;
import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.INPUT_FIELD_SELECTORS_MAP;
import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.NEXT_PAGE_BUTTON;
import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.PAGE_SELECT_FIELD;
import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.PREVIOUS_PAGE_BUTTON;
import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.RESULTS_PER_PAGE_DROPDOWN_SELECTOR;
import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.SUBMIT_BUTTON_SELECTOR;
import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.TOTAL_NUMBER_OF_PAGES;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.openqa.selenium.Keys;

import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTableRow;
import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage;
import com.example.selenium.serenity.demo.steps.InputData;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.actions.SendKeys;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Steps;

public class WebTablesPageStepDefinitions {
    private static final String PREVIOUS = "previous";
    private static final String ENABLED = "enabled";
    private static final String MORPHEUS = "Morpheus";
    private static final String VALUE_ATTRIBUTE = "value";

    private static final Duration ELEMENT_LOAD_WAIT_TIME = Duration.ofSeconds(5);

    @Steps
    private InputData inputData;

    private WebTablesPage webTablesPage;

    private Map<String, String> testDataMap = new HashMap<>();

    @Before("@WebTablesPage")
    public void prepareScreenPlayTests() {
        OnStage.setTheStage(new OnlineCast());
    }

    @After("@WebTablesPage")
    public void tearDownAfterTest() {
        inputData.resetGeneratedInputData();
        testDataMap.clear();
    }

    @Given("{word} opens the webtables page")
    public void actorOpensTheWebTablesPage(final String testUserName) {
        theActorCalled(testUserName).attemptsTo(Open.browserOn(webTablesPage));
    }

    @When("^(.*) clicks on the add new record button$")
    public void actorClicksTheAddNewRecordButton(final String testUserName) {
        theActorCalled(testUserName).attemptsTo(Click.on(ADD_NEW_RECORD_BUTTON_SELECTOR));
    }

    @When("^(?:s)?he prepares valid input for the add table row form$")
    public void actorPreparesValidInputForTheAddTableRowForm() {
        testDataMap = inputData.generateInputDataForNewWebTableRecord();
    }

    @When("^(.*) adds the prepared test data to the table (\\d+) times$")
    public void actorAddsTheTestDataToTheFormGivenNumberOfTimes(final String testUserName, final int numberOfTimesToAddRow) {
        IntStream.range(0, numberOfTimesToAddRow).forEach(
            occurrenceIndex -> {
                actorClicksTheAddNewRecordButton(testUserName);
                actorFillsAllInputFieldsWithValidInput(testUserName);
                actorClicksTheSubmitButton();
            }
        );
    }

    @When("^(.*) sets the display results per page dropdown to (5|10|20|25|50|100)$")
    public void actorSetsTheDisplayResultsPerPageDropdownTo(final String testUserName, final String numberOfRowsToDisplay) {
        theActorCalled(testUserName).attemptsTo(SelectFromOptions.byValue(numberOfRowsToDisplay).from(RESULTS_PER_PAGE_DROPDOWN_SELECTOR));
    }

    @When("^(?:s)?he clicks the (previous|next) page pagination button$")
    public void actorClicksThePaginationButton(final String button) {
        theActorCalled(MORPHEUS).attemptsTo(Click.on(PREVIOUS.equals(button) ? PREVIOUS_PAGE_BUTTON : NEXT_PAGE_BUTTON));
    }

    @When("^(.*) fills all input fields with the prepared valid input$")
    public void actorFillsAllInputFieldsWithValidInput(final String testUserName) {
        testDataMap.forEach((inputFieldName, inputValue)
            -> theActorCalled(testUserName).attemptsTo(Enter.theValue(inputValue).into(INPUT_FIELD_SELECTORS_MAP.get(inputFieldName))));
    }

    @When("^(?:s)?he clicks on the submit button$")
    public void actorClicksTheSubmitButton() {
        theActorCalled(MORPHEUS).attemptsTo(Click.on(SUBMIT_BUTTON_SELECTOR));
    }

    @When("^(?:s)?he sets the current page index to (.*)$")
    public void actorSetsTheCurrentPageIndexIsTo(final String pageIndexToSet) throws InterruptedException {
        theActorCalled(MORPHEUS).attemptsTo(SendKeys.of(pageIndexToSet).into(PAGE_SELECT_FIELD).thenHit(Keys.ENTER));
    }

    @Then("^(?:s)?he should see the page index input field of the pagination containing (\\d)$")
    public void thePageIndexPaginationFieldShouldContain(final int expectedCurrentPageIndex) {
        theActorCalled(MORPHEUS).attemptsTo(
            Ensure.that(PAGE_SELECT_FIELD.waitingForNoMoreThan(ELEMENT_LOAD_WAIT_TIME)).attribute(VALUE_ATTRIBUTE).isEqualTo(String.valueOf(expectedCurrentPageIndex)));
    }

    @Then("^(?:s)?he should see that the (previous|next) page pagination button is (enabled|disabled)$")
    public void thePaginationButtonShouldBeEnabledOrDisabled(final String buttonName, final String expectedState) {
        final Target paginationButton = PREVIOUS.equals(buttonName) ? PREVIOUS_PAGE_BUTTON : NEXT_PAGE_BUTTON;

        if (ENABLED.equals(expectedState)) {
            theActorCalled(MORPHEUS).attemptsTo(Ensure.that(paginationButton).isEnabled());
        } else {
            theActorCalled(MORPHEUS).attemptsTo(Ensure.that(paginationButton).isDisabled());
        }
    }

    @Then("^(?:s)?he should see (\\d) total number of pages in the table$")
    public void theTotalNumberOfPagesInTheTableShouldBe(final int expectedNumberOfPages) {
        theActorCalled(MORPHEUS)
            .attemptsTo(
                Ensure.that(TOTAL_NUMBER_OF_PAGES.waitingForNoMoreThan(ELEMENT_LOAD_WAIT_TIME))
                    .text()
                    .isEqualTo(String.valueOf(expectedNumberOfPages))
            );
    }

    @Then("(.*) should see (\\d+) row(?:s)? in the table on the current page$")
    public void actorShouldSeeGivenNumberOfRowsInTheTable(final String testUserName, final int expectedNumberOfRows) {
        theActorCalled(testUserName)
            .attemptsTo(
                Ensure.that(FILLED_ROWS.resolveAllFor(theActorCalled(testUserName))).hasSize(expectedNumberOfRows)
            );
    }

    @Then("^(.*) should see (\\d) new row(?:s)? present with the prepared values$")
    public void actorShouldSeeNewRowPresent(final String testUserName, final int expectedNumberOfNewRows) {
        final WebTableRow expectedRow = inputData.buildWebTableRowFromPreparedTestInput();

        final List<WebTableRow> matchingWebTableRows = inputData.getRowsOfTheTableMatchingExpectedInput2(expectedRow, theActorCalled(testUserName));

        theActorCalled(testUserName).attemptsTo(Ensure.that(matchingWebTableRows).hasSize(expectedNumberOfNewRows));
    }
}
