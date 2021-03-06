package enemy;

import gui.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import player.Player;
import player.SpriteBase;

public class Enemy extends SpriteBase {

	
	
    public Enemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage, int dir) {
        super(layer, image, x, y, r, dx, dy, dr, health, damage, dir);
        
    }

    public double calcArc(Player player){
        double tanx = (this.getY() - player.getY()) / (this.getX() - player.getX());

        double arc =  Math.atan(tanx);
        if (player.getX() < this.getX())
            if (player.getY() > this.getY()) return (Math.PI + arc);
        else return arc - Math.PI;
        return arc;
    }
    public void move(Player player){

        double x = getX();
        double y = getY();
        this.setDr(getDr() + getR());

        double arc = calcArc(player);

        double deltaX = Math.abs(Math.cos(arc)) * this.getDx();
        double deltaY = Math.abs(Math.sin(arc)) * this.getDy();

        if (arc > -Math.PI/4 && arc <= Math.PI/4) setDir(Settings.RIGHT);
        else if (arc > Math.PI/4 && arc <= 3*Math.PI/4) setDir(Settings.DOWN);
        else if (arc > -3*Math.PI/4 && arc <= -Math.PI/4) setDir(Settings.UP);
        else setDir(Settings.LEFT);

        if (x < player.getX() || x > player.getX() + Settings.SIZE){
            x += player.getX() > x ? deltaX : -deltaX;
            setX(x);
        }
        if (y < player.getY() || y > player.getY() + Settings.SIZE) {
                y += player.getY() > y ? deltaY : -deltaY;
                setY(y);
            }
    }

    @Override
    public void checkRemovability() {

        if( Double.compare( getY(), Settings.SCENE_HEIGHT) > 0) {
            setRemovable(true);
        }


    }
}