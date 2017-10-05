package player;

import animation.Explosion;
import gui.Settings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

public abstract class SpriteBase {

    Image image;
    ImageView imageView;

    Pane layer;

    double x;
    double y;
    double r;

    double dx;
    double dy;
    double dr;

    double health;
    double damage;

    boolean removable = false;

    boolean isIdle;

    double w;
    double h;

    int count = 0;
    int frame = 0;
    private int [] id = {1,2,1,0};

    PixelReader reader;
	WritableImage newImg;

	int dir;
    
    boolean canMove = true;

    public SpriteBase(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double damage, int dir) {

        this.layer = layer;
        this.image = image;
        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;

        this.health = health;
        this.damage = damage;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        this.imageView.setRotate(r);
        
        this.dir = dir;
        setDirectionImage();
        this.w = newImg.getWidth(); // imageView.getBoundsInParent().getWidth();
        this.h = newImg.getHeight(); // imageView.getBoundsInParent().getHeight();
      
        addToLayer();

    }

    public void setDirectionImage(){
        reader = image.getPixelReader();
        int toChoose = 0;
        if (!isIdle) toChoose = count % 4;
        newImg = new WritableImage(reader,id[toChoose] * Settings.SIZE,dir * Settings.SIZE,Settings.SIZE,Settings.SIZE);
        this.imageView.setImage(newImg);

    }

    public void setIdle(boolean x){
        isIdle = x;
    }

    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {

        this.layer.getChildren().remove(this.imageView);
        Explosion e = new Explosion(layer,x,y);

    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void move() {
        if( !canMove)
            return;

        x += dx;
        y += dy;
        r += dr;


    }


    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }

    public ImageView getView() {
        return imageView;
    }

    public void updateUI() {
        frame++;
        if (frame % 10 == 0) count++;
        if (dir <= Settings.UP) setDirectionImage();


        imageView.relocate(x, y);
        imageView.setRotate(r);

    }

    public int getDir() {
        return dir;
    }

    public void setDir(int x){
        dir = x;
    }
    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }

    // TODO: per-pixel-collision
    public boolean collidesWith( SpriteBase otherSprite) {

        return ( otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);

    }

    /**
     * Reduce health by the amount of damage that the given sprite can inflict
     * @param sprite
     */
    public void getDamagedBy( SpriteBase sprite) {
        health -= sprite.getDamage();
    }

    /**
     * Set health to 0
     */
    public void kill() {
        setHealth( 0);
    }

    /**
     * Set flag that the sprite can be removed from the UI.
     */
    public void remove() {
        setRemovable(true);
    }

    /**
     * Set flag that the sprite can't move anymore.
     */
    public void stopMovement() {
        this.canMove = false;
    }

    public abstract void checkRemovability();

}