package spaceWarrior;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;



public class GameStage {
	public static final int WINDOW_HEIGHT = 404;
	public static final int WINDOW_WIDTH = 720;
	private Scene scene;
	private Group rootGame;
	private Canvas canvas;
	private GraphicsContext gc;
	private MenuScreen menu;
	private GameTimer gametimer;


	public GameStage(MenuScreen menu) {
		this.menu=menu;
		this.rootGame=new Group();
		this.scene = new Scene(rootGame, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		ImageView imgView= menu.createDisplay();

		this.rootGame.getChildren().addAll(imgView,canvas);

		this.gametimer = new GameTimer(this.gc,this.scene, this.menu);
		this.gametimer.start();
	}

	Scene getScene(){
		return this.scene;
	}
}
