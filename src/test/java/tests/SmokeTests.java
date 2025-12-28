package tests;

import data.ExperimentInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.FindMyGiftResultsPage;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SmokeTests extends BaseTest {

	private final ExperimentInput e;

	public SmokeTests(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	@Test
	public void openFillSubmit_shouldShowResultsAndCards() {
		step("Navigate to results using " + e);
		FindMyGiftResultsPage results = goToResults(e);

		step("Verify results grid is visible");
		checkTrue("Results grid should be visible", results.isResultsVisible());

		int count = results.getCardsCount();
		step("Verify cards count (actual=" + count + ")");
		checkTrue("Expected 5 product cards", count == 5);
	}
}
