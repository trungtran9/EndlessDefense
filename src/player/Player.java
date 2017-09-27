
package player;

import gui.Settings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
public class Player extends SpriteBase {

    double playerShipMinX;
    double playerShipMaxX;
    double playerShipMinY;
    double playerShipMaxY;

    Input input;

    double speed;

    public Player(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double speed, Input input) {

        super(layer, image, x, y, r, dx, dy, dr, health, damage);

        this.speed = speed;
        this.input = input;

        init();
    }


    private void init() {

        // calculate movement bounds of the player ship
        // allow half of the ship to be outside of the screen 
        playerShipMinX = 0 - image.getWidth() / 2.0;
        playerShipMaxX = Settings.SCENE_WIDTH - image.getWidth() / 2.0;
        playerShipMinY = 0 - image.getHeight() / 2.0;
        playerShipMaxY = Settings.SCENE_HEIGHT -image.getHeight() / 2.0;

    }

    public void processInput() {

        // ------------------------------------
        // movement
        // ------------------------------------

        // vertical direction
        if( input.isMoveUp()) {
            dy = -speed;
        } else if( input.isMoveDown()) {
            dy = speed;
        } else {
            dy = 0d;
        }

        // horizontal direction
        if( input.isMoveLeft()) {
            dx = -speed;
        } else if( input.isMoveRight()) {
            dx = speed;
        } else {
            dx = 0d;
        }

    }

    @Override
    public void move() {

        super.move();

        // ensure the ship can't move outside of the screen
        checkBounds();


    }

    private void checkBounds() {

        // vertical
        if( Double.compare( y, playerShipMinY) < 0) {
            y = playerShipMinY;
        } else if( Double.compare(y, playerShipMaxY) > 0) {
            y = playerShipMaxY;
        }

        // horizontal
        if( Double.compare( x, playerShipMinX) < 0) {
            x = playerShipMinX;
        } else if( Double.compare(x, playerShipMaxX) > 0) {
            x = playerShipMaxX;
        }

    }


    @Override
    public void checkRemovability() {
        // TODO Auto-generated method stub
    }

}