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
	 * Constructor
	 */
	public CoapServerTestApp() {
		super();
	}

	/**
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
