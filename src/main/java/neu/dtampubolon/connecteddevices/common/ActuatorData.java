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
	
	// Constructor
	public ActuatorData() {
		timeStamp = new Date().toString();
		name = "Name not set";
		hasError = false;
		command = 0;
		errCode = 0;
		statusCode = 0;		
		val = 0;
	}
	
	//This method returns the name of this ActuatorData
	public String getName() {
		return name;
	}

	//This method returns the value of command
	public int getCommand() {
		return command;
	}

	//This method returns the value of errCode
	public int getErrorCode() {
		return errCode;
	}

	//This method returns the value of stateData
	public int getStateData() {
		return stateData;
	}

	//This method returns the current value of ActuatorData
	public float getValue() {
		return val;
	}
	
	//This method returns boolean value of hasError 
	public boolean hasError() {
		return hasError;
	}

	//This method returns the value of statusCode
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * This method sets the name of this ActuatorData instance
	 * @param name: String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method sets the variable command
	 * @param command
	 */
	public void setCommand(int command) {
		this.command = command;
	}

	/**
	 * This method sets the variable statusCode
	 * @param statusCode: integer
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * This method sets the variable stateData
	 * @param stateData: integer
	 */
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
	
	//This method updates the timeStamp
	public void updateTimeStamp() {
		timeStamp = new Date().toString();
	}

}
