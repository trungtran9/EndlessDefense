
package player;

import gui.Settings;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
public class Player extends SpriteBase {

    double playerShipMinX;
    double playerShipMaxX;
    double playerShipMinY;
    double playerShipMaxY;

    public boolean fire = false;


    Input input;

    double speed;

    public Player(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double speed, Input input,int dir) {

        super(layer, image, x, y, r, dx, dy, dr, health, damage, dir);

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
        if (input.isMoveUp() || input.isMoveLeft() || input.isMoveRight() || input.isMoveDown())
            setIdle(false);
        else setIdle(true);
            fire = false;
            // vertical direction
            if (input.isMoveUp()) {
                dir = Settings.UP;
                dy = -speed;
            } else if (input.isMoveDown()) {
                dy = speed;
                dir = Settings.DOWN;
            } else {
                dy = 0d;
            }

            // horizontal direction
            if (input.isMoveLeft()) {
                dir = Settings.LEFT;
                dx = -speed;
            } else if (input.isMoveRight()) {
                dx = speed;
                dir = Settings.RIGHT;
            } else {
                dx = 0d;
            }

            //diagonal direction
            if (input.isMoveUp() && input.isMoveLeft()) dir = Settings.UPLEFT;
            else if (input.isMoveUp() && input.isMoveRight()) dir = Settings.UPRIGHT;
            else if (input.isMoveDown() && input.isMoveLeft()) dir = Settings.DOWNLEFT;
            else if (input.isMoveDown() && input.isMoveRight()) dir = Settings.DOWNRIGHT;

        // ----------------------------------
        // shoot
        // ----------------------------------

        if (input.isFirePrimaryWeapon()){
            fire = true;
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