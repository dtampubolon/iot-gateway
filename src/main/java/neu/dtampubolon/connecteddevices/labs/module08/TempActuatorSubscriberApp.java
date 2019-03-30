package neu.dtampubolon.connecteddevices.labs.module08;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

import neu.dtampubolon.connecteddevices.common.DataUtil;
import neu.dtampubolon.connecteddevices.common.SensorData;
import neu.dtampubolon.connecteddevices.labs.module06.MqttClientConnector;


public class TempActuatorSubscriberApp {

	private static final Logger _Logger = Logger.getLogger(TempActuatorSubscriberApp.class.getName());
	private String brokerUrl, topic, payload;
	private MqttClientConnector _mqttClient;
	private SensorData sensorData;
	private DataUtil dataUtil = new DataUtil();
	private int qos;
	private String name;
	private String authToken;
	private String certFilePath;
	private ConfigUtil confUtil = ConfigUtil.getInstance();
	/**
	 * Constructor
	 */
	public TempActuatorSubscriberApp() {
		confUtil.loadConfig("C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-gateway\\src\\main\\java\\com\\labbenchstudios\\edu\\connecteddevices\\common\\ConnectedDevicesConfig.props");
		authToken = confUtil.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY); //Temporary authorization token
		brokerUrl = "ssl://things.ubidots.com:8883";
		certFilePath = "C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-gateway\\src\\main\\java\\neu\\dtampubolon\\connecteddevices\\common\\ubidots_cert.pem";
		sensorData = new SensorData();
		qos = 0;
		_mqttClient = new MqttClientConnector(brokerUrl, authToken, certFilePath, "");
		topic = "/v1.6/devices/thermostat/tempactuator/lv";
		
		
		/* For debugging purposes:
		 * brokerUrl = "tcp://things.ubidots.com:1883";
		_mqttClient = new MqttClientConnector(brokerUrl, authToken,certFilePath, authToken);
		*/
		
	}
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		TempActuatorSubscriberApp _App = new TempActuatorSubscriberApp();
		_App.run();
	}

	/**
	 * When this method is called, app connects to the broker and subscribes to a topic
	 */
	public void run() {
		try {
			_mqttClient.connect();
		} catch (KeyManagementException | NoSuchAlgorithmException | MqttException e) {
			e.printStackTrace(); //when exception is caught, print stack trace
		}
		
		_mqttClient.subscribe(topic, qos);

		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			e.printStackTrace(); //when exception is caught, print stack trace
		}
		
		
	}
}
