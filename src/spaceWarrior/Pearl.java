package spaceWarrior;

import javafx.scene.image.Image;

public class Pearl extends PowerUps {
	public final static Image PEARL_IMAGE = new Image("images/pearl.png", Pearl.PEARL_WIDTH, Pearl.PEARL_WIDTH,false,false);
	public final static int PEARL_WIDTH=30;

	Pearl(int x, int y){
		super(x,y);

		this.loadImage(Pearl.PEARL_IMAGE);
	}
}
