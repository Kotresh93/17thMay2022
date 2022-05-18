package com.sg.methods;

import org.openqa.selenium.WebDriver;
import com.sg.driver.DriverScript;
import com.sg.locators.ObjectLocators;

public class AppDependentMethods extends DriverScript implements ObjectLocators {

	/**************************************
	 * Method Name : navigateURL 
	 * Purpose : To navigate the URL 
	 * Author : 
	 * Reviewer Name: 
	 * Date Written : 
	 * Return value : boolean 
	 * Param : oDriver, URL
	 **************************************/

	public boolean navigateURL(WebDriver oDriver, String URL) {
		try {
			oDriver.navigate().to(URL);
			appInd.waitForTheWebElement(oDriver, obj_Login_Btn, "Clickable", "", 10);

			String strTitle = oDriver.getTitle();
			if (appInd.compareValues(oDriver, strTitle, "actiTIME - Login"))
				return true;
			else
				return false;
		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'navigateURL()' method. " + e);
			return false;
		}
	}

	/**************************************
	 * Method Name : loginToApp 
	 * Purpose : Author : 
	 * Reviewer Name: 
	 * Date Written :
	 * Return value : boolean 
	 * Param : oDriver, userName, password
	 **************************************/
	public boolean loginToApp(WebDriver oDriver, String userType, String userName, String password) {
		try {
			appInd.setObject(oDriver, obj_UserName_Edit, userName);
			appInd.setObject(oDriver, obj_Password_Edit, password);
			appInd.clickObject(oDriver, obj_Login_Btn);

			if (userType.equals("New")) {
				appInd.waitForTheWebElement(oDriver, obj_StartExploringActitime_Btn, "Clickable", "", 20);
				appInd.clickObject(oDriver, obj_StartExploringActitime_Btn);

			}

			appInd.waitForTheWebElement(oDriver, obj_LoginLogo_image, "Clickable", "", 10);
			
			reports.writeReport(oDriver, "Screenshot", "Login to actiTime was successfull");
			if (appInd.verifyElementPresent(oDriver, obj_LoginLogo_image)) {
				reports.writeReport(oDriver, "Pass", "Login to actiTime is successful");

				appInd.waitForTheWebElement(oDriver, obj_Shortcut_Close_Btn, "Clickable", "", 10);
				boolean blnRes = appInd.verifyOptionalElement(oDriver, obj_Shortcut_Window);
				if (blnRes)
					appInd.clickObject(oDriver, obj_Shortcut_Close_Btn);
				return true;
			} else {
				reports.writeReport(oDriver, "Fail", "Failed to login to actiTime");
				return false;
			}
		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'loginToApp()' method. " + e);
			return false;
		}
	}
	
	/**************************************
	 * Method Name	: logoutFromApp
	 * Purpose		: 
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: boolean
	 * Param		: oDriver
	 **************************************/
	public boolean logoutFromApp(WebDriver oDriver) {
		try {
			appInd.clickObject(oDriver, obj_Logout_Link);
			appInd.waitForTheWebElement(oDriver, obj_Login_Header, "Text", "Please identify yourself", 10);
				
		reports.writeReport(oDriver, "Screenshot", "Logout from actiTime was successfull");

			
			if(appInd.verifyText(oDriver, obj_Login_Header, "Text", "Please identify yourself")) {
				reports.writeReport(oDriver, "Pass", "Logout from the actiTime was successful");
				return true;
			}else {
				reports.writeReport(oDriver, "Fail", "Failed to logout from the actiTime");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'logoutFromApp()' method. " + e);
			return false;
		}
	}
}
