package com.sg.locators;

import org.openqa.selenium.By;

public interface ObjectLocators {
	static final By obj_UserName_Edit= By.xpath("//input[@id='username']");
	static final By obj_Password_Edit=By.xpath("//input[@name='pwd']");
	static final By obj_Login_Btn=By.xpath("//a[@id='loginButton']");
	static final By obj_LoginLogo_image=By.cssSelector("img[src*='default-logo.png']");
	static final By obj_Shortcut_Window = By.xpath("//div[@style='display: block;']");
	static final By obj_Shortcut_Close_Btn = By.id("gettingStartedShortcutsMenuCloseId");
	static final By obj_USERS_Menu = By.xpath("//div[text()='USERS']");
	static final By obj_UserList_PageHeader = By.xpath("//span[text()='User List']");
	static final By obj_AddUser_Btn = By.xpath("//div[@class='buttonText']");
	static final By obj_AddUser_Window = By.id("userDataLightBox_titlePlaceholder");
	static final By obj_Users_FirstName_Edit = By.name("firstName");
	static final By obj_Users_LastName_Edit = By.name("lastName");
	static final By obj_Users_Email_Edit = By.name("email");
	static final By obj_Users_UserName_Edit = By.name("username");
	static final By obj_Users_Password_Edit = By.name("password");
	static final By obj_Users_ReypePassword_Edit = By.name("passwordCopy");
	static final By obj_CreateUser_Btn = By.cssSelector("span[class='buttonTitle']");
	static final By obj_DeleteUser_Btn = By.id("userDataLightBox_deleteBtn");
	static final By obj_Logout_Link = By.xpath("//a[@id='logoutLink']");
	static final By obj_Login_Header = By.id("headerContainer");
	static final By obj_StartExploringActitime_Btn = By.xpath("//span[@class='startExploringText']");

}
