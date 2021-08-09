package steps;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.TestContext;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.CartPage;
import pageObjects.Homepage;
import pageObjects.ProductPage;
import pageObjects.TestInputs;

public class StepDefinitions {
	
	private static final Logger LOG = LoggerFactory.getLogger(StepDefinitions.class);


	private WebDriver driver;
	private TestContext testContext; 
	private Scenario scenario; 

	private Homepage homepage;
	private ProductPage productPage;
	private CartPage cartPage;
	
	
	public StepDefinitions(TestContext context) {
		LOG.info("StepDefinitions constructor");
		testContext = context;
	}

	@Given("the user acesses the homepage of the marketplace")
	public void the_user_acesses_the_homepage_of_the_marketplace() {
		testContext.getWebDriverFactory();
		this.homepage = testContext.getPageObjectManager().getHomePage();
		this.homepage.navigateToHomePage();

	}

	@When("user selects a product category {string}")
	public void user_selects_a_product_category(String productCategory) {
		this.homepage.selectCategoryOfProducts(productCategory);
	}

	@When("selects the product {string}")
	public void selects_the_product(String productName) {
		this.homepage.selectProduct(productName);
	}

	@Then("adds the product to cart, accepts the pop-up confirmation")
	public void adds_the_product_to_cart_accepts_the_pop_up_confirmation() {
		this.productPage = testContext.getPageObjectManager().getProductPage();
		this.productPage.clickAddToCart();
		this.productPage.confirmPopUp();
	}

	@Then("user goes back to the main page of the marketplace")
	public void user_goes_back_to_the_main_page_of_the_marketplace() {
		this.homepage.navigateToHomePage();
	}

	@When("user navigates to the cart page")
	public void user_navigates_to_the_cart_page() {
		this.cartPage = testContext.getPageObjectManager().getCartPage();
		this.cartPage.navigateTo();
	}

	@When("deletes a product {string}")
	public void deletes_a_product(String nameOfProduct) throws InterruptedException {
		this.cartPage.deleteProduct(nameOfProduct);
	}

	@When("clicks on placing the order")
	public void clicks_on_placing_the_order() {
		this.cartPage.verifiyTotalPrice();
		this.cartPage.clickToPlaceOrder();
	}

	@When("fills the field {string} with the value {string}")
	public void fills_the_field_with_the_value(String nameOfField, String value) {
		TestInputs.setPurchaseData(nameOfField, value);
		this.cartPage.fillFormFields(nameOfField, value);
	}

	@When("user finishes the purchase")
	public void user_finishes_the_purchase() {
		this.cartPage.clickToPurchase();
	}

	@When("user verifies the information of the purchase")
	public void user_verifies_the_information_of_the_purchase() {
		this.cartPage.confirmPurchaseInformation();
		//Reporter.addStepLog("Purchase information");
	}

	@When("user closes the confirmation box")
	public void user_closes_the_confirmation_box() {
		this.cartPage.clickToCloseConfirmationBox();
	}

}
