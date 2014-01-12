import java.rmi.*;

/**
 * This interface extends the class Remote and specifies the remote accessible 
 * methods login and broadcast, which can be accessed externally. 
 * @autor Matthias Braunhofer
 */
public interface ServerInterface extends Remote {
	
	/**
	 * This method can be invoked remotely from a non-local virtual machine. It takes
	 * the nickname of a client and a reference to a remote object of type Client
	 * as parameter.
	 */
	public void login(ClientInterface client, String nickname) throws RemoteException;
	
	/**
	 * This method can be invoked remotely from a non-local virtual machine. It broadcasts
	 * an incoming message to all currently connected clients.
	 */
	public void broadcastMessage(String message, String nickname) throws RemoteException;
}

