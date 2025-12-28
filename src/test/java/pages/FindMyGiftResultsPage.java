package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class FindMyGiftResultsPage {

	private final WebDriver driver;
	private final WebDriverWait wait;

	private static final By PRODUCT_GRID = By.cssSelector("[data-testid='product-grid']");
	private static final By PRODUCT_CARDS = By.cssSelector("[data-testid^='product-card-']");

	public FindMyGiftResultsPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public FindMyGiftResultsPage waitUntilLoaded() {
		WebElement grid = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_GRID));
		wait.until(d -> grid.findElements(PRODUCT_CARDS).size() > 0);
		return this;
	}

	public boolean isResultsVisible() {
		try {
			return driver.findElement(PRODUCT_GRID).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public List<ProductCardComponent> getProductCards() {
		WebElement grid = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_GRID));
		List<WebElement> roots = grid.findElements(PRODUCT_CARDS);

		return roots.stream().map(root -> new ProductCardComponent(driver, root)).collect(Collectors.toList());
	}

	public int getCardsCount() {
		return getProductCards().size();
	}

	public ProductCardComponent getCard(int index) {
		WebElement grid = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_GRID));
		WebElement root = grid.findElement(By.cssSelector("[data-testid='product-card-" + index + "']"));
		return new ProductCardComponent(driver, root);
	}
}
