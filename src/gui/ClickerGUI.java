package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import clicker.Click;
import clicker.Item1;
import clicker.Utils;

import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClickerGUI {
	static JFrame frame;
	static JTextArea algo;
	static JScrollPane scrollPane;
	static JLabel minus;
	static JLabel[] command_label;
	static JButton[] add_command_button;
	static JComboBox<String> class_combo;
	static JComboBox<String> rarity_combo;
	static JComboBox<String> item_place_combo;
	static JComboBox<String> item_type_combo;
	static JTextField level_bound[];
	static JTextField process_page;
	static JFileChooser chooser;
	static JButton start;
	static JButton save;
	static JButton load;
	static JButton delete; 
	static boolean[] selected;
	static int selected_class;
	static int selected_place;
	static String[] temp;
	static final String[] LABEL = {"Select Level Bounds", "Select Rarity", "Select Class", "Select Item Place", "Select Item Type", "Process Pages"};
	static final String[] CLASS = {"Barbarian", "Demon Hunter", "Monk", "Witch Doctor", "Wizard"};
	static final String[] RARITY = {"Rare", "Legendary"};
	static final String[] ITEM_PLACE = {"1-Hand", "2-Hand", "Off-Hand", "Armor", "Follower Special"};
	static ArrayList<String> file;
	
	private static class add_command_listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch(((JButton)arg0.getSource()).getName().charAt(1)){
			case '0' : 
				file.add("0 " + level_bound[0].getText() + " " + level_bound[0].getText()); 
				dispatchEvent(file.get(file.size() - 1));
				break;
			case '1' :
				file.add("1 " + rarity_combo.getSelectedIndex());
				dispatchEvent(file.get(file.size() - 1));
				break;
			case '2' :
				file.add("2 " + class_combo.getSelectedIndex());
				selected_class = class_combo.getSelectedIndex();
				dispatchEvent(file.get(file.size() - 1));
				break;
			case '3' : 
				file.add("3 " + item_place_combo.getSelectedIndex());
				selected_place = item_place_combo.getSelectedIndex();
				dispatchEvent(file.get(file.size() - 1));
				break;
			case '4' :
				int k = 0;
				int ans = 0;
				for (int i = 0; i < Utils.item_amount[selected_place]; i++)
					if (Utils.can_equip[selected_class][selected_place][i] == 1) 
						if (k++ == item_type_combo.getSelectedIndex()) {
							ans = i;
							break;
						}
				file.add("4 " + ans);
				dispatchEvent(file.get(file.size() - 1));
				break;
			case '5' :
				file.add("5 " + (process_page.getText().length() != 0 ? process_page.getText() : "-1"));
				dispatchEvent(file.get(file.size() - 1));
				break;
			case '6' :
				int res = chooser.showDialog(frame, "Save");
				if (res == JFileChooser.APPROVE_OPTION){
					File f = chooser.getSelectedFile();
					try {
						PrintWriter pw = new PrintWriter(f);
						for (int i = 0; i < file.size(); i++)
							pw.println(file.get(i));
						pw.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}	
				}
				break;
			case '7' :
				try {
					PrintWriter pw = new PrintWriter(new File(chooser.getCurrentDirectory().getPath() + "\\temp.txt"));
					pw.println(file.size());
					for (int i = 0; i < file.size(); i++)
						pw.println(file.get(i));
					pw.close();
					Click.main(new String[]{chooser.getCurrentDirectory().getPath() + "\\temp.txt"});
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case '8' :
				if (file.size() == 1) {
					algo.setText("");
					file.remove(0);
					for (int i = 0; i < 6; i++)
						selected[i] = false;
					break;
				}
				algo.setText(algo.getText().substring(0, algo.getText().length() - 1));
				algo.setText(algo.getText().substring(0, algo.getText().lastIndexOf('\n')));
				algo.append("\n");
				for (int i = file.get(file.size() - 1).charAt(0) - '0'; i < 6; i++)
					selected[i] = false;
				file.remove(file.size() - 1);
				break;
			case '9' :
				res = chooser.showDialog(frame, "Load");
				if (res == JFileChooser.APPROVE_OPTION){
					algo.setText("");
					file = new ArrayList<String>();
					File f = chooser.getSelectedFile();
					try {
						Scanner sc = new Scanner(f);
						while (sc.hasNextLine()) {
							file.add(sc.nextLine());
							dispatchEvent(file.get(file.size() - 1));
						}
						sc.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}	
				}
			}
			resolve();
		}
		
	}
	
	private static void dispatchEvent(String event){
		StringTokenizer st = new StringTokenizer(event, " ");
		int c = Integer.parseInt(st.nextToken());
		switch(c) {
		case 0 :
			algo.append("   Select Level Bounds from " + Integer.parseInt(st.nextToken()) + " to " + Integer.parseInt(st.nextToken()) + ".\n");
			selected[0] = true;
			break;
		case 1 : 
			algo.append("   Select " + RARITY[Integer.parseInt(st.nextToken())] + " Items.\n");
			selected[1] = true;
			break;
		case 2 : 
			algo.append("   Select " + CLASS[Integer.parseInt(st.nextToken())] + ".\n");
			selected[2] = true;
			break;
		case 3 :
			selected[3] = true;
			algo.append("   Select " + ITEM_PLACE[Integer.parseInt(st.nextToken())] + ".\n");
			break;
		case 4 :
			selected[4] = true;
			int offset = 0;
			for (int i = 0; i < selected_place; i++)
				offset += Utils.item_amount[i];
			algo.append("   Select " + Item1.NAME[offset + Integer.parseInt(st.nextToken())] + ".\n");
			break;
		case 5 :
			selected[5] = true;
			int k = Integer.parseInt(st.nextToken());
			algo.append("   Process " + (k > 0 ? k : "available") + " pages.\n");
			break;
		}
	}
	
	private static void resolve(){
		add_command_button[0].setEnabled(true);
		delete.setEnabled(file.size() != 0);
		for (int i = 1; i < 6; i++)
			add_command_button[i].setEnabled(selected[i - 1]);
		if (selected[3]){
			int offset = 0;
			for (int i = 0; i < selected_place; i++)
				offset += Utils.item_amount[i];
			int size = 0;
			for (int i = 0; i < Utils.item_amount[selected_place]; i++)
				size += Utils.can_equip[selected_class][selected_place][i];
			temp = new String[size];
			int k = 0;
			for (int i = 0; i < Utils.item_amount[selected_place]; i++)
				if (Utils.can_equip[selected_class][selected_place][i] == 1)
					temp[k++] = Item1.NAME[offset + i];
			frame.remove(item_type_combo);
			item_type_combo = new JComboBox<String>(temp);
			item_type_combo.setBounds(240, 180, 250, 20);
		    item_type_combo.setFont(algo.getFont());
		    frame.add(item_type_combo);
		    frame.repaint();
		}
	}
	
	private static void createAndShowGUI(){
		frame = new JFrame();
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBounds(100, 200, 1100, 800);
	    frame.setTitle("Clicker");
	    frame.setVisible(true);
	    
	    algo = new JTextArea();
        algo.setEditable(false);
        algo.setFont(algo.getFont().deriveFont(18.0f));
        scrollPane = new JScrollPane(algo);
        scrollPane.setBounds(650, 20, 400, 670);
        frame.add(scrollPane);
        
        command_label = new JLabel[6];
        for (int i = 0; i < 6; i++) {
        	command_label[i] = new JLabel(LABEL[i]);
        	command_label[i].setFont(algo.getFont());
        	command_label[i].setBounds(20, 20 + i * 40, 200, 20);
        	frame.add(command_label[i]);
        }
        
        level_bound = new JTextField[2];
        for (int i = 0; i < 2; i++){
        	level_bound[i] = new JTextField();
        	level_bound[i].setFont(algo.getFont());
        	level_bound[i].setText("60");
        	level_bound[i].setBounds(240 + i * 42, 16, 30, 30);
        	frame.add(level_bound[i]);
        }
        
        minus = new JLabel("-");
        minus.setFont(algo.getFont());
        minus.setBounds(272, 16, 10, 30);
        frame.add(minus);
        
        rarity_combo = new JComboBox<String>(RARITY);
        rarity_combo.setBounds(240, 60, 150, 20);
        rarity_combo.setFont(algo.getFont());
        frame.add(rarity_combo);
        
        class_combo = new JComboBox<String>(CLASS);
        class_combo.setBounds(240, 100, 250, 20);
        class_combo.setFont(algo.getFont());
        frame.add(class_combo);
        
        item_place_combo = new JComboBox<String>(ITEM_PLACE);
        item_place_combo.setBounds(240, 140, 250, 20);
        item_place_combo.setFont(algo.getFont());
        frame.add(item_place_combo);
        
        item_type_combo = new JComboBox<String>();
        item_type_combo.setBounds(240, 180, 250, 20);
        item_type_combo.setFont(algo.getFont());
        frame.add(item_type_combo);
        
        process_page = new JTextField();
    	process_page.setFont(algo.getFont());
    	process_page.setText("");
    	process_page.setBounds(240, 216, 30, 30);
    	process_page.setToolTipText("Leave blank to process all available pages");
    	frame.add(process_page);
    	
    	selected = new boolean[6];
    	add_command_button = new JButton[6];
    	for (int i = 0; i < 6; i++){
    		selected[i] = false;
    		add_command_button[i] = new JButton("Add");
    		add_command_button[i].setFont(algo.getFont());
    		add_command_button[i].setBounds(500, 20 + i * 40, 100, 30);
    		add_command_button[i].setName("b" + i);
    		add_command_button[i].addActionListener(new add_command_listener());
    		frame.add(add_command_button[i]);
    	}
    
    	file = new ArrayList<String>();
    	
    	save = new JButton("Save");
    	save.setFont(algo.getFont());
    	save.setBounds(650, 707, 100, 30);
    	save.setName("b6");
    	save.addActionListener(new add_command_listener());
    	frame.add(save);
    	
    	chooser = new JFileChooser();
    	chooser.setCurrentDirectory(new File("C:\\Users\\Hedin\\workspace\\Clicker\\Algos"));
    	
    	start = new JButton("Start");
    	start.setName("b7");
    	start.setBounds(35, 670, 200, 60);
    	start.setFont(start.getFont().deriveFont(30.0f));
    	start.addActionListener(new add_command_listener());
    	frame.add(start);
    	
    	delete = new JButton("Delete last entry");
    	delete.setFont(algo.getFont());
    	delete.setBounds(200, 270, 200, 30);
    	delete.setName("b8");
    	delete.addActionListener(new add_command_listener());
    	frame.add(delete);
    	
    	load = new JButton("Load");
    	load.setFont(algo.getFont());
    	load.setBounds(770, 707, 100, 30);
    	load.setName("b9");
    	load.addActionListener(new add_command_listener());
    	frame.add(load);
    	
    	resolve();
	}
	
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
