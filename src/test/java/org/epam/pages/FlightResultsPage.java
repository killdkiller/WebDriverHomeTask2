package org.epam.pages;

import java.util.List;

import org.epam.utils.CommonFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class FlightResultsPage {
	
	@FindBy(xpath = "//div[@id='fare-selector-outbound']//label[contains(@class,'flight-select__chart__day__label--selected')]//time")
	private WebElement departureLabel;
	
	@FindBy(xpath = "//div[@id='fare-selector-return']//label[contains(@class,'flight-select__chart__day__label--selected')]//time")
	private WebElement returnLabel;
	
	@FindBys({
		@FindBy(xpath = "//div[@id='fare-selector-outbound']//tbody[@class='booking-flow__prices-table__content']")
	})
	private List<WebElement> departureFlightSDetails;
	
	private static final String DYN_DEPFLIGHT_TIMINGS = "(//div[@id='fare-selector-outbound']//tbody[@class='booking-flow__prices-table__content']//td[contains(@class,'prices-table__content__column--time')])[%d]";	
	
	private WebDriver driver;
	private CommonFunctions objCommonFunc = new CommonFunctions();
	
	public FlightResultsPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public boolean isTextPresent(String strText)
	{
		objCommonFunc.waitForElementVisible(departureLabel, 30);
		return objCommonFunc.isTextPresent(strText);
	}
	
	public String getDepartureDate()
	{
		return departureLabel.getText().trim();
	}
	
	public String getReturnDate()
	{
		return returnLabel.getText().trim();
	}
	
	public void printFlightDepartureDetails()
	{
		int NumofDepartureFlights = departureFlightSDetails.size();
		System.out.println("Num Of Departure Flights:"+NumofDepartureFlights);
		for(int i=1;i<=NumofDepartureFlights;i++)
		{
			System.out.print("Departure Flight"+i+" :");
			System.out.println(driver.findElement(By.xpath(String.format(DYN_DEPFLIGHT_TIMINGS, i))).getText().trim());
			
		}
	}
	
}
