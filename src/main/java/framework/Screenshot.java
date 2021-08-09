package framework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.cucumber.java.Scenario;

public class Screenshot {

	private static final Logger LOG = LoggerFactory.getLogger(Screenshot.class);

	public static void take(WebDriver driver, Scenario scenario) {
		if (driver instanceof TakesScreenshot) {
			TakesScreenshot screenshotableDriver = (TakesScreenshot) driver;
			try {
				byte[] screenshot = screenshotableDriver.getScreenshotAs(OutputType.BYTES);
				scenario.attach(screenshot, "image/png", "Screenshot " + scenario.getName());
			} catch (WebDriverException e) {
				LOG.info(e.getMessage());
			}
		} else {
			LOG.warn("This web driver implementation cannot create screenshots");
		}
	}
	
	/*

	public static void take2(WebDriver driver, Scenario scenario) {

		String screenshotName = scenario.getName().replaceAll(" ", "_");
		try {
			TakesScreenshot screenshotableDriver = (TakesScreenshot) driver;
			 sourcePath = screenshotableDriver.getScreenshotAs(OutputType.FILE);
			Path destinationPath = new Path(
					System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName + ".png");

			Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
			Reporter.addScreenCaptureFromPath(destinationPath.toString());
		} catch (IOException e) {
			LOG.info(e.getMessage());
		}

	}
	*/

}
