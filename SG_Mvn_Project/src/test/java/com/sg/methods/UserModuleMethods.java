package com.sg.methods;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.sg.driver.DriverScript;
import com.sg.locators.ObjectLocators;

public class UserModuleMethods extends DriverScript implements ObjectLocators {
	/**************************************
	 * Method Name	: createUser
	 * Purpose		: 
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: boolean
	 * Param		: oDriver
	 **************************************/
	public String createUser(WebDriver oDriver, Map<String, String> objData) {
		String userName = null;
		try {
			appInd.clickObject(oDriver, obj_USERS_Menu);
			appInd.waitForTheWebElement(oDriver, obj_AddUser_Btn, "Clickable", "", 10);
			appInd.verifyText(oDriver, obj_UserList_PageHeader, "Text", "User List");
				
			
			//click on "add Users" button and validate
			appInd.clickObject(oDriver, obj_AddUser_Btn);
			appInd.waitForTheWebElement(oDriver, obj_Users_ReypePassword_Edit, "Clickable", "", 10);
			appInd.verifyElementPresent(oDriver, obj_AddUser_Window);
			
			
			
			//6. Enter all the details to create the user & validate
			String FN = objData.get("FirstName");
			String LN = objData.get("LastName");
			appInd.setObject(oDriver, obj_Users_FirstName_Edit, FN);
			appInd.setObject(oDriver, obj_Users_LastName_Edit, LN);
			appInd.setObject(oDriver, obj_Users_Email_Edit, objData.get("email"));
			appInd.setObject(oDriver, obj_Users_UserName_Edit, objData.get("User_UN"));
			appInd.setObject(oDriver, obj_Users_Password_Edit, objData.get("User_PWD"));
			appInd.setObject(oDriver, obj_Users_ReypePassword_Edit, objData.get("User_RetypePwd"));
			
			userName = LN+", "+FN;
			appInd.clickObject(oDriver, obj_CreateUser_Btn);
			appInd.waitForTheWebElement(oDriver, By.xpath("//div[@class='name']/span[text()='" + userName + "']"), "Text", userName, 10);
			boolean blnRes = appInd.verifyElementPresent(oDriver, By.xpath("//div[@class='name']/span[text()='" + userName + "']"));
						reports.writeReport(oDriver, "Screenshot", "User created was successfull");

			if(blnRes) {
				reports.writeReport(oDriver, "Pass", "The user was created successful");
				return userName;
			}else {
				reports.writeReport(oDriver, "Fail", "Failed to create the user");
				return null;
			}
		}catch(Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'createUser()' method. " + e);
			return null;
		}
	}
	
	/**************************************
	 * Method Name	: deleteUser
	 * Purpose		: 
	 * Author		:
	 * Reviewer Name:
	 * Date Written :
	 * Return value	: boolean
	 * Param		: oDriver, userName
	 **************************************/
	public boolean deleteUser(WebDriver oDriver, String userName) {
		try {
						reports.writeReport(oDriver, "Screenshot", "Before Deleting the User");

			appInd.clickObject(oDriver, By.xpath("//div[@class='name']/span[text()='" + userName + "']"));
			appInd.waitForTheWebElement(oDriver, obj_DeleteUser_Btn, "clickable", "", 10);
			appInd.clickObject(oDriver, obj_DeleteUser_Btn);
			Thread.sleep(2000);
			oDriver.switchTo().alert().accept();
			Thread.sleep(2000);
			
						reports.writeReport(oDriver, "Screenshot", "After deleting the User");

			
			if(appInd.verifyElelementNotPresent(oDriver, By.xpath("//div[@class='name']/span[text()='" + userName + "']"))) {
				reports.writeReport(oDriver, "Pass", "User was deleted successful");
				return true;
				
			}else {
				reports.writeReport(oDriver, "Fail", "Failed to delete the user");
				return false;
			}
		}catch(Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'deleteUser()' method. " + e);
			return false;
		}
	}

}
