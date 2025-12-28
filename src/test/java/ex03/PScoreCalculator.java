package ex03;

public class PScoreCalculator {

	public static double p(data.ExperimentInput c) {
		return 1.0 * c.f1 + 1.2 * c.f2 + 1.3 * c.f3 + 1.5 * c.f4 + 1.0 * c.f5 + 0.8 * c.f6 + 1.1 * c.f7 + 1.4 * c.f8;
	}

	public static String categoryByP(double p) {
		if (p <= 16.4)
			return "sunglasses";
		if (p <= 17.2)
			return "kitchen-accessories";
		if (p <= 18.2)
			return "home-decoration";
		if (p <= 18.7)
			return "sports-accessories";
		if (p <= 19.8)
			return "smartphones";
		return "tablets";
	}
}
