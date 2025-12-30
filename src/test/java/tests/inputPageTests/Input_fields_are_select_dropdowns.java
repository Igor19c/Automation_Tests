package tests.inputPageTests;

import java.util.List;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import tests.BaseTest;

public class Input_fields_are_select_dropdowns extends BaseTest {

	@Test
	public void fields_shouldBeSelectDropdowns() {
		List<WebElement> dropdowns = openInputPage().getAllFields();

		for (WebElement el : dropdowns) {
			String id = el.getAttribute("id");
			checkTrue("Field " + id + " should be a SELECT dropdown", "select".equals(el.getTagName().toLowerCase()));
		}
	}
}
