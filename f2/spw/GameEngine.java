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
	private ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	private SpaceShip v;	
	private Timer timer;

	private long score = 0;	
	private double difficulty = 0.1;
	private int heart = 3;

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

	private void generateBullet(){
		Bullet b = new Bullet(v.x+27,v.y-30);
		gp.sprites.add(b);
		bullet.add(b);
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
				
			}
		}
		Iterator<EnemySecond> es_iter = enemiessec.iterator();
		while(es_iter.hasNext()){
			EnemySecond es = es_iter.next();
			es.proceed();
			
			if(!es.isAlive()){
				es_iter.remove();
				gp.sprites.remove(es);
				
			}
		}
		Iterator<Bullet> b_iter = bullet.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
				
			}
		}

		gp.updateGameUI(this);
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double br;
		for(Enemy e : enemies){
			er = e.getRectangle();
			for(Bullet b : bullet){
				br = b.getRectangle();
				if(br.intersects(er)){
					score += 100;
					e.notAlive();
					b.notAlive();
					return;
				}
			}
			if(er.intersects(vr)){
				if(heart>0){
					heart--;
					e.notAlive();
				}
				else
					die();
				return;
			}
			
		}
		Rectangle2D.Double ers;

		for(EnemySecond es : enemiessec){
			ers = es.getRectangle();
			for(Bullet b : bullet){
				br = b.getRectangle();
				if(br.intersects(ers)){
					score += 20;
					es.notAlive();
					b.notAlive();
					return;
				}
			}
			if(ers.intersects(vr)){

				if(heart>0){
					heart--;
					es.notAlive();
				}
				else
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
		case KeyEvent.VK_SPACE:
			generateBullet();
			break;				
		}
	}

	public long getScore(){
		return score;
	}

	public int getHeart(){
		return heart;
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
