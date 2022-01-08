package com.example.serenity.browsermob.demo.stepdefs;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.example.serenity.browsermob.demo.config.UITestSpringConfig;
import com.example.serenity.browsermob.demo.pageobject.webtablespage.WebTablesPage;
import com.example.serenity.browsermob.demo.steps.ClickOn;
import com.example.serenity.browsermob.demo.steps.NavigateTo;
import com.example.serenity.browsermob.demo.steps.ScrollTo;
import com.example.serenity.browsermob.demo.util.WebTrafficRecorder;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;

@ContextConfiguration(classes = UITestSpringConfig.class)
public class WebTablesStepDefinitions {

    private static final String NO_ROWS_FOUND_MESSAGE = "No rows found";

    private WebTablesPage webTablesPage;

    @Autowired
    private WebTrafficRecorder webTrafficRecorder;

    @Steps
    private NavigateTo navigateTo;

    @Steps
    private ClickOn clickOn;

    @Steps
    private ScrollTo scrollTo;

    @After("@WebTablesPageWithProxy")
    public void resetWebTablesTestData(final Scenario scenario) {
        webTrafficRecorder.saveHttpArchiveToFile(scenario);
    }

    @Given("^the web tables page is opened$")
    public void theWebTablesPageIsOpened() {
        navigateTo.theWebTablesPage();
        webTrafficRecorder.setUpProxyFromSerenity();
    }

    @When("^the delete button for item number (\\d+) is clicked$")
    public void theDeleteButtonForTheGivenItemIsClicked(final int numberOfRowToDelete) {
        scrollTo.theDeleteButton(numberOfRowToDelete);
        clickOn.theWebTableDeleteButton(numberOfRowToDelete);
    }

    @Then("^there should be (\\d+) row(?:s)? in the table on the current page$")
    public void thereShouldBeGivenNumberOfRowsInTheTable(final int expectedNumberOfRows) {
        assertThat(
            String.format("There should be exactly %s rows in the table.", expectedNumberOfRows),
            webTablesPage.getFilledTableRows(),
            hasSize(expectedNumberOfRows)
        );
    }

    @Then("^the 'No rows found' text should be visible in the table$")
    public void thereShouldBeGivenNumberOfRowsInTheTable() {
        final WebElementFacade noDataTextElement = webTablesPage.getNoDataTextElement();
        noDataTextElement.shouldBeVisible();
        noDataTextElement.shouldContainText(NO_ROWS_FOUND_MESSAGE);
    }
}
