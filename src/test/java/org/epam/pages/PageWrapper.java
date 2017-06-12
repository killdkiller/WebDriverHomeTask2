package org.epam.pages;

import org.epam.driver.SetUpSelenium;
import org.openqa.selenium.support.PageFactory;

public class PageWrapper {
	
	public FlightSearchPage getFlightSearchPage()
	{
		return PageFactory.initElements(SetUpSelenium.getDriver(), FlightSearchPage.class);
	}
	
	public FlightResultsPage getFlightResultsPage()
	{
		return PageFactory.initElements(SetUpSelenium.getDriver(), FlightResultsPage.class);
	}

}
