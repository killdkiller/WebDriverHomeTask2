package org.epam.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.epam.driver.BaseTest;
import org.epam.utils.DataUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckFlightsTest extends BaseTest{
	
	private static final String PAGE_TITLE = "Official Wizz Air website | Book direct for the cheapest prices";
	private static final String ORIGIN_COUNTRY = "Belgium";
	private static final String ORIGIN_AIRPORT = "Brussels Charleroi";
	private static final String WRONG_DESTINATION_COUNTRY = "Germany";
	private static final String DESTINATION_COUNTRY = "Hungary";
	private static final String DESTINATION_AIRPORT = "Budapest";
	private static final String DYNAIRPORTSEARCH ="//*[starts-with(@class,'flight-search__panel__locations-container')]//*[contains(text(),'%s')]";
	private static final String NO_RESULT_COMMENTS = "There is no result for this search.";
	private static final String EXP_PASSENGER_DATA = "1 adult 2 children";
	private static final String FLIGHT_RESULT_PAGE = "Select flights";
	private static final int I_NUM_OF_CHILD = 2;
	
	
	private Date departureDate = null;
	private Date returnDate = null;

	@Test
	public void validateFlightHomaePageTest()
	{
		
		String appTitle = driver.getTitle();
		Assert.assertEquals(PAGE_TITLE, appTitle,"Flight Home Page Title is not matching ");
		System.out.println("Title is Matching");
	}
	
	@Test(dependsOnMethods={"validateFlightHomaePageTest"})
	public void selectOriginAirportTest()
	{
		objFlightSearchPage.setOrignAirport(ORIGIN_COUNTRY,ORIGIN_AIRPORT);
		String appOriginAirport = objFlightSearchPage.getOriginAttribute("value");
		Assert.assertEquals(ORIGIN_AIRPORT, appOriginAirport,"Origin Airport value is not entered properly ");
		System.out.println("OriginAirport Value is selected and validated");
	}
	
	@Test(dependsOnMethods={"selectOriginAirportTest"})
	public void selectDestinationAirportTest()
	{
		objFlightSearchPage.setDestinationCountry(WRONG_DESTINATION_COUNTRY);
		boolean isDisplayed = driver.findElement(By.xpath(String.format(DYNAIRPORTSEARCH, NO_RESULT_COMMENTS))).isDisplayed();
		Assert.assertTrue(isDisplayed, "Empty Search search result is not displayed");
		System.out.println("Wrong Destination validated");
		
		objFlightSearchPage.setDestinationAirport(DESTINATION_COUNTRY,DESTINATION_AIRPORT);
		String appDestinationAirport = objFlightSearchPage.getDestinationAttribute("value");
		Assert.assertEquals(DESTINATION_AIRPORT, appDestinationAirport,"Destinatio Airport value is not entered properly ");
		System.out.println("DestinationAirport Value is selected and validated");
	}
	
	@Test(dependsOnMethods={"selectDestinationAirportTest"})
	public void selectDepartureDate()
	{
		try
		{
			int iMaxRandomDays = 180;
			departureDate = DataUtil.giveRandomDate(iMaxRandomDays);
			System.out.println("DepartureDate: "+departureDate);
			objFlightSearchPage.setDeparturDate(departureDate);
			System.out.println("Departure Date Value is selected sucessfully");
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		

	}
	
	@Test(dependsOnMethods={"selectDepartureDate"})
	public void selectArrivalDate()
	{
		try
		{	
			int iNumOfDaysOfTrip =3;
			Calendar cal = Calendar.getInstance();
			cal.setTime(departureDate);		 		 		 
			cal.add(Calendar.DAY_OF_MONTH, iNumOfDaysOfTrip);
			returnDate = cal.getTime();
			System.out.println("ReturnDate: "+returnDate);
			objFlightSearchPage.setReturnDate(returnDate);
			System.out.println("Return Date Value is selected sucessfully");
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	@Test(dependsOnMethods={"selectArrivalDate"})
	public void selectPassengers()
	{
		objFlightSearchPage.setPassengersData(I_NUM_OF_CHILD);
		String appPassenger = objFlightSearchPage.getPassengerData();
		Assert.assertEquals(EXP_PASSENGER_DATA, appPassenger,"Passengers data is not filled properly");
		System.out.println("Passenger values are selected and validated sucessfully");
	}
	
	@Test(dependsOnMethods={"selectPassengers"})
	public void validateSearchResults()
	{
		objFlightResultsPage = objFlightSearchPage.clickSearch();
		Assert.assertTrue(objFlightResultsPage.isTextPresent(FLIGHT_RESULT_PAGE),"Flight Results Page is not prsent");
		System.out.println("validated Flights results page");
		
		String appDepartureDate = objFlightResultsPage.getDepartureDate();
		String appReturnDate = objFlightResultsPage.getReturnDate();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE dd, MMM");
		String strDepartureDate = sdf.format(departureDate);
		Assert.assertEquals(strDepartureDate.toLowerCase(), appDepartureDate.toLowerCase(),"Departure date is not entered properly");
		String strReturnDate = sdf.format(returnDate);
		Assert.assertEquals(strReturnDate.toLowerCase(), appReturnDate.toLowerCase(),"Return date is not entered properly");
		System.out.println("validated Flights results Dates");
	}
	
	@Test(dependsOnMethods={"validateSearchResults"})
	public void printFlightDetails()
	{
		objFlightResultsPage.printFlightDepartureDetails();
	}
	

}
