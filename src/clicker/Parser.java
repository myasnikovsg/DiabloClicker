package clicker;

import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;



public class Parser {
	
	private static int y, max_len;
	private static int magic_n;
	private static int triangles_found = 0;
	private static int emptys_found = 0;
	private static boolean[][] img;
	private static boolean second_line;
	
	private static boolean findSymbol(boolean bust_allowed, Pattern pattern, int x){
		if (!bust_allowed)
			return pattern.match(img, new Point(x, y), 0) == 0;
		while (y < img.length - pattern.getHeight()){
			if (pattern.match(img, new Point(x, y), 0) == 0)
				return true;
			y++;
		}
		return false;
	}
	
	private static Point skip(Point p, int len, boolean line_break_allowed){
		p.x += len;
		if (p.x >= max_len && line_break_allowed) {
			p.x -= max_len;
			p.y += Utils.PROPERTY_HEIGHT; 
		}
		return p;
	}
	
	private static boolean somethingInAColumn(Point p){
		for (int i = 0; i < Utils.PROPERTY_HEIGHT - 5; i++)
			if (img[p.y + i][p.x])
				return true;
		return false;
	}
	
	private static Point skipWhitespace(Point p, boolean line_break_allowed){
		while (p.x < max_len && !somethingInAColumn(p)) 
			p = skip(p, 1, line_break_allowed);
		return p;
	}
	
	private static Point skipCharacter(Point p){
		while (somethingInAColumn(p))
			p = skip(p, 1, false);
		return p;
	}
	
	private static Property tryOut(ArrayList<Integer> value, Node node, Point p){
		for (int i = 0; i < node.next.size(); i++){
			Property res = parseProperty(value, node.next.get(i), p); 
			if (res != null)
				return res; 
		}
		return null;
	}
	
	private static Point match(Pattern pattern, Point p, double max_loss_ratio, int radius, int max_loss_value){
		if (pattern.name == null)
			return p;
		int i = 0;
		for (int k = 0; k < radius * 2 + 1; k++){
			int loss = pattern.match(img, new Point(p.x + i, p.y), max_loss_ratio);
			if (pattern.name.equals("1")) {
				if (loss < 2)
					return new Point(p.x + i + pattern.getWidth(), p.y);
			} else
				if (pattern.name.equals("t1")){
					if (loss < 4)
						return new Point(p.x + i + pattern.getWidth(), p.y);
				} else
			if (loss <= max_loss_ratio * pattern.getWidth() * pattern.getHeight())
				return new Point(p.x + i + pattern.getWidth(), p.y);
			if (i == 0)
				i = 1;
			else
				if (i > 0)
					i = -i;
				else
					if (i < 0)
						i = -i + 1;
		}
		return p;
	}
	
	private static Property parseProperty(ArrayList<Integer> value, Node node, Point p){
		switch(node.type){
			case TERMINAL :
				return new Property(node.value, value); 
			case SKIP :
				p = skip(p, node.value, true);
				return tryOut(value, node, p);
			case SYMBOL : 
				if (node.value == -1)
					return null;
				p = skipWhitespace(p, true);
				int mem_x = p.x;
				if (Utils.getRes(node.value).name.equals("."))
					p = match(Utils.getRes(node.value), p, 0.0, 0, 10); // . is a very small pattern
				else
					p = match(Utils.getRes(node.value), p, 0.08, 1, 10); // calibrate
				if (mem_x != p.x)
					return tryOut(value, node, p);
				else
					return null;
			case ROOT :
				return tryOut(value, node, p);
			case NUMBER :
				int n = 0;
				boolean found = false;
				boolean f = true;
				while (f){
					p = skipWhitespace(p, true);
					f = false;
					for (int i = 0; i < 10; i++){
						mem_x = p.x;
						p = match(Utils.getRes(Utils.getResId(Integer.toString(i))), p, 0.09, 1, 10); // calibrate
						if (p.x != mem_x) {
							n = n * 10 + i;
							f = true;
							found = true;
							break;
						}
					}
				}
				if (found){
					value.add(n);
					return tryOut(value, node, p);
				} else
					return null;
			case NUMBER_AGRESSIVE :
				n = 0;
				found = false;
				while (!found && (!second_line || p.x < max_len)){
					f = true;
					while (f){
						p = skipWhitespace(p, true);
						f = false;
						for (int i = 0; i < 10; i++){
							mem_x = p.x;
							p = match(Utils.getRes(Utils.getResId(Integer.toString(i))), p, 0.09, 1, 10); // calibrate
							if (p.x != mem_x) {
								n = n * 10 + i;
								f = true;
								found = true;
								break;
							}
						}
					}
					if (found)
						break;
					skipCharacter(p);
				}
				if (found){
					value.add(n);
					return tryOut(value, node, p);
				} else
					return null;
			case SHIT :
				return null;
		}
		return null;
	}
	
