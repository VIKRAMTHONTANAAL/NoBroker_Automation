package pages;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import baseClassLibrary.BaseClass;
import keyWordLibrary.KeyWordLibrary;
import properties.NoBroker_AllowUsTo;
import properties.NoBroker_Home;
import properties.NoBroker_Login;
import properties.NoBroker_Search;
import properties.NoBroker_SearchResult;
import properties.NoBroker_SearchResultDetail;
import properties.NoBroker_SuggestAnEdit;
import properties.NoBroker_WhatsWrong;
public class NoBroker_BuyPropertyPage extends BaseClass{


	KeyWordLibrary kl=new KeyWordLibrary();

	@BeforeMethod
	public void beforemethod(String TestCaseName,String TestCaseObjective,String  appPackage,String appActivity) {
		kl.startReport(TestCaseName, this.getClass().getSimpleName(),TestCaseObjective,appPackage, appActivity);

	}
	//********************************************************************************************************************************************************
	//********************************************************************************************************************************************************    
	@Test
	//Test Case :  NoBroker_BuyProperty_ReportAbuse
	//Objectives: The purpose of this Test Case is to Verify Abuse Report related functionality for the 'Buy' property
	//Author: Vikram M Thonthanaal
	//Date: 10/12/2020

	public void NoBroker_BuyProperty_ReportAbuse(String deviceName, String platformName, String appPackage, String appActivity, String searchLocation1, String searchLocation2, String username, String password, String suggestionNote) throws Exception {	

		try{
			//Launch No Broker Application
			kl.launchApplication(deviceName, platformName, appPackage, appActivity);

			//Tap on 'Continue' button on 'Allow us to' page
			kl.tapElement(NoBroker_AllowUsTo.btnContinue, "Continue");

			//Verify that No Broker Homepage is displayed
			kl.verifyPageDisplayed(NoBroker_Home.btnBuy, "No broker Home");

			//Tap on 'Buy' button
			kl.tapElement(NoBroker_Home.btnBuy, "Buy Button");

			//Tap on 'Search' box
			kl.tapElement(NoBroker_Home.txtSearch,"Search box");

			//Tap on the drop down
			kl.tapElement(NoBroker_Search.ddCity, "City Dropdown");

			//Tap on 'Bangalore' from the list displayed
			kl.tapElement(NoBroker_Search.weLstBangalore, "Bangalore from dropdown list");

			//Input 'Marathahalli' on Localities search bar
			kl.inputText(NoBroker_Search.txtLocalities,"Localities", searchLocation1);

			Thread.sleep(6000);
			//Tap on Marathahalli suggestion
			kl.tapElement(NoBroker_Search.autoSuggestionMarathahalli,"Auto-suggestion Marathahalli");

			//Input 'HSR Layout' on Localities search bar
			kl.inputText(NoBroker_Search.txtLocalities,"Localities", searchLocation2);


			Thread.sleep(6000);
			//Tap on HSR Layout suggestion			
			kl.tapElement(NoBroker_Search.autoSuggestionHSRLayout,"Auto-suggestion HSR Layout");
			
			kl.hideKeyboard();

			//Tap on the 'Include nearby properties' checkbox
			kl.tapElement(NoBroker_Search.chkboxNearByProperties,"'Include nearby properties' checkbox");

			//Tap on 2 BHK BUTTON
			kl.tapElement(NoBroker_Search.btn2BHK,"'2BHK Button'");

			//Tap on 3 BHK BUTTON
			kl.tapElement(NoBroker_Search.btn3BHK,"'3BHK Button'");

			//Click on 'Search' button 
			kl.tapElement(NoBroker_Search.btnSearch, "Search Button");

			//Swipe down 
			//Swipe Down Till The Fourth Tile
			for (int i = 0; i <=2;i++)
				kl.swipeInVerticalUnits(0.5, 0.9, 0.5, 0.2, 2);

			//Click on '4th' property
			kl.tapElement(NoBroker_SearchResult.weTxt4thProperty, "4th Property");


			//Swipe down 

			for (int i = 0; i <=7;i++)
				kl.swipeInVerticalUnits(0.5, 0.9, 0.5, 0.2, 2);
			kl.tapElement(NoBroker_SearchResultDetail.btnWrongInfo, "Wrong Info");

			//Input Phone Number
			kl.inputText(NoBroker_Login.txtPhoneNumber, "Phone Number Field",username);

			//Click on 'I've Password' to stop loading for OTP
			kl.tapElement(NoBroker_Login.radioButtonIvePassword, "radioButton I've Password");

			//Click on 'I've Password' 
			kl.tapElement(NoBroker_Login.radioButtonIvePassword, "radioButton I've Password");

			//Input Password
			kl.inputText(NoBroker_Login.txtPassword,"Password", password);

			//Click on 'Continue' button
			kl.tapElement(NoBroker_Login.btnContinue, "Contiue button");

			//Click on All the check box on What's wrong page
			kl.tapElement(NoBroker_WhatsWrong.chkboxLocation, "Checkbox Location");
			kl.tapElement(NoBroker_WhatsWrong.chkboxFakePhotos, "Checkbox Fake photos");
			kl.tapElement(NoBroker_WhatsWrong.chkboxBHKType, "Checkbox BHK Type");
			kl.tapElement(NoBroker_WhatsWrong.chkboxAvailabilityDate, "Checkbox Availability Date");
			kl.tapElement(NoBroker_WhatsWrong.chkboxPrice, "Checkbox Price");
			kl.tapElement(NoBroker_WhatsWrong.chkboxOther, "Checkbox Other");

			//Click on 'Report' button
			kl.tapElement(NoBroker_WhatsWrong.btnReport, "Button Report");

			kl.hideKeyboard();
			for (int i = 0; i <=1;i++)
				kl.swipeInVerticalUnits(0.5, 0.9, 0.5, 0.2, 2);
			//Click on '2BHK' dropdown
			kl.tapElement(NoBroker_SuggestAnEdit.dd2BHK, "2BHK dropdown");

			//Click on '4BHK+' 
			kl.tapElement(NoBroker_SuggestAnEdit.dd4BHKPlus, "4BHK+ item");

			kl.hideKeyboard();

			//Swipe Down
			for (int i = 0; i <=3;i++)
				kl.swipeInVerticalUnits(0.5, 0.9, 0.5, 0.2, 2);
			kl.inputText(NoBroker_SuggestAnEdit.weTxtAddANote,"Add A Note", suggestionNote);
			
			//Click on 'Save Changes'
			kl.tapElement(NoBroker_SuggestAnEdit.btnSaveChanges, "Save Changes button");

			
			//Verify that No Broker Thank You For The Feedback is displayed
			kl.verifyPageDisplayed(NoBroker_SuggestAnEdit.weTxtThankYouForYourFeedback, "No broker Thank you for Your Feedback");


		}

		catch(Exception e){
			kl.endReport();

		}

	}



	@AfterMethod
	public void afterMethod() {

		kl.endReport();
	}




}

