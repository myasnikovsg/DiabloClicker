package clicker;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.SeekableByteChannel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;

import model.GroupMessage;
import model.Item;
import model.ItemGroup;
import model.ItemMessage;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.jboss.netty.util.internal.ConcurrentHashMap;
 
public class Click {
	static Robot robot;
	static Point orig = new Point(300, 300);
	static Point cur = orig;
	static int MOTION_STEP = 3;
	static int scr_fst;
	static int scr_n;
	static int cur_scr;
	static int loss = 0;
	static int EDGE_FINDER_PRECISION = 5;
	static int selected_class;
	static int selected_rarity;
	static int selected_type1;
	static int selected_type2;
	static int screened;
	static ArrayList<Long> scr_time;
	static String res;
	static ArrayList<Integer> cur_click_pos;
	static LinkedList<Item> items;
	static LinkedList<Integer> scr_nums;
	static LinkedList<Integer> groupId;
	static int item_type_number;
	static int[] prev_pref_stat;
	static Hashtable<String, String> table;
	static InitialContext init_ctx;
	private static QueueSender sender;
	static QueueSession session;
	static volatile ArrayList<ConcurrentHashMap<Integer, ItemGroup>> mapItemGroup;
	static private String id;
	static byte region;
	static ArrayList<Integer> money_type;
	static String server_ip;
	static final int naming_port = 1099;
	static final int connection_port = 5445;
	static String parser_id; 
	
	static double myRandom(){
		double res = Math.random();
		if (res > 0.9)
			res = 0.9;
		if (res < 0.1)
			res = 0.1;
		return res;
	}
	
	static int getRandomDelay(int base_delay){
		return (int)(base_delay + Math.random() * 5);
	}
	
	static Point pickRandomPoint(Rectangle rect){
		return new Point((int)(rect.x + myRandom() * rect.width), (int)(rect.y + myRandom() * rect.height));
	}
	
	static void moveTo(Rectangle rect) throws IOException, NamingException, JMSException{
		Point dest = pickRandomPoint(rect);
		Point motion_vector = new Point(dest.x - cur.x, dest.y - cur.y);
		double len = motion_vector.distance(0, 0);
		double real_x = cur.x, real_y = cur.y;
		while (!rect.contains(cur)){
			real_x += (1.0f * motion_vector.x) / len * MOTION_STEP;
			real_y += (1.0f * motion_vector.y) / len * MOTION_STEP;
			cur.x = (int) real_x;
			cur.y = (int) real_y;
			robot.mouseMove(cur.x, cur.y);
			delay(5);
		}
	}
	
