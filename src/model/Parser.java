package model;

import java.io.Serializable;
import java.util.List;

public class Parser implements Serializable{

	private String id;
	private byte region;
	private List<Integer> moneyTypes;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte getRegion() {
		return region;
	}
	public void setRegion(byte region) {
		this.region = region;
	}
	public List<Integer> getMoneyTypes() {
		return moneyTypes;
	}
	public void setMoneyTypes(List<Integer> moneyTypes) {
		this.moneyTypes = moneyTypes;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((moneyTypes == null) ? 0 : moneyTypes.hashCode());
		result = prime * result + region;
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
		Parser other = (Parser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
