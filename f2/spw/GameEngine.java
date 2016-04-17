package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private SpaceShip v;	
	private Timer timer;

	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	
	
	private void process(){

		gp.updateGameUI(this);
		Rectangle2D.Double vr = v.getRectangle();
		
	}
	
	public void die(){
		timer.stop();
	}

	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.moveLR(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.moveLR(1);
			break;
		case KeyEvent.VK_UP:
			v.moveUD(-1);
			break;
		case KeyEvent.VK_DOWN:
			v.moveUD(1);
			break;	
		case KeyEvent.VK_S:
			die();
			break;			
		case KeyEvent.VK_P:
			start();
			break;
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
