package ConnectDAO;

public class Account {
	String username;
	String password;
	String confirm;
	public Account() {
		super();
		
	}
	public Account(String username, String password, String confirm) {
		super();
		this.username = username;
		this.password = password;
		this.confirm = confirm;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirm() {
		return confirm;
	}
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}	
}
