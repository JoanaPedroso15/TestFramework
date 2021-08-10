package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.FileReaderManager;
import framework.Wait;

public class Homepage {
	
	private static final Logger LOG = LoggerFactory.getLogger(Homepage.class);
	private static final String PAGE_URL = "https://www.demoblaze.com/index.html";

	private WebDriver driver;
	private WebDriverWait wait;
	
	public Homepage(WebDriver _driver) {
		this.driver = _driver; 
	    PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 50);
	}

	private static final String CATEGORY_LAPTOPS =  "//*[contains(text(),'Laptops')]"; 
	private static final String CATEGORY_PHONES = "//*[contains(text(),'Phones')]";
	private static final String CATEGORY_MONITORS = "//*[contains(text(),'Monitors')]";
	
	@FindBy(how = How.XPATH, using = CATEGORY_LAPTOPS)
	private WebElement laptopCategory;

	@FindBy(how = How.XPATH, using = CATEGORY_PHONES)
	private WebElement phonesCategory;

	@FindBy(how = How.XPATH, using = CATEGORY_MONITORS)
	private WebElement monitorsCategory;

	
	
	public void navigateToHomePage() {
		LOG.info("Navigate to homepage");
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	}

	public void selectCategoryOfProducts(String valueToSelect) {
		LOG.info("Select a product category: " + valueToSelect);
		switch (valueToSelect) {
		case "laptops":
			wait.until(ExpectedConditions.elementToBeClickable(laptopCategory));
			laptopCategory.click();
			break;
		case "phones":
			phonesCategory.click();
			break;
		case "monitors":
			monitorsCategory.click();
			break;
		}
	}

	public void selectProduct(String productName) {
		LOG.info("Select a product: " + productName);
		String expression = "//a[contains(text(), '" + productName + "')]";
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(expression)));
		Wait.retryStaleElements(By.xpath(expression), driver, 3);
		//driver.findElement(By.xpath(expression)).click();
	}


}
