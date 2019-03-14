/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module07;
import com.labbenchstudios.edu.connecteddevices.common.*;
import neu.dtampubolon.connecteddevices.common.*;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

/**
 * @author Doni Tampubolon
 *
 */
public class CoapClientConnector {

	/**
	 * This class builds and connects a CoAP client to a CoAP server
	 */
	
	private static final Logger _Logger = Logger.getLogger(CoapClientConnector.class.getName());
	private String _protocol;
	private int _port;
	private String _host;
	private String _serverAddr;
	private CoapClient _clientConn;
	private boolean useNON = false; //Use nonconfirmable message
	private boolean _isInitialized = false;
	private CoapObserveRelation relation1;
	//private String resourceName;
	
	/**
	 * Constructor
	 * @param host
	 * @param isSecure
	 */
	public CoapClientConnector(String host, boolean isSecure) {
		if (isSecure) {
			_protocol = ConfigConst.SECURE_COAP_PROTOCOL;
			_port = ConfigConst.SECURE_COAP_PORT;
		}
		else {
			_protocol = ConfigConst.DEFAULT_COAP_PROTOCOL;
			_port = ConfigConst.DEFAULT_COAP_PORT;
		}
		
		if (host != null && host.trim().length()  > 0) {
			_host = host;
		}
		else {
			_host = "127.0.0.1";
		}
		
		//URL does not have a protocol handler 
		_serverAddr = _protocol + "://" + _host + ":" + _port;
		
		_Logger.info("Using URL to connect to server: " + _serverAddr);
		}
	
	//Public methods
	/**
	 * 
	 */
	public void discoverResources() {
		_Logger.info("Issuing discover to server...");
		
		initClient();
		Set<WebLink> wlSet = _clientConn.discover();
		if (wlSet != null) {
			for (WebLink wl: wlSet) {
				_Logger.info(" --> WebLink: " + wl.getURI());
			}
		}
	}
	
	/**
	 * 
	 * @param resourceName
	 */
	public void sendGetRequest(String resourceName) {
		initClient(resourceName);
		
		if(useNON) {
			_clientConn.useNONs();
		}
		else { 
			_clientConn.useCONs();
		}
		_clientConn.get(new CoapHandler() {

			@Override
			public void onLoad(CoapResponse response) {
				String content = response.getResponseText();
				_Logger.info("RESPONSE FROM SERVER: " + content);
			}

			@Override
			public void onError() {
				_Logger.log(Level.WARNING, "GET REQUEST FAILED");
			}
			
		});
	}
	
	/**
	 * 
	 * @param resourceName
	 * @param payload
	 */
	public void sendPutRequest(String resourceName, String payload){
		initClient(resourceName);
		
		if(useNON) {
			_clientConn.useNONs();
		}
		else { 
			_clientConn.useCONs();
		}
		
		_Logger.info("Sending PUT request to: " + _serverAddr + "/" +  resourceName);
		CoapResponse response = _clientConn.put(payload, MediaTypeRegistry.TEXT_PLAIN);
		_Logger.info("RESPONSE FROM SERVER: " + response.getResponseText());
	}
	
	/**
	 * 
	 * @param resourceName
	 * @param payload
	 */
	public void sendPostRequest(String resourceName, String payload) {
		initClient(resourceName);
		if(useNON) {
			_clientConn.useNONs();
		}
		else { 
			_clientConn.useCONs();
		}
		_Logger.info("Sending POST request to: " + _serverAddr + "/" +  resourceName);
		CoapResponse response = _clientConn.post(payload, MediaTypeRegistry.TEXT_PLAIN);
		_Logger.info("RESPONSE FROM SERVER: " + response.getResponseText());
	}
	
	public void sendDeleteRequest(String resourceName) {
		_Logger.info("Sending DELETE request to: " + _serverAddr + "/" + resourceName);
		CoapResponse response = _clientConn.delete();
		_Logger.info("RESPONSE FROM SERVER: " + response.getResponseText());
	}
	
	/**
	 * 
	 * @param resourceName
	 */
	public void sendObserveRequest(String resourceName) {
		initClient(resourceName);
		if(useNON) {
			_clientConn.useNONs();
		}
		else { 
			_clientConn.useCONs();
		}
		
		_Logger.info("------\nGET " + resourceName + " with Observe");
		
		relation1 = _clientConn.observe(new CoapHandler() {

			@Override
			public void onLoad(CoapResponse response) {
				// TODO Auto-generated method stub
				String content = response.getResponseText();
				_Logger.info("NOTIFICATION: " + content);
			}

			@Override
			public void onError() {
				// TODO Auto-generated method stub
				_Logger.warning("OBSERVATION FAILED");
			}
		});
	}
	
	/**
	 * 
	 */
	public void cancelObserve() {
		if(!_isInitialized) {
			return;
		}
		else if(relation1!=null) {
			relation1.proactiveCancel();
		}
	}
	
	/**
	 * 
	 */
	private void initClient() {
		initClient(null);
	}
	
	/**
	 * 
	 * @param resourceName
	 */
	private void initClient(String resourceName) {
		if (_isInitialized) {
			return;
		}
		
		if (_clientConn != null) {
			_clientConn.shutdown();
			_clientConn = null;
		}
		
		try {
			if(resourceName!= null) {
				_serverAddr += "/"  + resourceName;
			}
			
			_clientConn = new CoapClient(_serverAddr);
			
			_Logger.info("Created client conenction to server / resource: " + _serverAddr);
		}
		catch (Exception e) {
			_Logger.log(Level.SEVERE, "Failed to connect to server: " + getCurrentUri(), e);
		}	
	}
	
	/**
	 * 
	 * @return
	 */
	private String getCurrentUri() {
		return _clientConn.getURI();
	}
}