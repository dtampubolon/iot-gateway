package neu.dtampubolon.connecteddevices.project;

import com.labbenchstudios.edu.connecteddevices.common.ConfigConst;
import com.labbenchstudios.edu.connecteddevices.common.ConfigUtil;

public class GatewayDeviceApp {

	private static ConfigUtil config = ConfigUtil.getInstance();
	private static String[] mailRecipient = new String[] {"tampubolon.d@husky.neu.edu"};
	private String mailSubject;
	private String mailText;
	
	public GatewayDeviceApp() {
		// TODO Auto-generated constructor stub
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		config.loadConfig();
		SmtpConnector conn = new SmtpConnector("dtampubolon.iot", config.getProperty(ConfigConst.SMTP_CLOUD_SECTION, ConfigConst.USER_AUTH_TOKEN_KEY));
		
		conn.sendMail(mailRecipient, "Test Subject", "This is a test email");
	}

}
