package com.sg.reports;

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sg.driver.DriverScript;

public class ReportUtils extends DriverScript{
	/****************************************
	 * method name		: startExtentReport
	 * 
	 * 
	 ****************************************/
	public ExtentReports startExtentReport(String fileName, String buildName)
	{
		String resultPath = null;
		try {
			resultPath = System.getProperty("user.dir") + "\\Results\\" + buildName;
			
			File objResultPath = new File(resultPath);
			if(!objResultPath.exists())
			{
				objResultPath.mkdirs();
			}
			
			screenshotLocation = objResultPath + "//ScreenShots";
			File objScreenshotLoc = new File(screenshotLocation);
			if(!objScreenshotLoc.exists())
			{
				objScreenshotLoc.mkdirs();
			}
			
			
			extent = new ExtentReports(resultPath + "\\" + fileName + ".html");
			extent.addSystemInfo("Host Name", System.getProperty("os.name"));
			extent.addSystemInfo("Environment",appInd.getPropData("Environment"));
			extent.addSystemInfo("User Name", System.getProperty("user.name"));
			extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
			return extent;
		}catch(Exception e) {
			System.out.println("Exception in 'startExtentReport()' method. " + e);
			return null;
		}
	}
	
	
	
	/****************************************
	 * method name		: endExtentReport
	 * 
	 * 
	 ****************************************/
	public void endExtentReport(ExtentTest test) {
		try {
			extent.endTest(test);
			extent.flush();
		}catch(Exception e) {
			System.out.println("Exception in 'endExtentReport()' method. " + e);
		}
	}
	
	
	/****************************************
	 * method name		: captureScreenshot
	 * 
	 * 
	 ****************************************/
	public String captureScreenshot(WebDriver oBrowser) {
		File objSource = null;
		String strDestination = null;
		File objDestination = null;
		try {
			strDestination = screenshotLocation + "\\" + "screenshot_" +  appInd.getDateAndTime("ddMMyyyy_hhmmss")+ ".png";
			TakesScreenshot ts = (TakesScreenshot) oBrowser;
			objSource = ts.getScreenshotAs(OutputType.FILE);
			objDestination = new File(strDestination);
			FileHandler.copy(objSource, objDestination);
			return strDestination;
		}catch(Exception e) {
			System.out.println("Exception in 'captureScreenshot()' method. " + e);
			return null;
		}
		finally {
			objSource = null;
			objDestination = null;
		}
	}
	
	
	
	/****************************************
	 * method name		: writeReport
	 * 
	 * 
	 ****************************************/
	
	public void writeReport(WebDriver oBrowser, String status, String message)
	{
		try {
			switch (status.toLowerCase()) {
			case"pass":
				test.log(LogStatus.PASS, message);
				break;
			case"fail":
			test.log(LogStatus.FAIL, message + " : " + test.addScreenCapture(captureScreenshot(oBrowser)));
			break;
			case "warning":
				test.log(LogStatus.WARNING, message);
				break;
			case"exception":
				test.log(LogStatus.FATAL, message + " : " + test.addScreenCapture(captureScreenshot(oBrowser)));
				break;
			case"info":
				test.log(LogStatus.PASS, message);
				break;
			case"screenshot":
				test.log(LogStatus.PASS, message + " : " + test.addScreenCapture(captureScreenshot(oBrowser)));
				break;
				default:
				System.out.println("Invalid report status '" + status +"' provided. Please provide the appropriate status ");
				
			
			
			}
		} catch (Exception e) {
			System.out.println("Exception in 'writereport()' method. " + e);
		}
	}
	
	
}
