package power;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import player.Player;

public abstract class powerUp {
	
	Image image;
	public ImageView imageView = new ImageView();
	
	double speed;
	int duration;
	int existence;
	
	public int countForDuration;
	
	boolean movementReverse;
	
	
	
	
	public powerUp(Image image, double speed, int duration, int existence){
			this.image = image;
			this.duration = duration;
			this.speed = speed;
			this.existence = existence;
			this.imageView.setImage(image);
	}
	
	
	public void takeEffect(Player player){
		
	}
	
}
