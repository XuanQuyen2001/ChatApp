package ChattingSoftwear;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class ServerManager extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextField txtPort;
	private JPanel panel;
	private JLabel lbPort;
	private JTabbedPane tabbedPane;
	private ServerSocket srvSocket = null;
	private BufferedReader bf = null;
	private Thread th;
	private JButton btnStart;
	private Socket server = null;
	private DataInputStream inFromClient = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerManager frame = new ServerManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerManager() {
		super("Server Manager");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ServerManager.class.getResource("/img/iconfinder_Account.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 540, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setForeground(new Color(255, 255, 240));
		panel.setBackground(new Color(47, 79, 79));
		contentPane.add(panel, BorderLayout.NORTH);
		
		lbPort = new JLabel("Manager Port: ");
		lbPort.setFont(new Font("Arial", Font.BOLD, 13));
		lbPort.setForeground(new Color(255, 255, 240));
		panel.add(lbPort);
		
		txtPort = new JTextField();
		txtPort.setText("1902");
		panel.add(txtPort);
		txtPort.setColumns(10);
		
		btnStart = new JButton("Start");
		btnStart.setFont(new Font("Arial", Font.PLAIN, 12));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					srvSocket = new ServerSocket(Integer.parseInt(txtPort.getText())); 
					JOptionPane.showMessageDialog(null, "Server is running at port: "+ txtPort.getText());
					txtPort.setEditable(false);
					txtPort.setForeground(new Color(11, 60, 159));
					txtPort.setBackground(new Color(0, 221, 255));
					btnStart.setText("Running...");
					btnStart.setBackground(Color.RED);
					btnStart.setForeground(Color.WHITE);
					btnStart.setFont(new Font("Arial", Font.ITALIC, 12));
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Fail !!!");
					e1.printStackTrace();
				}
				startThread();
			}
		});
		btnStart.setForeground(Color.BLACK);
		btnStart.setBackground(Color.GREEN);
		panel.add(btnStart);
		
		
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(new Color(248, 248, 255));
		tabbedPane.setBackground(new Color(0, 0, 128));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
	}

	protected void startThread() {
		// TODO Auto-generated method stub
		th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				Socket aStaffSocket = srvSocket.accept();
				if(aStaffSocket != null) { 
					bf = new BufferedReader(new InputStreamReader(aStaffSocket.getInputStream()));
					String s = bf.readLine();
					int pos = s.indexOf(":");
					String staffName = 	s.substring(pos + 1);
					
					//create a tab for this connection
						ChatPanel chatPanel = new ChatPanel(aStaffSocket, "Manager", staffName);
						tabbedPane.add(staffName, chatPanel);
						chatPanel.updateUI();
						contentPane.validate();
						contentPane.repaint(); 				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}	
	}
	
}
