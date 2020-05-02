package com.Framework.CreateFramework.setup;

import static io.restassured.RestAssured.given;

import java.lang.reflect.Method;

import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.Framework.CreateFramework.testUtils.CinfigProperty;
import com.Framework.CreateFramework.testUtils.ExcelReader;
import com.Framework.CreateFramework.testUtils.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class APIsetUp {
	
	public static CinfigProperty configProperty;
	
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\APITestData.xlsx");
	
	public static ExtentReports extentReport;
	
	public static ThreadLocal<ExtentTest> classLevelLog = new ThreadLocal<ExtentTest>(); // To log class level info
	public static ThreadLocal<ExtentTest> testLevelLog = new ThreadLocal<ExtentTest>(); // to log Test Level Info
	
	
	
	
	
	@BeforeSuite
	public void beforeSuite()
	{
		
        configProperty = ConfigFactory.create(CinfigProperty.class);  // It will create instance for ConfigFactory interface
		
		RestAssured.baseURI= configProperty.getBaseURI();
		RestAssured.basePath = configProperty.getBasePath();
		extentReport= ExtentManager.GetExtent(configProperty.getTestReportPath()+configProperty.getTestReportName()); // extentReport object creation
	}
	
	@BeforeClass
	public void beforeClass()
	{
		ExtentTest classLevelTest  =  extentReport.createTest(getClass().getSimpleName()); // Create object of ExtentTest for class level
		classLevelLog.set(classLevelTest);
	}
	
	@BeforeMethod
	public void beforeMethod(Method method)  // we will define the method name at run time with the concept of reflection. 
	                                         // Method method will capture the info of next running method
	{
		ExtentTest test = classLevelLog.get().createNode(method.getName()); // Create object of ExtentTest for class level
		testLevelLog.set(test);  // Setting Value of testLevel log
		testLevelLog.get().info("Test Case started ----> "+ method.getName() + "  Execution Started");
		
		//System.out.println("TestCase  --->   " + method.getName()+ "Execution Started");
		
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result)
	{
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			testLevelLog.get().pass("Test Case pass");  // with testLevel Log
			//System.out.println("This Test Case is Passed");  -------------------> with sop
		}
		else if (result.getStatus()==ITestResult.SKIP)
		{
			//System.out.println("Test Case skipped");
			testLevelLog.get().skip("Test Case skipped");
		}
		else if(result.getStatus()==ITestResult.FAILURE)
		{
			//System.out.println("Test case is failed");
			testLevelLog.get().fail("Test Case failed");
		}
		
		extentReport.flush();
	}
	
	public static RequestSpecification  setMethodSpecification()
	{
		RequestSpecification spec = given().auth().basic(configProperty.getSecretKey(), "");
		testLevelLog.get().info("RequestSpecification is set");
		return spec;
	}
	
	@AfterSuite
	public void afterSuite()
	{
		  
	}

}
