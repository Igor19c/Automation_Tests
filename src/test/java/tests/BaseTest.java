package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import data.ExperimentInput;
import data.JsonExperimentLoader;
import pages.ResultPage;
import pages.GiftFormPage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseTest {

	protected static WebDriver driver;
	protected WebDriverWait wait;

	protected static final String baseUrl = "https://app--gift-genius-e436c719.base44.app";

	protected static final Logger log = LogManager.getLogger(BaseTest.class);

	@Rule
	public TestLoggingRule testLogger = new TestLoggingRule();

//	@Before
//	public void setUp() {
//		driver = new ChromeDriver();
//		driver.manage().window().maximize();
//		wait = new WebDriverWait(driver, Duration.ofSeconds(12));
//	}
//
//	@After
//	public void tearDown() {
//		if (driver != null)
//			driver.quit();
//	}

	@Before
	public void setUp() {
		if (driver == null) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, Duration.ofSeconds(12));
		}
	}

	@After
	public void tearDown() {

	}

	@AfterClass
	public static void finalTearDown() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	protected GiftFormPage openInputPage() {
		return new GiftFormPage(driver).open(baseUrl).waitUntilLoaded();
	}

	public static Collection<Object[]> experimentsData() {
		List<ExperimentInput> exps = JsonExperimentLoader.load("/experiments_L18.json");
		return exps.stream().map(x -> new Object[] { x }).collect(Collectors.toList());
	}

	protected ResultPage goToResults(ExperimentInput e) {
		new GiftFormPage(driver).open(baseUrl)
				.fillAll(e.getF1(), e.getF2(), e.getF3(), e.getF4(), e.getF5(), e.getF6(), e.getF7(), e.getF8())
				.submit();

		return new ResultPage(driver).waitUntilLoaded();
	}

	protected void step(String msg) {
		log.info("STEP: {}", msg);
	}

	protected boolean checkTrue(String what, boolean condition) {
		if (condition)
			log.info("STEP PASS: {}", what);
		else
			log.error("STEP FAIL: {}", what);
		assertTrue(what, condition);
		return condition;
	}

	protected boolean checkFalse(String what, boolean condition) {
		if (!condition)
			log.info("STEP PASS: {}", what);
		else
			log.error("STEP FAIL: {}", what);
		assertFalse(what, condition);
		return !condition;
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