	// algo -
	// 1) find first triangle. y will be set to topline of first property
	// 2) parse this property (first line)
	// 3) on second line (if any) will be no alteration. So just search for numbers(if needed).
	// 4) after steps 2)-3) y will be set to topline of previous property(not changed) _OR_ to topline of second line of previous property. So check for triangle on y or on 
	//    y + PROPERTY_HEIGHT
	// 5) if there is no triangles, search for sockets. First try to find some on SOCKET_X. Then search for 'E' on E_X
	
	static private int countSockets(int type, int triangles_found, int emptys_found){
		if (type < 19) // weapons
			return triangles_found + emptys_found > 0 ? 1 : 0;
		else
			return triangles_found + emptys_found;
	}
	
	static Point parseExtraNumber(Point p) { // res.x = new x of point, p.y = number
		Point res = new Point(0, 0);
		boolean found = true;
		int mem_x;
		while (found) {
			p = skipWhitespace(p, false);
			found = false;
			mem_x = p.x;
			for (int i = 0; i < 10; i++){
				 p = match(Utils.getRes(Utils.getResId("e" + Integer.toString(i))), p, 0.08, 1, 10);
				 if (mem_x != p.x) {
					 res.y = res.y * 10 + i;
					 found = true;
					 break;
				 }
			 }
		}
		res.x = p.x;
		return res;
	}
	
	static ArrayList<Integer> parseExtra(int ex_y, int type) {
		int res[] = new int[4];
		boolean found;
		Point p = new Point();
		for (int y = ex_y - 1; y > 0; y--) {
			found = false;
			for (int x = Utils.EXTRA_X; x < Utils.EXTRA_X + 100; x++)
				if (img[y][x]) {
					found = true;
					break;
				}
			if (found) {
				p = new Point(Utils.EXTRA_X, y - Utils.EXTRA_SYMBOL_HEIGHT - Utils.EXTRA_LINE_OFFSET + 1);
				break;
			}
		}
		if (type < 19) {
			 Point temp = parseExtraNumber(p);
			 p.x = temp.x;
			 res[0] = temp.y;
			 p.x += 3; // skipping '-'
			 temp = parseExtraNumber(p);
			 p.x = temp.x;
			 res[1] = temp.y;
			 p.y += Utils.EXTRA_LINE_OFFSET; // jump to next line of extra
			 p.x = Utils.EXTRA_X;
			 temp = parseExtraNumber(p);
			 p.x = temp.x;
			 res[2] = temp.y;
			 p.x += 1; // skipping '.'
			 temp = parseExtraNumber(p);
			 p.x = temp.x;
			 res[3] = temp.y;
		}
		if (type == 22) {
			p = skipWhitespace(p, false); // near '+' now
			p.x += 5; // skipping '+'
			 Point temp = parseExtraNumber(p);
			 p.x = temp.x;
			 res[2] = temp.y;
			 p.x += 1; // skipping '.'
			 temp = parseExtraNumber(p);
			 p.x = temp.x;
			 res[3] = temp.y;
			 p.y += Utils.EXTRA_LINE_OFFSET; // jump to next line of extra
			 p.x = Utils.EXTRA_X;
			 temp = parseExtraNumber(p);
			 p.x = temp.x;
			 res[0] = temp.y;
			 p.x += 3; // skipping '-'
			 temp = parseExtraNumber(p);
			 p.x = temp.x;
			 res[1] = temp.y;
		}
		ArrayList<Integer> list = new ArrayList<Integer>(4);
		for (int i = 0; i < res.length; i++)
			list.add(res[i]);
		return list;
	}
	
