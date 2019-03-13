/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module07;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * @author Doni Tampubolon
 *
 */
public class TempResourceHandler extends CoapResource {

	private String name;
	private static final Logger _Logger = Logger.getLogger(TempResourceHandler.class.getName());
	/**
	 * 
	 */
	public TempResourceHandler(String name) {
		super(name, true);
		this.name = name;
		getAttributes().setTitle(name.replace("/", "").toUpperCase() + " FILES");
		
	}
	
	//Public methods
	@Override
	public void handleGET(CoapExchange exchange) {
	     exchange.respond("GET request received");
	   }
	
	@Override
	public void handlePOST(CoapExchange exchange) {
	     exchange.accept();
	     exchange.respond(ResponseCode.CREATED, "Resource CREATED");
	   }
	
	@Override
	public void handlePUT(CoapExchange exchange) {
	     // ...
	     exchange.respond(ResponseCode.CHANGED, "Resource CHANGED");
	     changed(); // notify all observers
	}
	
	@Override
	public void handleDELETE(CoapExchange exchange) {
	     delete();
	     exchange.respond(ResponseCode.DELETED, "Resource DELETED");
	     _Logger.info("Received DELETE request from client");
	}
}
