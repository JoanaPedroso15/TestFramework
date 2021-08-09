package framework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.Scenario;


public class Screenshot {

	private static final Logger LOG = LoggerFactory.getLogger(Screenshot.class);

	public static String screenshotdir = System.getProperty("user.dir") + "/Reports/Screenshots/";

	public static void take(WebDriver driver, Scenario scenario) {
		if (driver instanceof TakesScreenshot) {
			TakesScreenshot screenshotableDriver = (TakesScreenshot) driver;
			try {
				File screenshot = screenshotableDriver.getScreenshotAs(OutputType.FILE);
				byte[] fileContent = FileUtils.readFileToByteArray(screenshot);
				scenario.attach(fileContent, "image/png", "screenshot");
	
			} catch (WebDriverException | IOException e) {
				LOG.info(e.getMessage());
			}
		} else {
			LOG.warn("This web driver implementation cannot create screenshots");
		}
	}

	
	public static String getScreenshot(WebDriver driver) throws IOException 
	{
	    String Base64StringOfScreenshot="";
	    TakesScreenshot screenshotableDriver = (TakesScreenshot) driver;
	    
	    //File src = screenshotableDriver.getScreenshotAs(OutputType.FILE);
	    
	    byte[] fileContent = screenshotableDriver.getScreenshotAs(OutputType.BYTES);
			    
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_HHmmss");
		String sDate = sdf.format(date);

		//FileUtils.copyFile(, new File(screenshotdir + "image_" + sDate + ".png"));
			    
		//byte[] fileContent = FileUtils.readFileToByteArray(src);
		Base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
		return Base64StringOfScreenshot;
	}
	
//	public static void take(WebDriver driver, Scenario scenario) {
//
//		String screenshotName = scenario.getName().replaceAll(" ", "_");
//		try {
//			TakesScreenshot screenshotableDriver = (TakesScreenshot) driver;
//			 sourcePath = screenshotableDriver.getScreenshotAs(OutputType.FILE);
//			Path destinationPath = new Path(
//					System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName + ".png");
//
//			Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
//			Reporter.addScreenCaptureFromPath(destinationPath.toString());
//		} catch (IOException e) {
//			LOG.info(e.getMessage());
//		}
//
//	}

}
