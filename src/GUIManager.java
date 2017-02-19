import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;


public class GUIManager extends JFrame {

	private JPanel contentPane;
	private JPanel welcomePanel;
	private JPanel chatPanel;
	private JTextArea chatTextArea;
	
	volatile SocketManager chatSocketManager;
	Integer port = 2222;
	String errorMessage;
	
	
	
	
	/**
	 * MAIN
	 */
	public static void main(String[] args) {
	
		GUIManager frame = null;
		
		try {
			frame = new GUIManager();
			frame.setVisible(true);
				
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		while(true)
			{
			frame.read();
			}

	/*EventQueue.invokeLater(new Runnable() {
			public void run() {	
				GUIManager frame = null;
				try {
					frame = new GUIManager();
					frame.setVisible(true);
						
				} catch (Exception e) {
						e.printStackTrace();
				}
				
				frame.read();
			}
		});	*/
	}

	
	public void read()
	{
		String message;
		
		if(chatSocketManager!=null)
		{
			
			message = chatSocketManager.recieveMessage();
			if(message!=null) System.out.println("WIADOMOSC: " + message);
			
			if(message!=null)
			{
				chatTextArea.append(message + "\n");
			}
		}
	}
	
	
	/**
	 * Konstruktor
	 */
	public GUIManager() {
		
		/**********************************************************************************************************************/
		/*****************************************************INICJALIZACJA****************************************************/
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		welcomePanel = new JPanel();
		contentPane.add(welcomePanel, "name_21000608227909");
		welcomePanel.setLayout(null);
		
		/*****************************************************BUTTONY INICJUJACE*****/
		
		JButton serverButton = new JButton("Rozpocznij Konwersacje");
		serverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {							//Utworz Serwer
				
				welcomePanel.setVisible(false);
				chatPanel.setVisible(true);
				
				try {
					chatSocketManager = new ServerSocketManager(port);
				} catch (IOException e) {
					errorMessage = chatSocketManager.getErrorMessage();
				}
				
				(new Thread(chatSocketManager)).start();
				
				welcomePanel.setVisible(false);
				chatPanel.setVisible(true); 
			}
		});
		serverButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		serverButton.setBounds(163, 11, 250, 106);
		welcomePanel.add(serverButton);
		
		JButton clientButton = new JButton("Do\u0142\u0105cz Do Konwersacji");
		clientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {							//Utworz Klienta
				
				try {
					chatSocketManager = new ClientSocketManager(port);
				} catch (IOException e) {
					e.printStackTrace();
					errorMessage = chatSocketManager.getErrorMessage();
				}
				
				(new Thread(chatSocketManager)).start();
				
				welcomePanel.setVisible(false);
				chatPanel.setVisible(true);
				
			}
		});
		clientButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		clientButton.setBounds(163, 152, 250, 98);
		welcomePanel.add(clientButton);
		
		
		/**********************************************************************************************************************/
		/*****************************************************OKNO CZATU******************************************************/

		chatPanel = new JPanel();
		contentPane.add(chatPanel, "name_21007602345088");
		chatPanel.setLayout(null);
		
		JEditorPane chatEditorPane = new JEditorPane();
		chatEditorPane.setBounds(10, 159, 564, 58);
		chatPanel.add(chatEditorPane);
		
		JButton sendButton = new JButton("Wy\u015Blij");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String message = chatEditorPane.getText();
				chatSocketManager.sendMessage(message);
				chatEditorPane.setText(null);
			}
		});
		sendButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		sendButton.setBounds(0, 228, 574, 33);
		chatPanel.add(sendButton);
		
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		chatTextArea.setBounds(10, 11, 564, 137);
		chatPanel.add(chatTextArea);
	}
}
