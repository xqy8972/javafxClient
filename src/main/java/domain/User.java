package domain;

public class User {
	private String name;

	private String username;

	private String email;

	private int uid;

	private String avatar;

	public User() {
	}

	public User(String name, String username, String email, int uid, String avatar) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.uid = uid;
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public User setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public int getUid() {
		return uid;
	}

	public User setUid(int uid) {
		this.uid = uid;
		return this;
	}

	public String getAvatar() {
		return avatar;
	}

	public User setAvatar(String avatar) {
		this.avatar = avatar;
		return this;
	}
}
