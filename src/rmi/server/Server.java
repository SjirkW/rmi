package rmi.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import rmi.client.ClientInterface;
import rmi.enums.Enums.Direction;

public class Server extends UnicastRemoteObject implements ServerInterface {

	protected ArrayList<ClientInterface> clients = new ArrayList<ClientInterface>();
	protected ArrayList<Player> players = new ArrayList<Player>();

	public static final int GRID_SIZE = 10;

	public Server() throws RemoteException {
	}

	public void login(ClientInterface client, String nickname)
			throws RemoteException {
		broadcastMessage("--> " + nickname + " is entering the game", "");
		Player player = new Player(client, nickname);
		players.add(player);
	}

	public void logout(ClientInterface client) throws RemoteException {
		broadcastMessage("--> " + "someone" + " left the game", "");

		// for (int i = 0; i < players.size(); i++) {
		// Player player = players.get(i);
		// if (player.getClient().equals(client)) {
		// players.remove(i);
		// }
		// }

		players.remove(getPlayer(client));
	}

	public void broadcastMessage(String message, String nickname)
			throws RemoteException {
		for (int i = 0; i < clients.size(); i++) {
			ClientInterface c = clients.get(i);
			try {
				c.getMessage(message, nickname);
			} catch (RemoteException e) {
				logout(c);
				i = i - 1;
			}
		}
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

	@Override
	public void move(ClientInterface client, Direction direction)
			throws RemoteException {
		Player player = getPlayer(client);
		player.changePosition(direction);
		if (isColliding(player)) {
			player.incScore();
			System.out.println(player.getName() + " Scored! (" + player.getScore() + ")" );
		}
	}

	private boolean isColliding(Player player) throws RemoteException {
		for (int i = 0; i < players.size(); i++) {
			Player otherPlayer = players.get(i);
			if (!player.getClient().equals(otherPlayer.getClient())){
				if (otherPlayer.hasPosition(player.getX(), player.getY())) {
					respawn(otherPlayer);
					return true;
				}
			}
		}
		return false;
	}
	
	private void respawn(Player player){
		//get new coords
		player.respawn();
		for (int i = 0; i < players.size(); i++) {	
			Player otherPlayer = players.get(i);
			//dont compare if it's the same player
			if (!player.getClient().equals(otherPlayer.getClient())){
				if ((player.getX() == otherPlayer.getX())
						&& player.getY() == otherPlayer.getY()){
					//recursive call
					respawn(player);
				}
			}
		}
		System.out.println(player.getName() + " has spawned at " + player.getX() + "," + player.getY());
	}

	private Player getPlayer(ClientInterface client) {
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			if (player.getClient().equals(client)) {
				return player;
			}
		}
		return null;
	}
}
