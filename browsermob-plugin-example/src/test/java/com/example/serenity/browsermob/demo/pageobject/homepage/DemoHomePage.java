package com.example.serenity.browsermob.demo.pageobject.homepage;

import java.util.List;

import lombok.Getter;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@Getter
@DefaultUrl("https://demoqa.com/")
public class DemoHomePage extends PageObject {
    private static final String HREF_ATTRIBUTE = "href";

    @FindBy(css = "header img")
    private WebElementFacade headerImage;

    @FindBy(css = "#app > header > a")
    private WebElementFacade header;

    @FindBy(css = ".home-banner a")
    private WebElementFacade joinLink;

    @FindBy(css = ".home-banner img")
    private WebElementFacade certificationTrainingImage;

    @FindBy(className = "card")
    private List<WebElementFacade> widgets;

    @FindBy(css = "footer")
    private WebElementFacade footer;

    public String getHeaderLink() {
        return header.getAttribute(HREF_ATTRIBUTE);
    }

    public String getJoinLink() {
        return joinLink.getAttribute(HREF_ATTRIBUTE);
    }
}
