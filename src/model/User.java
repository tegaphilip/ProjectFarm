package model;

import java.io.Serializable;

public abstract class User implements Serializable {

	private static final long serialVersionUID = -5822209320482420372L;
	
	public static final String USER_TYPE_OWNER = "owner";
	public static final String USER_TYPE_EVALUATOR = "evaluator";

	private String email;
	private String name;

	private String password;
	private String userType;

	public User(String email, String name, String password, String userType) {
		setEmail(email);
		setName(name);
		setPassword(password);
		setUserType(userType);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	public String getUserType() {
		return userType;
	}
	
	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
