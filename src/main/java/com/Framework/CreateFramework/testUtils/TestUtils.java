package com.Framework.CreateFramework.testUtils;

import com.Framework.CreateFramework.setup.APIsetUp;

import io.restassured.specification.RequestSpecification;

public class TestUtils extends APIsetUp {
	
	
	public static RequestSpecification setFormParam(String arguments, RequestSpecification spec)
	
	{
		String[] listOfArguments = arguments.split(",");
		for(String singleArgument:listOfArguments)
		{
			
			String[] KeyValue = singleArgument.split(":");
			{
				String key = KeyValue[0];
				String value= KeyValue[1];
				
				// reqSpecs.body(arg0)
				spec.formParam(key, value);
			}
			
			
		}
		
		return spec;
		
		
	}

	
}
