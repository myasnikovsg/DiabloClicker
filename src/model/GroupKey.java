package model;

import java.io.Serializable;


public class GroupKey implements Serializable{
	private static final long serialVersionUID = -2298846626371885864L;
	
	private byte region;
	private int moneyType;
	private byte itemType;
	private int groupId;

	public GroupKey() {
		super();
	}

	public GroupKey(byte region, int moneyType, byte itemType, int groupId) {
		super();
		this.region = region;
		this.moneyType = moneyType;
		this.itemType = itemType;
		this.groupId = groupId;
	}

	public byte getRegion() {
		return region;
	}

	public void setRegion(byte region) {
		this.region = region;
	}

	public int getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(int moneyType) {
		this.moneyType = moneyType;
	}

	public byte getItemType() {
		return itemType;
	}

	public void setItemType(byte itemType) {
		this.itemType = itemType;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + groupId;
		result = prime * result + itemType;
		result = prime * result + moneyType;
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
		GroupKey other = (GroupKey) obj;
		if (groupId != other.groupId)
			return false;
		if (itemType != other.itemType)
			return false;
		if (moneyType != other.moneyType)
			return false;
		if (region != other.region)
			return false;
		return true;
	}
}
