package com.example.selenium.serenity.demo.pageobject.homepage;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

@DefaultUrl("https://demoqa.com/")
public class DemoHomePage extends PageObject {
    public static final String HEADER_SELECTOR = "#app > header > a";
    public static final Target HEADER = Target.the("the header").locatedBy(HEADER_SELECTOR);
    public static final String JOIN_LINK_SELECTOR = ".home-banner a";
    public static final Target CERTIFICATION_IMAGE = Target.the("the certification training image").locatedBy(".home-banner img");
    public static final String FOOTER_SELECTOR = "footer";
    public static final Target WIDGETS = Target.the("widget cards").locatedBy(".card");
}
