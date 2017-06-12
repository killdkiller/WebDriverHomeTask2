package org.epam.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.epam.driver.SetUpSelenium;
import org.epam.utils.CommonFunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class FlightSearchPage {
	
	private String dynAirportSearch ="//*[starts-with(@class,'flight-search__panel__locations-container')]//*[contains(text(),'%s')]";
	private static final String DATE_BUTTON_LOCATOR = "//div[@class='calendar']//*[@data-pika-month='%s' and @data-pika-day='%s']";
	
	
	@FindBy(id ="search-departure-station")
	private WebElement txtOrigin;
	
	@FindBy(id = "search-arrival-station")
	private WebElement txtDestination;
	
	@FindBy(xpath = "//button[@data-test='flight-search-submit' and text()='Search']")
	private WebElement btnSearch;
	
	@FindBy(id = "search-passenger")
	private WebElement btnPassengers;
	
	@FindBy(xpath="//fieldset[contains(@class,'light-search__panel__fieldset--passengers')]//button[text()='OK']")
	private WebElement btnPassengersOK;
	
	@FindBy(xpath="//fieldset[contains(@class,'light-search__panel__fieldset--passengers')]//button[parent::div//*[contains(.,'child')] and contains(@class,'add')]")
	private WebElement btnChildAdd;
	
	@FindBy(xpath = "//div[@class='calendar']//button[text()='Next month']")
	private WebElement btnNextMonth;
	
	@FindBy(id = "search-departure-date")
	private WebElement departureDateEle;
	
	@FindBy(id = "search-return-date")
	private WebElement returnDateEle;
	
	@FindBy(xpath = "//div[@class='calendar']//button[text()='OK']")
	private WebElement btnCalendarOk;
	
	@FindBy(xpath = "//*[@class='calendar']//*[@class='pika-title']//*[@class='pika-label']")
	private WebElement lableYearAndMonths;
	
	@FindBys({
			@FindBy(xpath = "//*[@class='calendar']//*[@class='pika-title']//*[@class='pika-label']")})
	private List<WebElement> yearAndMontList;
		
	private CommonFunctions objCommonFunc = new CommonFunctions();
	private WebDriver driver;
	
	public FlightSearchPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public void setOrignAirport(String country,String airport)
	{
		objCommonFunc.waitForElementVisible(txtOrigin, SetUpSelenium.IMAXTIME);
		txtOrigin.clear();
		txtOrigin.sendKeys(country);
		driver.findElement(By.xpath(String.format(dynAirportSearch, airport))).click();;
	}
	
	public String getOriginAttribute(String attribute)
	{
		return txtOrigin.getAttribute(attribute);
	}
	
	public void setDestinationAirport(String country,String airport)
	{
		txtDestination.clear();
		txtDestination.sendKeys(country);
		driver.findElement(By.xpath(String.format(dynAirportSearch, airport))).click();
	}
	
	public void setDestinationCountry(String country)
	{
		txtDestination.clear();
		txtDestination.sendKeys(country);
	}
	
	
	public String getDestinationAttribute(String attribute)
	{
		return txtDestination.getAttribute(attribute);
	}
	public void setDeparturDate(Date date) throws InterruptedException
	{
		stubbornWait(departureDateEle, SetUpSelenium.IMAXTIME);
		departureDateEle.click();
		stubbornWait(lableYearAndMonths, SetUpSelenium.IMAXTIME);
		objCommonFunc.waitForElementVisible(lableYearAndMonths, SetUpSelenium.IMAXTIME);
		selectCalendarDate(date);
		
	}
	
	public void selectCalendarDate(Date date) throws InterruptedException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-YYYY");
		String strDate = sdf.format(date);
		String[] arrDate = strDate.split("-");
		selectCalendarYearAndMonth(arrDate[1],arrDate[2]);
				
		String day = arrDate[0];
		day =  (day.startsWith("0")?day.substring(1):day);
		sdf = new SimpleDateFormat("MM");
		String month = sdf.format(date);
		
		List<Object> args = new ArrayList<Object>();
		args.add(Integer.parseInt(month)-1);
		args.add(day);		
		
		Actions actions = new Actions(driver);
		stubbornWait(driver.findElement(By.xpath(String.format(DATE_BUTTON_LOCATOR,args.toArray()))), SetUpSelenium.IMAXTIME);
		objCommonFunc.waitForElementVisible(driver.findElement(By.xpath(String.format(DATE_BUTTON_LOCATOR,args.toArray()))), SetUpSelenium.IMAXTIME);
		actions.moveToElement(driver.findElement(By.xpath(String.format(DATE_BUTTON_LOCATOR,args.toArray())))).click();
		objCommonFunc.waitForElementVisible(btnCalendarOk, 20);			    
	    actions.moveToElement(btnCalendarOk).click().perform();
	}
	
	public void setReturnDate(Date date) throws InterruptedException
	{

		stubbornWait(returnDateEle, SetUpSelenium.IMAXTIME);
		returnDateEle.click();
		stubbornWait(lableYearAndMonths, SetUpSelenium.IMAXTIME);
		selectCalendarDate(date);
	}
	
	public void setPassengersData(int numOfChild)
	{
		objCommonFunc.waitForElementVisible(btnPassengers, SetUpSelenium.IMAXTIME);
		btnPassengers.click();
		for(int i=1;i<=numOfChild;i++)
		{
			objCommonFunc.waitForElementVisible(btnChildAdd, SetUpSelenium.IMAXTIME);
			btnChildAdd.click();
		}
		
//		objCommonFunc.waitForElementVisible(btnPassengersOK, SetUpSelenium.IMAXTIME);
//		Actions actions = new Actions(SetUpSelenium.getDriver());
//		actions.moveToElement(btnPassengersOK).click().perform();
		
	}
	
	public String getPassengerData()
	{
		return (btnPassengers.getText().trim());
	}
	
	public FlightResultsPage clickSearch()
	{
		btnSearch.click();
		return new FlightResultsPage(driver);
	}
	
	public void clickOnNextMonth()
	{
		btnNextMonth.click();
	}
	
	public void selectCalendarYearAndMonth(String month,String year) throws InterruptedException
	{
		for (int i = 0; i < 15; i++) 
		{
			if (!isCalendarForDateSelectionDisplayed(month, year)) 
			{
				stubbornWait(btnNextMonth, SetUpSelenium.IMAXTIME);
				Actions actions = new Actions(driver);
			    actions.moveToElement(btnNextMonth).click().perform();
				stubbornWait(btnNextMonth, SetUpSelenium.IMAXTIME);
			} else
				break;
		}
	}
	
	public boolean isCalendarForDateSelectionDisplayed(String month,String year) throws InterruptedException
	{
		List<String> displayedMonthAndYearLabels = new ArrayList<String>();
		Thread.sleep(500);
		stubbornWait(lableYearAndMonths, SetUpSelenium.IMAXTIME);
		objCommonFunc.waitForElementVisible(lableYearAndMonths, 30);
		Thread.sleep(500);
		for (WebElement label :yearAndMontList) {
			displayedMonthAndYearLabels.add(label.getText());
		}
		return displayedMonthAndYearLabels.contains(month.toUpperCase()) && displayedMonthAndYearLabels.contains(year);

	}
	
	
	public void stubbornWait(WebElement webElement,int timeout)
	{
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		
		Function<WebDriver,Boolean> function = new Function<WebDriver,Boolean>()
				{
					public Boolean apply(WebDriver t) {
						return (webElement.isEnabled() && webElement.isDisplayed());
					}
				};
				wait.until(function);
	}
	
	
}
