package ChattingSoftwear;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.border.TitledBorder;

import ConnectDAO.ChatDAO;
import Form.SignUp_Form;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Toolkit;


public class ClientChatter extends JFrame {

	private JPanel contentPane;
	private JTextField txtStaff;
	private JPasswordField txtPass;
	private JTextField txtServerIP;
	private JTextField txtServerPort;
	Socket mngSocket = null;
	String mngIP = "";
	int mngPort = 0;
	String staffName = "";
	BufferedReader bf = null;
	DataOutputStream os = null;
	ChatDAO A = null;
	SignUp_Form signUp;
	JPanel jPanel1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientChatter frame = new ClientChatter();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ClientChatter() throws SQLException {
		super("Client Chatter");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientChatter.class.getResource("/img/iconfinder_Chat_73220.png")));
		A = new ChatDAO();
		setResizable(false);
		setBounds(100, 100, 540, 550);
		contentPane =  new JPanel();	
		contentPane.setBackground(new Color(32, 178, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		jPanel1 = new JPanel();
		jPanel1.setBackground(new Color(47, 79, 79));
		jPanel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(255, 255, 255), new Color(160, 160, 160)), "Staff and Server Info", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		contentPane.add(jPanel1, BorderLayout.NORTH);
		
		JLabel lblStaff = new JLabel("USER:");
		lblStaff.setFont(new Font("Arial", Font.BOLD, 10));
		lblStaff.setForeground(new Color(255, 255, 255));
		lblStaff.setHorizontalAlignment(SwingConstants.RIGHT);
		
		txtStaff = new JTextField("USER");
		txtStaff.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(txtStaff.getText().trim().equals("USER")) {
					txtStaff.setText("");
					
				}
			}
				@Override
			public void focusLost(FocusEvent e) {
					if(txtStaff.getText().trim().equals("")) {
						txtStaff.setText("USER");
						
					}
				txtStaff.setForeground(Color.GRAY);
			}
		});
		txtStaff.setColumns(10);
		txtPass = new JPasswordField();
		txtStaff.setColumns(10);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					signUp = new SignUp_Form();
			}
		});
		btnSignUp.setBackground(new Color(255, 0, 0));
		btnSignUp.setForeground(new Color(255, 250, 250));
		btnSignUp.setFont(new Font("Arial", Font.BOLD, 12));
		
		
		JLabel lblManagerIp = new JLabel("Manager IP:");
		lblManagerIp.setFont(new Font("Arial", Font.BOLD, 10));
		lblManagerIp.setForeground(new Color(255, 255, 255));
		lblManagerIp.setHorizontalAlignment(SwingConstants.TRAILING);
		
		txtServerIP = new JTextField();
		txtServerIP.setText("localhost");
		txtServerIP.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Arial", Font.BOLD, 10));
		lblPort.setForeground(new Color(255, 255, 255));
		lblPort.setHorizontalAlignment(SwingConstants.TRAILING);
		
		txtServerPort = new JTextField();
		txtServerPort.setText("1902");
		txtServerPort.setColumns(10);
		this.setLocationRelativeTo(null);
		JButton btnConnect = new JButton("Login");
		btnConnect.setForeground(new Color(255, 250, 250));
		btnConnect.setFont(new Font("Arial", Font.BOLD, 12));
		btnConnect.setBackground(new Color(255, 0, 0));
		btnConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnConnectActionPerformed(arg0);
				
			}
		});
		
		JLabel lblPassword = new JLabel("PASSWORD:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Arial", Font.BOLD, 10));
		GroupLayout gl_jPanel1 = new GroupLayout(jPanel1);
		gl_jPanel1.setHorizontalGroup(
			gl_jPanel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jPanel1.createSequentialGroup()
					.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_jPanel1.createSequentialGroup()
							.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_jPanel1.createSequentialGroup()
									.addGap(24)
									.addComponent(lblStaff, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_jPanel1.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING)
								.addComponent(txtPass)
								.addComponent(txtStaff, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_jPanel1.createSequentialGroup()
							.addGap(27)
							.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnSignUp)))
					.addGap(90)
					.addGroup(gl_jPanel1.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblManagerIp)
						.addComponent(lblPort, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING)
						.addComponent(txtServerPort, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
						.addComponent(txtServerIP, GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_jPanel1.setVerticalGroup(
			gl_jPanel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_jPanel1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_jPanel1.createSequentialGroup()
							.addGroup(gl_jPanel1.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtStaff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblStaff, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_jPanel1.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_jPanel1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnConnect)
								.addComponent(btnSignUp))
							.addGap(20))
						.addGroup(gl_jPanel1.createSequentialGroup()
							.addGroup(gl_jPanel1.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtServerIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblManagerIp))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_jPanel1.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtServerPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPort))
							.addGap(62))))
		);
		jPanel1.setLayout(gl_jPanel1);
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				if(!txtStaff.getText().isEmpty() && mngSocket!=null) {
					String noti = " < Has Logout ! >" ; 
					
					try {
						os.write(noti.getBytes("UTF-8"));
						os.write(13);
						os.write(10);
				
						os.flush();
//						System.out.println(noti);
						JOptionPane.showMessageDialog(null, "\""+staffName+"\""+" Has Logout !");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
			
		});
	}
	
	private void btnConnectActionPerformed (ActionEvent e) { 
		mngIP = this.txtServerIP.getText();
		mngPort = Integer.parseInt(this.txtServerPort.getText());
		staffName = this.txtStaff.getText();
		
		String myPass = String.valueOf(txtPass.getPassword());
		if(A.checkLogin(staffName, myPass)==true) {
			
		//ket noi server
		try {
			mngSocket = new Socket(mngIP, mngPort);
			if(mngSocket != null) {
				ChatPanel chatPanel = new ChatPanel(mngSocket, staffName, "Manager");
				this.getContentPane().add(chatPanel);
				chatPanel.getTxtMessages().append("Login success !");
				chatPanel.updateUI();
				Thread th = new Thread();
				th.sleep(1000);
				bf = new BufferedReader(new InputStreamReader(mngSocket.getInputStream(), "UTF-8"));
				os = new DataOutputStream(mngSocket.getOutputStream());
				os.writeUTF("Staff: " + staffName);
				os.write(13);
				os.write(10);
				os.flush();
				jPanel1.setLayout(null);
				
			}
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(this, "Manager is not running.");
			}
		}
		else JOptionPane.showMessageDialog(null, "Wrong USERNAME or PASSWORD !");
	}

}
