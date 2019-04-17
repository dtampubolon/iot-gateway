package neu.dtampubolon.connecteddevices.project;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

import neu.dtampubolon.connecteddevices.common.DataUtil;
import neu.dtampubolon.connecteddevices.common.PitchData;
import neu.dtampubolon.connecteddevices.labs.module06.MqttClientConnector;

public class GatewayDeviceApp implements Observer {
	private static final Logger _Logger = Logger.getLogger(GatewayDeviceApp.class.getName());
	private static ConfigUtil config = ConfigUtil.getInstance();
	private boolean enable;
	
	//SMTP variables
	private static String[] mailRecipient = new String[] {"tampubolon.d@husky.neu.edu"};
	private String mailSubject;
	private String mailText;
	private static SmtpConnector smtpConn;
	
	//MQTT	variables
	private static MqttClientConnector mqttConn;
	private String brokerUrl = "tcp://iot.eclipse.org:1883";
	private String clientName = "GatewayDevice-CSYE6530";
	private String subscribeTopic = "PitchData-CSYE6530";
	private String publishTopic = "LED-CSYE6530";
	private String authToken;
	
	//Ubidots
	private String ubidotsUrl = "ssl://things.ubidots.com:8883";
	private String certFilePath = "C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-gateway\\src\\main\\java\\neu\\dtampubolon\\connecteddevices\\common\\ubidots_cert.pem";
	private static MqttClientConnector ubidotsMqtt;
	private static ApiConnector ubidotsApi;
	private String valveTopic = "/v1.6/devices/finalproject/valve/lv";
	//Actuator
	private int ledON = 0;
	
	//Pitch data
	private PitchData pd = new PitchData();
	private double minPitch = 310;
	
	public GatewayDeviceApp() {
		//Load configuration file
		config.loadConfig();
		
		mqttConn = new MqttClientConnector(brokerUrl, clientName);
		smtpConn = new SmtpConnector("dtampubolon.iot", config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY));

		authToken = config.getProperty(ConfigConst.UBIDOTS_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY); //Temporary authorization token
		ubidotsMqtt = new MqttClientConnector(ubidotsUrl, authToken, certFilePath, "");
		
		ubidotsApi = new ApiConnector();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GatewayDeviceApp app = new GatewayDeviceApp();
		
		//Adding this app as an observer to mqttConn callbacks
		mqttConn.addObserver(app);
		ubidotsMqtt.addObserver(app);
		
		//smtpConn.sendMail(mailRecipient, "Test Subject", "This is a test email");
		app.run();
	}
	
	public void run() {
		try {
			mqttConn.connect();
			ubidotsMqtt.connect();
		} catch (KeyManagementException | NoSuchAlgorithmException | MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mqttConn.subscribe(subscribeTopic, 2);
		ubidotsMqtt.subscribe(valveTopic, 2);
		
//		ubidotsApi.sendPitchValue((double)360);

		int count=0;
		while(enable) {
			count=0*2;
		}
	}

	@Override
	public void update(Observable o, Object data) {
		// TODO Auto-generated method stub
		if(o.equals(mqttConn)) {
			pd = DataUtil.jsonToPitchData((String) data, true);
			_Logger.info("Gateway device: New Data Received: " + pd);
			ubidotsApi.sendPitchValue((double) pd.getCurValue());
		}
		
		else if(o.equals(ubidotsMqtt)) {
			int valveData = Integer.parseInt((String) data);
			if(valveData != ledON) {
				ledON = valveData;
				mqttConn.publish(publishTopic, 0, String.valueOf(ledON));
				
				if(ledON==1) {
					System.out.println("Turning ON Valve(LED)...\n");
				}
				else {
					System.out.println("Turning OFF Valve(LED)...\n");
				}
			}
		}
	}
	
}
