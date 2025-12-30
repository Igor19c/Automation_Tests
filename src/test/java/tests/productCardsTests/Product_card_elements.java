package tests.productCardsTests;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import data.ExperimentInput;
import pages.ProductCard;
import pages.ResultPage;
import tests.BaseTest;

@RunWith(Parameterized.class)
public class Product_card_elements extends BaseTest {

	private final ExperimentInput e;
	private static ResultPage sharedResultsPage;
	private static ExperimentInput lastInput;

	public Product_card_elements(ExperimentInput e) {
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
	public void productCards_shouldDisplayThumbnail() {
		ResultPage results = getOrNavigate();
		int count = results.getCardsCount();
		int countThumbnailFailures = 0;

		for (int i = 0; i < count; i++) {
			ProductCard card = results.getCard(i);
			boolean chk = checkFalse("Card #" + (i + 1) + " should have Tumbnail", card.getThumbnailUrl().isEmpty());
			if (!chk)
				countThumbnailFailures++;
		}
		checkTrue("All cards should have Tumbnail", countThumbnailFailures == 0);
	}

	@Test
	public void productCards_shouldDisplayproductAdditionalImagesBtn() {
		ResultPage results = getOrNavigate();
		int count = results.getCardsCount();
		int countImagesBtnFailures = 0;

		for (int i = 0; i < count; i++) {
			ProductCard card = results.getCard(i);
			boolean chk = checkTrue("Card #" + (i + 1) + " should have Additional Images Button",
					card.getImageBtn().isDisplayed());
			if (!chk)
				countImagesBtnFailures++;
		}
		checkTrue("All cards should have Additional Images Button", countImagesBtnFailures == 0);
	}

	@Test
	public void productCards_shouldDisplayReviewsBtn() {
		ResultPage results = getOrNavigate();
		int count = results.getCardsCount();
		int countReviewBtnFailures = 0;

		for (int i = 0; i < count; i++) {
			ProductCard card = results.getCard(i);
			boolean chk = checkTrue("Card #" + (i + 1) + " should have Review Button",
					card.getReviewBtn().isDisplayed());
			if (!chk)
				countReviewBtnFailures++;
		}
		checkTrue("All cards should have Review Button", countReviewBtnFailures == 0);
	}

	@Test
	public void productCards_shouldDisplayTitle() {
		ResultPage results = getOrNavigate();
		int count = results.getCardsCount();
		int countTitleFailures = 0;

		for (int i = 0; i < count; i++) {
			ProductCard card = results.getCard(i);
			boolean condition = card.getTitle().isEmpty();
			boolean chk = checkFalse("Card #" + (i + 1) + " should have Title (card's title: "
					+ (condition ? "---" : card.getTitle()) + ")", condition);
			if (!chk)
				countTitleFailures++;
		}
		checkTrue("All cards should have Title", countTitleFailures == 0);
	}

	@Test
	public void productCards_shouldDisplayDescription() {
		ResultPage results = getOrNavigate();
		int count = results.getCardsCount();
		int countDescriptionFailures = 0;

		for (int i = 0; i < count; i++) {
			ProductCard card = results.getCard(i);
			boolean condition = card.getDescription().isEmpty();
			boolean chk = checkFalse("Card #" + (i + 1) + " should have Description (card's description: "
					+ (condition ? "---" : card.getDescription()) + ")", condition);
			if (!chk)
				countDescriptionFailures++;
		}
		checkTrue("All cards should have Description", countDescriptionFailures == 0);
	}

	@Test
	public void productCards_shouldDisplayPrice() {
		ResultPage results = getOrNavigate();
		int count = results.getCardsCount();
		int countPriceFailures = 0;

		for (int i = 0; i < count; i++) {
			ProductCard card = results.getCard(i);
			boolean condition = card.getPriceText().isEmpty();
			boolean chk = checkFalse("Card #" + (i + 1) + " should have Price (card's price: "
					+ (condition ? "---" : card.getPrice()) + ")", condition);
			if (!chk)
				countPriceFailures++;
		}
		checkTrue("All cards should have Price", countPriceFailures == 0);
	}

	@Test
	public void productCards_shouldDisplayRating() {
		ResultPage results = getOrNavigate();
		int count = results.getCardsCount();
		int countRatingFailures = 0;

		for (int i = 0; i < count; i++) {
			ProductCard card = results.getCard(i);
			boolean condition = card.getRatingText().isEmpty();
			boolean chk = checkFalse("Card #" + (i + 1) + " should have Rating (card's rating: "
					+ (condition ? "---" : card.getPrice()) + ")", condition);
			if (!chk)
				countRatingFailures++;
		}
		checkTrue("All cards should have Rating", countRatingFailures == 0);
	}

//	@Test
//	public void cardValidation() {
//		ResultPage results = getOrNavigate();
//		int count = results.getCardsCount();
//
//		int countThumbnailFailures = 0;
//		int countTitleFailures = 0;
//		int countDescriptionFailures = 0;
//		int countPriceFailures = 0;
//		int countRatingFailures = 0;
//		int countImageBtnFailures = 0;
//		int countReviewBtnFailures = 0;
//
//		for (int i = 0; i < count; i++) {
//			ProductCard card = results.getCard(i);
//			countThumbnailFailures += isThereThumbnailInProductCard(card, i);
//			countTitleFailures += isThereTitleInProductCard(card, i);
//			countDescriptionFailures += isThereDescriptionInProductCard(card, i);
//			countPriceFailures += isTherePriceInProductCard(card, i);
//			countRatingFailures += isThereRatingInProductCard(card, i);
//			countImageBtnFailures += isThereAdditionalImageBtnInProductCard(card, i);
//			countReviewBtnFailures += isThereReviewBtnInProductCard(card, i);
//			checkTrue("Card #" + (i + 1) + " should have all the components",
//					countThumbnailFailures == 0 && countTitleFailures == 0 && countDescriptionFailures == 0
//							&& countPriceFailures == 0 && countRatingFailures == 0 && countImageBtnFailures == 0
//							&& countReviewBtnFailures == 0);
//		}
//	}
//
//	public int isThereThumbnailInProductCard(ProductCard card, int index) {
//		boolean chk = checkFalse("Card #" + (index + 1) + " should have Tumbnail", card.getThumbnailUrl().isEmpty());
//		return chk ? 0 : 1;
//	}
//
//	public int isThereTitleInProductCard(ProductCard card, int index) {
//		boolean chk = checkFalse("Card #" + (index + 1) + " should have Title (card's title: "
//				+ (card.getTitle().isEmpty() ? "---" : card.getTitle()) + ")", card.getTitle().isEmpty());
//		return chk ? 0 : 1;
//	}
//
//	public int isThereDescriptionInProductCard(ProductCard card, int index) {
//		boolean chk = checkFalse(
//				"Card #" + (index + 1) + " should have Rating (card's description: "
//						+ (card.getDescription().isEmpty() ? "---" : card.getDescription()) + ")",
//				card.getDescription().isEmpty());
//		return chk ? 0 : 1;
//	}
//
//	public int isTherePriceInProductCard(ProductCard card, int index) {
//		boolean chk = checkFalse("Card #" + (index + 1) + " should have Price (card's price: "
//				+ (card.getPriceText().isEmpty() ? "---" : card.getPrice()) + ")", card.getPriceText().isEmpty());
//		return chk ? 0 : 1;
//	}
//
//	public int isThereRatingInProductCard(ProductCard card, int index) {
//		boolean chk = checkFalse(
//				"Card #" + (index + 1) + " should have Rating (card's rating: "
//						+ (card.getRatingText().isEmpty() ? "---" : card.getRating()) + ")",
//				card.getRatingText().isEmpty());
//		return chk ? 0 : 1;
//
//	}
//
//	public int isThereAdditionalImageBtnInProductCard(ProductCard card, int index) {
//		boolean chk = checkTrue("Card #" + (index + 1) + " should have Additional Images Button",
//				card.getImageBtn().isDisplayed());
//		return chk ? 0 : 1;
//	}
//
//	public int isThereReviewBtnInProductCard(ProductCard card, int index) {
//		boolean chk = checkTrue("Card #" + (index + 1) + " should have Review Button",
//				card.getReviewBtn().isDisplayed());
//		return chk ? 0 : 1;
//	}
}
