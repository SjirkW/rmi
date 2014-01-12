package rmi.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import rmi.client.ClientInterface;
import rmi.enums.Enums.Direction;

public class Server extends UnicastRemoteObject implements ServerInterface {
	Game game;

	public static final int GRID_SIZE = 10;

	public Server() throws RemoteException {
		game = new Game();
	}

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			Naming.rebind("Server", new Server());
			System.out.println("Server is ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login(ClientInterface client, String nickname)
			throws RemoteException {
		System.out.println(client);
		System.out.println(nickname);
		Player player = new Player(client, nickname);
		game.addPlayer(player);
	}

	public void broadcastMessage(String message, String nickname)
			throws RemoteException {
		game.broadcastMessage(message, nickname);
	}

	public void move(ClientInterface client, Direction direction)
			throws RemoteException {
		game.move(client, direction);
	}

}
