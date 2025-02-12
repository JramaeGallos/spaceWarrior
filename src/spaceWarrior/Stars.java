package spaceWarrior;

import javafx.scene.image.Image;

public class Stars extends PowerUps {
	public final static Image STAR_IMAGE = new Image("images/star.png", Stars.STAR_WIDTH, Stars.STAR_WIDTH,false,false);
	public final static int STAR_WIDTH=30;

	Stars(int x, int y){
		super(x,y);

		this.loadImage(Stars.STAR_IMAGE);
	}
}
