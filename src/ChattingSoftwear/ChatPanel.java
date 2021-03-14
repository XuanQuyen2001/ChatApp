package ChattingSoftwear;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.DropMode;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class ChatPanel extends JPanel {

	Socket socket = null;
	BufferedReader bf = null;
	DataOutputStream os = null;
	OutputThread outPutThread = null;
	String sender, receiver;
	JTextArea txtMessages;
	JTextArea txtMessage;
	JButton btnSend;
	JButton btnFile;
	JPanel newMessage;
	JPanel pnMain;
	final JFileChooser fileDialog = new JFileChooser();
	File file;
	public ChatPanel(Socket s, String sender, String receiver) {
		initComponents();
		this.socket = s;
		this.sender = sender;
		this.receiver = receiver;

		try {
			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os = new DataOutputStream(socket.getOutputStream());
			outPutThread = new OutputThread(socket, txtMessages, sender, receiver);
			outPutThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JTextArea getTxtMessages() {
		return this.txtMessages;
	}
	
	public void sendMessage() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();
		if (this.txtMessage.getText().trim().length() == 0)
			return;
		try {
			os.writeUTF(txtMessage.getText());
			this.txtMessages.append("\n" + "["+dtf.format(now)+"] "  + sender + ": " + txtMessage.getText());
			this.txtMessage.setText("");

		} catch (Exception except) {
			except.printStackTrace();
		}
	}
	
	public void sendFile() {
		try {
			int returnVal = fileDialog.showOpenDialog(pnMain);
			byte [] file = Files.readAllBytes(Paths.get(fileDialog.getSelectedFile().getAbsolutePath()));
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				os.writeUTF("DB_SENDING_FILE");
				os.writeUTF(fileDialog.getSelectedFile().getName());
				os.writeInt(file.length);
				os.write(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void btnSendActionPerformed(ActionEvent e) {
		sendMessage();
	}

	public void initComponents() {
		setLayout(new BorderLayout(0, 0));

		JPanel panelMessage = new JPanel();
		panelMessage.setBorder(new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelMessage.setToolTipText("");
		add(panelMessage, BorderLayout.SOUTH);
		panelMessage.setLayout(new BoxLayout(panelMessage, BoxLayout.X_AXIS));

		JScrollPane jScrollPane = new JScrollPane();
		panelMessage.add(jScrollPane);

		txtMessage = new JTextArea();
		txtMessage.setForeground(new Color(255, 0, 0));
		jScrollPane.setViewportView(txtMessage);

		ImageIcon iconSend = new ImageIcon();
		btnSend = new JButton("Send", new ImageIcon(ChatPanel.class.getResource("/img/send.png")));
		btnSend.setBackground(new Color(0, 255, 255));

		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnSendActionPerformed(e);
			}
		});
		
		btnFile = new JButton("Browes");
		btnFile.setBackground(new Color(211, 211, 211));
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendFile();
			}
		});
		btnFile.setIcon(new ImageIcon(ChatPanel.class.getResource("/img/iconfinder_ic_attach_file_48px_352032.png")));
		panelMessage.add(btnFile);
		panelMessage.add(btnSend);

		JScrollPane jScrollPane2 = new JScrollPane();
		add(jScrollPane2, BorderLayout.CENTER);

		txtMessages = new JTextArea();
		txtMessages.setForeground(new Color(255, 69, 0));
		txtMessages.setEditable(false);
		jScrollPane2.setViewportView(txtMessages);
	}
}
