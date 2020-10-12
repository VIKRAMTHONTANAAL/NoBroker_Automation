package properties;
import org.openqa.selenium.By;
public class NoBroker_Login {

	//Input Field Phone number
	public static By txtPhoneNumber=By.id("com.nobroker.app:id/et_signup_phone");
	
	//Radio button 'I've Password'
	public static By radioButtonIvePassword=By.xpath("//*[@text=concat('I', \"'\", 've Password')]");

	//Input Field Password
	public static By txtPassword=By.xpath("//*[@id='et_signup_pwd']");

	//Login Button
	public static By btnContinue=By.xpath("//*[@text='Continue']");

}
