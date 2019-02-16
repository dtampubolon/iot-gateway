/**
 * 
 */
package schooldomain.studentname.connecteddevices.labs.module05;

import java.util.logging.Logger;

import com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp;
import com.labbenchstudios.edu.connecteddevices.common.DeviceApplicationException;

/**
 *
 */
public class Module05App extends BaseDeviceApp
{
	// static
	
	private static final Logger _Logger =
		Logger.getLogger(Module05App.class.getSimpleName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Module05App app = new Module05App(Module05App.class.getSimpleName(), args);
		app.startApp();
	}
	
	// private var's
	
	
	// constructors
	
	/**
	 * Default.
	 * 
	 */
	public Module05App()
	{
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param appName
	 */
	public Module05App(String appName)
	{
		super(appName);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param appName
	 * @param args
	 */
	public Module05App(String appName, String[] args)
	{
		super(appName, args);
	}
	
	// protected methods
	
	/* (non-Javadoc)
	 * @see com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp#start()
	 */
	@Override
	protected void start() throws DeviceApplicationException
	{
		_Logger.info("Hello - module05 here!");
		
	}
	
	/* (non-Javadoc)
	 * @see com.labbenchstudios.edu.connecteddevices.common.BaseDeviceApp#stop()
	 */
	@Override
	protected void stop() throws DeviceApplicationException
	{
		_Logger.info("Stopping module05 app...");
	}
	
}
