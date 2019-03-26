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

import com.labbenchstudios.edu.connecteddevices.common.CertManagementUtil;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.net.ssl.SSLSocketFactory;
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
	private boolean clean = true; //decides whether previous session's data are saved by the broker(false) or not (true).
	private int qos;
	private String lastRecMsg;
	private String certFilePath;
	private String username; //Ubidots auth TOKEN
	private String password =""; //blank for ubidots
	private SSLSocketFactory sslSockFac;
	private boolean tls = false;
	
	/**
	 * Constructor
	 * @param brokerUrl
	 * @param clientID
	 */
	public MqttClientConnector(String brokerUrl, String clientID) {
		this.brokerUrl = brokerUrl;
		this.clientID = clientID;
		
		conOpt = new MqttConnectOptions();
		conOpt.setCleanSession(clean);
	}
	
	/**
	 * Alternate constructor
	 * @param brokerUrl
	 * @param authToken
	 * @param certFilePath
	 */
	public MqttClientConnector(String brokerUrl, String authToken, String certFilePath, String password) {
		this.brokerUrl = brokerUrl;
		this.clientID = MqttClient.generateClientId();
		this.certFilePath = certFilePath;
		this.username = authToken;
		this.password = password;
		//sslSockFac = CertManagementUtil.getInstance().loadCertificate(certFilePath);

		conOpt = new MqttConnectOptions();
		conOpt.setCleanSession(clean);
		
		tls = true;
	}
	
	/**
	 * This method is called to initiate connection with the broker
	 * @throws MqttException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public void connect() throws MqttException, NoSuchAlgorithmException, KeyManagementException{
		//Don't use MemoryPersistence to store data if reliability is required i.e. when clean session is set to false.
		//when clean session is false, use MqttDefaultFilePersistence
		if(tls) {
			client = new MqttClient(brokerUrl, clientID, new MemoryPersistence());
			client.setCallback(this);
			conOpt.setUserName(username);
			conOpt.setPassword(password.toCharArray());
			
			//this next line of code is only needed if certificate has not been loaded as trusted cert by the JVM
			//conOpt.setSocketFactory(sslSockFac);
		}
		else {
			client = new MqttClient(brokerUrl, clientID, new MemoryPersistence());
			client.setCallback(this);
		}
		System.out.println("Connecting to " + brokerUrl + " with client ID " + clientID);
		client.connect(conOpt);
		System.out.println("Connected to " + brokerUrl + "\n");
	}
	
	/**
	 * This method is called to disconnect from the broker
	 */
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
    
	/**
     * Subscribe to a topic on an MQTT server
     * Once subscribed this method waits for the messages to arrive from the server
     * that match the subscription. It continues listening for messages until the enter key is
     * pressed.
     * @param topic: String. The topic to subscribe to
     * @param qos: int. The quality of service for this subscription
     */
	public void subscribe(String topic, int qos) {
		System.out.println("Subscribing to topic \"" + topic + "\" QoS: " + qos );
		
		try {
			client.subscribe(topic, qos);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		System.out.println("Subscription successful!");
	}
	
	/**
	 * This method is called to unsubscribe from a topic
	 * @param topic
	 */
	public void unsubscribe(String topic) {
		System.out.println("Unsubscribing from topic \"" + topic + "\"");
		
		try {
			client.unsubscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * This function is called in order to publish a message to an MQTT server
     * @param topic the name of the topic to publish to
     * @param qos the quality of service to delivery the message at (0,1,2)
     * @param payload to send to the MQTT server
     */
	public void publish(String topic, int qos, String payload) {
		System.out.println("Publishing " + payload + " to topic: " + topic + "\t QoS: " + qos);
		MqttMessage message = new MqttMessage(payload.getBytes());
		message.setQos(qos);
		
		try {
			client.publish(topic, message);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	//Callback methods
	/**
	 * This method is called when connection to MQTT broker is lost
	 */
	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost because: " + cause);
		System.exit(1);
	}

	/**
	 * This method is called when a message from the broker arrives 
	 * that matches the subscription made by the client
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("New message received:");
		System.out.println("Time:\t" +time +
                "  \nTopic:\t" + topic +
                "  \nMessage:\t" + new String(message.getPayload()) +
                "  \nQoS:\t" + message.getQos());
		
		lastRecMsg = new String(message.getPayload());
	}

	/**
	 * This method is called when a message has been delivered to the broker
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("Message has been delivered to broker");
		
	}

	//This method returns client ID
	public String getClientID() {
		return clientID;
	}

	//This method is used to set client ID
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	//This method is used to get current broker URL
	public String getBrokerUrl() {
		return brokerUrl;
	}

	//This method is used to set a new broker URL
	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}

	//This method is used to get the QoS that has been set
	public int getQos() {
		return qos;
	}
	
	//This method is used to set the Quality of Service (QoS) of the MQTT connection
	public void setQos(int qos) {
		this.qos = qos;
	}
	
}
