package rmi.client;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.enums.Enums.Direction;

public class Client extends UnicastRemoteObject implements ClientInterface {

	public Client() throws RemoteException {
	}

	public void getScores(String scores) throws RemoteException {
		GUI.showScores(scores);
	}
	
	public void getPosition(int x, int y) throws RemoteException {
		GUI.drawPosition(x, y);
	}
}
