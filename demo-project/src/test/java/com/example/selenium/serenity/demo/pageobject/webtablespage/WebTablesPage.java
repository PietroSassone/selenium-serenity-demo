package com.example.selenium.serenity.demo.pageobject.webtablespage;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.Getter;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;

@Getter
@DefaultUrl("https://demoqa.com/webtables")
public class WebTablesPage extends PageObject {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String AGE = "age";
    private static final String SALARY = "salary";
    private static final String DEPARTMENT = "department";

    @FindBy(id = "addNewRecordButton")
    private WebElementFacade addNewRecordButton;

    @FindBy(id = "firstName")
    private WebElementFacade firstNameInput;

    @FindBy(id = "lastName")
    private WebElementFacade lastNameInput;

    @FindBy(id = "userEmail")
    private WebElementFacade emailInput;

    @FindBy(id = "age")
    private WebElementFacade ageInput;

    @FindBy(id = "salary")
    private WebElementFacade salaryInput;

    @FindBy(id = "department")
    private WebElementFacade departmentInput;

    @FindBy(id = "submit")
    private WebElementFacade submitButton;

    @FindBy(xpath = "//div[@class='rt-tr-group']/div[div[1][text()]]")
    private List<WebElementFacade> filledTableRows;
    public static final String FILLED_TABLE_ROWS_SELECTOR = "//div[@class='rt-tr-group']/div[div[1][text()]]";

    private WebTablePagination webTablePagination;

    public WebElement getInputFieldByName(final String fieldName) {
        final Map<String, WebElementFacade> inputFieldsMap = Map.of(
            FIRST_NAME, firstNameInput,
            LAST_NAME, lastNameInput,
            EMAIL, emailInput,
            AGE, ageInput,
            SALARY, salaryInput,
            DEPARTMENT, departmentInput
        );
        return inputFieldsMap.get(fieldName);
    }
}
