package bp2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
		
		//1.Select Tab
		webSupport.clickOnMenu("Insurance");
		webSupport.clickOnMenu("Travel");

		//2.Select trip type
		String tripType = ex.getConfigData("TripType");
		webSupport.selectTripType(tripType);

		//3.Select traveler
		String travelPlan = ex.getConfigData("TravelPlan");
		webSupport.selectTravelPlan(travelPlan);

		//4.Select destination
		String destination = ex.getConfigData("Destination");
		webSupport.selectDestination(destination);

		//5.Choose travel date
		String dateFrom = ex.getConfigData("DateFrom");
		String dateTo = ex.getConfigData("DateTo");
		webSupport.selectTravelDate(dateFrom, dateTo);

		//6.Click Search
		webSupport.clickOnElement("//button[contains(text(),'Show my results')]");

		//7.Filter - Insurers
		String insurer = ex.getConfigData("Insurer");
		webSupport.selectInsurers(insurer);

		//8.Expand See more
		webSupport.clickOnElement("//a[contains(text(),'SEE MORE')]//link");

		//9.Move slider (to be improve)
		String slider = ex.getConfigData("SliderName");
		webSupport.moveSliderByOffset(slider, 50, 0);

		//10.Scroll down
		Thread.sleep(3000);
		webSupport.scrollByPixel(0, 500);

		//11.Sort Insurers
		String sortBy = ex.getConfigData("SortBy");
		webSupport.selectSortByOption(sortBy);

		//12.Details - Destination
		String country = ex.getConfigData("Country");
		webSupport.selectDetailsDestination(country);

		//13.Details - Calendar
		String detailDateFrom = ex.getConfigData("DetailDateFrom");
		String detailDateTo = ex.getConfigData("DetailDateTo");
		webSupport.selectTravelDate(detailDateFrom, detailDateTo);

		//14.Wait and close browser
		Thread.sleep(3000);
		driver.quit();
	}

}
