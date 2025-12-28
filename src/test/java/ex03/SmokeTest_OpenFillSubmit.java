package ex03;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import data.JsonExperimentLoader;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class SmokeTest_OpenFillSubmit {

	private WebDriver driver;

	private static final String APP_URL = "https://app--gift-genius-e436c719.base44.app";

	private final data.ExperimentInput tc;

	public SmokeTest_OpenFillSubmit(data.ExperimentInput tc) {
		this.tc = tc;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		List<data.ExperimentInput> cases = JsonExperimentLoader.load("/testcases.json");
		return cases.stream().map(c -> new Object[] { c }).toList();
	}

	@Before
	public void setUp() {
		driver = DriverFactory.newChrome();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	@After
	public void tearDown() {
		if (driver != null)
		driver.quit();
	}

	private void selectByLevel(String selectId, int level) {
		Select s = new Select(driver.findElement(By.id(selectId)));

		int realIndex = 0;
		int pickedCount = 0;

		for (int i = 0; i < s.getOptions().size(); i++) {
			String t = s.getOptions().get(i).getText().trim();
			boolean isPlaceholder = t.isEmpty() || t.matches("(?i).*(select|choose|pick|בחר|בחרו).*");
			if (isPlaceholder)
				continue;

			pickedCount++;
			if (pickedCount == level) {
				realIndex = i;
				s.selectByIndex(realIndex);
				return;
			}
		}

		throw new RuntimeException(
				"Could not select level=" + level + " for " + selectId + " (options=" + s.getOptions().size() + ")");
	}

	private void assertSelectedNotPlaceholder(String selectId) {
		Select s = new Select(driver.findElement(By.id(selectId)));
		String sel = s.getFirstSelectedOption().getText().trim();
		System.out.println(selectId + " selected: " + sel);
		assertFalse("Dropdown still on placeholder: " + selectId,
				sel.matches("(?i).*(select|choose|pick|בחר|בחרו).*") || sel.isEmpty());
	}

	@Test
	public void smoke_shouldShowFirstProductTitle() throws InterruptedException {
		driver.get(APP_URL);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
		By submitBtn = By.xpath("//button[contains(normalize-space(.),'Find My Gift')]");
		wait.until(d -> !d.findElements(submitBtn).isEmpty());

		System.out.println("Submit enabled at start? " + driver.findElement(submitBtn).isEnabled());
		assertFalse("Submit should be disabled before filling", driver.findElement(submitBtn).isEnabled());

		// ממלאים דוגמה פשוטה: כולם 1
		selectByLevel("dropdown-f1", tc.f1);
		selectByLevel("dropdown-f2", tc.f2);
		selectByLevel("dropdown-f3", tc.f3);
		selectByLevel("dropdown-f4", tc.f4);
		selectByLevel("dropdown-f5", tc.f5);
		selectByLevel("dropdown-f6", tc.f6);
		selectByLevel("dropdown-f7", tc.f7);
		selectByLevel("dropdown-f8", tc.f8);

		System.out.println("Submit enabled after fill? " + driver.findElement(submitBtn).isEnabled());
		assertTrue("Submit should be enabled after filling", driver.findElement(submitBtn).isEnabled());

		driver.findElement(submitBtn).click();

		// מחכים לתוצאה: לפי ה-HTML שלך יש data-testid=product-title-0
		wait.until(d -> !d.findElements(By.cssSelector("[data-testid='product-title-0']")).isEmpty());

		
		String title = driver.findElement(By.cssSelector("[data-testid='product-title-0']")).getText().trim();
		System.out.println("First title: " + title);
		assertFalse("First product title should not be blank", title.isBlank());

		String title2 = driver.findElement(By.cssSelector("[data-testid='product-title-1']")).getText().trim();
		System.out.println("Second title: " + title2);
		assertFalse("Second product title should not be blank", title2.isBlank());
	}
}
