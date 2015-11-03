/*
To compile and run this in NotePad++  Save it as Template.java (needs to be changed to whatever the Java class name is). Press F5 and then paste the line below into the box:

R:\DigiTech\JDK86\comp.bat "$(FULL_CURRENT_PATH)" "$(CURRENT_DIRECTORY)" "$(NAME_PART)"

Then Press Run
----------------------------------------------------------------------------------------------------------------------
Use this file as the starting point for all of your programs in the programming topic. 

*/
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.applet.Applet;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class SnakeUI extends JFrame{
	Canvas c;
	Button startButton, stopButton;
	boolean animationRunning = true;
	private BufferStrategy	strategy;
	Image background;
	Font smallFont = new Font("Visitor TT1 BRK", Font.BOLD, 30);
	Font bigFont = new Font("Visitor TT1 BRK", Font.BOLD, 72);
	int mouseX = 0, mouseY = 0;
	
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
	
	boolean vsync = false;
	int fps = 30;
	Color player1Color = new Color(255,255,0);
	Color player2Color = new Color(0,255,0);
	Color aiColor = new Color(0,0,255);
	
	ArrayList<UIelement> uiElements = new ArrayList<UIelement>();
	public static void main(String s[]) {
		new SnakeUI();
	}
	MENUSTATE menuState = MENUSTATE.DEFAULT;
	public enum MENUSTATE{
		DEFAULT, NEWGAME, NEWGAME_1PLAYER, NEWGAME_2PLAYER, LEADERBOARD, OPTIONS, OPTIONS_VSYNC,OPTIONS_FPS, OPTIONS_PLAYER1COLOR, OPTIONS_PLAYER2COLOR, OPTIONS_AICOLOR, EXIT
	}
	
	static {
		System.setProperty("sun.java2d.transaccel", "True");
		// System.setProperty("sun.java2d.trace", "timestamp,log,count");
		System.setProperty("sun.java2d.opengl", "True");
		//System.setProperty("sun.java2d.d3d", "True");
		System.setProperty("sun.java2d.ddforcevram", "True");
	}
	
	public SnakeUI(){
		c = new Canvas();
		c.setSize(500, 500);
		background = loadImage("uiBackground/croppedGrid.jpg");
		// Add a window listner for close button
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		c.addMouseMotionListener(new MouseMotionListener(){
            public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			public void mouseDragged(MouseEvent e) {
			   mouseX = e.getX();
			   mouseY = e.getY();
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
								menuState = MENUSTATE.NEWGAME_1PLAYER;
							}else if(uielement.text == "2 PLAYER"){
								menuState = MENUSTATE.NEWGAME_2PLAYER;
							}else if(uielement.text == "LOCAL"){
								
							}else if(uielement.text == "AI"){
								
							}else if(uielement.text == "LAN"){
								
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
							}else if(uielement.text == "20"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_FPS_10).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_30).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_40).optionDeselected();
								fps = 20;
							}else if(uielement.text == "30"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_FPS_10).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_20).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_40).optionDeselected();
								fps = 30;
							}else if(uielement.text == "40"){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_FPS_10).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_20).optionDeselected();
								uiElements.get(UI_OPTIONS_FPS_30).optionDeselected();
								fps = 40;
							}else if(uielement.text == "P1 COLOR"){
								menuState = MENUSTATE.OPTIONS_PLAYER1COLOR;
							}else if(i == UI_OPTIONS_PLAYER1COLOR_RED){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW).optionDeselected();
								player1Color = new Color(255,0,0);
							}else if(i == UI_OPTIONS_PLAYER1COLOR_GREEN){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW).optionDeselected();
								player1Color = new Color(0,255,0);
							}else if(i == UI_OPTIONS_PLAYER1COLOR_BLUE){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_YELLOW).optionDeselected();
								player1Color = new Color(0,0,255);
							}else if(i == UI_OPTIONS_PLAYER1COLOR_YELLOW){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER1COLOR_BLUE).optionDeselected();
								player1Color = new Color(255,255,0);
							}else if(uielement.text == "P2 COLOR"){
								menuState = MENUSTATE.OPTIONS_PLAYER2COLOR;
							}else if(i == UI_OPTIONS_PLAYER2COLOR_RED){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW).optionDeselected();
								player2Color = new Color(255,0,0);
							}else if(i == UI_OPTIONS_PLAYER2COLOR_GREEN){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW).optionDeselected();
								player2Color = new Color(0,255,0);
							}else if(i == UI_OPTIONS_PLAYER2COLOR_BLUE){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_YELLOW).optionDeselected();
								player2Color = new Color(0,0,255);
							}else if(i == UI_OPTIONS_PLAYER2COLOR_YELLOW){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_PLAYER2COLOR_BLUE).optionDeselected();
								player2Color = new Color(255,255,0);
							}else if(uielement.text == "AI COLOR"){
								menuState = MENUSTATE.OPTIONS_AICOLOR;
							}else if(i == UI_OPTIONS_AICOLOR_RED){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_AICOLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_AICOLOR_BLUE).optionDeselected();
								uiElements.get(UI_OPTIONS_AICOLOR_YELLOW).optionDeselected();
								player1Color = new Color(255,0,0);
							}else if(i == UI_OPTIONS_AICOLOR_GREEN){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_AICOLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_AICOLOR_BLUE).optionDeselected();
								uiElements.get(UI_OPTIONS_AICOLOR_YELLOW).optionDeselected();
								aiColor = new Color(0,255,0);
							}else if(i == UI_OPTIONS_AICOLOR_BLUE){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_AICOLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_AICOLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_AICOLOR_YELLOW).optionDeselected();
								aiColor = new Color(0,0,255);
							}else if(i == UI_OPTIONS_AICOLOR_YELLOW){
								uielement.optionSelected();
								uiElements.get(UI_OPTIONS_AICOLOR_RED).optionDeselected();
								uiElements.get(UI_OPTIONS_AICOLOR_GREEN).optionDeselected();
								uiElements.get(UI_OPTIONS_AICOLOR_BLUE).optionDeselected();
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
		new AnimationThread(this);
		loadElements();
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
		UIelement ui_options_fps_30 = new UIelement("30",smallFont,400,310,false,true);
		UIelement ui_options_fps_40 = new UIelement("40",smallFont,400,340,false);
		UIelement ui_options_player1color = new UIelement("P1 COLOR",smallFont,210,310,false);
		UIelement ui_options_player1color_red = new UIelement("RED",smallFont,380,250,false);
		UIelement ui_options_player1color_green = new UIelement("GREEN",smallFont,380,280,false);
		UIelement ui_options_player1color_blue = new UIelement("BLUE",smallFont,380,310,false);
		UIelement ui_options_player1color_yellow = new UIelement("YELLOW",smallFont,380,340,false,true);
		UIelement ui_options_player2color = new UIelement("P2 COLOR",smallFont,210,340,false);
		UIelement ui_options_player2color_red = new UIelement("RED",smallFont,380,280,false);
		UIelement ui_options_player2color_green = new UIelement("GREEN",smallFont,380,310,false,true);
		UIelement ui_options_player2color_blue = new UIelement("BLUE",smallFont,380,340,false);
		UIelement ui_options_player2color_yellow = new UIelement("YELLOW",smallFont,380,370,false);
		UIelement ui_options_aicolor = new UIelement("AI COLOR",smallFont,210,370,false);
		UIelement ui_options_aicolor_red = new UIelement("RED",smallFont,380,310,false);
		UIelement ui_options_aicolor_green = new UIelement("GREEN",smallFont,380,340,false);
		UIelement ui_options_aicolor_blue = new UIelement("BLUE",smallFont,380,370,false,true);
		UIelement ui_options_aicolor_yellow = new UIelement("YELLOW",smallFont,380,400,false);
		
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
	
	public int randomInteger(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
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
	
	public void updateGame(){
		updateUI();
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
				if(uiElements.get(UI_OPTIONS_VSYNC).hovered){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_VSYNC));
				}else if(uiElements.get(UI_OPTIONS_FPS).hovered){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_FPS));
				}else if(uiElements.get(UI_OPTIONS_PLAYER1COLOR).hovered){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_PLAYER1COLOR));
				}else if(uiElements.get(UI_OPTIONS_PLAYER2COLOR).hovered){
					drawConnection(uiElements.get(UI_OPTIONS), uiElements.get(UI_OPTIONS_PLAYER2COLOR));
				}else if(uiElements.get(UI_OPTIONS_AICOLOR).hovered){
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
	
	public void updateCanvas(){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();  
		g.drawImage(background,0,0,null);
		//g.setColor(new Color(150,150,150,150));
		//g.fillRect(0,0,c.getWidth(),c.getHeight());
		//Drawing goes in here
		drawUI();
		g.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
	}
	
	class Block{
		int x=0;
		int y=0;
		public static final int WIDTH = 25;
		public static final int HEIGHT = 25;
		Color primaryColor = new Color(50,50,50);
		Color secondaryColor = new Color(200, 200, 200);
		void draw(){
			Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
			g.setColor(secondaryColor);
			g.fillRect(x,y,WIDTH,HEIGHT);
			g.setColor(primaryColor);
			g.fillRect(x+WIDTH/10,y+HEIGHT/10,WIDTH*4/5,HEIGHT*4/5);
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
		
		void optionDeselected(){
			this.optionSelected = false;
			this.primaryColor = new Color(255,255,255);
			this.secondaryColor = new Color(0,0,0);
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
		SnakeUI at;
		
		public AnimationThread(SnakeUI _at){
			at=_at;
			start();
		}
		
		public void run(){
			int FPS = fps;
			int SKIP_TICKS = 1000 / FPS;
			long next_game_tick = System.currentTimeMillis();
			long sleep_time = 0;
			while(true){
				if(animationRunning){
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
