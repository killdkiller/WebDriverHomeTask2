package org.epam.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DataUtil {
	
	public static Date giveRandomDate(int iMaxNumOfDays)
	{
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		Random rand = new Random();
		int numOfDays = rand.nextInt(iMaxNumOfDays)+1;
		calendar.add(Calendar.DAY_OF_MONTH, numOfDays);
		return calendar.getTime();
	}
	
	

}
