/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module07;

import java.util.logging.Logger;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import neu.dtampubolon.connecteddevices.common.*;

/**
 * @author Doni Tampubolon
 *
 */
public class CoapClientTestApp {

	private static final Logger _Logger = Logger.getLogger(CoapClientTestApp.class.getName());
	private static CoapClientTestApp app;
	private static CoapClientConnector _coapClient;
	private static SensorData sensorData;
	private static DataUtil dataUtil;
	private String payload;
	/**
	 * Constructor
	 */
	public CoapClientTestApp() {
		super();
		sensorData = new SensorData();
		dataUtil = new DataUtil();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Adding values to sensorData
		app = new CoapClientTestApp();
		
		try {
			app.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Public methods
	/**
	 * Connect to the CoAP server
	 */
	public void start() {
		sensorData.addValue(31.8);
		sensorData.addValue(23.5);
		sensorData.addValue(22.1);
		sensorData.addValue(25.5);
		sensorData.addValue(26.9);
		
		payload = dataUtil.sensorDataToJson(sensorData);
		
		_coapClient = new CoapClientConnector("127.0.0.1", false);
		//System.out.println("Sending discover request..");
		_coapClient.discoverResources();
		
		//System.out.println("Sending get request..");
		//_coapClient.sendGetRequest("json");
		//_coapClient.sendPostRequest("json", payload);
		//NOTE: has to stop calling initClient for every request
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
