package neu.dtampubolon.connecteddevices.common;

import java.util.Date;

/**
 * This class stores pitch readings
 * @author Doni Tampubolon
 *
 */
public class PitchData {

	private String timeStamp;
	private String name;
	private double curValue = 0;
	
	/**
	 * Constructor
	 */
	public PitchData() {
		timeStamp = new Date().toString();
		name = "PitchData";
	}
	
	/**
	 * Alternate Constructor
	 * @param name
	 */
	public PitchData(String name) {
		setName(name);
	}
	
	/**
	 * This method sets the name of a PitchData instance
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method returns the current pitch value
	 * @return
	 */
	public double getCurValue() {
		return curValue;
	}
	
	/**
	 * This method updates the current pitch value
	 * @param curValue
	 */
	public void setCurValue(double curValue) {
		this.curValue = curValue;
	}
	
	/**
	 * This method is a string formatter for this class
	 */
	public String toString() {
		return String.format("\tTime: %1$s "
				+ "\tCurrent: %2$.0f", timeStamp, curValue);
	}
}
