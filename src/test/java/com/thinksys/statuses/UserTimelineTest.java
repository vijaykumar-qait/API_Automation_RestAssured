package com.thinksys.statuses;

import static io.restassured.RestAssured.given;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.thinksys.utilities.Restutilities;
import com.thinksys.constants.EndPoints;
import com.thinksys.constants.Path;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;


public class UserTimelineTest {

	RequestSpecification reqSpec;
	ResponseSpecification resSpec;

	@BeforeClass
	public void setup() {
		reqSpec = Restutilities.getRequestSpecification();
		reqSpec.queryParam("screen_name", "amrit43363502");
		reqSpec.basePath(Path.STATUSES);

		resSpec = Restutilities.getResponseSpecification();
	}

	@Test
	public void readTweets() {
		given()
		.spec(reqSpec)
		.when()
		.get(EndPoints.STATUSES_USER_TIMELINE)
		.then()
		.log().all()
		.spec(resSpec)
		.body("user.name", hasItem("amrit"));
	}


	@Test
	public void readTweets1() {
		given()
		.spec(Restutilities.createQueryParam(reqSpec, "count", "1"))
		.when()
		.get(EndPoints.STATUSES_USER_TIMELINE)
		.then()
		.log().all()
		.spec(resSpec)
		.body("user.name", hasItem("amrit"));
	}
	
	@Test
	public void readTweets3() {
		Restutilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
		Response res = Restutilities.getResponse(
				Restutilities.createQueryParam(reqSpec, "count", "2"), "get");
		ArrayList<String> screenNameList = res.path("user.name");
		System.out.println("Read Tweets 2 Method");
		Assert.assertTrue(screenNameList.contains("amrit"));
	}
}