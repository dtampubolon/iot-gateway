/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module08;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;
import com.ubidots.*;

/**
 * @author Doni Tampubolon
 *
 */
public class TempSensorCloudPublisherApp {

	private String apiToken;
	private ApiClient api;
	private DataSource thermostat;
	private Variable tempSensor;
	private static ConfigUtil confUtil = ConfigUtil.getInstance();
	/**
	 * Constructor for App
	 */
	public TempSensorCloudPublisherApp() {
		confUtil.loadConfig("C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-gateway\\src\\main\\java\\com\\labbenchstudios\\edu\\connecteddevices\\common\\ConnectedDevicesConfig.props");
		apiToken = confUtil.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY); //Temporary authorization token
		api = new ApiClient(apiToken);
		DataSource[] dataSourceArr = api.fromToken(apiToken).getDataSources();
		thermostat = api.getDataSource("5c9258b7c03f975885d64c1a");
		tempSensor = api.getVariable("5c92595dc03f9759864a3534");
	}

	/** Main method
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TempSensorCloudPublisherApp _App = new TempSensorCloudPublisherApp();
		_App.sendNewTempReading();
	}
	
	/**
	 * Periodically send new data to tempsensor variable on Ubidots
	 */
	public void sendNewTempReading() {
		TimerTask sendReading = new TimerTask() {
			public void run() {
				double reading = Math.random() *30 + 1;
				System.out.println("Time: " + LocalDateTime.now());
				System.out.println("Sending new temperature reading: " + reading + "degree celsius\n");
				tempSensor.saveValue(reading);
			}
		};
		
		Timer timer = new Timer();
		long delay =  1000L; //delay before the first reading transmission
		long period = 1000L * 60L * 5L; //5 minutes interval between data transmission
		timer.scheduleAtFixedRate(sendReading, delay, period);	
	}

}
