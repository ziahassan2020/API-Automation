package com.generic.com;

import org.testng.Assert;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UpdateAPI {
	
	public Response updatedata(String url) {
	//	RequestSpecification request = RestAssured.given();			
	//	request.header("Content-type", "application/json");			
	//	request.body(json.toString());		
	//	Response response = request.put("http://localhost:3000/posts/99");
		
		JsonObject object = new JsonObject();
		JsonObject coord = new JsonObject();  // inner object
		coord.addProperty("lon", -122.08);
		coord.addProperty("lat", 37.39);
		object.add("coord", coord);
		
		JsonArray weather = new JsonArray();
		JsonObject weatherItem = new JsonObject();
		weatherItem.addProperty("id", 99);
		weatherItem.addProperty("main","Clear");
		weatherItem.addProperty("description","clear sky");
		weatherItem.addProperty("icon", "01d");		
		weather.add(weatherItem);
		object.add("weather", weather);
		
		JsonObject main = new JsonObject();
		main.addProperty("temp", 296.71);
		main.addProperty("pressure", 1013);
		main.addProperty("humidity", 53);
		main.addProperty("temp_min", 294.82);
		main.addProperty("temp_max", 298.71);
		object.add("main", main);
		
		object.addProperty("visibility", 16093);
		
		JsonObject wind = new JsonObject();
		wind.addProperty("speed",1.5);
		wind.addProperty("deg", 350);
		object.add("wind", wind);
		
		JsonObject clouds = new JsonObject();
		clouds.addProperty("all", 1);
		object.add("clouds", clouds);
		
		JsonObject date = new JsonObject();
		date.addProperty("dt", 1560350645);
		object.add("dt", date);
		
		JsonObject sys = new JsonObject();
		sys.addProperty("type", 1);
		sys.addProperty("id", 5122);
		sys.addProperty("message", 0.0139);
		sys.addProperty("country", "USA");
		sys.addProperty("sunrise", 1560343627);
		sys.addProperty("sunset", 1560396563);
		object.add("sys",sys);
		
		object.addProperty("timezone","Eastern");
		object.addProperty("id", 99);
		object.addProperty("City", "Arizona");
		object.addProperty("cod", 200);		
		
		RequestSpecification request = RestAssured.given();
		
		request.header("Content-type", "application/json");
		request.body(object.toString());
		
		Response response = request.put(url);	
		return response;
		
	}
	public void validateUpdateStatus(Response response, int statusCode) {
		//System.out.println("HTTP Status code = " + response.getStatusCode());
		//System.out.println("HTTP Status Line = " + response.getStatusLine());
		System.out.println("Update validate status Actual:Expected: "+response.getStatusCode()+":"+statusCode);
		Assert.assertEquals(response.getStatusCode(), statusCode);
	}

}
