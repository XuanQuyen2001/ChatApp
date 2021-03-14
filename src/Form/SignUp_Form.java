package Form;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ConnectDAO.Account;
import ConnectDAO.ChatDAO;

import java.awt.Color;
import java.awt.Window.Type;
import java.awt.Toolkit;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class SignUp_Form extends JFrame {
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp_Form frame = new SignUp_Form();
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
	public SignUp_Form() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(SignUp_Form.class.getResource("/img/iconfinder_compose_1055085.png")));
		setTitle("Sign Up");
		setForeground(Color.BLUE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 394, 271);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(47, 79, 79));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(47, 79, 79));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		txtUser = new JTextField();
		txtUser.setBounds(170, 82, 118, 19);
		panel.add(txtUser);
		txtUser.setColumns(10);

		JLabel lbUser = new JLabel("User name: ");
		lbUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lbUser.setFont(new Font("Arial", Font.PLAIN, 13));
		lbUser.setForeground(SystemColor.textHighlightText);
		lbUser.setBounds(85, 83, 78, 16);
		panel.add(lbUser);

		JLabel lbPassword = new JLabel("Password:");
		lbPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lbPassword.setForeground(Color.WHITE);
		lbPassword.setFont(new Font("Arial", Font.PLAIN, 13));
		lbPassword.setBounds(85, 118, 78, 16);
		panel.add(lbPassword);

		JLabel lbConfirm = new JLabel("Confirm password:");
		lbConfirm.setHorizontalAlignment(SwingConstants.RIGHT);
		lbConfirm.setForeground(Color.WHITE);
		lbConfirm.setFont(new Font("Arial", Font.PLAIN, 13));
		lbConfirm.setBounds(45, 153, 118, 16);
		panel.add(lbConfirm);

		JButton lbSignUp = new JButton("Sign Up");
		lbSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account chatter = new Account();
				chatter.setUsername(txtUser.getText());
				chatter.setPassword(String.valueOf(txtPassword.getPassword()));
				chatter.setConfirm(String.valueOf(txtConfirm.getPassword()));
				
				System.out.println(String.valueOf(txtPassword.getPassword()));
				System.out.println(String.valueOf(txtConfirm.getPassword()));
				
				try {
						if (String.valueOf(txtPassword.getPassword()).equals(String.valueOf(txtConfirm.getPassword())) ) {
							new ChatDAO().signUp(chatter); 
							JOptionPane.showMessageDialog(null, "SIGN UP SUCCESS !!!");
							txtUser.setText("");
							txtPassword.setText("");
							txtConfirm.setText("");
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "PASSWORD NOT SAME !!!");
						}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1);
					
				}
			}
		});
		lbSignUp.setFont(new Font("Arial", Font.PLAIN, 13));
		lbSignUp.setBackground(new Color(0, 255, 255));
		lbSignUp.setBounds(198, 192, 85, 21);
		panel.add(lbSignUp);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(SignUp_Form.class.getResource("/img/iconfinder_Account.png")));
		lblNewLabel.setBounds(162, 16, 53, 56);
		panel.add(lblNewLabel);

		JButton lbCancel = new JButton("Cancel");
		lbCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		lbCancel.setBackground(new Color(0, 255, 255));
		lbCancel.setFont(new Font("Arial", Font.PLAIN, 13));
		lbCancel.setBounds(95, 192, 85, 21);
		panel.add(lbCancel);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(170, 117, 118, 19);
		panel.add(txtPassword);

		txtConfirm = new JPasswordField();
		txtConfirm.setBounds(170, 152, 118, 19);
		panel.add(txtConfirm);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
