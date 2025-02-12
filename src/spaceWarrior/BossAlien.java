package spaceWarrior;

import javafx.scene.image.Image;

public class BossAlien extends Alien{
	private int health;
	public final static Image ALIEN_IMAGE = new Image("images/aliens.png",BossAlien.ALIEN_WIDTH,BossAlien.ALIEN_WIDTH,false,false);
	public final static int ALIEN_WIDTH=200;

	BossAlien(int x, int y){
		super(x,y);
		this.health=3000;

		this.loadImage(BossAlien.ALIEN_IMAGE);
	}

	public void setHealth(int damage){
		this.health-=damage;
	}

	public int getHealth(){
		return this.health;
	}
}
