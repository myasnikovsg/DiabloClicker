package model;

import java.io.Serializable;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author AlexFess
 */
public class Item implements Serializable {

	private static final long serialVersionUID = -3007235456277147663L;
	private transient final String ARRAY_STRING_SEPARATOR = "|";

	private long id;
	private byte typeId;
	private byte lvlGroup;
	private int bid;
	private int buyOut;
	private long endTime;
	private int mainProp;
	private Map<Integer, Integer> props;
	private int[] fullInfo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int[] getFullInfo() {
		return fullInfo;
	}

	public void setFullInfo(int[] fullInfo) {
		this.fullInfo = fullInfo;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public int getBuyOut() {
		return buyOut;
	}

	public void setBuyOut(int buyOut) {
		this.buyOut = buyOut;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
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

	public Map<Integer, Integer> getProps() {
		return props;
	}

	public void setProps(Map<Integer, Integer> props) {
		this.props = props;
	}

	public byte getTypeId() {
		return typeId;
	}

	public void setTypeId(byte typeId) {
		this.typeId = typeId;
	}

	public Object[] getArrayForSave() {
		Object[] ar = new Object[9];
		ar[0] = typeId;
		ar[1] = id;
		ar[2] = lvlGroup;
		ar[3] = mainProp;
		ar[4] = bid;
		ar[5] = buyOut;
		ar[6] = endTime;
		ar[7] = getFullInfoAsString();
		ar[8] = System.currentTimeMillis();
		return ar;
	}

	public Object[] getArrayForPreSaveSelect() {
		Object[] ar = new Object[3];
		ar[0] = typeId;
		ar[1] = getFullInfoAsString();
		ar[2] = endTime;
		return ar;
	}

	public String getFullInfoAsString() {
		StringBuilder str = new StringBuilder();
		for (int o : fullInfo) {
			str.append(o + ARRAY_STRING_SEPARATOR);
		}
		return str.toString();
	}

	public void setFullInfoFromString(String s) {
		StringTokenizer st = new StringTokenizer(s, ARRAY_STRING_SEPARATOR);
		fullInfo = new int[st.countTokens()];
		int ind = 0;
		while (st.hasMoreTokens()) {
			fullInfo[ind] = Integer.parseInt(st.nextToken());
			ind++;
		}
	}
}
