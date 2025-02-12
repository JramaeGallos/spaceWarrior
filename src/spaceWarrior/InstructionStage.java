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


public class InstructionStage {
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private MenuScreen menu;

	public InstructionStage(MenuScreen menu){
		this.menu=menu;
		this.root=new Group();
		this.scene = new Scene(root, MenuScreen.WINDOW_WIDTH, MenuScreen.WINDOW_HEIGHT);
		this.canvas = new Canvas(MenuScreen.WINDOW_WIDTH, MenuScreen.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.setProperties();

		ImageView imgView= menu.createDisplay();
		Button b= createBackButton();
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

	private void setProperties(){
		Font theFont = Font.font("Impact",FontWeight.BOLD,30);
		this.gc.setFont(theFont);

		this.gc.setFill(Color.WHITE);						//set font color of text
		this.gc.fillText("Instruction", MenuScreen.WINDOW_WIDTH*0.4, MenuScreen.WINDOW_HEIGHT*0.1);

		Font content= Font.font("Arial",15);
		this.gc.setFont(content);
		this.gc.setFill(Color.WHITE);
		this.gc.fillText("Brave pilot, our galaxy is under attack by the alien invaders! As a space warrior, you are tasked", 60, 100);
		this.gc.fillText("to protect it with your life.  From the galactic forces,  you are  bestowed an initial random strength of",20,125);
		this.gc.fillText("100-150 ultra-power. Indeed, you are extraordinary! But don’t be too confident for these creepy aliens ", 20,150);
		this.gc.fillText("are terribly destructive. Once collided with these terrible creatures, your ultra-power is reduced by 30. ", 20, 175);
		this.gc.fillText("And because you are too powerful and mighty, it will instantly kill them. Beware there’s more! The gi-", 20,200);
		this.gc.fillText("gantic boss alien wants to control the galaxy and it won’t let you hold the galactic forces. This is terribly", 20, 225);
		this.gc.fillText("more disastrous and can reduce your ultra-power by 50 once collided.", 20, 250);
		this.gc.fillText("I know you are terrified by now but no worries brave pilot, we won’t let you battle alone. The ga-", 60, 275);
		this.gc.fillText("lactic forces will send you magical stars that will make you immortal for 3 seconds and powerful pearls", 20, 300);
		this.gc.fillText("that will add 50 ultra-powers to your strength. Be sure to catch them all. They are now in attack! Held", 20, 325);
		this.gc.fillText("your bullets ready. Shoot them all and crash them! The future of the galaxy is in your hand.", 20, 350);
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

	Scene getScene(){
		return this.scene;
	}
}
