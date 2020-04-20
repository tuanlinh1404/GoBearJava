package common.functions;

import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebSupport {
	
	WebDriver driver;
	WebDriverWait wait;
	Actions act;
	WebSupport webSupport;
	public Logger logger = LogManager.getLogger("DEMO");
	
	public WebSupport(WebDriver driver123) {
		this.driver = driver123;
		this.wait = new WebDriverWait(this.driver, 30);
		this.act = new Actions(driver123);
	}
	
	public void waitForLoading() {
		String spin = "//div[@id='system-loader']";
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(spin)));
	}
	public void waitForPopUp() {
		String popup ="//*[@id='toast-container']/descendant::span";
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(popup)));
	}
	public void clickOnElement(String xpath) {
		WebElement elm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		act.moveToElement(elm).build().perform();
		elm.click();
	}
	
	public void switchToPopUp() {
		String subWindowHandler = null;
		Set<String> handles = driver.getWindowHandles(); // get all window handles
		 java.util.Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()){
		    subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler); // switch to popup window
	}
	
	public void navigateToURL(String URL) throws Exception{
		logger.info("GET URL --- " + URL);
		driver.get(URL);
		driver.manage().window().maximize();
		logger.info("GO TO WEBSITE");
	}	
	
	public void clickOnEnter(String xpath) {
		WebElement elm = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		act.moveToElement(elm).build().perform();
		elm.sendKeys(Keys.ENTER);;
	}
	
	public void sendKeysToElement(String xpath, String keys) {
		WebElement elm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		act.moveToElement(elm).build().perform();
		//elm.clear();
		elm.sendKeys(keys);
	}
	public void sendKeysToElementToEdit(String xpath, String keys) {
		WebElement elm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		act.moveToElement(elm).build().perform();
		elm.clear();
		elm.sendKeys(keys);
	}
	
	public String GetText(String xpath) {
		WebElement elm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		act.moveToElement(elm).build().perform();
		return elm.getText();
	}
	
	public Boolean verifyElement(String xpath) {
		Boolean result = false;
		try {
			WebElement elm = driver.findElement(By.xpath(xpath));
			act.moveToElement(elm).build().perform();
			Thread.sleep(1000);
			result = true;
		} catch (Exception e) {
			result = false;
		}
		return result;
	}

	public void dragAndDropElementByOffset(String xpath, int xOffset, int yOffset) {
		WebElement elm =  driver.findElement(By.xpath(xpath));
		act.moveToElement(elm).build().perform();
		act.dragAndDropBy(elm, xOffset, yOffset).build().perform();
	}
	
	public void scrollByPixel(int x, int y) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy("+ x + "," + y + ")");
	}
}
