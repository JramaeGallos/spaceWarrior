package spaceWarrior;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.image.Image;


public class Rocket extends Sprite {
	private String name;
	private int strength;
	private boolean alive;

	private ArrayList<Bullet> bullets;
	public final static Image ROCKET_IMAGE = new Image("images/rockets.png",Rocket.SHIP_WIDTH,Rocket.SHIP_WIDTH,false,false);
	private final static int SHIP_WIDTH = 50;

	public Rocket(String name, int x, int y){
		super(x,y);
		this.name = name;
		Random r = new Random();
		this.strength = r.nextInt(150-100)+100;
		this.alive = true;
		this.loadImage(Rocket.ROCKET_IMAGE);

		this.bullets = new ArrayList<Bullet>();
	}

	public boolean isAlive(){
		if(this.alive) return true;
		return false;
	}
	public String getName(){
		return this.name;
	}

	public void setAlive(boolean life){
		this.alive=life;
	}

	public void setStrength(int i){
		this.strength=i;
	}

	public int getStrenght(){
		return this.strength;
	}

	//method that will get the bullets 'shot' by the ship
	public ArrayList<Bullet> getBullets(){
		return this.bullets;
	}

	//method called if spacebar is pressed
	public void shoot(){
		//compute for the x and y initial position of the bullet
		int x = (int) (this.x + this.width+20);
		int y = (int) (this.y + this.height/2);

		Bullet b= new Bullet(x,y);
		this.bullets.add(b);
    }

	//method called if up/down/left/right arrow key is pressed.
	public void move() {
		if(this.x >= GameStage.WINDOW_WIDTH-40) this.x= GameStage.WINDOW_WIDTH-40;
		if(this.y >= GameStage.WINDOW_HEIGHT-40) this.y= GameStage.WINDOW_HEIGHT-40;
		if(this.x <= 0) this.x=0;
		if(this.y <= 0) this.y=0;

		this.x += this.dx;
		this.y += this.dy;

	}

}

