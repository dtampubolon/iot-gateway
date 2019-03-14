package neu.dtampubolon.connecteddevices.labs.module07;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.logging.Logger;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.core.network.EndpointManager;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.ConcurrentCoapResource;

public class CoapServerConnector {

	private static final Logger _Logger = Logger.getLogger(CoapServerConnector.class.getName());
	private CoapServer _coapServer=null;
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
	
	private void addEndpoints() {
    	for (InetAddress addr : EndpointManager.getEndpointManager().getNetworkInterfaces()) {
    		// only binds to IPv4 addresses and localhost
			if (addr instanceof Inet4Address || addr.isLoopbackAddress()) {
				InetSocketAddress bindToAddress = new InetSocketAddress(addr, 5683);
				_coapServer.addEndpoint(new CoapEndpoint(bindToAddress));
			}
		}
    }
	
	public void start() {
		if(_coapServer == null) {
			_Logger.info("Creating CoAP server instance and 'temp' resource handler...");
			
			_coapServer = new CoapServer();
			addEndpoints();
			
			TempResourceHandler tempHandler = new TempResourceHandler(resourceName);
			
			_coapServer.add(tempHandler);
			
			_Logger.info("Starting CoAP server...");
			_coapServer.start();
		}
	}
	public void stop() {
		_Logger.info("Stopping CoAP server...");
		_coapServer.stop();
	}
}
