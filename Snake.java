package snake;

import javax.swing.JFrame;

public class Snake extends JFrame{
	
	public Snake() {
		add(new Board());
		setResizable(false);
		pack();
		setTitle("Falling snake");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}
