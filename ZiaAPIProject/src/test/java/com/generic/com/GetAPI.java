package com.generic.com;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetAPI {
	
	public Response getBody(String url) {
		Response response = RestAssured.get(url);
		response.getBody().prettyPrint();
		return response;
	}
	
	public void checkAttributes(Response response) {
		//This is one way to validate data from Json
		if(response.body().asString().contains("coor")) {
			Assert.assertTrue(response.body().asString().contains("coord"));
			System.out.println("Body Contains coord");
		} else {
			System.out.println("Body does not contain coord");
		}
		//This is another way by using jsonpath to extract/validate data
		JsonPath jpath = response.jsonPath();
		
	//	Assert.assertTrue(jsonPathEvaluator.get("coord").toString() != null);
		Assert.assertFalse(jpath.get("coord").toString().replace("[","").replace("]", "").equalsIgnoreCase("null"));
		Assert.assertFalse(jpath.get("weather").toString().replace("[","").replace("]", "").equalsIgnoreCase("null"));
		Assert.assertFalse(jpath.get("main").toString().replace("[","").replace("]", "").equalsIgnoreCase("null"));
		Assert.assertFalse(jpath.get("visibility").toString().replace("[","").replace("]", "").equalsIgnoreCase("null"));
		Assert.assertFalse(jpath.get("wind").toString().replace("[","").replace("]", "").equalsIgnoreCase("null"));
		Assert.assertFalse(jpath.get("clouds").toString().replace("[","").replace("]", "").equalsIgnoreCase("null"));
		Assert.assertFalse(jpath.get("sys").toString().replace("[","").replace("]", "").equalsIgnoreCase("null"));
		
	}
	public void verifyData(Response response, int value) {		
		JsonPath jpath = response.jsonPath();
		System.out.println("Get: Verify Data Actual:Expected: "+jpath.get("id").toString()+":"+value);
		Assert.assertEquals(Integer.parseInt(jpath.get("id").toString().replace("[","").replace("]", "")),value);

	}
	
	public void verifyInvalidData(Response response, int value) {		
		JsonPath jpath = response.jsonPath();		
		System.out.println("Get: Verify Invalid Data Actual:Expected: "+jpath.get("id").toString()+":"+value);
		Assert.assertNotEquals(Integer.parseInt(jpath.get("id").toString().replace("[","").replace("]", "")),value);
	}
	public void verifyContent(Response response) {
		System.out.println("Get: Verify Content Type Actual:Expected: "+response.getContentType()+":application/json; charset=utf-8");		
		Assert.assertEquals(response.getContentType().toString(),"application/json; charset=utf-8");		
	}
	public void verifyUSA(Response response) {		
		JsonPath jpath = response.jsonPath();
	//	System.out.println(jpath.get("sys.country").toString());
		System.out.println("Get: Verify USA Actual:Expected: "+jpath.get("sys.country").toString()+":USA");
		Assert.assertEquals(jpath.get("sys.country").toString().replace("[","").replace("]", ""), "USA");		
	}
	public void verifyBdesh(Response response) {		
		JsonPath jpath = response.jsonPath();
		System.out.println(jpath.get("sys.country").toString());
		System.out.println("Get: Verify BDESH Actual:Expected: "+jpath.get("sys.country").toString()+":BANGLADESH");
		Assert.assertNotEquals(jpath.get("sys.country").toString().replace("[","").replace("]", ""), "BANGLADESH");		
	}
	public void verifyNY(Response response) {
		JsonPath jpath = response.jsonPath();
	//	System.out.println(jpath.get("City").toString());
		System.out.println("Get: Verify NY Actual:Expected: "+jpath.get("City").toString()+":New York");
		Assert.assertEquals(jpath.get("City").toString().replace("[","").replace("]", ""), "New York");
	}
	public void verifyLasVegas(Response response) {
		JsonPath jpath = response.jsonPath();
	//	System.out.println(jpath.get("City").toString());
		System.out.println("Get: Verify NY Actual:Expected: "+jpath.get("City").toString()+":Las Vegas");
		Assert.assertNotEquals(jpath.get("City").toString().replace("[","").replace("]", ""), "Las Vegas");
	}
}
