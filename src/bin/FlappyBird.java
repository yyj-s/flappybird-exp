package bin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FlappyBird extends JPanel {
	public static final int START=0;
	public static final int RUNNING=1;
	public static final int GAME_OVER=2;
	public static final int WIDTH=430;
	public static final int HEIGHT=640;
	
	public static final int XSPEED=2;
	public static final long INTERVEL=(1000/60);
	
	private int state;
	private int score;
	
	public static BufferedImage background;
	public static BufferedImage birdpicture;
	public static BufferedImage column;
	public static BufferedImage groundpicture;
	public static BufferedImage start;
	public static BufferedImage gameover;
	
	Timer timer;
	Bird bird=new Bird();
	Column column1,column2;
	Ground ground;
	
	static {
		try {
			background=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/flappybird/src/picture/background.png"));
			birdpicture=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/flappybird/src/picture/bird.png"));
			column=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/flappybird/src/picture/column.png"));
			groundpicture=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/flappybird/src/picture/ground.png"));
			start=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/flappybird/src/picture/start.png"));
			gameover=ImageIO.read(new File("C:/Users/cx/eclipse-workspace/flappybird/src/picture/gameover.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	FlappyBird(){
		score=0;
		state=START;
		column1=new Column(1);
		column2=new Column(2);
		ground=new Ground();
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		
		g.drawImage(column, column1.getX(), column1.getY(), null);
		g.drawImage(column, column2.getX(), column2.getY(), null);
		g.drawImage(groundpicture, ground.getX(), ground.getY(), null);
		Font font=new Font(Font.SANS_SERIF,Font.BOLD,14);
		g.setColor(new Color(0x3a3fff));
		g.setFont(font);
		g.drawString("SCORE: "+score, 10, 25);
		paintBird(g);
		paintState(g);
	}
	public void paintBird(Graphics g) {
		Graphics2D g2=(Graphics2D)g;
		g2.rotate(-bird.alpha, bird.getX(), bird.getY());
		g.drawImage(birdpicture, bird.getX(), bird.getY(), null);
		g2.rotate(bird.alpha, bird.getX(), bird.getY());
	}
	public void paintState(Graphics g) {
		switch(state) {
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			Font font=new Font(Font.SANS_SERIF,Font.BOLD,14);
			g.setColor(new Color(0x3a3fff));
			g.setFont(font);
			g.drawString("YOUR SCORE: "+score, 180, 400);
		
		}
	}
	
	
	public static void main(String[] args) {
		FlappyBird game=new FlappyBird();
		JFrame frame=new JFrame();
		frame.add(game);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.action();
	}
	
	public void action() {
		MouseAdapter lis=new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch(state) {
				case START:
					state=RUNNING;
					break;
				case RUNNING:
					bird.flappy();
					break;
				case GAME_OVER:
					score=0;
					column1=new Column(1);
					column2=new Column(2);
					bird=new Bird();
					state=START;
				}
			}
		};
		this.addMouseListener(lis);
		this.addMouseMotionListener(lis);
		timer=new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(state==RUNNING) {
					stepAction();
					outOfBoundsAction();
					checkGameOverAction();
				}
				repaint();
			}
			
		},INTERVEL,INTERVEL);
	}
	public void stepAction() {
		column1.step();
		column2.step();
		ground.step();
		bird.step();
	}
	
	public void outOfBoundsAction() {
		if(ground.outOfBounds()) {
			ground.reset();
		}
		if(column1.outOfBounds()) {
			column1.reset(column2.getX());
		}
		if(column2.outOfBounds()) {
			column2.reset(column1.getX());
		}
	}
	
	public void checkGameOverAction() {
		if(bird.hit(column1)||bird.hit(column2)||bird.hit(ground)) {
			state=GAME_OVER;
		}
		
	}

}
