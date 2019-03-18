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
import neu.dtampubolon.connecteddevices.common.*;
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
	private SensorData sd;
	private DataUtil du = new DataUtil();
	//private boolean wasUpdated = false;
	
	/**
	 * Constructor
	 * @param name: String name of resource
	 */
	public TempResourceHandler(String name) {
		super(name, true);
		this.name = name;
		getAttributes().setTitle("Temp files");
		getAttributes().addResourceType("temp");
		setObserveType(Type.CON);
	}
	
	//Public methods
	
	/**
	 * This method handles GET requests that come from the clients
	 * @param exchange: CoapExchange
	 */
	@Override
	public void handleGET(CoapExchange exchange) {
	     exchange.respond(CONTENT, payload, dataCf);
	   }
	
	/**
	 * This method handles the POST requests that come from the clients
	 * @param exchange: CoapExchange
	 */
	@Override
	public void handlePOST(CoapExchange exchange) {
		 
	     exchange.accept();
	     payload = exchange.getRequestText();
	     exchange.respond(ResponseCode.CREATED, "Resource CREATED");
	     
	     _Logger.info("Received payload from client: " + payload);
	     _Logger.info("Converting JSON to SensorData object");
	     sd = du.jsonToSensorData(payload, true);
	     _Logger.info("\nConverting SensorData object back to JSON string:");
	     _Logger.info("Result: " + du.sensorDataToJson(sd));
	     
	   }
	
	/**
	 * This method handles PUT requests that come from the CoAP clients
	 * @param exchange: CoapExchange
	 */
	@Override
	public void handlePUT(CoapExchange exchange) {
		
	     exchange.respond(ResponseCode.CHANGED, "Resource CHANGED");
	     payload = exchange.getRequestText();
	     changed(); // notify all observers
    
	     _Logger.info("Received payload from client: " + payload);
	     _Logger.info("Converting JSON to SensorData object");
	     sd = du.jsonToSensorData(payload);
	     _Logger.info("\nConverting SensorData object back to JSON string:");
	     _Logger.info("Result: " + du.sensorDataToJson(sd));      
	     
	}
	
	/**
	 * This method handles DELETE requests that come from the CoAP clients
	 * @param exchange: CoapExchange
	 */
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
