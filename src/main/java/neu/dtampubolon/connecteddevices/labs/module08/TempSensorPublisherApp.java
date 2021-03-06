package neu.dtampubolon.connecteddevices.labs.module08;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

import neu.dtampubolon.connecteddevices.common.DataUtil;
import neu.dtampubolon.connecteddevices.common.SensorData;
import neu.dtampubolon.connecteddevices.labs.module06.MqttClientConnector;

public class TempSensorPublisherApp {
	
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
	public TempSensorPublisherApp() {
		confUtil.loadConfig("C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-gateway\\src\\main\\java\\com\\labbenchstudios\\edu\\connecteddevices\\common\\ConnectedDevicesConfig.props");
		authToken = confUtil.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY); //Temporary authorization token
		brokerUrl = "ssl://things.ubidots.com:8883";
		certFilePath = "C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-gateway\\src\\main\\java\\neu\\dtampubolon\\connecteddevices\\common\\ubidots_cert.pem";
		sensorData = new SensorData();
		qos = 1;
		_mqttClient = new MqttClientConnector(brokerUrl, authToken, certFilePath, "");
		topic = "/v1.6/devices/thermostat";
	}

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		TempSensorPublisherApp _App = new TempSensorPublisherApp();
		_App.run();
	}
	
	/**
	 * Periodically send new data to tempsensor variable on Ubidots
	 */
	public void sendNewTempReading() {
		TimerTask sendReading = new TimerTask() {
			int count = 0;
			public void run() {
				double reading = Math.random() *30 + 1;
				System.out.println("Sending new temperature reading: " + reading + "degree celsius");
				String payload = "{\"tempsensor\": "+ reading +"}";
				System.out.println(payload);
				_mqttClient.publish(topic, qos, payload);
				count++;
			}
		};
		Timer timer = new Timer();
		long delay =  1000L; //delay before the first reading transmission
		long period = 1000L * 60L * 5L; //5 minutes interval between data transmission
		timer.scheduleAtFixedRate(sendReading, delay, period);	
	}
	
	public void run() {
		try {
			_mqttClient.connect();
			sendNewTempReading();
		} catch (KeyManagementException | NoSuchAlgorithmException | MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
