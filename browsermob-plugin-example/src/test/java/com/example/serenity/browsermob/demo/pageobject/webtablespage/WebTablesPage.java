package com.example.serenity.browsermob.demo.pageobject.webtablespage;

import java.util.List;

import org.openqa.selenium.support.FindBy;

import lombok.Getter;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@Getter
@DefaultUrl("https://demoqa.com/webtables")
public class WebTablesPage extends PageObject {

    @FindBy(xpath = "//div[@class='rt-tr-group']/div[div[1][text()]]")
    private List<WebElementFacade> filledTableRows;

    @FindBy(xpath = "//div[@class='action-buttons']/span[2]")
    private List<WebElementFacade> deleteButtons;

    @FindBy(className = "rt-noData")
    private WebElementFacade noDataTextElement;
}
