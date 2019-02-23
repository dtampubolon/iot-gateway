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
public class MqttPubClientTestApp {

	/**
	 * 
	 */
	
	private static final Logger _Logger = Logger.getLogger(MqttPubClientTestApp.class.getName());
	
	private String brokerUrl, topic, payload;
	private MqttClientConnector _mqttClient;
	private SensorData sensorData;
	private DataUtil dataUtil = new DataUtil();
	private int qos;
	private String name;
	
	public MqttPubClientTestApp() {
		//Constructor
		name = "CSYE6530JavaPublisher";
		brokerUrl = "tcp://iot.eclipse.org:1883";
		_mqttClient = new MqttClientConnector(brokerUrl,name);
		sensorData = new SensorData();
		qos = 2;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MqttPubClientTestApp _App = new MqttPubClientTestApp();
		
		try {
			_App.run();
		} catch (MqttException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() throws MqttException, InterruptedException {
		_mqttClient.connect();
		topic = "Topic-CSYE6530";
		
		//Adding values to sensorData
		sensorData.addValue(31.8);
		sensorData.addValue(23.5);
		sensorData.addValue(22.1);
		sensorData.addValue(25.5);
		sensorData.addValue(26.9);
		
		//Convert sensorData to json string
		payload = dataUtil.sensorDataToJson(sensorData);
		
		_mqttClient.publish(topic, qos, payload);
		
		Thread.sleep(30000);
		
		_mqttClient.disconnect();
	}

}
