package rmi.client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class Client extends UnicastRemoteObject implements ClientInterface {

	public Client() throws RemoteException {
	}

	/**
	 * show the scores on the GUI
	 */
	public void getScores(String scores) throws RemoteException {
		GUI.showScores(scores);
	}
	
	/**
	 * draw the positions on the GUI
	 */
	public void getPosition(int x, int y) throws RemoteException {
		GUI.drawPosition(x, y);
	}
}
