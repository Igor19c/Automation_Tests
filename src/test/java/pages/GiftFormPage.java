package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class GiftFormPage {
	private final WebDriver driver;
	private final WebDriverWait wait;

	private final By f1 = By.id("dropdown-f1");
	private final By f2 = By.id("dropdown-f2");
	private final By f3 = By.id("dropdown-f3");
	private final By f4 = By.id("dropdown-f4");
	private final By f5 = By.id("dropdown-f5");
	private final By f6 = By.id("dropdown-f6");
	private final By f7 = By.id("dropdown-f7");
	private final By f8 = By.id("dropdown-f8");

	private final By submitBtn = By.id("submit-button");

	public GiftFormPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public GiftFormPage open(String url) {
		driver.get(url);
		return this;
	}

	public GiftFormPage waitUntilLoaded() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(f1));
			wait.until(ExpectedConditions.visibilityOfElementLocated(f1));
			return this;
		} catch (TimeoutException e) {
			throw new AssertionError("GiftFormPage did not load. Current URL: " + driver.getCurrentUrl() + " | Title: "
					+ driver.getTitle(), e);
		}
	}

	public boolean isSubmitEnabled() {
		return wait.until(ExpectedConditions.presenceOfElementLocated(submitBtn)).isEnabled();
	}

	private void selectLevel(By locator, int level) {
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
		Select s = new Select(el);
		List<WebElement> options = s.getOptions();

		boolean hasPlaceholder = !options.isEmpty() && options.get(0).getText().trim().matches(".*(בחר|Select|בחרו).*");

		int indexToSelect = hasPlaceholder ? level : (level - 1);

		s.selectByIndex(indexToSelect);
	}

	public GiftFormPage fillAll(int f1v, int f2v, int f3v, int f4v, int f5v, int f6v, int f7v, int f8v) {
		selectLevel(f1, f1v);
		selectLevel(f2, f2v);
		selectLevel(f3, f3v);
		selectLevel(f4, f4v);
		selectLevel(f5, f5v);
		selectLevel(f6, f6v);
		selectLevel(f7, f7v);
		selectLevel(f8, f8v);
		return this;
	}

	public void fill_1(int f1v) {
		selectLevel(f1, f1v);
	}

	public void fill_2(int f2v) {
		selectLevel(f2, f2v);
	}

	public void fill_3(int f3v) {
		selectLevel(f3, f3v);
	}

	public void fill_4(int f4v) {
		selectLevel(f4, f4v);
	}

	public void fill_5(int f5v) {
		selectLevel(f5, f5v);
	}

	public void fill_6(int f6v) {
		selectLevel(f6, f6v);
	}

	public void fill_7(int f7v) {
		selectLevel(f7, f7v);
	}

	public void fill_8(int f8v) {
		selectLevel(f8, f8v);
	}


	public void submit() {
		wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
	}
	
	private WebElement getDropdownElement(By by) {
		return driver.findElement(by);
	}
	public List<WebElement> getAllFields() {
	    return Arrays.asList(getDropdownElement(f1), getDropdownElement(f2), getDropdownElement(f3)
	    		, getDropdownElement(f4), getDropdownElement(f5), getDropdownElement(f6), 
	    		getDropdownElement(f7), getDropdownElement(f8));
	}

}
