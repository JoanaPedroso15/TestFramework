package testRunners;


import java.io.IOException;

import org.junit.runner.RunWith;

import com.aventstack.extentreports.ExtentReports;

import framework.Screenshot;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = { "steps" },
		plugin = { "pretty", "json:target/json-report/cucumber.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" },
		publish = true)

public class TestRunner {

	
	
	
}
