package RestApi;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.*;

import org.apache.logging.log4j.LogManager;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



import googleApi.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class basics3 {
	
	private static Logger log = LogManager.getLogger(basics3.class.getName());
	
	Properties prop = new Properties();
	
	@BeforeTest
	public void envData() throws IOException{
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\env.properties");
		prop.load(fis);
		
		
	}
	
	@Test
	public void addAndDelete(){
		
		log.info("Hosting Information "+prop.getProperty("HOST"));
		RestAssured.baseURI = prop.getProperty("HOST");


		
		Response res = given().
		
		queryParam("key",prop.getProperty("KEY")).
		body(PayLoad.getPostData()).
		when().
		post(Resources.placePostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
		body("status",equalTo("OK")).
		extract().response();
		
		String responseString = res.asString();
		//System.out.println(responseString);
		log.info(responseString);
		JsonPath js = new JsonPath(responseString);
		String placeId = js.get("place_id");
		//System.out.println(placeId);
		log.info(placeId);
		
		given().
		queryParam("key","AIzaSyBfU_WlcnPDTI1rdjYW0WqQOdVhQqx8Q2w").
		body("{"+
  "\"place_id\": \""+placeId+"\""+
"}").
	when().
	post("/maps/api/place/delete/json").
	then().assertThat().statusCode(200).and().contentType(ContentType.JSON).
	body("status",equalTo("OK"));
	
	
	
	}
	

}
