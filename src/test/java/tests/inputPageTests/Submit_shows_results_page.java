package tests.inputPageTests;

import data.ExperimentInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.ResultPage;
import tests.BaseTest;

import java.util.Collection;

@RunWith(Parameterized.class)
public class Submit_shows_results_page extends BaseTest {

	private final ExperimentInput e;

	public Submit_shows_results_page(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	@Test
	public void submit_shouldShowResultsPage() {
		ResultPage results = goToResults(e);
		step("Verify results grid is visible");
		checkTrue("Results grid should be visible", results.isResultsVisible());
	}

}
