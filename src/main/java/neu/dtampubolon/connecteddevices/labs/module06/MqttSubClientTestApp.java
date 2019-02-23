/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module06;

import java.util.logging.Logger;
import neu.dtampubolon.connecteddevices.common.*;

import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * @author Doni Tampubolon
 *
 */
public class MqttSubClientTestApp {

	/**
	 * 
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MqttSubClientTestApp _App = new MqttSubClientTestApp();
		
		try {
			_App.run();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() throws MqttException {
		_mqttClient.connect();

		topic = "Topic-CSYE6530";
		
		//Subscribing to topic;
		_mqttClient.subscribe(topic, qos);
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//disconnect
		_mqttClient.disconnect();
	}

}
