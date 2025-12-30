package tests.resultPageTests;

import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import data.ExpectedData;
import data.ExperimentInput;
import pages.ResultPage;
import tests.BaseTest;

@RunWith(Parameterized.class)
public class Product_cards_number extends BaseTest {

	private final ExperimentInput e;

	public Product_cards_number(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	@Test
	public void resultPage_shouldHaveExactly5ProductCards() {
		ResultPage results = goToResults(e);
		int count = results.getCardsCount();
		step("P Score is: " + ExpectedData.round2(ExpectedData.score(e)));
		step("Verify cards count (actual = " + count + ")");
		checkTrue("Expected 5 product cards", count == 5);
	}
}
