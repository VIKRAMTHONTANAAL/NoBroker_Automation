package properties;
import org.openqa.selenium.By;
public class NoBroker_Search {

	//Drop down City
	public static By ddCity=By.id("android:id/text1");

	//Bangalore city xpath
	public static By weLstBangalore=By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[1]");

	//Localities field
	public static By txtLocalities=By.id("com.nobroker.app:id/localityAutoCompleteTxt");

	//xpath Autosuggestion marathahalli
	public static By autoSuggestionMarathahalli=By.xpath("//*[@text='Marathahalli' and (./preceding-sibling::* | ./following-sibling::*)[@text='Bengaluru, Karnataka, India']]");

	//xpath Autosuggestion HSR Layout
	public static By autoSuggestionHSRLayout=By.xpath("//*[@text='HSR Layout']");


	//checkbox Include nearby properties
	public static By chkboxNearByProperties=By.xpath("//*[@text='Include nearby properties']");

	//BUTTON 2BHK
	public static By btn2BHK=By.xpath("//*[@text='2 BHK']");

	//BUTTON 3BHK
	public static By btn3BHK=By.xpath("//*[@text='3 BHK']");

	//BUTTON Search
	public static By btnSearch=By.xpath("//*[@text='SEARCH']");
}
