package com.w2a.API_FrameWork.setUp;

import static io.restassured.RestAssured.*;

import java.lang.reflect.Method;

import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.w2a.API_FrameWork.TestUtils.ConfigProperty;
import com.w2a.API_FrameWork.TestUtils.ExcelReader;
import com.w2a.API_FrameWork.TestUtils.Extentmanager;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class APISetUp {

	public static ConfigProperty configProperty = null;

	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "/src/test/resources/testData/TestData.xlsx");
	public static ExtentReports extentReport;
	public static ThreadLocal<ExtentTest> classLevelLog = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testLevelLog = new ThreadLocal<ExtentTest>();

	@BeforeSuite
	public void beforeSuite() {
		configProperty = ConfigFactory.create(ConfigProperty.class);
		RestAssured.baseURI = configProperty.getBaseURI();
		RestAssured.basePath = configProperty.getBasePath();
		extentReport = Extentmanager
				.GetExtent(configProperty.getTestReportFilePath() + configProperty.getTestReportName());
	}

	@BeforeClass
	public void beforeClass() {
		ExtentTest classLevelTest = extentReport.createTest(getClass().getSimpleName());
		classLevelLog.set(classLevelTest);
	}

	@BeforeMethod
	public void beforeMethod(Method method) // Need to Import java.lang.reflect.Method package
	{
		ExtentTest test = classLevelLog.get().createNode(method.getName());
		testLevelLog.set(test);
		testLevelLog.get().info("Test Case:---  " + method.getName() + " execution started.");
		// System.out.println("The Test Case:- " + method +" Execution
		// Started.");//lagacy code before extent report integration.

	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			testLevelLog.get().pass("The Test Cases is Passed.");
			// System.out.println("The Test Cases is Passed.");

		} else if (result.getStatus() == ITestResult.FAILURE) {
			testLevelLog.get().fail("The Test Cases is Failed.");
			// System.out.println("The Test Cases is Failed.");

		} else if (result.getStatus() == ITestResult.SKIP) {
			testLevelLog.get().info("The Test Cases is Skipped");
			// System.out.println("The Test Cases is Skipped.");
		}
		extentReport.flush();
	}

	@AfterSuite
	public void afterSuite() {
		configProperty = ConfigFactory.create(ConfigProperty.class);

		RestAssured.baseURI = configProperty.getBaseURI();
		RestAssured.basePath = configProperty.getBasePath();
	}

	public static RequestSpecification setRequestSpecification()// This method is to set the request specification
	{
		RequestSpecification spec = given().auth().basic(configProperty.getSecretKey(), "");
		testLevelLog.get().info("Request Specification is set");
		return spec;
	}
}