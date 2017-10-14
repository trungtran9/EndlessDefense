package bullet;

import enemy.Enemy;
import gui.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Bullet {

    private double x;
    private double y;
    private int dir;
    public Image img;
    public ImageView imageView;
    Pane layer;
    int frameCount;
    public boolean isEnd;

    Timeline timeline;


    public Bullet(Pane layer,double x, double y, int dir, Image img){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.img = img;
        imageView = new ImageView(img);
        imageView.relocate(x,y);
        this.layer = layer;
        addToPane();
        isEnd = false;
        frameCount = 0;

        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(30),(ActionEvent event) ->{
            nextFrame();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void addToPane(){
        this.layer.getChildren().add(this.imageView);
    }

    public void nextFrame(){
        frameCount++;
        if (frameCount < 15){
            switch (dir){
                case Settings.UP : y -= 18.0; break;
                case Settings.DOWN : y += 18.0; break;
                case Settings.LEFT : x -= 18.0; break;
                case Settings.RIGHT : x += 18.0; break;
                case Settings.UPLEFT : x -= (18.0 / Math.sqrt(2)); y -= (18.0 / Math.sqrt(2)); break;
                case Settings.UPRIGHT : x += 18.0 / Math.sqrt(2); y -= 18.0 / Math.sqrt(2); break;
                case Settings.DOWNLEFT : x -= 18.0 / Math.sqrt(2); y += 18.0 / Math.sqrt(2); break;
                case Settings.DOWNRIGHT : x += 18.0 / Math.sqrt(2); y += 18.0 / Math.sqrt(2); break;
            }
        }
        else isEnd = true;
        imageView.relocate(x,y);
    }

    public boolean hit(Enemy enemy){
        if( (x >= enemy.getX() && x <= enemy.getX() + Settings.SIZE) && (y >= enemy.getY()) && y <= enemy.getY() + Settings.SIZE){
            return true;
        }
        return false;
    }
}