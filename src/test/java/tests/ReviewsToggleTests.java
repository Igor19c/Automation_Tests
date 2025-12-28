package tests;

import data.ExperimentInput;
import data.JsonExperimentLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.FindMyGiftResultsPage;
import pages.ProductCardComponent;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class ReviewsToggleTests extends BaseTest {

    private final ExperimentInput e;

    public ReviewsToggleTests(ExperimentInput e) {
        this.e = e;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        List<ExperimentInput> exps = JsonExperimentLoader.load("/experiments_L18.json");
        return exps.stream().map(x -> new Object[]{x}).collect(Collectors.toList());
    }

    @Test
    public void reviewsToggle_shouldToggleAndShowReviews() {
        step("Navigate to results using " + e);
        FindMyGiftResultsPage results = goToResults(e);

        step("Open first product card");
        ProductCardComponent card0 = results.getCard(0);
        card0.waitUntilReady();

        boolean initiallyOpen = card0.isReviewsOpen();
        int initialCount = card0.getReviews().size();
        step("Initial state: reviewsOpen=" + initiallyOpen + ", reviews=" + initialCount);

        step("Toggle Reviews");
        card0.toggleReviews();

        step("Wait until reviews open state toggles");
        wait.until(d -> card0.isReviewsOpen() != initiallyOpen);

        boolean afterFirstToggle = card0.isReviewsOpen();
        step("After toggle: reviewsOpen=" + afterFirstToggle);
        checkTrue("Reviews state should toggle after click", afterFirstToggle != initiallyOpen);

        step("Verify reviews exist when Reviews is open");
        List<ProductCardComponent.ReviewSnapshot> reviews = card0.getReviews();
        step("Reviews count after open: " + reviews.size());
        checkTrue("Expected at least 1 review when reviews are open", reviews.size() >= 1);

        for (int i = 0; i < reviews.size(); i++) {
            ProductCardComponent.ReviewSnapshot r = reviews.get(i);
            checkTrue("Review author should not be blank (index " + i + ")", r.getAuthor() != null && !r.getAuthor().trim().isEmpty());
            checkTrue("Review stars should be 1..5 (index " + i + ", actual=" + r.getStars() + ")", r.getStars() >= 1 && r.getStars() <= 5);
            checkTrue("Review text should not be blank (index " + i + ")", r.getText() != null && !r.getText().trim().isEmpty());
            checkTrue("Review dateText should not be null (index " + i + ")", r.getDateText() != null);
        }

        step("Toggle Reviews again to return to initial state");
        card0.toggleReviews();

        step("Wait until reviews open state returns to initial");
        wait.until(d -> card0.isReviewsOpen() == initiallyOpen);

        boolean afterSecondToggle = card0.isReviewsOpen();
        step("After second toggle: reviewsOpen=" + afterSecondToggle);
        checkTrue("Reviews state should toggle back after second click", afterSecondToggle == initiallyOpen);
    }
}
