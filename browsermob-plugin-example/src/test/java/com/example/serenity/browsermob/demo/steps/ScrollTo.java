package com.example.serenity.browsermob.demo.steps;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.example.serenity.browsermob.demo.pageobject.webtablespage.WebTablesPage;
import net.thucydides.core.annotations.Step;

public class ScrollTo {

    private static final String SCROLL_INTO_VIEW_SCRIPT = "arguments[0].scrollIntoView();";

    private WebTablesPage webTablesPage;

    @Step
    public void theDeleteButton(final int indexOfDeleteButton) {
        moveToElementWithJs(webTablesPage.getDeleteButtons().get(indexOfDeleteButton -1));
    }

    /**
     * Scrolling to a given web element. In case of some web elements, the regular scrolling doesn't work.
     * Especially useful for firefox.
     */
    public void moveToElementWithJs(final WebElement webElement) {
        ((JavascriptExecutor) webTablesPage.getDriver()).executeScript(SCROLL_INTO_VIEW_SCRIPT, webElement);
    }
}
