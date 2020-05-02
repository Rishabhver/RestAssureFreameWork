package com.Framework.CreateFramework.testUtils;

import com.Framework.CreateFramework.setup.APIsetUp;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class ExtentManager extends APIsetUp {
	
	
	private static ExtentReports extent;
	//private static ExtentTest test;
	private static ExtentHtmlReporter htmlReporter;
	// private static String filePath = "./extentreport.html";


	public static ExtentReports GetExtent(String filePath) {
		if (extent != null) {
			return extent;
		} else {
			extent = new ExtentReports();
			extent.attachReporter(getHtmlReporter(filePath));
			extent.setSystemInfo("Host Name", "Rishabh@Java");
			
			extent.setAnalysisStrategy(AnalysisStrategy.CLASS);
			return extent;
		}
	}

	public static ExtentHtmlReporter getHtmlReporter(String filePath ) {

		htmlReporter = new ExtentHtmlReporter(filePath);
		/*htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle(" Automation Report");
		htmlReporter.config().setReportName("Automation");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);*/
		
		//htmlReporter.setAppendExisting(false);
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"/src/test/resources/ConfigXMLFiles/ReportsConfig.xml");

		return htmlReporter;
	}

}
