package testRunners;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "steps" }, 
        plugin = {"pretty","html:target/site/cucumber-pretty","json:target/cucumber/cucumber.json"}, 
		publish = true)


public class TestRunner {

	@AfterClass
	public static void writeExtentReport() {
		//Reporter.loadXMLConfig(new File("src/test/resources/extend-config.xml"));
	}

}
