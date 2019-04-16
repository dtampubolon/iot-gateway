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
	private String mqttTopic = "PitchData-CSYE6530";
	
	//Actuator
	private boolean ledON = false;
	
	//Pitch data
	PitchData pd = new PitchData();
	
	public GatewayDeviceApp() {
		mqttConn = new MqttClientConnector(brokerUrl, clientName);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GatewayDeviceApp app = new GatewayDeviceApp();
		config.loadConfig();
		
		//Adding this app as an observer to mqttConn callbacks
		mqttConn.addObserver(app);
		
		smtpConn = new SmtpConnector("dtampubolon.iot", config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY));
		//smtpConn.sendMail(mailRecipient, "Test Subject", "This is a test email");
		app.run();
	}
	
	public void run() {
		try {
			mqttConn.connect();
		} catch (KeyManagementException | NoSuchAlgorithmException | MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mqttConn.subscribe(mqttTopic, 2);
		int count=0;
		while(enable) {
			count=0*2;
		}
	}

	@Override
	public void update(Observable o, Object jsonData) {
		// TODO Auto-generated method stub
		pd = DataUtil.jsonToPitchData((String) jsonData, true);
		_Logger.info("Gateway device: New Data Received: " + pd);
	}
	
}
