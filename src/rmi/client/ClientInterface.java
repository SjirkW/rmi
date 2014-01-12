package rmi.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.server.Enums;

public interface ClientInterface extends Remote {
	public void getMessage(String message, String nickname)
			throws RemoteException;

}
