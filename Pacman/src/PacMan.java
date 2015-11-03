/*
To compile and run this in NotePad++  Save it as Template.java (needs to be changed to whatever the Java class name is). Press F5 and then paste the line below into the box:

R:\DigiTech\JDK86\comp.bat "$(FULL_CURRENT_PATH)" "$(CURRENT_DIRECTORY)" "$(NAME_PART)"

Then Press Run
----------------------------------------------------------------------------------------------------------------------
Use this file as the starting point for all of your programs in the programming topic. 

*/
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.Applet;
import java.awt.Graphics2D;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.util.ArrayList;

public class PacMan extends JFrame implements ActionListener, KeyListener{
	Canvas c;
	Button startButton, stopButton;
	boolean animationRunning = true;
	private BufferStrategy	strategy;
	boolean deathAnimationMode = false;
	boolean scared = false;
	boolean scoreAnimation = false;
	boolean started = false;
	int flickerCounter = 0;
	int popupScoreX, popupScoreY = 0;
	int scareCounter = 0;
	int numberOfGhostsEaten = 0;
	int numberAdded = 0;
	int startCounter = 0;
	int scoreAnimationCounter = 0;
	int messageCounter = 0;
	ArrayList<Image> pacmanLeft;
	ArrayList<Image> pacmanRight;
	ArrayList<Image> pacmanUp;
	ArrayList<Image> pacmanDown;
	ArrayList<Image> redGhostUp;
	ArrayList<Image> redGhostDown;
	ArrayList<Image> redGhostLeft;
	ArrayList<Image> redGhostRight;
	ArrayList<Image> blueGhostUp;
	ArrayList<Image> blueGhostDown;
	ArrayList<Image> blueGhostLeft;
	ArrayList<Image> blueGhostRight;
	ArrayList<Image> orangeGhostUp;
	ArrayList<Image> orangeGhostDown;
	ArrayList<Image> orangeGhostLeft;
	ArrayList<Image> orangeGhostRight;
	ArrayList<Image> pinkGhostUp;
	ArrayList<Image> pinkGhostDown;
	ArrayList<Image> pinkGhostLeft;
	ArrayList<Image> pinkGhostRight;
	ArrayList<Image> scaredGhost;
	ArrayList<Fruit> fruits;
	ArrayList<Image> numbers;
	ArrayList<Image> letters;
	ArrayList<Image> deathDown;
	ArrayList<Image> deathUp;
	ArrayList<Image> deathLeft;
	ArrayList<Image> deathRight;
	ArrayList<Image> ghostDeathEyes;
	int scaredIndex = 0;
	char[] textToDraw = {'y', 'o', 'u', ' ', 's', 'u', 'c', 'k'};
	int index = 0;
	boolean scareMode = false;
	String direction = "right";
	String preTurn = null;
	String previousDirection = "right";
	String previousPreTurn = null;
	Pacman pacman;
	boolean paused = false;
	Image pacmanMap;
	Image pacmanMap2;
	int mouseX, mouseY = 1;
	int scaredCounter = 0;
	boolean backwards = false;
	int space = 1;
	int row = 27;
	int column = 14;
	int[] pacmansPosition = {row, column};
	int widthOfEachBlock = 8;
	int heightOfEachBlock = 8;
	int mouseColumn = 0;
	int mouseRow = 0;
	int ghostIndex = 0;
	Ghost freddyTheReddy;
	int lives = 3;
	int score = 0;
	int highscore = 0;
	Image iconLives;
	Image gameOverMap;
	Ghost blueEyDaBlueGuy;
	Ghost morangeTheOrange;
	Ghost pinkyTheGayPinkGhost;
	boolean gameOver = false;
	boolean inCenter = false;
	boolean debugMode = false;
	int [][] mapData = {
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
		{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
		{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
		{0,1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1,0},
		{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
		{0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,1,0,1,1,1,1,1,1,0,1,0,0,1,0,0,0,0,0,0},
		{1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1},
		{0,0,0,0,0,0,1,0,0,1,0,1,1,1,1,1,1,0,1,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
		{0,1,0,0,0,0,1,0,0,0,0,0,1,0,0,1,0,0,0,0,0,1,0,0,0,0,1,0},
		{0,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,0},
		{0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
		{0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
		{0,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,0},
		{0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
		{0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0},
		{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		};
	
	public static void main(String s[]) {
		new PacMan();
	}
	static {
		System.setProperty("sun.java2d.transaccel", "True");
		// System.setProperty("sun.java2d.trace", "timestamp,log,count");
		System.setProperty("sun.java2d.opengl", "True");
		//System.setProperty("sun.java2d.d3d", "True");
		System.setProperty("sun.java2d.ddforcevram", "True");
	}
	
	public PacMan(){
		c = new Canvas();
		c.addKeyListener(this);
		c.setSize(224, 288);
		Panel buttonPanel = new Panel();
		buttonPanel.setLayout(new FlowLayout());
		startButton = new Button("Start");
		stopButton = new Button("Stop");
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		startButton.addActionListener( this);
		stopButton.addActionListener( this);
		pacman = new Pacman();
		pacmanLeft = new ArrayList<Image>();
		pacmanRight = new ArrayList<Image>();
		pacmanUp = new ArrayList<Image>();
		pacmanDown = new ArrayList<Image>();
		redGhostDown = new ArrayList<Image>();
		redGhostUp = new ArrayList<Image>();
		redGhostLeft = new ArrayList<Image>();
		redGhostRight = new ArrayList<Image>();
		blueGhostUp = new ArrayList<Image>();
		blueGhostDown = new ArrayList<Image>();
		blueGhostLeft = new ArrayList<Image>();
		blueGhostRight = new ArrayList<Image>();
		orangeGhostUp = new ArrayList<Image>();
		orangeGhostDown = new ArrayList<Image>();
		orangeGhostLeft = new ArrayList<Image>();
		orangeGhostRight = new ArrayList<Image>();
		pinkGhostUp = new ArrayList<Image>();
		pinkGhostDown = new ArrayList<Image>();
		pinkGhostLeft = new ArrayList<Image>();
		pinkGhostRight = new ArrayList<Image>();
		scaredGhost = new ArrayList<Image>();
		fruits = new ArrayList<Fruit>();
		numbers = new ArrayList<Image>();
		letters = new ArrayList<Image>();
		deathDown = new ArrayList<Image>();
		deathUp = new ArrayList<Image>();
		deathLeft = new ArrayList<Image>();
		deathRight = new ArrayList<Image>();
		ghostDeathEyes = new ArrayList<Image>();
		loadAllImages();
		loadAllFruit();
		spawnGhost();
		iconLives = loadImage("../img/lives.png");
		pacmanMap = loadImage("../img/pacmanMap2.png");
		pacmanMap2 = loadImage("../img/pacmanMap3.png");
		gameOverMap = loadImage("../img/pacmanMap4.png");
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
				if(debugMode){
					System.out.println("mouseColumn: " + (mouseColumn) + " mouseRow: " + (mouseRow));
				}
			}

			public void mouseDragged(MouseEvent e) {
			   mouseX = e.getX();
			   mouseY = e.getY();
			   System.out.println("mouseX: " + mouseX + " " + "mouseY: " + mouseY);
			}     
        });
		//this.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		this.getContentPane().add(c, BorderLayout.CENTER);
		this.setTitle("Program Template");
		this.pack();
		this.setVisible(true);
		c.createBufferStrategy(2);
		strategy = c.getBufferStrategy();
		new AnimationThread(this);
	}
	
	public void keyPressed(KeyEvent e){
		char key = e.getKeyChar();
		int keyCode = e.getKeyCode();
		if(key == 'w' || keyCode == KeyEvent.VK_UP && pacman.upIsDisabled == false && deathAnimationMode == false){ //put brackets in to disable the cheat
			direction = "up";
		}else if(key == 'a' || keyCode == KeyEvent.VK_LEFT && pacman.leftIsDisabled == false && deathAnimationMode == false){
			direction = "left";
		}else if(key == 's' || keyCode == KeyEvent.VK_DOWN && pacman.downIsDisabled == false && deathAnimationMode == false){
			direction = "down";
		}else if(key == 'd' || keyCode == KeyEvent.VK_RIGHT && pacman.rightIsDisabled == false && deathAnimationMode == false){
			direction = "right";
		}else  if(key == 'w' || keyCode == KeyEvent.VK_UP && pacman.upIsDisabled == true && deathAnimationMode == false){ //put brackets in to disable the cheat
			preTurn = "up";
		}else if(key == 'a' || keyCode == KeyEvent.VK_LEFT && pacman.leftIsDisabled == true && deathAnimationMode == false){
			preTurn = "left";
		}else if(key == 's' || keyCode == KeyEvent.VK_DOWN && pacman.downIsDisabled == true && deathAnimationMode == false){
			preTurn = "down";
		}else if(key == 'd' || keyCode == KeyEvent.VK_RIGHT && pacman.rightIsDisabled == true && deathAnimationMode == false){
			preTurn = "right";
		}
		if(keyCode == KeyEvent.VK_SPACE){
			if(debugMode){
				debugMode = false;
			}else{
				debugMode = true;
			}
		}
	}
	public void keyReleased(KeyEvent e){
	}
	public void keyTyped(KeyEvent e){
	}
	
	public void loadAllImages(){
		Image pacmanLeft1 = loadImage("../img/minipacman/pacmanLeft1.png");
		Image pacmanLeft2 = loadImage("../img/minipacman/pacmanLeft2.png");
		Image pacmanLeft3 = loadImage("../img/minipacman/pacmanLeft3.png");
		Image pacmanLeft4 = loadImage("../img/minipacman/pacmanLeft4.png");
		
		pacmanLeft.add(pacmanLeft1);
		pacmanLeft.add(pacmanLeft2);
		pacmanLeft.add(pacmanLeft3);
		pacmanLeft.add(pacmanLeft4);
		
		Image pacmanRight1 = loadImage("../img/minipacman/pacmanRight1.png");
		Image pacmanRight2 = loadImage("../img/minipacman/pacmanRight2.png");
		Image pacmanRight3 = loadImage("../img/minipacman/pacmanRight3.png");
		Image pacmanRight4 = loadImage("../img/minipacman/pacmanRight4.png");
		
		pacmanRight.add(pacmanRight1);
		pacmanRight.add(pacmanRight2);
		pacmanRight.add(pacmanRight3);
		pacmanRight.add(pacmanRight4);
		
		Image pacmanDown1 = loadImage("../img/minipacman/pacmanDown1.png");
		Image pacmanDown2 = loadImage("../img/minipacman/pacmanDown2.png");
		Image pacmanDown3 = loadImage("../img/minipacman/pacmanDown3.png");
		Image pacmanDown4 = loadImage("../img/minipacman/pacmanDown4.png");
		
		pacmanDown.add(pacmanDown1);
		pacmanDown.add(pacmanDown2);
		pacmanDown.add(pacmanDown3);
		pacmanDown.add(pacmanDown4);
		
		Image pacmanUp1 = loadImage("../img/minipacman/pacmanUp1.png");
		Image pacmanUp2 = loadImage("../img/minipacman/pacmanUp2.png");
		Image pacmanUp3 = loadImage("../img/minipacman/pacmanUp3.png");
		Image pacmanUp4 = loadImage("../img/minipacman/pacmanUp4.png");
		
		pacmanUp.add(pacmanUp1);
		pacmanUp.add(pacmanUp2);
		pacmanUp.add(pacmanUp3);
		pacmanUp.add(pacmanUp4);
		
		Image redUp1 = loadImage("../img/ghosts/redUp1.png");
		Image redUp2 = loadImage("../img/ghosts/redUp2.png");
		redGhostUp.add(redUp1);
		redGhostUp.add(redUp2);
		Image redDown1 = loadImage("../img/ghosts/redDown1.png");
		Image redDown2 = loadImage("../img/ghosts/redDown2.png");
		redGhostDown.add(redDown1);
		redGhostDown.add(redDown2);
		Image redLeft1 = loadImage("../img/ghosts/redleft1.png");
		Image redLeft2 = loadImage("../img/ghosts/redleft2.png");
		redGhostLeft.add(redLeft1);
		redGhostLeft.add(redLeft2);
		Image redRight1 = loadImage("../img/ghosts/redRight1.png");
		Image redRight2 = loadImage("../img/ghosts/redRight2.png");
		redGhostRight.add(redRight1);
		redGhostRight.add(redRight2);
		
		Image blueUp1 = loadImage("../img/ghosts/blueUp1.png");
		Image blueUp2 = loadImage("../img/ghosts/blueUp2.png");
		blueGhostUp.add(blueUp1);
		blueGhostUp.add(blueUp2);
		Image blueDown1 = loadImage("../img/ghosts/blueDown1.png");
		Image blueDown2 = loadImage("../img/ghosts/blueDown2.png");
		blueGhostDown.add(blueDown1);
		blueGhostDown.add(blueDown2);
		Image blueLeft1 = loadImage("../img/ghosts/blueleft1.png");
		Image blueLeft2 = loadImage("../img/ghosts/blueleft2.png");
		blueGhostLeft.add(blueLeft1);
		blueGhostLeft.add(blueLeft2);
		Image blueRight1 = loadImage("../img/ghosts/blueRight1.png");
		Image blueRight2 = loadImage("../img/ghosts/blueRight2.png");
		blueGhostRight.add(blueRight1);
		blueGhostRight.add(blueRight2);
		
		Image orangeUp1 = loadImage("../img/ghosts/orangeUp1.png");
		Image orangeUp2 = loadImage("../img/ghosts/orangeUp2.png");
		orangeGhostUp.add(orangeUp1);
		orangeGhostUp.add(orangeUp2);
		Image orangeDown1 = loadImage("../img/ghosts/orangeDown1.png");
		Image orangeDown2 = loadImage("../img/ghosts/orangeDown2.png");
		orangeGhostDown.add(orangeDown1);
		orangeGhostDown.add(orangeDown2);
		Image orangeLeft1 = loadImage("../img/ghosts/orangeleft1.png");
		Image orangeLeft2 = loadImage("../img/ghosts/orangeleft2.png");
		orangeGhostLeft.add(orangeLeft1);
		orangeGhostLeft.add(orangeLeft2);
		Image orangeRight1 = loadImage("../img/ghosts/orangeRight1.png");
		Image orangeRight2 = loadImage("../img/ghosts/orangeRight2.png");
		orangeGhostRight.add(orangeRight1);
		orangeGhostRight.add(orangeRight2);
		
		Image scaredGhost1 = loadImage("../img/ghosts/scared1.png");
		Image scaredGhost2 = loadImage("../img/ghosts/scared2.png");
		Image scaredGhost3 = loadImage("../img/ghosts/scared3.png");
		Image scaredGhost4 = loadImage("../img/ghosts/scared4.png");
		scaredGhost.add(scaredGhost1);
		scaredGhost.add(scaredGhost2);
		scaredGhost.add(scaredGhost3);
		scaredGhost.add(scaredGhost4);
		
		Image pinkUp1 = loadImage("../img/ghosts/pinkUp1.png");
		Image pinkUp2 = loadImage("../img/ghosts/pinkUp2.png");
		pinkGhostUp.add(pinkUp1);
		pinkGhostUp.add(pinkUp2);
		Image pinkDown1 = loadImage("../img/ghosts/pinkDown1.png");
		Image pinkDown2 = loadImage("../img/ghosts/pinkDown2.png");
		pinkGhostDown.add(pinkDown1);
		pinkGhostDown.add(pinkDown2);
		Image pinkLeft1 = loadImage("../img/ghosts/pinkleft1.png");
		Image pinkLeft2 = loadImage("../img/ghosts/pinkleft2.png");
		pinkGhostLeft.add(pinkLeft1);
		pinkGhostLeft.add(pinkLeft2);
		Image pinkRight1 = loadImage("../img/ghosts/pinkRight1.png");
		Image pinkRight2 = loadImage("../img/ghosts/pinkRight2.png");
		pinkGhostRight.add(pinkRight1);
		pinkGhostRight.add(pinkRight2);
		
		Image number0 = loadImage("../img/numbers/0.png");
		Image number1 = loadImage("../img/numbers/1.png");
		Image number2 = loadImage("../img/numbers/2.png");
		Image number3 = loadImage("../img/numbers/3.png");
		Image number4 = loadImage("../img/numbers/4.png");
		Image number5 = loadImage("../img/numbers/5.png");
		Image number6 = loadImage("../img/numbers/6.png");
		Image number7 = loadImage("../img/numbers/7.png");
		Image number8 = loadImage("../img/numbers/8.png");
		Image number9 = loadImage("../img/numbers/9.png");
		numbers.add(number0);
		numbers.add(number1);
		numbers.add(number2);
		numbers.add(number3);
		numbers.add(number4);
		numbers.add(number5);
		numbers.add(number6);
		numbers.add(number7);
		numbers.add(number8);
		numbers.add(number9);
		
		Image a = loadImage("../img/letters/yellow/a.png");
		Image b = loadImage("../img/letters/yellow/b.png");
		Image c = loadImage("../img/letters/yellow/c.png");
		Image d = loadImage("../img/letters/yellow/d.png");
		Image e = loadImage("../img/letters/yellow/e.png");
		Image f = loadImage("../img/letters/yellow/f.png");
		Image g = loadImage("../img/letters/yellow/g.png");
		Image h = loadImage("../img/letters/yellow/h.png");
		Image i = loadImage("../img/letters/yellow/i.png");
		Image j = loadImage("../img/letters/yellow/j.png");
		Image k = loadImage("../img/letters/yellow/k.png");
		Image l = loadImage("../img/letters/yellow/l.png");
		Image m = loadImage("../img/letters/yellow/m.png");
		Image n = loadImage("../img/letters/yellow/n.png");
		Image o = loadImage("../img/letters/yellow/o.png");
		Image p = loadImage("../img/letters/yellow/p.png");
		Image q = loadImage("../img/letters/yellow/q.png");
		Image r = loadImage("../img/letters/yellow/r.png");
		Image s = loadImage("../img/letters/yellow/s.png");
		Image t = loadImage("../img/letters/yellow/t.png");
		Image u = loadImage("../img/letters/yellow/u.png");
		Image v = loadImage("../img/letters/yellow/v.png");
		Image w = loadImage("../img/letters/yellow/w.png");
		Image x = loadImage("../img/letters/yellow/x.png");
		Image y = loadImage("../img/letters/yellow/y.png");
		Image z = loadImage("../img/letters/yellow/z.png");
		letters.add(a);
		letters.add(b);
		letters.add(c);
		letters.add(d);
		letters.add(e);
		letters.add(f);
		letters.add(g);
		letters.add(h);
		letters.add(i);
		letters.add(j);
		letters.add(k);
		letters.add(l);
		letters.add(m);
		letters.add(n);
		letters.add(o);
		letters.add(p);
		letters.add(q);
		letters.add(r);
		letters.add(s);
		letters.add(t);
		letters.add(u);
		letters.add(v);
		letters.add(w);
		letters.add(x);
		letters.add(y);
		letters.add(z);
		
		Image deathDown1 = loadImage("../img/minipacman/deathDown1.png");
		Image deathDown2 = loadImage("../img/minipacman/deathDown2.png");
		Image deathDown3 = loadImage("../img/minipacman/deathDown3.png");
		Image deathDown4 = loadImage("../img/minipacman/deathDown4.png");
		Image deathDown5 = loadImage("../img/minipacman/deathDown5.png");
		Image deathDown6 = loadImage("../img/minipacman/deathDown6.png");
		Image deathDown7 = loadImage("../img/minipacman/deathDown7.png");
		Image deathDown8 = loadImage("../img/minipacman/deathDown8.png");
		Image deathDown9 = loadImage("../img/minipacman/deathDown9.png");
		Image deathDown10 = loadImage("../img/minipacman/deathDown10.png");
		Image deathDown11 = loadImage("../img/minipacman/deathDown11.png");
		deathDown.add(deathDown1);
		deathDown.add(deathDown2);
		deathDown.add(deathDown3);
		deathDown.add(deathDown4);
		deathDown.add(deathDown5);
		deathDown.add(deathDown6);
		deathDown.add(deathDown7);
		deathDown.add(deathDown8);
		deathDown.add(deathDown9);
		deathDown.add(deathDown10);
		deathDown.add(deathDown11);
		
		Image deathUp1 = loadImage("../img/minipacman/deathUp1.png");
		Image deathUp2 = loadImage("../img/minipacman/deathUp2.png");
		Image deathUp3 = loadImage("../img/minipacman/deathUp3.png");
		Image deathUp4 = loadImage("../img/minipacman/deathUp4.png");
		Image deathUp5 = loadImage("../img/minipacman/deathUp5.png");
		Image deathUp6 = loadImage("../img/minipacman/deathUp6.png");
		Image deathUp7 = loadImage("../img/minipacman/deathUp7.png");
		Image deathUp8 = loadImage("../img/minipacman/deathUp8.png");
		Image deathUp9 = loadImage("../img/minipacman/deathUp9.png");
		Image deathUp10 = loadImage("../img/minipacman/deathUp10.png");
		Image deathUp11 = loadImage("../img/minipacman/deathUp11.png");
		deathUp.add(deathUp1);
		deathUp.add(deathUp2);
		deathUp.add(deathUp3);
		deathUp.add(deathUp4);
		deathUp.add(deathUp5);
		deathUp.add(deathUp6);
		deathUp.add(deathUp7);
		deathUp.add(deathUp8);
		deathUp.add(deathUp9);
		deathUp.add(deathUp10);
		deathUp.add(deathUp11);
		
		Image deathLeft1 = loadImage("../img/minipacman/deathLeft1.png");
		Image deathLeft2 = loadImage("../img/minipacman/deathLeft2.png");
		Image deathLeft3 = loadImage("../img/minipacman/deathLeft3.png");
		Image deathLeft4 = loadImage("../img/minipacman/deathLeft4.png");
		Image deathLeft5 = loadImage("../img/minipacman/deathLeft5.png");
		Image deathLeft6 = loadImage("../img/minipacman/deathLeft6.png");
		Image deathLeft7 = loadImage("../img/minipacman/deathLeft7.png");
		Image deathLeft8 = loadImage("../img/minipacman/deathLeft8.png");
		Image deathLeft9 = loadImage("../img/minipacman/deathLeft9.png");
		Image deathLeft10 = loadImage("../img/minipacman/deathLeft10.png");
		Image deathLeft11 = loadImage("../img/minipacman/deathLeft11.png");
		deathLeft.add(deathLeft1);
		deathLeft.add(deathLeft2);
		deathLeft.add(deathLeft3);
		deathLeft.add(deathLeft4);
		deathLeft.add(deathLeft5);
		deathLeft.add(deathLeft6);
		deathLeft.add(deathLeft7);
		deathLeft.add(deathLeft8);
		deathLeft.add(deathLeft9);
		deathLeft.add(deathLeft10);
		deathLeft.add(deathLeft11);
		
		Image deathRight1 = loadImage("../img/minipacman/deathRight1.png");
		Image deathRight2 = loadImage("../img/minipacman/deathRight2.png");
		Image deathRight3 = loadImage("../img/minipacman/deathRight3.png");
		Image deathRight4 = loadImage("../img/minipacman/deathRight4.png");
		Image deathRight5 = loadImage("../img/minipacman/deathRight5.png");
		Image deathRight6 = loadImage("../img/minipacman/deathRight6.png");
		Image deathRight7 = loadImage("../img/minipacman/deathRight7.png");
		Image deathRight8 = loadImage("../img/minipacman/deathRight8.png");
		Image deathRight9 = loadImage("../img/minipacman/deathRight9.png");
		Image deathRight10 = loadImage("../img/minipacman/deathRight10.png");
		Image deathRight11 = loadImage("../img/minipacman/deathRight11.png");
		deathRight.add(deathRight1);
		deathRight.add(deathRight2);
		deathRight.add(deathRight3);
		deathRight.add(deathRight4);
		deathRight.add(deathRight5);
		deathRight.add(deathRight6);
		deathRight.add(deathRight7);
		deathRight.add(deathRight8);
		deathRight.add(deathRight9);
		deathRight.add(deathRight10);
		deathRight.add(deathRight11);
		
		Image up = loadImage("../img/eyes/up.png");
		Image down = loadImage("../img/eyes/down.png");
		Image left = loadImage("../img/eyes/left.png");
		Image right = loadImage("../img/eyes/right.png");
		ghostDeathEyes.add(up);
		ghostDeathEyes.add(left);
		ghostDeathEyes.add(right);
		ghostDeathEyes.add(down);
	}
	
	public Image getGhostEyes(String direction){
		if(direction == "up"){
			return ghostDeathEyes.get(0);
		}else if(direction == "left"){
			return ghostDeathEyes.get(1);
		}else if(direction == "right"){
			return ghostDeathEyes.get(2);
		}else{
			return ghostDeathEyes.get(3);
		}
	}
	
	public Image loadImage(String url){
		BufferedImage image = null;
		try{
		image = ImageIO.read(new File(url));
		}catch(Exception e){
			System.out.println("Error while loading image: " + url);
		}
		return image;
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
	
	public int fruitX(int column){
		return ((column-1)*widthOfEachBlock);
	}
	
	public int fruitY(int row){
		return ((row-1)*heightOfEachBlock);
	}
	
	public void addFruitColumn(int min, int max,int column,int superFruitLocation){
		for(int i = min; i < (max+1); i++){
			Fruit fruit = new Fruit();
			fruit.row = i;
			fruit.column = column;
			fruit.x = fruitX(fruit.column);
			fruit.y = fruitY(fruit.row);
			fruit.width = widthOfEachBlock/2;
			fruit.height = heightOfEachBlock/2;
			if(i == superFruitLocation){
				fruit.superFruit = true;
				fruit.width = widthOfEachBlock;
				fruit.height = heightOfEachBlock;
			}else{
				fruit.superFruit = false;
			}
			fruits.add(fruit);
		}
	}
	
	public void addFruitColumn(int min, int max, int column){
		for(int i = min; i < (max+1); i++){
			Fruit fruit = new Fruit();
			fruit.row = i;
			fruit.column = column;
			fruit.superFruit = false;
			fruit.x = fruitX(fruit.column);
			fruit.y = fruitY(fruit.row);
			fruits.add(fruit);
		}
	}
	
	public void addFruitRow(int min, int max, int row){
		for(int i=min; i<(max+1); i++){
			Fruit fruit = new Fruit();
			fruit.row = row;
			fruit.column = i;
			fruit.superFruit = false;
			fruit.x = fruitX(fruit.column);
			fruit.y = fruitY(fruit.row);
			fruits.add(fruit);
		}
	}
	
	public void addFruitRow(int min, int max, int row, int superFruitLocation){
		for(int i=min; i<(max+1); i++){
			Fruit fruit = new Fruit();
			fruit.row = row;
			fruit.column = i;
			fruit.width = widthOfEachBlock/2;
			fruit.height = heightOfEachBlock/2;
			fruit.x = fruitX(fruit.column);
			fruit.y = fruitY(fruit.row);
			if(i == superFruitLocation){
				fruit.superFruit = true;
				fruit.width = widthOfEachBlock;
				fruit.height = heightOfEachBlock;
			}else{
				fruit.superFruit = false;
			}
			fruits.add(fruit);
		}
	}
	
	public void loadAllFruit(){
		Fruit fruit = new Fruit();
		fruit.row = 5;
		fruit.column = 2;
		fruit.x = fruitX(fruit.column);
		fruit.y = fruitY(fruit.row);
		fruit.superFruit = false;
		fruits.add(fruit);
		addFruitColumn(5, 12, 2, 7);
		addFruitRow(3, 13, 5);
		addFruitRow(2, 27, 9);
		addFruitRow(16, 27, 5);
		addFruitColumn(6, 8, 16);
		addFruitColumn(6, 8, 27, 7);
		addFruitColumn(6, 8, 7);
		addFruitColumn(10, 30, 7);
		addFruitColumn(10, 30, 22);
		addFruitColumn(10, 12, 27);
		addFruitColumn(10, 12, 19);
		addFruitColumn(10, 12, 10);
		addFruitRow(3, 6, 12);
		addFruitRow(23, 26, 12);
		addFruitRow(11, 13, 12);
		addFruitRow(16, 19, 12);
		addFruitColumn(24, 27, 2, 27);
		addFruitColumn(30, 33, 2);
		addFruitColumn(24, 27, 27, 27);
		addFruitColumn(30, 33, 27);
		addFruitRow(3, 26, 33);
		addFruitRow(8, 13, 27);
		addFruitRow(16, 21, 27);
		addFruitRow(3, 6, 30);
		addFruitRow(23, 26, 30);
		addFruitRow(11, 12, 30);
		addFruitRow(17, 18, 30);
		addFruitColumn(28, 30, 19);
		addFruitColumn(28, 30, 10);
		addFruitColumn(30, 32, 16);
		addFruitColumn(30, 32, 13);
		addFruitColumn(28, 29, 4);
		addFruitColumn(28, 29, 25);
		addFruitRow(25, 26, 27);
		addFruitRow(3, 4, 27);
		addFruitRow(3, 6, 24);
		addFruitRow(8, 12, 24);
		addFruitRow(17, 21, 24);
		addFruitRow(23, 26, 24);
		addFruitColumn(24, 26, 16);
		addFruitColumn(24, 26, 13);
		addFruitColumn(6, 8, 22);
		addFruitColumn(6, 8, 13);
		Fruit cherry = new Fruit();
	}
	
	public void spawnGhost(){
		freddyTheReddy = new Ghost();
		blueEyDaBlueGuy = new Ghost();
		morangeTheOrange = new Ghost();
		pinkyTheGayPinkGhost = new Ghost();
		freddyTheReddy.row = 15;
		freddyTheReddy.column = 14;
		freddyTheReddy.x = 112;
		freddyTheReddy.y = 116;
		blueEyDaBlueGuy.x = 96;
		blueEyDaBlueGuy.y = 140;
		pinkyTheGayPinkGhost.x = 112;
		pinkyTheGayPinkGhost.y = 140;
		morangeTheOrange.x = 128;
		morangeTheOrange.y = 140;
		mapData[15][13] = 1;
		mapData[15][14] = 1;
		mapData[15][12] = 1;
		mapData[15][11] = 1;
		mapData[15][15] = 1;
		mapData[15][16] = 1;
	}
	
	public void updateGhostPosition(){
		freddyTheReddy.column = (freddyTheReddy.x/widthOfEachBlock);
		freddyTheReddy.row = (freddyTheReddy.y/heightOfEachBlock);
		morangeTheOrange.column = (morangeTheOrange.x/widthOfEachBlock);
		morangeTheOrange.row = (morangeTheOrange.y/heightOfEachBlock);
		blueEyDaBlueGuy.column = (blueEyDaBlueGuy.x/widthOfEachBlock);
		blueEyDaBlueGuy.row = (blueEyDaBlueGuy.y/heightOfEachBlock);
		pinkyTheGayPinkGhost.column = (pinkyTheGayPinkGhost.x/widthOfEachBlock);
		pinkyTheGayPinkGhost.row = (pinkyTheGayPinkGhost.y/heightOfEachBlock);
	}
	
	public void drawGhosts(){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		Image redGhost = null;
		if(freddyTheReddy.deadEyesMode){
			redGhost = getGhostEyes(freddyTheReddy.direction);
		}else{
			if(freddyTheReddy.scaredMode == false){
				if(freddyTheReddy.direction == "up"){
					redGhost = redGhostUp.get(ghostIndex);
				}else if(freddyTheReddy.direction == "down"){
					redGhost = redGhostDown.get(ghostIndex);
				}else if(freddyTheReddy.direction == "left"){
					redGhost = redGhostLeft.get(ghostIndex);
				}else if(freddyTheReddy.direction == "right"){
					redGhost = redGhostRight.get(ghostIndex);
				}
			}else{
				redGhost = scaredGhost.get(scaredIndex);
			}
		}
		g.drawImage(redGhost, (freddyTheReddy.x - freddyTheReddy.width/2), (freddyTheReddy.y - freddyTheReddy.height/2), null);
		Image blueGhost = null;
		if(blueEyDaBlueGuy.deadEyesMode){
			blueGhost = getGhostEyes(blueEyDaBlueGuy.direction);
		}else{
			if(blueEyDaBlueGuy.scaredMode == false){
				if(blueEyDaBlueGuy.direction == "up"){
					blueGhost = blueGhostUp.get(ghostIndex);
				}else if(blueEyDaBlueGuy.direction == "down"){
					blueGhost = blueGhostDown.get(ghostIndex);
				}else if(blueEyDaBlueGuy.direction == "left"){
					blueGhost = blueGhostLeft.get(ghostIndex);
				}else if(blueEyDaBlueGuy.direction == "right"){
					blueGhost = blueGhostRight.get(ghostIndex);
				}
			}else{
				blueGhost = scaredGhost.get(scaredIndex);
			}
		}
		g.drawImage(blueGhost, (blueEyDaBlueGuy.x - blueEyDaBlueGuy.width/2), (blueEyDaBlueGuy.y - blueEyDaBlueGuy.height/2), null);
		Image orangeGhost = null;
		if(morangeTheOrange.deadEyesMode){
			orangeGhost = getGhostEyes(morangeTheOrange.direction);
		}else{
			if(morangeTheOrange.scaredMode == false){
				if(morangeTheOrange.direction == "up"){
					orangeGhost = orangeGhostUp.get(ghostIndex);
				}else if(morangeTheOrange.direction == "down"){
					orangeGhost = orangeGhostDown.get(ghostIndex);
				}else if(morangeTheOrange.direction == "left"){
					orangeGhost = orangeGhostLeft.get(ghostIndex);
				}else if(morangeTheOrange.direction == "right"){
					orangeGhost = orangeGhostRight.get(ghostIndex);
				}
			}else{
				orangeGhost = scaredGhost.get(scaredIndex);
			}
		}
		g.drawImage(orangeGhost, (morangeTheOrange.x - morangeTheOrange.width/2), (morangeTheOrange.y - morangeTheOrange.height/2), null);
		Image pinkGhost = null;
		if(pinkyTheGayPinkGhost.deadEyesMode){
			pinkGhost = getGhostEyes(pinkyTheGayPinkGhost.direction);
		}else{
			if(pinkyTheGayPinkGhost.scaredMode == false){
				if(pinkyTheGayPinkGhost.direction == "up"){
					pinkGhost = pinkGhostUp.get(ghostIndex);
				}else if(pinkyTheGayPinkGhost.direction == "down"){
					pinkGhost = pinkGhostDown.get(ghostIndex);
				}else if(pinkyTheGayPinkGhost.direction == "left"){
					pinkGhost = pinkGhostLeft.get(ghostIndex);
				}else if(pinkyTheGayPinkGhost.direction == "right"){
					pinkGhost = pinkGhostRight.get(ghostIndex);
				}
			}else{
				pinkGhost = scaredGhost.get(scaredIndex);
			}
		}
		g.drawImage(pinkGhost, (pinkyTheGayPinkGhost.x - pinkyTheGayPinkGhost.width/2), (pinkyTheGayPinkGhost.y - pinkyTheGayPinkGhost.height/2), null);
	}
	
	public void displayPopupScore(int number){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		g.setColor(Color.black);
		Image image = null;
		if(number == 200){
			image = loadImage("../img/numbers/popupScore/200.png");
		}else if(number == 400){
			image = loadImage("../img/numbers/popupScore/400.png");
		}else if(number == 800){
			image = loadImage("../img/numbers/popupScore/800.png");
		}else if(number == 1600){
			image = loadImage("../img/numbers/popupScore/1600.png");
		}else if(number == 300){
			image = loadImage("../img/numbers/popupScore/300.png");
		}else if(number == 500){
			image = loadImage("../img/numbers/popupScore/500.png");
		}else if(number == 1000){
			image = loadImage("../img/numbers/popupScore/1000.png");
		}else if(number == 2000){
			image = loadImage("../img/numbers/popupScore/2000.png");
		}else if(number == 3000){
			image = loadImage("../img/numbers/popupScore/3000.png");
		}else if(number == 5000){
			image = loadImage("../img/numbers/popupScore/5000.png");
		}
		g.drawImage(image, popupScoreX, popupScoreY, null);
	}
	
	public void updateCanvas(){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();  
		g.setColor(Color.black);
		g.fillRect(0,0,c.getWidth(), c.getHeight()); //clears screen
		//Drawing goes in here
		if(gameOver){
			if(flickerCounter < 10){
				flickerCounter++;
			}else{
				flickerCounter = 0;
			}
			if(flickerCounter >= 5){
				g.drawImage(gameOverMap,0,0,null);
			}else{
				g.drawImage(pacmanMap2, 0,0,null);
			}
		}else{
			if(started){
				g.drawImage(pacmanMap2,0,0,null);
			}else{
				g.drawImage(pacmanMap, 0,0,null);
			}
			Image img = null;
			if(deathAnimationMode == false){
				if(direction == "left"){
					img = pacmanLeft.get(index);
				}else if(direction == "right"){
					img = pacmanRight.get(index);
				}else if(direction == "up"){
					img = pacmanUp.get(index);
				}else if(direction == "down"){
					img = pacmanDown.get(index);
				}else if(direction == "still"){
					if(previousDirection == "down"){
						img = pacmanDown.get(index);
					}else if(previousDirection == "up"){
						img = pacmanUp.get(index);
					}else if(previousDirection == "left"){
						img = pacmanLeft.get(index);
					}else if(previousDirection == "right"){
						img = pacmanRight.get(index);
					}
				}
			}else{
				drawGhosts();
				if(direction == "down"){
					img = deathDown.get(index);
				}else if(direction == "left"){
					img = deathLeft.get(index);
				}else if(direction == "up"){
					img = deathUp.get(index);
				}else if(direction == "right"){
					img = deathRight.get(index);
				}
				System.out.println("index: " + index);
			}
			
			g.drawImage(img, (int)(pacman.x-(pacman.width/2)),(int)(pacman.y-(pacman.height/2)),null);
			if(gameOver == false){
				for(int i=0; i<fruits.size(); i++){
				g.setColor(new Color(255,184,151));
				Fruit fruit = fruits.get(i);
				if(fruit.eaten == false){
					if(fruit.superFruit){
						g.fillOval(fruit.x, fruit.y, fruit.width, fruit.height);
					}else{
						g.fillOval(fruit.x + (fruit.width/2), fruit.y + (fruit.height/2), fruit.width, fruit.height);
					}
				}
			}
		}
			
			if(deathAnimationMode == false){
				drawGhosts();
			}
		}
		
		Image deatH = null;
		if(deathAnimationMode){
			drawGhosts();
			if(direction == "down"){
				deatH = deathDown.get(index);
			}else if(direction == "left"){
				deatH = deathLeft.get(index);
			}else if(direction == "up"){
				deatH = deathUp.get(index);
			}else if(direction == "right"){
				deatH = deathRight.get(index);
			}
			System.out.println("index: " + index);
		}
		g.drawImage(deatH, (int)(pacman.x-(pacman.width/2)),(int)(pacman.y-(pacman.height/2)),null);
		if(debugMode){
				g.setColor(Color.white);
				for(double y=0; y < 288; y+= heightOfEachBlock){
					g.drawLine(0, (int)y, c.getWidth(), (int)y);
				}
				for(double x=0; x < 224; x+= widthOfEachBlock){
					g.drawLine((int)x, 0, (int)x, 471);
				}
				mouseColumn = (int)(mouseX/widthOfEachBlock);
				mouseRow = (int)(mouseY/heightOfEachBlock);
				if(mapData[mouseRow][mouseColumn] == 0){
					g.setColor(Color.blue);
				}else{
					g.setColor(Color.white);
				}
				g.fillRect((mouseColumn*heightOfEachBlock), (mouseRow*heightOfEachBlock), widthOfEachBlock, heightOfEachBlock);
			}
		int number4 = 11;
		int number3 = 11;
		int number2 = 11;
		int number = 0;
		int j = (int)Math.floor(score/100);
		int k = (int)Math.floor(score/1000);
		int l = (int)Math.floor(score/10000);
		if(score < 100){
			number = score/10;
		}else if(score < 1000){
			number = (score-(100*j))/10;
			number2 = j;
		}else if(score < 10000){
			number3 = k;
			number2 = (int)Math.floor((score-(1000*k))/100);
			number = (score-((number3*1000)+(number2*100)))/10;
		}else if(score < 100000){
			number4 = l;
			number3 = (int)Math.floor((score-(10000*l))/1000);
			number2 = (int)Math.floor((score-((number4*10000)+(number3*1000)))/100);
			number = (int)Math.floor((score-((number4*10000)+(number3*1000)+(number2*100)))/10);
		}
		g.drawImage(numbers.get(number), 41, 9, null);
		if(number2 != 11){
			g.drawImage(numbers.get(number2), 34,9,null);
		};
		if(number3 != 11){
			g.drawImage(numbers.get(number3), 27, 9, null);
		}
		if(number4 != 11){
			g.drawImage(numbers.get(number4), 20, 9, null);
		}
		if(score > highscore){
			highscore = score;
		}
		int jh = (int)Math.floor(highscore/100);
		int number3h = 11;
		int number2h = 11;
		int numberh = 0;
		int kh = (int)Math.floor(highscore/1000);
		if(highscore < 100){
			numberh = highscore/10;
		}else if(highscore < 1000){
			numberh = (highscore-(100*jh))/10;
			number2h = jh;
		}else if(highscore < 10000){
			number3h = kh;
			number2h = (int)Math.floor((highscore-(1000*kh))/100);
			numberh = (highscore-((number3h*1000)+(number2h*100)))/10;
		}
		g.drawImage(numbers.get(numberh), 121, 9, null);
		if(number2h != 11){
			g.drawImage(numbers.get(number2h), 114,9,null);
		}
		if(number3h != 11){
			g.drawImage(numbers.get(number3h), 107, 9, null);
		}
		if(gameOver){
			for(int i=0; i<fruits.size(); i++){
				g.setColor(new Color(255,184,151));
				Fruit fruit = fruits.get(i);
				if(fruit.eaten == false){
					if(fruit.superFruit){
						g.fillOval(fruit.x, fruit.y, fruit.width, fruit.height);
					}else{
						g.fillOval(fruit.x + (fruit.width/2), fruit.y + (fruit.height/2), fruit.width, fruit.height);
					}
				}
			}
			messageCounter++;
			char[] message = {'g', 'a', 'm', 'e',' '};
			char[] message2 = {'o', 'v', 'e', 'r'};
			if(messageCounter >= 10){
				drawText(message, 71,109);
			}
			if(messageCounter >= 25){
				drawText(message2, 71+(8*message.length), 109);
			}
		}
		//g.drawImage(numbers.get(nifkldsfjkld), 121,9, null);
		if(lives == 2){
			g.drawImage(iconLives, 19, 274, null);
		}else if(lives == 3){
			g.drawImage(iconLives, 35, 274, null);
			g.drawImage(iconLives, 19, 274, null);
		}else if(lives == 1){
			drawLetter('y', 19, 274);
			drawText(textToDraw, 19, 274);
		}
		if(scoreAnimation){
			popupScoreY--;
			scoreAnimationCounter++;
			displayPopupScore(numberAdded);
			if(scoreAnimationCounter > 5){
				scoreAnimation = false;
				scoreAnimationCounter = 0;
			}
		}
		g.dispose();
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void drawText(char[] text, int x, int y){
		for(int i = 0; i < text.length; i++){
			char letter = text[i];
			drawLetter(letter, x, y);
			x+=8;
		}
		
	}
	
	public void drawLetter(char letter, int x, int y){
		Graphics2D g= (Graphics2D)strategy.getDrawGraphics();
		if(letter == 'a' || letter == 'A'){
			g.drawImage(letters.get(0), x, y, null);
		}else if(letter == 'b' || letter == 'B'){
			g.drawImage(letters.get(1), x, y, null);
		}else if(letter == 'c' || letter == 'C'){
			g.drawImage(letters.get(2), x, y, null);
		}else if(letter == 'd' || letter == 'D'){
			g.drawImage(letters.get(3), x, y, null);
		}else if(letter == 'e' || letter == 'E'){
			g.drawImage(letters.get(4), x, y, null);
		}else if(letter == 'f' || letter == 'F'){
			g.drawImage(letters.get(5), x, y, null);
		}else if(letter == 'g' || letter == 'G'){
			g.drawImage(letters.get(6), x, y, null);
		}else if(letter == 'h' || letter == 'H'){
			g.drawImage(letters.get(7), x, y, null);
		}else if(letter == 'i' || letter == 'I'){
			g.drawImage(letters.get(8), x, y, null);
		}else if(letter == 'j' || letter == 'J'){
			g.drawImage(letters.get(9), x, y, null);
		}else if(letter == 'k' || letter == 'K'){
			g.drawImage(letters.get(10), x, y, null);
		}else if(letter == 'l' || letter == 'L'){
			g.drawImage(letters.get(11), x, y, null);
		}else if(letter == 'm' || letter == 'M'){
			g.drawImage(letters.get(12), x, y, null);
		}else if(letter == 'n' || letter == 'N'){
			g.drawImage(letters.get(13), x, y, null);
		}else if(letter == 'o' || letter == 'O'){
			g.drawImage(letters.get(14), x, y, null);
		}else if(letter == 'p' || letter == 'P'){
			g.drawImage(letters.get(15), x, y, null);
		}else if(letter == 'q' || letter == 'Q'){
			g.drawImage(letters.get(16), x, y, null);
		}else if(letter == 'r' || letter == 'R'){
			g.drawImage(letters.get(17), x, y, null);
		}else if(letter == 's' || letter == 'S'){
			g.drawImage(letters.get(18), x, y, null);
		}else if(letter == 't' || letter == 'T'){
			g.drawImage(letters.get(19), x, y, null);
		}else if(letter == 'u' || letter == 'U'){
			g.drawImage(letters.get(20), x, y, null);
		}else if(letter == 'v' || letter == 'V'){
			g.drawImage(letters.get(21), x, y, null);
		}else if(letter == 'w' || letter == 'W'){
			g.drawImage(letters.get(22), x, y, null);
		}else if(letter == 'x' || letter == 'X'){
			g.drawImage(letters.get(23), x, y, null);
		}else if(letter == 'y' || letter == 'Y'){
			g.drawImage(letters.get(24), x, y, null);
		}else if(letter == 'z' || letter == 'Z'){
			g.drawImage(letters.get(25), x, y, null);
		}
	}
	
	public double getDistance(double x, double y, double x2, double y2){
		return (Math.sqrt(((x-x2) * (x-x2)) + ((y-y2)*(y-y2))));
	}
	
	public void updatePacmansPosition(){
		row = pacman.y/heightOfEachBlock;
		column = pacman.x/widthOfEachBlock;
	}
	
	public void updateFruit(){
		for(int i=0; i<fruits.size(); i++){
			Fruit fruit = fruits.get(i);
			if(fruit.row == (row+1) && fruit.column == (column+1)){
				if(fruit.eaten == false){
					score+= 10;
					if(fruit.superFruit == true){
						numberOfGhostsEaten = 0;
						freddyTheReddy.scaredMode = true;
						morangeTheOrange.scaredMode = true;
						pinkyTheGayPinkGhost.scaredMode = true;
						blueEyDaBlueGuy.scaredMode = true;
						scareCounter = 0;
						scared = true;
					}
				}
				fruit.eaten = true;
			}
		}
	}
	
	public void randomDirection(Ghost ghost, String direction1, String direction2){
		int randomInt = randomInteger(1,2);
		if(randomInt == 1){
			ghost.previousDirection = ghost.direction;
			ghost.direction = direction1;
		}else{
			ghost.previousDirection = ghost.direction;
			ghost.direction = direction2;
		}
	}
	
	public void randomDirection(Ghost ghost, String direction1, String direction2, String direction3){
		int randomInt = randomInteger(1,3);
		if(randomInt == 1){
			if(ghost.previousDirection != direction1){
				ghost.previousDirection = direction1;
			}
			ghost.direction = direction1;
		}else if(randomInt == 2){
			ghost.previousDirection = direction2;
			ghost.direction = direction2;
		}else{
			ghost.previousDirection = direction3;
			ghost.direction = direction3;
		}
	}
	
	public void moveGhost(Ghost ghost){
		
		if(ghost.deadEyesMode){
			int targetX = 112;
			int targetY = 140;
			if(ghost.x > targetX){
				ghost.xPriority = "left";
			}
			if(ghost.x < targetX){
				ghost.xPriority = "right";
			}
			if(ghost.y > targetY){
				ghost.yPriority = "up";
			}
			if(ghost.y < targetY){
				ghost.yPriority = "down";
			}
			if(ghost.x == targetX){
				ghost.xPriority = "onTarget";
			}
			if(ghost.y == targetY){
				ghost.yPriority = "onTarget";
			}
			if(ghost.x == targetX && ghost.y == targetY){
				ghost.xPriority = "none";
				ghost.yPriority = "none";
				ghost.deadEyesMode = false;
			}
			if(mapData[ghost.row-1][ghost.column] == 0 && mapData[ghost.row-1][ghost.column-1] == 0){
				ghost.upIsDisabled = true;
			}else{
				ghost.upIsDisabled = false;
			}
			
			if(mapData[ghost.row+1][ghost.column-1] == 0 && mapData[ghost.row+1][ghost.column] == 0){
				ghost.downIsDisabled = true;
			}else{
				ghost.downIsDisabled = false;
			}
			//System.out.println(mapData[24][12]);
			//System.out.println("ghost.row: " + ghost.row + " ghost.column: " + ghost.column);
			if(mapData[ghost.row][ghost.column-1] == 0){
				ghost.leftIsDisabled = true;
			}else{
				ghost.leftIsDisabled = false;
			}
			if(direction == "right"){
				if(mapData[ghost.row][ghost.column] == 0){
					ghost.rightIsDisabled = true;
				}else{
					ghost.rightIsDisabled = false;
				}
			}else{
				if(ghost.column != 27){
					if(mapData[ghost.row][ghost.column+1] == 0){
						ghost.rightIsDisabled = true;
					}else{
						ghost.rightIsDisabled = false;
					}
				}else{
					ghost.rightIsDisabled = true;
				}
			}
			if((ghost.row == row) && ((ghost.column < (column+30)) && (ghost.column > (column - 30)))){
				ghost.followMode = true;
			}else if((ghost.column == column) && ((ghost.row < (row+30)) && (ghost.row > (row-30)))){
				ghost.followMode = true;
			}else{
				ghost.followMode = false;
			}
			
			if(ghost.xPriority == "left" && ghost.yPriority == "up"){
				if((ghost.leftIsDisabled == false && ghost.previousDirection != "right") && (ghost.upIsDisabled == false  && ghost.previousDirection != "down")){
					randomDirection(ghost, "up", "left");
				}else if(ghost.leftIsDisabled == false && ghost.previousDirection != "right"){
					ghost.previousDirection = ghost.direction;
					ghost.direction = "left";
				}else if(ghost.upIsDisabled == false && ghost.previousDirection != "down"){
					ghost.previousDirection = ghost.direction;
					ghost.direction = "up";
				}else{
					if((ghost.rightIsDisabled == false && ghost.previousDirection != "left")&& (ghost.downIsDisabled == false  && ghost.previousDirection != "up")){
						randomDirection(ghost, "right", "down");
					}else if(ghost.rightIsDisabled == false && ghost.previousDirection != "left"){
						ghost.previousDirection = ghost.direction;
						ghost.direction = "right";
					}else if(ghost.downIsDisabled == false && ghost.previousDirection != "up"){
						ghost.previousDirection = ghost.direction;
						ghost.direction = "down";
					}
				}
			}else if(ghost.xPriority == "right" && ghost.yPriority == "up"){
				if(ghost.rightIsDisabled == false && ghost.upIsDisabled == false && ghost.previousDirection != "left" && ghost.previousDirection != "down"){
					randomDirection(ghost, "right", "up");
				}else if(ghost.rightIsDisabled == false && ghost.previousDirection != "left"){
					ghost.previousDirection = ghost.direction;
					ghost.direction = "right";
				}else if(ghost.upIsDisabled == false && ghost.previousDirection != "down"){
					ghost.previousDirection = ghost.direction;
					ghost.direction = "up";
				}else{
					if(ghost.downIsDisabled == false && ghost.leftIsDisabled == false && ghost.previousDirection != "right" && ghost.previousDirection != "down"){
						randomDirection(ghost, "left", "down");
					}else if(ghost.downIsDisabled == false && ghost.previousDirection != "up"){
						ghost.previousDirection = ghost.direction;
						ghost.direction = "down";
					}else if(ghost.leftIsDisabled == false && ghost.previousDirection != "right"){
						ghost.previousDirection = ghost.direction;
						ghost.direction = "left";
					}
				}
			}else if(ghost.xPriority == "left" && ghost.yPriority == "down"){
				if(ghost.leftIsDisabled == false && ghost.downIsDisabled == false && ghost.previousDirection != "right" && ghost.previousDirection != "up"){
					randomDirection(ghost, "left", "down");
				}else if(ghost.leftIsDisabled == false && ghost.previousDirection != "right"){
					ghost.previousDirection = ghost.direction;
					ghost.direction = "left";
				}else if(ghost.downIsDisabled == false && ghost.previousDirection != "up"){
					ghost.previousDirection = ghost.direction;
					ghost.direction = "down";
				}else{
					if(ghost.upIsDisabled == false && ghost.rightIsDisabled == false && ghost.previousDirection != "left" && ghost.previousDirection != "down"){
						randomDirection(ghost, "up", "right");
					}else if(ghost.upIsDisabled == false && ghost.previousDirection != "down"){
						ghost.previousDirection = ghost.direction;
						ghost.direction = "up";
					}else if(ghost.rightIsDisabled == false && ghost.previousDirection != "left"){
						ghost.previousDirection = ghost.direction;
						ghost.direction = "right";
					}
				}
			}else if(ghost.xPriority == "right" && ghost.yPriority == "down"){
				if(ghost.rightIsDisabled == false && ghost.downIsDisabled == false && ghost.previousDirection != "left" && ghost.previousDirection != "up"){
					randomDirection(ghost, "right", "down");
				}else if(ghost.rightIsDisabled == false && ghost.previousDirection != "left"){
					ghost.previousDirection = ghost.direction;
					ghost.direction = "right";
				}else if(ghost.downIsDisabled == false && ghost.previousDirection != "up"){
					ghost.previousDirection = ghost.direction;
					ghost.direction = "down";
				}else{
					if(ghost.leftIsDisabled == false && ghost.upIsDisabled == false && ghost.previousDirection != "right" && ghost.previousDirection != "down"){
						randomDirection(ghost, "left", "up");
					}else if(ghost.leftIsDisabled == false && ghost.previousDirection != "right"){
						ghost.previousDirection = ghost.direction;
						ghost.direction = "left";
					}else if(ghost.upIsDisabled == false && ghost.previousDirection != "down"){
						ghost.previousDirection = ghost.direction;
						ghost.direction = "up";
					}
				}
			}
			if((ghost.direction == "up") && (ghost.upIsDisabled == false)){
				ghost.y -= ghost.speed;
			}else if(ghost.direction == "down" && ghost.downIsDisabled == false){
				ghost.y += ghost.speed;
			}else if(ghost.direction == "left" && ghost.leftIsDisabled == false){
				ghost.x -= ghost.speed;
			}else if(ghost.direction == "right" && ghost.rightIsDisabled == false){
				ghost.x += ghost.speed;
			}else{
				if(ghost.rightIsDisabled == false && ghost.leftIsDisabled == false && ghost.upIsDisabled == false && ghost.downIsDisabled == false){
					if(direction == "left"){
						if(ghost.xPriority == "left"){
							ghost.direction = "left";
						}else if(ghost.yPriority == "up"){
							ghost.direction = "up";
						}else if(ghost.yPriority == "down"){
							ghost.direction = "down";
						}else{
							randomDirection(ghost, "left", "up", "down");
						}
					}else if(direction == "right"){
						if(ghost.xPriority == "right"){
							ghost.direction = "right";
						}else if(ghost.yPriority == "up"){
							ghost.direction = "up";
						}else if(ghost.yPriority == "down"){
							ghost.direction = "down";
						}else{
							randomDirection(ghost, "right", "up", "down");
						}
					}
				}else if(direction == "left" && ghost.upIsDisabled == false && ghost.leftIsDisabled == false){
					if(ghost.yPriority == "up"){
						if(ghost.previousDirection != "up"){
							ghost.previousDirection = "up";
						}
						ghost.direction = "up";
					}else if(ghost.xPriority == "left"){
						ghost.direction = "left";
					}
				}else if(direction == "left" && ghost.downIsDisabled == false && ghost.leftIsDisabled == false){
					if(ghost.yPriority == "down"){
						ghost.direction = "down";
					}else if(ghost.xPriority == "left"){
						ghost.direction = "left";
					}
				}else if(direction == "right" && ghost.upIsDisabled == false && ghost.rightIsDisabled == false){
					if(ghost.yPriority == "up"){
						ghost.direction = "up";
					}else if(ghost.xPriority == "right"){
						ghost.direction = "right";
					}
				}else if(ghost.upIsDisabled && ghost.direction == "up"){
					if(ghost.leftIsDisabled == false && ghost.rightIsDisabled == false){
						if(ghost.xPriority == "left"){
							ghost.direction = "left";
						}else if(ghost.xPriority == "right"){
							ghost.direction = "right";
						}else{
							randomDirection(ghost, "left", "right");
						}
					}else if(ghost.leftIsDisabled == false){
						ghost.direction = "left";
					}else if(ghost.rightIsDisabled == false){
						ghost.direction = "right";
					}
				}else if(ghost.leftIsDisabled && ghost.direction == "left"){
					if(ghost.upIsDisabled == false && ghost.downIsDisabled == false){
						if(ghost.yPriority == "up"){
							ghost.direction = "up";
						}else if(ghost.yPriority == "down"){
							ghost.direction = "down";
						}else{
							randomDirection(ghost, "up", "down");
						}
					}else if(ghost.upIsDisabled == false){
						ghost.direction = "up";
					}else if(ghost.downIsDisabled == false){
						ghost.direction = "down";
					}
				}else if(ghost.downIsDisabled && ghost.direction == "down"){
					if(ghost.leftIsDisabled == false && ghost.rightIsDisabled == false){
						if(ghost.xPriority == "left"){
							ghost.direction = "left";
						}else if(ghost.xPriority == "right"){
							ghost.direction = "right";
						}else{
							randomDirection(ghost, "left", "right");
						}
					}else if(ghost.leftIsDisabled == false){
						ghost.direction = "left";
					}else if(ghost.rightIsDisabled == false){
						ghost.direction = "right";
					}
				}else if(ghost.rightIsDisabled && ghost.direction == "right"){
					if(ghost.upIsDisabled == false && ghost.downIsDisabled == false){
						if(ghost.yPriority == "up"){
							ghost.yPriority = "up";
						}else if(ghost.yPriority == "down"){
							ghost.yPriority = "down";
						}else{
							randomDirection(ghost, "up", "down");
						}
					}else if(ghost.upIsDisabled == false){
						ghost.direction = "up";
					}else if(ghost.downIsDisabled == false){
						ghost.direction = "down";
					}
				}
			}
		}else{
			if(mapData[ghost.row-1][ghost.column] == 0 && mapData[ghost.row-1][ghost.column-1] == 0){
				ghost.upIsDisabled = true;
			}else{
				ghost.upIsDisabled = false;
			}
			
			if(mapData[ghost.row+1][ghost.column-1] == 0 && mapData[ghost.row+1][ghost.column] == 0){
				ghost.downIsDisabled = true;
			}else{
				ghost.downIsDisabled = false;
			}
			//System.out.println(mapData[24][12]);
			//System.out.println("ghost.row: " + ghost.row + " ghost.column: " + ghost.column);
			if(mapData[ghost.row][ghost.column-1] == 0){
				ghost.leftIsDisabled = true;
			}else{
				ghost.leftIsDisabled = false;
			}
			if(direction == "right"){
				if(mapData[ghost.row][ghost.column] == 0){
					ghost.rightIsDisabled = true;
				}else{
					ghost.rightIsDisabled = false;
				}
			}else{
				if(ghost.column != 27){
					if(mapData[ghost.row][ghost.column+1] == 0){
						ghost.rightIsDisabled = true;
					}else{
						ghost.rightIsDisabled = false;
					}
				}else{
					ghost.rightIsDisabled = true;
				}
			}
			if((ghost.row == row) && ((ghost.column < (column+30)) && (ghost.column > (column - 30)))){
				ghost.followMode = true;
			}else if((ghost.column == column) && ((ghost.row < (row+30)) && (ghost.row > (row-30)))){
				ghost.followMode = true;
			}else{
				ghost.followMode = false;
			}
			
			if(ghost.followMode){
				if(ghost.column < column){
					if(ghost.rightIsDisabled == false){
						ghost.direction = "right";
					}
				}else if(ghost.column > column){
					if(ghost.leftIsDisabled == false){
						ghost.direction = "left";
					}
				}
				if(ghost.row < row){
					if(ghost.downIsDisabled == false){
						ghost.direction = "down";
					}
				}else if(ghost.row > row){
					if(ghost.upIsDisabled == false){
						ghost.direction = "up";
					}
				}
			}
			
			if((ghost.direction == "up") && (ghost.upIsDisabled == false)){
				ghost.y -= ghost.speed;
			}else if(ghost.direction == "down" && ghost.downIsDisabled == false){
				ghost.y += ghost.speed;
			}else if(ghost.direction == "left" && ghost.leftIsDisabled == false){
				ghost.x -= ghost.speed;
			}else if(ghost.direction == "right" && ghost.rightIsDisabled == false){
				ghost.x += ghost.speed;
			}else{
				if(ghost.upIsDisabled && ghost.direction == "up"){
					if(ghost.leftIsDisabled == false && ghost.rightIsDisabled == false){
						randomDirection(ghost, "left", "right");
					}else if(ghost.leftIsDisabled == false){
						ghost.direction = "left";
					}else if(ghost.rightIsDisabled == false){
						ghost.direction = "right";
					}
				}else if(ghost.leftIsDisabled && ghost.direction == "left"){
					if(ghost.upIsDisabled == false && ghost.downIsDisabled == false){
						randomDirection(ghost, "up", "down");
					}else if(ghost.upIsDisabled == false){
						ghost.direction = "up";
					}else if(ghost.downIsDisabled == false){
						ghost.direction = "down";
					}
				}else if(ghost.downIsDisabled && ghost.direction == "down"){
					if(ghost.leftIsDisabled == false && ghost.rightIsDisabled == false){
						randomDirection(ghost, "left", "right");
					}else if(ghost.leftIsDisabled == false){
						ghost.direction = "left";
					}else if(ghost.rightIsDisabled == false){
						ghost.direction = "right";
					}
				}else if(ghost.rightIsDisabled && ghost.direction == "right"){
					if(ghost.upIsDisabled == false && ghost.downIsDisabled == false){
						randomDirection(ghost, "up", "down");
					}else if(ghost.upIsDisabled == false){
						ghost.direction = "up";
					}else if(ghost.downIsDisabled == false){
						ghost.direction = "down";
					}
				}else if(ghost.rightIsDisabled == false && ghost.leftIsDisabled == false && ghost.upIsDisabled == false && ghost.downIsDisabled == false){
					if(direction == "left"){
						randomDirection(ghost, "left", "up", "down");
					}else if(direction == "right"){
						randomDirection(ghost, "right", "up", "down");
					}
				}
			}
			//System.out.println("row: " + ghost.row + " column: " + ghost.column);
			if(getDistance(ghost.x, ghost.y, (ghost.column*widthOfEachBlock), ghost.y) == (widthOfEachBlock/2)){
				ghost.inCenter = true;
			}else{
				ghost.inCenter = false;
			}
		}
	}
	
	public void movePacman(){
		if(direction == "up" || preTurn == "up"){
			if(mapData[row-1][column] == 0){
				if(getDistance(pacman.x, pacman.y, pacman.x, (row*heightOfEachBlock)) >= (pacman.height/2)){
					pacman.y -= pacman.speed;
				}
			}else{
				if(preTurn == "up"){
					preTurn = null;
					direction = "up";
				}
				pacman.y-= pacman.speed;
				pacman.upIsDisabled = false;
			}
			if(column != 0){
				if(mapData[row][column-1] == 0){
					pacman.leftIsDisabled = true;
				}else{
					pacman.leftIsDisabled = false;
				}
			}
			if(mapData[row][column+1] == 0){
				pacman.rightIsDisabled = true;
			}else{
				pacman.rightIsDisabled = false;
			}
			if(mapData[row+1][column] == 0){
				pacman.downIsDisabled = true;
			}else{
				pacman.downIsDisabled = false;
			}
		}else if(direction == "down" || preTurn == "down"){
			if(mapData[row+1][column] == 0){
				if(getDistance(pacman.x, pacman.y, pacman.x, ((row+1)*heightOfEachBlock)) >= (pacman.height/2)){
					pacman.y+=pacman.speed;
				}
			}else{
				if(preTurn == "down"){
					preTurn = null;
					direction = "down";
				}
				pacman.y+= pacman.speed;
				pacman.downIsDisabled = false;
			}
			if(column != 0){
				if(mapData[row][column-1] == 0){
					pacman.leftIsDisabled = true;
				}else{
					pacman.leftIsDisabled = false;
				}
			}
			if(column != 27){
				if(mapData[row][column+1] == 0){
					pacman.rightIsDisabled = true;
				}else{
					pacman.rightIsDisabled = false;
				}
			}
			if(mapData[row-1][column] == 0){
				pacman.upIsDisabled = true;
			}else{
				pacman.upIsDisabled = false;
			}
		}
		if(direction == "left" || preTurn == "left"){
			if(column != 0){
				if(mapData[row][column-1] == 0){
					if(getDistance(pacman.x,pacman.y,((column)*heightOfEachBlock), pacman.y) >= (pacman.width/2)){
						pacman.x-=pacman.speed;
					}
				}else{
					if(preTurn == "left"){
						preTurn = null;
						direction = "left";
					}
					pacman.x-= pacman.speed;
					pacman.leftIsDisabled = false;
				}
			}
			if(mapData[row-1][column] == 0){
				pacman.upIsDisabled = true;
			}else{
				pacman.upIsDisabled = false;
			}
			if(mapData[row+1][column] == 0){
				pacman.downIsDisabled = true;
			}else{
				pacman.downIsDisabled = false;
			}
			if(column != 27){
				if(mapData[row][column+1] == 0){
					pacman.rightIsDisabled = true;
				}else{
					pacman.rightIsDisabled = false;
				}
			}
		}else if(direction == "right" || preTurn == "right"){
			if(mapData[row][column+1] == 0){
				if(getDistance(pacman.x, pacman.y, ((column+1)*heightOfEachBlock), pacman.y) >= (pacman.width/2)){
					pacman.x+= pacman.speed;
				}
			}else{
				if(preTurn == "right"){
					preTurn = null;
					direction = "right";
				}
				pacman.x+= pacman.speed;
				pacman.rightIsDisabled = false;
			}
			if(mapData[row-1][column] == 0){
				pacman.upIsDisabled = true;
			}else{
				pacman.upIsDisabled = false;
			}
			if(mapData[row+1][column] == 0){
				pacman.downIsDisabled = true;
			}else{
				pacman.downIsDisabled = false;
			}
			if(column != 0){
				if(mapData[row][column-1] == 0){
					pacman.leftIsDisabled = true;
				}else{
					pacman.leftIsDisabled = false;
				}
			}
		}
	}
	
	public void updateGame(){
		if(!started){
			startCounter++;
			direction = "still";
			if(startCounter > 10){
				started = true;
			}
		}else{
			if(scared ){
				scareCounter++;
				//System.out.println(scareCounter);
			}else{
				numberOfGhostsEaten = 0;
			}
			if(scareCounter > 160){
				scareCounter = 0;
				scared = false;
				freddyTheReddy.scaredMode = false;
				morangeTheOrange.scaredMode = false;
				pinkyTheGayPinkGhost.scaredMode = false;
				blueEyDaBlueGuy.scaredMode = false;
			}
			if(deathAnimationMode){
				if(index < 10){
					index ++;
				}else{
					deathAnimationMode = false;
					index = 0;
					if(lives >= 1){
						restart();
					}
				}
			}else{
				updatePacmansPosition();
				updateGhostPosition();
				if(gameOver == false){
					updateFruit();
				}
				if(index < 3){
					if(ghostIndex < 1 && index == 2){
						ghostIndex++;
					}else if(ghostIndex == 1 && index == 2){
						ghostIndex = 0;
					}
					if(scaredIndex < 3 && index == 2){
						scaredIndex++;
					}else if(scaredIndex == 3 && index == 2){
						scaredIndex = 0;
					}
					index++;
				}else{
					index=0;
				}
				
				if(column == 0 && direction == "left"){
					pacman.x = 220;
					updatePacmansPosition();
				}else if(column == 27 && direction == "right"){
					pacman.x = 1;
					updatePacmansPosition();
				}
				/*if(inCenter == false){
					if(preTurn != null){
						previousPreTurn = preTurn;
					}
					preTurn = null;
				}else{
					preTurn = previousPreTurn;
					/*if(preTurn != null){
						if(preTurn == "down" && pacman.downIsDisabled == false){
							direction = "down";
						}else if(preTurn == "up" && pacman.upIsDisabled == false){
							direction = "up";
						}else if(preTurn == "left" && pacman.leftIsDisabled == false){
							direction = "left";
						}else if(preTurn == "right" && pacman.rightIsDisabled == false){
							direction = "right";
						}
					}
					
				}*/
				
				if(getDistance(pacman.x, pacman.y, (column*widthOfEachBlock), pacman.y) == (widthOfEachBlock/2)){
					inCenter = true;
				}else{
					inCenter = false;
				}
				
				movePacman();
				if(pacman.x > 500){
					pacman.x = 0;
				}
				if(pacman.x < 0){
					pacman.x = 500;
				}
				if(inCenter){
				}
				//System.out.println("pacman.x: " + pacman.x + " pacman.y: " + pacman.y);
				moveGhost(freddyTheReddy);
				moveGhost(blueEyDaBlueGuy);
				moveGhost(pinkyTheGayPinkGhost);
				moveGhost(morangeTheOrange);
				updatePacmansPosition();
				if((row == freddyTheReddy.row && column == freddyTheReddy.column) || (row == morangeTheOrange.row && column == morangeTheOrange.column)
				|| (row == pinkyTheGayPinkGhost.row && column == pinkyTheGayPinkGhost.column) || (row == blueEyDaBlueGuy.row && column == blueEyDaBlueGuy.column)){
						if(row == freddyTheReddy.row && column == freddyTheReddy.column && freddyTheReddy.scaredMode == false && freddyTheReddy.deadEyesMode == false){
							if(lives == 1){
								deathAnimationMode = true;
								scared = false;
								freddyTheReddy.x = 1000;
								pinkyTheGayPinkGhost.x = 1000;
								morangeTheOrange.x = 1000;
								blueEyDaBlueGuy.x = 1000;
								gameOver();
							}else{
								lives--;
								scared = false;
								deathAnimationMode = true;
								index = 0;
								freddyTheReddy.x = 1000;
								pinkyTheGayPinkGhost.x = 1000;
								morangeTheOrange.x = 1000;
								blueEyDaBlueGuy.x = 1000;
							}
						}else if(row == morangeTheOrange.row && column == morangeTheOrange.column && morangeTheOrange.scaredMode == false && morangeTheOrange.deadEyesMode == false){
							if(lives == 1){
								deathAnimationMode = true;
								scared = false;
								freddyTheReddy.x = 1000;
								pinkyTheGayPinkGhost.x = 1000;
								morangeTheOrange.x = 1000;
								blueEyDaBlueGuy.x = 1000;
								gameOver();
							}else{
								lives--;
								deathAnimationMode = true;
								index = 0;
								scared = false;
								freddyTheReddy.x = 1000;
								pinkyTheGayPinkGhost.x = 1000;
								morangeTheOrange.x = 1000;
								blueEyDaBlueGuy.x = 1000;
							}
						}else if(row == pinkyTheGayPinkGhost.row && column == pinkyTheGayPinkGhost.column && pinkyTheGayPinkGhost.scaredMode == false && pinkyTheGayPinkGhost.deadEyesMode == false){
							if(lives == 1){
								deathAnimationMode = true;
								scared = false;
								freddyTheReddy.x = 1000;
								pinkyTheGayPinkGhost.x = 1000;
								morangeTheOrange.x = 1000;
								blueEyDaBlueGuy.x = 1000;
								gameOver();
							}else{
								lives--;
								deathAnimationMode = true;
								scared = false;
								index = 0;
								freddyTheReddy.x = 1000;
								pinkyTheGayPinkGhost.x = 1000;
								morangeTheOrange.x = 1000;
								blueEyDaBlueGuy.x = 1000;
							}
						}else if(row == blueEyDaBlueGuy.row && column == blueEyDaBlueGuy.column && blueEyDaBlueGuy.scaredMode == false && blueEyDaBlueGuy.deadEyesMode == false){
							if(lives == 1){
								deathAnimationMode = true;
								freddyTheReddy.x = 1000;
								scared = false;
								pinkyTheGayPinkGhost.x = 1000;
								morangeTheOrange.x = 1000;
								blueEyDaBlueGuy.x = 1000;
								gameOver();
							}else{
								lives--;
								deathAnimationMode = true;
								scared = false;
								index = 0;
								freddyTheReddy.x = 1000;
								pinkyTheGayPinkGhost.x = 1000;
								morangeTheOrange.x = 1000;
								blueEyDaBlueGuy.x = 1000;
							}
						}else{
							if((row == freddyTheReddy.row|| row == freddyTheReddy.row-1 || row == freddyTheReddy.row+1) && 
						(column == freddyTheReddy.column || column == freddyTheReddy.column-1 || column == freddyTheReddy.column+1)
						&& freddyTheReddy.scaredMode == true){
								if((((direction == "up" || direction == "down") && (freddyTheReddy.column-1 == column || freddyTheReddy.column+1 == column || freddyTheReddy.column == column)) && freddyTheReddy.row == row
								) || ((direction == "left" || direction == "right") && (freddyTheReddy.row-1 == row || freddyTheReddy.row+1 == row || freddyTheReddy.row == row) && freddyTheReddy.column == column)){
									freddyTheReddy.scaredMode = false;
									numberOfGhostsEaten++;
									numberAdded = (int)(Math.pow(2, numberOfGhostsEaten)*100);
									score+= numberAdded;
									popupScoreX = pacman.x;
									popupScoreY = pacman.y;
									displayPopupScore(numberAdded);
									System.out.println(numberAdded);
									scoreAnimation = true;
									scoreAnimationCounter = 0;
									//freddyTheReddy.deadEyesMode = true;
									freddyTheReddy.x = 112;
									freddyTheReddy.y = 140;
								}
							}
							if((row == morangeTheOrange.row|| row == morangeTheOrange.row-1 || row == morangeTheOrange.row+1) && 
						(column == morangeTheOrange.column || column == morangeTheOrange.column-1 || column == morangeTheOrange.column+1)
						&& morangeTheOrange.scaredMode == true){
								if((((direction == "up" || direction == "down") && (morangeTheOrange.column-1 == column || morangeTheOrange.column+1 == column || morangeTheOrange.column == column)) && morangeTheOrange.row == row
								) || ((direction == "left" || direction == "right") && (morangeTheOrange.row-1 == row || morangeTheOrange.row+1 == row || morangeTheOrange.row == row) && morangeTheOrange.column == column)){
									morangeTheOrange.scaredMode = false;
									numberOfGhostsEaten++;
									numberAdded = (int)(Math.pow(2, numberOfGhostsEaten)*100);
									score+= numberAdded;
									popupScoreX = pacman.x;
									popupScoreY = pacman.y;
									displayPopupScore(numberAdded);
									System.out.println(numberAdded);
									scoreAnimation = true;
									scoreAnimationCounter = 0;
									//morangeTheOrange.deadEyesMode = true;
									morangeTheOrange.x = 112;
									morangeTheOrange.y = 140;
								}
							}
							if((row == pinkyTheGayPinkGhost.row|| row == pinkyTheGayPinkGhost.row-1 || row == pinkyTheGayPinkGhost.row+1) && 
						(column == pinkyTheGayPinkGhost.column || column == pinkyTheGayPinkGhost.column-1 || column == pinkyTheGayPinkGhost.column+1)
						&& pinkyTheGayPinkGhost.scaredMode == true){
								if((((direction == "up" || direction == "down") && (pinkyTheGayPinkGhost.column-1 == column || pinkyTheGayPinkGhost.column+1 == column || pinkyTheGayPinkGhost.column == column)) && pinkyTheGayPinkGhost.row == row
								) || ((direction == "left" || direction == "right") && (pinkyTheGayPinkGhost.row-1 == row || pinkyTheGayPinkGhost.row+1 == row || pinkyTheGayPinkGhost.row == row) && pinkyTheGayPinkGhost.column == column)){
									pinkyTheGayPinkGhost.scaredMode = false;
									numberOfGhostsEaten++;
									numberAdded = (int)(Math.pow(2, numberOfGhostsEaten)*100);
									score+= numberAdded;
									popupScoreX = pacman.x;
									popupScoreY = pacman.y;
									displayPopupScore(numberAdded);
									System.out.println(numberAdded);
									scoreAnimation = true;
									scoreAnimationCounter = 0;
									//pinkyTheGayPinkGhost.deadEyesMode = true;
									pinkyTheGayPinkGhost.x = 112;
									pinkyTheGayPinkGhost.y = 140;
								}
							}
							if((row == blueEyDaBlueGuy.row|| row == blueEyDaBlueGuy.row-1 || row == blueEyDaBlueGuy.row+1) && 
						(column == blueEyDaBlueGuy.column || column == blueEyDaBlueGuy.column-1 || column == blueEyDaBlueGuy.column+1)
						&& blueEyDaBlueGuy.scaredMode == true){
								if((((direction == "up" || direction == "down") && (blueEyDaBlueGuy.column-1 == column || blueEyDaBlueGuy.column+1 == column || blueEyDaBlueGuy.column == column)) && blueEyDaBlueGuy.row == row
								) || ((direction == "left" || direction == "right") && (blueEyDaBlueGuy.row-1 == row || blueEyDaBlueGuy.row+1 == row || blueEyDaBlueGuy.row == row) && blueEyDaBlueGuy.column == column)){
									blueEyDaBlueGuy.scaredMode = false;
									numberOfGhostsEaten++;
									numberAdded = (int)(Math.pow(2, numberOfGhostsEaten)*100);
									score+= numberAdded;
									popupScoreX = pacman.x;
									popupScoreY = pacman.y;
									displayPopupScore(numberAdded);
									System.out.println(numberAdded);
									scoreAnimation = true;
									scoreAnimationCounter = 0;
									//blueEyDaBlueGuy.deadEyesMode = true;
									blueEyDaBlueGuy.x = 112;
									blueEyDaBlueGuy.y = 140;
								}
							}
						}
				}
			}
		}
	}
	
	public void restart(){
		pacman.x = 106;
		pacman.y = 211;
		pacman.downIsDisabled = false;
		pacman.leftIsDisabled = false;
		pacman.rightIsDisabled = false;
		pacman.upIsDisabled = false;
		direction = "still";
		spawnGhost();
	}
	
	public void gameOver(){
		gameOver = true;
	}
	
	class Pacman{
		int x=106;
		int y=211;
		double width = 12;
		double height = 12;
		int speed = 4;
		boolean downIsDisabled = false;
		boolean upIsDisabled = false;
		boolean leftIsDisabled = false;
		boolean rightIsDisabled = false;
	}
	
	class Fruit{
		int x, y, column, row;
		int width = (widthOfEachBlock/2);
		int height = (heightOfEachBlock/2);
		boolean eaten = false;
		boolean superFruit = false;
	}
	
	class Ghost{
		int x,y,column, row;
		int width = 14;
		int height = 14;
		int speed = 2;
		String direction = "up";
		String xPriority = "none";
		String yPriority = "none";
		String previousDirection = "none";
		boolean upIsDisabled, downIsDisabled, leftIsDisabled, rightIsDisabled, inCenter, followMode, scaredMode, deadEyesMode = false;
	}
	
	class AnimationThread extends Thread{
		PacMan at;
		
		public AnimationThread(PacMan _at){
			at=_at;
			start();
		}
		
		public void run(){
			int FPS = 18;
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
