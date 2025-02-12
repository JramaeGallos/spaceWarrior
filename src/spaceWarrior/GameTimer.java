package spaceWarrior;

import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class GameTimer extends AnimationTimer {
	private GraphicsContext gc;
	private Scene theScene;
	private MenuScreen menu;

	private Rocket myRocket;
	private ArrayList<Alien> aliens;
	private ArrayList<PowerUps> powers;
	private Alien boss;
	private PowerUps pearl;
	private PowerUps star;
	public static final int MAX_NUM_ALIENS = 3;
	public static final int INITIAL_NUM_ALIENS=7;

	private long startSpawn;
	private int spawnCtr;
	private int bossCtr;
	private int powerCtr;
	private int addStrengthCtr;
	private boolean activatedStar=false;
	private boolean catchStar=false;

	private int starSec=3;
	private int starTimerStart;
	private int seconds=60;
	private int score;

	GameTimer(GraphicsContext gc, Scene theScene, MenuScreen menu){
		this.menu=menu;
		this.gc = gc;
		this.theScene = theScene;
		this.myRocket = new Rocket("lupet",150,250);
		this.startSpawn = System.nanoTime();

		this.aliens = new ArrayList<Alien>();
		this.powers= new ArrayList<PowerUps>();

		this.loadAliens();
		this.createBoss();
		this.handleKeyPressEvent();

		this.timer();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

		this.showCount();

		//change the location of the image
		this.myRocket.move();
		this.moveAliens();
		this.moveBullets();

		//draws the image in the canvas
		this.myRocket.render(this.gc);
		this.renderAliens();
		this.renderBullets();

		this.gameWon();

		try{
			this.rocketHitsAlien();
			this.bulletsHitsAlien();
		} catch (Exception e){}


		long currentSec = TimeUnit.NANOSECONDS.toSeconds(currentNanoTime);
		long startSec = TimeUnit.NANOSECONDS.toSeconds(this.startSpawn);


		//spawn 3 aliens every 5 seconds
		long intervalAllien= (currentSec - startSec)%5;
		if(this.spawnCtr< GameTimer.MAX_NUM_ALIENS){
			if(intervalAllien==4){
				this.spawnAliens();
				this.spawnCtr++;
			}
		}

		if(intervalAllien == 3){
			this.spawnCtr=0;
		}


		//adds the boss alien to the array list of aliens when the timer reaches 30 seconds
		if(this.seconds==30){
			if(this.bossCtr<1){
				this.aliens.add(this.boss);
				this.bossCtr++;
			}
		}

		//loads and display the power up at specific interval
		if(this.seconds <=50){
			long interval= (currentSec - startSec)%10;
			if(interval==0 ||  interval==1 ||  interval==2 ||  interval==3 ||  interval==4){
				if(this.powerCtr<1){
					this.loadPearl();
					this.loadStar();
					this.powers.add(this.star);
					this.powers.add(this.pearl);
					this.powerCtr++;
				}
				this.rocketCatchesStar();
				this.rocketCatchesPearl();
				this.renderPowers();
			} else{
				if(this.powerCtr==1){
					this.powers.remove(this.star);
					this.powers.remove(this.pearl);
				}
				this.powerCtr=0;
				this.addStrengthCtr=0;
			}

		}

		//checks if the rocket catches the star and sets the rocket to immortality
		if(this.catchStar){
			if(this.starTimerStart<1){
				this.timerStarCtr();
				this.starTimerStart=1;
			}
		}

		if(this.starSec==0){
			this.activatedStar=false;
			this.catchStar=false;
			this.starTimerStart=0;
			this.starSec=3;
		}
	}

	//stops the animation and changes the scene when the timer ends
	private void gameWon(){
		if(this.seconds==0){
			if(this.myRocket.isAlive()){
				this.stop();
				this.menu.flashGameOverStage(GameOverStage.WIN);
			}
		}
	}

	//star activation timer
	private void timerStarCtr(){
		Timeline timerStar = new Timeline(new KeyFrame(Duration.seconds(1), ev ->{
			this.starSec--;
		}));
		timerStar.setCycleCount(3);
		if(this.starSec>0){
			timerStar.play();
			this.activatedStar=true;
		}else timerStar.stop();
	}

	//game timer
	private void timer(){
		Timeline timerCount = new Timeline(new KeyFrame(Duration.seconds(1.0),  ev->{
			this.seconds--;
		}));
		timerCount.setCycleCount(60);
		if(this.seconds > 0){
			timerCount.play();
		}else {
			timerCount.stop();
			this.seconds =0;
		}

	}

	// displays the game status bar
	private void showCount(){
		String time= null;
		if(this.seconds == 60){
			time = "01:00";
		}else if(this.seconds < 60 && this.seconds >=10){
			time= "00:"+ this.seconds;
		}else if(this.seconds <10){
			time= "00:0"+ this.seconds;
		}else if(this.seconds <= 0){
			time= "00:00";
		}

		String textScore = null;
		if(score==0){
			textScore="0000";
		}
		else if(score<10){
			textScore="000"+ score;
		}
		else if(score<100){
			textScore="00"+score;
		}

		String immortal=null;
		if(this.activatedStar){
			immortal="Immortal Mode";
		}else immortal="  ";

		String text="Time: "+time + "     Score: "+ textScore + "     Strength: " + this.myRocket.getStrenght() + "     "+ immortal;
		Font theFont = Font.font("pxlxxl Bold",20);
		this.gc.setFont(theFont);
		this.gc.setFill(Color.WHITE);
		this.gc.fillText(text, 20,20);
	}

	//instantiate the initial seven aliens at random location and load it in this.aliens
	private void loadAliens(){
		Random r = new Random();
		for(int i=0; i<GameTimer.INITIAL_NUM_ALIENS; i++){
			int x = GameStage.WINDOW_WIDTH-50;
			int y = r.nextInt(GameStage.WINDOW_HEIGHT-40);
			Alien a= new NormalAlien(x,y);
			this.aliens.add(a);
		}
	}

	//method that will spawn/instantiate three aliens at a random x,y location
	synchronized private void spawnAliens(){
		Random r = new Random();
		int x = r.nextInt(GameStage.WINDOW_WIDTH);
		int y = r.nextInt(GameStage.WINDOW_HEIGHT-40);
		Alien a= new NormalAlien(x,y);
		this.aliens.add(a);
	}

	//creates an instance of boss
	private void createBoss(){
		Random r = new Random();
		int x = GameStage.WINDOW_WIDTH-50;
		int y = r.nextInt(GameStage.WINDOW_HEIGHT-60);
		boss= new BossAlien(x,y);
	}

	//creates an instance of pearl
	private void loadPearl(){
		Random r = new Random();
		int x = r.nextInt(GameStage.WINDOW_WIDTH/2);
		int y = r.nextInt(GameStage.WINDOW_HEIGHT-60);
		pearl= new Pearl(x,y);
	}

	//creates an instance of star
	private void loadStar(){
		Random r = new Random();
		int x = r.nextInt(GameStage.WINDOW_WIDTH/2);
		int y = r.nextInt(GameStage.WINDOW_HEIGHT-60);
		star= new Stars(x,y);
	}

	//method that will render/draw the aliens to the canvas
	private void renderAliens() {
		for (Alien a : this.aliens){
			a.render(this.gc);
		}
	}

	//method that will render/draw the bullets to the canvas
	private void renderBullets() {
		for (Bullet b: this.myRocket.getBullets()){
			b.render(this.gc);
		}
	}

	////method that will render/draw the powers to the canvas
	private void renderPowers(){
		for(PowerUps p: this.powers){
			p.render(this.gc);
		}
	}


	//method that will move the bullets shot by a ship
	private void moveBullets(){
		ArrayList<Bullet> bList = this.myRocket.getBullets();

		for(int i = 0; i < bList.size(); i++){
			Bullet b = bList.get(i);
			if(b.getVisible()==true){
				b.move();
			}else bList.remove(b);
		}
	}

	//method that will move the fishes
	private void moveAliens(){
		for(int i = 0; i < this.aliens.size(); i++){
			Alien a = this.aliens.get(i);
			if(a.isAlive()==true){
				a.move();
			}else this.aliens.remove(a);
		}
	}

	//method the will checks if a rocket catches a pearl and performs the necessary conditions
	private void rocketCatchesPearl(){
		if(this.myRocket.collidesWith(this.pearl)){
			if (this.addStrengthCtr<1){
				this.myRocket.setStrength(this.myRocket.getStrenght()+50);
				this.addStrengthCtr++;
			}
			this.pearl.setVisible(false);
			this.powers.remove(this.pearl);
		}
	}

	//method the will checks if a rocket catches a star and performs the necessary conditions
	private void rocketCatchesStar(){
		if(this.myRocket.collidesWith(this.star)){
			this.catchStar=true;
			this.powers.remove(this.star);
		}
	}

	//method the will checks is a rocket hits an alien and performs the necessary conditions
	private void rocketHitsAlien(){
		for(int i=0; i< this.aliens.size(); i++){
			Alien f= this.aliens.get(i);
			if(f.collidesWith(this.myRocket)){
				if(f instanceof BossAlien){
					if(this.activatedStar==false){
						this.myRocket.setStrength(this.myRocket.getStrenght()-50);
					}
				}
				else{
					if(this.activatedStar==false){
						this.myRocket.setStrength(this.myRocket.getStrenght()-30);
					}
					f.setIsAlive(false);
					this.aliens.remove(f);
				}
				if(isRocketDead()){
					this.gameOver();
				}
			}
		}
	}

	//method the will checks if a bullet hits an alien and performs the necessary conditions
	private void bulletsHitsAlien(){
		for(Bullet b: this.myRocket.getBullets()){
			for(Alien f: this.aliens){;
				if(b.collidesWith(f)){
					if(f instanceof BossAlien){
						BossAlien bF=(BossAlien) f;
						bF.setHealth(this.myRocket.getStrenght());
						if(this.isBossDead(bF)){
							this.aliens.remove(f);
							this.score++;
							f.setIsAlive(false);
							b.setVisible(false);
							this.myRocket.getBullets().remove(b);
						}else b.setVisible(false); this.myRocket.getBullets().remove(b);
					}
					else{
						f.setIsAlive(false);
						b.setVisible(false);
						this.aliens.remove(f);
						this.score++;
						this.myRocket.getBullets().remove(b);
					}
				}
			}
		}

	}

	//method that checks if boss is dead
	private boolean isBossDead(BossAlien bF){
		if(bF.getHealth()<=0){
			return true;
		}else return false;
	}

	//method that  checks if rocket is dead
	private boolean isRocketDead(){
		if(this.myRocket.getStrenght()<=0){
			return true;
		}else return false;
	}

	//sets the game over methods
	private void gameOver(){
		this.myRocket.setAlive(false);
		this.stop();
		this.menu.flashGameOverStage(GameOverStage.LOSE);
	}


	//method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
            	KeyCode code = e.getCode();
                moveMyRocket(code);
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		            	KeyCode code = e.getCode();
		                stopMyRocket(code);
		            }
		        });
    }

	//method that will move the ship depending on the key pressed
	private void moveMyRocket(KeyCode ke) {
		if(ke==KeyCode.UP) this.myRocket.setDY(-10);

		if(ke==KeyCode.LEFT) this.myRocket.setDX(-10);

		if(ke==KeyCode.DOWN) this.myRocket.setDY(10);

		if(ke==KeyCode.RIGHT) this.myRocket.setDX(10);

		if(ke==KeyCode.SPACE) this.myRocket.shoot();

		System.out.println(ke+" key pressed.");
   	}

	//method that will stop the ship's movement; set the ship's DX and DY to 0
	private void stopMyRocket(KeyCode ke){
		this.myRocket.setDX(0);
		this.myRocket.setDY(0);
	}

}
