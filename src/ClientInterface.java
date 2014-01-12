import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface extends the class Remote and specifies a remote accessible 
 * method getMessage, which can be accessed externally.
 * @autor Matthias Braunhofer
 */
public interface ClientInterface extends Remote {
	
	/**
	 * This method can be invoked remotely from a non-local virtual machine.
	 */
	public void getMessage(String message, String nickname) throws RemoteException;
}
