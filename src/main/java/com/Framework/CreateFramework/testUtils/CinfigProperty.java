package com.Framework.CreateFramework.testUtils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;;

/*// Pass the location of config.Properties file and then extends Config Interface which pre-defined in Owner class.
This Owner class we add by adding dependencies*/

@Sources({
	
	"file:src\\test\\resources\\propertyFiles\\config.Properties"
})

// config is the class of ownwer API
public interface CinfigProperty extends Config {
	
	// This @Key means value of baseURI in config.properties file will map to getBaseURI().
	@Key("baseURI")
	String getBaseURI();  // Here String is because value of baseURI is a string
	
	@Key("basePath")
	String getBasePath();
	
	@Key("secretKey")
	String getSecretKey();
	
	@Key("testReportPath")
	String getTestReportPath();
	
	@Key("testReportName")
	String getTestReportName();
	
	@Key("TestDataSheetName")
	String getTestDataSheetName();
	
	
	

}
