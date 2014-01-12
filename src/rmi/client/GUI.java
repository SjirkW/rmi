package rmi.client;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import rmi.enums.Enums.Direction;
import rmi.server.ServerInterface;

public class GUI extends javax.swing.JFrame {
	private Client client; // reference to the local Client object
	private ServerInterface server; // reference to the remote Server object
	private static String nickname;
	private static String host;
	private static JTextArea History;
	private JTextField Message;
	private JScrollPane jScrollPaneHistory;
	
	static Grid grid = new Grid(10, 10, 200, 200);

	/**
	 * Display the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		host = JOptionPane.showInputDialog("Enter the host of the gameserver",
				"localhost");
		nickname = JOptionPane.showInputDialog("Enter your nickname");
		if (host != null && nickname != null && !nickname.equals("")) {
			try {
				GUI inst = new GUI();
				inst.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		} else
			System.exit(0);
	}

	/**
	 * log in to the server and init the GUI
	 * 
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public GUI() throws MalformedURLException, RemoteException,
			NotBoundException {
		super();
		server = (ServerInterface) Naming.lookup("//" + host + "/Server");
		client = new Client();
		server.login(client, nickname);
		initGUI();
	}

	private void initGUI() {
		try {
			{
				jScrollPaneHistory = new JScrollPane();
				getContentPane().add(jScrollPaneHistory);
				jScrollPaneHistory.setBounds(7, 7, 150, 203);
				jScrollPaneHistory
						.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				{
					History = new JTextArea();
					History.setLineWrap(true);
					History.setEditable(false);
					jScrollPaneHistory.setViewportView(History);
				}
				
				getContentPane().add(grid);
				grid.setBounds(170, 7, 210, 210);
			}
			{
				Message = new JTextField();
				getContentPane().add(Message);
				Message.setBounds(7, 217, 378, 42);
				Message.addKeyListener(new KeyAdapter() {
					public void keyReleased(KeyEvent evt) {
						try {
							arrowKeyPressed(evt);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setTitle("MyRMIChat - " + nickname);
			this.setResizable(false);
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showScores(String scores)
	{
		History.setText(scores);
	}
	
	public static void drawPosition(int x, int y){
		grid.fillCell(x, y);
	}
	/**
	 * keyhandler
	 * @throws RemoteException 
	 */
	private void arrowKeyPressed(KeyEvent evt) throws RemoteException {	
		if (evt.getKeyCode() == KeyEvent.VK_LEFT){
			server.move(this.client, Direction.LEFT);
		}
		else if (evt.getKeyCode() == KeyEvent.VK_RIGHT){
			server.move(this.client, Direction.RIGHT);
		}
		else if (evt.getKeyCode() == KeyEvent.VK_DOWN){
			server.move(this.client, Direction.DOWN);
		}
		else if (evt.getKeyCode() == KeyEvent.VK_UP){
			server.move(this.client, Direction.UP);
		}
	}
	

}
