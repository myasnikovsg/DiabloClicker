package clicker;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;



public class Utils {
	static final int STRING_WIDTH = 120;
	static final int STRING_HEIGHT = 23;
	static final int CREATED_CLASS_AMOUNT = 2; // количество созданных персонажей
	static final int ITEM_TYPE_1_AMOUNT = 5;
	//static final Point CHECK_POINT = new Point(1071, 818);
	static final Point RIGHT_ARROW_PIXEL = new Point(700, 580);
	static final int ITEM_PIC_OFFSET = 32;
	static final int PREFFERED_STAT_OFFSET = 27;
	//static final Color RIGHT_ARROW_COLOR = new Color(-11791616);
	static final int ITEM_DESCRIPTION_WIDTH = 280;
	static final int PREFERED_STAT_AMOUNT = 10;
	static final int GET_ITEM_X = 366;
	static int loss;
	static final int[] minLevel = {50, 55, 60};
	static final int[] maxLevel = {54, 59, 60};
	static String SCREEN_PATH;
	
	public static final int[][][] can_equip = {
		{{1, 0, 0, 1, 0, 1, 1, 1, 1, 0}, {1, 0, 0, 0, 1, 1, 1, 0, 1}, {0, 0, 0, 1}, {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0}, {1, 1, 1}}, //Barbarian
		{{0, 0, 1, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0}}, //Demon Hunter
		{{0, 0, 0, 0, 1, 0, 0, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0}}, //Monk
		{{0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 0}}, //Witch Doctor
		{{0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 0, 0}} // Wizard
		};
	public static final int[][] stat = {
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 136, 43, 44}, // Axe
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 27, 31, -1, 33, 34, 35, 36, 37, -1, 38, 39, 40, -1, 136, 43, 44}, // Ceremonial Knife 
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 26, 29, -1, 33, 34, 35, 36, 37, -1, 136, 43, 44}, // Hand Crossbow
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 38, 39, 40, 41, 42, -1, 136, 43, 44}, // Dagger
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, 24, -1, 32, -1, 33, 34, 35, 36, 37, -1, 136, 43, 44}, // Fist Weapon
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 136, 43, 44}, // Mace
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 30, -1, 33, 34, 35, 36, 37, -1, 136, 43, 44}, // Mighty Weapon
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 38, 39, 40, 41, 42, -1, 136, 43, 44}, // Spear
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 41, 42, -1, 136, 43, 44}, // Sword
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 41, 42, -1, 136, 43, 44}, // Wand
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 45, 46, 47, 48, -1, 136, 43, 44}, // Two-Handed Axe
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 38, 39, 40, 52, 53, 54, 55, -1, 136, 43, 44}, // Bow
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, 24, -1, 32, -1, 33, 34, 35, 36, 37, -1, 49, 51, -1, 136, 43, 44}, // Daibo
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 38, 39, 40, 52, 53, 54, 55, -1, 136, 43, 44}, // Crossbow
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 45, 46, 47, 48, -1, 136, 43, 44}, // Two-Handed Mace
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 45, 46, 47, 48, -1, 136, 43, 44}, // Two-Handed Mighty Weapon
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 45, 46, 47, 48, 49, 50, 51, -1, 136, 43, 44}, // Polearm
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 49, 50, 51, 41, 42, -1, 136, 43, 44}, // Staff
		{-1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, -1, 12, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 21, 22, 23, -1, 33, 34, 35, 36, 37, -1, 45, 46, 47, 48, -1, 136, 43, 44}, // Two-Handed Sword
		{-1, -1, 1, 10, 11, -1, 63, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 70, 71, 72, -1, 27, 31, -1, 33, 34, 35, 36, 37, -1, 52, 53, 54, 55, -1, 84, 136, 43, 85}, // Mojo
		{-1, -1, 1, 10, 11, -1, 63, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 70, 71, 72, -1, 25, 28, -1, 33, 34, 35, 36, 37, -1, 79, 80, 81, 82, 83, -1, 84, 136, 43, 85}, // Source
		{-1, -1, 1, 10, 11, -1, 63, -1, 13, 14, 15, 16, 17, 18, 19, 20, -1, 70, 71, 72, -1, 26, 29, -1, 33, 34, 35, 36, 37, -1, 73, 74, 75, 76, 77, 78, -1, 84, 136, 43, 85}, // Quiver
		{-1, -1, 1, 10, 11, -1, -1, 13, 14, 15, 16, 17, 18, 19, 20, 69, -1, 56, 57, 58, 1, 59, 10, 60, 11, 61, 62, -1, 63, 64, 65, 66, 67, 68, -1, 13, 14, 15, 16, 17, 18, 19, 20, 69, -1, 70, 71, 72, -1, 33, 34, 35, 36, 37, -1, 45, 46, 47, 48, 73, 74, 75, 76, 77, 78, 52, 53, 54, 55, 79, 80, 81, 82, 83, -1, 84, 136, 43, 85}, // Shield
		{-1, -1, 56, 57, 58, 0, 8, 60, 116, 11, 61, 62, 63, 64, 65, 67, 68, -1, 69, -1,  70, 71, 72, 22, 23, -1, 33, 34, 35, 36, 37, -1, 84, 136, 85}, // Amulet
		{-1, -1, 56, 57, 58, 60, 61, 62, 63, 64, 65, -1, 16, -1, 70, 71, 72, 21, -1, 30, -1, 33, 34, 35, 36, 37, -1, 86, 87, 88, 89, 90, 91, -1, 84, 136, 43, 85, 115, 44}, // Belt
		{-1, -1, 56, 57, 58, 60, 61, 62, 63, 64, 65, -1, 17, -1, 70, 72, -1, 33, 34, 35, 36, 37, -1, 84, 43, 85, 114, 115, 44}, // Boots
		{-1, -1, 56, 57, 58, 60, 116, 61, 62, 63, 64, 65, 67, 68, -1, 18, -1, 70, 72, -1, 33, 34, 35, 36, 37, -1, 84, 136, 43, 85, 115, 44}, // Bracers
		{-1, -1, 56, 57, 58, 60, 61, 62, 63, 64, 65, 66, 67, 68, -1, 70, 71, 72, -1, 26, 29, -1, 33, 34, 35, 36, 37, -1, 92, 93, 94, 95, 96, -1, 84, 136, 43, 85, 115, 44}, // Chest Armor
		{-1, -1, 56, 57, 58, 60, 61, 62, 63, 64, 65, 66, 67, 68, -1, 70, 71, 72, -1, 26, 29, -1, 33, 34, 35, 36, 37, -1, 92, 93, 94, 95, 96, -1, 84, 136, 43, 85, 115, 44}, // Cloak
		{-1, -1, 56, 57, 58, 0, 60, 116, 11, 61, 62, 63, 64, 65, -1, 20, -1, 70, 72, -1, 33, 34, 35, 36, 37, -1, 84, 43, 85, 115, 44}, // Gloves
		{-1, -1, 56, 57, 58, 60, 116, 61, 62, 63, 64, 65, -1, 15, 69, -1, 70, 71, 72, 24, -1, 25, 27, 28, 31, 32, -1, 33, 34, 35, 36, 37, -1, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, -1, 84, 136, 43, 85, 115, 44}, // Helm
		{-1, -1, 56, 57, 58, 60, 61, 62, 63, 64, 65, -1, 19, -1, 70, 72, -1, 33, 34, 35, 36, 37, -1, 84, 136, 43, 85, 115, 44}, // Pants
		{-1, -1, 56, 57, 58, 60, 61, 62, 63, 64, 65, -1, 16, -1, 70, 71, 72, 21, -1, 30, -1, 33, 34, 35, 36, 37, -1, 86, 87, 88, 89, 90, 91, -1, 84, 136, 43, 85, 115, 44}, // Mighty Belt
		{-1, -1, 56, 57, 58, 60, 8, 61, 62, 63, 64, 65, -1, 69, -1, 70, 71, 72, 22, 23, -1, 33, 34, 35, 36, 37, -1, 84, 136, 85}, // Ring
		{-1, -1, 56, 57, 58, 60, 61, 62, 63, 64, 65, -1, 14, -1, 70, 71, 72, -1, 33, 34, 35, 36, 37, -1, 84, 43, 85, 115, 44}, // Shoulders
		{-1, -1, 56, 57, 58, 60, 116, 61, 62, 63, 64, 65, -1, 15, 69, -1, 70, 71, 72, 24, -1, 32, -1, 33, 34, 35, 36, 37, -1, 97, 98, 99, 100, 101, 102, 103, -1, 84, 136, 43, 85, 115, 44}, // Spirit Stone
		{-1, -1, 56, 57, 58, 60, 116, 61, 62, 63, 64, 65, -1, 15, 69, -1, 70, 71, 72, -1, 27, 31, -1, 33, 34, 35, 36, 37, -1, 104, 105, 106, 107, -1, 84, 136, 43, 85, 115, 44}, // Voodoo Mask
		{-1, -1, 56, 57, 58, 60, 116, 61, 62, 63, 64, 65, -1, 15, 69, -1, 70, 71, 72, -1, 25, 28, -1, 33, 34, 35, 36, 37, -1, 108, 109, 110, 111, 112, 113, -1, 84, 136, 43, 85, 115, 44}, // Wizard Hat
		{-1, -1, 56, 57, 60, 116, 61, 62, 64, 65, -1, 72, 22, 23, -1, 33, 34, 35, 36, 37}, // Enchantress Focus
		{-1, -1, 56, 57, 60, 116, 61, 62, 64, 65, -1, 72, 22, 23, -1, 33, 34, 35, 36, 37}, // Scoundrel Token
		{-1, -1, 56, 57, 58, 59, 60, 116, 61, 62, 64, 65, -1, 72, 22, 23, -1, 33, 34, 35, 36, 37} // Templar Relic
	};
	
	public static final int[] item_amount = {10, 9, 4, 15, 3};
	
	static final Rectangle AUCTION_BUTTON = new Rectangle(55, 442, 220, 28);
	//static final Rectangle AUCTION_MAIN_AREA = new Rectangle(700, 300, 860, 490);
	static final Rectangle EQUIPMENT_BUTTON = new Rectangle(95, 155, 205, 25);
	static final Rectangle CLASS_SELECT_BUTTON = new Rectangle(271, 211, 14, 14);
	static final Rectangle CLASS_PICK_BUTTON = new Rectangle(130, 240, STRING_WIDTH, STRING_HEIGHT);
	static final Rectangle ITEM_TYPE_1_SELECT_BUTTON = new Rectangle(271, 238, 14, 14);
	static final Rectangle ITEM_TYPE_1_PICK_BUTTON = new Rectangle(130, 265, STRING_WIDTH, STRING_HEIGHT); 
	static final Rectangle ITEM_TYPE_2_SELECT_BUTTON = new Rectangle(271, 265, 14, 14);
	static final Rectangle ITEM_TYPE_2_PICK_BUTTON = new Rectangle(130, 316, STRING_WIDTH, STRING_HEIGHT); 
	static final Rectangle ARMOR_DOWN_BUTTON = new Rectangle(272, 612, 10, 10);
	static final Rectangle LEVEL_FROM_BOX = new Rectangle(109, 312, 20, 15);
	static final Rectangle LEVEL_TO_BOX = new Rectangle(161, 312, 20, 15);
	static final Rectangle RARITY_SELECT_BUTTON = new Rectangle(271, 312, 20, 20);
	static final Rectangle PICK_RARE_BUTTON = new Rectangle(211, 455, 70, STRING_HEIGHT);
	static final Rectangle PICK_LEGENDARY_BUTTON = new Rectangle(211, 478, 70, STRING_HEIGHT);
	static final Rectangle SEARCH_BUTTON = new Rectangle(128, 588, 140, 17);
	static final Rectangle ITEM_PIC = new Rectangle(333, 220, 18, 18);
	static final Rectangle RIGHT_ARROW_BUTTON = new Rectangle(693, 577, 10, 10);
	static final Rectangle TITLE_PAGE = new Rectangle(650, 214, 290, 350);
	static final Rectangle PREFERED_STAT_SELECT_BUTTON = new Rectangle(227, 416, 14, 14);
	static final Rectangle PREFERED_STAT_PICK_BUTTON = new Rectangle(130, 452, 120, STRING_HEIGHT);
	static final Rectangle PREFERED_STAT_VALUE_BOX = new Rectangle(260, 417, 20, 15); 
	static final Rectangle BUYOUT_BOX = new Rectangle(108, 369, 100, 14);
	
	static BufferedImage getImage(int n) throws IOException{
		return ImageIO.read(new File(SCREEN_PATH + get3Digit(n) + ".jpg"));
	}
	
	static void writeImage(BufferedImage img, String name) throws IOException{
		ImageIO.write(img, "png", new File(name + ".png"));
	}
	
	static BufferedImage getTitlePage(int n) throws IOException{
		BufferedImage img = getImage(n);
		return img.getSubimage(TITLE_PAGE.x, TITLE_PAGE.y, TITLE_PAGE.width, TITLE_PAGE.height);
	}
	
	static boolean[][] convertItemImage(BufferedImage img, String name) throws IOException{
		if (img.getHeight() == 1)
			return new boolean[0][0];
		Pattern res = new Pattern(img.getHeight(), img.getWidth(), "temp");
		for (int x = 0; x < img.getWidth(); x++)
			for (int y = 0; y < img.getHeight(); y++){
				Color c = new Color(img.getRGB(x, y));
				int blue = c.getBlue();
				if (blue > 148)
					c = Color.BLACK;
				else
					c = Color.WHITE;
				res.set(y, x, c.equals(Color.BLACK));
				img.setRGB(x, y, c.getRGB());
			}
		writeImage(img, name);
		return res.pattern; // fix it - remove wrapper
	}
	
	static boolean[][] convertTitleImage(BufferedImage img, String name) throws IOException{
		if (img.getHeight() == 1)
			return new boolean[0][0];
		Pattern res = new Pattern(img.getHeight(), img.getWidth(), "temp");
		for (int x = 0; x < img.getWidth(); x++)
			for (int y = 0; y < img.getHeight(); y++){
				Color c = new Color(img.getRGB(x, y));
				int red = c.getRed();
				int blue = c.getBlue();
				if (blue > 70)
					c = Color.BLACK;
				else
					c = Color.WHITE;
				res.set(y, x, c.equals(Color.BLACK));
				img.setRGB(x, y, c.getRGB());
			}
		writeImage(img, name);
		return res.pattern; // fix it - remove wrapper
	}
	
	static Pattern getResorce(BufferedImage img, String name){
		Pattern res = new Pattern(img.getHeight(), img.getWidth(), name);
		for (int x = 0; x < img.getWidth(); x++)
			for (int y = 0; y < img.getHeight(); y++){
				Color c = new Color(img.getRGB(x, y));
				res.set(y, x, c.equals(Color.BLACK));
			}
		return res;
	}
	
	static int getOffsettedIndex(int selected_type1, int selected_type2){
		int offset = 0;
		for (int i = 0; i < selected_type1; i++)
			offset += item_amount[i];
		return offset + selected_type2;
	}
	
	static BufferedImage getItemImage(int n) throws IOException{
		BufferedImage img = getImage(n);
		int x = Utils.GET_ITEM_X;
		int w = Utils.ITEM_DESCRIPTION_WIDTH;
		int y_top = 768;
		int y_bot = 0;
		int m = x + w / 2;
		int temp = 0;
		for (int j = 0; j < 768; j++){
			if (img.getRGB(m, j) == -16777216)
				temp++;
			else
				temp = 0;
			if (temp == 3) {
				y_top = j;
				break;
			}
		}
		temp = 0;
		for (int j = 767; j > 0; j--){
			if (img.getRGB(m, j) == -16777216)
				temp++;
			else
				temp = 0;
			if (temp == 3){
				y_bot = j;
				break;
			}
		}
		if (y_top == 768 && y_bot == 0) {
			loss++;
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		} else
			return img.getSubimage(x, y_top, w, y_bot - y_top);
	}
	
	static boolean nextPageExists(int n) throws IOException{
		if (n < 0)
			return false;
		BufferedImage img = getImage(n);
		if (img.getRGB(Utils.RIGHT_ARROW_PIXEL.x, Utils.RIGHT_ARROW_PIXEL.y) == -9233408)
			System.out.println("Next page exist");
		else
			System.out.println("Next page does not exist");
		return img.getRGB(Utils.RIGHT_ARROW_PIXEL.x, Utils.RIGHT_ARROW_PIXEL.y) == -9233408;
	}
	
	static final int TRIANGLE_X = 9;
	static final int SOCKET_TRIANGLE_X = 27;
	static final int EMPTY_X = 25;
	static final int EXTRA_X = 50;
	static final int EXTRA_SYMBOL_HEIGHT = 8;
	static final int EXTRA_LINE_OFFSET = 14;
	static final int PROPERTY_HEIGHT = 14;
	static final int TITLE_MAIN_ATTRIBUTE_X = 20; // 670
	static final int TITLE_MAIN_ATTRIBUTE_LENGTH = 35; // 705
	static final int TITLE_BID_PRICE_X = 60; // 710
	static final int TITLE_BID_PRICE_LENGTH = 60; // 770
	static final int TITLE_BUYOUT_PRICE_X = 135; // 785
	static final int TITLE_BUYOUT_PRICE_LENGTH = 80; // 865
	static final int TITLE_TIME_X = 225; // 875
	static final int TITLE_TIME_LENGTH = 60; // 935
	static final int TITLE_SKIP_Y = 32;
	static final int TITLE_Y = 11;
	static Pattern TRIANGLE; 
	static Pattern EMPTY;
