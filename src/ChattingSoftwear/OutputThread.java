package ChattingSoftwear;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class OutputThread extends Thread{
	Socket socket;
	JTextArea txt;
	BufferedReader bf;
	String sender;
	String receiver;
	boolean isStop = false;
	DataInputStream dis;
	DateTimeFormatter dtf;
	LocalDateTime now;
	public OutputThread(Socket socket, JTextArea txt, String sender, String receiver) {
		super();
		this.socket = socket;
		this.txt = txt;
		this.sender = sender;
		this.receiver = receiver;
		try {
			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dis = new DataInputStream(socket.getInputStream());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Network rerror");
			System.exit(0 );
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				String sms = dis.readUTF();
				if (sms.equals("DB_SENDING_FILE")) {
					String fileName = dis.readUTF();
					int length = dis.readInt();
					
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Save as");
					fileChooser.setSelectedFile(new File(fileName));
					int userSelection = fileChooser.showSaveDialog(null);
					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File myObj = fileChooser.getSelectedFile();
						if (length > 0) {
							byte[] file = new byte[length];
							dis.readFully(file,0,file.length);
							try (FileOutputStream fos = new FileOutputStream(myObj.getAbsolutePath())){
								fos.write(file);
								txt.append("\n" + "["+dtf.format(now)+"] "+ receiver + ":" + fileName);
								JOptionPane.showMessageDialog(null, receiver+" Recive file Success !!!");
							}
						}
					}	
				}
				else {
					dtf = DateTimeFormatter.ofPattern("HH:mm");
					now = LocalDateTime.now();
					txt.append("\n" + "["+dtf.format(now)+"] "+ receiver + ":" + sms);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
