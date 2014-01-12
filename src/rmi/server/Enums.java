package rmi.server;

public class Enums {
	 public enum Direction {
		   RIGHT, 
		   UP, 
		   LEFT, 
		   DOWN
		 }
	 
	 public enum Message{
		 PUBLICMESSAGE,
		 ONLINE,
		 OFFLINE,
		 POSITION_CHANGED,
		 PLAYER_SCORED
	 }
}
