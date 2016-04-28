package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpaceShip extends Sprite{

	int step = 8;
	BufferedImage image;

	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		try{
			image = ImageIO.read(new File("f2/image/Spaceship.png"));
		}
		catch(IOException e){

		}
	}

	@Override
	public void draw(Graphics2D g) {
		//g.setColor(Color.RED);
		//g.fillRect(x, y, width, height);
		g.drawImage(image, x, y, width, height, null);
	
	}
	public void moveLR(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;

		if(x > 690 - width)
			x = 690 - width;
	}		
	public void moveUD(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		
		if(y >  600- height)
			y = 600 - height;
	}
	
}
