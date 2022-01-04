package com.example.selenium.serenity.demo;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.example.selenium.serenity.demo.config.UITestSpringConfig;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features="src/test/resources/features",
    glue = "com/example/selenium/serenity/demoa",
    plugin = "pretty"
)
@ContextConfiguration(classes = UITestSpringConfig.class)
public class RunCucumberTestsIT {

}
