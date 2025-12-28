package data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonExperimentLoader {

	public static List<ExperimentInput> load(String resourcePath) {
		try (java.io.InputStream is = JsonExperimentLoader.class.getResourceAsStream(resourcePath)) {
			if (is == null)
				throw new IllegalArgumentException("Resource not found: " + resourcePath);

			JSONArray arr = (JSONArray) new JSONParser().parse(new InputStreamReader(is, StandardCharsets.UTF_8));
			List<ExperimentInput> out = new ArrayList<>();

			for (Object o : arr) {
				JSONObject jo = (JSONObject) o;

				int exp = ((Long) jo.get("experimentNumber")).intValue();
				int f1 = ((Long) jo.get("f1")).intValue();
				int f2 = ((Long) jo.get("f2")).intValue();
				int f3 = ((Long) jo.get("f3")).intValue();
				int f4 = ((Long) jo.get("f4")).intValue();
				int f5 = ((Long) jo.get("f5")).intValue();
				int f6 = ((Long) jo.get("f6")).intValue();
				int f7 = ((Long) jo.get("f7")).intValue();
				int f8 = ((Long) jo.get("f8")).intValue();

				out.add(new ExperimentInput(exp, f1, f2, f3, f4, f5, f6, f7, f8));
			}

			return out;
		} catch (Exception e) {
			throw new RuntimeException("Failed to load JSON " + resourcePath, e);
		}
	}
}
