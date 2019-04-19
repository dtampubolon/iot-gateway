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
import neu.dtampubolon.connecteddevices.common.SensorData;
import neu.dtampubolon.connecteddevices.labs.module06.MqttClientConnector;

public class GatewayDeviceApp implements Observer {
	private static final Logger _Logger = Logger.getLogger(GatewayDeviceApp.class.getName());
	private static ConfigUtil config = ConfigUtil.getInstance();
	private boolean enable;
	private boolean oneShot = true;
	
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
	private String subscribeTopic2 = "Temperature-CSYE6530";
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
	private double normPitch = 345;
	
	//Temperature data
	private SensorData sd;
	
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
		mqttConn.subscribe(subscribeTopic2, 2);
		ubidotsMqtt.subscribe(valveTopic, 2);
		
//		ubidotsApi.sendPitchValue((double)360);

		int count=0;
		while(enable) {
			enable = true;
		}
	}

	@Override
	public void update(Observable o, Object data) {
		// TODO Auto-generated method stub
		if(o.equals(mqttConn)) {
			if(((String[]) data)[0].equals(subscribeTopic)) {
				pd = DataUtil.jsonToPitchData(((String[]) data)[1], true);
				_Logger.info("Gateway device:\nNew pitch reading received:" + pd);
				ubidotsApi.sendPitchValue((double) pd.getCurValue());
				
				if((pd.getCurValue() <= minPitch) && oneShot) {
					smtpConn.sendMail("tampubolon.d@husky.neu.edu", "ALERT: Water Level", "Water level has fallen below the minimum!");
					oneShot = false;
				}
			}
			
			else if(((String[]) data)[0].equals(subscribeTopic2)) {
				sd = DataUtil.jsonToSensorData(((String[]) data)[1], true);
				_Logger.info("Gateway device:\nNew Temperature reading received" + sd);
				ubidotsApi.sendTempValue((double) sd.getCurValue());
			}

		}
		
		else if(o.equals(ubidotsMqtt)) {
			int valveData = Integer.parseInt(((String[]) data)[1]);
			if(valveData != ledON) {
				ledON = valveData;
				mqttConn.publish(publishTopic, 0, String.valueOf(ledON));
				
				if(ledON==1) {
					System.out.println("Turning ON Valve(LED)...\n");
				}
				else {
					oneShot = true;
					System.out.println("Turning OFF Valve(LED)...\n");
				}
			}
		}
	}
	
}
