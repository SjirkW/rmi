import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * This class implements the interface ServerInterface and extends the class
 * UnicastRemoteObject. Its constructor and member functions login and 
 * broadcast can be accessed remotely. 
 * @autor Matthias Braunhofer
 */
public class Server extends UnicastRemoteObject implements ServerInterface {
	
	/**
	 * This ArrayList stores a remote reference for each currently logged in client as
	 * well as its nickname and is used for broadcasting a message, when a client 
	 * invokes the remotely accessible method login.
	 */
	protected ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();
	
	/**
	 * This constructor is used for creating a remote object of type Server. It
	 * throws a RemoteException if the object handle cannot be constructed.
	 */
	public Server() throws RemoteException {
	}

	/**
	 * Implementation of the remotely invocable method. If a client invokes this 
	 * method then all other currently logged in clients get a notification
	 * that a new user has joined the chat. This is done by a remote call of the 
	 * method getMessage which is defined for the Client object. A remote reference 
	 * to the new client is added to the ArrayList.
	 */
	public void login(ClientInterface client, String nickname) throws RemoteException {
		broadcastMessage("--> " + nickname + " is entering the chatroom", "");	
		clients.add(client);
	}
	
	/**
	 * This method is a remote method which is used for broadcasting an incoming message
	 * and the nickname of its sender to all currently logged in clients. This 
	 * broadcasting is done by a remote call of the method getMessage which is
	 * defined for a client object. The references to such clients are obtained from
	 * the ArrayList which stores a remote reference to each currently logged in client.
	 */
	public void broadcastMessage(String message, String nickname) throws RemoteException {
		for (int i = 0; i < clients.size(); i++) {
			ClientInterface c = clients.get(i);
			try {
				c.getMessage(message, nickname);
			} catch (RemoteException e) {
				/**
				 * If a client is not accessible, then it is removed from
				 * the ArrayList and the index i is decremented because 
				 * all other clients go down one place
				 */
				logout(c);
				i = i - 1;
			} 
		}
	}
	
	/**
	 * This method is a local method which is used for removing a connection
	 * from the ArrayList of all connections.
	 */
	public void logout(ClientInterface client) {
		clients.remove(client);
	}
	
	/**
	 * This main method registers a remote accessible Server object by invoking the 
	 * rebind method on the class Naming. The name of this object is "Server". This
	 * means that all clients which want to get a reference to this object have to
	 * specify this name in the lookup method. 
	 */
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			Naming.rebind("Server", new Server());
			System.out.println("Server is ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
