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
public class ImagesToggleTests extends BaseTest {

    private final ExperimentInput e;

    public ImagesToggleTests(ExperimentInput e) {
        this.e = e;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        List<ExperimentInput> exps = JsonExperimentLoader.load("/experiments_L18.json");
        return exps.stream().map(x -> new Object[]{x}).collect(Collectors.toList());
    }

    @Test
    public void imagesToggle_shouldToggleAndShowAdditionalImages() {
        step("Navigate to results using " + e);
        FindMyGiftResultsPage results = goToResults(e);

        step("Open first product card");
        ProductCardComponent card0 = results.getCard(0);
        card0.waitUntilReady();

        boolean initiallyOpen = card0.isImagesOpen();
        int initialCount = card0.getAdditionalImageUrls().size();
        step("Initial state: imagesOpen=" + initiallyOpen + ", additionalImages=" + initialCount);

        step("Toggle Images");
        card0.toggleImages();

        step("Wait until images open state toggles");
        wait.until(d -> card0.isImagesOpen() != initiallyOpen);

        boolean afterFirstToggle = card0.isImagesOpen();
        step("After toggle: imagesOpen=" + afterFirstToggle);
        checkTrue("Images state should toggle after click", afterFirstToggle != initiallyOpen);

        step("Verify additional images exist when Images is open");
        List<String> urls = card0.getAdditionalImageUrls();
        step("Additional images count after open: " + urls.size());
        checkTrue("Expected at least 1 additional image when images are open", urls.size() >= 1);

        for (int i = 0; i < urls.size(); i++) {
            String u = urls.get(i);
            checkTrue("Additional image src should not be blank (index " + i + ")", u != null && !u.trim().isEmpty());
        }

        step("Toggle Images again to return to initial state");
        card0.toggleImages();

        step("Wait until images open state returns to initial");
        wait.until(d -> card0.isImagesOpen() == initiallyOpen);

        boolean afterSecondToggle = card0.isImagesOpen();
        step("After second toggle: imagesOpen=" + afterSecondToggle);
        checkTrue("Images state should toggle back after second click", afterSecondToggle == initiallyOpen);
    }
}
