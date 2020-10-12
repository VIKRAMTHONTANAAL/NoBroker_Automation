package tests;

import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import baseClassLibrary.BaseClass;
import excelLibrary.ExcelLib;
import jxl.read.biff.BiffException;
import pages.NoBroker_BuyPropertyPage;

public class NoBroker_BuyProperty_ReportAbuse extends BaseClass{

	@DataProvider
	public Object[][] data() throws BiffException, IOException {
		excelLibrary.ExcelLib xl = new ExcelLib("Android", this.getClass().getSimpleName());
		return xl.getTestdata();
	}


	private static String TestCaseObjective="Verify Abuse Report related functionality for the 'Buy' property";

	@Test(dataProvider = "data")
	public void run(String deviceName, String platformName, String appPackage, String appActivity, String searchLocation1, String searchLocation2, String username, String password, String suggestionNote) throws Exception
	{	

		NoBroker_BuyPropertyPage nbr=new NoBroker_BuyPropertyPage();
		nbr.beforemethod(this.getClass().getSimpleName(), TestCaseObjective,  appPackage,appActivity);		
		nbr.NoBroker_BuyProperty_ReportAbuse(deviceName, platformName, appPackage,  appActivity,  searchLocation1,  searchLocation2,  username,  password,  suggestionNote);
		nbr.afterMethod();
	}

}


