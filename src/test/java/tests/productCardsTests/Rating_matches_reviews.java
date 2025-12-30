package tests.productCardsTests;

import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import data.ExperimentInput;
import pages.ResultPage;
import tests.BaseTest;
import pages.ProductCard;

@RunWith(Parameterized.class)
public class Rating_matches_reviews extends BaseTest {
	private final ExperimentInput e;
	private static ResultPage sharedResultsPage;
	private static ExperimentInput lastInput;

	public Rating_matches_reviews(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	private ResultPage getOrNavigate() {
		if (lastInput != null && lastInput.equals(e) && sharedResultsPage != null) {
			step("♻️ Using existing results for same input.");
			return sharedResultsPage;
		}

		step("📝 New input: Filling form fields.");
		sharedResultsPage = goToResults(e);
		lastInput = e;
		return sharedResultsPage;
	}

	@Test
	public void reviewsShouldMatchRating() {
		ResultPage results = getOrNavigate();
		boolean isMatchAll = true;
		int count = results.getCardsCount();

		for (int i = 0; i < count; i++) {
			step("=== CARD " + (i + 1) + " ===");

			ProductCard card = results.getCard(i);
			card.waitUntilReady();

			double product_rating = card.getRating();
			int sum_of_stars = 0;

			step("Toggle Reviews");
			if (!card.isReviewsOpen())
				card.toggleReviews();

			step("Is reviews window open?: " + card.isReviewsOpen());

			int number_of_reviwes = card.getNumberOfReviews();
			step("Number of reviews: " + number_of_reviwes);

			List<ProductCard.ReviewSnapshot> reviews = card.getReviews();

			for (int j = 0; j < number_of_reviwes; j++) {
				ProductCard.ReviewSnapshot review = reviews.get(j);
				int stars = review.getStars();
				step("stars:" + stars);
				sum_of_stars += stars;
			}
			step("sum of stars:" + sum_of_stars);
			double actual_rating = (double) sum_of_stars / number_of_reviwes;
			if (actual_rating != product_rating)
				isMatchAll = false;
			step("#" + (i + 1) + " Product rating: " + product_rating + " ||| Actual rating:" + actual_rating);
		}
		checkTrue("All reviews ratings should match the product rating", isMatchAll);
	}

	@Test
	public void numberOfReviewsShouldMatch() {
		ResultPage results = getOrNavigate();
		int count = results.getCardsCount();
		boolean isMatch = true;

		for (int i = 0; i < count; i++) {
			step("=== CARD " + (i + 1) + " ===");

			ProductCard card = results.getCard(i);
			card.waitUntilReady();
			step("Toggle Reviews");
			if (!card.isReviewsOpen())
				card.toggleReviews();

			step("Is reviwes window open?: " + card.isReviewsOpen());

			int review_count = card.getReviewCount();
			int actual_count = card.getNumberOfReviews();
			if (review_count != actual_count)
				isMatch = false;
			step("#" + (i + 1) + " review_count:" + review_count + " actual_count: " + actual_count);

		}
		checkTrue("number of reviews should match review count", isMatch);

	}

}
