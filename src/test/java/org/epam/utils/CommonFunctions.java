package org.epam.utils;



import org.epam.driver.SetUpSelenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonFunctions {
	
	public boolean isTextPresent(String strText)
	{
			String strXpath = "//*[text()[contains(.,'"+strText+"')]]";
			return (SetUpSelenium.getDriver().findElements(By.xpath(strXpath)).size()!=0);
	}
	
	public void waitForElementVisible(WebElement webElement,int timeout)
	{
		WebDriverWait wait = (new WebDriverWait( SetUpSelenium.getDriver(),timeout));
		wait.until(ExpectedConditions.elementToBeClickable(webElement));
	}
	

}
