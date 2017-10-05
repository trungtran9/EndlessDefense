package animation;

import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
public class Explosion {

    final ImageView explosionImg = new ImageView(new Image( getClass().getResource("../animation/explosion.png").toExternalForm()));
    final int width = 80;
    final int height = 80;
    final int col = 8;
    final int count = 8;
    final int offsetX = 0;
    final int offsetY = 0;

    Pane layer;
    public Explosion(Pane layer,double x, double y){

        this.layer = layer;
        explosionImg.setViewport(new Rectangle2D(offsetX,offsetY,width,height));
        explosionImg.relocate(x - 10,y - 10);
        final Animation animation = new SpriteAnimation(explosionImg, Duration.millis(1000),count,col,offsetX,offsetY,width,height);
        animation.setCycleCount(1);

        animation.play();
        animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeFromPane();
            }
        });
        addToPane();

    }

    public void addToPane(){
        this.layer.getChildren().add(explosionImg);
    }

    public void removeFromPane(){
        this.layer.getChildren().remove(explosionImg);
    }
}