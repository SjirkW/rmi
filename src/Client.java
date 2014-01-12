import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class implements the interface ClientInterface and extends the class
 * UnicastRemoteObject. Its constructor and member function getMessage can be
 * accessed remotely.
 * @autor Matthias Braunhofer
 */
public class Client extends UnicastRemoteObject implements ClientInterface {

	/**
	 * Constructor for the remote object of type Client. This constructor
	 * throws a RemoteException if the object handle cannot be constructed.
	 */
	public Client() throws RemoteException {
	}

	/**
	 * This method implements the remotely invocable method. It should be called
	 * by the server to send a message and the nickname of its sender to a
	 * client's graphical user interface. It also throws a RemoteException when an
	 * error occurs during the remote method call.
	 */
	public void getMessage(String message, String nickname) throws RemoteException {
		GUI.showMessage(message, nickname);
	}
}
