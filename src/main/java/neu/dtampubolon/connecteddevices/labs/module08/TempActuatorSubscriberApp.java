package neu.dtampubolon.connecteddevices.labs.module08;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;

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
	
	//Constructor
	public TempActuatorSubscriberApp() {
		authToken = "A1E-Sv1nHOuzR8M950zKv6yWTipuRMjTcN";
		brokerUrl = "ssl://things.ubidots.com:8883";
		certFilePath = "C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-gateway\\src\\main\\java\\neu\\dtampubolon\\connecteddevices\\common\\ubidots_cert.pem";
		sensorData = new SensorData();
		qos = 2;
		_mqttClient = new MqttClientConnector(brokerUrl, authToken, certFilePath);
		topic = "/v1.6/devices/thermostat/tempactuator/lv";
		
	}
	
	//Main method
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_mqttClient.subscribe(topic, 1);
				
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
