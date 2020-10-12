package keyWordLibrary;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import baseClassLibrary.BaseClass;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


public class KeyWordLibrary extends BaseClass{

	/**
	 * Process
	 */
	private static java.lang.Process p;
	/**
	 * Appium Service URL
	 */
	private static String appiumServiceUrl;
	DesiredCapabilities cap;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent ;
	String PageName;
	//Dynamic Report Folder Path
	String dateFolder;
	File FILE;
	public File imFILE;



	public AppiumDriver<MobileElement> driver;



	//Current Directory
	String currentDir = System.getProperty("user.dir");
	//DateComboToGenerateReport
	java.util.Date runStartTimeStamp = new java.util.Date();
	String[] date1 = runStartTimeStamp.toString().split(" ");
	String[] date2 = date1[3].split(":");
	String dateval = date2[0] + date2[1] + date2[2];


	ExtentTest logger;

	//******************************************************************************************************************************
	//ScreenShot function
	//******************************************************************************************************************************
	public  String takeScreenshot()
	{
		//****************************************************************************
		//Folder path creation
		//****************************************************************************
		//If ExtentReports folder is not present create an image folder in current directory
		imFILE = new File(currentDir +"\\ExtentReports");
		if (!imFILE.exists())
		{
			imFILE.mkdir();
		}

		this.dateFolder = currentDir+"\\ExtentReports\\"+this.date1[1]+"_"+this.date1[2]+"_"+this.date1[5];

		this.FILE = new File(dateFolder);
		if (!this.FILE.exists())
		{
			this.FILE.mkdir();
		}

		//Create page specific folder 
		this.FILE = new File(dateFolder+"\\"+PageName);
		if (!this.FILE.exists())
		{
			this.FILE.mkdir();
		}

		String pageFolder=dateFolder+"\\"+PageName;

		//If Screenshots folder is not present, then create a screenshot folder in current directory
		imFILE = new File(pageFolder + "\\Screenshots");
		if (!imFILE.exists())
		{
			imFILE.mkdir();
		}

		//****************************************************************************

		//Image Time Stamp
		java.util.Date imgTimeStamp = new java.util.Date();
		System.out.println(imgTimeStamp);
		String[] imgdate1 = imgTimeStamp.toString().split(" ");
		String[] imgdate2 = imgdate1[3].split(":");
		String imgdateval = imgdate2[0] + imgdate2[1] + imgdate2[2]; 

		//ImagePath
		String imgPath = pageFolder+"\\Screenshots\\page_"+imgdate1[1] + imgdate1[2] + imgdateval+".jpeg";

		//****************************************************************************

		//GetScreenShot Method Directory and Image File
		File getSreenShotMethodImageFile = new File (imgPath);

		//Take Screenshot of viewable area
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//Write Screenshot to a file
		try 
		{
			FileUtils.copyFile(scrFile, getSreenShotMethodImageFile);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imgPath;
	}


	public  void startReport(String TestCaseName,String PageName,String TestCaseObjective,String appPackage,String appActivity){
		//****************************************************************************
		//Folder Path Creation
		//****************************************************************************		

		this.PageName=PageName;

		//Create Extent_Reports folder if it is not created
		this.FILE = new File(currentDir+"\\ExtentReports");
		if (!this.FILE.exists())
		{
			this.FILE.mkdir();
		}

		this.dateFolder = currentDir+"\\ExtentReports\\"+this.date1[1]+"_"+this.date1[2]+"_"+this.date1[5];

		this.FILE = new File(dateFolder);
		if (!this.FILE.exists())
		{
			this.FILE.mkdir();
		}

		//Create page specific folder 
		this.FILE = new File(dateFolder+"\\"+PageName);
		if (!this.FILE.exists())
		{
			this.FILE.mkdir();
		}
		this.dateFolder = currentDir+"\\ExtentReports\\"+this.date1[1]+"_"+this.date1[2]+"_"+this.date1[5];
		//Current Directory

		htmlReporter=new ExtentHtmlReporter(dateFolder+"\\"+PageName+"\\"+"TestClass_" + date1[1] + date1[2] + dateval +".html");
		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
		//****************************************************************************
		//HTML Report Initialization
		//****************************************************************************	
		logger=extent.createTest(TestCaseName);

		logger.log(Status.INFO, TestCaseObjective);
		logger.log(Status.INFO, appPackage);
		logger.log(Status.INFO, appActivity);
	}

	public void launchApplication(String deviceName,String platformName,String appPackage,String appActivity){		

		try {


			launchAppiumServer();

			setDesiredCapabilities(deviceName, platformName, appPackage, appActivity);
			logger.log(Status.INFO, "Launching Application");

			driver=new AppiumDriver<MobileElement>(new URL(appiumServiceUrl),cap);

			System.out.println("APPLICATION LAUNCHED");
			Thread.sleep(4000);
			logger.log(Status.PASS, "APPLICATION LAUNCHED SUCCESSFULLY");		

			logger.log(Status.PASS, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			System.out.println(e.getCause());

			e.printStackTrace();

		}

	}

	/**
	 * getDeviceID
	 */
	private String getDeviceID()
	{
		String deviceID="";
		try
		{
			java.lang.Runtime rt = java.lang.Runtime.getRuntime();
			// Start a new process: UNIX command ls
			p = rt.exec("adb devices");

			// Get process output: its InputStream
			java.io.InputStream is = p.getInputStream();
			java.io.BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));

			//dummy read to skip the unwanted string
			reader.readLine();

			deviceID = reader.readLine().split("\t")[0];
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return deviceID;
	}

	/**
	 * Get Platform Version - Returns Platform Version
	 * @return String Platform Version
	 */
	private String getPlatformVersion()
	{
		String platformVersion = "";
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();
		try 
		{
			// Start a new process: UNIX command ls
			p = rt.exec("adb shell getprop ro.build.version.release");
			// Get process output: its InputStream
			java.io.InputStream is = p.getInputStream();
			java.io.BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
			platformVersion = reader.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return platformVersion;
	}


	/**
	 * launchAppiumServer - Launches Appium Service Using Default Appium Port
	 */
	public static void launchAppiumServer()
	{
		server = AppiumDriverLocalService.buildDefaultService();
		server.start();
		appiumServiceUrl = server.getUrl().toString();
	}

	public 	void endReport(){
		extent.flush();
		extent.close();

		driver.quit();

		terminateAppiumServer();


	}
	/**
	 * setDesiredCapabilities - Sets The Desired Capabilities
	 * @param deviceName - Provide Device Name
	 * @param platformName - Provide Platform Name (Android/IOS)
	 * @param appPackage - Provide The Application Package Name
	 * @param appActivity - Provide The Application Activity
	 */
	private void setDesiredCapabilities(String deviceName, String platformName, String appPackage, String appActivity)
	{
		cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability(MobileCapabilityType.UDID, getDeviceID());
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, getPlatformVersion());
		cap.setCapability("appPackage", appPackage);
		cap.setCapability("appActivity", appActivity);
		cap.setCapability("autoGrantPermissions", true);
		cap.setCapability("automationName", "UiAutomator2");
		cap.setCapability("autoAcceptAlerts", true);
		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 100);
		cap.setCapability("chromedriverExecutable","C:\\Selenium\\Appium\\drivers\\chromedriver.exe");
		cap.setCapability("–session-override",true);
	}

