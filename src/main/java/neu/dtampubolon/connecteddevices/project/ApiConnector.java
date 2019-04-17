package neu.dtampubolon.connecteddevices.project;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;
import com.ubidots.*;

public class ApiConnector {

	private String apiToken;
	private ApiClient api;
	private Variable temperature;
	private Variable pitch;
	private Variable valve;
	private static ConfigUtil confUtil = ConfigUtil.getInstance();
	
	/**
	 * Constructor
	 */
	public ApiConnector() {
		confUtil.loadConfig("C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-gateway\\src\\main\\java\\com\\labbenchstudios\\edu\\connecteddevices\\common\\ConnectedDevicesConfig.props");
		apiToken = confUtil.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY); //Temporary authorization token
		api = new ApiClient(apiToken);
		DataSource[] dataSourceArr = api.fromToken(apiToken).getDataSources();
		temperature = api.getVariable("5ca8a87dc03f9708c6932c96");
		pitch = api.getVariable("5ca8a86bc03f97080556d509");
		valve = api.getVariable("5ca8a8c5c03f97090b0300b1");
	}
	
	/*//For testing only
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApiConnector test = new ApiConnector();
		test.sendPitchValue(360.0);
	}*/
	
	public void sendPitchValue(double value) {
		 pitch.saveValue(value);
	}
	
	public void sendTempValue(double value) {
		temperature.saveValue(value);
	}
	
	public void setValve(int value) {
		valve.saveValue(value);
	}
}
