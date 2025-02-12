package spaceWarrior;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.*;

public class GameOverStage {
	public final static int WIN=1;
	public final static int LOSE=0;
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private HBox horBox;
	private GraphicsContext gc;
	private MenuScreen menu;

	public GameOverStage(MenuScreen menu){
		this.menu=menu;
		this.root=new Group();
		this.scene = new Scene(root, MenuScreen.WINDOW_WIDTH, MenuScreen.WINDOW_HEIGHT);
		this.canvas = new Canvas(MenuScreen.WINDOW_WIDTH, MenuScreen.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();

		ImageView imgView= menu.createDisplay();
		horBox= createHBox();
		this.root.getChildren().addAll(imgView,canvas, horBox);
	}

	public void setResult(int num){
		this.setProperties(num);
	}

	private HBox createHBox(){
		HBox hbox=new HBox();

		Button b1 = new Button("Back to Home");
		Button b2= new Button("Quit");

		b1.setStyle("-fx-pref-height: 28px; -fx-pref-width: 150px; -fx-text-fill: white; -fx-background-color: darkslategray;");
		b2.setStyle("-fx-pref-height: 28px; -fx-pref-width: 150px; -fx-text-fill: white; -fx-background-color: darkslategray;");

		hbox.setAlignment(Pos.BASELINE_LEFT);
	 	hbox.setPadding(new Insets(10));
	 	hbox.setSpacing(400);

		this.setMouseHandler(b1);
		this.setMouseHandler(b2);

		hbox.getChildren().addAll(b1,b2);
		return hbox;
	}

	 private void setMouseHandler(Button b) {
	    	b.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent e) {
	                System.out.println(b.getText());
	                if (b.getText()=="Back to Home"){ menu.flashHomeScreen();}
	                if (b.getText()=="Quit"){System.exit(0);}
	            }
	        });
	    }

	private void setProperties(int num){
		Font theFont = Font.font("Impact",FontWeight.BOLD,30);
		this.gc.setFont(theFont);

		this.gc.setFill(Color.WHITE);
		if(num==0){ this.gc.fillText("Game Over!", MenuScreen.WINDOW_WIDTH*0.4, MenuScreen.WINDOW_HEIGHT*0.3);}
		if(num==1){this.gc.fillText("You Win!", MenuScreen.WINDOW_WIDTH*0.4, MenuScreen.WINDOW_HEIGHT*0.3);}
	}

	Scene getScene(){
		return this.scene;
	}
}
