package com.Framework.CreateFramework.testCases;

import org.aeonbits.owner.ConfigFactory;

import org.testng.Assert;
import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;

import com.Framework.CreateFramework.API.CustomerAPI;
import com.Framework.CreateFramework.setup.APIsetUp;
import com.Framework.CreateFramework.testUtils.CinfigProperty;
import com.Framework.CreateFramework.testUtils.DataProviderClass;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

public class TestCustomerAPI extends APIsetUp {
	
	
	@Test(dataProviderClass= DataProviderClass.class,dataProvider="dp", enabled=false )
	public void validateCreateCustomerAPI(Hashtable<String, String> data)
	{
		/*  URI :https://api.stripe.com/v1/customers
			Method Type : Post
			Argument : email, description , balance 
			Auth : HTTP Basic Auth. -  Key is : sk_test_uqAtgiyJ9Ou8VZTu8N7Ijn2L00tErzCHEM
			To Read config.property file we have to add owner dependency in pom.xml
			*/
		
		
		testLevelLog.get().assignAuthor("Rishabh");       // testLevelLog is defined in ExtentManager and APISetUp
		testLevelLog.get().assignCategory("Regression");
		RequestSpecification spec = setMethodSpecification().formParam("email", data.get("email") )
		.formParam("description", data.get("description")).formParam("balance", 1000).log().all();
		
		System.out.println("===========================");
		
		Response response = spec.post("/customers");
		testLevelLog.get().info(response.asString());
		
		// fetch email address from response
		
		String emailInTheResponse = response.path("email");
		System.out.println(emailInTheResponse);
		
		// Fetch email from response from JsonPath method
		
		JsonPath jsonPath = new JsonPath(response.asString());
		System.out.println("fetching email using JsonPath method" +jsonPath.get("email"));
		
		// fetch description from the response
		String descriptionInTheResponse = response.path("description");
		System.out.println(descriptionInTheResponse);
		
		// fetch description from the response using JsonPath
		System.out.println("fetching description using JsonPath method" +jsonPath.get("description"));
		
		
		// Fetching value of invoice_settings---> footer
		System.out.println("Value of foorter is --->  " + response.path("invoice_settings.path"));
		
		
		
		
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		
	}
	
	@Test(dataProviderClass= DataProviderClass.class,dataProvider="dp", enabled= false )
	public void listAllCustomers(Hashtable<String, String> data)
	{
		testLevelLog.get().assignAuthor("Rishabh");       // testLevelLog is defined in ExtentManager and APISetUp
		testLevelLog.get().assignCategory("Regression");
		// Here we do not require formParam as it is a get request and we have also pplied the limits to 3 which is fetch from excel
		RequestSpecification spec = setMethodSpecification().formParam("limit", data.get("limit")).log().all();
		
		System.out.println("===========================");
		
		// Here we are taking method type --> get from excel sheet and endpoint from excel sheet
		Response response = spec.request(data.get("methodType"), data.get("endpoint"));
		testLevelLog.get().info(response.asString());
		
		// NoTE : *** We are fetching values from the response of AllCustomer API whose response is in array*******
		
		// fetching list of Ids
		
		ArrayList<String> listofId = response.path("data.id");
		System.out.println(listofId);
		
		// fetch the size of data ---> data is a field in the response
		System.out.println("Size of data---> " +response.path("data.size()"));
		
		// fetch number for fields in array
		System.out.println("no of fields in data at 1st position ---> " +response.path("data[0].size()"));
		
		//how to fetch customer Id from response of customer whose Created Id = 1587383942 
		//"created": 1587383942,  cust id - cus_H8EkZ8euroFniD	
		
		int lengthofData = response.path("data.size()");
		int ActualValueOfCreated = 1587383942;
		
		for(int i =0; i<lengthofData; i++)
		{
			int ExpectedValueOfCreated = response.path("data["+i+"].created");
			if(ExpectedValueOfCreated==ActualValueOfCreated)
			{
				String custID = response.path("data["+i+"].id");
				System.out.println("Customer Id is -----> " + custID);
			}
			
			
			}
		
		String valueofCity = response.path("data[1].shipping.address.city");
		System.out.println("Value of data is-----> "+valueofCity);
		
		// traversing complex json from google 
		String jsonValue = "{\"destination_addresses\":[\"Philadelphia, PA, USA\"],\"origin_addresses\":[\"New York, NY, USA\"],\"rows\":[{\"elements\":[{\"distance\":{\"text\":\"94.6 mi\",\"value\":152193},\"duration\":{\"text\":\"1 hour 44 mins\",\"value\":6227},\"status\":\"OK\"}]}],\"status\":\"OK\"}";
		JsonPath json = new JsonPath(jsonValue);
		
		String DurationValue = json.getString("rows.elements.duration.value");
		System.out.println("durationValis is ----> " +DurationValue);
		
		//response.prettyPrint();
	}

	@Test(dataProviderClass= DataProviderClass.class, dataProvider="dp",enabled= false )
	public void ValidatedeleteCustomerApi()
	{
		
		testLevelLog.get().assignAuthor("Rishabh");       // testLevelLog is defined in ExtentManager and APISetUp
		testLevelLog.get().assignCategory("Regression");
		// Here we do not require formParam as it is a get request and we have also pplied the limits to 3 which is fetch from excel
		RequestSpecification spec = setMethodSpecification().log().all();
		Response response = spec.delete("customerID");
		
	}

	@Test(dataProviderClass= DataProviderClass.class, dataProvider="dp",enabled=true,priority=0)
	public void checkCreateCustomerApiwithValiddata(Hashtable<String, String> data)
	{
		
		testLevelLog.get().assignAuthor("Rishabh");       // testLevelLog is defined in ExtentManager and APISetUp
		testLevelLog.get().assignCategory("Regression");
		
		Response response = CustomerAPI.sendPostRequestWithValidDeta(data);
		response.prettyPrint();
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(data.get("ExtectedStatusCode")));
		Assert.assertEquals(response.jsonPath().get("email"), data.get("Expectedemail"));
		
	}
	
	/*@Test(dataProviderClass= DataProviderClass.class, dataProvider="dp",priority=0,enabled=true)
	public void checkCreateCustomerApiwithInValidAuthKey(Hashtable<String, String> data)
	{
		
		
		
	}

	
	@Test(dataProviderClass= DataProviderClass.class, dataProvider="dp",priority=1,enabled=true)
	public void checkCreateCustomerApiwithvalidEmail(Hashtable<String, String> data)
	{
		
		
	}
	
	@Test(dataProviderClass= DataProviderClass.class, dataProvider="dp",priority=3,enabled=true)
	public void checkCreateCustomerApiwithInvalidFields(Hashtable<String, String> data)
	{
		
		
	}


	@Test(dataProviderClass= DataProviderClass.class, dataProvider="dp",priority=4,enabled=true)
	public void checkCreateCustomerApiwithoutPassingAuthKey()
	{
		
		
	}*/

}



