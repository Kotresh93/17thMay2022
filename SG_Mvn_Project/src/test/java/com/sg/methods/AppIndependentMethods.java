package com.sg.methods;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sg.driver.DriverScript;

public class AppIndependentMethods extends DriverScript {
	/*
	 * method name:getDateAndTime
	 *
	 *
	 *
	 *
	 */
	public String getDateAndTime(String format) {
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(format);
			return sdf.format(dt);
		} catch (Exception e) {
			reports.writeReport(null, "Exception", "Exception in 'getDateAndTime()' method. " + e);
			return null;
		} finally {
			dt = null;
			sdf = null;
		}
	}

	/*
	 * method name: launchBrowser
	 * 
	 * 
	 * 
	 * 
	 */

	public WebDriver launchBrowser(String browserName) {
		WebDriver oDriver = null;

		try {
			switch (browserName.toLowerCase()) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\Library\\drivers\\chromedriver.exe");
				oDriver = new ChromeDriver();
				break;
			case "firefox":
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\Library\\drivers\\geckodriver.exe");
				oDriver = new FirefoxDriver();
				break;
			case "ie":
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\Library\\drivers\\IEDriverServer.exe");
				oDriver = new InternetExplorerDriver();
				break;

			case "edge":
				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + "\\Library\\drivers\\msedgedriver.exe");
				oDriver = new EdgeDriver();
				break;
			default:
				reports.writeReport(oDriver, "Fail", "Invalid " + browserName + " browser name");
			}
			if (oDriver != null) {
				reports.writeReport(oDriver, "Pass", "The browser '" + browserName + "' has launched successfull ");
			
				oDriver.manage().window().maximize();
				return oDriver;
			} else {
				reports.writeReport(oDriver, "Fail",
						"The browser '" + browserName + "' did not launched successfully ");
				return null;
			}
		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception while executing 'clickObject()' method. " + e);
			return null;
		}
	}

	/**************************************
	 * Method Name : clickObject
	 * Purpose :
	 * Author :
	 * Reviewer Name:
	 * Date Written :
	 * Return value : boolean
	 * Param : Webdriver, By
	 **************************************/

	public boolean clickObject(WebDriver odDriver, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = odDriver.findElements(objBy);
			if (oEles.size() > 0) {
				reports.writeReport(odDriver, "Pass",
						"The element '" + String.valueOf(objBy) + "' was clicked successfully");
				oEles.get(0).click();
				return true;

			} else {
				reports.writeReport(odDriver, "Fail",
						"The element '\"+String.valueOf(objBy)+\"' was not found in the DOM");
				return false;
			}
		} catch (Exception e) {
			reports.writeReport(odDriver, "Exception", "Exception in 'clickObject()' method. " + e);
			return false;
		} finally {
			oEles = null;
		}
	}

	/**************************************
	 * Method Name : setObject 
	 * Purpose :
	 *  Author : 
	 *  Reviewer Name:
	 *   Date Written :
	 * Return value : boolean
	 *  Param : Webdriver, objBy, strValue
	 **************************************/

	public boolean setObject(WebDriver oDriver, By objBy, String strValue) {

		List<WebElement> oEles = null;
		try {

			oEles = oDriver.findElements(objBy);
			if (oEles.size() > 0) {
				reports.writeReport(oDriver, "Pass", "The data '" + strValue + "' was entered in the element '"
						+ String.valueOf(objBy) + "' successful");
				oEles.get(0).sendKeys(strValue);
				return true;
			} else {
				reports.writeReport(oDriver, "Fail",
						"The element '" + String.valueOf(objBy) + "' was not found in the DOM");
				return false;
			}
		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception in 'setObject()' method. \"+ e");
			return false;
		} finally {
			oEles = null;
		}
	}

	/**************************************
	 * Method Name : compareValues
	 *  Purpose :
	 *   Author :
	 *    Reviewer Name:
	 *     Date Written :
	 * Return value : boolean
	 *  Param : actualValue, expectedValue
	 **************************************/

	public boolean compareValues(WebDriver oDriver, String actualValue, String expectedValue) {
		try {
			if (actualValue.equals(expectedValue)) {
				reports.writeReport(oDriver, "Pass",
						"The actual '" + actualValue + "' & expected '" + expectedValue + "' are matched successful");
				return true;
			} else {
				reports.writeReport(oDriver, "Fail",
						"Mis-match in the actual '" + actualValue + "' & expected '" + expectedValue + "' value");
				return false;
			}

		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception in 'compareValues()' method. \"+ e");
			return false;
		}

	}

	/**************************************
	 * Method Name : verifyText 
	 * Purpose : 
	 * Author : 
	 * Reviewer Name: 
	 * Date Written :
	 * Return value : boolean 
	 * Param : oDriver, objBy, strObjectType, expectedValue
	 **************************************/

	public boolean verifyText(WebDriver oDriver, By objBy, String strObjectType, String expectedValue) {

		List<WebElement> oEles = null;
		Select oSelect = null;
		String actualValue = null;
		try {
			oEles = oDriver.findElements(objBy);
			if (oEles.size() > 0) {
				switch (strObjectType.toLowerCase()) {
				case "text":
					actualValue = oEles.get(0).getText();
					break;
				case "value":
					actualValue = oEles.get(0).getAttribute("value");
					break;
				case "dropdown":
					oSelect = new Select(oEles.get(0));
					actualValue = oSelect.getFirstSelectedOption().getText();
				default:
					reports.writeReport(oDriver, "Fail", "Invalid object type '" + strObjectType + "'");
				}

				if (compareValues(oDriver, actualValue, expectedValue)) {
					return true;
				} else {
					return false;
				}
			} else {
				reports.writeReport(oDriver, "Fail",
						"The element '" + String.valueOf(objBy) + "' was not found in the DOM");
				return false;
			}

		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception in 'verifyText()' method. \"+ e");
			return false;
		} finally {
			oSelect = null;
			oEles = null;
		}

	}

	/**************************************
	 * Method Name : verifyElelementPresent 
	 * Purpose : 
	 * Author : 
	 * Reviewer Name: 
	 * DateWritten : 
	 * Return value : boolean 
	 * Param : oDriver, objBy
	 **************************************/

	public boolean verifyElementPresent(WebDriver oDriver, By objBy) {

		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);

			if (oEles.size() > 0) {
				reports.writeReport(oDriver, "Pass", "The element '" + String.valueOf(objBy) + "' present in the DOM");
				return true;
			} else {
				reports.writeReport(oDriver, "Fail",
						"The element '" + String.valueOf(objBy) + "' doesnot exist in the DOM");
				return false;
			}
		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception", "Exception in 'setObject()' method. \"+ e");
			return false;
		} finally {

			oEles = null;
		}
	}

	/**************************************
	 * Method Name : verifyElelementNotPresent 
	 * Purpose : 
	 * Author : 
	 * Reviewer Name:
	 * Date Written : 
	 * Return value : boolean 
	 * Param : oDriver, objBy
	 **************************************/
	public boolean verifyElelementNotPresent(WebDriver oDriver, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);

			if (oEles.size() > 0) {
				reports.writeReport(oDriver, "Fail",
						"The element '" + String.valueOf(objBy) + "' still exist in the DOM");
				return false;
			} else {
				reports.writeReport(oDriver, "Pass",
						"The element '" + String.valueOf(objBy) + "' is NOT present in the DOM");
				return true;
			}
		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception",
					"Exception while executing 'verifyElelementNotPresent()' method. " + e);
			return false;
		} finally {
			oEles = null;
		}
	}

	/**************************************
	 * Method Name : verifyOptionalElement 
	 * Purpose : 
	 * Author : 
	 * Reviewer Name: 
	 * DateWritten : 
	 * Return value : boolean 
	 * Param : oDriver, objBy
	 **************************************/
	public boolean verifyOptionalElement(WebDriver oDriver, By objBy) {
		List<WebElement> oEles = null;
		try {
			oEles = oDriver.findElements(objBy);

			if (oEles.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			reports.writeReport(oDriver, "Exception",
					"Exception while executing 'verifyOptionalElement()' method. " + e);
			return false;
		} finally {
			oEles = null;
		}
	}

	/**************************************
	 * Method Name : getPropData 
	 * Purpose : 
	 * Author : 
	 * Reviewer Name: 
	 * Date Written :
	 * Return value : String 
	 * Param : strKey
	 **************************************/

	public String getPropData(String strKey) {
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir") + "\\Configuration\\masterData.properties");
			prop = new Properties();
			prop.load(fin);

			return prop.getProperty(strKey);

		} catch (Exception e) {
			reports.writeReport(null, "Exception", "Exception while executing 'getPropData()' method. " + e);
			return null;
		} finally {
			try {
				fin.close();
				fin = null;
				prop = null;
			} catch (Exception e) {
				reports.writeReport(null, "Exception", "Exception while executing 'getPropData()' method. " + e);
			}
		}
	}

	/*
	 * waitForTheElemt
	 * 
	 * 
	 */
	public boolean waitForTheWebElement(WebDriver oDriver, By objBy, String waitCondition, String strValue,
			int waitTimeout) {
		WebDriverWait oWait = null;

		try {
			oWait = new WebDriverWait(oDriver, Duration.ofSeconds(waitTimeout));
			switch (waitCondition.toLowerCase()) {
			case "text":
				oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, strValue));
				break;
			case "value":
				oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, strValue));
			case "clickable":
				oWait.until(ExpectedConditions.elementToBeClickable(objBy));
				break;
			case "refreshed":
				oWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(objBy)));
				break;
			case "visibility":
				oWait.until(ExpectedConditions.visibilityOfElementLocated(objBy));
				break;
			case "presence":
				oWait.until(ExpectedConditions.presenceOfElementLocated(objBy));
				break;
			case "invisibility":
				oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
				break;
			default:
				reports.writeReport(oDriver, "Fail",
						"Invalid wait condition '" + waitCondition + "' was provided. Please provide appropriate");
			}
			return true;

		} catch (Exception e) {
			System.out.println("Exception while executing 'waitForTheWebElement()' method. " + e);
			return false;
		} finally {
			oWait = null;
		}

	}

}
