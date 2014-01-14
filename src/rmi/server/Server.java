package rmi.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import rmi.client.ClientInterface;
import rmi.enums.Enums.Direction;

@SuppressWarnings("serial")
public class Server extends UnicastRemoteObject implements ServerInterface {
	Game game;
	static Frame frame;
	public static final int GRID_SIZE = 10;

	public Server() throws RemoteException {
		game = new Game();
	}

	/**
	 * create the RMI server here
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			Naming.rebind("Server", new Server());
			System.out.println("Server is ready");
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame = new Frame();
	}

	/**
	 * add a player to the game
	 */
	public void login(ClientInterface client, String nickname)
			throws RemoteException {
		Player player = new Player(client, nickname);
		game.addPlayer(player);
	}

	/**
	 * move a player in a given direction
	 */
	public void move(ClientInterface client, Direction direction)
			throws RemoteException {
		game.move(client, direction);
	}
	
	/**
	 * log a player out
	 * @param client
	 * @throws RemoteException
	 */
	public void logout(ClientInterface client, String nickname) throws RemoteException {
		game.logout(client, nickname);
	}

}