	static public ArrayList<Property> parseItem(boolean[][] image, final int type){
		if (image.length == 0)
			return null;
		ArrayList<Property> props = new ArrayList<Property>();
		img = image;
		y = 0;
		findSymbol(true, Utils.TRIANGLE, Utils.TRIANGLE_X);
		max_len = img[0].length;
		int x;
		ArrayList<Integer> extra = new ArrayList<Integer>();
		if (type < 19 || type == 22) {
			extra = parseExtra(y, type);
		}
		while (y < img.length){
			magic_n++;
			x = Utils.TRIANGLE_X + Utils.TRIANGLE.getWidth();
			second_line = false;
			Property prop = null;
			if (type < 19)
				prop = parseProperty(new ArrayList<Integer>(), Node.weapon_root, new Point(x, y));
			else
				prop = parseProperty(new ArrayList<Integer>(), Node.armor_root, new Point(x, y));
			if (prop != null)
				props.add(prop);
			else
				props.add(new Property(-2, null));
			y += Utils.PROPERTY_HEIGHT; // to topline of next or to second line of current
			if (!findSymbol(false, Utils.TRIANGLE, Utils.TRIANGLE_X)) // if second line current
				y += Utils.PROPERTY_HEIGHT; // now topline of next
			if (!findSymbol(false, Utils.TRIANGLE, Utils.TRIANGLE_X)) // so there is no triangles left
				break;
		}
		
		//props.add(new Property(1, new ArrayList<Integer>(){{add(magic_n);}}));
		y -= Utils.PROPERTY_HEIGHT;
		int y_mem = y;
		triangles_found = 0;
		while (y < img.length && findSymbol(true, Utils.TRIANGLE, Utils.SOCKET_TRIANGLE_X)){
			triangles_found++;
			y += Utils.TRIANGLE.getHeight();
		}
		y = y_mem;
		emptys_found = 0;
		while (y < img.length && findSymbol(true, Utils.EMPTY, Utils.EMPTY_X)){
			emptys_found++;
			y += Utils.EMPTY.getHeight();
		}
		props.add(new Property(136, new ArrayList<Integer>() {{add(countSockets(type, triangles_found, emptys_found));}}));
		if (type < 19 || type == 22)
			props.add(new Property(1000, extra));
		return props;
	}
	
