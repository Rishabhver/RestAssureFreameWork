package com.Framework.CreateFramework.API;

import java.util.Hashtable;

import com.Framework.CreateFramework.setup.APIsetUp;
import com.Framework.CreateFramework.testUtils.TestUtils;

import io.restassured.response.Response;

public class CustomerAPI extends APIsetUp {
	
	
	public static Response sendPostRequestWithValidDeta(Hashtable<String, String> data)
	 
	{
		
		Response response = TestUtils.setFormParam(data.get("arguments"), setMethodSpecification()).post(data.get("endpoint"));
		return response;
		
	}

}
