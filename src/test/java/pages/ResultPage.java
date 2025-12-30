package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class ResultPage {

	private final WebDriver driver;
	private final WebDriverWait wait;

	private static final By PRODUCT_GRID = By.cssSelector("[data-testid='product-grid']");
	private static final By PRODUCT_CARDS = By.cssSelector("[data-testid^='product-card-']");
	private static final By PRODUCT_CATEGORY_NAME = By.xpath("//h3[@data-filename='pages/FindMyGift']");
	private static final By PRODUCT_SCORE = By.xpath("//p[@data-visual-selector-id='pages/FindMyGift331']");
	private static final By LAST_UPDATE_FIELD = By.id("last-update-field");

	public ResultPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public ResultPage waitUntilLoaded() {
		WebElement grid = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_GRID));
		wait.until(d -> grid.findElements(PRODUCT_CARDS).size() > 0);
		return this;
	}

	private String[] getLastUpdate() {
		WebElement dateTime = driver.findElement(LAST_UPDATE_FIELD);
		String fullUpdateText = dateTime.getText();
		String dateTimePart = fullUpdateText.split("Last Update: ")[1].trim();
		String[] parts = dateTimePart.split(" ");
		return parts;
	}

	public LocalTime getTime() {
		LocalTime timeVal = LocalTime.parse(getLastUpdate()[1]);
		return timeVal;
	}

	public LocalDate getDate() {
		LocalDate dateVal = LocalDate.parse(getLastUpdate()[0]);
		return dateVal;
	}

	public String getProductCatagory() {
		WebElement name = driver.findElement(PRODUCT_CATEGORY_NAME);
		String fullText = name.getText();
		String result = fullText.split("Recommended: ")[1].trim();
		return result;
	}

	public Double getProductScore() {
		WebElement scoreElement = driver.findElement(PRODUCT_SCORE);
		String fullScoreText = scoreElement.getText();
		String scoreValue = fullScoreText.replaceAll("[^0-9.]", "");
		double score = Double.parseDouble(scoreValue);
		return score;
	}

	public boolean isResultsVisible() {
		try {
			return driver.findElement(PRODUCT_GRID).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public List<ProductCard> getProductCards() {
		WebElement grid = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_GRID));
		List<WebElement> roots = grid.findElements(PRODUCT_CARDS);

		return roots.stream().map(root -> new ProductCard(driver, root)).collect(Collectors.toList());
	}

	public int getCardsCount() {
		return getProductCards().size();
	}

	public ProductCard getCard(int index) {
		WebElement grid = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_GRID));
		WebElement root = grid.findElement(By.cssSelector("[data-testid='product-card-" + index + "']"));
		return new ProductCard(driver, root);
	}
}
