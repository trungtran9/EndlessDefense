package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;


public class Main extends Application {


    public Pane playfieldLayer;
    public VBox scoreLayer;

    public Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new VBox();

        root.getChildren().add( playfieldLayer);
        root.getChildren().add( scoreLayer);

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        Image bg = new Image(getClass().getResource("../pictures/background/Bg.png").toExternalForm());
        ImagePattern pattern = new ImagePattern(bg);
        scene.setFill(pattern);

        primaryStage.setScene( scene);
        primaryStage.show();


        Controller ctrl = new Controller(this);
    }

    public static void main(String[] args) {
        launch(args);
    }

}