	/**
	 * terminateAppiumServer - Terminates the appium server and frees up the space
	 */
	public static void terminateAppiumServer()
	{
		//kill Appium server process
		server.stop();
		//Log For User Awareness
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println("!!!!!!......Terminated Appium Server......!!!!!!");
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	}


	/**
	 * tapOnElement - Performs Tap Action On The Target Element 
	 * @param by -  Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param elementName
	 */
	public void tapElement(By by, String elementName) throws Exception
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			MobileElement e1 = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(by));
			if (e1.isDisplayed())
			{

				e1.click();

				logger.log(Status.PASS, "Clicked "+elementName+" Successfully");		

				logger.log(Status.PASS, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));

			}
		}
		catch (Exception e)
		{
			

			logger.log(Status.PASS, "Clicked "+elementName+" Successfully");		

			logger.log(Status.PASS, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));
			logger.log(Status.FAIL, "Clicked "+elementName+" Failed");		

			logger.log(Status.FAIL, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));
		}
	}


	/**
	 * verifyPageDisplayed - Verify Page Is Displayed With The Help Of Object In That Page
	 * @param by - Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param pageName - Page Name To Be Verified
	 * @throws Exception
	 */
	public void verifyPageDisplayed(By by, String pageName) throws Exception
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			MobileElement e1 = (MobileElement)wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if(e1.isDisplayed()){
				logger.log(Status.PASS, "The "+pageName+" page is displayed Successfully");	
				logger.log(Status.PASS, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));
				}
			else{
				logger.log(Status.FAIL, "The "+pageName+"page failed to display");	
				logger.log(Status.FAIL, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));
				}
		}
		catch(Exception e)
		{
			logger.log(Status.FAIL, "The "+pageName+"page failed to display");	
			logger.log(Status.FAIL, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));
			}
		}
	/**
	 * inputText - Enter Text In The Edit Field
	 * @param by - Element Locator Address (Xpath, CSS, ID, Classname..etc)
	 * @param elementname - Element Name Where In Text Has To Be Entered
	 * @param data - Text To Be Entered
	 * @throws Exception
	 */
	public void inputText(By by, String elementname, String data) throws Exception 
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			MobileElement e1 = (MobileElement)wait.until(ExpectedConditions.elementToBeClickable(by));
			if (e1.isDisplayed()) 
			{
				e1.click();
				e1.clear();
				e1.sendKeys(data);
				logger.log(Status.PASS, "Entered the "+elementname+" Successfully");	
				logger.log(Status.PASS, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));
			}
		} 
		catch (Exception e) 
		{
			
			
			logger.log(Status.FAIL, "Failed to Enter the "+elementname+" ");	
			logger.log(Status.FAIL, "Screenshot below: "+logger.addScreenCaptureFromPath(takeScreenshot()));
		}
	}
	
	/**
	 * swipeInVerticalUnits - Swipe In Vertical Units Up or Down
	 * @param start_xd - Start X Position
	 * @param start_yd - Start Y Position
	 * @param end_xd   - End X Position
	 * @param end_yd   - End Y Position
	 * @param duration - Duration In Seconds To Wait Before Performing Action
	 * @throws InterruptedException 
	 * @example - example : swipe up : 0.5, 0.8, 0.5, 0.2 and swipe down: 0.5, 0.2, 0.5, 0.2
	 */
	@SuppressWarnings("rawtypes")
	public void swipeInVerticalUnits(double start_xd, double start_yd, double end_xd, double end_yd, int duration) throws InterruptedException 
	{
		
		Thread.sleep(5000);
		Dimension size = driver.manage().window().getSize();
		int start_x = (int)(size.width*start_xd);
		int start_y = (int)(size.height*start_yd);
		int end_x = (int)(size.width*end_xd);
		int end_y = (int)(size.height*end_yd);
		new TouchAction((AppiumDriver<MobileElement>)driver)
		.press(PointOption.point(start_x, start_y))
		.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(duration)))
		.moveTo(PointOption.point(end_x, end_y)).release().perform();
	}

	
	public void hideKeyboard(){
		driver.hideKeyboard();

		
	}
	}



