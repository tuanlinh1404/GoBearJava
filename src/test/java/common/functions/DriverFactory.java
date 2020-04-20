package common.functions;

import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

	public static WebDriver createDriver() throws Exception {
		//-- Change special loaction path
		String downloadFilepath = "C:\\downloadFile";
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		DesiredCapabilities cap =  new DesiredCapabilities();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		
		ReadExcel ex = new ReadExcel();
		String remote = ex.getConfigData("Remote");
		String version = ex.getConfigData("Version");
		String seleniumHub = ex.getConfigData("Selenium Hub");
		String browser = ex.getConfigData("Browser");
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		WebDriver driver;
		if (remote.toLowerCase().equals("false")){
			switch (browser) {
			case "chrome":
				driver = new ChromeDriver(cap);
				break;
			case "ie":
				driver = new InternetExplorerDriver(cap);
				break;
			default:
				driver = new FirefoxDriver(cap);
				break;
			}
		}
		else {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			URL SeleniumGridURL = new URL(seleniumHub);
	        capabilities.setBrowserName(browser);
	        capabilities.setPlatform(Platform.WINDOWS);
	        capabilities.setVersion(version);
	        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
	        driver = new RemoteWebDriver(SeleniumGridURL, capabilities);	
		}

		return driver;

	}

}