	static void mousePress(){
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	static void doPrtScr(){
		robot.keyPress(KeyEvent.VK_PRINTSCREEN);
		robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
	}
	
	static void sendItem(Item item, int groupId) throws NamingException, JMSException{
		ItemMessage itm_msg = new ItemMessage();
		itm_msg.setGroupId(groupId);
		itm_msg.setItem(item);
		ObjectMessage msg = session.createObjectMessage();
		msg.setObject(itm_msg);
		sender.send(msg);
	}
	
	static void delay(int base_delay) throws IOException, NamingException, JMSException{
		int delay = getRandomDelay(base_delay);
		if (scr_nums.size() > 4 && delay >= 100) {
			long start_time = (new Date()).getTime();
			while (true){
				Item item = items.poll();
				int scr = scr_nums.poll();
				ArrayList<Property> props = Parser.parseItem(Utils.convertItemImage(Utils.getItemImage(scr), "item"), item.getTypeId());
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				int fullinfo_len = 9;
				Property extra = new Property(0, null);
				int prop_number = 0;
				for (Property prop : props){
					int val = prop.toOneNumbered();
					if (prop.type != 1000) {
						prop_number++;
						fullinfo_len++;
						if (prop.value != null)
							fullinfo_len += prop.value.size();
					}
					else
						extra = prop;
					if (val != -1)
						map.put(prop.type, val);
				}
				int fullinfo[] = new int[fullinfo_len];
				fullinfo[0] = item.getTypeId();
				//fullinfo[1] = item.getBid();
				fullinfo[2] = item.getBuyOut();
				//fullinfo[3] = (int) item.getEndTime();
				fullinfo[4] = item.getMainProp();
				if (fullinfo[0] < 19 || fullinfo[0] == 22){
					fullinfo[5] = extra.value.get(0);
					fullinfo[6] = extra.value.get(1);
					fullinfo[7] = extra.value.get(2);
					fullinfo[8] = extra.value.get(3);
				}
				int count = 9;
				for (int i = 0; i < prop_number; i++) {
					fullinfo[count++] = props.get(i).type;
					if (props.get(i).value != null)
						for (int j = 0; j < props.get(i).value.size(); j++)
							fullinfo[count++] = props.get(i).value.get(j);
				}
				item.setProps(map);
				item.setFullInfo(fullinfo);
				sendItem(item, groupId.poll());
				if ((new Date()).getTime() - start_time > delay)
					break;
			}
		} else
			robot.delay(delay);
	}
	
	static void selectClass(int k) throws IOException, NamingException, JMSException{
		if (selected_class == k)
			return;
		moveTo(Utils.CLASS_SELECT_BUTTON);
		delay(100);
		mousePress();
		delay(100);
		Rectangle to = new Rectangle(Utils.CLASS_PICK_BUTTON);
		to.y += (Utils.CREATED_CLASS_AMOUNT + k + 1) * Utils.STRING_HEIGHT;
		moveTo(to);
		delay(100);
		mousePress();
		delay(100);
		selected_class = k;
	}
	
	private static boolean just_selected;
	private static QueueConnection queue_con;
	private static MessageConsumer cons;
	
	static void selectItem1Type(int k) throws IOException, NamingException, JMSException{
		if (selected_type1 == k) {
			just_selected = false;
			return;
		}
		moveTo(Utils.ITEM_TYPE_1_SELECT_BUTTON);
		delay(100);
		mousePress();
		delay(100);
		Rectangle to = new Rectangle(Utils.ITEM_TYPE_1_PICK_BUTTON);
		to.y += k * Utils.STRING_HEIGHT;
		moveTo(to);
		delay(100);
		mousePress();
		delay(100);
		just_selected = true;
		selected_type1 = k;
	}
	
	static void selectItem2Type(int k, int type, int cl) throws IOException, NamingException, JMSException{
		if (selected_type2 == k)
			return;
		moveTo(Utils.ITEM_TYPE_2_SELECT_BUTTON);
		delay(100);
		mousePress();
		delay(100);
		selected_type2 = k;
		if (cl == 4 && type == 3){ // wizard picks armor
			moveTo(Utils.ARMOR_DOWN_BUTTON);
			mousePress();
			delay(50);
			mousePress();
			delay(50);
			mousePress();
			delay(50);
		}
		Rectangle to = new Rectangle(Utils.ITEM_TYPE_2_PICK_BUTTON);
		to.y += k * Utils.STRING_HEIGHT;
		if (type == 3){
			if (!just_selected)
				to.y -= Utils.STRING_HEIGHT;
			if (cl == 3)
				to.y -= 5;
		}
		moveTo(to);
		delay(100);
		mousePress();
		delay(100);
	}
	
	static void printSymbol(char symbol) throws IOException, NamingException, JMSException{
		int key = KeyEvent.VK_0 + symbol;
		if (symbol == 'd') 
			key = KeyEvent.VK_DELETE;
		if (symbol == 'b')
			key = KeyEvent.VK_BACK_SPACE;
		robot.keyPress(key);
		delay(50);
		robot.keyRelease(key);
		delay(50);
	}
	
	static void printNumber(int number) throws IOException, NamingException, JMSException {
		String num = Integer.toString(number);
		for (int i = 0; i < num.length(); i++) {
			printSymbol((char)(num.charAt(i) - '0'));
		}
	}
	
	static void selectLevels(int from, int to) throws IOException, NamingException, JMSException{
		moveTo(Utils.LEVEL_FROM_BOX);
		delay(100);
		mousePress();
		delay(100);  
		printNumber(from);
		
		moveTo(Utils.LEVEL_TO_BOX);
		delay(100);
		mousePress();
		delay(100);
		printNumber(to);
	}
	
	static void selectRarity(int rarity) throws IOException, NamingException, JMSException{
		moveTo(Utils.RARITY_SELECT_BUTTON);
		delay(100);
		mousePress();
		delay(100);
		selected_rarity = rarity;
		if (rarity == 0) {
			Rectangle to = new Rectangle(Utils.PICK_RARE_BUTTON);
			moveTo(to);
			delay(100);
			mousePress();
			delay(100);
		} else {
			moveTo(Utils.PICK_LEGENDARY_BUTTON);
			delay(100);
			mousePress();
			delay(100);
		}
	}
	
	static void pressSearch() throws IOException, NamingException, JMSException{
		moveTo(Utils.SEARCH_BUTTON);
		delay(100);
		mousePress();
		delay(700);
	}
	
	static int processPage(int group_id, ItemGroup group) throws IOException, NamingException, JMSException{
		doPrtScr();
		delay(500);
		ArrayList<Double[]> title = Parser.parseTitle(Utils.convertTitleImage(Utils.getTitlePage(cur_scr), "title"));
		cur_scr++;
		Rectangle to;
		boolean broken = false;
		for (int i = 0; i < title.size(); i++){
			if (group.getMainProp() > title.get(i)[0]) {
				broken = true;
				break;
			}
			to = new Rectangle(Utils.ITEM_PIC);
			to.y += i * Utils.ITEM_PIC_OFFSET;
			moveTo(to);
			robot.mouseMove(to.x + (to.width / 2), to.y + (to.height / 2));
			delay(50);
			doPrtScr();
			delay(200);
			//scr_time.add((new Date()).getTime());
			
			int type = Utils.getOffsettedIndex(selected_type1, selected_type2);
			
			Item item = new Item();
			item.setBid(title.get(i)[1].intValue());
			item.setBuyOut(title.get(i)[2].intValue());
			item.setEndTime((new Date()).getTime() + title.get(i)[3].intValue() * 60 * 1000);
			item.setLvlGroup(group.getLvlGroup());
			item.setMainProp((int)(title.get(i)[0] * 10));
			item.setTypeId((byte)type);
			
			items.add(item);
			scr_nums.add(cur_scr);
			groupId.add(group_id);
			cur_scr++;
		}
		return broken ? -1 : cur_scr - 1;
	}
	
	static void nextPage() throws IOException, NamingException, JMSException{
		moveTo(Utils.RIGHT_ARROW_BUTTON);
		delay(100);
		mousePress();
		delay(100);
		Rectangle to = new Rectangle(Utils.RIGHT_ARROW_BUTTON);
		to.y += 50;
		moveTo(to); // move cursor from right arrow
		delay(100);
	}
	
	static void selectPrefferedStats(int pref_stat_n, int pref_stat, int pref_stat_val) throws IOException, NamingException, JMSException {
		int offset = 0;
		for (int i = 0; i < cur_click_pos.size(); i++)
			if (pref_stat == cur_click_pos.get(i)) {
				offset = i;
				break;
			}
		System.out.println(offset);
		int position = 0;
		int distance = 0;
		Rectangle to;
		while (offset != position) {
			to = new Rectangle(Utils.PREFERED_STAT_SELECT_BUTTON);
			to.y += Utils.PREFFERED_STAT_OFFSET * pref_stat_n;
			moveTo(to);
			delay(50);
			mousePress();
			distance = offset - position;
			to = new Rectangle(Utils.PREFERED_STAT_PICK_BUTTON);
			to.y += Utils.PREFFERED_STAT_OFFSET * pref_stat_n;
			if (distance < Utils.PREFERED_STAT_AMOUNT) { 
				if (position < cur_click_pos.size() - Utils.PREFERED_STAT_AMOUNT + 1)
					to.y += Utils.STRING_HEIGHT * distance;
				else
					to.y += Utils.STRING_HEIGHT * distance + (Utils.PREFERED_STAT_AMOUNT - (cur_click_pos.size() - position)) * Utils.STRING_HEIGHT;
				position = offset;
			}
			else
				for (int i = Utils.PREFERED_STAT_AMOUNT - 1; i > 0; i--)
					if (cur_click_pos.get(position + i) != -1) {
						to.y += Utils.STRING_HEIGHT * i;
						position += i;
						break;
					}
			moveTo(to);
			delay(50);
			mousePress();
			delay(50);
		}
		cur_click_pos.remove(offset);
		to = new Rectangle(Utils.PREFERED_STAT_VALUE_BOX);
		to.y += Utils.PREFFERED_STAT_OFFSET * pref_stat_n;
		moveTo(to);
		delay(50);
		mousePress();
		delay(50);
		printNumber(pref_stat_val);
	}
	
	public static void selectBuyout(int buyout) throws IOException, NamingException, JMSException{
		moveTo(Utils.BUYOUT_BOX);
		delay(50);
		mousePress();
		delay(50);
		printNumber(buyout);
	}
	
	static void mouseWheel(int amount) throws IOException, NamingException, JMSException{
		for (int i = 0; i < amount; i++) {
			robot.mouseWheel(-1);
			delay(10);
		}
	}
	
	static void clearPrefferedStats() throws IOException, NamingException, JMSException{
		Rectangle to;
		moveTo(Utils.LEVEL_FROM_BOX);
		delay(50);
		mousePress();
		delay(50);
		printSymbol('d');
		printSymbol('d');
		printSymbol('b');
		printSymbol('b');
		delay(50);
		moveTo(Utils.LEVEL_TO_BOX);
		delay(50);
		mousePress();
		delay(50);
		printSymbol('d');
		printSymbol('d');
		printSymbol('b');
		printSymbol('b');
		for (int i = 0; i < 3; i++){
			to = new Rectangle(Utils.PREFERED_STAT_SELECT_BUTTON);
			to.y += i * Utils.PREFFERED_STAT_OFFSET;
			moveTo(to);
			delay(50);
			mousePress();
			delay(50);
			to = new Rectangle(Utils.PREFERED_STAT_PICK_BUTTON);
			to.y += i * Utils.PREFFERED_STAT_OFFSET;
			moveTo(to);
			delay(50);
			mouseWheel(50);
			delay(50);
			mousePress();
			delay(50);
			to = new Rectangle(Utils.PREFERED_STAT_VALUE_BOX);
			to.y += i * Utils.PREFFERED_STAT_OFFSET;
			moveTo(to);
			delay(50);
			mousePress();
			delay(50);
			printSymbol('d');
			printSymbol('d');
			printSymbol('d');
			printSymbol('b');
			printSymbol('b');
			printSymbol('b');
			delay(50);
		}
		moveTo(Utils.BUYOUT_BOX);
		delay(50);
		mousePress();
		delay(50);
		printSymbol('d');
		printSymbol('d');
		printSymbol('d');
		printSymbol('d');
		printSymbol('d');
		printSymbol('d');
		printSymbol('d');
		printSymbol('d');
		printSymbol('b');
		printSymbol('b');
		printSymbol('b');
		printSymbol('b');
		printSymbol('b');
		printSymbol('b');
		printSymbol('b');
		printSymbol('b');
		delay(50);
	}
	
	private static class ItemGroupMessageListener implements MessageListener {

		@Override
		public void onMessage(Message message) {
			try {
				GroupMessage msg = (GroupMessage)(((ObjectMessage) message).getObject());
				System.out.println(msg.toString());
				switch (msg.getOperationCode()){
					case 0 :
						mapItemGroup.get(msg.getGroup().getItem_type()).put(msg.getGroup().getId(), msg.getGroup());
						break;
					case 1 : 
						mapItemGroup.get(msg.getGroup().getItem_type()).remove(msg.getGroup().getId());
						break;
					case 2 :
						for (Map map : mapItemGroup)
							map.clear();
						break;
					default: break;
				}	
			} catch (JMSException e) {
				e.printStackTrace();
			} 
		}
		
	}
		
	
//	public static synchronized Map<Integer, ItemGroup> getItemGroup(int type) {
//		return mapItemGroup.get(type);
//	}
	
	public static boolean isMapEmpty(){
		for (int i = 0; i < mapItemGroup.size(); i++)
			if (!mapItemGroup.get(i).isEmpty())
				return false;
		return true;
	}
	
	public static void initJMS() throws NamingException, JMSException {
		table = new Hashtable<String, String>();
		table.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		table.put(Context.PROVIDER_URL, "jnp://" + server_ip + ":" + naming_port);
		table.put(Context.URL_PKG_PREFIXES, "org.jboss.naming");
		
		init_ctx = new InitialContext(table);
		ConnectionFactory con_fac;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(TransportConstants.PORT_PROP_NAME, connection_port);
		param.put(TransportConstants.HOST_PROP_NAME, server_ip);
		
		TransportConfiguration conf = new TransportConfiguration(NettyConnectorFactory.class.getName(), param);
		con_fac = (ConnectionFactory) HornetQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, conf);
		
		TopicConnection topic_con =  ((TopicConnectionFactory) con_fac).createTopicConnection();
		topic_con.start();
		
		TopicSession topic_session = topic_con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		TopicSubscriber topic_sub = topic_session.createSubscriber((Topic) init_ctx.lookup("/topic/GroupTopic"));
		topic_sub.setMessageListener(new ItemGroupMessageListener());
//		MessageConsumer topic_consumer = topic_session.createConsumer((Topic) init_ctx.lookup("/topic/GroupTopic"));
//		topic_consumer.setMessageListener();
		
		queue_con = ((QueueConnectionFactory) con_fac).createQueueConnection();
		queue_con.start();
		session = queue_con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		
//		cons = session.createConsumer((Destination)init_ctx.lookup("/queue/ParserTestQueue"));
//		cons.setMessageListener(new ParserIdMessageListener());
		sender = session.createSender((Queue) init_ctx.lookup("/queue/MyQueue"));
		ObjectMessage reg_message = session.createObjectMessage();
		model.Parser reg_msg = new model.Parser();
		reg_msg.setRegion(region);
		reg_msg.setMoneyTypes(money_type);
		reg_msg.setId(parser_id);
		reg_message.setObject(reg_msg);
		sender.send(reg_message);
		//reg_sender.close();
	}
	
