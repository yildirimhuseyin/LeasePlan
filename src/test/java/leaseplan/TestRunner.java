package leaseplan;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {
                // below plugin allows to create html report
                "html:target/reports/cucumber-report.html",
        },
        features = "src/test/resources/features/search",
        glue = "leaseplan/stepdefinitions",
        // tags used to run specific scenarios like @smoke, @regression, @positive
        tags = "")

public class TestRunner {}
