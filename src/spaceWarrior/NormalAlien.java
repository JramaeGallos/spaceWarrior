package spaceWarrior;

import javafx.scene.image.Image;

public class NormalAlien extends Alien {
	public final static Image ALIEN_IMAGE = new Image("images/aliens.png",NormalAlien.ALIEN_WIDTH,NormalAlien.ALIEN_WIDTH,false,false);
	public final static int ALIEN_WIDTH=50;

	NormalAlien(int x, int y){
		super(x,y);

		this.loadImage(NormalAlien.ALIEN_IMAGE);
	}
}
