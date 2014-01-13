package clicker;
import java.util.ArrayList;


public class Property {
	int type;
	
	ArrayList<Integer> value;
	
	public Property(int type, ArrayList<Integer> value){
		this.type = type;
		this.value = value;
	}
	
	public String toBaseString(){
		if (value.size() == 0)
			return "";
		String res = "" + type + " " + value.get(0) + (value.size() > 1 ? " " + value.get(1) + " " : "");
		if (type == 1)
			res += ((value.get(2) + value.get(3)) / 2) * value.get(4);
		res += "\r\n";
		return res;
	}
	
	public int toOneNumbered(){
		return 0;
	}
	
	public String toString(){
		switch (type){ 
			case 0   : return "Increases Attack Speed by " + value.get(0) + "%";
			case 1   : return "" + value.get(0) + "." + value.get(1) + "% to inflict Bleed for " + value.get(2) + "-" + value.get(3) + " damage over " + value.get(4) + " seconds";
			case 2   : return "+" + value.get(0) + "-" + value.get(1) + " Arcane Damage";
			case 3   : return "+" + value.get(0) + "-" + value.get(1) + " Cold Damage";
			case 4   : return "+" + value.get(0) + "-" + value.get(1) + " Fire Damage";
			case 5   : return "+" + value.get(0) + "-" + value.get(1) + " Holy Damage";
			case 6   : return "+" + value.get(0) + "-" + value.get(1) + " Lightning Damage";
			case 7   : return "+" + value.get(0) + "-" + value.get(1) + " Poison Damage";
			case 8   : return "+" + value.get(0) + " Minimum Damage";
			case 9   : return "+" + value.get(0) + " Maximum Damage";
			case 10  : return "Increases Damage Against Elites by " + value.get(0) + "%";
			case 11  : return "Critical Hit Damage Increased by " + value.get(0) + "%";
			case 12  : return "+" + value.get(0) + "% Damage";
			case 13  : return value.get(0) + "." + value.get(1) + "% Chance to Blind on Hit";
			case 14  : return value.get(0) + "." + value.get(1) + "% Chance to Chill on Hit";
			case 15  : return value.get(0) + "." + value.get(1) + "% Chance to Fear on Hit";
			case 16  : return value.get(0) + "." + value.get(1) + "% Chance to Freeze on Hit";
			case 17  : return value.get(0) + "." + value.get(1) + "% Chance to Immobilize on Hit";
			case 18  : return value.get(0) + "." + value.get(1) + "% Chance to Knockback on Hit";
			case 19  : return value.get(0) + "." + value.get(1) + "% Chance to Slow on Hit";
			case 20  : return value.get(0) + "." + value.get(1) + "% Chance to Stun on Hit";
			case 21  : return value.get(0) + "." + value.get(1) + "% of Damage Dealt Is Converted to Life";
			case 22  : return "+" + value.get(0) + " Life after Each Kill";
			case 23  : return "Each Hit Adds +" + value.get(0) + " Life";
			case 24  : return "Gain " + value.get(0) + "." + value.get(1) + " Life per Spirit Spent";
			case 25  : return "Critical Hits grant " + value.get(0) + " Arcane Power";
			case 26  : return "Increases Hatred Regeneration by " + value.get(0) + "." + value.get(1) + " per Second";
			case 27  : return "Increases Mana Regeneration by " + value.get(0) + " per Second";
			case 28  : return "+" + value.get(0) + " Maximum Arcane Power";
			case 29  : return "+" + value.get(0) + " Maximum Discipline";
			case 30  : return "+" + value.get(0) + " Maximum Fury";
			case 31  : return "+" + value.get(0) + " Maximum Mana";
			case 32  : return "Increases Spirit Regeneration by " + value.get(0) + "." + value.get(1) + " per Second";
			case 33  : return "+" + value.get(0) + " Dexterity";
			case 34  : return "Monster kills grant +" + value.get(0) + " expirience";
			case 35  : return "+" + value.get(0) + " Intelligence";
			case 36  : return "+" + value.get(0) + " Strength";
			case 37  : return "+" + value.get(0) + " Vitality";
			case 38  : return "Reduces resource cost of Firebomb by " + value.get(0) + " Mana";
			case 39  : return "Increases Haunt Damage by " + value.get(0) + "%";
			case 40  : return "Increases Plague of Thoads Damage by " + value.get(0) + "%";
			case 41  : return "Increases Critical Hit Chance of Energy Twister by " + value.get(0) + "%";
			case 42  : return "Increases Magic Missile Damage by " + value.get(0) + "%";
			case 43  : return "Ignores Durability Loss";
			case 44  : return "Level Requirement Reduced by " + value.get(0);
			case 45  : return "Reduces resource cost of Hammer of the Ancients by " + value.get(0) + " Fury";
			case 46  : return "Increases Critical Hit Chance of Overpower by " + value.get(0) + "%";
			case 47  : return "Increases Critical Hit Chance of Seismic Slam by " + value.get(0) + "%";
			case 48  : return "Increases Critical Hit Chance of Whirlwind by " + value.get(0) + "%";
			case 49  : return "Reduces resource cost of Lashing Tail Kick by " + value.get(0) + " Spirit";
			case 50  : return "Increases Critical Hit Chance of Tempest Rush by " + value.get(0) + "%";
			case 51  : return "Increases Critical Hit Chance of Wave of Light by " + value.get(0) + "%";
			case 52  : return "Increases Poison Dart Damage by " + value.get(0) + "%";
			case 53  : return "Increases Spirit Barrage Damage by " + value.get(0) + "%";
			case 54  : return "Reduces cooldown of Wall of Zombies by " + value.get(0) + " seconds";
			case 55  : return "Reduces resource cost of Zombie Charger by " + value.get(0) + " Mana";
			case 56  : return "+" + value.get(0) + " Resistance to All Elements";
			case 57  : return "+" + value.get(0) + " Arcane Resistance";
			case 58  : return "+" + value.get(0) + " Armor";
			case 59  : return "+" + value.get(0) + " % Chance to Block";
			case 60  : return "+" + value.get(0) + " Cold Resistance";
			case 61  : return "+" + value.get(0) + " Fire Resistance";
			case 62  : return "+" + value.get(0) + " Lightning Resistance";
			case 63  : return "Melee attackers takes " + value.get(0) + " damage per hit";
			case 64  : return "+" + value.get(0) + " Physical Resistance";
			case 65  : return "+" + value.get(0) + " Poison Resistance";
			case 66  : return "Reduces damage from elites by " + value.get(0) + "%";
			case 67  : return "Reduces damage from melee attacks by " + value.get(0) + "%";
			case 68  : return "Reduces damage from ranged attacks by " + value.get(0) + "%";
			case 69  : return "Reduces duration of control imparing effects by " + value.get(0) + "%";
			case 70  : return "Health Globes Grant +" + value.get(0) + " Life";
			case 71  : return "+" + value.get(0) + "% Life";
			case 72  : return "Regenerates " + value.get(0) + " Life per Second";
			case 73  : return "Increases Bola Shot Damage by " + value.get(0) + "%";
			case 74  : return "Increases Elemental Arrow Damage by " + value.get(0) + "%";
			case 75  : return "Increases Entagling Shot Damage by " + value.get(0) + "%";
			case 76  : return "Increases Hungering Arrow Damage by " + value.get(0) + "%";
			case 77  : return "Increases Critical Hit Chance of Multishot by " + value.get(0) + "%";
			case 78  : return "Increases Critical Hit Chance of Rapid Fire by " + value.get(0) + "%";
			case 79  : return "Increases Critical Hit Chance of Arcane Orb by " + value.get(0) + "%";
			case 80  : return "Increases Duration of Blizzard by " + value.get(0) + " Seconds";
			case 81  : return "Reduces resource cost of Meteor by " + value.get(0) + " Arcane Power";
			case 82  : return "Increases Shock Pulse Damage by " + value.get(0) + "%";
			case 83  : return "Increases Spectral Blade Damage by " + value.get(0) + "%";
			case 84  : return "+" + value.get(0) + "% Extra Gold from Monsters";
			case 85  : return value.get(0) + "% Better Chance of Finding Magical Items";
			case 86  : return "Increases Bash Damage by " + value.get(0) + "%";
			case 87  : return "Increases Cleave Damage by " + value.get(0) + "%";
			case 88  : return "Increases Frenzy Damage by " + value.get(0) + "%";
			case 89  : return "Reduces resource cost of Rend by " + value.get(0) + " Fury";
			case 90  : return "Increases Critical Hit Chance of Revenge by " + value.get(0) + "%";
			case 91  : return "Reduces resource cost of Weapon Throw by " + value.get(0) + " Fury";
			case 92  : return "Reduces resource cost of Chakram by " + value.get(0) + " Hatred";
			case 93  : return "Increases Evasive Fire Damage by " + value.get(0) + "%";
			case 94  : return "Increases Grenades Damage by " + value.get(0) + "%";
			case 95  : return "Reduces resource cost of Impale by " + value.get(0) + " Hatred";
			case 96  : return "Increases Spike Trap Damage by " + value.get(0) + "%";
			case 97  : return "Increases Crippling Wave Damage by " + value.get(0) + "%";
			case 98  : return "Reduces resource cost of Cyclone Strike by " + value.get(0) + " Spirit";
			case 99  : return "Increases Deadly Reach Damage by " + value.get(0) + "%";
			case 100 : return "Increases Exploding Palm Damage by " + value.get(0) + "%";
			case 101 : return "Increases Fists of Thunder Damage by " + value.get(0) + "%";
			case 102 : return "Increases Sweeping Wind Damage by " + value.get(0) + "%";
			case 103 : return "Increases Way of the Hundred Fists Damage by " + value.get(0) + "%";
			case 104 : return "Increases Critical Hit Chance of Acid Cloud by " + value.get(0) + "%";
			case 105 : return "Reduces resource cost of Firebats by " + value.get(0) + " Mana";
			case 106 : return "Increases Locust Swarm Damage by " + value.get(0) + "%";
			case 107 : return "Reduces cooldown of Summon Zombie Dogs by " + value.get(0) + " seconds";
			case 108 : return "Reduces resource cost of Arcane Torrent by " + value.get(0) + " Arcane Power";
			case 109 : return "Reduces resource cost of Desintegrate by " + value.get(0) + " Arcane Power";
			case 110 : return "Increases Electrocute Damage by " + value.get(0) + "%";
			case 111 : return "Increases Critical Hit Chance of Explosive Blast by " + value.get(0) + "%";
			case 112 : return "Reduces resource cost of Hydra by " + value.get(0) + " Arcane Power";
			case 113 : return "Increases Critical Hit Chance of Ray of Frost by " + value.get(0) + "%";
			case 114 : return "+" + value.get(0) + " Movement Speed";
			case 115 : return "Increases Gold and Health pickup by " + value.get(0) + "yards";
			case 116 : return "Critical Hit Chance Increased by " + value.get(0) + "." + value.get(1) + "%";
			case 117 : return "Attack Speed Increased by " + value.get(0) + "%";
			case 118 : return "+" + value.get(0) + "-" + value.get(1) + " Damage";
			case 119 : return "+" + value.get(0) + "% Damage to Humans";
			case 120 : return "+" + value.get(0) + "% Damage to Undead";
			case 121 : return "Adds " + value.get(0) + "% to Fire Damage";
			case 122 : return "+" + value.get(0) + "% Damage to Demons";
			case 123 : return "+" + value.get(0) + "% Damage to Beasts";
			case 124 : return "+" + value.get(0) + "." + value.get(1) + " Attacks per Second";
			case 125 : return "Adds " + value.get(0) + "% to Lightining Damage";
			case 126 : return "Gain " + value.get(0) + " Life per Fury Spent";
			case 127 : return "Adds " + value.get(0) + "% to Poison Damage";
			case 128 : return "Adds " + value.get(0) + "% to Holy Damage";
			case 129 : return "Reduces damage from Lightning attacks by " + value.get(0) + "%";
			case 130 : return "Reduces damage from Cold attacks by " + value.get(0) + "%";
			case 131 : return "Adds " + value.get(0) + "% to Arcane Damage";
			case 132 : return "Adds " + value.get(0) + "% to Cold Damage";
			case 133 : return "Increases Critical Hit Chance of Strafe by " + value.get(0) + "%";
			case 134 : return "Reduces resource cost of Cluster Arrow by " + value.get(0) + " Hatred";
			case 135 : return "Increases Corpse Spiders Damage by " + value.get(0) + "%";
			case 136 : return "Socketed[" + value.get(0) + "]";
			default  : return "Undefined";
		}
	}
}
