package bp2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import common.functions.DriverFactory;
import common.functions.ReadExcel;
import common.functions.WebSupport;

public class TCGoBear {
	private WebDriver driver;
	WebSupport webSupport;
	public Logger logger = LogManager.getLogger("DEMO");

	@Test
	public void GoBear() throws Exception {
		driver = DriverFactory.createDriver();
		webSupport = new WebSupport(driver);
		
		ReadExcel ex = new ReadExcel("DEMO1");
		String URL = ex.getConfigData("URL");
		webSupport.navigateToURL(URL);
		
		// Select Tab
		webSupport.clickOnElement("//a[@href='#Insurance']");
		webSupport.clickOnElement("//a[@href='#Travel']");

		// Select trip type
		webSupport.clickOnElement("//div[contains(text(),'looking for')]//button");
		String tripType = ex.getConfigData("TripType");
		webSupport.clickOnElement("//span[contains(text(), '" + tripType + "')]//following::link");

		// Select traveler
		webSupport.clickOnElement("//div[@data-gb-name='traveller']//button");
		String travelPlan = ex.getConfigData("TravelPlan");
		webSupport.clickOnElement("//span[contains(text(), '" + travelPlan + "')]//following::link");

		// Select destination
		webSupport.clickOnElement("//div[contains(text(),'going to')]//button");
		String destination = ex.getConfigData("Destination");
		webSupport.clickOnElement("//span[contains(text(), '" + destination + "')]//following::link");

		// Choose date
		String dateFrom = ex.getConfigData("DateFrom");
		String dateTo = ex.getConfigData("DateTo");
		webSupport.clickOnElement("//div[@class='date-from']");
		webSupport.clickOnElement("//tbody//td[(text()='" + dateFrom + "' and @class!='old disabled day') and (text()='"
				+ dateFrom + "' and @class!='new day') and (text()='" + dateFrom + "' and @class!='old day')]");
		webSupport.clickOnElement("//div[@class='date-to']");
		webSupport.clickOnElement("//tbody//td[(text()='" + dateTo + "' and @class!='old disabled day') and (text()='"
				+ dateTo + "' and @class!='new day') and (text()='" + dateTo + "' and @class!='old day')]");

		// Click Search
		webSupport.clickOnElement("//button[contains(text(),'Show my results')]");

		// Filter - Insurers
		String insurer = ex.getConfigData("Insurer");
		webSupport.clickOnElement("//label[contains(text(),'" + insurer + "')]");

		// Expand See more
		webSupport.clickOnElement("//a[contains(text(),'SEE MORE')]//link");

		// Move slider (to be improve)
		String slider = ex.getConfigData("SliderName");
		webSupport.dragAndDropElementByOffset(
				"//label[contains(text(),'" + slider + "')]//following-sibling::div//div[@role='slider']", 50, 0);

		// Scroll down
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");

		// Sort Insurers
		String sortBy = ex.getConfigData("SortBy");
		webSupport.clickOnElement("//input//following::label[contains(text(),'" + sortBy + "')]");

		// Details - Destination
		webSupport.clickOnElement("//div[label[contains(text(),'DESTINATION')]]//following::button");
		String country = ex.getConfigData("Country");
		webSupport.clickOnElement("//span[contains(text(), '" + country + "')]//following::link");

		// Details - Calendar
		String detailDateFrom = ex.getConfigData("DetailDateFrom");
		String detailDateTo = ex.getConfigData("DetailDateTo");
		webSupport.clickOnElement("//div[@class='date-from']");
		webSupport.clickOnElement("//tbody//td[(text()='" + detailDateFrom
				+ "' and @class!='old disabled day') and (text()='" + detailDateFrom
				+ "' and @class!='new day') and (text()='" + detailDateFrom + "' and @class!='old day')]");
		webSupport.clickOnElement("//div[@class='date-to']");
		webSupport.clickOnElement("//tbody//td[(text()='" + detailDateTo
				+ "' and @class!='old disabled day') and (text()='" + detailDateTo
				+ "' and @class!='new day') and (text()='" + detailDateTo + "' and @class!='old day')]");

		Thread.sleep(5000);
		driver.quit();
	}

}
