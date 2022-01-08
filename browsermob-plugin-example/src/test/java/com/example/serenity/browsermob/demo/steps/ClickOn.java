package com.example.serenity.browsermob.demo.steps;

import com.example.serenity.browsermob.demo.pageobject.webtablespage.WebTablesPage;
import net.thucydides.core.annotations.Step;

public class ClickOn {

    private WebTablesPage webTablesPage;

    @Step
    public void theWebTableDeleteButton(final int indexOfButton) {
        webTablesPage.getDeleteButtons().get(indexOfButton - 1).click();
    }

}
