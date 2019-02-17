/**
 * 
 */
package neu.dtampubolon.connecteddevices.labs.module05;

import neu.dtampubolon.connecteddevices.common.DataUtil;
import neu.dtampubolon.connecteddevices.common.SensorData;

/**
 * CSYE 6530 - Connected Devices
 * Lab module 5
 * @author Doni Tampubolon
 *
 */
public class TempManagementApp {

	/**
	 * This class reads/writes data from/to sensors
	 * JSON strings are received from Python apps that process sensor/actuator data
	 * @param args
	 */
	DataUtil du;
	String fileName;
	SensorData sd;
	public TempManagementApp() {
		fileName = "C:\\Users\\Doni Tampubolon\\Documents\\Grad School\\CSYE6530\\gitrepo\\iot-device\\apps\\labs\\data\\Data.json";
		du = new DataUtil();
	}
	
	public static void main(String[] args) {
		TempManagementApp app = new TempManagementApp();
		app.run();
		
	}
	public void run() {
		//TEST: Read json from a file and convert to SensorData object
		sd = du.jsonToSensorData(fileName);
		System.out.println("Reading json string from:"+ fileName +"\nand converting to object..");
		System.out.println("Object:");
		System.out.println(sd+"\n");
		
		//TEST: Convert an objet to a json string
		System.out.println("Convert from object to JSON:");
		System.out.println(du.sensorDataToJson(sd));
		
	}
}
