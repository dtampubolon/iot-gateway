package neu.dtampubolon.connecteddevices.labs.module07;

import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

public class CoapServerConnector {

	private static final Logger _Logger = Logger.getLogger(CoapServerConnector.class.getName());
	private CoapServer _coapServer;
	private String resourceName;
	/**
	 * Constructor
	 */
	public CoapServerConnector() {
		super();
		resourceName = "json/";
	}
	
	/**
	 * 
	 * @param resource
	 */
	public void addResource(CoapResource resource) {
		if(resource != null) {
			_coapServer.add(resource);
		}
	}
	
	public void start() {
		if(_coapServer == null) {
			_Logger.info("Creating CoAP server instance and 'temp' resource handler...");
			
			_coapServer = new CoapServer();
			
			TempResourceHandler tempHandler = new TempResourceHandler(resourceName);
			
			_coapServer.add(tempHandler);
		}
	}
}
