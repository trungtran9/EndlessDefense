package gui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import controller.Controller;
import enemy.Enemy;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import player.Input;
import player.MusterPlayer;
import player.Player;
import player.SpriteBase;



public class Main extends Application {


    public Pane playfieldLayer;
    public Pane scoreLayer;


    public Scene scene;

    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();

        root.getChildren().add( playfieldLayer);
        root.getChildren().add( scoreLayer);

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        primaryStage.setScene( scene);
        primaryStage.show();
        
        Controller ctrl = new Controller(this);
    }

    public static void main(String[] args) {
        launch(args);
    }

}