package spaceWarrior;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AboutStage {
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private MenuScreen menu;

	public AboutStage(MenuScreen menu){
		this.menu=menu;
		this.root=new Group();
		this.scene = new Scene(root, MenuScreen.WINDOW_WIDTH, MenuScreen.WINDOW_HEIGHT);
		this.canvas = new Canvas(MenuScreen.WINDOW_WIDTH, MenuScreen.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.setProperties();

		ImageView imgView= menu.createDisplay();
		Button b=createBackButton();
		this.root.getChildren().addAll(imgView,canvas, b);
	}

	private Button createBackButton(){
		Button b1 = new Button("Back to Home");
		b1.setLayoutX(20);
		b1.setLayoutY(20);
		b1.setStyle("-fx-pref-height: 28px; -fx-pref-width: 150px; -fx-text-fill: white; -fx-background-color: darkslategray;");
		this.setMouseHandler(b1);
		return b1;
	}

	 private void setMouseHandler(Button b) {
	    	b.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	                System.out.println(b.getText());
	                menu.flashHomeScreen();
	            }
	        });
	    }

	private void setProperties(){
		Font theFont = Font.font("Impact",FontWeight.BOLD,30);
		this.gc.setFont(theFont);

		this.gc.setFill(Color.WHITE);						//set font color of text
		this.gc.fillText("About the Game", MenuScreen.WINDOW_WIDTH*0.4, MenuScreen.WINDOW_HEIGHT*0.1);

		Font contentTitle= Font.font("Arial",FontWeight.BOLD, 20);
		this.gc.setFont(contentTitle);
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("Game Developer: ", 20, 100);
		this.gc.fillText("Credits: ", 20, 170);

		Font content= Font.font("Arial",15);
		this.gc.setFont(content);
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("Jramae A. Gallos (BS Computer Science, Batch 2020 – University of the Philippines Los Baños)", 60,125);
		this.gc.fillText("-	CMSC 22 base code- Lab Mini Project Template", 60, 195);
		this.gc.fillText("-	Rocket.png- (Retrieved from: https://www.subpng.com/png-igtkth/)", 60, 220);
		this.gc.fillText("-	Alien.png – (Retrieved from: https://www.nicepng.com/maxp/u2e6w7a9w7e6q8w7/)", 60, 245);
		this.gc.fillText("-	Background.gif – (Retrieved from: https://commons.wikimedia.org/wiki/File:Backgorund.gif)", 60, 270);
		this.gc.fillText("-	Star.png – (Retrieved from: https://www.freeiconspng.com/images/stars-png)", 60, 295);
		this.gc.fillText("-	Pearl.png – (Retrieved from: https://www.pngitem.com/middle/iwoobbR_red-silver-ball-pearl-", 60, 320);
		this.gc.fillText("diamond-png-png-images/)", 95, 345);
	}

	Scene getScene(){
		return this.scene;
	}
}
