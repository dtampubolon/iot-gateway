/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module06;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;

/**
 * @author Doni Tampubolon
 *
 */
public class MqttClientConnector implements MqttCallback{

	/**
	 * This class sets up and manages the connection between a client and a server(broker)
	 * All control packets are handled by an instance of this class
	 */
	
	private MqttClient client;
	private MqttConnectOptions conOpt;
	
	private String clientID;
	//private String topic;
	//private String payload;
	private String brokerUrl;
	private boolean clean = true;
	private int qos;
	private String lastRecMsg;
	
	//private String password;
	//private String username;
	
	public MqttClientConnector(String brokerUrl, String clientID) {
		// Constructor
		this.brokerUrl = brokerUrl;
		this.clientID = clientID;
		//this.password = password;
		//this.username = username; 
		
		conOpt = new MqttConnectOptions();
		conOpt.setCleanSession(clean);
	}
	
	public void connect() throws MqttException{
		client = new MqttClient(brokerUrl, clientID, new MemoryPersistence());
		client.setCallback(this);
		System.out.println("Connecting to " + brokerUrl + " with client ID " + clientID);
		client.connect(conOpt);
		System.out.println("Connected to " + brokerUrl + "\n");
	}
	
	public void disconnect() {
		System.out.println("Disconnecting from " + brokerUrl + " with client ID " + clientID);
		
		try {
			client.disconnect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Disconnected successfully from " + brokerUrl);
	}
	
	public void subscribe(String topic, int qos) {
		System.out.println("Subscribing to topic \"" + topic + "\" QoS: " + qos );
		
		try {
			client.subscribe(topic, qos);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	public void unsubscribe(String topic) {
		System.out.println("Unsubscribing from topic \"" + topic + "\"");
		
		try {
			client.unsubscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	public void publish(String topic, int qos, String payload) {
		System.out.println("Publishing to topic: " + topic + "\t QoS: " + qos);
		MqttMessage message = new MqttMessage(payload.getBytes());
		message.setQos(qos);
		
		try {
			client.publish(topic, message);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost because: " + cause);
		System.exit(1);
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:\t" +time +
                "  Topic:\t" + topic +
                "  Message:\t" + new String(message.getPayload()) +
                "  QoS:\t" + message.getQos());
		
		lastRecMsg = new String(message.getPayload());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("Message has been delivered to broker");
		
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}

	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	public int getQos() {
		return qos;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}
	
}
