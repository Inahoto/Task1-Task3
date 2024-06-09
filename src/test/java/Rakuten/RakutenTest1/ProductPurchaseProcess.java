package Rakuten.RakutenTest1;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductPurchaseProcess {
	static WebDriver driver = new ChromeDriver();

	public static void main(String[] args) throws InterruptedException {
		// Launching the Browser and maximizing the browser window
		driver.get("https://sweetshop.netlify.app/");
		driver.manage().window().maximize();

		// Add different quantities of 4 products to basket
		AddMultipleProductsToCart();

		// navigate to basket page
		WebElement pgeBasket = driver.findElement(By.xpath("//a[@href='/basket']"));
		pgeBasket.click();

		// Verify All the selected items are present in basket
		VerifySelectedItemsPresentInBasket();

		// Test the total price in GBP is correct
		// i.e., matches the price of individual items based on quantity.
		VerifyAllItemsPriceInBasket();

		// Change the delivery type to Standard Shipping
		WebElement rdoButtonStandardShipping = driver
				.findElement(By.xpath("(//label[@class='custom-control-label'])[2]"));
		rdoButtonStandardShipping.click();

		// Verify the total price again after changing the delivery type as standard
		// shipping
		VerifyAllItemsPriceInBasket();

		// Filling all the details in Billing Page
		FillBillingFormOnCheckout();

		// Clicking on Checkout button
		 WebElement btnCheckout = driver.findElement(By.xpath("//button[text()='Continue to checkout']"));
		 btnCheckout.click();

		// Closing the Current browser once the test is completed
		// driver.close();

	}

// Function for Adding multiple product to Cart with different quantity
	public static void AddMultipleProductsToCart() {
		List<WebElement> btnAddToCart = driver.findElements(By.xpath("//a[text()='Add to Basket']"));
		// Creating an Array object to store the objects to be added to cart
		String[] productItemsName = { "Bon Bons", "Chocolate Cups", "Sherbert Straws", "Sherbert Discs" };

		List<WebElement> intProductsTitle = driver.findElements(By.xpath("//h4[@class='card-title']"));
		int intProductTitleCount = intProductsTitle.size();
		System.out.println("Total Products present in the Page is : " + intProductTitleCount);

		for (int i = 0; i < intProductTitleCount; i++) {
			String productName = intProductsTitle.get(i).getText();

			// Converting Array to Array list for easy access to objects in Array
			List<String> newItemsList = Arrays.asList(productItemsName);

			if (productName.contains("Bon Bons") && newItemsList.contains(productName)) {
				for (int j = 1; j <= 4; j++) {
					btnAddToCart.get(i).click();
				}
			} else if (productName.contains("Chocolate Cups") && newItemsList.contains(productName)) {
				for (int j = 1; j <= 1; j++) {
					btnAddToCart.get(i).click();
				}
			} else if (productName.contains("Sherbert Straws") && newItemsList.contains(productName)) {
				for (int j = 1; j <= 2; j++) {
					btnAddToCart.get(i).click();
				}
			} else if (productName.contains("Sherbert Discs") && newItemsList.contains(productName)) {
				for (int j = 1; j <= 3; j++) {
					btnAddToCart.get(i).click();
				}
			}

		}
	}

	public static void VerifySelectedItemsPresentInBasket() {

		// Declaring the Array with the same product list added to Basket to match if
		// the same product is preset in the basket
		String[] expProductItemsInBasket = { "Bon Bons", "Chocolate Cups", "Sherbert Straws", "Sherbert Discs" };

		List<WebElement> lstProductsInBasket = driver.findElements(By.xpath("//h6[@class='my-0']"));
		int intProductTitleCount = lstProductsInBasket.size();
		System.out.println("Total Products present in Basket is : " + intProductTitleCount);

		for (int i = 0; i < intProductTitleCount; i++) {
			String productName = lstProductsInBasket.get(i).getText();
			System.out.println(productName);

			// converting array to Array list to access the Array object
			List<String> newProductItemsList = Arrays.asList(expProductItemsInBasket);

			if (newProductItemsList.contains(productName)) {
				System.out.println("The product item -" + productName + " is present in the Basket");
			} else {
				System.out.println("Error: The product item -" + productName + " is not present in the Basket");
			}

		}

	}

	public static void VerifyAllItemsPriceInBasket() {
		int covertedItemQnty = 1;
		double covertedItemPrice = 1.0;
		double actualItemPrice = 1.0;
		double totalItemPrice = 0.0;
		double expectedItemPrice = 1.0;
		List<WebElement> itemQuanity = driver.findElements(By.xpath("//ul//small[@class='text-muted']"));
		List<WebElement> itemPrice = driver.findElements(By.xpath("//ul//span[@class='text-muted']"));

		for (int i = 0; i < itemQuanity.size(); i++) {
			String[] itemQnty = itemQuanity.get(i).getText().split(" ");
			String formatedItemQnty = itemQnty[1].trim();
			covertedItemQnty = Integer.parseInt(formatedItemQnty);

			// Pick the first Product Price value from the loop
			String[] formatedproductPrice = itemPrice.get(i).getText().split("£");
			String fomatedItemPrice = formatedproductPrice[1].trim();
			covertedItemPrice = Double.parseDouble(fomatedItemPrice);

			// Calculate the actual item price
			actualItemPrice = covertedItemQnty * covertedItemPrice;

			// Accumulate the total price
			totalItemPrice += actualItemPrice;

		}
		System.out.println("Actual Total Sum of all the Product Price in the Bucket " + totalItemPrice);
		String[] totalBucketAmount = driver.findElement(By.xpath("//ul//strong")).getText().split("£");
		String formatedAmount = totalBucketAmount[1].trim();
		expectedItemPrice = Double.parseDouble(formatedAmount);

		// compare the addition of all items price in the card with Total Amount shown
		if (totalItemPrice == expectedItemPrice) {
			System.out.println("Total Item Price on adding is : [" + totalItemPrice
					+ "] Total Item Price shown in Total Column is : [" + expectedItemPrice + "]");
		} else {
			System.out.println("Error: Total item price on adding is not equals to Overall Total Amount shown");
		}
	}

	public static void FillBillingFormOnCheckout() {
		// Locators declared at the begin
		WebElement txtBoxfirstName = driver.findElement(By.xpath("(//input[@id='name'])[1]"));
		WebElement txtBoxLastName = driver.findElement(By.xpath("(//input[@id='name'])[2]"));
		WebElement txtBoxEmail = driver.findElement(By.xpath("//input[@id='email']"));
		WebElement txtBoxAddress = driver.findElement(By.xpath("//input[@id='address']"));
		WebElement txtBoxAddress1 = driver.findElement(By.xpath("//input[@id='address2']"));
		WebElement txtBoxZipCode = driver.findElement(By.id("zip"));
		WebElement txtCardName = driver.findElement(By.id("cc-name"));
		WebElement txtCardNumber = driver.findElement(By.id("cc-number"));
		WebElement txtCardExpiry = driver.findElement(By.id("cc-expiration"));
		WebElement txtCardCvvNumber = driver.findElement(By.id("cc-cvv"));

		txtBoxfirstName.sendKeys("inahoto");
		txtBoxLastName.sendKeys("Aye");
		txtBoxEmail.sendKeys("inahoto@gmail.com");
		txtBoxAddress.sendKeys("1st Sector, HSR Layout");
		txtBoxAddress1.sendKeys("124 street rd, Opposite to Universal Mall");

		Select dpwSelectCountry = new Select(driver.findElement(By.id("country")));
		dpwSelectCountry.selectByVisibleText("United Kingdom");

		Select dpwSelectCity = new Select(driver.findElement(By.id("city")));
		dpwSelectCity.selectByVisibleText("Cardiff");

		txtBoxZipCode.sendKeys("110011");
		txtCardName.sendKeys("Inahoto Aye");
		txtCardNumber.sendKeys("567656746376");
		txtCardExpiry.sendKeys("10/25");
		txtCardCvvNumber.sendKeys("123");
	}
}
