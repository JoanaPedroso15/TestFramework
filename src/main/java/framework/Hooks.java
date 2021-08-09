package framework;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;


public class Hooks {

	private static final Logger LOG = LoggerFactory.getLogger(Screenshot.class);
	TestContext testContext;
	WebDriver driver;
	Scenario scenario;


	public static String screenshotdir = System.getProperty("user.dir") + "/test-output/screenshots/";

	public Hooks(TestContext context) {
		testContext = context;
	}

	@Before()
	public void beforeTest(Scenario _scenario) throws InterruptedException, IOException {
		LOG.info("Screenshot directory " + screenshotdir);
		this.scenario = _scenario;
		if ((new File(screenshotdir)).exists())
			FileUtils.cleanDirectory(new File(screenshotdir));
	}

	@After
	public void endOfTest(Scenario scenario) {
		if (scenario.isFailed()) {
			Screenshot.take(driver, scenario);
		}
		driver.quit();
		testContext.getWebDriverFactory().closeDriver();
	}

}
