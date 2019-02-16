/**
 * 
 */
package neu.dtampubolon.connecteddevices.common;

import java.util.Date;
/**
 * CSYE 6530 - Connected Devices
 * Lab module 5
 * @author Doni Tampubolon
 *
 */
public class ActuatorData {

	/**
	 * This class contains the appropriate data to signal a temperature increase
	 * or decrease request
	 */
	private String timeStamp;
	private String name; 
	private boolean hasError;
	private int command;
	private int errCode;
	private int statusCode;
	private int stateData;
	private float val;
	
	public ActuatorData() {
		// Constructor
		timeStamp = new Date().toString();
		name = "Name not set";
		hasError = false;
		command = 0;
		errCode = 0;
		statusCode = 0;		
		val = 0;
	}
	
	public String getName() {
		return name;
	}

	public int getCommand() {
		return command;
	}

	public int getErrorCode() {
		return errCode;
	}

	public int getStateData() {
		return stateData;
	}

	public float getValue() {
		return val;
	}
	
	public boolean hasError() {
		return hasError;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setStateData(int stateData) {
		this.stateData = stateData;
	}

	/**
	 * This method takes copies the values of member variables from an ActuatorData instance
	 * @param ActuatorData: data
	 */
	public void updateData(ActuatorData data) {
		this.command = data.getCommand();
		this.statusCode = data.getStatusCode();
		this.errCode = data.getErrorCode();
		this.stateData = data.getStateData();
		this.val = data.getValue();
	}
	
	public void updateTimeStamp() {
		timeStamp = new Date().toString();
	}

}
