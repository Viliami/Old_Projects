/*
To compile and run this in NotePad++  Save it as Template.java (needs to be changed to whatever the Java class name is). Press F5 and then paste the line below into the box:

R:\DigiTech\JDK86\comp.bat "$(FULL_CURRENT_PATH)" "$(CURRENT_DIRECTORY)" "$(NAME_PART)"

Then Press Run
----------------------------------------------------------------------------------------------------------------------
Use this file as the starting point for all of your p t m,\ mkm,  rograms in the programming topic. 

*/
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.imageio.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
//swag
import java.util.Collections;

public class Snake extends JFrame implements ActionListener, KeyListener{
	Canvas c;
	boolean dlc = true;
	int DLCADx = 0;
	static Snake snakeClass;
	Button startButton, stopButton;
	boolean animationRunning = true;
	private BufferStrategy	strategy;
	GameMode mode = GameMode.ONEPLAYER;
	User player1 = new User();
	User player2 = new User();
	User ai = new User();
	SnakePlayer snake = new SnakePlayer(0,0,Color.black,player1);
	SnakePlayer snake2 = new SnakePlayer(0,0,Color.black,player2);
	SnakePlayer sneke = new SnakePlayer(0,0,Color.black,ai);
	ArrayList<Block> wallBlocks = new ArrayList<Block>();
	ArrayList<Block> fruitArray = new ArrayList<Block>();
	boolean isUpdated = false;
	boolean isUpdated2 = false;
	boolean walls = false; 
	int counter;
	boolean forward = true;
	int highScore = 5;
	Font sanSerifFont = new Font("Impact", Font.BOLD, 20);
	//Font bigFont = new Font("Impact", Font.BOLD, 70);
	//Font smallFont = new Font("Impact", Font.PLAIN, 15);
	int players = 1;
	int otherCounter = 1;
	ArrayList<User> users = new ArrayList<User>();
	ArrayList<User> leaderBoard = new ArrayList<User>();
	GameState gameState = GameState.MENU;
	int mouseX = 0;
	int mouseY = 0;
	ImageIcon icon = new ImageIcon("iconAnim/iconAnim1.png");
	ImageIcon icon2 = new ImageIcon("iconAnim/iconAnim2.png");
	ImageIcon icon3 = new ImageIcon("iconAnim/iconAnim3.png");
	ImageIcon icon4 = new ImageIcon("iconAnim/iconAnim4.png");
	ImageIcon icon5 = new ImageIcon("iconAnim/iconAnim5.png");
	ImageIcon icon6 = new ImageIcon("iconAnim/iconAnim6.png");
	ImageIcon icon7 = new ImageIcon("iconAnim/iconAnim7.png");
	ImageIcon icon8 = new ImageIcon("iconAnim/iconAnim8.png");
	boolean aiPlays = false;
	final static int UI_TITLE = 0;
	final static int UI_NEWGAME = 1;
	final static int UI_NEWGAME_1PLAYER = 2;
	final static int UI_NEWGAME_2PLAYER = 3;
	final static int UI_NEWGAME_2PLAYER_LOCAL = 4;
	final static int UI_NEWGAME_2PLAYER_AI = 5;
	final static int UI_NEWGAME_2PLAYER_LAN = 6;
	final static int UI_LEADERBOARD = 7;
	final static int UI_OPTIONS = 8;
	final static int UI_OPTIONS_VSYNC = 9;
	final static int UI_OPTIONS_VSYNC_ON = 10;
	final static int UI_OPTIONS_VSYNC_OFF = 11;
	final static int UI_OPTIONS_FPS = 12;
	final static int UI_OPTIONS_FPS_10 = 13;
	final static int UI_OPTIONS_FPS_20 = 14;
	final static int UI_OPTIONS_FPS_30 = 15;
	final static int UI_OPTIONS_FPS_40 = 16;
	final static int UI_OPTIONS_PLAYER1COLOR = 17;
	final static int UI_OPTIONS_PLAYER1COLOR_RED = 18;
	final static int UI_OPTIONS_PLAYER1COLOR_GREEN = 19;
	final static int UI_OPTIONS_PLAYER1COLOR_BLUE = 20;
	final static int UI_OPTIONS_PLAYER1COLOR_YELLOW = 21;
	final static int UI_OPTIONS_PLAYER2COLOR = 22;
	final static int UI_OPTIONS_PLAYER2COLOR_RED = 23;
	final static int UI_OPTIONS_PLAYER2COLOR_GREEN = 24;
	final static int UI_OPTIONS_PLAYER2COLOR_BLUE = 25;
	final static int UI_OPTIONS_PLAYER2COLOR_YELLOW = 26;
	final static int UI_OPTIONS_AICOLOR = 27;
	final static int UI_OPTIONS_AICOLOR_RED = 28;
	final static int UI_OPTIONS_AICOLOR_GREEN = 29;
	final static int UI_OPTIONS_AICOLOR_BLUE = 30;
	final static int UI_OPTIONS_AICOLOR_YELLOW = 31;
	final static int UI_EXIT = 32;
	Image background;
	Font smallFont; //= new Font("Visitor TT1 BRK", Font.BOLD, 30);
	Font bigFont; //= new Font("Visitor TT1 BRK", Font.BOLD, 72);
	boolean vsync = false;
	int fps = 30;
	Color player1Color = new Color(255,255,0);
	Color player2Color = new Color(0,255,0);
	Color aiColor = new Color(0,0,255);
	boolean gridOn = false;
	Socket socket;
	ServerSocket serverSocket;
	int port = 1234;
	PrintWriter lanOutput;
	BufferedReader lanInput;
	String lanMessage = "DEFAULT";
	boolean updatedLanUp2 = false, updatedLanLeft2 = false, updatedLanDown2 = false, updatedLanRight2 = false, updatedLanUp = false, updatedLanLeft = false, updatedLanDown = false, updatedLanRight = false;
	ArrayList<UIelement> uiElements = new ArrayList<UIelement>();
	MENUSTATE menuState = MENUSTATE.DEFAULT;
	public enum MENUSTATE{
		DEFAULT, NEWGAME, NEWGAME_1PLAYER, NEWGAME_2PLAYER, LEADERBOARD, OPTIONS, OPTIONS_VSYNC,OPTIONS_FPS, OPTIONS_PLAYER1COLOR, OPTIONS_PLAYER2COLOR, OPTIONS_AICOLOR, EXIT
	}
	public enum GameMode{
		ONEPLAYER, TWOPLAYER_LOCAL, TWOPLAYER_AI, TWO_PLAYER_LAN_HOST, TWO_PLAYER_LAN_CLIENT
	}
	public enum BlockType{
		SNAKE, SNAKEHEAD, WALL, TRAILING, FRUIT
	}
	public enum GameState{
		MENU, GAMEPLAY, GAMEOVER
	}
	public static void main(String s[]) {
		snakeClass = new Snake();
	}
	static {
		System.setProperty("sun.java2d.transaccel", "True");
		// System.setProperty("sun.java2d.trace", "timestamp,log,count");
		System.setProperty("sun.java2d.opengl", "True");
		//System.setProperty("sun.java2d.d3d", "True");
		System.setProperty("sun.java2d.ddforcevram", "True");
	}
	
