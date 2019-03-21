/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module06;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import neu.dtampubolon.connecteddevices.common.*;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Doni Tampubolon
 *
 */
public class MqttSubClientTestApp {

	/**
	 * This class is an MQTT client that connects to an MQTT broker, subscribes to a topic and waits for new messages from the topic
	 */	
	private static final Logger _Logger = Logger.getLogger(MqttSubClientTestApp.class.getName());
	
	private String brokerUrl, topic, payload;
	private MqttClientConnector _mqttClient;
	private SensorData sensorData;
	private DataUtil dataUtil = new DataUtil();
	private int qos;
	private String name;
	
	public MqttSubClientTestApp() {
		//Constructor
		name = "CSYE6530JavaSubscriber";
		brokerUrl = "tcp://iot.eclipse.org:1883";
		_mqttClient = new MqttClientConnector(brokerUrl,name);
		sensorData = new SensorData();
		qos =2;
	}

	/**
	 * Main method of app
	 * @param args
	 */
	public static void main(String[] args) {
		MqttSubClientTestApp _App = new MqttSubClientTestApp();
		
		try {
			_App.run();
		} catch (MqttException | KeyManagementException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
	}
	
	//This method runs the app
	public void run() throws MqttException, KeyManagementException, NoSuchAlgorithmException {
		_mqttClient.connect();

		topic = "Topic-CSYE6530";
		
		//Subscribing to topic;
		_mqttClient.subscribe(topic, qos);
		
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Unsubscribe
		_mqttClient.unsubscribe(topic);
		
		//disconnect
		_mqttClient.disconnect();
	}

}
