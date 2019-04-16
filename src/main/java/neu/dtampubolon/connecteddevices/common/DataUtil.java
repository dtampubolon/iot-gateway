/**
 * 
 */
package neu.dtampubolon.connecteddevices.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

/**
 * @author Doni Tampubolon
 *
 */
public class DataUtil {

	/**
	 * This class converts data from and to JSON strings
	 */
	File dataFile; //File that contains json string
	BufferedReader reader = null;
	
	public DataUtil() {
		//Default Constructor
		dataFile = null;
	}
	
	/**
	 * Alternate constructor
	 * @param fileName: name and location of json file
	 */
	public DataUtil(String fileName) {
		dataFile = new File(fileName);
	}
	
	/**
	 * This method converts SensorData instance into json string
	 * @param sensorData
	 * @return jsonData of type String
	 */
	public String sensorDataToJson(SensorData sensorData) {
		String jsonData = null;
		
		if(sensorData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(sensorData);
		}
		return jsonData;
	}
	
	/**
	 * This method converts ActuatorData instance into json string
	 * @param actuatorData
	 * @return jsonData of type String
	 */
	public String actuatorDataToJson(ActuatorData actuatorData) {
		String jsonData = null;
		
		if(actuatorData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(actuatorData);
		}
		return jsonData;
	}
	/**
	 * This method converts PitchData instance into JSON string
	 * @param pitchData
	 * @return JSON string
	 */
	public static String pitchDataToJson(PitchData pitchData) {
		String jsonData = null;
		
		if(pitchData != null) {
			Gson gson = new Gson();
			jsonData = gson.toJson(pitchData);
		}
		return jsonData;
	}
	
	/**
	 * This method reads a json file and converts it into ActuatorData
	 * @param dataFile: name and location of json string file
	 * @return ActuatorData object
	 */
	public ActuatorData jsonToActuatorData(String dataFile) {
		String jsonData = readFile(dataFile);
		Gson gson = new Gson();
		ActuatorData ad = gson.fromJson(jsonData, ActuatorData.class);
		
		return ad;
	}
	
	/**
	 * This method accepts a JSON string and converts it into SensorData
	 * @param dataFile: name and location of json string file
	 * @return ActuatorData object
	 */
	public SensorData jsonToSensorData(String jsonData, boolean isJSON) {
		SensorData sd = null;
		if(isJSON) {
			Gson gson = new Gson();
			sd = gson.fromJson(jsonData, SensorData.class);
		}
		return sd;
	}
	
	/**
	 * This method converts a JSON string into a PitchData object
	 * @param jsonData
	 * @param isJSON
	 * @return PitchData object
	 */
	public static PitchData jsonToPitchData(String jsonData, boolean isJSON) {
		PitchData pd = null;
		if(isJSON) {
			Gson gson = new Gson();
			pd = gson.fromJson(jsonData, PitchData.class);
		}
		return pd;
	}
	
	/**
	 * This method reads a json file and converts it into SensorData
	 * @param dataFile: name and location of json string file
	 * @return SensorData object
	 */
	public SensorData jsonToSensorData(String dataFile) {
		String jsonData = readFile(dataFile);
		Gson gson = new Gson();
		SensorData sd = gson.fromJson(jsonData, SensorData.class);
		
		return sd;
	}
	
	/**
	 * This method reads json string in a file
	 * @param String: fileName is the name and location of file
	 * @return json string in a String
	 */
	public String readFile(String fileName) {
		dataFile = new File(fileName);
		if(!dataFile.exists()) //Does it exist?
		{
			String out = fileName + " not found!";
			return out;
		}
		else {
			String jsonString = null;
			try {
				jsonString = new String(Files.readAllBytes(Paths.get(fileName)));
			} catch (IOException e) {
				// Catch if an Exception is thrown, print stack trace
				e.printStackTrace();
			}
			return jsonString;
		}
	}
}
