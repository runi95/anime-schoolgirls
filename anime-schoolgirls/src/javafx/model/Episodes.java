package javafx.model;

public class Episodes {
	private int epnumber;
	private String name;
	
	public Episodes(int epnumber, String name) {
		this.epnumber = epnumber;
		this.name = name;
	}
	
	public int getEpnumber() {
		return epnumber;
	}
	public void setEpnumber(int epnumber) {
		this.epnumber = epnumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
