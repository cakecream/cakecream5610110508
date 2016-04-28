package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	BufferedImage heart;
	BufferedImage bg;

	public GamePanel() {
		bi = new BufferedImage(700, 650, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.BLACK);
		
		try{
 			bg = ImageIO.read(new File("f2/image/bg.png"));
 		}
 		catch(IOException e){
 		}	

 		try{
 			heart = ImageIO.read(new File("f2/image/heart.png"));
 		}
 		catch(IOException e){
 		}
 
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 700, 650);

		big.drawImage(bg, 0, 0, 700, 650, null);
		big.setColor(Color.WHITE);		
		big.drawString(String.format("%05d", reporter.getScore()), 630, 40);	

		big.setColor(Color.WHITE);
		big.drawImage(heart, 20, 35, 25, 20, null);	
		big.drawString(String.format("%02d", reporter.getHeart()), 50, 55);

		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}
	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
