package com.example.selenium.serenity.demo.stepdefs;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage.CERTIFICATION_IMAGE;
import static com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage.FOOTER_SELECTOR;
import static com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage.HEADER;
import static com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage.HEADER_SELECTOR;
import static com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage.JOIN_LINK_SELECTOR;
import static com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage.WIDGETS_SELECTOR;
import static net.serenitybdd.screenplay.GivenWhenThen.and;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.ensure.Ensure;

import net.serenitybdd.screenplay.ensure.*;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.springframework.test.context.ContextConfiguration;

import com.example.selenium.serenity.demo.config.UITestSpringConfig;
import com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage;
import com.example.selenium.serenity.demo.steps.NavigateTo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.pages.ListOfWebElementFacades;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.questions.Attribute;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.Visibility;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;

@ContextConfiguration(classes = UITestSpringConfig.class)
public class DemoHomePageStepDefinitions {

    private static final String EXPECTED_FOOTER_TEXT = "© 2013-2020 TOOLSQA.COM | ALL RIGHTS RESERVED.";
    private static final String EXPECTED_HEADER_LINK = "https://demoqa.com/";
    private static final String EXPECTED_JOIN_LINK = "https://www.toolsqa.com/selenium-training/";
    private static final String HEADER_STRING = "header";
    private static final String HREF_ATTRIBUTE = "href";

    private final Actor morpheus = Actor.named("Morpheus");

    private DemoHomePage homePage;

    @Managed
    private WebDriver browser;

    @Steps
    private NavigateTo navigateTo;

    @Before("@HomePage")
    public void prepareScreenPlayTests() {
        morpheus.can(BrowseTheWeb.with(browser));
    }

    @Given("^Morpheus is on the demo QA homepage$")
    public void theDemoQaHomePageIsOpened() {
        morpheus.attemptsTo(
            Open.browserOn(homePage)
        );
    }

    @Then("^he should see the (certification training|header) image$")
    public void theImageShouldBeVisible(final String imageName) {
        final Target imageToTest = imageName.equals(HEADER_STRING) ? HEADER : CERTIFICATION_IMAGE;

        then(morpheus).should(seeThat(Visibility.of(imageToTest), is(true)));
    }

    @Then("^he should see that the (header|join now) link is correct$")
    public void theLinkShouldBeCorrect(final String linkName) {
        final boolean isHeaderLinkToBeChecked = linkName.equals(HEADER_STRING);
        final String expectedLinkValue = isHeaderLinkToBeChecked ? EXPECTED_HEADER_LINK : EXPECTED_JOIN_LINK;
        final String linkLocator = isHeaderLinkToBeChecked ? HEADER_SELECTOR : JOIN_LINK_SELECTOR;

        then(morpheus).should(seeThat(Attribute.of(linkLocator, HREF_ATTRIBUTE), is(expectedLinkValue)));
    }

    @And("^he should see (\\d+) category widgets on the page$")
    public void thereShouldBeGivenNumberOfWidgets(final int expectedNumberOfWidgets) {
        then(morpheus).attemptsTo(
            Ensure.that(WIDGETS_SELECTOR).hasSize(expectedNumberOfWidgets)
        );
    }

    @And("^he should see the following category widgets in order:$")
    public void theWidgetsShouldBe(final DataTable dataTable) {
        then(morpheus).attemptsTo(
            Ensure.that(WIDGETS_SELECTOR).containsExactlyInAnyOrder(dataTable.asList())
        );
    }

    @And("he should see that the footer should is visible and correct")
    public void theFooterShouldBeVisibleAndCorrect() {
        then(morpheus).should(seeThat(Visibility.of(FOOTER_SELECTOR), is(true)));

        and(morpheus).should(seeThat(Text.of(FOOTER_SELECTOR), is(EXPECTED_FOOTER_TEXT)));

    }
}
