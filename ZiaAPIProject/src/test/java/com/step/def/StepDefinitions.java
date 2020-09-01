package com.step.def;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import com.generic.com.DeleteAPI;
import com.generic.com.GetAPI;
import com.generic.com.PostAPI;
import com.generic.com.UpdateAPI;
import com.util.ScreenShot;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

public class StepDefinitions {
	
	Response postResponse, getResponse, updateResponse, deleteResponse;
	PostAPI post;
	GetAPI get;
	UpdateAPI update;
	DeleteAPI delete;
	
	@Given("User able to go {string}")
	public void user_able_to_go(String url) {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
		
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
		Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
		WebDriver driver = new ChromeDriver();		
		driver.get(url);
		ScreenShot.emptyScreenShotFolder();
		try {
			ScreenShot.getScreenShot(driver, "API Page");
		} catch (IOException e) {			
			e.printStackTrace();
		}
		driver.quit();
	}

	@Given("Post the weather json to server")
	public void post_the_weather_json_to_server() {
		post = new PostAPI();
		postResponse = post.postJsonToServer();
	}

	@Then("User able to validate status code is {int}")
	public void user_able_to_validate_status_code_is(Integer statusCode) {
	    post.validatePostStatus(postResponse, statusCode);
	}

	@When("get json body response from the server {string}")
	public void get_json_body_response_from_the_server(String url) {
	    get = new GetAPI();
	    getResponse = get.getBody(url);
	}

	@Then("Verify that following {string} are available in resonse body coord, weather,  main, visibility, wind ,clouds, sys")
	public void verify_that_following_are_available_in_resonse_body_coord_weather_main_visibility_wind_clouds_sys(String string) {
	    get.checkAttributes(getResponse);
	}

	@Then("verify id contains {int}")
	public void verify_id_contains(Integer value) {
	    get.verifyData(getResponse, value);
	}

	@Then("verify id {int} not present")
	public void verify_id_not_present(Integer value) {
		get.verifyInvalidData(getResponse, value);
	}

	@Then("verify the context type in json format")
	public void verify_the_context_type_in_json_format() {
	    get.verifyContent(getResponse);
	}

	@Then("verify country is USA")
	public void verify_country_is_USA() {
	    get.verifyUSA(getResponse);
	}

	@Then("verify country Bangladesh  is not there")
	public void verify_country_Bangladesh_is_not_there() {
	    get.verifyBdesh(getResponse);
	}

	@Then("Verify city name is New York")
	public void verify_city_name_is_New_York() {
	    get.verifyNY(getResponse);
	}

	@Then("Verify city name Las Vagus is not there")
	public void verify_city_name_Las_Vagus_is_not_there() {
		get.verifyLasVegas(getResponse);
	}

	@When("Update  city name to Arizona {string}")
	public void update_city_name_to_Arizona(String url) {
	    update = new UpdateAPI();
	    updateResponse = update.updatedata(url);
	}

	@Then("validate update status code is {int}")
	public void validate_update_status_code_is(Integer value) {
	    update.validateUpdateStatus(updateResponse, value);
	}

	@When("Detele the json weather data {string}")
	public void detele_the_json_weather_data(String url) {
	    delete = new DeleteAPI();
	    deleteResponse = delete.deleteData(url);
	}

	@Then("User able to validate delete status code is {int}")
	public void user_able_to_validate_delete_status_code_is(Integer value) {
		delete.validateStatusCode(deleteResponse, value);
	}

}
