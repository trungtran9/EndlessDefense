package player;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MusterPlayer extends Rectangle{
	public MusterPlayer(double x, double y){
		this.setFill(Color.BLACK);
		this.setHeight(20);
		this.setWidth(20);
		this.setX(x);
		this.setY(y);		
		
	}
}