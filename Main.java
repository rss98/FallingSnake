package snake;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main {
	public static void main(String[]args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame e = new Snake();
				e.setVisible(true);
			}
		});
	}
}
