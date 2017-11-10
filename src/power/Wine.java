package power;

import javafx.scene.image.Image;

public class Wine extends powerUp{
	
	
	public Wine(Image image, double speed, int duration, int existence) {
		super(image, speed, duration, existence);
		movementReverse = true;
	}
	
}
