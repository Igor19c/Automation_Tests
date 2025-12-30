package data;

public final class ExpectedData {

	private ExpectedData() {
	}

	// משקולות לפי האפיון
	private static final double W1 = 1.0;
	private static final double W2 = 1.2;
	private static final double W3 = 1.3;
	private static final double W4 = 1.5;
	private static final double W5 = 1.0;
	private static final double W6 = 0.8;
	private static final double W7 = 1.1;
	private static final double W8 = 1.4;

	public static double score(ExperimentInput e) {
		return (e.getF1() * W1) + (e.getF2() * W2) + (e.getF3() * W3) + (e.getF4() * W4) + (e.getF5() * W5)
				+ (e.getF6() * W6) + (e.getF7() * W7) + (e.getF8() * W8);
	}

	public static double round2(double v) {
		return Math.round(v * 100.0) / 100.0;
	}

	public static String categoryByScore(double p) {
		if (p <= 16.4)
			return "Sunglasses";
		if (p <= 17.2)
			return "Kitchen Accessories";
		if (p <= 18.2)
			return "Home Decoration";
		if (p <= 18.7)
			return "Sports Accessories";
		if (p <= 19.8)
			return "Smartphones";
		return "Tablets";
	}

	public static String expectedCategory(ExperimentInput e) {
		return categoryByScore(score(e));
	}
}