//	                                                                                                                                                                                                                                                                                            !          !                                !        !          !         !             !                          !           !           !            !         !          !        !         !         !         !          !          !         !        !         !       !            !               !         !       !                       !            !        !        !        !          !         !          !          !                   !                                                                   !                                                                                                                                                                        																								                                                                                                                                                                                   
	static final String[] RESOURCE_NAME = {
		"t,", "t.", "t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7", "t8", "t9", "h", "m", "d", "+", "%", "-", ".", "0", "1",
		"2", "3", "4", "5", "6", "7", "8", "9", "e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9",
		"Minimum", "Dexterity", "Intelligence", "Strength", "Vitality", "Maximum", "DDamage",
		"Arcane", "Mana", "Discipline", "Fury", "Extra", "Life", "Movement", "Life", "Humans", "Undead", "Physical", "Elemental", "Bola",
		"Spike", "Crippling", "Deadly", "Exploding", "Gold", "Corpse", "Acid", "Entagliny", "Hungering", "DDuration", "Shock", "Spectral",
		"Bash", "Cleave", "Frenzy", "Evasive", "Grenades", "Strafe", "Fists", "Sweeping", "Way", "Locust", "Electrolute", "Multishot",
		"Rapid", "Revenge", "damage", "Desintagrate", "Hydra", "Meteor", "Rend", "Weapon", "Chakram", "Impale", "Cyclone", "Firebats",
		"Wall", "Summon", "elites", "melee", "ranged", "Attack", "Health", "Demons", "Beasts", "Cold", "Fire", "Holy", "Lightning",
		"Poison", "Armor", "Resistance", "Increases", "Critical", "Attack", "Hatred", "Spirit Reg", "Haunt", "Plague", "Cluster",
		"Magic", "Poison", "Spirit Bar", "Energy", "Overpower", "Whirlwind", "Seismic", "Tempest", "Wave", "of", "CChance", "chance",
		"Blind", "Chill", "Fear", "Freeze", "Immobilize", "Knockback", "Slow", "Stun", "Reduces", "cooldown", "resource", "duration",
		"Firebomb", "Hammer", "Lashing", "Zombie", "Adds", "Hits grant", "Hit", "Each", "Gain", "Ignores", "Level", "Monster", "Regenerates", "Melee", "NA"};

	static ArrayList<Pattern> res;
	
	static void LoadPatterns() throws IOException{
		TRIANGLE = getResorce(ImageIO.read(new File("res\\triangle.png")), "triangle");
		EMPTY = getResorce(ImageIO.read(new File("res\\empty.png")), "E");
		res = new ArrayList<Pattern>();
		for (int i = 0; i < RESOURCE_NAME.length; i++)
			try {
				res.add(getResorce(ImageIO.read(new File("res\\" + RESOURCE_NAME[i] + ".png")), RESOURCE_NAME[i]));
			}
			catch (Exception e) {
				System.out.println(RESOURCE_NAME[i]);
			}
		Node.constructTree();
	}
	
	static String get3Digit(int n){
		String res = "";
		if (n < 10)
			res += "0";
		if (n < 100)
			res += "0";
		res += n;
		return res;
	}
	
	static int getResId(String str){
		for (int i = 0; i < res.size(); i++)
			if (res.get(i).name.equals(str))
				return i;
		return -1;
	}
	
	static Pattern getRes(int id){
		if (id != -1)
			return res.get(id);
		else return new Pattern(0, 0, null);
	}
	
	static Double parseTime(String s){
		Double res = 0.0;
		while (s.length() > 0){
			int t = s.indexOf('m');
			if (t > 0 && t < 3) {
				res += Integer.parseInt(s.substring(0, t));
				s = s.substring(t + 1);
				continue;
			}
			t = s.indexOf('h');
			if (t > 0 && t < 3) {
				res += Integer.parseInt(s.substring(0, t)) * 60;
				s = s.substring(t + 1);
				continue;
			}
			t = s.indexOf('d');
			if (t > 0 && t < 3) {
				res += Integer.parseInt(s.substring(0, t)) * 24 * 60;
				s = s.substring(t + 1);
				continue;
			}
		}
		return res;
	}
}
