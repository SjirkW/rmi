package rmi.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import rmi.client.ClientInterface;
import rmi.enums.Enums.Direction;

public class Game {
	protected ArrayList<Player> players = new ArrayList<Player>();

	public Game() {

	}

	/**
	 * add a player to player list
	 * 
	 * @param player
	 * @throws RemoteException
	 */
	public void addPlayer(Player player) throws RemoteException {
		players.add(player);
		player.respawn();
		System.out.println(player.getName() + " joined");
	}

	/**
	 * move a single player in a given direction
	 * 
	 * @param client
	 * @param direction
	 * @throws RemoteException
	 */
	public void move(ClientInterface client, Direction direction)
			throws RemoteException {
		Player player = getPlayer(client);
		player.changePosition(direction);
		client.getPosition(player.getX(), player.getY());
		if (isColliding(player)) {
			player.incScore();
			updateScores();
			System.out.println(player.getName() + " Scored! ("
					+ player.getScore() + ")");
		}
	}

	/**
	 * check if a player is colliding with other players
	 * 
	 * @param player
	 * @return
	 * @throws RemoteException
	 */
	public boolean isColliding(Player player) throws RemoteException {
		for (int i = 0; i < players.size(); i++) {
			Player otherPlayer = players.get(i);
			if (!player.getClient().equals(otherPlayer.getClient())) {
				if (otherPlayer.hasPosition(player.getX(), player.getY())) {
					respawn(otherPlayer);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * respawn a player on a new position, this is a recursive method
	 * 
	 * @param player
	 * @throws RemoteException
	 */
	public void respawn(Player player) throws RemoteException {
		// get new coords
		player.respawn();
		try {
			player.getClient().getPosition(player.getX(), player.getY());
		} catch (RemoteException e) {
			logout(player.getClient(), player.getName());
		}
		for (int i = 0; i < players.size(); i++) {
			Player otherPlayer = players.get(i);
			// dont compare if it's the same player
			if (!player.getClient().equals(otherPlayer.getClient())) {
				if ((player.getX() == otherPlayer.getX())
						&& player.getY() == otherPlayer.getY()) {
					// recursive call
					respawn(player);
				}
			}
		}
		System.out.println(player.getName() + " has spawned at "
				+ player.getX() + "," + player.getY());
	}

	/**
	 * remove a client from the game
	 * 
	 * @param client
	 * @throws RemoteException
	 */
	public void logout(ClientInterface client, String nickname) throws RemoteException {
		System.out.println("--> " + nickname + " left the game");
		players.remove(getPlayer(client));
	}

	/**
	 * update the scores for all players in the game
	 * 
	 * @throws RemoteException
	 */
	private void updateScores() throws RemoteException {
		String scores = "";
		for (int i = 0; i < players.size(); i++) {
			String playerAndScore = players.get(i).getName() + ": "
					+ players.get(i).getScore();
			scores += playerAndScore + "\n";
		}

		for (int i = 0; i < players.size(); i++) {
			ClientInterface c = players.get(i).getClient();
			try {
				c.getScores(scores);
			} catch (RemoteException e) {
				logout(c, players.get(i).getName() );
				i--;
			}
		}
	}

	/**
	 * get a player from the list using client as its input
	 * 
	 * @param client
	 * @return
	 */
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
