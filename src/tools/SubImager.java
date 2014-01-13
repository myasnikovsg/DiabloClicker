package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class SubImager {
	static final int SYMBOL_HEIGHT = 10;
	static final int TITLE_SYMBOL_HEIGHT = 10;
	static final int EXTRA_SYMBOL_HEIGHT = 8;
	
	public static void main(String[] args) throws IOException {
		String item_name = "item(49)1.png"; 
		BufferedImage img = ImageIO.read(new File(item_name));
		String symbol_name = "e9";
		int y = 97;
		int x = 69;
		int length = 74 - x + 1;
	
		ImageIO.write(img.getSubimage(x, y, length, EXTRA_SYMBOL_HEIGHT), "png", new File("res\\" + symbol_name + ".png"));
	}

}