	public static void loadProperties() throws IOException{
		Properties props = new Properties();
		FileInputStream MyInputStream = new FileInputStream("conf.properties");
		props.load(MyInputStream);        
		MyInputStream.close(); // better in finally block
		cur_scr = Integer.parseInt(props.getProperty("current_screen"));
		Utils.SCREEN_PATH = props.getProperty("screen_path");
		region = Byte.parseByte(props.getProperty("region"));
		StringTokenizer st = new StringTokenizer(props.getProperty("money_type"), ",");
		money_type = new ArrayList<Integer>();
		while (st.hasMoreTokens()) {
			money_type.add(Integer.parseInt(st.nextToken()));
		}
		server_ip = props.getProperty("server_ip");
		parser_id = props.getProperty("parser_id");
	}
	
	public static void storeProperties() throws IOException {
		Properties props = new Properties();
		FileInputStream MyInputStream = new FileInputStream("conf.properties");
		props.load(MyInputStream);        
		MyInputStream.close(); 
		props.setProperty("current_screen", Integer.toString(cur_scr));
		FileOutputStream MyOutputStream = new FileOutputStream("conf.properties");        
		props.store(MyOutputStream, "myAddedKey: myAddedValue");
		MyOutputStream.close(); 
	}
	
	public static void init() throws IOException, NamingException, JMSException, AWTException {
		selected_class = -1;
		selected_type1 = -1;
		selected_type2 = -1;
		
		loadProperties();
		
		Utils.LoadPatterns();
		mapItemGroup = new ArrayList<ConcurrentHashMap<Integer,ItemGroup>>();
		for (int i = 0; i < 50; i++)
			mapItemGroup.add(new ConcurrentHashMap<Integer, ItemGroup>());
		items = new LinkedList<Item>();
		scr_nums = new LinkedList<Integer>();
		groupId = new LinkedList<Integer>();

		initJMS();
		
		robot = new Robot();
	}
	
