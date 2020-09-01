package com.base.test;

import org.testng.Assert;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherGetAPI {

	public static void main(String[] args) {
		WeatherGetAPI get = new WeatherGetAPI();
		get.addData(); // Create json in Server
		Response response = get.getBody();
		get.checkAttributes(response);
		get.verifyData(response);
		get.verifyContent(response);
		get.updatedata();
		get.deleteData();
	}
	public void addData() {
		
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
		object.addProperty("City", "New York");
		object.addProperty("cod", 200);
		
		
		RequestSpecification request = RestAssured.given();
		
		request.header("Content-type", "application/json");
		request.body(object.toString());
		
		Response response = request.post("http://localhost:3000/posts");
		
		// HTTP 3 parts = Body(data), Header(server or application information) , status
		// line(status = OK)
		response.body().prettyPrint();
		System.out.println("");
		System.out.println("HTTP Header = " + response.getHeaders());
		System.out.println("");
		System.out.println("HTTP status line or request line =" + response.getStatusLine());
		
		System.out.println("");
		System.out.println("HTTP Status code = " + response.getStatusCode());
		System.out.println("Time  = " + response.getTime() + " ms");
		
		System.out.println("Application type /server type /language =>> json or xml =" + response.getContentType());
		
		// Validation of API testing for status code

		if (response.getStatusCode() == 201) {
			Assert.assertEquals(response.getStatusCode(), 201, "Test passed as status code found 201");
			System.out.println("My Test passed");
		} else {
			Assert.assertEquals(response.getStatusCode(), 201, "Test Failed as status code not found 201");
			System.out.println("My Test Failed");
		}
		// Validation of API testing for status line = Created text
		if (response.getStatusLine().contains("Created")) {
			Assert.assertTrue(response.getStatusLine().contains("Created"), "Test passed");
			System.out.println("Test passed");
		} else {
			Assert.assertTrue(response.getStatusLine().contains("Created"), "Test Failed");
			System.out.println("Test failed");
		}
		
	}

	public Response getBody() {
		Response response = RestAssured.get("http://localhost:3000/posts/99");
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
	
	public void verifyData(Response response) {
		
		JsonPath jpath = response.jsonPath();
		System.out.println(jpath.get("id").toString());
		Assert.assertEquals(jpath.get("id").toString().replace("[","").replace("]", ""),"99");
		System.out.println(jpath.get("sys.country").toString());		
		Assert.assertEquals(jpath.get("sys.country").toString().replace("[","").replace("]", ""), "USA");
		Assert.assertNotEquals(jpath.get("sys.country").toString().replace("[","").replace("]", ""), "UK");
		Assert.assertNotEquals(jpath.get("id").toString().replace("[","").replace("]", ""), "22");
		Assert.assertEquals(jpath.get("City").toString().replace("[","").replace("]", ""), "New York");
	}
	public void verifyContent(Response response) {
		System.out.println("Content Type: "+response.getContentType());
		System.out.println("Status Code: "+response.getStatusCode());
		System.out.println("Status Line: "+response.getStatusLine());
		Assert.assertEquals(response.getContentType().toString(),"application/json; charset=utf-8");
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getStatusLine().toString(), "HTTP/1.1 200 OK");
		
	}
	
	public void updatedata() {
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
		object.addProperty("City", "Karachi");
		object.addProperty("cod", 200);		
		
		RequestSpecification request = RestAssured.given();
		
		request.header("Content-type", "application/json");
		request.body(object.toString());
		
		Response response = request.put("http://localhost:3000/posts/99");	
		System.out.println("HTTP Status code = " + response.getStatusCode());
		System.out.println("HTTP Status Line = " + response.getStatusLine());
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	public void deleteData() {
		Response response = RestAssured.delete("http://localhost:3000/posts/99");
		System.out.println("Delete Status Code: "+response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
