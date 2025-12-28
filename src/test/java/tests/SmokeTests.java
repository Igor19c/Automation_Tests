package tests;

import data.ExperimentInput;
import data.JsonExperimentLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.FindMyGiftResultsPage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class SmokeTests extends BaseTest {

    private final ExperimentInput e;

    public SmokeTests(ExperimentInput e) {
        this.e = e;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        List<ExperimentInput> exps = JsonExperimentLoader.load("/experiments_L18.json");
        return exps.stream().map(x -> new Object[]{x}).collect(Collectors.toList());
    }

    @Test
    public void openFillSubmit_shouldShowResultsAndCards() {
        step("Navigate to results using " + e);
        FindMyGiftResultsPage results = goToResults(e);

        step("Verify results grid is visible");
        checkTrue("Results grid should be visible", results.isResultsVisible());

        int count = results.getCardsCount();
        step("Verify cards count (actual=" + count + ")");
        checkTrue("Expected at least 1 product card", count >= 1);
    }
}
