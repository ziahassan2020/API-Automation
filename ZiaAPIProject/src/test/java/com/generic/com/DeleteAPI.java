package com.generic.com;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteAPI {
	
	public Response deleteData(String url) {
		Response response = RestAssured.delete(url);
		return response;
	}
	public void validateStatusCode(Response response, int statusCode) {
	//	System.out.println("Delete Status Code: "+response.getStatusCode());
		System.out.println("Delete Response Actual:Expected: "+response.getStatusCode()+":"+statusCode);
		Assert.assertEquals(response.getStatusCode(), statusCode);
	}
}
