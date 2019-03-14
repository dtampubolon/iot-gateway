/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module07;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.*;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.*;

/**
 * @author Doni Tampubolon
 *
 */
public class TempResourceHandler extends CoapResource {

	private String name;
	private static final Logger _Logger = Logger.getLogger(TempResourceHandler.class.getName());
	private byte[] data = null;
	private int dataCf = TEXT_PLAIN;
	private String payload = null;
	//private boolean wasUpdated = false;
	/**
	 * 
	 */
	public TempResourceHandler(String name) {
		super(name, true);
		this.name = name;
		getAttributes().setTitle("Temp files");
		getAttributes().addResourceType("temp");
		setObserveType(Type.CON);
	}
	
	//Public methods
	@Override
	public void handleGET(CoapExchange exchange) {
	     exchange.respond(CONTENT, payload, dataCf);
	   }
	
	@Override
	public void handlePOST(CoapExchange exchange) {
	     exchange.accept();
	     payload = exchange.getRequestText();
	     exchange.respond(ResponseCode.CREATED, "Resource CREATED");
	   }
	
	@Override
	public void handlePUT(CoapExchange exchange) {
	     // ...
	     exchange.respond(ResponseCode.CHANGED, "Resource CHANGED");
	     payload = exchange.getRequestText();
	     changed(); // notify all observers
	}
	
	@Override
	public void handleDELETE(CoapExchange exchange) {
	     delete();
	     exchange.respond(ResponseCode.DELETED, "Resource DELETED");
	     _Logger.info("Received DELETE request from client");
	}
	
	/*
	 * Convenience function to store data contained in a 
	 * PUT/POST-Request. Notifies observing endpoints about
	 * the change of its contents.
	 */
	private synchronized void storeData(byte[] payload, int format) {

		//wasUpdated = true;
		
		if (format != dataCf) {
			clearAndNotifyObserveRelations(NOT_ACCEPTABLE);
		}
		
		// set payload and content type
		data = payload;
		dataCf = format;

		getAttributes().clearContentType();
		getAttributes().addContentType(dataCf);
		
		// signal that resource state changed
		changed();
	}
}
