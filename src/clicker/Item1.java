package clicker;

import java.util.ArrayList;


public class Item1 {
	int type;
	Double main_attr;
	String name;
	int rarity; // 0 - Rare, 1 - Set
	Double buyout;
	Double bid;
	Double time_left;
	ArrayList<Property> props;
	
	public static final String[] NAME = {"Axe", "Ceremonial Knife", "Hand Crossbow", "Dagger", "Fist Weapon", "Mace", "Mighty Weapon", "Spear", "Sword", "Wand", "Two-Handed Axe", "Bow", "Daibo", "Crossbow", "Two-Handed Mace", "Two-Handed Mighty Weapon", "Polearm", "Staff", "Two-Handed Sword", "Mojo", "Source", "Quiver", "Shield", "Amulet", "Belt", "Boots", "Bracers", "Chest Armor", "Cloak", "Gloves", "Helm", "Pants", "Mighty Belt", "Ring", "Shoulders", "Spitit Stone", "Voodoo Mask", "Wizard Hat", "Enchantress Focus", "Scoundrel Token", "Templar Relic"};
	
	public Item1(int type, double main_attr, String name, int rarity, ArrayList<Property> props, double buyout, double bid, double time_left){
		this.type = type;
		this.main_attr = main_attr;
		this.name = name;
		this.rarity = rarity;
		this.props = props;
		this.buyout = buyout;
		this.bid = bid;
		this.time_left = time_left;
	}
	
	public String toBaseString(){
		String res = "" + type + " " + Math.floor(main_attr * 10) + " " + rarity + " " + Math.floor(bid * 100) + " " + Math.floor(buyout * 100) + " " + Math.floor(time_left) + "\r\n";
		for (Property p : props)
			res += p.toBaseString();
		return res;
	}
	
	public String toString(){
		String res = "";
		res += name + "\r\n";
		switch (rarity){
			case 0 :
				res += "Rare";
				break;
			case 1 :
				res += "Legendary";
				break;
		}
		res += " ";
		res += NAME[type] + "\r\n";
		res += "Main attribute = " + main_attr + "\r\n";
		for (Property p : props){
			res += p.toString() + "\r\n";
		}
		res += "bid = " + bid + "\r\n";
		res += "buyout = " + buyout + "\r\n";
		res += "time = " + time_left + "\r\n";
		return res;
	}
}
