package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductCardComponent {

    private final WebElement root;
    private final WebDriverWait wait;

    private static final By TITLE = byTestIdPrefix("product-title-");
    private static final By PRICE = byTestIdPrefix("product-price-");
    private static final By DESCRIPTION = byTestIdPrefix("product-description-");
    private static final By RATING_BLOCK = byTestIdPrefix("product-rating-");
    private static final By THUMBNAIL_IMG = byTestIdPrefix("product-image-");
    private static final By IMAGES_BUTTON = byTestIdPrefix("images-button-");
    private static final By REVIEWS_BUTTON = byTestIdPrefix("reviews-button-");

    private static By byTestIdPrefix(String prefix) {
        return By.cssSelector("[data-testid^='" + prefix + "']");
    }

    public ProductCardComponent(WebDriver driver, WebElement root) {
        this.root = root;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitUntilReady() {
        wait.until(d -> !root.findElements(TITLE).isEmpty());
        wait.until(d -> !root.findElements(PRICE).isEmpty());
    }

    public String getTitle() { return find(TITLE).getText().trim(); }

    public String getDescription() { return find(DESCRIPTION).getText().trim(); }

    public String getPriceText() { return find(PRICE).getText().trim(); }

    public double getPrice() { return parsePrice(getPriceText()); }

    public double getRating() {
        WebElement rating = find(RATING_BLOCK);
        for (WebElement span : rating.findElements(By.cssSelector("span"))) {
            OptionalDouble v = tryParseDouble(span.getText());
            if (v.isPresent()) return v.getAsDouble();
        }
        throw new NoSuchElementException("Could not parse rating number in this card");
    }

    public String getThumbnailUrl() { return find(THUMBNAIL_IMG).getAttribute("src"); }

    // --------- Images ----------
    public void toggleImages() { find(IMAGES_BUTTON).click(); }

    public boolean isImagesOpen() {
        Optional<WebElement> panel = findOptional(byImagesPanel());
        if (panel.isEmpty()) return false;

        // אם יש לפחות תמונה נוספת אחת, זה סימן שזה פתוח באמת
        return !root.findElements(By.cssSelector(
                "[data-testid^='product-additional-image-" + getCardIndex() + "-']"
        )).isEmpty();
    }


    public void waitImagesOpen() { wait.until(d -> isImagesOpen()); }

    public void waitImagesClosed() { wait.until(d -> !isImagesOpen()); }

    public List<String> getAdditionalImageUrls() {
        String idx = String.valueOf(getCardIndex());
        By imgs = By.cssSelector("[data-testid^='product-additional-image-" + idx + "-']");

        List<String> urls = new ArrayList<>();
        for (WebElement img : root.findElements(imgs)) {
            String src = img.getAttribute("src");
            if (src != null && !src.isBlank()) urls.add(src.trim());
        }
        return urls;
    }

    // --------- Reviews ----------
    public void toggleReviews() { find(REVIEWS_BUTTON).click(); }

    public boolean isReviewsOpen() {
        return findOptional(byReviewsPanel()).map(WebElement::isDisplayed).orElse(false);
    }

    public void waitReviewsOpen() { wait.until(d -> isReviewsOpen()); }

    public void waitReviewsClosed() { wait.until(d -> !isReviewsOpen()); }

    public List<ReviewSnapshot> getReviews() {
        String idx = String.valueOf(getCardIndex());
        By reviewCards = By.cssSelector("[data-testid^='product-review-" + idx + "-']");

        List<ReviewSnapshot> res = new ArrayList<>();
        for (WebElement reviewRoot : root.findElements(reviewCards)) {
            res.add(parseReview(reviewRoot));
        }
        return res;
    }

    public static class ReviewSnapshot {
        private final String author;
        private final int stars;
        private final String text;
        private final String dateText;

        public ReviewSnapshot(String author, int stars, String text, String dateText) {
            this.author = author;
            this.stars = stars;
            this.text = text;
            this.dateText = dateText;
        }

        public String getAuthor() { return author; }
        public int getStars() { return stars; }
        public String getText() { return text; }
        public String getDateText() { return dateText; }
    }

    private ReviewSnapshot parseReview(WebElement reviewRoot) {
        String author = "";
        int stars = 0;
        String text = "";
        String date = "";

        List<WebElement> spans = reviewRoot.findElements(By.cssSelector("span"));
        if (!spans.isEmpty()) author = spans.get(0).getText().trim();
        for (WebElement s : spans) {
            OptionalInt v = tryParseInt(s.getText());
            if (v.isPresent()) { stars = v.getAsInt(); break; }
        }

        List<WebElement> ps = reviewRoot.findElements(By.cssSelector("p"));
        if (!ps.isEmpty()) text = ps.get(0).getText().trim();
        if (ps.size() >= 2) date = ps.get(1).getText().trim();

        return new ReviewSnapshot(author, stars, text, date);
    }

    // --------- internal helpers ----------
    private By byImagesPanel() {
        return By.cssSelector("[data-testid='product-images-" + getCardIndex() + "']");
    }

    private By byReviewsPanel() {
        return By.cssSelector("[data-testid='product-reviews-" + getCardIndex() + "']");
    }

    private int getCardIndex() {
        String testId = root.getAttribute("data-testid"); // "product-card-0"
        if (testId == null) throw new IllegalStateException("Card root is missing data-testid");
        Matcher m = Pattern.compile("product-card-(\\d+)").matcher(testId);
        if (!m.find()) throw new IllegalStateException("Unexpected card data-testid: " + testId);
        return Integer.parseInt(m.group(1));
    }

    private WebElement find(By by) {
        wait.until(d -> !root.findElements(by).isEmpty());
        return root.findElement(by);
    }

    private Optional<WebElement> findOptional(By by) {
        List<WebElement> els = root.findElements(by);
        if (els.isEmpty()) return Optional.empty();
        return Optional.of(els.get(0));
    }

    private static double parsePrice(String priceText) {
        String cleaned = priceText.replaceAll("[^0-9.]", "");
        if (cleaned.isBlank()) throw new IllegalArgumentException("Cannot parse price: " + priceText);
        return Double.parseDouble(cleaned);
    }

    private static OptionalDouble tryParseDouble(String text) {
        if (text == null) return OptionalDouble.empty();
        String cleaned = text.trim().replaceAll("[^0-9.]", "");
        if (cleaned.isBlank()) return OptionalDouble.empty();
        try { return OptionalDouble.of(Double.parseDouble(cleaned)); }
        catch (NumberFormatException e) { return OptionalDouble.empty(); }
    }

    private static OptionalInt tryParseInt(String text) {
        if (text == null) return OptionalInt.empty();
        Matcher m = Pattern.compile("(\\d+)").matcher(text.trim());
        if (!m.find()) return OptionalInt.empty();
        try { return OptionalInt.of(Integer.parseInt(m.group(1))); }
        catch (NumberFormatException e) { return OptionalInt.empty(); }
    }
}
