package clicker;
import java.awt.Point;
import java.io.ObjectInputStream.GetField;


public class Pattern {
	boolean[][] pattern;
	String name;
	
	public Pattern(int w, int h, String name){
		pattern = new boolean[w][h];
		this.name = name;
	}
	
	public void set(int i, int j, boolean val){
		pattern[i][j] = val;
	}
	
	public boolean get(int i, int j){
		return pattern[i][j];
	}
	
	public int getWidth(){
		return pattern[0].length;
	}
	
	public int getHeight(){
		return pattern.length;
	}
	
	public int match(boolean[][] img, Point p, double max_loss_ratio){
		int err = 0;
		for (int i = p.y; i < Math.min(p.y + getHeight(), img.length); i++) {
			if (getWidth() > 10 && 1.0 * ((i - p.y) * getWidth()) / (getWidth() * getHeight()) > 0.3 && max_loss_ratio < (1.0 * err / ((i - p.y) * getWidth())))
				return 10000;
			for (int j = p.x; j < Math.min(p.x + getWidth(), img[0].length); j++)
				if (img[i][j] != pattern[i - p.y][j - p.x])
					err++;
			}
		return err;
	}
}
