package spaceWarrior;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Group;

public class MenuScreen {
	public static final int WINDOW_HEIGHT = 404;
	public static final int WINDOW_WIDTH = 720;
	private Scene scene;
	private Stage stage;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;

	private InstructionStage instruction;
	private GameStage gamestage;
	private AboutStage about;
	private GameOverStage gameover;

	private Button newGameButton;
	private Button instructionButton;
	private Button aboutButton;
	private Button quitButton;


	public MenuScreen() {
		this.root=new Group();
		this.scene = new Scene(root, MenuScreen.WINDOW_WIDTH,MenuScreen.WINDOW_HEIGHT);
		this.canvas = new Canvas( MenuScreen.WINDOW_WIDTH,MenuScreen.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();


		this.instruction = new InstructionStage(this);
		this.about= new AboutStage(this);
		this.gameover=new GameOverStage(this);

		// menu buttons
		this.newGameButton=this.createB1();
		this.instructionButton=this.createB2();
		this.aboutButton=this.createB3();
		this.quitButton=this.createB4();

		this.setProperties();
	}

	//New Game Button
	public Button createB1(){
		Button b1 = new Button("New Game");
		b1.setLayoutX(275);
		b1.setLayoutY(200);
		b1.setStyle("-fx-pref-height: 28px; -fx-pref-width: 175px; -fx-text-fill: white; -fx-background-color: darkslategray;");
		this.setMouseHandler(b1);
		return b1;
	}

	//Instruction Button
	public Button createB2(){
		Button b2 = new Button("Instruction");
		b2.setLayoutX(275);
		b2.setLayoutY(250);
		b2.setStyle("-fx-pref-height: 28px; -fx-pref-width: 175px; -fx-text-fill: white; -fx-background-color: darkslategray;");
		this.setMouseHandler(b2);
		return b2;
	}

	//About Button
	public Button createB3(){
		Button b3 = new Button("About");
		b3.setLayoutX(275);
		b3.setLayoutY(300);
		b3.setStyle("-fx-pref-height: 28px; -fx-pref-width: 175px; -fx-text-fill: white; -fx-background-color: darkslategray;");
		this.setMouseHandler(b3);
		return b3;
	}

	//Quit Button
	public Button createB4(){
		Button b4 = new Button("Quit");
		b4.setLayoutX(275);
		b4.setLayoutY(350);
		b4.setStyle("-fx-pref-height: 28px; -fx-pref-width: 175px; -fx-text-fill: white; -fx-background-color: darkslategray;");
		this.setMouseHandler(b4);
		return b4;
	}

	//creates an instance of GameStage
	public void gameStagePlay(){
		this.gamestage= new GameStage(this);
	}

	public void setProperties(){
		Font theFont = Font.font("Papyrus",FontWeight.BOLD,60);
		this.gc.setFont(theFont);

		this.gc.setFill(Color.WHITE);
		this.gc.fillText("Space Warriors", 150, MenuScreen.WINDOW_HEIGHT*0.3);
	}

	//sets the background image
	 public ImageView createDisplay() {
		 Image bg = new Image("images/display.gif");
	     ImageView view = new ImageView();
	     view.setImage(bg);

	     return view;
	}


	 public void setStage(Stage stage){
		 this.stage=stage;
		 ImageView imgview=this.createDisplay();

		 this.root.getChildren().addAll(imgview,this.canvas,this.newGameButton, this.instructionButton, this.aboutButton, this.quitButton);
		 this.stage.setTitle("Space Warrior");
		 this.stage.setScene(this.scene);
		 this.stage.show();
	}

	 private void setMouseHandler(Button b) {
	    	b.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	                System.out.println(b.getText());
	                if(b.getText()=="New Game"){ flashGameStage();}
	                if(b.getText()=="Instruction"){ flashInstructionStage();}
	                if(b.getText()=="About"){ flashAboutStage();}
	                if(b.getText()=="Quit") {System.exit(0);}
	            }
	        });
	    }

	 //change the scene to GameStage scene
	 void flashGameStage(){
			PauseTransition transition = new PauseTransition(Duration.seconds(1));
			transition.play();

			transition.setOnFinished(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent arg0) {
					gameStagePlay();
					stage.setScene(gamestage.getScene());
				}
			});
		}

	 //change the scene to instruction scene
	 void flashInstructionStage(){
			PauseTransition transition = new PauseTransition(Duration.seconds(1));
			transition.play();

			transition.setOnFinished(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent arg0) {
					stage.setScene(instruction.getScene());
				}
			});
		}

	 //change the scene to about scene
	 void flashAboutStage(){
			PauseTransition transition = new PauseTransition(Duration.seconds(1));
			transition.play();

			transition.setOnFinished(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent arg0) {
					stage.setScene(about.getScene());
				}
			});
		}

	 //change the scene to GameOverStage scene
	 void flashGameOverStage(int num){
			PauseTransition transition = new PauseTransition(Duration.seconds(1));
			transition.play();

			transition.setOnFinished(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent arg0) {
					gameover.setResult(num);
					stage.setScene(gameover.getScene());
				}
			});
		}

	 //change the scene back to Menu Screen
	 void flashHomeScreen(){
		 PauseTransition transition = new PauseTransition(Duration.seconds(1));
			transition.play();

			transition.setOnFinished(new EventHandler<ActionEvent>() {

				public void handle(ActionEvent arg0) {
					stage.setScene(scene);
				}
			});
	 }
}
