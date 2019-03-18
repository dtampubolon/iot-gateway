/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module07;

/**
 * @author Doni Tampubolon
 *
 */
public class CoapServerTestApp {

	private static CoapServerConnector _coapServer;
	private static CoapServerTestApp app;
	/**
	 * This app is used to test the CoAP server functionalities
	 * Constructor
	 */
	public CoapServerTestApp() {
		super();
	}

	/** Main method
	 * @param args
	 */
	public static void main(String[] args) {
		app = new CoapServerTestApp();
		
		try {
			app.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Public methods
	/**
	 * This method is used to start the CoAP Server
	 */
	public void start() {
		_coapServer = new CoapServerConnector();
		_coapServer.start();
	}

}
