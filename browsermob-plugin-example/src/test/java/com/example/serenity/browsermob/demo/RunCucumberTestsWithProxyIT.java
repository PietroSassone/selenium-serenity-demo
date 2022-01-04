package com.example.serenity.browsermob.demo;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.example.serenity.browsermob.demo.config.UITestSpringConfig;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com/example/serenity/browsermob/demo",
    plugin = "pretty"
)
@ContextConfiguration(classes = UITestSpringConfig.class)
public class RunCucumberTestsWithProxyIT {

}
