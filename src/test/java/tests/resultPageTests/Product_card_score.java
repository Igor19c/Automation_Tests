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
public class Product_card_score extends BaseTest {

	private final ExperimentInput e;

	public Product_card_score(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	@Test
	public void score_shouldMatchExpectedScore() {
		ResultPage results = goToResults(e);

		checkTrue("Results should be visible", results.isResultsVisible());

		double actual = results.getProductScore();
		double expected = ExpectedData.round2(ExpectedData.score(e));

		step("Expected p = " + expected + ", actual p = " + actual);
		checkTrue("P score should be same as expected", actual == expected);
	}
}
