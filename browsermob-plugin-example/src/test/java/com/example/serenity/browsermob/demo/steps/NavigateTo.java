package com.example.serenity.browsermob.demo.steps;

import com.example.serenity.browsermob.demo.pageobject.homepage.DemoHomePage;
import com.example.serenity.browsermob.demo.pageobject.webtablespage.WebTablesPage;
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
