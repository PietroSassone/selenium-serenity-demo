package com.example.selenium.serenity.demo.pageobject.webtablespage;

import org.openqa.selenium.support.FindBy;

import lombok.Getter;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

@Getter
public class WebTablePagination extends PageObject {

    @FindBy(css = ".-pageSizeOptions select")
    private WebElementFacade resultsPerPageDropdown;

    @FindBy(css = ".-totalPages")
    private WebElementFacade totalNumberOfTablePages;

    @FindBy(css = ".-pageJump input")
    private WebElementFacade pageJumpField;

    @FindBy(css = ".-previous button")
    private WebElementFacade previousPageButton;

    @FindBy(css = ".-next button")
    private WebElementFacade nextPageButton;
}
