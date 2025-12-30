package tests.productCardsTests;

import data.ExperimentInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.ResultPage;
import pages.ProductCard;
import tests.BaseTest;

import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class Product_card_reviews_toggle extends BaseTest {

	private final ExperimentInput e;

	public Product_card_reviews_toggle(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return experimentsData();
	}

	@Test
	public void reviewsToggle_shouldToggleAndShowReviews() {
		ResultPage results = goToResults(e);

		int count = results.getCardsCount();

		for (int i = 0; i < count; i++) {
			step("=== CARD " + (i + 1) + " ===");

			ProductCard card = results.getCard(i);
			card.waitUntilReady();

			boolean initiallyOpen = card.isReviewsOpen();
			int initialCount = card.getReviews().size();
			step("Initial state: reviewsOpen=" + initiallyOpen + ", reviews=" + initialCount);

			step("Toggle Reviews");
			card.toggleReviews();

			wait.until(d -> card.isReviewsOpen() != initiallyOpen);

			checkTrue("Card " + (i + 1) + ": Reviews state should toggle after click",
					card.isReviewsOpen() != initiallyOpen);

			step("Verify reviews exist when Reviews is open");
			List<ProductCard.ReviewSnapshot> reviews = card.getReviews();
			step("Card " + (i + 1) + ": Reviews count after open: " + reviews.size());
			checkTrue("Card " + (i + 1) + ": Expected at least 1 review when reviews are open", reviews.size() >= 1);

			step("Toggle Reviews again to return to initial state");
			card.toggleReviews();

			wait.until(d -> card.isReviewsOpen() == initiallyOpen);

			checkTrue("Card " + (i + 1) + ": Reviews state should toggle back after second click",
					card.isReviewsOpen() == initiallyOpen);
			step("\n");

		}
	}
}
