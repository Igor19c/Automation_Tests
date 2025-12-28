package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import data.ExperimentInput;
import pages.FindMyGiftResultsPage;
import pages.GiftFormPage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.time.Duration;

public abstract class BaseTest {

	protected WebDriver driver;
	protected WebDriverWait wait;

	protected static final String baseUrl = "https://app--gift-genius-e436c719.base44.app";

	// protected static final List<ExperimentInput> experiments =
	// JsonExperimentLoader.load("/testcases.json");

	protected static final Logger log = LogManager.getLogger(BaseTest.class);

	@Rule
	public TestLoggingRule testLogger = new TestLoggingRule();

	@Before
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(12));
	}

	@After
	public void tearDown() {
		if (driver != null)
			driver.quit();
	}

	protected FindMyGiftResultsPage goToResults(ExperimentInput e) {
		new GiftFormPage(driver).open(baseUrl)
				.fillAll(e.getF1(), e.getF2(), e.getF3(), e.getF4(), e.getF5(), e.getF6(), e.getF7(), e.getF8())
				.submit();

		return new FindMyGiftResultsPage(driver).waitUntilLoaded();
	}

//	protected ExperimentInput exp(int expNum) {
//		return experiments.stream().filter(x -> x.getExperimentNumber() == expNum).findFirst()
//				.orElseThrow(() -> new IllegalArgumentException("Experiment not found: " + expNum));
//	}

	protected void step(String msg) {
		log.info("STEP: {}", msg);
	}

	protected void checkTrue(String what, boolean condition) {
		if (condition)
			log.info("STEP PASS: {}", what);
		else
			log.error("STEP FAIL: {}", what);
		assertTrue(what, condition);
	}

	protected void checkFalse(String what, boolean condition) {
		if (!condition)
			log.info("STEP PASS: {}", what);
		else
			log.error("STEP FAIL: {}", what);
		assertFalse(what, condition);
	}

	protected void checkEquals(String what, Object expected, Object actual) {
		boolean ok = (expected == null) ? actual == null : expected.equals(actual);
		if (ok)
			log.info("STEP PASS: {} | expected={}, actual={}", what, expected, actual);
		else
			log.error("STEP FAIL: {} | expected={}, actual={}", what, expected, actual);
		assertEquals(what, expected, actual);
	}

}