	public Snake(){
		c = new Canvas();
		c.setSize(500, 500);
		c.addKeyListener(this);
		setResizable(false);
		background = loadImage("uiBackground/croppedGrid.jpg");
		try{
		  InputStream is = Snake.class.getResourceAsStream("visitor1.ttf");
		  Font temp = Font.createFont(Font.TRUETYPE_FONT, is);
		  smallFont = temp.deriveFont(Font.BOLD, 30f);
		  bigFont = smallFont.deriveFont(Font.BOLD, 72f);
		}catch(Exception ex) {
		  ex.printStackTrace();
		}
		// Add a window listner for close button
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				cleanUp();
			}
		});
		c.addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				//System.out.println("mouseX = " + mouseX + " mouseY = " + mouseY);
			}

			public void mouseDragged(MouseEvent e) {
			   mouseX = e.getX();
			   mouseY = e.getY();
			   //System.out.println("mouseX = " + mouseX + " mouseY = " + mouseY);
			}     
        });
		c.addMouseListener(new MouseListener(){
			public void mousePressed(MouseEvent e){}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e){}
			public void mouseExited(MouseEvent e){}
			public void mouseClicked(MouseEvent e){
				boolean skip = false;
				if(menuState == MENUSTATE.NEWGAME || menuState == MENUSTATE.OPTIONS || menuState == MENUSTATE.EXIT ||menuState == MENUSTATE.LEADERBOARD){
					if(checkMouse(0,60,200,400)){
						menuState = MENUSTATE.DEFAULT;
						UIelement ui_newGame = uiElements.get(UI_NEWGAME);
						UIelement ui_leaderBoard = uiElements.get(UI_LEADERBOARD);
						UIelement ui_options = uiElements.get(UI_OPTIONS);
						UIelement ui_exit = uiElements.get(UI_EXIT);
						ui_newGame.show = true;
						ui_newGame.partPartShow = false;
						ui_newGame.partShow = false;
						ui_newGame.x = ui_newGame.x_origin;
						ui_newGame.activated = false;
						
						ui_leaderBoard.show = true;
						ui_leaderBoard.partPartShow = false;
						ui_leaderBoard.partShow = false;
						ui_leaderBoard.x = ui_leaderBoard.x_origin;
						ui_leaderBoard.activated = false;
						
						ui_options.show = true;
						ui_options.partPartShow = false;
						ui_options.partShow = false;
						ui_options.x = ui_options.x_origin;
						ui_options.activated = false;
						
						ui_exit.show = true;
						ui_exit.partPartShow = false;
						ui_exit.partShow = false;
						ui_exit.x = ui_exit.x_origin;
						ui_exit.activated = false;
						skip = true;
					}
				}else if(menuState == MENUSTATE.NEWGAME_1PLAYER || menuState == MENUSTATE.NEWGAME_2PLAYER){
					if(checkMouse(0,60,370,400)){
						UIelement ui_newGame_1player = uiElements.get(UI_NEWGAME_1PLAYER);
						UIelement ui_newGame_2player = uiElements.get(UI_NEWGAME_2PLAYER);
						menuState = MENUSTATE.NEWGAME;
						ui_newGame_1player.show = true;
						ui_newGame_1player.partPartShow = false;
						ui_newGame_1player.partShow = false;
						ui_newGame_1player.x = ui_newGame_1player.x_origin;
						ui_newGame_1player.activated = false;
						ui_newGame_2player.show = true;
						ui_newGame_2player.partPartShow = false;
						ui_newGame_2player.partShow = false;
						ui_newGame_2player.x = ui_newGame_2player.x_origin;
						ui_newGame_2player.activated = false;
						skip = true;
					}
				}else if(menuState == MENUSTATE.OPTIONS_VSYNC || menuState == MENUSTATE.OPTIONS_FPS || menuState == MENUSTATE.OPTIONS_PLAYER1COLOR || menuState == MENUSTATE.OPTIONS_PLAYER2COLOR || menuState == MENUSTATE.OPTIONS_AICOLOR){
					if(checkMouse(0,60,370,400)){
						UIelement ui_options_vsync = uiElements.get(UI_OPTIONS_VSYNC);
						UIelement ui_options_fps = uiElements.get(UI_OPTIONS_FPS);
						UIelement ui_options_player1color = uiElements.get(UI_OPTIONS_PLAYER1COLOR);
						UIelement ui_options_player2color = uiElements.get(UI_OPTIONS_PLAYER2COLOR);
						UIelement ui_options_aicolor = uiElements.get(UI_OPTIONS_AICOLOR);
						menuState = MENUSTATE.OPTIONS;
						ui_options_vsync.show = true;
						ui_options_vsync.partPartShow = false;
						ui_options_vsync.partShow = false;
						ui_options_vsync.x = ui_options_vsync.x_origin;
						ui_options_vsync.activated = false;
						ui_options_fps.show = true;
						ui_options_fps.partPartShow = false;
						ui_options_fps.partShow = false;
						ui_options_fps.x = ui_options_fps.x_origin;
						ui_options_fps.activated = false;
						ui_options_player1color.show = true;
						ui_options_player1color.partPartShow = false;
						ui_options_player1color.partShow = false;
						ui_options_player1color.x = ui_options_player1color.x_origin;
						ui_options_player1color.activated = false;
						ui_options_player2color.show = true;
						ui_options_player2color.partPartShow = false;
						ui_options_player2color.partShow = false;
						ui_options_player2color.x = ui_options_player2color.x_origin;
						ui_options_player2color.activated = false;
						ui_options_aicolor.show = true;
						ui_options_aicolor.partPartShow = false;
						ui_options_aicolor.partShow = false;
						ui_options_aicolor.x = ui_options_aicolor.x_origin;
						ui_options_aicolor.activated = false;
						skip = true;
					}
				}
				if(!skip){
					for(int i = 0; i <= uiElements.size()-1; i++){
						UIelement uielement = uiElements.get(i);
						if(checkMouse(uielement.x, uielement.y-uielement.h, uielement.w, uielement.h) && uielement.show){
							uielement.activated = true;
							if(uielement.text == "NEW GAME"){
								menuState = MENUSTATE.NEWGAME;
							}else if(uielement.text == "1 PLAYER"){
								mode = GameMode.ONEPLAYER;
								menuState = MENUSTATE.DEFAULT;
								players = 1;
								aiPlays = false;
								createUser(player1,1);
								restart();
							}else if(uielement.text == "2 PLAYER"){
								menuState = MENUSTATE.NEWGAME_2PLAYER;
							}else if(uielement.text == "LOCAL"){
								menuState = MENUSTATE.DEFAULT;
								mode = GameMode.TWOPLAYER_LOCAL;
								players = 2;
								spawnFruit();
								aiPlays = false;
								createUser(player1,1);
								createUser(player2,2);
								restart();
							}else if(uielement.text == "AI"){
								menuState = MENUSTATE.DEFAULT;
								mode = GameMode.TWOPLAYER_AI;
								players = 1;
								aiPlays = true;
								spawnFruit();
								createUser(player1,1);
								restart();
							}else if(uielement.text == "LAN"){
								menuState = MENUSTATE.DEFAULT;
								//mode = GameMode.TWO_PLAYER_LAN_HOST;
								createUser(player1,1);
								createUser(player2,2);
								System.out.print("Host or join a new game?");
								String userChoice = System.console().readLine();
								if(userChoice.toUpperCase().equals("HOST")){
									mode = GameMode.TWO_PLAYER_LAN_HOST;
									initLan();
									spawnFruit(250,100);
								}else if(userChoice.toUpperCase().equals("CLIENT") || userChoice.toUpperCase().equals("JOIN")){
									mode = GameMode.TWO_PLAYER_LAN_CLIENT;
									System.out.print("client mode selected");
									initLan();
									spawnFruit(250,100);
								}
								players = 2;
								aiPlays = false;
								restart();
							}else if(uielement.text == "LEADERBOARD"){
								menuState = MENUSTATE.LEADERBOARD;
							}else if(uielement.text == "OPTIONS"){
								menuState = MENUSTATE.OPTIONS;
							}else if(uielement.text == "VSYNC"){
								menuState = MENUSTATE.OPTIONS_VSYNC;
							}else if(uielement.text == "ON"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_VSYNC_OFF).optionDeselected();
								vsync = true;
							}else if(uielement.text == "OFF"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_VSYNC_ON).optionDeselected();
								vsync = false;
							}else if(uielement.text == "FPS"){
								menuState = MENUSTATE.OPTIONS_FPS;
							}else if(uielement.text == "10"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_FPS_20).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_30).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_40).optionDeselected();
								fps = 10;
								System.out.println(fps);
							}else if(uielement.text == "20"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_FPS_10).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_30).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_40).optionDeselected();
								fps = 20;
								System.out.println(fps);
							}else if(uielement.text == "30"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_FPS_10).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_20).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_40).optionDeselected();
								fps = 30;
								System.out.println(fps);
							}else if(uielement.text == "40"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_FPS_10).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_20).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_30).optionDeselected();
								fps = 40;
								System.out.println(fps); 
							}else if(uielement.text == "P1 COLOR"){
								menuState = MENUSTATE.OPTIONS_PLAYER1COLOR;
							}else if(i == UI_OPTIONS_PLAYER1COLOR_RED){
								uielement.optionSelected(new Color(255,0,0),new Color(150,0,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE).optionDeselected(new Color(0,0,150));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW).optionDeselected(new Color(150,150,0));
								player1Color = new Color(255,0,0);
							}else if(i == UI_OPTIONS_PLAYER1COLOR_GREEN){
								uielement.optionSelected(new Color(0,255,0),new Color(0,150,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE).optionDeselected(new Color(0,0,150));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW).optionDeselected(new Color(150,150,0));
								player1Color = new Color(0,255,0);
							}else if(i == UI_OPTIONS_PLAYER1COLOR_BLUE){
								uielement.optionSelected(new Color(0,0,255),new Color(0,0,150));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW).optionDeselected(new Color(150,150,0));
								player1Color = new Color(0,0,255);
							}else if(i == UI_OPTIONS_PLAYER1COLOR_YELLOW){
								uielement.optionSelected(new Color(255,255,0),new Color(150,150,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE).optionDeselected(new Color(0,0,150));
								player1Color = new Color(255,255,0);
							}else if(uielement.text == "P2 COLOR"){
								menuState = MENUSTATE.OPTIONS_PLAYER2COLOR;
							}else if(i == UI_OPTIONS_PLAYER2COLOR_RED){
								uielement.optionSelected(new Color(255,0,0),new Color(150,0,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE).optionDeselected(new Color(0,0,150));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW).optionDeselected(new Color(150,150,0));
								player2Color = new Color(255,0,0);
							}else if(i == UI_OPTIONS_PLAYER2COLOR_GREEN){
								uielement.optionSelected(new Color(0,255,0),new Color(0,150,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE).optionDeselected(new Color(0,0,150));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW).optionDeselected(new Color(150,150,0));
								player2Color = new Color(0,255,0);
							}else if(i == UI_OPTIONS_PLAYER2COLOR_BLUE){
								uielement.optionSelected(new Color(0,0,255),new Color(0,0,150));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW).optionDeselected(new Color(150,150,0));
								player2Color = new Color(0,0,255);
							}else if(i == UI_OPTIONS_PLAYER2COLOR_YELLOW){
								uielement.optionSelected(new Color(255,255,0),new Color(150,150,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE).optionDeselected(new Color(0,0,150));
								player2Color = new Color(255,255,0);
							}else if(uielement.text == "AI COLOR"){
								menuState = MENUSTATE.OPTIONS_AICOLOR;
							}else if(i == UI_OPTIONS_AICOLOR_RED){
								uielement.optionSelected(new Color(255,0,0),new Color(150,0,0));
								uiElements.get(UI_OPTIONS_AICOLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_AICOLOR_BLUE).optionDeselected(new Color(0,0,150));
								uiElements.get(UI_OPTIONS_AICOLOR_YELLOW).optionDeselected(new Color(150,150,0));
								player1Color = new Color(255,0,0);
							}else if(i == UI_OPTIONS_AICOLOR_GREEN){
								uielement.optionSelected(new Color(0,255,0),new Color(0,150,0));
								uiElements.get(UI_OPTIONS_AICOLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_AICOLOR_BLUE).optionDeselected(new Color(0,0,150));
								uiElements.get(UI_OPTIONS_AICOLOR_YELLOW).optionDeselected(new Color(150,150,0));
								aiColor = new Color(0,255,0);
							}else if(i == UI_OPTIONS_AICOLOR_BLUE){
								uielement.optionSelected(new Color(0,0,255),new Color(0,0,150));
								uiElements.get(UI_OPTIONS_AICOLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_AICOLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_AICOLOR_YELLOW).optionDeselected(new Color(150,150,0));
								aiColor = new Color(0,0,255);
							}else if(i == UI_OPTIONS_AICOLOR_YELLOW){
								uielement.optionSelected(new Color(255,255,0),new Color(150,150,0));
								uiElements.get(UI_OPTIONS_AICOLOR_RED).optionDeselected(new Color(150,0,0));
								uiElements.get(UI_OPTIONS_AICOLOR_GREEN).optionDeselected(new Color(0,150,0));
								uiElements.get(UI_OPTIONS_AICOLOR_BLUE).optionDeselected(new Color(0,0,150));
								aiColor = new Color(255,255,0);
							}else if(uielement.text == "EXIT"){
								menuState = MENUSTATE.EXIT;
							}
						}else{
							uielement.activated = false;
						}
					}
				}
			}
		});
		this.getContentPane().add(c, BorderLayout.CENTER);
		this.setTitle("SNAKE");
		this.pack();
		this.setVisible(true);
		c.createBufferStrategy(2);
		strategy = c.getBufferStrategy();
		counter = 0;
		spawnFruit(350,400);
		ai.name = "Computer";
		//generateLeaderBoard();
		File f = new File("save.data");
		if(!f.exists()){
			createSaveFile("save.data");
			//createSaveFile("save.xml");
		}
		loadSaveFile("save.data");
		//printFile("save.data");
		this.setIconImage(icon.getImage());
		loadElements();
		new AnimationThread(this);
	}
	
	public void cleanUp(){
		try{
			if(serverSocket != null){
				serverSocket.close();
				serverSocket = null;
			}
			if(socket != null){
				socket.close();
				socket = null;
			}
			if(lanInput != null){
				lanInput.close();
				lanInput = null;
			}
			if(lanOutput != null){
				lanOutput.close();
				lanOutput = null;
			}
			System.out.println("Sockets are closed.");
		}catch(Exception e){
			System.out.println("Failed to clean up");
		}
	}
	
	public void initLan(){
		try{
			if(mode == GameMode.TWO_PLAYER_LAN_HOST){
				serverSocket = new ServerSocket(port);
				System.out.println("Waiting for client to connect....");
				socket = serverSocket.accept();
			}else if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
				socket = new Socket("localhost",port);
			}
			lanOutput = new PrintWriter(socket.getOutputStream(),true);
			lanInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception e){
			System.out.println("Could not initialize LAN");
		}
	}
	
	public void createUser(User user, int playerNumber){
		String temp;
		do{
			temp = JOptionPane.showInputDialog("Player " + playerNumber + " name: (max length = 15)");
			String errorStack = "";
			if(temp == null){
				errorStack += "Don't press that.\n";
			}else{
				if(temp.length() > 15)
					errorStack += "Max length is 15 characters.\n";
				if(temp.isEmpty())
					errorStack += "Type something fgt.\n";
				}
				if(errorStack != "")
					JOptionPane.showMessageDialog(null, errorStack);
		}while(temp == null || temp.length() > 15 || temp.isEmpty());
		boolean existingUser = false;
		if(temp != null && temp.length() <= 15 && !temp.isEmpty()){
			user.name = temp;
			for(int i = 0; i <= users.size()-1; i++){
				User tempUser = users.get(i);
				if(tempUser.name.toLowerCase().equals(user.name.toLowerCase())){
					existingUser = true;
					user.highScore = tempUser.highScore;
					user.totalScore = tempUser.totalScore;
					user.position = tempUser.position;
					users.remove(i);
					users.add(i,user);
					break;
				}
			}
			if(existingUser){
				JOptionPane.showMessageDialog(null, "Welcome back " + user.name + "\nYour stats:\n\t" + "Highscore = " + user.highScore + "\n\tPosition = " + user.position + "\n\tScore total = " + user.totalScore);
			}
		}
		if(!existingUser)
			users.add(user);
	}
	
	public void createSaveFile(String saveFile){
		generateLeaderBoard();
		writeLineToFile(saveFile, "<SAVEDATA>", false, false);
		writeLineToFile(saveFile, "<LEADERBOARD>", true);
		for(int i = 0; i <= leaderBoard.size()-1; i++){
			User user = leaderBoard.get(i);
			writeLineToFile(saveFile, i+1 + ". " + user.name +  " = " + user.score, true);
		}
		writeLineToFile(saveFile, "</LEADERBOARD>", true);
		writeLineToFile(saveFile, "<PLAYERSTATS>", true);
		for(int i = 0; i <= users.size()-1; i++){
			User user = users.get(i);
			writeLineToFile(saveFile, "<USER>",true);
			writeLineToFile(saveFile, "<name>" + user.name + "</name>",false);
			writeLineToFile(saveFile, "<score>" + user.score + "</score>",false);
			writeLineToFile(saveFile, "<totalScore>" + user.totalScore + "</totalScore>",false);
			writeLineToFile(saveFile, "<highScore>" + user.highScore + "</highScore>",false);
			writeLineToFile(saveFile, "<position>" + user.position + "</position>",false);
			writeLineToFile(saveFile, "<playSessions>" + user.playSessions + "</playSessions>", false);
			writeLineToFile(saveFile, "</USER>",false);
		}
		writeLineToFile(saveFile, "</PLAYERSTATS>", true);
		writeLineToFile(saveFile, "</SAVEDATA>", true);
	}
	
	public void saveData(String saveFile){
		writeLineToFile(saveFile, "<SAVEDATA>", false, false);
		writeLineToFile(saveFile, "<LEADERBOARD>", true);
		for(int i = 0; i <= leaderBoard.size()-1; i++){
			User user = leaderBoard.get(i);
			writeLineToFile(saveFile, i+1 + ". " + user.name +  " = " + user.score, true);
		}
		writeLineToFile(saveFile, "</LEADERBOARD>", true);
		writeLineToFile(saveFile, "<PLAYERSTATS>", true);
		for(int i = 0; i <= users.size()-1; i++){
			User user = users.get(i);
			writeLineToFile(saveFile, "<USER>",true);
			writeLineToFile(saveFile, "<name>" + user.name + "</name>",false);
			writeLineToFile(saveFile, "<score>" + user.score + "</score>",false);
			writeLineToFile(saveFile, "<totalScore>" + user.totalScore + "</totalScore>",false);
			writeLineToFile(saveFile, "<highScore>" + user.highScore + "</highScore>",false);
			writeLineToFile(saveFile, "<position>" + user.position + "</position>",false);
			writeLineToFile(saveFile, "<playSessions>" + user.playSessions + "</playSessions>",false);
			writeLineToFile(saveFile, "</USER>",false);
		}
		writeLineToFile(saveFile, "</PLAYERSTATS>", true);
		writeLineToFile(saveFile, "</SAVEDATA>", true);
	}
	
	public void loadSaveFile(String filePath){
		String data = readFile(filePath);
		ArrayList<String> userdata = new ArrayList<String>();
		between(data, "<USER>", "</USER>", userdata);
		for(int i = 0; i <= userdata.size()-1; i++){ //load users
			User user = new User();
			user.name = between(userdata.get(i), "<name>", "</name>");
			user.score = Integer.parseInt(between(userdata.get(i), "<score>", "</score>"));
			user.highScore = Integer.parseInt(between(userdata.get(i), "<highScore>", "</highScore>"));
			user.totalScore = Integer.parseInt(between(userdata.get(i), "<totalScore>", "</totalScore>"));
			user.position = Integer.parseInt(between(userdata.get(i), "<position>", "</position>"));
			user.playSessions = Integer.parseInt(between(userdata.get(i), "<playSessions>", "</playSessions>"));
			users.add(user);
			//System.out.println("\nUser " + user.name + "{\n" + "\t" + "Name = " + user.name + "\n\t" + "Score = " + user.score + "\n}");
		}
		loadLeaderBoard();
	}
	
	public void loadLeaderBoard(){
		for(int i = 0; i <= users.size()-1; i++){
			User user = users.get(i);
			leaderBoard.add(user);
		}
		Collections.sort(users);
		Collections.reverse(users);
		for(int i = 6; i <= leaderBoard.size()-1; i++){ //delete everyone after 6th place
			User user = users.get(i);
			leaderBoard.remove(i);
		}
		printFile("save.data");
	}
	
	public String between(String input, String str1, String str2, ArrayList<String> array){
		Pattern p = Pattern.compile(str1 + "(.+?)" + str2);
		Matcher m = p.matcher(input);
		String contents = "";
		while (m.find()) {
			contents += m.group(1);
			array.add(m.group(1));
		}
		return contents;
	}
	
	public String between(String input, String str1, String str2){
		Pattern p = Pattern.compile(str1 + "(.+?)" + str2);
		Matcher m = p.matcher(input);
		String contents = "";
		while (m.find()) {
			contents += m.group(1);
		}
		return contents;
	}
	
	public String readFile(String filePath){
		String line = null;
		String paragraph = "";
		try{
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			while((line = br.readLine()) != null){
				//System.out.println(line);
				paragraph += line + "\n";
			}
			br.close();
		}catch(FileNotFoundException exception){
			System.out.println("File could not be opened: '" + filePath + "'");
		}catch(IOException exception){
			System.out.println("File could not be read: '" + filePath + "'");
		}
		return paragraph;
	}
	
	public void printFile(String filePath){
		System.out.println(readFile(filePath));
	}
	
	public void writeLineToFile(String filePath, String content, boolean newLine, boolean append){
		try{
			FileWriter fw = new FileWriter(filePath, append);
			BufferedWriter bw = new BufferedWriter(fw);
			if(newLine)
				bw.newLine();
			bw.write(content);
			//if(newLine)
				//bw.newLine();
			bw.close();
		}catch(IOException exception){
			System.out.println("Couldn't write to file: '" + filePath + "'");
		}
	}
	
	public void writeLineToFile(String filePath, String content, boolean newLine){
		writeLineToFile(filePath, content, newLine, true);
	}
	
	public void generateLeaderBoard(){
		lbAddUser("Viliami", 5, 1);
		lbAddUser("Alex", 4,2);
		lbAddUser("Brian", 3,3);
		lbAddUser("Aaryn", 2,4);
		lbAddUser("Jaden", 1,5);
		lbAddUser("Matthew", 1,6);
	}
	
	public void lbAddUser(String name, int score, int position){
		User user = new User(name, score);
		user.position = position;
		if(user.score > user.highScore)
			user.highScore = user.score;
		leaderBoard.add(user);
		users.add(user);
	}
	
	public void lbAddUser(User user, int position){
		if(user.score > user.highScore)
			user.highScore = user.score;
		user.position = position;
		leaderBoard.add(user);
	}
	
	public void spawnFruit(){
		Block fruit = new Block(BlockType.FRUIT);
		fruit.x = fruit.WIDTH*randomInteger(1, (c.getWidth()/fruit.WIDTH)-1);
		fruit.y = fruit.HEIGHT*randomInteger(3, (c.getHeight()/fruit.HEIGHT)-1);
		fruitArray.add(fruit);
		if(mode == GameMode.TWO_PLAYER_LAN_HOST){
			lanOutput.print("fruitx=" + fruit.x + "y="+fruit.y+"\n");
			lanOutput.flush();
			System.out.println("Sent");
		}
	}
	
	public void spawnFruit(int x, int y){
		Block fruit = new Block(BlockType.FRUIT);
		fruit.x = x;
		fruit.y = y;
		fruitArray.add(fruit);
	}
	
	public void createWall(){
		wallBlocks.clear();
		int  nigWratio= c.getWidth()/25;
		int  nigHratio= c.getHeight()/25;
		for(int i = 0; i <= nigWratio -1; i++){
			Block wall = new Block(BlockType.WALL);
			wall.x = 0 + (i*wall.WIDTH);
			wall.y = 50;
			wallBlocks.add(wall);
		}
		for(int i = 0; i <= nigHratio-1; i++){
			Block wall = new Block(BlockType.WALL);
			wall.x = 0;
			wall.y = 50 + (i*wall.HEIGHT);
			wallBlocks.add(wall);
		}
		nigHratio--;
		for(int i = 0; i <= nigWratio; i++){
			Block wall = new Block(BlockType.WALL);
			wall.x = c.getWidth()-wall.WIDTH;
			wall.y = 50+ (i*wall.HEIGHT);
			wallBlocks.add(wall);
		}
		nigWratio--;
		for(int i = 1; i <= nigWratio; i++){
			Block wall = new Block(BlockType.WALL);
			wall.x = 0 + (i*wall.WIDTH);
			wall.y = c.getHeight()-(wall.HEIGHT);
			wallBlocks.add(wall);
		}
	}
	
	public void drawWall(){
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		for(int i = 0; i <= wallBlocks.size()-1; i++){
			Block wall = wallBlocks.get(i);
			g.setColor(wall.secondaryColor);
			g.fillRect(wall.x, wall.y, wall.WIDTH, wall.HEIGHT);
			g.setColor(wall.primaryColor);
			g.fillRect(wall.x+wall.WIDTH/10, wall.y + wall.HEIGHT/10, wall.WIDTH*4/5, wall.HEIGHT*4/5);
		}
	}
	
	public void keyTyped(KeyEvent event){}
	public void keyReleased(KeyEvent event){}
	public void keyPressed(KeyEvent event){
		int keyCode = event.getKeyCode();
		char keyChar = event.getKeyChar();
		if(gameState == GameState.MENU){
			
		}else if(gameState == GameState.GAMEPLAY && dlc){
			if(mode == GameMode.TWO_PLAYER_LAN_CLIENT || mode == GameMode.TWO_PLAYER_LAN_HOST){
			
				Block snakeHead;
				if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
					snakeHead = snake2.blocks.get(0);
				}else{
					snakeHead = snake.blocks.get(0);
				}
				// if(keyCode == KeyEvent.VK_UP){
					// if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
						// if(snake2.currentDirection != "DOWN"){
							// snake2.move("UP",true);
							// isUpdated2 = true;
							// //lanOutput.print("UP\n");
							// lanOutput.print("SNAKE2:UP x=" + snakeHead.x + "y=" + snakeHead.y + "\n");
							// lanOutput.flush();
						// }
					// }else if(mode == GameMode.TWO_PLAYER_LAN_HOST){
						// if(snake.currentDirection != "DOWN"){
							// snake.move("UP",true);
							// isUpdated = true;
							// // lanOutput.print("UP\n");
							// lanOutput.print("SNAKE:UP x=" + snakeHead.x + "y=" + snakeHead.y + "\n");
							// lanOutput.flush();
						// }
					// }
				// }else if(keyCode == KeyEvent.VK_DOWN){
					// if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
						// if(snake2.currentDirection != "UP"){
							// snake2.move("DOWN",true);
							// isUpdated2 = true;
							// //lanOutput.print("DOWN\n");
							// lanOutput.print("SNAKE2:DOWN x=" + snakeHead.x + "y=" + snakeHead.y + "\n");
							// lanOutput.flush();
						// }
					// }else if(mode == GameMode.TWO_PLAYER_LAN_HOST){
						// if(snake.currentDirection != "UP"){
							// snake.move("DOWN",true);
							// isUpdated = true;
							// // lanOutput.print("DOWN\n");
							// lanOutput.print("SNAKE:DOWN x=" + snakeHead.x + "y=" + snakeHead.y + "\n");
							// lanOutput.flush();
						// }
					// }
				// }else if(keyCode == KeyEvent.VK_LEFT){
					// if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
						// if(snake2.currentDirection != "RIGHT"){
							// snake2.move("LEFT",true);
							// isUpdated2 = true;
							// //lanOutput.print("LEFT\n");
							// lanOutput.print("SNAKE2:LEFT x=" + snakeHead.x + "y=" + snakeHead.y + "\n");
							// lanOutput.flush();
						// }
					// }else if(mode == GameMode.TWO_PLAYER_LAN_HOST){
						// if(snake.currentDirection != "RIGHT"){
							// snake.move("LEFT",true);
							// isUpdated = true;
							// // lanOutput.print("LEFT\n");
							// lanOutput.print("SNAKE:LEFT x=" + snakeHead.x + "y=" + snakeHead.y + "\n");
							// lanOutput.flush();
						// }
					// }
				// }else if(keyCode == KeyEvent.VK_RIGHT){
					// if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
						// if(snake2.currentDirection != "LEFT"){
							// snake2.move("RIGHT",true);
							// isUpdated2 = true;
							// // lanOutput.print("RIGHT\n");
							// lanOutput.print("SNAKE2:RIGHT x=" + snakeHead.x + "y=" + snakeHead.y + "\n");
							// lanOutput.flush();
						// }
					// }else if(mode == GameMode.TWO_PLAYER_LAN_HOST){
						// if(snake.currentDirection != "LEFT"){
							// snake.move("RIGHT",true);
							// isUpdated = true;
							// // lanOutput.print("RIGHT\n");
							// lanOutput.print("SNAKE:RIGHT x=" + snakeHead.x + "y=" + snakeHead.y + "\n");
							// lanOutput.flush();
						// }
					// }
				// }
				if(mode == GameMode.TWO_PLAYER_LAN_HOST){
					if(keyCode == KeyEvent.VK_UP && !snake.currentDirection.equals("DOWN")){
						lanOutput.print("UP\n");
						lanOutput.flush();
						snake.move("UP",true);
						isUpdated = true;
					}else if(keyCode == KeyEvent.VK_DOWN && !snake.currentDirection.equals("UP")){
						lanOutput.print("DOWN\n");
						lanOutput.flush();
						snake.move("DOWN",true);
						isUpdated = true;
					}else if(keyCode == KeyEvent.VK_LEFT && !snake.currentDirection.equals("RIGHT")){
						lanOutput.print("LEFT\n");
						lanOutput.flush();
						snake.move("LEFT",true);
						isUpdated = true;
					}else if(keyCode == KeyEvent.VK_RIGHT && !snake.currentDirection.equals("LEFT")){
						lanOutput.print("RIGHT\n");
						lanOutput.flush();
						snake.move("RIGHT",true);
						isUpdated = true;
					}
				}else if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
					if(keyCode == KeyEvent.VK_UP && !snake2.currentDirection.equals("DOWN")){
						lanOutput.print("UP\n");
						lanOutput.flush();
						snake2.move("UP",true);
						isUpdated2 = true;
					}else if(keyCode == KeyEvent.VK_DOWN && !snake2.currentDirection.equals("UP")){ 
						lanOutput.print("DOWN\n");
						lanOutput.flush();
						snake2.move("DOWN",true);
						isUpdated2 = true;
					}else if(keyCode == KeyEvent.VK_LEFT && !snake2.currentDirection.equals("RIGHT")){
						lanOutput.print("LEFT\n");
						lanOutput.flush();
						snake2.move("LEFT",true);
						isUpdated2 = true;
					}else if(keyCode == KeyEvent.VK_RIGHT && !snake2.currentDirection.equals("LEFT")){
						lanOutput.print("RIGHT\n");
						lanOutput.flush();
						snake2.move("RIGHT",true);
						isUpdated2 = true;
					}
				}
			}else{
				if(keyCode == KeyEvent.VK_UP){
					snake.move("UP", false);
					snake.update();
					System.out.println("up");
					isUpdated = true;
				}else if(keyCode == KeyEvent.VK_DOWN){
					snake.move("DOWN", false);
					snake.update();
					isUpdated = true;
				}else if(keyCode == KeyEvent.VK_LEFT && snake.currentDirection != "STILL"){
					snake.move("LEFT", false);
					snake.update();
					isUpdated = true;
				}else if(keyCode == KeyEvent.VK_RIGHT){
					snake.move("RIGHT", false);
					snake.update();
					isUpdated = true;
				}else if(keyCode == KeyEvent.VK_SPACE){
					snake.addBlock();
				}else if(keyChar == 'x' || keyChar == 'X'){
					walls = !walls;
					if(walls){
						createWall();
					}
				}else if(keyChar == 'w' || keyChar == 'W'){
					snake2.move("UP", false);
					snake2.update();
					isUpdated2 = true;
				}else if(keyChar == 's'|| keyChar == 'S'){
					snake2.move("DOWN", false);
					snake2.update();
					isUpdated2 = true;
				}else if((keyChar == 'a'|| keyChar == 'A') && snake2.currentDirection != "STILL"){
					System.out.println("Current direction = " + snake2.currentDirection);
					snake2.move("LEFT", false);
					snake2.update();
					isUpdated2 = true;
				}else if(keyChar == 'd'|| keyChar == 'D'){
					snake2.move("RIGHT", false);
					snake2.update();
					isUpdated2 = true;
				}
				if(keyChar == 'y' || keyChar == 'Y'){
					gridOn = !gridOn;
				}
			}
		}else if (!dlc) {
			JOptionPane.showMessageDialog(null, "You must buy the DLC to continue playing.");
		}else if(gameState == GameState.GAMEOVER){
			if(keyCode == KeyEvent.VK_SPACE){
				restart();
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == startButton){
			animationRunning=true;
		}else if(e.getSource() == stopButton){
			animationRunning=false;
		}
	}
		
	public int randomInteger(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public void burs(int x, int y, int index, int speed, SnakePlayer snakePlayer, int r, int g, int b){
		if(index >= 1){
			if(255 - (speed*index) >= 0){
				drawTransparentGlowingCircle(0,20,x+index,y+index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x+index,y+(index/2),2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x-index,y-index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x-index,y-(index/2),2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x-index,y+(index/2),2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x+index,y-(index/2),2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x+(index/2),y-index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x-(index/2),y-index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x+(index/2),y+index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x-(index/2),y+index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x-index,y+index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x+index,y-index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x+index,y,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x-index,y,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x,y+index,2,r,g,b,255-(speed*index));
				drawTransparentGlowingCircle(0,20,x,y-index,2,r,g,b,255-(speed*index));
			}else{
				 snakePlayer.toBurs = false;
			}
		}
	}
	
	public void drawOutlineGlowingCircle(int x, int y, int radius, int glowRadius,  int r, int g, int b, int maxAlpha){
		Graphics2D g2d = (Graphics2D)strategy.getDrawGraphics();
		int nigzRatio = maxAlpha/glowRadius;
		for(int i = 1; i<= glowRadius;i+=2){
			drawOutlineCentredCircle(x,y,radius-i,new Color(r,g,b,maxAlpha-(i*nigzRatio)));
			drawOutlineCentredCircle(x,y,radius+i,new Color(r,g,b,maxAlpha-(i*nigzRatio)));
		}
		
	}
	
	public void drawOutlineCentredCircle(int x, int y, int radius, Color color){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		g.setColor(color);
		x = x-(radius/2);
		y = y-(radius/2);
		g.drawOval(x,y,radius,radius);
	}
	
	public void drawTransparentGlowingCircle(int opaqueRadius, int glowingRadius, int x, int y, int steps, int r, int g, int b, int maxAlpha){
		Graphics2D g2d= (Graphics2D)strategy.getDrawGraphics();
		for(int i = 1; i<= glowingRadius; i+=steps){
			drawCentredCircle(x, y, opaqueRadius + i, new Color(r,g,b,(int)(maxAlpha/i)));
		}
	}
	
	public void drawTransparentGlowingCircle(int opaqueRadius, int glowingRadius, int x, int y, int steps){
		Graphics2D g2d= (Graphics2D)strategy.getDrawGraphics();
		for(int i = 1; i<= glowingRadius; i+=steps){
			drawCentredCircle(x, y, opaqueRadius + i, new Color(255,255,0,(int)(255/i)));
		}
	}
	
	public void drawCentredCircle(int x, int y, int radius, Color color){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		x = x-(radius/2);
		y = y-(radius/2);
		g.setColor(color);
		g.fillOval(x,y,radius,radius);
	}
	
	public void drawFruit(Color color){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		for(int i = 0; i<= fruitArray.size()-1; i++){
			Block fruit = fruitArray.get(i);
			g.setColor(fruit.secondaryColor);
			g.fillRect(fruit.x, fruit.y, fruit.WIDTH, fruit.HEIGHT);
			g.setColor(color);
			g.fillRect(fruit.x+fruit.WIDTH/10,fruit.y+fruit.HEIGHT/10,fruit.WIDTH*4/5,fruit.HEIGHT*4/5);
			drawGlowingCircle(g,fruit.x+fruit.WIDTH/2, fruit.y+fruit.HEIGHT/2,5,5,80, color);
		}
	}
	
	public void restart(){
		gameState = GameState.GAMEPLAY;
		player1.score = 0;
		snake = new SnakePlayer(100,100, player1Color, player1);
		if(players == 2){
			player2.score = 0;
			snake2 = new SnakePlayer(200,200, player2Color, player2);
		}
		if(aiPlays){
			ai.score = 0;
			sneke = new SnakePlayer(200,200, aiColor, ai);
		}
	}
	
	public void drawLines(Color color, Color secondColor){
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		int spacing = 1;
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		for(int i = 0; i<= c.getHeight(); i+=25){
			for(int j = 50; j<= c.getWidth(); j+=25){
				g.setColor(color);
				g.drawLine(i,j,i,j);
				g.setColor(secondColor);
				g.drawLine(i+spacing,j+spacing,i+spacing,j+spacing);
				g.drawLine(i-spacing,j-spacing,i-spacing,j-spacing);
				g.drawLine(i+spacing,j,i+spacing,j);
				g.drawLine(i-spacing,j,i-spacing,j);
				g.drawLine(i,j+spacing,i,j+spacing);
				g.drawLine(i,j-spacing,i,j-spacing);
				g.drawLine(i+spacing,j-spacing,i+spacing,j-spacing);
				g.drawLine(i-spacing,j+spacing,i-spacing,j+spacing);
			}
		}
		for(int i = 0; i<= c.getHeight(); i+=25){
			g.setColor(new Color(red,green,blue,50));
			g.drawLine(0,i+50,c.getWidth(),i+50);
			g.drawLine(i,50,i,c.getHeight());
		}
	}
	
	public void drawGlowingCircle(Graphics2D g, int x, int y, int steps, int startRadius, int endRadius, Color color){
		int j = 0;
		for(int i = startRadius; i<= endRadius; i+=steps){
			j++;
			drawCenteredCircle(g,x,y,i,255/(2*j), color);
		}
	}
	
	public void drawGlowingRect(Graphics2D g, int x, int y, int steps, int height, int startWidth, int glowingWidth, Color color){
		int j = 0;
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		for(int i = startWidth; i<= glowingWidth; i+=steps){
			j++;
			//drawCenteredCircle(g,x,y,i,255/(2*j), color);
			g.setColor(new Color(red,green,blue,255/(2*j)));
			g.fillRect(x-i/2,y-height/2,i,height);
		}
	}
	
	public void drawCenteredCircle(Graphics2D g, int x, int y, int r, int a, Color color){
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		x = x-(r/2);
		y = y-(r/2);
		g.setColor(new Color(red,green,blue,a));
		g.fillOval(x,y,r,r);
	}
	
	
	/*public Color blend(Color c0, Color c1) {
		double totalAlpha = c0.getAlpha() + c1.getAlpha();
		double weight0 = c0.getAlpha() / totalAlpha;
		double weight1 = c1.getAlpha() / totalAlpha;

		double r = weight0 * c0.getRed() + weight1 * c1.getRed();
		double g = weight0 * c0.getGreen() + weight1 * c1.getGreen();
		double b = weight0 * c0.getBlue() + weight1 * c1.getBlue();
		double a = Math.max(c0.getAlpha(), c1.getAlpha());

		return new Color((int) r, (int) g, (int) b, (int) a);
  }*/
  
	public void updateIcon(){
		if(otherCounter == 5){
			snakeClass.setIconImage(icon.getImage());
		}else if(otherCounter == 10){
			snakeClass.setIconImage(icon2.getImage());
		}else if(otherCounter == 15){
			snakeClass.setIconImage(icon3.getImage());
		}else if(otherCounter == 20){
			snakeClass.setIconImage(icon4.getImage());
		}else if(otherCounter == 25){
			snakeClass.setIconImage(icon5.getImage());
		}else if(otherCounter == 30){
			snakeClass.setIconImage(icon6.getImage());
		}else if(otherCounter == 35){
			snakeClass.setIconImage(icon7.getImage());
		}else if(otherCounter == 40){
			snakeClass.setIconImage(icon8.getImage());
			otherCounter = 0;
		}
		otherCounter++;
	}
	
	public void updateCanvas(){
		updateIcon();
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		FontMetrics fontmetrics = g.getFontMetrics(sanSerifFont);
		g.setColor(Color.black);
		g.fillRect(0,0,c.getWidth(), c.getHeight()); //clears screen
		//Drawing goes in here
		// This is a comment.
		if(forward){
				counter+=4;
		}else{
			counter-=4;
		}
		if(counter >= 251 || counter <= 3){
			forward = !forward;
		}
		if(gameState == GameState.MENU){
			g.drawImage(background,0,0,null);
			//g.setColor(new Color(50,50,50));
			//g.fillRect(0,0,c.getWidth(),c.getHeight());
			drawUI();
		}else if(gameState == GameState.GAMEPLAY){
			
			if(walls){
				drawWall();
			}
			drawScores(fontmetrics);
			if(aiPlays || mode == GameMode.TWOPLAYER_AI){
				if(!sneke.dead){
					Block snakeHead = sneke.blocks.get(0);
					drawGlowingCircle(g,snakeHead.x+snakeHead.WIDTH/2,snakeHead.y+snakeHead.WIDTH/2,5,5,70,snakeHead.primaryColor);
					sneke.draw(255-counter, 255, 0);
				}
			}
			if(!snake.dead){
				Block snakeHead = snake.blocks.get(0);
				drawGlowingCircle(g,snakeHead.x+snakeHead.WIDTH/2, snakeHead.y+snakeHead.WIDTH/2, 5, 5, 70, snakeHead.primaryColor);
				snake.draw(255-counter, 255, 0);
			}
			if(!snake2.dead && players == 2){
				Block snakeHead = snake2.blocks.get(0);
				drawGlowingCircle(g,snakeHead.x+snakeHead.WIDTH/2, snakeHead.y+snakeHead.WIDTH/2, 5, 5, 70, snakeHead.primaryColor);
				snake2.draw(255-counter, 255, 0);
			}
			drawFruit(new Color(255-counter, 255, 0));
			if(gridOn)
				drawLines(new Color(255-counter, 255, 0), new Color(0,counter,0));
			g.setColor(new Color(255, 0, 0, randomInteger(0, 255)));
			Font swagfont;
			switch (randomInteger(0,1)) {
				case 0:
					swagfont = new Font("System", Font.BOLD | Font.ITALIC, 36);
					g.setFont(swagfont);
					break;
				case 1:
					swagfont = new Font("Comic Sans MS", Font.BOLD, 36);
					g.setFont(swagfont);
			}
			if(!dlc){
				if (DLCADx == 500) DLCADx = 0;
				g.drawString("Lite version, buy DLC to remove this watermark.", DLCADx++, 440);
				g.drawString("Lite version, buy DLC to remove this watermark.", DLCADx++, 380);
				g.drawString("Lite version, buy DLC to remove this watermark.", DLCADx++, 320);
				g.drawString("Lite version, buy DLC to remove this watermark.", DLCADx++, 260);
				g.drawString("Lite version, buy DLC to remove this watermark.", DLCADx++, 200);
				// "Java sucks," - Chris 2015 (c) All Rights Reserved
			}
		}else if(gameState == GameState.GAMEOVER){
			
			Color fontColor = Color.blue;
			Color outlineColor = Color.blue;
			Color blockColor = Color.yellow;
			Color details_FontColor = Color.blue;
			Color details_BoxColor = Color.yellow;
			
			g.setFont(bigFont);
			g.setColor(fontColor);
			g.drawString("GAMEOVER", 100, 140);
			g.setFont(sanSerifFont);
			g.setColor(fontColor);
			g.drawString("Leaderboard", 200, 200);
			for(int i = 0; i<= leaderBoard.size()-1;i++){
				User user = leaderBoard.get(i);
				if(mouseY <= (207+(i*25) + 20 + 5) && mouseY > (207 + (i*25))){
					//draws the red box containing player details
					g.setColor(details_BoxColor);
					g.fillRect(350,207,120,145);
					//draw the outline
					g.setColor(outlineColor);
					g.drawRect(350,207,120,145);
					
					if(i+1 >= 2 && i+1 <= 5){
						int x[] = {330,351,351};
						int y[] = {205+2+(i*25)+10,205+2+(i*25)+20, 205+2+(i*25)+0};
						Polygon triangle = new Polygon(x,y,3);
						g.setColor(details_BoxColor);
						g.fillPolygon(triangle);
						
						g.setColor(outlineColor); //outline
						g.drawPolygon(triangle);
						g.setColor(details_BoxColor);
						g.drawLine(x[1],y[1],x[2],y[2]);
					}else if(i+1 == 1){
						int x[] = {330,351,351};
						int y[] = {207,205+2+(i*25)+20, 205+2+(i*25)+0};
						Polygon triangle = new Polygon(x,y,3);
						g.setColor(details_BoxColor);
						g.fillPolygon(triangle);
						
						g.setColor(outlineColor); //outline
						g.drawPolygon(triangle);
						g.setColor(details_BoxColor);
						g.drawLine(x[1],y[1],x[2],y[2]+1);
					}else if(i+1 == 6){
						int x[] = {330,351,351};
						int y[] = {205+2+(i*25)+20,205+2+(i*25)+20, 205+2+(i*25)+0};
						Polygon triangle = new Polygon(x,y,3);
						g.setColor(details_BoxColor);
						g.fillPolygon(triangle);
						
						g.setColor(outlineColor); //outline
						g.drawPolygon(triangle);
						g.setColor(details_BoxColor);
						g.drawLine(x[1],y[1]-1,x[2],y[2]);
					}
					
					//show player details
					g.setColor(details_FontColor);
					g.drawString(user.name, 370, 225);
					g.setFont(smallFont);
					String positionString = "" + (i+1);
					if(i+1 == 1){
						positionString+="st";
					}else if(i+1 == 2){
						positionString+="nd";
					}else if(i+1 == 3){
						positionString+="rd";
					}else{
						positionString+="th";
					}
					positionString += " Place";
					g.drawString(positionString, 360, 250);
					g.drawString("Highscore = " + user.highScore, 360, 275);
					g.drawString("Total score = " + user.totalScore, 360, 300);
					g.drawString("Sessions = " + user.playSessions, 360, 325);
					
					g.setFont(sanSerifFont);
					g.setColor(Color.black);
					
				}else{
					g.setColor(blockColor);
				}
				if(user.name.toLowerCase().equals(player1.name.toLowerCase())){
					g.setColor(new Color(150,150,0));
				}else if(user.name.toLowerCase().equals(player2.name.toLowerCase())){
					g.setColor(new Color(0,150,0));
				}
				g.fillRect(50, 205+2+(i*25), 280, 20); // box containing leaderboard elements for each player
				
				g.setColor(Color.black);
				g.fillRect(30,205+2+(i*25), 20, 20); //box around position numbers
				
				g.setColor(outlineColor);
				g.drawRect(30,205+2+(i*25), 300, 20); //box outline
				
				if(user.name.toLowerCase().equals(player1.name.toLowerCase())){
					g.setColor(Color.yellow);
				}else if(user.name.toLowerCase().equals(player2.name.toLowerCase())){
					g.setColor(Color.green);
				}else{
					g.setColor(fontColor);
				}
				g.drawString(i+1 + "", 33, 225 + (i*25)); //leaderboard position numbers
				g.drawString(user.name, 55, 225 + (i*25)); //users
				g.drawString(user.highScore + " ", 330-fontmetrics.stringWidth("" + user.highScore), 225 + (i*25)); //.. and their scores
			}
			g.setColor(Color.yellow);
			g.fillRect(150-5,425+5,fontmetrics.stringWidth("Press space to restart") + 10,25);
			g.setColor(Color.blue);
			g.drawString("Press space to restart", 150, 450);
		}
		g.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void drawScores(FontMetrics fontmetrics){
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		int fontHeight = fontmetrics.getHeight();
		int fontWidth = fontmetrics.stringWidth(player1.name);
		int fontWidth2 = fontmetrics.stringWidth(player2.name);
		g.setFont(sanSerifFont);
		g.setColor(player1Color);
		if(snake.dead){
			g.drawString(player1.name + " - dead", 0, 17);
		}else{
			g.drawString(player1.name, 0, 17);
		}
		for(int i = 0; i <= 100; i++){
			g.setColor(new Color(255,255,255-i));
			g.fillRect(i,20,10,20);
		}
		g.setColor(Color.black);
		g.drawString(player1.score + " ", 0,37);
		if(players == 2){
			g.setColor(player2Color);
			if(snake2.dead){
				fontWidth2 = fontmetrics.stringWidth(player2.name + " - dead");
				g.drawString(player2.name + " - dead", c.getWidth()-fontWidth2, 17);
			}else{
				g.drawString(player2.name, c.getWidth()-fontWidth2, 17);
			}
			for(int i = 0; i <= 100; i++){
				g.setColor(new Color(255-i,255,255-i));
				g.fillRect(c.getWidth()-i,20,10,20);
			}
			g.setColor(Color.black);
			g.drawString(player2.score + "", c.getWidth()-100,37);
		}
		if((players == 1 && aiPlays) || mode == GameMode.TWOPLAYER_AI){
			g.setColor(aiColor);
			if(sneke.dead){
				fontWidth2 = fontmetrics.stringWidth(ai.name + " - dead");
				g.drawString(ai.name + " - dead", c.getWidth()-fontWidth2, 17);
			}else{
				g.drawString(ai.name, c.getWidth()-fontWidth2, 17);
			}
			for(int i = 0; i <= 100; i++){
				g.setColor(new Color(255-i,255,255-i));
				g.fillRect(c.getWidth()-i,20,10,20);
			}
			g.setColor(Color.black);
			g.drawString(ai.score + "", c.getWidth()-100,37);
		}
	}
	
	/*public void snekeLogic(){
		Block snekeHead = sneke.blocks.get(0);
		/*
		if not snekeHead.left contain.Collider left = true
		if not ..... right
		if not .. up
		if not ... down
		
		pri list
		
		one = ''
		two = ''
		three = ''
		four = ''
		
		collider values:
			0 = free
			1 = wall
			2 = self
			3 = fruit
		String one = "RIGHT";
		String two = "UP";
		String three = "LEFT";
		String four = "DOWN";
		*/
		/*final int C_FREE = 0;
		final int C_WALL = 1;
		final int C_SELF = 2;
		final int C_FRUIT = 3;
		
		boolean moved = true;
		Priority one = new Priority("RIGHT", 0);
		Priority two = new Priority("UP", 0);
		Priority three = new Priority("LEFT", 0);
		Priority four = new Priority("DOWN", 0);
		boolean left, right, up, down = false;
		sneke.collisionDetection();
		//sneke.currentDirection = "RIGHT";
		Block fruit = fruitArray.get(0);
		for(int i = 0; i<= wallBlocks.size()-1; i++){
			Block wallBlock = wallBlocks.get(i);
			//if(sneke.currentDirection == "LEFT"){
				if(snekeHead.x >= wallBlock.x && snekeHead.x <= wallBlock.x + wallBlock.WIDTH){
					three.collider = C_WALL;
					// System.out.println("WALL");
				}else if(snekeHead.x >= fruit.x && snekeHead.x <= fruit.x + fruit.WIDTH){
					three.collider = C_FRUIT;
					// System.out.println("FRUIT");
				}else{
					boolean successFlag = false;
					for(int j = 1; j<= sneke.blocks.size()-1; j++){
						Block bodyBlocks = sneke.blocks.get(j);
						if(snekeHead.x >= bodyBlocks.x && snekeHead.x <= bodyBlocks.x + bodyBlocks.WIDTH){
							three.collider = C_SELF;
							successFlag = true;
						}
					}
					if(!successFlag){
						three.collider = C_FREE;
						// System.out.println("FREE");
					}
				}
			//}else if(sneke.currentDirection == "RIGHT"){
				if(snekeHead.x <= wallBlock.x && snekeHead.x >= wallBlock.x - wallBlock.WIDTH){
					one.collider = C_WALL;
					// System.out.println("WALL");
				}else if(snekeHead.x <= fruit.x && snekeHead.x >= fruit.x - fruit.WIDTH){
					one.collider = C_FRUIT;
					// System.out.println("FRUIT");
				}else{
					boolean successFlag = false;
					for(int j = 1; j<= sneke.blocks.size()-1; j++){
						Block bodyBlocks = sneke.blocks.get(j);
						if(snekeHead.x <= bodyBlocks.x && snekeHead.x >= bodyBlocks.x - bodyBlocks.WIDTH){
							one.collider = C_SELF;
							successFlag = true;
						}
					}
					if(!successFlag){
						one.collider = C_FREE;
						// System.out.println("FREE");
					}
				}
			//}else if(sneke.currentDirection == "DOWN"){
				if(snekeHead.y >= wallBlock.y && snekeHead.y <= wallBlock.y + wallBlock.HEIGHT){
					four.collider = C_WALL;
					// System.out.println("WALL");
				}else if(snekeHead.y >= fruit.x && snekeHead.y <= fruit.y + fruit.HEIGHT){
					four.collider = C_FRUIT;
					// System.out.println("FRUIT");
				}else{
					boolean successFlag = false;
					for(int j = 1; j<= sneke.blocks.size()-1; j++){
						Block bodyBlocks = sneke.blocks.get(j);
						if(snekeHead.y >= bodyBlocks.y && snekeHead.y <= bodyBlocks.y + bodyBlocks.HEIGHT){
							four.collider = C_SELF;
							successFlag = true;
						}
					}
					if(!successFlag){
						four.collider = C_FREE;
						// System.out.println("FREE");
					}
				}
			// }else if(sneke.currentDirection == "UP"){
				if(snekeHead.y <= wallBlock.y && snekeHead.y >= wallBlock.y - wallBlock.HEIGHT){
					two.collider = C_WALL;
					// System.out.println("WALL");
				}else if(snekeHead.y <= fruit.x && snekeHead.y >= fruit.y - fruit.HEIGHT){
					two.collider = C_FRUIT;
					// System.out.println("FRUIT");
				}else{
					boolean successFlag = false;
					for(int j = 1; j<= sneke.blocks.size()-1; j++){
						Block bodyBlocks = sneke.blocks.get(j);
						if(snekeHead.y <= bodyBlocks.y && snekeHead.y >= bodyBlocks.y - bodyBlocks.HEIGHT){
							two.collider = C_SELF;
							successFlag = true;
						}
					}
					if(!successFlag){
						two.collider = C_FREE;
						// System.out.println("FREE");
					}
				}
			// }
		}
		/*if(one.collider == C_FRUIT){
			sneke.currentDirection = one.direction;
		}else if(two.collider == C_FRUIT){
			sneke.currentDirection = two.direction;
		}else if(three.collider == C_FRUIT){
			sneke.currentDirection = three.direction;
		}else if(four.collider == C_FRUIT){
			sneke.currentDirection = four.direction;
		}else{
			moved = false;
		}
		if(!moved){
			moved = true;
			if(!(one.collider == C_WALL)){
				sneke.currentDirection = one.direction;
			}else if(!(two.collider == C_WALL)){
				sneke.currentDirection = two.direction;
			}else if(!(three.collider == C_WALL)){
				sneke.currentDirection = three.direction;
			}else if(!(four.collider == C_WALL)){
				sneke.currentDirection = four.direction;
			}else{
				moved = false;
				
			}
		}
		if(!moved){
			moved = true;
			if(!(one.collider == C_SELF)){
				sneke.currentDirection = one.direction;
			}else if(!(two.collider == C_SELF)){
				sneke.currentDirection = two.direction;
			}else if(!(three.collider == C_SELF)){
				sneke.currentDirection = three.direction;
			}else if(!(four.collider == C_SELF)){
				sneke.currentDirection = four.direction;
			}else{
				moved = false;
			}
		}
		/*if(!moved){
			moved = true;
			if(one.collider == C_FREE){
				sneke.currentDirection = one.direction;
			}else if(two.collider == C_FREE){
				sneke.currentDirection = two.direction;
			}else if(three.collider == C_FREE){
				sneke.currentDirection = three.direction;
			}else if(four.collider == C_FREE){
				sneke.currentDirection = four.direction;
			}else{
				moved = false;
			}
		}*/
		/*if(one.collider == C_FREE){
			sneke.currentDirection = one.direction;
		}else if(two.collider == C_FREE){
			sneke.currentDirection = two.direction;
		}else if(three.collider == C_FREE){
			sneke.currentDirection = three.direction;
		}else if(four.collider == C_FREE){
			sneke.currentDirection = four.direction;
		}
	}
	
	class Priority{
		String direction;
		int collider;
		public Priority(String direction, int collider){
			this.direction = direction;
			this.collider = collider;
		}
	}*/
	
	public void snekeLogic(){
		Block snekeHead = sneke.blocks.get(0);
		Block fruit = fruitArray.get(0);
		String snekeXdirection = "";
		String snekeYdirection = "";
		if(sneke.currentDirection == "UP" || sneke.currentDirection == "DOWN"){
			snekeYdirection = sneke.currentDirection;
		}else if(sneke.currentDirection == "LEFT" || sneke.currentDirection == "RIGHT"){
			snekeXdirection = sneke.currentDirection;
		}
		
		boolean isRightFree = true;
		boolean isLeftFree = true;
		boolean isUpFree = true;
		boolean isDownFree = true;
		
		boolean bodyBlockRight = false;
		boolean bodyBlockLeft = false;
		boolean bodyBlockUp = false;
		boolean bodyBlockDown = false;
		
		for(int i = 1; i<= sneke.blocks.size()-1;i++){
			Block snekeBody = sneke.blocks.get(i);
			if(snekeHead.x == snekeBody.x-snekeBody.WIDTH && snekeHead.y == snekeBody.y){
				isRightFree = false;
			}
			if(snekeHead.x == snekeBody.x+snekeBody.WIDTH && snekeHead.y == snekeBody.y){
				isLeftFree = false;
			}
			if(snekeHead.y == snekeBody.y-snekeBody.HEIGHT && snekeHead.x == snekeBody.x){
				isDownFree = false;
			}
			if(snekeHead.y == snekeBody.y+snekeBody.HEIGHT && snekeHead.x == snekeBody.x){
				isUpFree = false;
			}
			if(snekeBody.y > snekeHead.y){
				bodyBlockDown = true;
			}
			if(snekeBody.y < snekeHead.y){
				bodyBlockUp = true;
			}
			if(snekeBody.x > snekeHead.x){
				bodyBlockRight = true;
			}
			if(snekeBody.x < snekeHead.x){
				bodyBlockLeft = true;
			}
		}
		
		boolean skip = false;
		if(fruit.x > snekeHead.x && sneke.currentDirection != "LEFT" && snekeXdirection != "LEFT"){
			if(isRightFree){
				sneke.currentDirection = "RIGHT";
				snekeXdirection = "RIGHT";
			}else{
				if(isUpFree && !bodyBlockUp){
					sneke.currentDirection = "UP";
					snekeYdirection = "UP";
				}else if(isDownFree && !bodyBlockDown){
					sneke.currentDirection = "DOWN";
					snekeYdirection = "DOWN";
				}else if(isLeftFree && !bodyBlockLeft){
					sneke.currentDirection = "LEFT";
					snekeXdirection = "LEFT";
				}else if(isUpFree){
					sneke.currentDirection = "UP";
					snekeYdirection = "UP";
				}else if(isDownFree){
					sneke.currentDirection = "DOWN";
					snekeYdirection = "DOWN";
				}else if(isLeftFree){
					sneke.currentDirection = "LEFT";
					snekeXdirection = "LEFT";
				}
			}
		}else if(fruit.x < snekeHead.x && sneke.currentDirection != "RIGHT" && snekeXdirection != "RIGHT"){
			if(isLeftFree){
				sneke.currentDirection = "LEFT";
				snekeXdirection = "LEFT";
			}else{
				if(isUpFree && !bodyBlockUp){
					sneke.currentDirection = "UP";
					snekeYdirection = "UP";
				}else if(isDownFree && !bodyBlockDown){
					sneke.currentDirection = "DOWN";
					snekeYdirection = "DOWN";
				}else if(isRightFree && !bodyBlockRight){
					sneke.currentDirection = "RIGHT";
					snekeXdirection = "RIGHT";
				}else if(isUpFree){
					sneke.currentDirection = "UP";
					snekeYdirection = "UP";
				}else if(isDownFree){
					sneke.currentDirection = "DOWN";
					snekeYdirection = "DOWN";
				}else if(isRightFree){
					sneke.currentDirection = "RIGHT";
					snekeXdirection = "RIGHT";
				}
			}
		}
		if(fruit.y > snekeHead.y && sneke.currentDirection != "UP" && snekeYdirection != "UP"){
				if(isDownFree){
					sneke.currentDirection = "DOWN";
					snekeYdirection = "DOWN";
				}else{
					if(isLeftFree && !bodyBlockLeft){
						sneke.currentDirection = "LEFT";
						snekeXdirection = "LEFT";
					}else if(isRightFree && !bodyBlockRight){
						sneke.currentDirection = "RIGHT";
						snekeXdirection = "RIGHT";
					}if(isUpFree && !bodyBlockUp){
						sneke.currentDirection = "UP";
						snekeYdirection = "UP";
					}else if(isLeftFree){
						sneke.currentDirection = "LEFT";
						snekeXdirection = "LEFT";
					}else if(isRightFree){
						sneke.currentDirection = "RIGHT";
						snekeXdirection = "RIGHT";
					}else if(isUpFree){
						sneke.currentDirection = "UP";
						snekeYdirection = "UP";
					}
				}
			}else if(fruit.y < snekeHead.y && sneke.currentDirection != "DOWN" && snekeYdirection != "DOWN"){
				if(isUpFree){
					sneke.currentDirection = "UP";
					snekeYdirection = "UP";
				}else{
					if(isLeftFree && !bodyBlockLeft){
						sneke.currentDirection = "LEFT";
						snekeXdirection = "LEFT";
					}else if(isRightFree && !bodyBlockRight){
						sneke.currentDirection = "RIGHT";
						snekeXdirection = "RIGHT";
					}if(isDownFree && !bodyBlockDown){
						sneke.currentDirection = "DOWN";
						snekeYdirection = "DOWN";
					}else if(isLeftFree){
						sneke.currentDirection = "LEFT";
						snekeXdirection = "LEFT";
					}else if(isRightFree){
						sneke.currentDirection = "RIGHT";
						snekeXdirection = "RIGHT";
					}else if(isDownFree){
						sneke.currentDirection = "DOWN";
						snekeYdirection = "DOWN";
					}
				}
		}
		
		sneke.update();
	} 
	
	
	
	public void updateGame(){
		if(gameState == GameState.MENU){
			updateUI();
		}else if(gameState == GameState.GAMEPLAY){
			if(mode == GameMode.ONEPLAYER){
				if(snake.collisionDetection()){
					if(!isUpdated)
						snake.update();
					isUpdated = false;
				}else{
					snake.dead = true;
					gameState = GameState.GAMEOVER;
					checkUserWithLeaderboard(player1);
					saveData("save.data");
				}
			}else if(mode == GameMode.TWOPLAYER_AI){
				if(snake.collisionDetection()){
					if(!isUpdated)
						snake.update();
					isUpdated = false;
				}else{
					snake.dead = true;
				}
				if(sneke.collisionDetection()){
					snekeLogic();
				}else{
					sneke.dead = true;
				}
				if(snake.dead && sneke.dead){
					gameState = GameState.GAMEOVER;
					checkUserWithLeaderboard(player1);
					saveData("save.data");
				}
			}else if(mode == GameMode.TWO_PLAYER_LAN_HOST){
				try{
					if(lanInput.ready()){
						String message = lanInput.readLine();
						//System.out.println(message);
						if(message.contains("UP")){
							snake2.move("UP",true);
							//System.out.println("moved up");
						}else if(message.contains("DOWN")){
							snake2.move("DOWN",true);
						}else if(message.contains("LEFT")){
							snake2.move("LEFT",true);
						}else if(message.contains("RIGHT")){
							snake2.move("RIGHT",true);
						}
					}
				}catch(Exception e){
					
				}
				snake2.collisionDetection();
				snake.collisionDetection();
				if(!isUpdated){
					snake.move(snake.currentDirection,true);
					lanOutput.print(snake.currentDirection+"\n");
					lanOutput.flush();
				}
				isUpdated = false;
			}else if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
				try{
					if(lanInput.ready()){
						String message = lanInput.readLine();
						//System.out.println(message);
						if(message.contains("UP")){
							snake.move("UP",true);
							//System.out.println("moved up");
						}else if(message.contains("DOWN")){
							snake.move("DOWN",true);
						}else if(message.contains("LEFT")){
							snake.move("LEFT",true);
						}else if(message.contains("RIGHT")){
							snake.move("RIGHT",true);
						}
					}
				}catch(Exception e){
					
				}
				snake2.collisionDetection();
				snake.collisionDetection();
				if(!isUpdated2){
					snake2.move(snake2.currentDirection,true);
					lanOutput.print(snake2.currentDirection+"\n");
					lanOutput.flush();
				}
				isUpdated2 = false;
				// if(snake.collisionDetection()){
					// if(!isUpdated){
						// snake.update();
					// }
					// isUpdated = false;
				// }else{
					// snake.dead = true;
				// }
				// /*
				// if(snake2.collisionDetection()){
					// if(!isUpdated2)
						// snake2.update();
					// isUpdated2 = false;
				// }else{
					// snake2.dead = true;
				// }
				// if(snake.dead && snake2.dead){
					// gameState = GameState.GAMEOVER;
					// checkUserWithLeaderboard(player1);
					// checkUserWithLeaderboard(player2);
					// saveData("save.data");
				// }*/
				// try{
					// if(lanInput.ready())
						// lanMessage = lanInput.readLine();
					//System.out.println("lan message = " + lanMessage);
				// }catch(Exception e){
					
				// }
				// if(lanMessage.contains("LEFT") && lanMessage.contains("SNAKE2")){
				// if(lanMessage.contains("x=") && lanMessage.contains("y=") ){
					// int lanMessageX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y")));
					// int lanMessageY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
					// if(!updatedLanLeft2){
						// updatedLanLeft2 = true;
						// snake2.blocks.get(0).x = lanMessageX;
						// snake2.blocks.get(0).y = lanMessageY;
						// snake2.pushSnake();
					// }else{
						// snake2.move("LEFT",true);
					// }
				// }else{
					// snake2.move("LEFT",true);
				// }
				// updatedLanUp2 = false;
				// updatedLanDown2 = false;
				// updatedLanRight2 = false;
			// }else if(lanMessage.contains("RIGHT") && lanMessage.contains("SNAKE2")){
			// if(lanMessage.contains("x=") && lanMessage.contains("y=")){
					// int lanMessageX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y")));
					// int lanMessageY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
					// if(!updatedLanRight2){
						// updatedLanRight2 = true;
						// snake2.blocks.get(0).x = lanMessageX;
						// snake2.blocks.get(0).y = lanMessageY;
						// snake2.pushSnake();
					// }else{
						// snake2.move("RIGHT",true);
					// }
				// }else{
					// snake2.move("RIGHT",true);
				// }
				// updatedLanUp2 = false;
				// updatedLanDown2 = false;
				// updatedLanLeft2 = false;
			// }else if(lanMessage.contains("UP") && lanMessage.contains("SNAKE2")){
				// if(lanMessage.contains("x=") && lanMessage.contains("y=")){
					// int lanMessageX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y")));
					// int lanMessageY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
					// if(!updatedLanUp2){
						// updatedLanUp2 = true;
						// snake2.blocks.get(0).x = lanMessageX;
						// snake2.blocks.get(0).y = lanMessageY;
						// snake2.pushSnake();
					// }else{
						// snake2.move("UP",true);
					// }
				// }else{
					// snake2.move("UP",true);
				// }
				// updatedLanLeft2 = false;
				// updatedLanDown2 = false;
				// updatedLanRight2 = false;
			// }else if(lanMessage.contains("DOWN") && lanMessage.contains("SNAKE2")){
				// if(lanMessage.contains("x=") && lanMessage.contains("y=") ){
					// int lanMessageX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y")));
					// int lanMessageY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
					// if(!updatedLanDown2){
						// updatedLanDown2 = true;
						// snake2.blocks.get(0).x = lanMessageX;
						// snake2.blocks.get(0).y = lanMessageY;
						// snake2.pushSnake();
					// }else{
						// snake2.move("DOWN",true);
					// }
				// }else{
					// snake2.move("DOWN",true);
				// }
				// updatedLanUp2 = false;
				// updatedLanLeft2 = false;
				// updatedLanRight2 = false;
			// }
				
			// }else if(mode == GameMode.TWO_PLAYER_LAN_CLIENT){
				// if(snake2.collisionDetection()){
					// if(!isUpdated2)
						// snake2.update();
					// isUpdated2 = false;
				// }else{
					// snake2.dead = true;
				// }
				// snake.checkFruit();
				// try{
					// if(lanInput.ready())
						// lanMessage = lanInput.readLine();
					// System.out.println("lan message = " + lanMessage);
				// }catch(Exception e){
					
				// }
				// if(lanMessage.contains("fruit")){
					// int fruitX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y=")-1));
					// int fruitY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
					// spawnFruit(fruitX, fruitY);
				// }
				// if(lanMessage.contains("LEFT")){
					// if(lanMessage.contains("x=") && lanMessage.contains("y=") ){
						// int lanMessageX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y")));
						// int lanMessageY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
						// if(!updatedLanLeft){
							// updatedLanLeft = true;
							// snake.blocks.get(0).x = lanMessageX;
							// snake.blocks.get(0).y = lanMessageY;
							// snake.pushSnake();
						// }else{
							// snake.move("LEFT",true);
						// }
					// }else{
						// snake.move("LEFT",true);
					// }
					// updatedLanRight = false;
					// updatedLanUp = false;
					// updatedLanDown = false;
				// }else if(lanMessage.contains("RIGHT")){
					// if(lanMessage.contains("x=") && lanMessage.contains("y=") ){
						// int lanMessageX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y")));
						// int lanMessageY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
						// if(!updatedLanRight){
							// updatedLanRight = true;
							// snake.blocks.get(0).x = lanMessageX;
							// snake.blocks.get(0).y = lanMessageY;
							// snake.pushSnake();
						// }else{
							// snake.move("RIGHT",true);
						// }
					// }else{
						// snake.move("RIGHT",true);
					// }
					// updatedLanLeft = false;
					// updatedLanUp = false;
					// updatedLanDown = false;
				// }else if(lanMessage.contains("UP")){
					// if(lanMessage.contains("x=") && lanMessage.contains("y=") ){
						// int lanMessageX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y")));
						// int lanMessageY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
						// if(!updatedLanUp){
							// updatedLanUp = true;
							// snake.blocks.get(0).x = lanMessageX;
							// snake.blocks.get(0).y = lanMessageY;
							// snake.pushSnake();
						// }else{
							// snake.move("UP",true);
						// }
					// }else{
						// snake.move("UP",true);
					// }
					// updatedLanDown = false;
					// updatedLanLeft = false;
					// updatedLanRight = false;
				// }else if(lanMessage.contains("DOWN")){
					// if(lanMessage.contains("x=") && lanMessage.contains("y=") ){
						// int lanMessageX = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("x=")+2, lanMessage.indexOf("y")));
						// int lanMessageY = Integer.parseInt(lanMessage.substring(lanMessage.indexOf("y=")+2));
						// if(!updatedLanDown){
							// updatedLanDown = true;
							// snake.blocks.get(0).x = lanMessageX;
							// snake.blocks.get(0).y = lanMessageY;
							// snake.pushSnake();
						// }else{
							// snake.move("DOWN",true);
						// }
					// }else{
						// snake.move("DOWN",true);
					// }
					// updatedLanUp = false;
					// updatedLanLeft = false;
					// updatedLanRight = false;
				// }
			}else if(mode == GameMode.TWOPLAYER_LOCAL){
				if(snake.collisionDetection()){
					if(!isUpdated)
						snake.update();
					isUpdated = false;
				}else{
					snake.dead = true;
				}
				if(snake2.collisionDetection()){
					if(!isUpdated2)
						snake2.update();
					isUpdated2 = false;
				}else{
					snake2.dead = true;
				}
				if(snake.dead && snake2.dead){
					gameState = GameState.GAMEOVER;
					checkUserWithLeaderboard(player1);
					checkUserWithLeaderboard(player2);
					saveData("save.data");
				}
			}
		}else if(gameState == GameState.GAMEOVER){
			
		}
	}
	
	public void checkUserWithLeaderboard(User toCheck){
		toCheck.playSessions++;
		System.out.println("Totalscore = " + toCheck.totalScore + " + " + toCheck.score + " = " + (toCheck.totalScore + toCheck.score));
		if(toCheck.score > leaderBoard.get(leaderBoard.size()-1).highScore){
			boolean highScoreBeaten = false;
			if(toCheck.highScore < toCheck.score){
				highScoreBeaten = true;
				toCheck.highScore = toCheck.score;
				System.out.println("New highScore");
			}
			boolean flag1 = false;
			updateUserPositions();
			for(int i = 0; i <= leaderBoard.size()-1; i++){
				User user = leaderBoard.get(i);
				if(toCheck.score > user.score && !flag1){
					if(toCheck.position != -1 && toCheck.position <= 6){ //if user is already on the leaderboard
						if(!highScoreBeaten){ //if score is lower
							//do nothing
						}else{ //if new highscore
							toCheck.position = i+1;
						}
						flag1 = true;
					}else{
						toCheck.position = i+1;
						leaderBoard.add(i, toCheck);
						//i--;
						flag1 = true;
						leaderBoard.get(leaderBoard.size()-1).position = leaderBoard.size()+1;
						leaderBoard.remove(leaderBoard.size()-1);
					}
				}
				if(flag1)
					break;
			}
			Collections.sort(leaderBoard);
			Collections.reverse(leaderBoard);
			updateUserPositions();
		}
	}
	
	public void updateUserPositions(){
		for(int i = 0; i<= users.size()-1; i++){
			User user = users.get(i);
			user.position = i+1;
		}
	}
	
	public void updateUI(){
		for(int i = 0; i <= uiElements.size()-1; i++){
			UIelement uielement = uiElements.get(i);
			if(checkMouse(uielement.x, uielement.y-uielement.h,uielement.w,uielement.h)){
				if(!uielement.activated){
					uielement.hovered = true;
				}
			}else{
				uielement.hovered = false;
			}
		}
		if(uiElements.size() >= 6){
			UIelement ui_newGame = uiElements.get(UI_NEWGAME);
			UIelement ui_newGame_1player = uiElements.get(UI_NEWGAME_1PLAYER);
			UIelement ui_newGame_2player = uiElements.get(UI_NEWGAME_2PLAYER);
			UIelement ui_newGame_2player_local = uiElements.get(UI_NEWGAME_2PLAYER_LOCAL);
			UIelement ui_newGame_2player_ai = uiElements.get(UI_NEWGAME_2PLAYER_AI);
			UIelement ui_newGame_2player_lan = uiElements.get(UI_NEWGAME_2PLAYER_LAN);
			UIelement ui_leaderBoard = uiElements.get(UI_LEADERBOARD);
			UIelement ui_options = uiElements.get(UI_OPTIONS);
			UIelement ui_options_vsync = uiElements.get(UI_OPTIONS_VSYNC);
			UIelement ui_options_vsync_on = uiElements.get(UI_OPTIONS_VSYNC_ON);
			UIelement ui_options_vsync_off = uiElements.get(UI_OPTIONS_VSYNC_OFF);
			UIelement ui_options_fps = uiElements.get(UI_OPTIONS_FPS);
			UIelement ui_options_fps_10 = uiElements.get(UI_OPTIONS_FPS_10);
			UIelement ui_options_fps_20 = uiElements.get(UI_OPTIONS_FPS_20);
			UIelement ui_options_fps_30 = uiElements.get(UI_OPTIONS_FPS_30);
			UIelement ui_options_fps_40 = uiElements.get(UI_OPTIONS_FPS_40);
			UIelement ui_options_player1color = uiElements.get(UI_OPTIONS_PLAYER1COLOR);
			UIelement ui_options_player1color_red = uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED);
			UIelement ui_options_player1color_green = uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN);
			UIelement ui_options_player1color_blue = uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE);
			UIelement ui_options_player1color_yellow = uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW);
			UIelement ui_options_player2color = uiElements.get(UI_OPTIONS_PLAYER2COLOR);
			UIelement ui_options_player2color_red = uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED);
			UIelement ui_options_player2color_green = uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN);
			UIelement ui_options_player2color_blue = uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE);
			UIelement ui_options_player2color_yellow = uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW);
			UIelement ui_options_aicolor = uiElements.get(UI_OPTIONS_AICOLOR);
			UIelement ui_options_aicolor_red = uiElements.get(UI_OPTIONS_AICOLOR_RED);
			UIelement ui_options_aicolor_green = uiElements.get(UI_OPTIONS_AICOLOR_GREEN);
			UIelement ui_options_aicolor_blue = uiElements.get(UI_OPTIONS_AICOLOR_BLUE);
			UIelement ui_options_aicolor_yellow = uiElements.get(UI_OPTIONS_AICOLOR_YELLOW);
			UIelement ui_exit = uiElements.get(UI_EXIT);
			if(menuState == MENUSTATE.DEFAULT){
				ui_newGame_1player.show = false;
				ui_newGame_2player.show = false;
				ui_options_vsync.show = false;
				ui_options_fps.show = false;
				ui_options_player1color.show = false;
				ui_options_player2color.show = false;
				ui_options_aicolor.show = false;
				if(ui_newGame.hovered){
					ui_newGame_1player.partShow = true;
					ui_newGame_2player.partShow = true;
					//drawConnection(ui_newGame, ui_newGame_1player, ui_newGame_2player);
				}else{
					ui_newGame_1player.partShow = false;
					ui_newGame_2player.partShow = false;
				}
				if(ui_options.hovered){
					ui_options_vsync.partShow = true;
					ui_options_fps.partShow = true;
					ui_options_player1color.partShow = true;
					ui_options_player2color.partShow = true;
					ui_options_aicolor.partShow = true;
				}else{
					ui_options_vsync.partShow = false;
					ui_options_fps.partShow = false;
					ui_options_player1color.partShow = false;
					ui_options_player2color.partShow = false;
					ui_options_aicolor.partShow = false;
				}
			}else if(menuState == MENUSTATE.NEWGAME){
				ui_newGame_1player.show = true;
				ui_newGame_1player.partShow = false;
				ui_newGame_2player.show = true;
				ui_newGame_2player.partShow = false;
				
				if(ui_newGame_2player.hovered){
					ui_newGame_2player_local.partShow = true;
					ui_newGame_2player_local.show = false;
					ui_newGame_2player_ai.partShow = true;
					ui_newGame_2player_ai.show = false;
					ui_newGame_2player_lan.partShow = true;
					ui_newGame_2player_lan.show = false;
				}else{
					ui_newGame_2player_local.partShow = false;
					ui_newGame_2player_local.show = false;
					ui_newGame_2player_ai.partShow = false;
					ui_newGame_2player_ai.show = false;
					ui_newGame_2player_lan.partShow = false;
					ui_newGame_2player_lan.show = false;
				}
				
				ui_newGame.partShow();
				ui_options.partShow();
				ui_leaderBoard.partShow();
				ui_exit.partShow();
			}else if(menuState == MENUSTATE.NEWGAME_1PLAYER){
				
			}else if(menuState == MENUSTATE.NEWGAME_2PLAYER){
				ui_newGame.partShow = false;
				ui_newGame.partPartShow = true;
				ui_leaderBoard.partShow = false;
				ui_leaderBoard.partPartShow = true;
				ui_options.partShow = false;
				ui_options.partPartShow = true;
				ui_exit.partShow = false;
				ui_exit.partPartShow = true;
				ui_newGame_1player.partShow();
				ui_newGame_2player.partShow();
				ui_newGame_2player_local.show = true;
				ui_newGame_2player_ai.show = true;
				ui_newGame_2player_lan.show = true;
			}else if(menuState == MENUSTATE.LEADERBOARD){
				ui_newGame.partShow();
				ui_options.partShow();
				ui_leaderBoard.partShow();
				ui_exit.partShow();
			}else if(menuState == MENUSTATE.OPTIONS){
				ui_options_vsync.partShow = false;
				ui_options_vsync.show = true;
				ui_options_fps.partShow = false;
				ui_options_fps.show = true;
				ui_options_player1color.partShow = false;
				ui_options_player1color.show = true;
				ui_options_player2color.partShow = false;
				ui_options_player2color.show = true;
				ui_options_aicolor.partShow = false;
				ui_options_aicolor.show = true;
				
				if(ui_options_vsync.hovered){
					ui_options_vsync_on.partShow = true;
					ui_options_vsync_on.show = false;
					ui_options_vsync_off.partShow = true;
					ui_options_vsync_off.show = false;
				}else{
					ui_options_vsync_on.partShow = false;
					ui_options_vsync_on.show = false;
					ui_options_vsync_off.partShow = false;
					ui_options_vsync_off.show = false;
				}
				if(ui_options_fps.hovered){
					ui_options_fps_10.partShow = true;
					ui_options_fps_10.show = false;
					ui_options_fps_20.partShow = true;
					ui_options_fps_20.show = false;
					ui_options_fps_30.partShow = true;
					ui_options_fps_30.show = false;
					ui_options_fps_40.partShow = true;
					ui_options_fps_40.show = false;
				}else{
					ui_options_fps_10.partShow = false;
					ui_options_fps_10.show = false;
					ui_options_fps_20.partShow = false;
					ui_options_fps_20.show = false;
					ui_options_fps_30.partShow = false;
					ui_options_fps_30.show = false;
					ui_options_fps_40.partShow = false;
					ui_options_fps_40.show = false;
				}
				if(ui_options_player1color.hovered){
					ui_options_player1color_red.partShow = true;
					ui_options_player1color_red.show = false;
					ui_options_player1color_green.partShow = true;
					ui_options_player1color_green.show = false;
					ui_options_player1color_blue.partShow = true;
					ui_options_player1color_blue.show = false;
					ui_options_player1color_yellow.partShow = true;
					ui_options_player1color_yellow.show = false;
				}else{
					ui_options_player1color_red.partShow = false;
					ui_options_player1color_red.show = false;
					ui_options_player1color_green.partShow = false;
					ui_options_player1color_green.show = false;
					ui_options_player1color_blue.partShow = false;
					ui_options_player1color_blue.show = false;
					ui_options_player1color_yellow.partShow = false;
					ui_options_player1color_yellow.show = false;
				}
				if(ui_options_player2color.hovered){
					ui_options_player2color_red.partShow = true;
					ui_options_player2color_red.show = false;
					ui_options_player2color_green.partShow = true;
					ui_options_player2color_green.show = false;
					ui_options_player2color_blue.partShow = true;
					ui_options_player2color_blue.show = false;
					ui_options_player2color_yellow.partShow = true;
					ui_options_player2color_yellow.show = false;
				}else{
					ui_options_player2color_red.partShow = false;
					ui_options_player2color_red.show = false;
					ui_options_player2color_green.partShow = false;
					ui_options_player2color_green.show = false;
					ui_options_player2color_blue.partShow = false;
					ui_options_player2color_blue.show = false;
					ui_options_player2color_yellow.partShow = false;
					ui_options_player2color_yellow.show = false;
				}
				if(ui_options_aicolor.hovered){
					ui_options_aicolor_red.partShow = true;
					ui_options_aicolor_red.show = false;
					ui_options_aicolor_green.partShow = true;
					ui_options_aicolor_green.show = false;
					ui_options_aicolor_blue.partShow = true;
					ui_options_aicolor_blue.show = false;
					ui_options_aicolor_yellow.partShow = true;
					ui_options_aicolor_yellow.show = false;
				}else{
					ui_options_aicolor_red.partShow = false;
					ui_options_aicolor_red.show = false;
					ui_options_aicolor_green.partShow = false;
					ui_options_aicolor_green.show = false;
					ui_options_aicolor_blue.partShow = false;
					ui_options_aicolor_blue.show = false;
					ui_options_aicolor_yellow.partShow = false;
					ui_options_aicolor_yellow.show = false;
				}
				ui_newGame.partShow();
				ui_options.partShow();
				ui_leaderBoard.partShow();
				ui_exit.partShow();
			}else if(menuState == MENUSTATE.OPTIONS_VSYNC){
				ui_newGame.partPartShow = true;
				ui_newGame.partShow = false;
				ui_options.partPartShow = true;
				ui_options.partShow = false;
				ui_leaderBoard.partPartShow = true;
				ui_leaderBoard.partShow = false;
				ui_exit.partPartShow = true;
				ui_exit.partShow = false;
				ui_options_vsync.partShow();
				ui_options_fps.partShow();
				ui_options_player1color.partShow();
				ui_options_player2color.partShow();
				ui_options_aicolor.partShow();
				ui_options_vsync_on.show = true;
				ui_options_vsync_off.show = true;
			}else if(menuState == MENUSTATE.OPTIONS_FPS){
				ui_newGame.partPartShow = true;
				ui_newGame.partShow = false;
				ui_options.partPartShow = true;
				ui_options.partShow = false;
				ui_leaderBoard.partPartShow = true;
				ui_leaderBoard.partShow = false;
				ui_exit.partPartShow = true;
				ui_exit.partShow = false;
				ui_options_vsync.partShow();
				ui_options_fps.partShow();
				ui_options_player1color.partShow();
				ui_options_player2color.partShow();
				ui_options_aicolor.partShow();
				ui_options_fps_10.show = true;
				ui_options_fps_20.show = true;
				ui_options_fps_30.show = true;
				ui_options_fps_40.show = true;
			}else if(menuState == MENUSTATE.OPTIONS_PLAYER1COLOR){
				ui_newGame.partPartShow = true;
				ui_newGame.partShow = false;
				ui_options.partPartShow = true;
				ui_options.partShow = false;
				ui_leaderBoard.partPartShow = true;
				ui_leaderBoard.partShow = false;
				ui_exit.partPartShow = true;
				ui_exit.partShow = false;
				ui_options_vsync.partShow();
				ui_options_fps.partShow();
				ui_options_player1color.partShow();
				ui_options_player2color.partShow();
				ui_options_aicolor.partShow();
				ui_options_player1color_red.show = true;
				ui_options_player1color_green.show = true;
				ui_options_player1color_blue.show = true;
				ui_options_player1color_yellow.show = true;
			}else if(menuState == MENUSTATE.OPTIONS_PLAYER2COLOR){
				ui_newGame.partPartShow = true;
				ui_newGame.partShow = false;
				ui_options.partPartShow = true;
				ui_options.partShow = false;
				ui_leaderBoard.partPartShow = true;
				ui_leaderBoard.partShow = false;
				ui_exit.partPartShow = true;
				ui_exit.partShow = false;
				ui_options_vsync.partShow();
				ui_options_fps.partShow();
				ui_options_player1color.partShow();
				ui_options_player2color.partShow();
				ui_options_aicolor.partShow();
				ui_options_player2color_red.show = true;
				ui_options_player2color_green.show = true;
				ui_options_player2color_blue.show = true;
				ui_options_player2color_yellow.show = true;
			}else if(menuState == MENUSTATE.OPTIONS_AICOLOR){
				ui_newGame.partPartShow = true;
				ui_newGame.partShow = false;
				ui_options.partPartShow = true;
				ui_options.partShow = false;
				ui_leaderBoard.partPartShow = true;
				ui_leaderBoard.partShow = false;
				ui_exit.partPartShow = true;
				ui_exit.partShow = false;
				ui_options_vsync.partShow();
				ui_options_fps.partShow();
				ui_options_player1color.partShow();
				ui_options_player2color.partShow();
				ui_options_aicolor.partShow();
				ui_options_aicolor_red.show = true;
				ui_options_aicolor_green.show = true;
				ui_options_aicolor_blue.show = true;
				ui_options_aicolor_yellow.show = true;
			}else if(menuState == MENUSTATE.EXIT){
				System.exit(0);
			}else{
				
			}
		}
	}
	
	public void loadElements(){
		UIelement ui_title = new UIelement("SNAKE",bigFont,30,50,true);
		UIelement ui_newGame = new UIelement("NEW GAME",smallFont,30,150,true);
		UIelement ui_newGame_1player = new UIelement("1 PLAYER",smallFont,230,135,false);
		UIelement ui_newGame_2player = new UIelement("2 PLAYER",smallFont,230,165,false);
		UIelement ui_newGame_2player_local = new UIelement("LOCAL",smallFont,400,135,false);
		UIelement ui_newGame_2player_ai = new UIelement("AI",smallFont,400,165,false);
		UIelement ui_newGame_2player_lan = new UIelement("LAN",smallFont,400,195,false);
		UIelement ui_leaderBoard = new UIelement("LEADERBOARD",smallFont,30,200,true);
		UIelement ui_options = new UIelement("OPTIONS",smallFont,30,250,true);
		
		UIelement ui_options_vsync = new UIelement("VSYNC",smallFont,210,250,false);
		UIelement ui_options_vsync_on = new UIelement("ON",smallFont,400,250,false);
		UIelement ui_options_vsync_off = new UIelement("OFF",smallFont,400,280,false, true);
		UIelement ui_options_fps = new UIelement("FPS",smallFont,210,280,false);
		UIelement ui_options_fps_10 = new UIelement("10",smallFont,400,250,false);
		UIelement ui_options_fps_20 = new UIelement("20",smallFont,400,280,false);
		UIelement ui_options_fps_30 = new UIelement("30",smallFont,400,310,false, true);
		UIelement ui_options_fps_40 = new UIelement("40",smallFont,400,340,false);
		
		UIelement ui_options_player1color = new UIelement("P1 COLOR",smallFont,210,310,false);
		UIelement ui_options_player1color_red = new UIelement("RED",smallFont,380,250,false);
		ui_options_player1color_red.optionDeselected(new Color(150,0,0));
		UIelement ui_options_player1color_green = new UIelement("GREEN",smallFont,380,280,false,true);
		ui_options_player1color_green.optionSelected(new Color(0,255,0),new Color(0,150,0));
		UIelement ui_options_player1color_blue = new UIelement("BLUE",smallFont,380,310,false);
		ui_options_player1color_blue.optionDeselected(new Color(0,0,150));
		UIelement ui_options_player1color_yellow = new UIelement("YELLOW",smallFont,380,340,false);
		ui_options_player1color_yellow.optionDeselected(new Color(150,150,0));
		
		UIelement ui_options_player2color = new UIelement("P2 COLOR",smallFont,210,340,false);
		UIelement ui_options_player2color_red = new UIelement("RED",smallFont,380,280,false);
		ui_options_player2color_red.optionDeselected(new Color(150,0,0));
		UIelement ui_options_player2color_green = new UIelement("GREEN",smallFont,380,310,false);
		ui_options_player2color_green.optionDeselected(new Color(0,150,0));
		UIelement ui_options_player2color_blue = new UIelement("BLUE",smallFont,380,340,false);
		ui_options_player2color_blue.optionDeselected(new Color(0,0,150));
		UIelement ui_options_player2color_yellow = new UIelement("YELLOW",smallFont,380,370,false,true);
		ui_options_player2color_yellow.optionSelected(new Color(255,255,0),new Color(150,150,0));
		
		UIelement ui_options_aicolor = new UIelement("AI COLOR",smallFont,210,370,false);
		UIelement ui_options_aicolor_red = new UIelement("RED",smallFont,380,310,false);
		ui_options_aicolor_red.optionDeselected(new Color(255,0,0));
		UIelement ui_options_aicolor_green = new UIelement("GREEN",smallFont,380,340,false);
		ui_options_aicolor_green.optionDeselected(new Color(0,255,0));
		UIelement ui_options_aicolor_blue = new UIelement("BLUE",smallFont,380,370,false,true);
		ui_options_aicolor_blue.optionSelected(new Color(0,0,255),new Color(0,0,150));
		UIelement ui_options_aicolor_yellow = new UIelement("YELLOW",smallFont,380,400,false);
		ui_options_aicolor_yellow.optionDeselected(new Color(255,255,0));
		
		UIelement ui_exit = new UIelement("EXIT",smallFont,30,300,true);
		
		uiElements.add(ui_title);
		uiElements.add(ui_newGame);
		uiElements.add(ui_newGame_1player);
		uiElements.add(ui_newGame_2player);
		uiElements.add(ui_newGame_2player_local);
		uiElements.add(ui_newGame_2player_ai);
		uiElements.add(ui_newGame_2player_lan);
		uiElements.add(ui_leaderBoard);
		uiElements.add(ui_options);
		uiElements.add(ui_options_vsync);
		uiElements.add(ui_options_vsync_on);
		uiElements.add(ui_options_vsync_off);
		uiElements.add(ui_options_fps);
		uiElements.add(ui_options_fps_10);
		uiElements.add(ui_options_fps_20);
		uiElements.add(ui_options_fps_30);
		uiElements.add(ui_options_fps_40);
		uiElements.add(ui_options_player1color);
		uiElements.add(ui_options_player1color_red);
		uiElements.add(ui_options_player1color_green);
		uiElements.add(ui_options_player1color_blue);
		uiElements.add(ui_options_player1color_yellow);
		uiElements.add(ui_options_player2color);
		uiElements.add(ui_options_player2color_red);
		uiElements.add(ui_options_player2color_green);
		uiElements.add(ui_options_player2color_blue);
		uiElements.add(ui_options_player2color_yellow);
		uiElements.add(ui_options_aicolor);
		uiElements.add(ui_options_aicolor_red);
		uiElements.add(ui_options_aicolor_green);
		uiElements.add(ui_options_aicolor_blue);
		uiElements.add(ui_options_aicolor_yellow);
		uiElements.add(ui_exit);
	}
	
	public Image loadImage(String filepath){
		Image img = null;
		try{
			img = ImageIO.read(new File(filepath));
		}catch(Exception e){
			System.out.println("Error loading image: '" + filepath + "'");
		}
		return img;
	}
	
	public void drawConnection(UIelement parentElement, UIelement selectedElement){
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		g.setColor(new Color(255,255,255));
		int distance = selectedElement.x-(parentElement.x+parentElement.w);
		g.drawLine(parentElement.x+parentElement.w, parentElement.y-(parentElement.h/2), parentElement.x+parentElement.w+(distance/2), parentElement.y-(parentElement.h/2));
		g.drawLine(selectedElement.x-3, selectedElement.y-(selectedElement.h/2),selectedElement.x-(distance/2),selectedElement.y-(selectedElement.h/2));
		g.drawLine(parentElement.x+parentElement.w+(distance/2),selectedElement.y-(selectedElement.h/2),parentElement.x+parentElement.w+(distance/2),parentElement.y-(parentElement.h/2));
	}
	
	public void drawConnection(UIelement parentElement, UIelement selectedElement, int childLineLength){
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		g.setColor(new Color(255,255,255));
		int distance = selectedElement.x-(parentElement.x+parentElement.w);
		g.drawLine(parentElement.x+parentElement.w, parentElement.y-(parentElement.h/2), parentElement.x+parentElement.w+(distance-childLineLength), parentElement.y-(parentElement.h/2));
		g.drawLine(selectedElement.x-3, selectedElement.y-(selectedElement.h/2),selectedElement.x-childLineLength,selectedElement.y-(selectedElement.h/2));
		g.drawLine(parentElement.x+parentElement.w+(distance-childLineLength),selectedElement.y-(selectedElement.h/2),parentElement.x+parentElement.w+(distance-childLineLength),parentElement.y-(parentElement.h/2));
	}
	
	public void drawConnection(UIelement parentElement, UIelement childElement1, UIelement childElement2){
		Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
		g.setColor(new Color(100,100,100));
		int distance = childElement1.x-(parentElement.x+parentElement.w);
		g.drawLine(parentElement.x+parentElement.w, parentElement.y-(parentElement.h/2), parentElement.x+parentElement.w+(distance/2), parentElement.y-(parentElement.h/2));
		g.drawLine(childElement1.x-3, childElement1.y-(childElement1.h/2),childElement1.x-(distance/2),childElement1.y-(childElement1.h/2));
		g.drawLine(childElement2.x-3, childElement2.y-(childElement2.h/2),childElement2.x-(distance/2),childElement2.y-(childElement2.h/2));
		g.drawLine(childElement2.x-(distance/2), childElement1.y-(childElement1.h/2),childElement2.x-(distance/2),childElement2.y-(childElement2.h/2));
	}
	
	public void drawConnection(UIelement parentElement, UIelement childElement1, UIelement childElement2, UIelement childElement3){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		drawConnection(parentElement, childElement1, childElement3);
		int distance = childElement1.x-(parentElement.x+parentElement.w);
		g.setColor(new Color(100,100,100));
		g.drawLine(childElement2.x-3, childElement2.y-(childElement2.h/2),childElement2.x-(distance/2),childElement2.y-(childElement2.h/2));
	}
	
	public void drawConnection(UIelement parentElement, UIelement childElement1, UIelement childElement2, UIelement childElement3, UIelement childElement4){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		drawConnection(parentElement, childElement1, childElement4);
		drawConnection(parentElement, childElement2, childElement3);
	}
	
	public void drawConnection(UIelement parentElement, UIelement childElement1, UIelement childElement2, UIelement childElement3, UIelement childElement4, UIelement childElement5){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		drawConnection(parentElement, childElement1, childElement5);
		drawConnection(parentElement, childElement2,childElement3,childElement4);
	}
	
	public void drawConnection(UIelement parentElement, UIelement childElement1, UIelement childElement2, UIelement childElement3, UIelement childElement4, int childLineLength){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		g.setColor(new Color(100,100,100));
		int distance = childElement1.x-(parentElement.x+parentElement.w);
		g.drawLine(parentElement.x+parentElement.w, parentElement.y-(parentElement.h/2), parentElement.x+parentElement.w+(distance-childLineLength), parentElement.y-(parentElement.h/2));
		g.drawLine(childElement1.x-3, childElement1.y-(childElement1.h/2),childElement1.x-childLineLength,childElement1.y-(childElement1.h/2));
		g.drawLine(childElement2.x-3, childElement2.y-(childElement2.h/2),childElement2.x-childLineLength,childElement2.y-(childElement2.h/2));
		g.drawLine(childElement3.x-3, childElement3.y-(childElement3.h/2),childElement3.x-childLineLength,childElement3.y-(childElement3.h/2));
		g.drawLine(childElement4.x-3, childElement4.y-(childElement4.h/2),childElement4.x-childLineLength,childElement4.y-(childElement4.h/2));
		g.drawLine(childElement4.x-childLineLength, childElement1.y-(childElement1.h/2),childElement4.x-childLineLength,childElement4.y-(childElement4.h/2));
	}
	
	public boolean checkMouse(int x, int y, int w, int h){
		if(mouseX >= x  && mouseX <= (x+w) && mouseY >= y && mouseY <= (y+h)){
			return true;
		}else{
			return false;
		}
	}
	
	public void drawUI(){
		for(int i = 0; i<= uiElements.size()-1;i++){
			UIelement uielement = uiElements.get(i);
			if(uielement.show || uielement.partShow || uielement.partPartShow)
				uielement.draw();
		}
		if(uiElements.size()>=6){
			if((uiElements.get(UI_NEWGAME).hovered && menuState == MENUSTATE.DEFAULT) || menuState == MENUSTATE.NEWGAME || menuState == MENUSTATE.NEWGAME_1PLAYER || menuState == MENUSTATE.NEWGAME_2PLAYER){
				drawConnection(uiElements.get(UI_NEWGAME), uiElements.get(UI_NEWGAME_1PLAYER), uiElements.get(UI_NEWGAME_2PLAYER));
				if((uiElements.get(UI_NEWGAME_1PLAYER).hovered && menuState != MENUSTATE.NEWGAME_2PLAYER) || menuState == MENUSTATE.NEWGAME_1PLAYER){
					drawConnection(uiElements.get(UI_NEWGAME), uiElements.get(UI_NEWGAME_1PLAYER));
				}else if(uiElements.get(UI_NEWGAME_2PLAYER).hovered || menuState == MENUSTATE.NEWGAME_2PLAYER){
					drawConnection(uiElements.get(UI_NEWGAME), uiElements.get(UI_NEWGAME_2PLAYER));
				}
			}
			if((uiElements.get(UI_NEWGAME_2PLAYER).hovered && (uiElements.get(UI_NEWGAME_2PLAYER).show || uiElements.get(UI_NEWGAME_2PLAYER).partShow)) || (menuState == MENUSTATE.NEWGAME_2PLAYER)){
				drawConnection(uiElements.get(UI_NEWGAME_2PLAYER),uiElements.get(UI_NEWGAME_2PLAYER_LOCAL), uiElements.get(UI_NEWGAME_2PLAYER_AI),uiElements.get(UI_NEWGAME_2PLAYER_LAN));
				if(uiElements.get(UI_NEWGAME_2PLAYER_LOCAL).hovered){
					drawConnection(uiElements.get(UI_NEWGAME_2PLAYER), uiElements.get(UI_NEWGAME_2PLAYER_LOCAL));
				}else if(uiElements.get(UI_NEWGAME_2PLAYER_AI).hovered){
					drawConnection(uiElements.get(UI_NEWGAME_2PLAYER), uiElements.get(UI_NEWGAME_2PLAYER_AI));
				}else if(uiElements.get(UI_NEWGAME_2PLAYER_LAN).hovered){
					drawConnection(uiElements.get(UI_NEWGAME_2PLAYER), uiElements.get(UI_NEWGAME_2PLAYER_LAN));
				}
			}
			if(uiElements.get(UI_OPTIONS).hovered || menuState == MENUSTATE.OPTIONS || menuState == MENUSTATE.OPTIONS_VSYNC || menuState == MENUSTATE.OPTIONS_FPS || menuState == MENUSTATE.OPTIONS_PLAYER1COLOR || menuState == MENUSTATE.OPTIONS_PLAYER2COLOR || menuState == MENUSTATE.OPTIONS_AICOLOR){
				drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_VSYNC), uiElements.get(UI_OPTIONS_FPS), uiElements.get(UI_OPTIONS_PLAYER1COLOR), uiElements.get(UI_OPTIONS_PLAYER2COLOR), uiElements.get(UI_OPTIONS_AICOLOR));
				if((uiElements.get(UI_OPTIONS_VSYNC).hovered && menuState == MENUSTATE.OPTIONS) || menuState == MENUSTATE.OPTIONS_VSYNC){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_VSYNC));
				}else if((uiElements.get(UI_OPTIONS_FPS).hovered && menuState == MENUSTATE.OPTIONS) || menuState == MENUSTATE.OPTIONS_FPS){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_FPS));
				}else if((uiElements.get(UI_OPTIONS_PLAYER1COLOR).hovered && menuState == MENUSTATE.OPTIONS) || menuState == MENUSTATE.OPTIONS_PLAYER1COLOR){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_PLAYER1COLOR));
				}else if((uiElements.get(UI_OPTIONS_PLAYER2COLOR).hovered && menuState == MENUSTATE.OPTIONS) || menuState == MENUSTATE.OPTIONS_PLAYER2COLOR){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_PLAYER2COLOR));
				}else if((uiElements.get(UI_OPTIONS_AICOLOR).hovered && menuState == MENUSTATE.OPTIONS) || menuState == MENUSTATE.OPTIONS_AICOLOR){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_AICOLOR));
				}
				if(menuState == MENUSTATE.OPTIONS_VSYNC || (uiElements.get(UI_OPTIONS_VSYNC).hovered && menuState == MENUSTATE.OPTIONS)){
					drawConnection(uiElements.get(UI_OPTIONS_VSYNC), uiElements.get(UI_OPTIONS_VSYNC_ON), uiElements.get(UI_OPTIONS_VSYNC_OFF));
					if(uiElements.get(UI_OPTIONS_VSYNC_ON).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_VSYNC), uiElements.get(UI_OPTIONS_VSYNC_ON));
					}else if(uiElements.get(UI_OPTIONS_VSYNC_OFF).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_VSYNC), uiElements.get(UI_OPTIONS_VSYNC_OFF));
					}else if(uiElements.get(UI_OPTIONS_PLAYER1COLOR).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_VSYNC), uiElements.get(UI_OPTIONS_VSYNC_OFF));
					}
				}else if(menuState == MENUSTATE.OPTIONS_FPS || (uiElements.get(UI_OPTIONS_FPS).hovered && menuState == MENUSTATE.OPTIONS)){
					if(menuState != MENUSTATE.OPTIONS_FPS){
						drawConnection(uiElements.get(UI_OPTIONS_FPS), uiElements.get(UI_OPTIONS_FPS_10), uiElements.get(UI_OPTIONS_FPS_20), uiElements.get(UI_OPTIONS_FPS_30), uiElements.get(UI_OPTIONS_FPS_40),20);
					}else{
						drawConnection(uiElements.get(UI_OPTIONS_FPS), uiElements.get(UI_OPTIONS_FPS_10), uiElements.get(UI_OPTIONS_FPS_20), uiElements.get(UI_OPTIONS_FPS_30), uiElements.get(UI_OPTIONS_FPS_40),30);
					}
					if(uiElements.get(UI_OPTIONS_FPS_10).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_FPS), uiElements.get(UI_OPTIONS_FPS_10),30);
					}else if(uiElements.get(UI_OPTIONS_FPS_20).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_FPS), uiElements.get(UI_OPTIONS_FPS_20),30);
					}else if(uiElements.get(UI_OPTIONS_FPS_30).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_FPS), uiElements.get(UI_OPTIONS_FPS_30),30);
					}else if(uiElements.get(UI_OPTIONS_FPS_40).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_FPS), uiElements.get(UI_OPTIONS_FPS_40),30);
					}
				}else if(menuState == MENUSTATE.OPTIONS_PLAYER1COLOR || (uiElements.get(UI_OPTIONS_PLAYER1COLOR).hovered && menuState == MENUSTATE.OPTIONS)){
					drawConnection(uiElements.get(UI_OPTIONS_PLAYER1COLOR), uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED), uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN), uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE), uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW));
					if(uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_PLAYER1COLOR), uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED));
					}else if(uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_PLAYER1COLOR), uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN));
					}else if(uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_PLAYER1COLOR), uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE));
					}else if(uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_PLAYER1COLOR), uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW));
					}
				}else if(menuState == MENUSTATE.OPTIONS_PLAYER2COLOR || (uiElements.get(UI_OPTIONS_PLAYER2COLOR).hovered && menuState == MENUSTATE.OPTIONS)){
					drawConnection(uiElements.get(UI_OPTIONS_PLAYER2COLOR), uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED), uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN), uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE), uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW));
					if(uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_PLAYER2COLOR), uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED));
					}else if(uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_PLAYER2COLOR), uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN));
					}else if(uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_PLAYER2COLOR), uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE));
					}else if(uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_PLAYER2COLOR), uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW));
					}
				}else if(menuState == MENUSTATE.OPTIONS_AICOLOR || (uiElements.get(UI_OPTIONS_AICOLOR).hovered && menuState == MENUSTATE.OPTIONS)){
					drawConnection(uiElements.get(UI_OPTIONS_AICOLOR), uiElements.get(UI_OPTIONS_AICOLOR_RED), uiElements.get(UI_OPTIONS_AICOLOR_GREEN), uiElements.get(UI_OPTIONS_AICOLOR_BLUE), uiElements.get(UI_OPTIONS_AICOLOR_YELLOW));
					if(uiElements.get(UI_OPTIONS_AICOLOR_RED).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_AICOLOR), uiElements.get(UI_OPTIONS_AICOLOR_RED));
					}else if(uiElements.get(UI_OPTIONS_AICOLOR_GREEN).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_AICOLOR), uiElements.get(UI_OPTIONS_AICOLOR_GREEN));
					}else if(uiElements.get(UI_OPTIONS_AICOLOR_BLUE).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_AICOLOR), uiElements.get(UI_OPTIONS_AICOLOR_BLUE));
					}else if(uiElements.get(UI_OPTIONS_AICOLOR_YELLOW).hovered){
						drawConnection(uiElements.get(UI_OPTIONS_AICOLOR), uiElements.get(UI_OPTIONS_AICOLOR_YELLOW));
					}
				}
			}
		}
	}
	
	class User implements Comparable<User>{
		int highScore;
		String name = "NoobWithNoName";
		int score = 0;
		int totalScore;
		int position = -1;
		int playSessions = 1;
		
		public User(){
			totalScore = 0;
			highScore = highScore;
		}
		
		public User(String name, int score){
			this.score = score;
			this.name = name;
			totalScore = 0;
			highScore = score;
		}
		
		//@Override
		public int compareTo(User user){
			int compareHighScore = user.highScore;
			return this.highScore-user.highScore;
		}
	}
	
	class Block{
		int x=0;
		int y=0;
		public static final int WIDTH = 25;
		public static final int HEIGHT = 25;
		Color primaryColor = new Color(50,50,50);
		Color secondaryColor = new Color(200, 200, 200);
		public Block(BlockType type){
			if(type == BlockType.TRAILING){
				Color primaryColor = new Color(200,200,200);
				Color secondaryColor = new Color(200,200,200);
			}else if(type == BlockType.SNAKE){
			}else if(type == BlockType.SNAKEHEAD){
			}else if(type == BlockType.WALL){
			}else if(type == BlockType.FRUIT){
				
			}
		}
		void draw(){
			Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
			g.setColor(secondaryColor);
			g.fillRect(x,y,WIDTH,HEIGHT);
			g.setColor(primaryColor);
			g.fillRect(x+WIDTH/10,y+HEIGHT/10,WIDTH*4/5,HEIGHT*4/5);
		}
	}
	
	class SnakePlayer{
		ArrayList<Block> blocks = new ArrayList();
		String currentDirection = "STILL";
		User user;
		boolean toBurs = false;
		boolean dead = false;
		boolean red = false;
		boolean green = false;
		boolean blue = false;
		int r,r2,g,g2,b,b2 = 0;
		int burs_origin_x = 0;
		int burs_origin_y = 0;
		int bursIndex = 0;
		public SnakePlayer(int spawnX, int spawnY, Color color, User user){ //constructor
			this.user = user;
			red = color.getRed() == 255;
			green = color.getGreen() == 255;
			blue = color.getBlue() == 255;
			r = 0;
			r2 = 0;
			g = 0;
			g2 = 0;
			b = 0;
			b2 = 0;
			if(red){
				r = 255;
				r2 = 200;
			}
			if(green){
				g = 255;
				g2 = 200;
			}
			if(blue){
				b = 255;
				b2 = 200;
			}
			for(int i = 0; i <= 3; i++){
				Block block;
				if(i != 0){
					block = new Block(BlockType.SNAKE);
				}else{
					block = new Block(BlockType.SNAKEHEAD);
				}
				block.x = spawnX - (i*25);
				block.y = spawnY;
				if(i == 0){
					block.primaryColor = new Color(r,g,b);
					block.secondaryColor = new Color(r2,g2,b2);
				}else{
					if(red){
						r = 255 - (i*50);
						r2 = 200 - (i*50);
					}
					if(blue){
						b = 255 - (i*50);
						b2 = 200 - (i*50);
					}
					if(green){
						g = 255 - (i*50);
						g2 = 200 - (i*50);
					}
					block.primaryColor = new Color(r,g,b);
					block.secondaryColor = new Color(r2,g2,b2);
				}
				blocks.add(block);
			}
			distributeColors();
		}
		
		void distributeColors(){
			int size = blocks.size();
			int colorDistro = 220/size;
			int secondaryColorDistro = 180/size;
			if(red){
				r = 255;
				r2 = 200;
			}
			if(blue){
				b = 255;
				b2 = 200;
			}
			if(green){
				g = 255;
				g2 = 200;
			}
			for(int i = 0; i <= size-1; i++){
				Block block = blocks.get(i);
				if(red){
					r = 255-(i*colorDistro);
					r2 = 200-(i*secondaryColorDistro);
				}
				if(blue){
					b = 255-(i*colorDistro);
					b2 = 200-(i*secondaryColorDistro);
				}
				if(green){
					g = 255-(i*colorDistro);
					g2 = 200-(i*secondaryColorDistro);
				}
				block.primaryColor = new Color(r,g,b);
				block.secondaryColor = new Color(r2,g2,b2);
			}
		}
		
		void draw(){
				Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
				for(int i = 0; i <= blocks.size()-1; i++){
					Block temp = blocks.get(i);
					temp.draw();
				}
				if(toBurs){
					bursIndex+=3;
					if(bursIndex != 0 && (bursIndex*4) < 255)
						drawOutlineGlowingCircle(burs_origin_x,burs_origin_y,bursIndex*3,10+(bursIndex/4),255,255,0,255-(bursIndex*4));
					//System.out.println("toBurs");
					burs(burs_origin_x,burs_origin_y,bursIndex,3,this,255,255,0);
				}else{
					bursIndex = 0;
				}
				/*Block lastBlock = blocks.get(blocks.size()-1);
				g.setColor(new Color(238,238,238));
				g.fillRect(lastBlock.x, lastBlock.y,lastBlock.WIDTH, lastBlock.HEIGHT);*/

		}
		
		void draw(int r, int g, int b){
				//Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
				for(int i = 0; i <= blocks.size()-1; i++){
					Block temp = blocks.get(i);
					temp.draw();
				}
				if(toBurs){
					bursIndex+=3;
					if(bursIndex != 0 && (bursIndex*4) < 255)
						drawOutlineGlowingCircle(burs_origin_x,burs_origin_y,bursIndex*3,10+(bursIndex/4),r,g,b,255-(bursIndex*4));
					//System.out.println("toBurs");
					burs(burs_origin_x,burs_origin_y,bursIndex,3,this,r,g,b);
				}else{
					bursIndex = 0;
				}
				/*Block lastBlock = blocks.get(blocks.size()-1);
				g.setColor(new Color(238,238,238));
				g.fillRect(lastBlock.x, lastBlock.y,lastBlock.WIDTH, lastBlock.HEIGHT);*/

		}
		
		void addBlock(){
			Block block = new Block(BlockType.SNAKE);
			Block lastBlock = blocks.get(blocks.size()-1);
			block.x = -25;
			block.y = -25;
			blocks.add(block);
			distributeColors();
		}
		
		boolean collisionDetection(){
			boolean successFlag = true;
			for(int i = 0; i<= blocks.size()-1; i++){  //test collision with players body blocks
				Block block = blocks.get(i);
				for(int j = 0; j<= blocks.size()-1; j++){
					Block block2 = blocks.get(j);
					if(i!=j){
						if(block.x == block2.x && block.y == block2.y){
							successFlag = false;
						}
					}
				}
			}
			if(walls){
				for(int i = 0; i<= blocks.size()-1; i++){ //test collision with wall blocks
					Block block = blocks.get(i);
					for(int j = 0; j<=wallBlocks.size()-1; j++){
						Block wall = wallBlocks.get(j);
						if(block.x == wall.x && block.y == wall.y){
							successFlag = false;
						}
					}
				}
			}
			
			for(int i = 0; i<= blocks.size()-1; i++){ //test collision with fruit
				Block block = blocks.get(i);
				for(int j = 0; j<= fruitArray.size()-1; j++){
					Block fruit = fruitArray.get(j);
					if(block.x == fruit.x && block.y == fruit.y){
						user.score++;
						fruitArray.remove(j);
						// if(mode != GameMode.TWO_PLAYER_LAN_CLIENT)
						spawnFruit();
						addBlock();
						if(toBurs){
							bursIndex = 0;
							burs_origin_x = block.x+block.WIDTH/2;
							burs_origin_y = block.y+block.HEIGHT/2;
						}else{
							toBurs = true;
							burs_origin_x = block.x+block.WIDTH/2;
							burs_origin_y = block.y+block.HEIGHT/2;
						}
					}
				}
			}
			return successFlag;
		}
		
		void move(String direction, boolean toMove){
			Block snakeHead = blocks.get(0);
			if((direction == "up" || direction == "UP")/*&&(currentDirection != "DOWN" && currentDirection != "down")*/){
				if(toMove){
					pushSnake();
					snakeHead.y-=snakeHead.HEIGHT;
				}
				currentDirection = "UP";
				if(walls == false && snakeHead.y <= snakeHead.HEIGHT){
					snakeHead.y = c.getWidth()-snakeHead.HEIGHT;
				}
			}else if((direction == "down" || direction == "DOWN")/*&&(currentDirection != "UP" && currentDirection != "up")*/){
				if(toMove){
					pushSnake();
					snakeHead.y+=snakeHead.HEIGHT;
					if(walls == false && snakeHead.y >= c.getHeight()){
						snakeHead.y = snakeHead.HEIGHT*2;
					}
				}
				currentDirection = "DOWN";
			}else if((direction == "left" || direction == "LEFT")/*&&(currentDirection != "RIGHT" && currentDirection != "right")*/){
				if(toMove){
					pushSnake();
					snakeHead.x-=snakeHead.WIDTH;
					if(walls == false && snakeHead.x <= -snakeHead.WIDTH){
						snakeHead.x = c.getWidth()-snakeHead.WIDTH;
					}
				}
				currentDirection = "LEFT";
			}else if((direction == "right" || direction == "RIGHT")/*&&(currentDirection != "LEFT" && currentDirection != "left")*/){
				if(toMove){
					pushSnake();
					snakeHead.x+=snakeHead.WIDTH;
					if(walls == false && snakeHead.x >= c.getWidth()){
						snakeHead.x = 0;
					}
				}
				currentDirection = "RIGHT";
			}
		}
		
		void pushSnake(){
			for(int i = blocks.size()-1; i >= 1; i--){
				Block block = blocks.get(i);
				Block prevBlock = blocks.get(i-1);
				block.x = prevBlock.x;
				block.y = prevBlock.y;
			}
		}
		
		void update(){
			if(currentDirection != "STILL"){
				move(currentDirection, true);
			}
		}
		
		void restart(){
			blocks.clear();
			for(int i = 0; i <= 3; i++){
				Block block;
				if(i != 0){
					block = new Block(BlockType.SNAKE);
				}else{
					block = new Block(BlockType.SNAKEHEAD);
				}
				block.x = 250 - (i*25);
				block.y = 250;
				if(i == 0){
					block.primaryColor = new Color(255, 255, 0);
					block.secondaryColor = new Color(200,200,0);
				}else{
					block.primaryColor = new Color(255-(i*50), 255-(i*50), 0);
					block.secondaryColor = new Color(200-(i*50), 200-(i*50), 0);
				}
				blocks.add(block);
			}
			distributeColors();
			currentDirection = "STILL";
		}
		
		void checkFruit(){
			for(int i = 0; i<= blocks.size()-1; i++){ //test collision with fruit
				Block block = blocks.get(i);
				for(int j = 0; j<= fruitArray.size()-1; j++){
					Block fruit = fruitArray.get(j);
					if(block.x == fruit.x && block.y == fruit.y){
						user.score++;
						fruitArray.remove(j);
						if(mode != GameMode.TWO_PLAYER_LAN_CLIENT)
							spawnFruit();
						addBlock();
						if(toBurs){
							bursIndex = 0;
							burs_origin_x = block.x+block.WIDTH/2;
							burs_origin_y = block.y+block.HEIGHT/2;
						}else{
							toBurs = true;
							burs_origin_x = block.x+block.WIDTH/2;
							burs_origin_y = block.y+block.HEIGHT/2;
						}
					}
				}
			}
		}
	}
	
	class UIelement{
		String text;
		int x;
		int y;
		int w;
		int h;
		int x_origin;
		int y_origin;
		Color primaryColor = Color.white;
		Color secondaryColor = Color.black;
		Font font;
		boolean show;
		boolean partShow = false;
		boolean partPartShow = false;
		boolean activated = false;
		boolean hovered = false;
		boolean optionSelected = false;
		
		public UIelement(String text, Font font, int x, int y, Color primaryColor, Color secondaryColor, boolean show){
			this(text,font,x,y, show);
			this.primaryColor = primaryColor;
			this.secondaryColor = secondaryColor;
		}
		
		public UIelement(String text, Font font, int x, int y, boolean show){
			Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
			FontMetrics fm = g.getFontMetrics(font);
			this.font = font;
			this.text = text;
			this.x = x;
			this.y = y;
			x_origin = x;
			y_origin = y;
			this.h = fm.getHeight()*11/20;
			this.w = fm.stringWidth(text);
			this.show = show;
		}
		
		public UIelement(String text, Font font, int x, int y, boolean show, boolean optionSelected){
			this(text,font,x,y,show);
			this.optionSelected = optionSelected;
			this.primaryColor = new Color(255,255,255);
			this.secondaryColor = new Color(200,200,200);
		}
		
		void partShow(){
			partShow = true;
			show = false;
			x = x_origin-20;
		}
		
		void setText(String text){
			Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
			FontMetrics fm = g.getFontMetrics(font);
			this.text = text;
			this.h = fm.getHeight()*11/20;
			this.w = fm.stringWidth(text);
		}
		
		void optionSelected(){
			this.optionSelected = true;
			this.primaryColor = new Color(255,255,255);
			this.secondaryColor = new Color(200,200,200);
		}
		
		void optionSelected(Color pColor, Color sColor){
			this.optionSelected = true;
			this.primaryColor = pColor;
			this.secondaryColor = sColor;
		}
		
		void optionDeselected(){
			this.optionSelected = false;
			this.primaryColor = new Color(255,255,255);
			this.secondaryColor = new Color(0,0,0);
		}
		
		void optionDeselected(Color color){
			this.optionSelected = false;
			this.primaryColor = new Color(255,255,255);
			this.secondaryColor = color;
		}
		
		void draw(){
			Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
			g.setFont(font);
			if(show){
				if(checkMouse(x,y-h,w,h)){
					g.setColor(secondaryColor);
					g.drawString(text,x,y);
				}else{
					g.setColor(primaryColor);
					g.drawString(text,x,y);
				}
			}else if((optionSelected && partShow) || (optionSelected && show)){
				if(checkMouse(x,y-h,w,h)){
					g.setColor(secondaryColor);
					g.drawString(text,x,y);
				}else{
					g.setColor(primaryColor);
					g.drawString(text,x,y);
				}
			}else if(partShow){
				g.setColor(new Color(100,100,100));
				g.drawString(text,x,y);
			}else if(partPartShow){
				g.setColor(new Color(60,60,60));
				g.drawString(text,x,y);
			}
		}
	}
	
	class AnimationThread extends Thread{
		Snake at;
		
		public AnimationThread(Snake _at){
			at=_at;
			start();
		}
		
		public void run(){
			//int FPS = fps;
			int SKIP_TICKS = 1000 / fps;
			long next_game_tick = System.currentTimeMillis();
			long sleep_time = 0;
			while(true){
				if(animationRunning){
					SKIP_TICKS = 1000/fps;
					updateGame();
					at.updateCanvas();
					next_game_tick += SKIP_TICKS;
					sleep_time = next_game_tick - System.currentTimeMillis();
					if( sleep_time >= 0 ) {
						try{
							sleep(sleep_time); 
						}catch(InterruptedException ie){}
					}else{
						next_game_tick = System.currentTimeMillis();
					}					
				}
			}
		
		}
	
	}

}
