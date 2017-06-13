package com.epam.test;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.model.User;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserTest {
	private final static int I_RESPONSE_CODE =200;
	private final static String CONTENT_TYPE_VALUE = "application/json; charset=utf-8";
	private final static String CONTENT_TYPE_HEADER = "content-type";
	private final static int I_NUM_OF_USERS =10;
	
	@BeforeClass
	public void initTest()
	{
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
	}
	
	@Test
	public void validateStatusCode()
	{
		Response response = RestAssured.get("/users");
		System.out.println(response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), I_RESPONSE_CODE,"The Response Code for the reques is not 200");
	}
	
	@Test(priority=2)
	public void validateResponseHeader()
	{
		Response response = RestAssured.get("/users");
		Assert.assertTrue(response.getHeaders().hasHeaderWithName(CONTENT_TYPE_HEADER),"The 'content-type' Header is not present in the reponse body");
		Assert.assertEquals(response.getHeader(CONTENT_TYPE_HEADER), CONTENT_TYPE_VALUE,"The 'content-type' Header value is not matching");
		System.out.println(response.getHeader(CONTENT_TYPE_HEADER));
		
	}
	
	@Test(priority=3)
	public void validateResponseBody()
	{
		Response response = RestAssured.get("/users");
		User[] users = response.as(User[].class);
		Assert.assertEquals(users.length, I_NUM_OF_USERS, "Number of users is not matching");
		System.out.println(users.length);
	}
}
