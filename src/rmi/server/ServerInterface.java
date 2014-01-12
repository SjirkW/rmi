package rmi.server;

import java.rmi.*;

import rmi.client.ClientInterface;

public interface ServerInterface extends Remote {
	public void login(ClientInterface client, String nickname)
			throws RemoteException;

	public void broadcastMessage(String message, String nickname)
			throws RemoteException;

	public void move(ClientInterface client, Enums.Direction direction)
			throws RemoteException;
}
