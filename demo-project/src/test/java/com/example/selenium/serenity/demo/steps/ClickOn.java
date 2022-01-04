package com.example.selenium.serenity.demo.steps;

import com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage;
import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage;
import net.thucydides.core.annotations.Step;

public class ClickOn {

    private DemoHomePage homePage;
    private WebTablesPage webTablesPage;

    @Step
    public void theSubmitButton() {
        webTablesPage.getSubmitButton().click();
    }

    @Step
    public void theAddNewRecordButton() {
        webTablesPage.getAddNewRecordButton().click();
    }

    @Step
    public void thePreviousPageButton() {
        webTablesPage.getWebTablePagination().getPreviousPageButton().click();
    }

    @Step
    public void theNextPageButton() {
        webTablesPage.getWebTablePagination().getNextPageButton().click();
    }

}
