package model;

import java.io.Serializable;

/**
 *
 * @author AlexFess
 */
public class ItemGroup implements Serializable{
	
    private static final long serialVersionUID = 3761110191023462730L;
    
    private byte region;
    private int money_type;
    private byte item_type;
    private int id;
	private byte lvlGroup;
    private int mainProp;
    private int buyOut;
    private int props[];

    public byte getRegion() {
		return region;
	}

	public void setRegion(byte region) {
		this.region = region;
	}

	public int getMoney_type() {
		return money_type;
	}

	public void setMoney_type(int money_type) {
		this.money_type = money_type;
	}

	public byte getItem_type() {
		return item_type;
	}

	public void setItem_type(byte item_type) {
		this.item_type = item_type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBuyOut() {
        return buyOut;
    }

    public void setBuyOut(int buyOut) {
        this.buyOut = buyOut;
    }

    public byte getLvlGroup() {
        return lvlGroup;
    }

    public void setLvlGroup(byte lvlGroup) {
        this.lvlGroup = lvlGroup;
    }

    public int getMainProp() {
        return mainProp;
    }

    public void setMainProp(int mainProp) {
        this.mainProp = mainProp;
    }

    public int[] getProps() {
        return props;
    }

    public void setProps(int[] props) {
        this.props = props;
    }

    public GroupKey getKey(){
    	return new GroupKey(region, money_type, item_type, id);
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + item_type;
		result = prime * result + money_type;
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
		ItemGroup other = (ItemGroup) obj;
		if (id != other.id)
			return false;
		if (item_type != other.item_type)
			return false;
		if (money_type != other.money_type)
			return false;
		if (region != other.region)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder build = new StringBuilder();
		build.append("group:{");
		build.append("region = ");
		build.append(region);
		build.append(", money = ");
		build.append(money_type);
		build.append(", item = ");
		build.append(item_type);
		build.append(", id = ");
		build.append(id);
		build.append(", lvl = ");
		build.append(lvlGroup);
		build.append(", MP = ");
		build.append(mainProp);
		build.append(", BO = ");
		build.append(buyOut);
		build.append(", prop = [");
		for (int i = 0; i < props.length; i++){
			if (i != 0){
				build.append(", ");
			}
			build.append(props[i]);
		}
		build.append("]}");
		return build.toString();
	}
    
}
