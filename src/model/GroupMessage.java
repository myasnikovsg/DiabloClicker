/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.ItemGroup;
import java.io.Serializable;

/**
 * 
 * @author AlexFess
 */
public class GroupMessage implements Serializable {

	private static final long serialVersionUID = -6683614040020975613L;

	private String parserId;
	private byte operationCode;
	private ItemGroup group;

	public GroupMessage() {
		super();
	}

	public GroupMessage(String parserId, byte operationCode, ItemGroup group) {
		super();
		this.parserId = parserId;
		this.operationCode = operationCode;
		this.group = group;
	}

	public String getParserId() {
		return parserId;
	}

	public void setParserId(String parserId) {
		this.parserId = parserId;
	}

	public byte getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(byte operationCode) {
		this.operationCode = operationCode;
	}

	public ItemGroup getGroup() {
		return group;
	}

	public void setGroup(ItemGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "msg: (" + parserId + ", " + operationCode + ", " + group + ")";
	}

}
