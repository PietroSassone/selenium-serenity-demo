package com.example.selenium.serenity.demo.pageobject.webtablespage;

import java.util.Map;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://demoqa.com/webtables")
public class WebTablesPage extends PageObject {
    public static final String ADD_NEW_RECORD_BUTTON_SELECTOR = "#addNewRecordButton";
    public static final String SUBMIT_BUTTON_SELECTOR = "#submit";
    public static final Target FILLED_ROWS = Target.the("filled table rows").locatedBy("//div[@class='rt-tr-group']/div[div[1][text()]]");
    public static final String RESULTS_PER_PAGE_DROPDOWN_SELECTOR = ".-pageSizeOptions select";
    public static final Target TOTAL_NUMBER_OF_PAGES = Target.the("total number of pages").locatedBy(".-totalPages");
    public static final Target PAGE_SELECT_FIELD = Target.the("page select field").locatedBy(".-pageJump input");
    public static final Target PREVIOUS_PAGE_BUTTON = Target.the("previous page pagination button").locatedBy(".-previous button");
    public static final Target NEXT_PAGE_BUTTON = Target.the("next page pagination button").locatedBy(".-next button");

    public static final Map<String, String> INPUT_FIELD_SELECTORS_MAP = Map.of(
        "firstName", "#firstName",
        "lastName", "#lastName",
        "email", "#userEmail",
        "age", "#age",
        "salary", "#salary",
        "department", "#department"
    );

}
