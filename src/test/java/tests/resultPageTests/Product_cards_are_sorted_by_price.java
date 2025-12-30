package tests.resultPageTests;

import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import data.ExperimentInput;
import pages.ResultPage;
import pages.ProductCard;
import tests.BaseTest;

@RunWith(Parameterized.class)
public class Product_cards_are_sorted_by_price extends BaseTest {
	private final ExperimentInput e;

	public Product_cards_are_sorted_by_price(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	@Test
	public void cards_shouldBeSortedByPrice_ascending() {
		ResultPage results = goToResults(e);

		checkTrue("Results should be visible", results.isResultsVisible());

		int count = results.getCardsCount();
		boolean isSorted = true;

		ProductCard card = results.getCard(0);
		card.waitUntilReady();

		double currentPrice = card.getPrice();
		double nextPrice;
		step("The catagory is: " + results.getProductCatagory());

		for (int i = 1; i < count; i++) {
			card = results.getCard(i);
			card.waitUntilReady();

			nextPrice = card.getPrice();
			step(i + " price:" + currentPrice + " should be smaller then " + (i + 1) + " price:" + nextPrice);
			if (currentPrice > nextPrice) {
				isSorted = false;
				break;
			}
			currentPrice = nextPrice;
		}

		checkTrue("Product cards should be sorted in ascending order by product price", isSorted);

	}

}
