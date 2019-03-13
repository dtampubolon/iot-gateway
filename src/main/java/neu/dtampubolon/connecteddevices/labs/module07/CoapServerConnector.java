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
	
	public CoapServerConnector() {
		// TODO Auto-generated constructor stub
		super()
	}

}
