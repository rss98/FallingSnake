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
		private final int tailleBois = 45;
		private final int boisLimiteDroite = boardsWidth - 90;
		private final int boisLimiteGauche = 40;
		private final int boisLimiteBas = boardsHeight - 90;
                private final int boisLimiteHaut = 40;
		private final int rondsSize = 20;
		private final int totalRonds = 900;
		private final int rPosition = 30;
		private final int delai = 140;
		private final int x[] = new int[totalRonds];
		private final int y[] = new int[totalRonds];
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
		private int max = 20;
		private int min = 3;
		private boolean pause = false;
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

			
			ImageIcon teteIcon = new ImageIcon(getClass().getResource("/res/tete.png"));
			tete = teteIcon.getImage();

			
			ImageIcon fraiseIcon = new ImageIcon(getClass().getResource("/res/fraise.png"));
			fraise = fraiseIcon.getImage();

			
			ImageIcon myrtilleIcon = new ImageIcon(getClass().getResource("/res/myrtille.png"));
			myrtille = myrtilleIcon.getImage();

			
			ImageIcon boisIcon = new ImageIcon(getClass().getResource("/res/bois.png"));
			bois = boisIcon.getImage();
			
		 
		}
		
		private void initGame() {
			// Position de départ
			ronds = 3;
			for(int i = 0; i < ronds; i++) {
				x[i] = 40 - i*10;
				y[i] = 40;
			}
			locateFraise();
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
				// Positionnement des bois
				int distance = boardsWidth / tailleBois;
				int xBois = 0;
				int yBois = 0;
				for( int k = 0; k < distance; k++) {

				    //Bois du Haut
                    g.drawImage(bois, xBois, 0, this);
                    //Bois de Gauche
					g.drawImage(bois, 0, yBois, this);
                    //Bois du Bas
					g.drawImage(bois, xBois, (distance - 1) * tailleBois, this);
                    //Bois de Droite
					g.drawImage(bois, (distance - 1) * tailleBois, yBois, this);

					//Incrémentation des Bois
					xBois += tailleBois;
					yBois += tailleBois;
		}
				// Dessin de la fraise
				g.drawImage(fraise,  xFraise,  yFraise,  this);
				
				// Dessin des myrtilles
				g.drawImage(myrtille,  xMyrtille,  yMyrtille,  this);
			
				// Dessin de la tête et des ronds
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
		

		
		// Fin de jeu
		private void gameOver(Graphics g) {
			String message = "Game Over";
			Font small = new Font("Helvetice", Font.BOLD, 15);
			FontMetrics met = getFontMetrics(small);
			g.setColor(Color.white);
			g.setFont(small);
                        g.drawString(message, (boardsWidth - met.stringWidth(message))/2, boardsHeight/2);
		}
		
		
	         	public void checkFraise() {

		        // Check si la tête est sur la position que la fraise
			if((x[0] == xFraise) && (y[0] == yFraise)) {
				ronds++;
				locateFraise();
			}
		}
		
	         	private void locateFraise() {
			int r;
                        // Random position X fraise 
			r = (int) ((Math.random()*(max-min+1))+min);
			xFraise = rondsSize * r;

			// Random position Y fraise
			r = (int) ((Math.random()*(max-min+1))+min);
			yFraise = rondsSize * r;
		}
		
		        public void checkMyrtille() {

		        // Check si la tête est sur la position que la myrtille
			if((x[0] == xMyrtille) && (y[0] == yMyrtille)) {
				ronds++;
				locateFraise();
			}
		}
		
		        private void locateMyrtille() {
			int r;
                        // Random position X myrtille
			r = (int) ((Math.random()*(max-min+1))+min);
			xMyrtille = rondsSize * r;

			// Random position Y myrtille
			r = (int) ((Math.random()*(max-min+1))+min);
			yMyrtille = rondsSize * r;
		}
		


		
		        private void move() {
	         	// Mouvement des ronds
		        for(int i = ronds; i > 0; i--) {
				x[i] = x[i - 1];
				y[i] = y[i - 1];
			}
			// Mouvement tête vers la Gauche
			if(directionGauche) {
				x[0] -= rondsSize;
			}
                        // Mouvement tête vers la Droite
			if(directionDroite) {
				x[0] += rondsSize;
			}
                        // Mouvement tête vers le Haut
			if(directionHaut) {
				y[0] -= rondsSize;
			}
                        // Mouvement tête vers le Bas
			if(directionBas) {
				y[0] += rondsSize;
			}
		}
		
		
		        private void checkCollision() {
			// Collision Serpent
		        for(int i = ronds; i > 0; i --) {
				if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
					inGame = false;
				}
			}
			// Collision Bas
			if(y[0] >= boisLimiteBas) {
				inGame = false;
			}
                        // Collision Haut
			if(y[0] < boisLimiteHaut) {
				inGame = false;
			}
                        // Collision Droite
			if(x[0] >= boisLimiteDroite) {
				inGame = false;
			}
                        // Collision Gauche
			if(x[0] < boisLimiteGauche) {
				inGame = false;
			}
			// Arrêt de jeu
			if(!inGame) {
				t.stop();
			}
		}
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			 // Actions à executer
		    if(inGame) {
				checkFraise();
				checkCollision();
				move();
			}
		    // Actualiser le dessin
			repaint();
		}
		
		private class TAdapter extends KeyAdapter{
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();

	                        // Bouton Gauche
				if(key == KeyEvent.VK_LEFT && !directionDroite) {
					directionGauche = true;
					directionHaut = false;
					directionBas = false;
				}
	                        // Bouton Droite
				if(key == KeyEvent.VK_RIGHT && !directionGauche) {
					directionDroite = true;
					directionHaut = false;
					directionBas = false;
				}
				// Bouton haut
				if(key == KeyEvent.VK_UP && !directionBas) {
					directionHaut = true;
					directionDroite = false;
					directionGauche = false;
				}
	                        // Bouton Bas
				if(key == KeyEvent.VK_DOWN && !directionHaut) {
					directionBas = true;
					directionDroite = false;
					directionGauche = false;
				}
				// Pause
				if(key == KeyEvent.VK_SPACE) {
					if(pause == false){
						t.stop();
						pause = true;
					}else{
						t.start();
						pause = false;
					}
				}
			}
	  }
	}

