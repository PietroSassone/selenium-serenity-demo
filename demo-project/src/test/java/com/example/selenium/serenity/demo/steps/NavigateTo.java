package com.example.selenium.serenity.demo.steps;

import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage;
import com.example.selenium.serenity.demo.pageobject.homepage.DemoHomePage;
import net.thucydides.core.annotations.Step;

public class NavigateTo {

    private DemoHomePage homePage;
    private WebTablesPage webTablesPage;

    @Step
    public void theDemoHomePage() {
        homePage.open();
    }

    @Step
    public void theWebTablesPage() {
        webTablesPage.open();
    }
}
