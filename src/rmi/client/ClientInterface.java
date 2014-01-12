package rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.enums.Enums;

public interface ClientInterface extends Remote {

	public void getScores(String scores)throws RemoteException;
	
	public void getPosition(int x, int y) throws RemoteException;
}
