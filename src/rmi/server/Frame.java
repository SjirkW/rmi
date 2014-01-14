package rmi.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener {
    JLabel lblStatus;

    public Frame() {
        this.setTitle("Server Running");
        this.setSize(320, 240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        lblStatus = new JLabel();
        lblStatus.setText("server started");
        lblStatus.setBounds(50, 70, 230, 21);
        add(lblStatus);

        this.setVisible(true);
    }

    public static void main(String[] args) throws RemoteException {
        new Frame();
    }

    public void writeLabel(String text){
    	lblStatus.setText(text);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