	public static ArrayList<Double[]> parseTitle(boolean[][] image){
		ArrayList<Double[]> res = new ArrayList<Double[]>();
		img = image;
		max_len = img[0].length;
		for (int k = 0; k < 11; k++) {
			Point p = new Point(Utils.TITLE_MAIN_ATTRIBUTE_X, Utils.TITLE_Y + Utils.TITLE_SKIP_Y * k);
			boolean found = false;
			boolean f = false;
			String s = "";
			Double d[] = new Double[4];
			//Main Attribute
			p = skipWhitespace(p, false);
			while (p.x - Utils.TITLE_MAIN_ATTRIBUTE_X < Utils.TITLE_MAIN_ATTRIBUTE_LENGTH) {
				for (int i = 0; i < 10; i++){
					int mem_x = p.x;
					f = false;
					p = match(Utils.getRes(Utils.getResId("t" + i)), p, 0.1, 1, 8); // calibrate 
					if (mem_x != p.x) {
						s += i;
						f = true;
						break;
					}
				}
				if (!f){
					int mem_x = p.x;
					p = match(Utils.getRes(Utils.getResId("t.")), p, 0.0, 0, 0); 
					if (mem_x != p.x) {
						s += ".";
						f = true;
					}
				}
				if (!f)
					break;
				p = skipWhitespace(p, false);
			}
			if (s != ""){
				d[0] = Double.parseDouble(s);
				found = true;
			} else
				d[0] = 0.0;
			// Bid
			p.x = Utils.TITLE_BID_PRICE_X;
			s = "";
			p = skipWhitespace(p, false);
			while (p.x - Utils.TITLE_BID_PRICE_X < Utils.TITLE_BID_PRICE_LENGTH) {
				for (int i = 0; i < 10; i++){
					int mem_x = p.x;
					f = false;
					p = match(Utils.getRes(Utils.getResId("t" + i)), p, 0.1, 1, 8); // calibrate
					if (mem_x != p.x) {
						s += i;
						f = true;
						break;
					}
				}
				if (!f){
					int mem_x = p.x;
					p = match(Utils.getRes(Utils.getResId("t,")), p, 0.0, 0, 0);
					if (mem_x != p.x) {
						//s += ".";
						f = true;
					}
				}
				if (!f)
					break;
				p = skipWhitespace(p, false);
			}
			if (s != ""){
				d[1] = Double.parseDouble(s);
				found = true;
			} else
				d[1] = 0.0;
			//Buyout
			p.x = Utils.TITLE_BUYOUT_PRICE_X;
			s = "";
			p = skipWhitespace(p, false);
			int mem_x = p.x;
			p = match(Utils.getRes(Utils.getResId("NA")), p, 0.3, 1, 8); // calibrate
			p = skipWhitespace(p, false);
			if (mem_x == p.x)
				while (p.x - Utils.TITLE_BUYOUT_PRICE_X < Utils.TITLE_BUYOUT_PRICE_LENGTH) {
					for (int i = 0; i < 10; i++){
						mem_x = p.x;
						f = false;
						p = match(Utils.getRes(Utils.getResId("t" + i)), p, 0.1, 1, 8); // calibrate
						if (mem_x != p.x) {
							s += i;
							f = true;
							break;
						}
					}
					if (!f){
						mem_x = p.x;
						p = match(Utils.getRes(Utils.getResId("t,")), p, 0.0, 0, 0);
						if (mem_x != p.x) {
							//s += ".";
							f = true;
						}
					}
					if (!f)
						break;
					p = skipWhitespace(p, false);
				}
			if (s != ""){
				d[2] = Double.parseDouble(s);
				found = true;
			} else
				d[2] = 0.0;
			//Time
			p.x = Utils.TITLE_TIME_X;
			s = "";
			p = skipWhitespace(p, false);
			while (p.x - Utils.TITLE_TIME_X < Utils.TITLE_TIME_LENGTH) {
				f = false;
				mem_x = p.x;
				p = match(Utils.getRes(Utils.getResId("h")), p, 0.1, 1, 8); // calibrate
				if (mem_x != p.x) {
					s += "h";
					f = true;
				}
				if (!f){
					mem_x = p.x;
					p = match(Utils.getRes(Utils.getResId("d")), p, 0.1, 1, 8); // calibrate
					if (mem_x != p.x) {
						s += "d";
						f = true;
					}
				}
				if (!f){
					mem_x = p.x;
					p = match(Utils.getRes(Utils.getResId("m")), p, 0.1, 1, 8); // calibrate
					if (mem_x != p.x) {
						s += "m";
						f = true;
					}
				}
				if (!f)
					for (int i = 0; i < 10; i++){
						mem_x = p.x;
						f = false;
						p = match(Utils.getRes(Utils.getResId("t" + i)), p, 0.1, 1, 8); // calibrate
						if (mem_x != p.x) {
							s += i;
							f = true;
							break;
						}
					}
				if (!f)
					break;
				p = skipWhitespace(p, false);
			}
			if (s != ""){
				d[3] = Utils.parseTime(s);
				found = true;
			} else
				d[3] = 0.0;
			if (found)
				res.add(d);
			else
				break;
		}
		return res;
	}
}
