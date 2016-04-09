package f2.spw;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args){
		JFrame frame = new JFrame("Alien Attack");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 650);
		frame.getContentPane().setLayout(new BorderLayout());
		
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp);
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
}
