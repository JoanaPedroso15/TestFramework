package framework;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.Scenario;

public class TestContext {
	
private static final Logger LOG = LoggerFactory.getLogger(TestContext.class);
	
	private WebDriverFactory webDriverManager;
	private PageObjectsManager pageObjectsManager;


	public TestContext() {
		LOG.info("Test Context Constructor");
		webDriverManager = new WebDriverFactory();
		pageObjectsManager = new PageObjectsManager(webDriverManager.getDriver());
	}

	
	public WebDriverFactory getWebDriverFactory() {
		LOG.info("Test context: getWebDriver()");
		return webDriverManager; 
	}

	public PageObjectsManager getPageObjectManager() {
		LOG.info("Test context: getPageObjectManager()");
		return pageObjectsManager;
	}

}
