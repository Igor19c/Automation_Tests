package tests.resultPageTests;

import data.ExperimentInput;
import data.ExpectedData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.ResultPage;
import tests.BaseTest;

import java.util.Collection;

@RunWith(Parameterized.class)
public class Category_title_matches_api_score extends BaseTest {

    private final ExperimentInput e;

    public Category_title_matches_api_score(ExperimentInput e) {
        this.e = e;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return BaseTest.experimentsData();
    }

    @Test
    public void categoryTitle_shouldMatchExpectedByScore() {
        ResultPage results = goToResults(e);

        String expectedCategory = ExpectedData.expectedCategory(e);
        String actualCategoryTitle = results.getProductCatagory();

        step("Expected category=" + expectedCategory + ", actual title=" + actualCategoryTitle);
        checkTrue("Category title should match expected category",
                normalize(actualCategoryTitle).contains(normalize(expectedCategory)));
    }

    private static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }
}
