package pageObjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.Wait;

public class CartPage {

	private static final Logger LOG = LoggerFactory.getLogger(CartPage.class);
	private static final String PAGE_URL = "https://www.demoblaze.com/cart.html";
	private WebDriver driver;
	private WebDriverWait wait;

	public CartPage(WebDriver _driver) {
		this.driver = _driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 50);
	}

	private static final String BTN_PLACE_ORDER = "//button[contains(text(),'Place Order')]";
	private static final String TBODY_LIST_PRODUCTS = "tbodyid";
	private static final String FORM_ELEMENTS = "form-group";
	private static final String FORM_FIELD_NAME = "name";
	private static final String FORM_FIELD_COUNTRY = "country";
	private static final String FORM_FIELD_CITY = "city";
	private static final String FORM_FIELD_MONTH = "month";
	private static final String FORM_FIELD_YEAR = "year";
	private static final String FORM_FIELD_CARD = "card";
	private static final String BTN_FINISH_PURCHASE = "//*[contains(text(),'Purchase')]";
	private static final String BTN_CANCEL_PURCHASE = "//*[contains(text(),'Close')]";
	private static final String BTN_CONFIRM_DATA = "//button[contains(text(), 'OK')]";
	private static final String PURCHASE_PARAGRAPH = "//*[contains(@class, 'text-muted')]";

	@FindBy(how = How.XPATH, using = BTN_PLACE_ORDER)
	private WebElement btnPlaceOrder;

	@FindBy(how = How.ID, using = TBODY_LIST_PRODUCTS)
	private WebElement listOfProducts;

	@FindBy(how = How.CLASS_NAME, using = FORM_ELEMENTS)
	private List<WebElement> form;

	@FindBy(how = How.ID, using = FORM_FIELD_NAME)
	private WebElement inputFieldName;

	@FindBy(how = How.ID, using = FORM_FIELD_COUNTRY)
	private WebElement inputFieldCountry;

	@FindBy(how = How.ID, using = FORM_FIELD_CITY)
	private WebElement inputFieldCity;

	@FindBy(how = How.ID, using = FORM_FIELD_CARD)
	private WebElement inputFieldCreditCard;

	@FindBy(how = How.ID, using = FORM_FIELD_MONTH)
	private WebElement inputFieldMonth;

	@FindBy(how = How.ID, using = FORM_FIELD_YEAR)
	private WebElement inputFieldYear;

	@FindBy(how = How.XPATH, using = BTN_FINISH_PURCHASE)
	private WebElement btnPurchase;

	@FindBy(how = How.XPATH, using = BTN_CANCEL_PURCHASE)
	private WebElement btnCancelPurchase;

	@FindBy(how = How.XPATH, using = BTN_CONFIRM_DATA)
	private WebElement btnConfirmData;

	@FindBy(how = How.XPATH, using = PURCHASE_PARAGRAPH)
	private WebElement purchaseInfoParagraph;

	public void navigateTo() {
		LOG.info("Navigate to Cart Page");
		driver.get(this.PAGE_URL);
	}

	public void deleteProduct(String nameProduct) throws InterruptedException {
		LOG.info("Delete a product: " + nameProduct);
		String expression = "//*[contains(text(), '" + nameProduct + "')]/parent::*/td[4]/a";
		driver.findElement(By.xpath(expression)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(expression)));
	}

	public void verifiyTotalPrice() {
		LOG.info("Verify the total amount of the pruchase");
		String price = "";
		int totalAmount = 0;

		List<WebElement> listProducts = driver.findElements(By.xpath("//*[@id='" + TBODY_LIST_PRODUCTS + "']/tr"));
		for (int i = 1; i <= listProducts.size(); i++) {
			price = driver.findElement(By.xpath("//*[@id='" + TBODY_LIST_PRODUCTS + "']/tr[" + i + "]/td[3]"))
					.getText();
			totalAmount = totalAmount + Integer.valueOf(price);
		}
		TestInputs.setPurchaseData("total price", String.valueOf(totalAmount));
	}

	public void clickToPlaceOrder() {
		LOG.info("Click on placing order");
		Wait.retryStaleElements(By.xpath(BTN_PLACE_ORDER), driver, 4);
	}

	public void fillFormFields(String nameOfField, String value) {
		LOG.info("Fill purchase form field " + nameOfField + " with value " + value);
		switch (nameOfField) {
		case "name":
			LOG.info("Field name");
			inputFieldName.sendKeys(value);
			break;
		case "country":
			inputFieldCountry.sendKeys(value);
			break;
		case "city":
			inputFieldCity.sendKeys(value);
			break;
		case "credit card":
			inputFieldCreditCard.sendKeys(value);
			break;
		case "month":
			inputFieldMonth.sendKeys(value);
			break;
		case "year":
			inputFieldYear.sendKeys(value);
			break;
		}
	}

	public void clickToPurchase() {
		LOG.info("Click to finish purchase");
		try {
			btnPurchase.click();
		} catch (UnhandledAlertException e) {
			inputFieldName.sendKeys(TestInputs.getPurchaseData().get("name"));
			btnPurchase.click();
		}
		btnPurchase.click();
	}

	public void clickToCancelPurchase() {
		LOG.info("Click to cancel purchase");
		btnCancelPurchase.click();
	}

	public void confirmPurchaseInformation() {
		LOG.info("Confirm purchase information");
		Map<String, String> purchaseDataDisplayed = verifyPurchaseDataDisplayed();
		Map<String, String> purchaseDataFilled = TestInputs.getPurchaseData();
		assertPrice(purchaseDataFilled.get("total price"), purchaseDataDisplayed.get("amount"));

		LOG.info("PURCHASE ID= " + purchaseDataDisplayed.get("id"));
	}

	private Map<String, String> verifyPurchaseDataDisplayed() {
		String purchaseInfo = purchaseInfoParagraph.getText();

		String date = purchaseInfo.split("Date: ")[1];
		String temp = purchaseInfo.split("Date: ")[0];

		String name = temp.split("Name: ")[1].trim();
		String temp2 = temp.split("Name: ")[0];

		String card = temp2.split("Card Number: ")[1].trim();
		String temp3 = temp2.split("Card Number: ")[0];

		String amountCurrency = temp3.split("Amount: ")[1].trim();
		String amount = amountCurrency.split(" USD")[0];

		String id = temp3.split("Amount: ")[0].split("Id: ")[1].trim();

		Map<String, String> purchaseDataDisplayed = new HashMap<>();
		purchaseDataDisplayed.put("id", id);
		purchaseDataDisplayed.put("name", name);
		purchaseDataDisplayed.put("credit card", card);
		purchaseDataDisplayed.put("date", date);
		purchaseDataDisplayed.put("amount", amount);

		LOG.info("Purchase info displayed to user: " + purchaseDataDisplayed);
		return purchaseDataDisplayed;
	}

	private void assertPrice(String expectedValue, String realValue) {
		String errorMsg = "The total amount of the purchase " + realValue + " is not equal to the expected value "
				+ expectedValue;
		Assert.assertEquals(errorMsg, expectedValue, realValue);
	}

	public void clickToCloseConfirmationBox() {
		LOG.info("Close confirmation box of purchase");
		btnConfirmData.click();
	}

}
