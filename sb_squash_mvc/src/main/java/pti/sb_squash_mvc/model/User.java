package pti.sb_squash_mvc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import pti.sb_squash_mvc.util.Roles;

@Table(name="users")
public class User {
	
	@Id
	@Column(value ="id")
	private int id;
	
	@Column(value ="name")
	private String name;
	
	@Column(value ="password")
	private String password;
	
	@Column(value ="role")
	private  Roles role;
	
	@Column(value ="is_first_login")
	private boolean firstLogin;
	
	@Column(value ="logged_in")
	private boolean loggedIn;

	public User(int id, String name, String password, Roles role, boolean firstLogin, boolean loggedIn) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.role = role;
		this.firstLogin = firstLogin;
		this.loggedIn = loggedIn;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", role=" + role + ", firstLogin="
				+ firstLogin + ", loggedIn=" + loggedIn + "]";
	}
	
	
	
	
}
