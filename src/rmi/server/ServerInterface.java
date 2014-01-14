package rmi.server;

import java.rmi.*;

import rmi.client.ClientInterface;
import rmi.utils.Enums;

public interface ServerInterface extends Remote {
	public void login(ClientInterface client, String nickname)
			throws RemoteException;

	public void move(ClientInterface client, Enums.Direction direction)
			throws RemoteException;

	public void logout(ClientInterface client, String nickname) throws RemoteException;
}
