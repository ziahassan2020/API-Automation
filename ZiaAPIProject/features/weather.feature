@Smoke 
Feature: Basic API testing 

@TS_0123 @TC_002 
Scenario Outline: Successful API method tesing 

	Given User able to go "<URL>" 
	And Post the weather json to server 
	Then User able to validate status code is 201 
	When get json body response from the server "<URL>" 
	Then Verify that following "<attributes>" are available in resonse body coord, weather,  main, visibility, wind ,clouds, sys 
	And verify id contains 99 
	And verify id 22 not present 
	And verify the context type in json format 
	And verify country is USA 
	And verify country Bangladesh  is not there 
	And Verify city name is New York 
	And Verify city name Las Vagus is not there 
	When Update  city name to Arizona "<URL>"
	Then validate update status code is 200
	When Detele the json weather data "<URL>"
	Then User able to validate delete status code is 200 
	
	Examples: 
		|URL							|attributes			|
		|http://localhost:3000/posts/99	|coord				|
		
		
	