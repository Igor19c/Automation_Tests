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
public class ImagesToggleTests extends BaseTest {

	private final ExperimentInput e;

	public ImagesToggleTests(ExperimentInput e) {
		this.e = e;
	}

	@Parameterized.Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return BaseTest.experimentsData();
	}

	@Test
	public void imagesToggle_shouldToggleAndShowAdditionalImages() {

		// step("Navigate to results using " + e);
		FindMyGiftResultsPage results = goToResults(e);

		int count = results.getCardsCount();
//		checkTrue("Expected 5 product cards", count == 5);

		for (int i = 0; i < count; i++) {
			step("=== CARD " + (i + 1) + " ===");

			ProductCardComponent card = results.getCard(i);
			card.waitUntilReady();

			boolean initiallyOpen = card.isImagesOpen();
			int initialCount = card.getAdditionalImageUrls().size();
			step("Initial state: imagesOpen=" + initiallyOpen + ", additionalImages=" + initialCount);

			step("Toggle Images");
			card.toggleImages();

			// step("Wait until images open state toggles");
			wait.until(d -> card.isImagesOpen() != initiallyOpen);

			boolean afterFirstToggle = card.isImagesOpen();
			checkTrue("Card " + (i + 1) + ": Images state should toggle after click",
					afterFirstToggle != initiallyOpen);

			step("Verify additional images exist when Images is open");
			List<String> urls = card.getAdditionalImageUrls();
			step("Card " + (i + 1) + ": Additional images count after open: " + urls.size());
			checkTrue("Card " + (i + 1) + ": Expected at least 1 additional image when images are open",
					urls.size() >= 1);

//			for (int k = 0; k < urls.size(); k++) {
//				String u = urls.get(k);
//				checkTrue("Card " + i + ": Additional image src should not be blank (index " + k + ")",
//						u != null && !u.trim().isEmpty());
//			}

			step("Toggle Images again to return to initial state");
			card.toggleImages();

			// step("Wait until images open state returns to initial");
			wait.until(d -> card.isImagesOpen() == initiallyOpen);

			checkTrue("Card " + (i + 1) + ": Images state should toggle back after second click",
					card.isImagesOpen() == initiallyOpen);
		}
	}
}
