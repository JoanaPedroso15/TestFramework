package framework;

import org.openqa.selenium.WebDriver;

import pageObjects.CartPage;
import pageObjects.Homepage;
import pageObjects.ProductPage;

public class PageObjectsManager {

	private WebDriver driver;
	private Homepage homePage;
	private CartPage cartPage;
	private ProductPage productPage;

	public PageObjectsManager(WebDriver driver) {
		this.driver = driver;

	}

	public Homepage getHomePage() {

		return (homePage == null) ? homePage = new Homepage(driver) : homePage;

	}

	public ProductPage getProductPage() {
		return (productPage == null) ? productPage = new ProductPage(driver) : productPage;
	}

	public CartPage getCartPage() {
		return (cartPage == null) ? cartPage = new CartPage(driver) : cartPage;
	}

}
