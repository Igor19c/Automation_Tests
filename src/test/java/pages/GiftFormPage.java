package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
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

	/** חשוב: לחכות שהטופס באמת מופיע */
	public GiftFormPage waitUntilLoaded() {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(f1));
			wait.until(ExpectedConditions.visibilityOfElementLocated(f1));
			return this;
		} catch (TimeoutException e) {
			// דיבאג קריטי: נדע איפה אנחנו באמת ומה הכותרת
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

	public void submit() {
		wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
	}
}
