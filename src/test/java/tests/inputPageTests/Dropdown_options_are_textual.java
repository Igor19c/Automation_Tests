package tests.inputPageTests;

import java.util.List;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import tests.BaseTest;

public class Dropdown_options_are_textual extends BaseTest {

	@Test
	public void dropdown_optionsShouldBeTextual() {
		List<WebElement> dropdowns = openInputPage().getAllFields();

		for (WebElement dropdown : dropdowns) {
			String id = dropdown.getAttribute("id");
			Select select = new Select(dropdown);
			List<WebElement> options = select.getOptions();

			for (WebElement option : options) {
				String optionText = option.getText().trim();
				if (optionText.isEmpty())
					continue;
				if (option.getAttribute("value") != null && option.getAttribute("value").trim().isEmpty())
					continue;

				boolean isNumeric = optionText.matches("-?\\d+(\\.\\d+)?");
				checkTrue("Dropdown " + id + " option should be textual. Actual='" + optionText + "'", !isNumeric);
			}
		}
	}

}
