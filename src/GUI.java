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


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
* @autor Matthias Braunhofer
*/
public class GUI extends javax.swing.JFrame {
	private Client client; //reference to the local Client object
	private ServerInterface server; //reference to the remote Server object
	private static String nickname;
	private static String host;
	private static JTextArea History;
	private JTextField Message;
	private JScrollPane jScrollPaneHistory;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		host = JOptionPane.showInputDialog("Enter the host of the chatserver", "localhost");
		nickname = JOptionPane.showInputDialog("Enter your nickname");
		if (host != null && nickname != null && !nickname.equals("")) {
			try {
				GUI inst = new GUI();
				inst.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		else System.exit(0);	
	}
	
	/**
	 * Inside this constructor we invoke the lookup method on the class Naming for
	 * receiving a reference to a remote Server object. This remote reference
	 * is used for invoking the remote method login, which adds a reference to this
	 * Client object to an existing list of other clients. The remote Server object 
	 * is also used for broadcasting messages.
	 */
	public GUI() throws MalformedURLException, RemoteException, NotBoundException {
		super();
		server = (ServerInterface)Naming.lookup("//" + host + "/Server");
		client = new Client();
		server.login(client, nickname);
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				jScrollPaneHistory = new JScrollPane();
				getContentPane().add(jScrollPaneHistory);
				jScrollPaneHistory.setBounds(7, 7, 378, 203);
				jScrollPaneHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				{
					History = new JTextArea();
					History.setLineWrap(true);
					History.setEditable(false);
					jScrollPaneHistory.setViewportView(History);
				}
			}
			{
				Message = new JTextField();
				getContentPane().add(Message);
				Message.setBounds(7, 217, 378, 42);
				Message.addKeyListener(new KeyAdapter() {
					public void keyReleased(KeyEvent evt) {
						MessageKeyPressed(evt);
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
	
	/**
	 * This method is called by the getMessage method which is defined inside the
	 * Client class. It simply writes a message and its sender to the TextArea field.
	 * If the nickname is empty, then it means that a new client has joined the chat.
	 */
	public static void showMessage(String message, String nickname) {
		if (!nickname.equals(""))
			History.append(nickname + ": " + message + "\n");
		else History.append(message + "\n");
	}
	
	/**
	 * This method is called whenever the user presses the enter key. If the text field
	 * is not empty, then we invoke the remote method broadcastMessage on the remote
	 * Server object, which broadcasts the entered message to all other clients.
	 */
	private void MessageKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER && !Message.getText().equals("")) {
			try {
				server.broadcastMessage(Message.getText(), nickname);
				Message.setText("");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}

