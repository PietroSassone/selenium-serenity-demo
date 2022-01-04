package com.example.selenium.serenity.demo.stepdefs;

import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.FILLED_TABLE_ROWS_SELECTOR;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

import org.openqa.selenium.WebDriver;

import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Managed;

public class WebTablesPageWithScreenplayStepDefinitions {

    @Managed
    private WebDriver browser;

    private WebTablesPage webTablesPage;

    @Before
    public void prepareScreenPlayTests() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("{word} opens the webtables page")
    public void actorOpensTheWebTablesPage(final String testUserName) {
        theActorCalled(testUserName).attemptsTo(
            Open.browserOn(webTablesPage)
        );
    }

    @When("{word} clicks on the add new record button")
    public void actorClicksTheAddNewRecordButton(final String testUserName) {
        theActorCalled(testUserName).attemptsTo(
            Click.on(webTablesPage.getAddNewRecordButton())
        );
    }

    //    @When("valid input is prepared for the add table row form")
    //    public void validInputIsPreparedForTheAddTableRowForm() {
    //        inputData.generateInputDataForNewWebTableRecord();
    //    }


    @When("{word} clicks on the submit button")
    public void theSubmitButtonIsClicked(final String testUserName) {
        theActorCalled(testUserName).attemptsTo(
            Click.on(webTablesPage.getSubmitButton())
        );
    }

    @Then("{word} should see {int} rows in the table on the current page")
    public void thereShouldBeGivenNumberOfRowsInTheTable(final String testUserName, final int expectedNumberOfRows) {
        then(theActorCalled(testUserName))
            .attemptsTo(
                Ensure.that(FILLED_TABLE_ROWS_SELECTOR).hasSize(expectedNumberOfRows)
            );
    }

    //
    //    public Question<List<WebElementFacade>> displayed() {
    //        return webTablesPage.getFilledTableRows();
    //    }
}
