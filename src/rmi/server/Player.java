package rmi.server;

import java.rmi.RemoteException;

import rmi.client.ClientInterface;
import rmi.enums.Enums;

public class Player {
	private String nickName;
	private ClientInterface client;
	private int score;
	private int x;
	private int y;

	public Player(ClientInterface client, String nickName) {
		this.nickName = nickName;
		this.client = client;
	}

	public String getName() {
		return nickName;
	}

	public ClientInterface getClient() {
		return client;
	}

	public int getScore() {
		return score;
	}

	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void incScore() {
		this.score++;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String coordsToString() {
		return Integer.toString(x) + "," + Integer.toString(y);
	}

	/**
	 * change the direction of a user by looking at the last movement
	 * 
	 * @param dir
	 * @param userName
	 * @throws RemoteException
	 */
	public void changePosition(Enums.Direction dir) throws RemoteException {
		// check if not at the edge
		boolean canLeft = this.x > 0;
		boolean canRight = this.x < Server.GRID_SIZE - 1;
		boolean canUp = this.y > 0;
		boolean canDown = this.y < Server.GRID_SIZE - 1;
		// move player
		switch (dir) {
		case LEFT:
			if (canLeft) {
				x--;
			}
			break;
		case RIGHT:
			if (canRight) {
				x++;
			}
			break;
		case UP:
			if (canUp) {
				y--;
			}
			break;
		case DOWN:
			if (canDown) {
				y++;
			}
			break;
		}
	}

	/**
	 * Check if a player is colliding with another player
	 * 
	 * @param user
	 * @return
	 * @throws RemoteException
	 */
	public boolean hasPosition(int x, int y) throws RemoteException {
		if ((this.x == x) && (this.y == y)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * generate a new position for the player and send it to the client
	 * application
	 * 
	 * @throws RemoteException
	 */
	public void respawn() throws RemoteException {
		// generate random x and y between grid size
		int newX = (int) (Math.random() * Server.GRID_SIZE);
		int newY = (int) (Math.random() * Server.GRID_SIZE);
		// set a new position for the player
		this.x = newX;
		this.y = newY;
	}
}
