package rmi.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import rmi.client.ClientInterface;
import rmi.enums.Enums.Direction;

public class Game {
	protected ArrayList<Player> players = new ArrayList<Player>();

	public Game() {

	}
	
	public void addPlayer(Player player) throws RemoteException{
		players.add(player);
		broadcastMessage("--> " + player.getName() + " is entering the game", "");
	}
	
	public void move(ClientInterface client, Direction direction)
			throws RemoteException {
		Player player = getPlayer(client);
		player.changePosition(direction);
		if (isColliding(player)) {
			player.incScore();
			updateScores();
			System.out.println(player.getName() + " Scored! (" + player.getScore() + ")" );
		}
	}

	public boolean isColliding(Player player) throws RemoteException {
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
	
	public void respawn(Player player){
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
	
	public void remove(ClientInterface client){
		players.remove(getPlayer(client));
	}
	
	public void broadcastMessage(String message, String nickname)
			throws RemoteException {
		for (int i = 0; i < players.size(); i++) {
			ClientInterface c = players.get(i).getClient();
			try {
				c.getMessage(message, nickname);
			} catch (RemoteException e) {
				logout(c);
				i = i - 1;
			}
		}
	}
	
	private void logout(ClientInterface client) throws RemoteException {
		broadcastMessage("--> " + "someone" + " left the game", "");
		remove(client);
	}
	
	/**
	 * update the scores for all players in the game
	 * @throws RemoteException 
	 */
	private void updateScores() throws RemoteException{
		String scores = "";
		for (int i = 0; i < players.size(); i++) {
			ClientInterface c = players.get(i).getClient();
			String playerAndScore = players.get(i).getName() + ": " + players.get(i).getScore();
			scores += playerAndScore + "\n";
				c.getScores(scores);
		}
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
