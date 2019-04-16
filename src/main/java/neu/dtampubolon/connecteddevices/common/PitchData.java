package neu.dtampubolon.connecteddevices.common;

import java.util.Date;

public class PitchData {

	private String timeStamp;
	private String name;
	private double curValue = 0;
	
	public PitchData() {
		timeStamp = new Date().toString();
		name = "PitchData";
	}
	
	public PitchData(String name) {
		setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public double getCurValue() {
		return curValue;
	}

	public void setCurValue(double curValue) {
		this.curValue = curValue;
	}
	
	public String toString() {
		return String.format("\tTime: %1$s "
				+ "\tCurrent: %2$.0f", timeStamp, curValue);
	}
}
