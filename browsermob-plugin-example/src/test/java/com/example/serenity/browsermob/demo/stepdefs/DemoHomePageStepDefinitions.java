package com.example.serenity.browsermob.demo.stepdefs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.springframework.test.context.ContextConfiguration;

import com.example.serenity.browsermob.demo.config.UITestSpringConfig;
import com.example.serenity.browsermob.demo.pageobject.homepage.DemoHomePage;
import com.example.serenity.browsermob.demo.steps.NavigateTo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;

@ContextConfiguration(classes = UITestSpringConfig.class)
public class DemoHomePageStepDefinitions {

    private static final String EXPECTED_FOOTER_TEXT = "© 2013-2020 TOOLSQA.COM | ALL RIGHTS RESERVED.";
    private static final String EXPECTED_HEADER_LINK = "https://demoqa.coma/";
    private static final String EXPECTED_JOIN_LINK = "https://www.toolsqa.com/selenium-training/";
    private static final String HEADER = "header";

    private DemoHomePage homePage;

    @Steps
    private NavigateTo navigateTo;

    @Given("^the demo QA homepage is opened$")
    public void theDemoQaHomePageIsOpened() {
        navigateTo.theDemoHomePage();
    }

    @Then("^the (certification training|header) image should be visible$")
    public void theImageShouldBeVisible(final String imageName) {
        if (imageName.equals(HEADER)) {
            homePage.getHeader().shouldBeVisible();
        } else {
            homePage.getCertificationTrainingImage().shouldBeVisible();
        }
    }

    @Then("^the (header|join now) link should be correct$")
    public void theLinkShouldBeCorrect(final String linkName) {
        final String actualLink;
        final String expectedLink;

        if (linkName.equals(HEADER)) {
            actualLink = homePage.getHeaderLink();
            expectedLink = EXPECTED_HEADER_LINK;
        } else {
            actualLink = homePage.getJoinLink();
            expectedLink = EXPECTED_JOIN_LINK;
        }
        assertThat(String.format("The %s link should be %s on the homepage", linkName, expectedLink), actualLink, equalTo(expectedLink));
    }

    @And("^there should be (\\d+) category widgets on the page$")
    public void thereShouldBeGivenNumberOfWidgets(final int expectedNumberOfWidgets) {
        assertThat(
            "The number of widgets on the homepage should be: " + expectedNumberOfWidgets,
            homePage.getWidgets().size(),
            is(expectedNumberOfWidgets)
        );
    }

    @And("^the category widgets should be the following in order:$")
    public void theWidgetsShouldBe(final DataTable dataTable) {
        final List<String> expectedWidgetTitles = dataTable.asList();
        final ListOfWebElementFacades widgets = new ListOfWebElementFacades(homePage.getWidgets());

        assertThat("The widgets card titles should be: " + expectedWidgetTitles, widgets.textContents(), equalTo(expectedWidgetTitles));
    }

    @And("^the footer should be visible and correct$")
    public void theFooterShouldBeVisibleAndCorrect() {
        final WebElementFacade footer = homePage.getFooter();
        footer.shouldBeVisible();
        footer.shouldContainText(EXPECTED_FOOTER_TEXT);
    }
}