	public static void main(String[] args) throws Exception {
		init();
		robot.mouseMove(orig.x, orig.y);
		delay(5000);
		moveTo(Utils.AUCTION_BUTTON);
		delay(100);
		mousePress();
		delay(1000);
		moveTo(Utils.EQUIPMENT_BUTTON);
		delay(100);
		mousePress();
		delay(100);
		int count = 0;
		while (count < 2) {
			while (isMapEmpty())
				Thread.sleep(2000);
			for (int i = 0; i < 5; i++) 
				for (int j = 0; j < Utils.item_amount.length; j++)
					for (int k = 0; k < Utils.item_amount[j]; k++) 
						if (Utils.can_equip[i][j][k] == 1 && !mapItemGroup.get(Utils.getOffsettedIndex(j, k)).isEmpty()){
							selectClass(i);
							selectItem1Type(j);
							selectItem2Type(k, i, j);
							int index = Utils.getOffsettedIndex(j, k);
							for (int group_id : mapItemGroup.get(index).keySet()){
								ItemGroup group = mapItemGroup.get(index).get(group_id);
								selectLevels(Utils.minLevel[group.getLvlGroup()], Utils.maxLevel[group.getLvlGroup()]);
								cur_click_pos = new ArrayList<Integer>();
								item_type_number = Utils.getOffsettedIndex(selected_type1, selected_type2);
								for (int t = 0; t < Utils.stat[item_type_number].length; t++)
									cur_click_pos.add(Utils.stat[item_type_number][t]);
								for (int t = 0; t < group.getProps().length / 2; t++) 
									selectPrefferedStats(t, group.getProps()[t * 2], group.getProps()[t * 2 + 1]);
								selectBuyout(group.getBuyOut());
								pressSearch();
								while (true){
									if (!Utils.nextPageExists(processPage(group_id, group)))
										break;
									nextPage();
								}
								clearPrefferedStats();
							}
						}
			count++;
		}
		storeProperties();
		session.close();
		queue_con.close();
	 }
}