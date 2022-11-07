	package snake;
	
	import java.awt.Color;
import java.awt.Font;
	import java.awt.FontMetrics;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Image;
	import java.awt.Toolkit;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyAdapter;
	import java.awt.event.KeyEvent;
	import java.awt.event.ActionEvent;
	import javax.swing.Timer;
	import javax.swing.ImageIcon;
import javax.swing.JPanel;
	
	public class Board extends JPanel implements ActionListener {
		
		private final int boardsWidth = 700;
		private final int boardsHeight = 700;
		private final int rondsSize = 20;
		private final int totalRonds = 900;
		private final int rPosition = 30;
		private final int delai = 140;
		private final int x[] = new int[totalRonds];
		private final int y[] = new int[totalRonds];
		private final int s[][] = new int [boardsWidth][boardsHeight];
		private int ronds;
		private int xFraise;
		private int yFraise;
		private int xMyrtille;
		private int yMyrtille;
		private int RAND_POS;
		private boolean directionGauche = false;
		private boolean directionDroite = true;
		private boolean directionHaut = false;
		private boolean directionBas = true;
		private boolean inGame = true;
		private Image rond;
		private Image tete;
		private Image fraise;
		private Image myrtille;
		private Image bois;
		private Timer t;
	
		
		public Board() {
			addKeyListener(new TAdapter());
			setBackground(Color.black);
			setFocusable(true);
			setPreferredSize(new Dimension(boardsWidth, boardsHeight));
			loadImages();
			initGame();
		}
		
		private void loadImages() {
			ImageIcon rondIcon = new ImageIcon(getClass().getResource("/res/rond.png"));
			rond = rondIcon.getImage();
			// Image modifiedRondImage = rond.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
			// rondIcon = new ImageIcon(modifiedRondImage);
			
			ImageIcon teteIcon = new ImageIcon(getClass().getResource("/res/tete.png"));
			tete = teteIcon.getImage();
			// Image modifiedTeteImage = tete.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
			// teteIcon = new ImageIcon(modifiedTeteImage);
			
			ImageIcon fraiseIcon = new ImageIcon(getClass().getResource("/res/fraise.png"));
			fraise = fraiseIcon.getImage();
			// Image modifiedAppleImage = apple.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
			// appleIcon = new ImageIcon(modifiedAppleImage);
			
			ImageIcon myrtilleIcon = new ImageIcon(getClass().getResource("/res/myrtille.png"));
			myrtille = myrtilleIcon.getImage();

			
			ImageIcon boisIcon = new ImageIcon(getClass().getResource("/res/bois.png"));
			bois = boisIcon.getImage();
			// JLabel imageLabel = new JLabel(boisIcon);
		 
		}
		
		private void initGame() {
			ronds = 3;
			for(int i = 0; i < ronds; i++) {
				x[i] = 50 - i*10;
				y[i] = 50;
			}
			locateApple();
			t = new Timer(delai,this);
			t.start();
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			doDrawing(g);
		}
		
		private void doDrawing(Graphics g) {
			if(inGame) {
				for(int j = 0; j < s.length; j++) {
					//for(int k = 0; k < boardsHeight; k++) {
						g.drawImage(bois, 0, 0, this);
						// g.drawImage(bois, 0, 45, this);
						// g.drawImage(bois, 0, 90, this);
						// g.drawImage(bois, 45, 0, this);
						// g.drawImage(bois, 90, 0, this);

					//}
			}
				
				g.drawImage(fraise,  xFraise+100,  yFraise+200,  this);
				
				g.drawImage(myrtille,  xMyrtille+400,  yMyrtille+150,  this);
			
			    for(int i = 0; i < ronds; i++) {
			    	if(i == 0) {
			    		g.drawImage(tete,  x[i], y[i], this);
			    	} else {
			    		g.drawImage(rond,  x[i], y[i], this);
			    	}
			    }
			    Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);
		}
	}
		
		private void gameOver(Graphics g) {
			String message = "Game Over";
			Font small = new Font("Helvetice", Font.BOLD, 15);
			FontMetrics met = getFontMetrics(small);
			g.setColor(Color.white);
			g.setFont(small);
			g.drawString(message, (boardsWidth - met.stringWidth(message))/2, boardsHeight/2);
		}
		
		private void checkApple() {
			if((x[0] == xFraise) && (y[0] == yFraise)) {
				ronds++;
				locateApple();
			}
		}
		
		private void move() {
			for(int i = ronds; i > 0; i--) {
				x[i] = x[i - 1]; 
				y[i] = y[i - 1]; 
			}
			
			if(directionGauche) {
				x[0] -= rondsSize;
			}
			
			if(directionDroite) {
				x[0] += rondsSize;
			}
			
			if(directionHaut) {
				y[0] -= rondsSize;
			}
			
			if(directionBas) {
				y[0] += rondsSize;
			}
		}
		
		
		private void checkCollision() {
			for(int i = ronds; i > 0; i --) {
				if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
					inGame = false;
				}
			}
			
			if(y[0] >= boardsHeight) {
				inGame = false;
			}
			
			if(y[0] < 0) {
				inGame = false;
			}
			
			if(x[0] >= boardsWidth) {
				inGame = false;
			}
			
			if(x[0] < 0) {
				inGame = false;
			}
			
			if(!inGame) {
				t.stop();
			}
		}
		
		private void locateApple() {
			int r = (int) (Math.random()*RAND_POS);
			xFraise = r*rondsSize;
			r = (int) (Math.random()*RAND_POS);
			yFraise = r*rondsSize;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(inGame) {
				checkApple();
				checkCollision();
				move();
			}
			repaint();
		}
		
		private class TAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT && !directionDroite) {
				directionGauche = true;
				directionHaut = false;
				directionBas = false;
			}
			
			if(key == KeyEvent.VK_RIGHT && !directionGauche) {
				directionDroite = true;
				directionHaut = false;
				directionBas = false;
			}
			
			if(key == KeyEvent.VK_UP && !directionBas) {
				directionHaut = true;
				directionDroite = false;
				directionGauche = false;
			}
			
			if(key == KeyEvent.VK_DOWN && !directionHaut) {
				directionBas = true;
				directionDroite = false;
				directionGauche = false;
			}
		}
	  }
	}
