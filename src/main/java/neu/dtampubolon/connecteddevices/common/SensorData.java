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
public class SensorData {

	/** 
	 * This class keeps track of the maximum, minimum, and average temperature readings
	 */
	private String timeStamp;
	private String name;
	private double curValue = 0; //Current value of reading
	

	private double avgValue = 0; //Average value of readings
	private double minValue = 0; //Minimum value of readings
	private double maxValue = 0; //Maximum value of readings
	private double totValue = 0; //Sum of all the readings
	private int sampleCount = 0; //#Number of readings taken
	
	public SensorData() {
		// Constructor
		timeStamp = new Date().toString();
		name = "";
	}
	
	public SensorData(String name) {
		//Alternate constructor
		setName(name);
	}
	
	/**
	 * This method adds a new reading to the object
	 * @param newVale : new sensor reading to be added.
	 */
	public void addValue(double newVal) {
		sampleCount++;
		
		timeStamp = new Date().toString();
		curValue = newVal;
		totValue += newVal;
		
		if (curValue < minValue) minValue = curValue;
		
		if (curValue > maxValue) maxValue = curValue;
		
		if (totValue != 0 && sampleCount > 0) {
			avgValue = totValue / sampleCount;
		}
		
	}
	/**
	 * This method is the string formatter for SensorData objects when they are printed out to the console
	 */
	public String toString() {
		return String.format("\tTime: %1$s "
				+ "\tCurrent: %2$.5f"
				+ "\tAverage : %3$.5f"
				+ "\tSamples %4$1d"
				+ "\tMin: %5$.5f"
				+ "\tMax: %5$.5f", timeStamp, curValue, avgValue, sampleCount, minValue, maxValue);
	}
	
	/**
	 * This method returns the name of SensorData instance
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method sets the name of SensorData instance
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method returns the latest temperature value
	 * @return
	 */
	public double getCurValue() {
		return curValue;
	}

	/**
	 * This method returns the average temperature value
	 * @return
	 */
	public double getAvgValue() {
		return avgValue;
	}

	/**
	 * This method returns the minimum temperature value
	 * @return
	 */
	public double getMinValue() {
		return minValue;
	}

	/**
	 * This method returns the maximum temperature value
	 * @return
	 */
	public double getMaxValue() {
		return maxValue;
	}
}
