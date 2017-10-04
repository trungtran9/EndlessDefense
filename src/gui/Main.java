package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;



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
        int i = ctrl.frame;
        i = i + 1;
    }

    public static void main(String[] args) {
        launch(args);
    }

}