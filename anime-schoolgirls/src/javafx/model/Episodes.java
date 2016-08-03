package javafx.model;

public class Episodes {
	private int epnumber;
	private String name, epLink;
	
	public Episodes(int epnumber, String name, String epLink) {
		this.epnumber = epnumber;
		this.name = name;
		this.epLink = epLink;
	}
	
	public int getEpnumber() {
		return epnumber;
	}
	public void setEpnumber(int epnumber) {
		this.epnumber = epnumber;
	}
	
	public String getEpLink() {
		return epLink;
	}
	public void setEpLink(String epLink) {
		this.epLink = epLink;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
