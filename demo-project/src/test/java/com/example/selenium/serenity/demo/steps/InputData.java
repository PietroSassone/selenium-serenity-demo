package com.example.selenium.serenity.demo.steps;

import static com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage.FILLED_ROWS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.example.selenium.serenity.demo.config.UITestSpringConfig;
import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTableRow;
import com.example.selenium.serenity.demo.pageobject.webtablespage.WebTablesPage;
import com.github.javafaker.Faker;
import net.serenitybdd.screenplay.Actor;

@ContextConfiguration(classes = UITestSpringConfig.class)
public class InputData {
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String AGE = "age";
    private static final String SALARY = "salary";
    private static final String DEPARTMENT = "department";

    private final Map<String, String> inputFieldsAndValuesMap = new HashMap<>();

    private WebTablesPage webTablesPage;

    @Autowired
    private Faker testDataGenerator;

    public void resetGeneratedInputData() {
        inputFieldsAndValuesMap.clear();
    }

    public Map<String, String> generateInputDataForNewWebTableRecord() {
        inputFieldsAndValuesMap.put(FIRST_NAME, testDataGenerator.name().firstName());
        inputFieldsAndValuesMap.put(LAST_NAME, testDataGenerator.name().lastName());
        inputFieldsAndValuesMap.put(AGE, String.valueOf(testDataGenerator.number().numberBetween(1, 99)));
        inputFieldsAndValuesMap.put(EMAIL, testDataGenerator.internet().emailAddress());
        inputFieldsAndValuesMap.put(SALARY, String.valueOf(testDataGenerator.number().numberBetween(0, Integer.MAX_VALUE)));
        inputFieldsAndValuesMap.put(DEPARTMENT, testDataGenerator.lorem().characters(1, 20));
        return inputFieldsAndValuesMap;
    }

    public WebTableRow buildWebTableRowFromPreparedTestInput() {
        return WebTableRow.builder()
            .firstName(inputFieldsAndValuesMap.get(FIRST_NAME))
            .lastName(inputFieldsAndValuesMap.get(LAST_NAME))
            .age(Integer.parseInt(inputFieldsAndValuesMap.get(AGE)))
            .email(inputFieldsAndValuesMap.get(EMAIL))
            .salary(Integer.parseInt(inputFieldsAndValuesMap.get(SALARY)))
            .department(inputFieldsAndValuesMap.get(DEPARTMENT))
            .build();
    }

    public List<WebTableRow> getRowsOfTheTableMatchingExpectedInput2(final WebTableRow expectedRow, final Actor actor) {
        return FILLED_ROWS.resolveAllFor(actor).stream()
            .map(
                row -> WebTableRow.builder()
                    .firstName(getNthChildDivText(1).apply(row))
                    .lastName(getNthChildDivText(2).apply(row))
                    .age(getNthChildDivNumber(3).apply(row))
                    .email(getNthChildDivText(4).apply(row))
                    .salary(getNthChildDivNumber(5).apply(row))
                    .department(getNthChildDivText(6).apply(row))
                    .build()
            )
            .filter(expectedRow::equals)
            .collect(Collectors.toList());
    }

    private Function<WebElement, WebElement> getNthChildDivElement(final int indexOfDivChildElement) {
        return webElement -> webElement.findElement(By.cssSelector(String.format("div:nth-child(%s)", indexOfDivChildElement)));
    }

    private Function<WebElement, String> getNthChildDivText(final int indexOfDivChildElement) {
        return webElement -> getNthChildDivElement(indexOfDivChildElement).apply(webElement).getText();
    }

    private Function<WebElement, Integer> getNthChildDivNumber(final int indexOfDivChildElement) {
        return webElement -> Integer.valueOf(getNthChildDivText(indexOfDivChildElement).apply(webElement));
    }
}
