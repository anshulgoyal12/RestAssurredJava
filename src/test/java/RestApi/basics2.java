package RestApi;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import googleApi.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;

public class basics2 {
	
	@Test
	public void postData(){
		
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		given().
		
		queryParam("key","AIzaSyBfU_WlcnPDTI1rdjYW0WqQOdVhQqx8Q2w").
		body(PayLoad.createPlaceData()).
		when().
		post("/maps/api/place/add/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		body("status",equalTo("OK"));
		
	}

}
