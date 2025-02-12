package spaceWarrior;

import java.util.Random;

public class Alien extends Sprite {
	public static final int MAX_ALIEN_SPEED = 5;
	private boolean alive;
	private boolean moveRight;
	private int speed;

	Alien(int x, int y){
		super(x,y);
		this.alive = true;

		Random r= new Random();
		this.speed= r.nextInt(Alien.MAX_ALIEN_SPEED-1)+1;
		this.moveRight = false;

	}

	//method that will move or change the x and y location of the alien
	void move(){
		if(this.moveRight == false){
			this.setDX(-this.speed);
			if(this.x<=0){
				this.x=0;
				this.moveRight= true;
			}
		}
		if(this.moveRight== true){
			this.setDX(this.speed);
			if(this.x >= GameStage.WINDOW_WIDTH-40){
				this.x=GameStage.WINDOW_WIDTH-40;
				this.moveRight= false;
			}
		}
		this.x += this.dx;
	}

	//getter
	public boolean isAlive() {
		return this.alive;
	}

	public void setIsAlive(boolean b){
		this.alive=b;
	}

}
