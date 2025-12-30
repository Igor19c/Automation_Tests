package tests.inputPageTests;

import org.junit.Test;
import pages.GiftFormPage;
import tests.BaseTest;

public class Submit_btn_disabled_until_all_fields_filled extends BaseTest {

	@Test
	public void testSubmitButtonDisabledUntilAllFieldsFilled() {

		GiftFormPage inputPage = openInputPage();

		checkFalse("Button should be disabled on page load", inputPage.isSubmitEnabled());

		inputPage.fill_1(1);
		checkFalse("Button should be disabled after 1 fill", inputPage.isSubmitEnabled());

		inputPage.fill_2(1);
		checkFalse("Button should be disabled after 2 fill", inputPage.isSubmitEnabled());

		inputPage.fill_3(1);
		checkFalse("Button should be disabled after 3 fill", inputPage.isSubmitEnabled());

		inputPage.fill_4(1);
		checkFalse("Button should be disabled after 4 fill", inputPage.isSubmitEnabled());

		inputPage.fill_5(1);
		checkFalse("Button should be disabled after 5 fill", inputPage.isSubmitEnabled());

		inputPage.fill_6(1);
		checkFalse("Button should be disabled after 6 fill", inputPage.isSubmitEnabled());

		inputPage.fill_7(1);
		checkFalse("Button should be disabled after 7 fill", inputPage.isSubmitEnabled());

		inputPage.fill_8(1);
		checkTrue("Button should be enabled after filling all 8 fields", inputPage.isSubmitEnabled());
	}

}
