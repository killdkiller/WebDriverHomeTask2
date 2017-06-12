package org.epam.driver;

import java.util.concurrent.TimeUnit;

import org.epam.pages.FlightResultsPage;
import org.epam.pages.FlightSearchPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {
	
	protected WebDriver driver;
	protected FlightSearchPage objFlightSearchPage;
	protected FlightResultsPage objFlightResultsPage;
	
	@BeforeClass
	@Parameters({"browserName","url"})
	public void setUp(String browserName,String url)
	{
		SetUpSelenium.initializeDriver(browserName);
		driver = SetUpSelenium.getDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		SetUpSelenium.goToUrl(url);
		objFlightSearchPage = new FlightSearchPage(driver);
	
	}
	
	@AfterClass
	public void closeDriver()
	{
		SetUpSelenium.tearUp();
	}

}
