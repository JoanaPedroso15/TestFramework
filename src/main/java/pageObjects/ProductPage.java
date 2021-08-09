package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductPage {
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductPage.class);
	 private static final String PAGE_URL = "https://www.demoblaze.com/prod.html";
	 
	 private WebDriver driver;
	 private WebDriverWait wait;
	 
	 public ProductPage(WebDriver _driver) {
			this.driver = _driver; 
		    PageFactory.initElements(driver, this);
			wait = new WebDriverWait(driver, 50);
		}
	
	private static final String BTN_ADD_CART = "//a[contains(text(),'Add to cart')]"; 
	
	@FindBy(how = How.XPATH, using = BTN_ADD_CART) 
	 private WebElement btnAddCart;
	 
	public void navigateTo() {
		LOG.info("Navigate to Product Page");
		driver.get(this.PAGE_URL);
	}
	
	public void clickAddToCart () {
		LOG.info("Add product to cart");
		wait.until(ExpectedConditions.elementToBeClickable(btnAddCart));
		btnAddCart.click();
	}
	
	public void confirmPopUp () {
		LOG.info("Exit confirmation pop-up");
		wait.until(ExpectedConditions.alertIsPresent()); 
		 driver.switchTo().alert().accept();
	}

}
