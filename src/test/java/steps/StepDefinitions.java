package steps;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import framework.Screenshot;
import framework.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
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
	ExtentReports extent;
	ExtentTest logger;

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
	public void user_verifies_the_information_of_the_purchase() throws IOException {
		this.cartPage.confirmPurchaseInformation();
		String base64String = Screenshot.getScreenshot(driver);
		logger.addScreenCaptureFromBase64String("base64String");

	}

	@When("user closes the confirmation box")
	public void user_closes_the_confirmation_box() {
		this.cartPage.clickToCloseConfirmationBox();
	}

	@Before()
	public void bf0(Scenario scenario) throws InterruptedException, IOException {
		ExtentReports extent = new ExtentReports();
		ExtentTest test = extent.createTest("Scenario 01");
	}

	@After
	public void endOfTest(Scenario scenario) {
		if (scenario.isFailed()) {
			
			try {
				String base64String = Screenshot.getScreenshot(driver);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			logger.addScreenCaptureFromBase64String("base64String");
		
		}
		extent.flush();
		testContext.getWebDriverFactory().closeDriver();
	}

	/*
	 * 
	 * @AfterMethod public void getResult(ITestResult result) throws IOException{
	 * if(result.getStatus() == ITestResult.FAILURE){ logger.log(LogStatus.FAIL,
	 * "Test Case Failed is "+result.getName()); logger.log(LogStatus.FAIL,
	 * "Test Case Failed is "+result.getThrowable()); //To capture screenshot path
	 * and store the path of the screenshot in the string "screenshotPath" //We do
	 * pass the path captured by this mehtod in to the extent reports using
	 * "logger.addScreenCapture" method. String screenshotPath =
	 * ExtentReportsClass.getScreenshot(driver, result.getName()); //To add it in
	 * the extent report logger.log(LogStatus.FAIL,
	 * logger.addScreenCapture(screenshotPath)); }else if(result.getStatus() ==
	 * ITestResult.SKIP){ logger.log(LogStatus.SKIP,
	 * "Test Case Skipped is "+result.getName()); } // ending test //endTest(logger)
	 * : It ends the current test and prepares to create HTML report
	 * extent.endTest(logger); }
	 * 
	 * @AfterTest public void endReport(){ // writing everything to document
	 * //flush() - to write or update test information to your report.
	 * extent.flush(); //Call close() at the very end of your session to clear all
	 * resources. //If any of your test ended abruptly causing any side-affects (not
	 * all logs sent to ExtentReports, information missing), this method will ensure
	 * that the test is still appended to the report with a warning message. //You
	 * should call close() only once, at the very end (in @AfterSuite for example)
	 * as it closes the underlying stream. //Once this method is called, calling any
	 * Extent method will throw an error. //close() - To close all the operation
	 * extent.close(); } }
	 * 
	 */

}
