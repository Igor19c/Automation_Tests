package tests.resultPageTests;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import data.ExperimentInput;
import pages.ResultPage;
import tests.BaseTest;

@RunWith(Parameterized.class)
public class Last_update_matches_current_time extends BaseTest {

	private final ExperimentInput e;

	public Last_update_matches_current_time(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	@Test
	public void lastUpdate_shouldMatchCurrent() {
		ResultPage results = goToResults(e);

		checkTrue("Results should be visible", results.isResultsVisible());

		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
		
		String actualDate = results.getDate().format(dateformatter);
		String expectedDate = LocalDate.now().format(dateformatter);
		String actualTime = results.getTime().format(timeformatter);
		String expectedTime = LocalTime.now().format(timeformatter);
		 
		step("Expected time = " + expectedTime + ", actual time = " + actualTime);
		checkTrue("Last update time should be same as current time", expectedTime.equals(expectedTime));

		step("Expected date = " + expectedDate + ", actual date = " + actualDate);
		checkTrue("Last update should be same as current date", actualDate.equals(expectedDate));
		

	}
}