package tests;

import data.ExperimentInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.FindMyGiftResultsPage;
import pages.ProductCardComponent;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ReviewsToggleTests extends BaseTest {

	private final ExperimentInput e;

	public ReviewsToggleTests(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	@Test
	public void reviewsToggle_shouldToggleAndShowReviews() {
		// step("Navigate to results using " + e);
		FindMyGiftResultsPage results = goToResults(e);

		int count = results.getCardsCount();
//		checkTrue("Expected 5 product cards", count == 5);

		for (int i = 0; i < count; i++) {
			step("=== CARD " + (i + 1) + " ===");

			ProductCardComponent card = results.getCard(i);
			card.waitUntilReady();

			boolean initiallyOpen = card.isReviewsOpen();
			int initialCount = card.getReviews().size();
			step("Initial state: reviewsOpen=" + initiallyOpen + ", reviews=" + initialCount);

			step("Toggle Reviews");
			card.toggleReviews();

			// step("Wait until reviews open state toggles");
			wait.until(d -> card.isReviewsOpen() != initiallyOpen);

			checkTrue("Card " + (i + 1) + ": Reviews state should toggle after click",
					card.isReviewsOpen() != initiallyOpen);

			step("Verify reviews exist when Reviews is open");
			List<ProductCardComponent.ReviewSnapshot> reviews = card.getReviews();
			step("Card " + (i + 1) + ": Reviews count after open: " + reviews.size());
			checkTrue("Card " + (i + 1) + ": Expected at least 1 review when reviews are open", reviews.size() >= 1);

//			for (int k = 0; k < reviews.size(); k++) {
//				ProductCardComponent.ReviewSnapshot r = reviews.get(k);
//				checkTrue("Card " + i + ": Review author not blank (index " + k + ")",
//						r.getAuthor() != null && !r.getAuthor().trim().isEmpty());
//				checkTrue("Card " + i + ": Review stars 1..5 (index " + k + ", actual=" + r.getStars() + ")",
//						r.getStars() >= 1 && r.getStars() <= 5);
//				checkTrue("Card " + i + ": Review text not blank (index " + k + ")",
//						r.getText() != null && !r.getText().trim().isEmpty());
//				checkTrue("Card " + i + ": Review dateText not null (index " + k + ")", r.getDateText() != null);
//			}

			step("Toggle Reviews again to return to initial state");
			card.toggleReviews();

			// step("Wait until reviews open state returns to initial");
			wait.until(d -> card.isReviewsOpen() == initiallyOpen);

			checkTrue("Card " + (i + 1) + ": Reviews state should toggle back after second click",
					card.isReviewsOpen() == initiallyOpen);
			step("\n");

		}
	}
}
