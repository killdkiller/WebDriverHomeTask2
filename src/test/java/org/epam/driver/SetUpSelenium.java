package org.epam.driver;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class SetUpSelenium {
	
	private static WebDriver driver ;
	public static final int IMINTIME = 10;
	public static final int IMAXTIME = 30;
	public static final int IMIDTIME = 20;
	
	public static void initializeDriver(String browserName)
	{
		if(browserName.equalsIgnoreCase("IE"))
		{
			System.setProperty("webdriver.ie.driver", "Resources\\IEDriverServer32Bit.exe");
			driver = new InternetExplorerDriver();
		}
		
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "Resources\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "Resources\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else {
			System.setProperty("webdriver.gecko.driver", "Resources\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
	}
	
	public static WebDriver getDriver()
	{
		return driver;
	}
	
	public static void tearUp()
	{
		driver.close();
		driver.quit();
	}
	
	public static void goToUrl(String url)
	{
		driver.get(url);
		driver.manage().window().maximize();
	}

}
