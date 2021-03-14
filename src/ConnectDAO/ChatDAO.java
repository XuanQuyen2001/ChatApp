package ConnectDAO;
import java.sql.*;
import java.util.*;

public class ChatDAO {
	 Connection conn;
	
	public ChatDAO() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatclient","root","thanhxuan2701");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean checkLogin(String username, String password) {
		String sql = "SELECT * FROM account WHERE USER = ? AND PASSWORD = ?";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) return true; //Truy van dung: Co username va password trong csdl
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	public boolean signUp(Account s) {
		String sql = "INSERT INTO account (`USER`, `PASSWORD`, `CONFIRM PASSWORD`) VALUE(?, ?, ?)";
		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, s.getUsername());
			pst.setString(2, s.getPassword());
			pst.setString(3, s.getConfirm());
			return pst.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public static void main(String[] args) throws SQLException {
		ChatDAO A = new ChatDAO();
		System.out.println(A.checkLogin("xuanquyen", "xuanquyen"));
		System.out.println(A.checkLogin("thanhxuan", "thanhxuan"));
		System.out.println(A.checkLogin("duongleha", "leha"));
		
	}
}
