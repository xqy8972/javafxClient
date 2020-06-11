package domain;

public class Role {

	private String name;
	private int num;

	public Role(String name, int num) {
		this.name = name;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public int getNum() {
		return num;
	}
}
