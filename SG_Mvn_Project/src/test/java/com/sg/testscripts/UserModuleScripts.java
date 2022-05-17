package com.sg.testscripts;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.sg.driver.DriverScript;
public class UserModuleScripts extends DriverScript{
	
	
	/****************************************
	 * testScripr name		: TS_loginLogout()
	 * test case ID			: SRS_101
	 * 
	 ****************************************/
	public boolean TS_loginLogout()
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		try {
			test = extent.startTest("TS_loginLogout");
			objData = datatable.getExcelData("testData", "SRS_101");
			oBrowser = appInd.launchBrowser(appInd.getPropData("browserName"));
			Assert.assertNotNull(oBrowser, "The browser object was null");
			Assert.assertTrue(appDep.navigateURL(oBrowser, appInd.getPropData("URL")), "Failed to navigate the URL");
			Assert.assertTrue(appDep.loginToApp(oBrowser, "Existing", objData.get("UserName"), objData.get("Password")), "Failed to login to the actiTime application");
			Assert.assertTrue(appDep.logoutFromApp(oBrowser), "Failed tologout from the actiTime appilcation");
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception while executing 'TS_loginLogout()' test script. " + e);
			return false;
		}
		finally {
			reports.endExtentReport(test);
			oBrowser.close();
			objData = null;
			oBrowser = null;
		}
	}
	
	
	
	
	/****************************************
	 * testScripr name		: TS_CreateAndDeleteUser()
	 * test case ID			: SRS_102
	 * 
	 ****************************************/
	public boolean TS_CreateAndDeleteUser()
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String userName = null;
		try {
			test = extent.startTest("TS_CreateAndDeleteUser");
			objData = datatable.getExcelData("testData", "SRS_102");
			oBrowser = appInd.launchBrowser(appInd.getPropData("browserName"));
			Assert.assertNotNull(oBrowser, "The browser object was null");
			Assert.assertTrue(appDep.navigateURL(oBrowser, appInd.getPropData("URL")), "Failed to navigate the URL");
			Assert.assertTrue(appDep.loginToApp(oBrowser, "Existing", objData.get("UserName"), objData.get("Password")), "Failed to login to the actiTime application");
			userName = userMethods.createUser(oBrowser, objData);
			Assert.assertTrue(userMethods.deleteUser(oBrowser, userName), "Failed to delete the user");
			Assert.assertTrue(appDep.logoutFromApp(oBrowser), "Failed tologout from the actiTime appilcation");
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser, "Exception", "Exception while executing 'TS_CreateAndDeleteUser()' test script. " + e);
			return false;
		}
		finally {
			
			reports.endExtentReport(test);
			oBrowser.close();
			objData = null;
			oBrowser = null;
		}
	}
	

	/****************************************
	 * testScripr name		: TS_VerifyLoginWithNewUser_DeleteUser()
	 * test case ID			: SRS_102
	 * 
	 ****************************************/
	public boolean TS_VerifyLoginWithNewUser_DeleteUser()
	{
		WebDriver oBrowser1 = null;
		WebDriver oBrowser2 = null;
		Map<String, String> objData = null;
		String userName = null;
		try {
			test = extent.startTest("TS_VerifyLoginWithNewUser_DeleteUser");

			objData = datatable.getExcelData("testData", "SRS_103_1");
			oBrowser1 = appInd.launchBrowser(appInd.getPropData("browserName"));
			Assert.assertNotNull(oBrowser1, "The browser object was null");
			Assert.assertTrue(appDep.navigateURL(oBrowser1, appInd.getPropData("URL")), "Failed to navigate the URL");
			Assert.assertTrue(appDep.loginToApp(oBrowser1, "Existing", objData.get("UserName"), objData.get("Password")), "Failed to login to the actiTime application");
			userName = userMethods.createUser(oBrowser1, objData);
			
			//open new browser to check login works for the new user
			objData = datatable.getExcelData("testData", "SRS_103_2");
			oBrowser2 = appInd.launchBrowser(appInd.getPropData("browserName"));
			Assert.assertNotNull(oBrowser2, "The browser object was null");
			Assert.assertTrue(appDep.navigateURL(oBrowser2, appInd.getPropData("URL")), "Failed to navigate the URL");
			boolean blnRes=appDep.loginToApp(oBrowser2, "New", objData.get("UserName"), objData.get("Password"));
			if(blnRes) {
				reports.writeReport(oBrowser2, "Pass", "Login to actiTime with newly created user was successfull");
			}else {
				reports.writeReport(oBrowser2, "FAil", "Failed to login to actiTime with newly created user");
			}
			oBrowser2.close();

			Assert.assertTrue(userMethods.deleteUser(oBrowser1, userName), "Failed to delete the user");
			Assert.assertTrue(appDep.logoutFromApp(oBrowser1), "Failed tologout from the actiTime appilcation");
			return true;
		}catch(Exception e) {
			reports.writeReport(oBrowser1, "Exception", "Exception while executing 'TS_CreateAndDeleteUser()' test script. " + e);
			return false;
		}
		finally {
			reports.endExtentReport(test);
			oBrowser1.close();
			objData = null;
			oBrowser1 = null;
		}
	}
	
	
}
