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
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<EnemySecond> enemiessec = new ArrayList<EnemySecond>();
	private SpaceShip v;	
	private Timer timer;

	private long score = 0;	
	private double difficulty = 0.1;
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
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*680), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}
	private void generateEnemySecond(){
		EnemySecond es = new EnemySecond((int)(Math.random()*680), 30);
		gp.sprites.add(es);
		enemiessec.add(es);
	}	
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		if(Math.random() < difficulty){
			generateEnemySecond();
		}	


		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}
		Iterator<EnemySecond> es_iter = enemiessec.iterator();
		while(es_iter.hasNext()){
			EnemySecond es = es_iter.next();
			es.proceed();
			
			if(!es.isAlive()){
				es_iter.remove();
				gp.sprites.remove(es);
				score += 20;
			}
		}


		gp.updateGameUI(this);
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
		}
		Rectangle2D.Double ers;

		for(EnemySecond es : enemiessec){
			ers = es.getRectangle();
			if(ers.intersects(vr)){
				die();
				return;
			}
		}		
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
		case KeyEvent.VK_D:
			difficulty += 0.3;
			break;			
		}
	}

	public long getScore(){
		return score;
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
