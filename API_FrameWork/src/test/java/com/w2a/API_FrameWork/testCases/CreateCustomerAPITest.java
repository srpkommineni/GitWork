package com.w2a.API_FrameWork.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.API_FrameWork.setUp.APISetUp;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CreateCustomerAPITest extends APISetUp {
	@Test
	public void createCustomerAPIwithValidData() {
		/*
		 * URL:https://api.stripe.com/v1/customers Method-Type: POST arguments: email,
		 * description, account_balance Auth: Basic - sk_test_n1XsXrDnOTR0Z9iOIE0RSqCB
		 */
		testLevelLog.get().assignAuthor("Sivaram" +"  K");
		testLevelLog.get().assignCategory("Regression Test");
		RequestSpecification spec = setRequestSpecification().formParam("email", "siva.kommineni@gmail.com")
				.formParam("description", "-----14th Mar2019- src/test/resources/testReports/-----")
				.formParam("account_balance", 10000).log().all();
		Response respon = spec.post("customers");
		testLevelLog.get().info(respon.asString());
		respon.prettyPrint();
		Assert.assertEquals(200, respon.getStatusCode());
	}
}