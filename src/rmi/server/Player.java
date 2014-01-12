package rmi.server;

import java.net.Socket;
import java.rmi.RemoteException;

import rmi.client.ClientInterface;

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
	
	public ClientInterface getClient(){
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

	public String coordsToString() {
		return Integer.toString(x) + "," + Integer.toString(y);
	}

	/**
	 * change the direction of a user by looking at the last movement
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
		
		System.out.println(nickName + " new position = " + x + "," + y);
	}
}
