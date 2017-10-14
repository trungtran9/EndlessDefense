package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;



public class Main extends Application {
    public Controller ctrl;

    public Pane playfieldLayer;
    public Pane scoreLayer;

    Button restart;

    public Scene scene;

    public void showRestartButton() {
        scoreLayer.getChildren().add(restart);
    }

    private void reset(Stage stage) throws Exception {
        start(stage);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        Group root = new Group();

        // create layers
        playfieldLayer = new Pane();
        scoreLayer = new Pane();

        root.getChildren().add( playfieldLayer);
        root.getChildren().add( scoreLayer);

        scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        Image bg = new Image(getClass().getResource("/pictures/background/Bg.png").toExternalForm());
        ImagePattern pattern = new ImagePattern(bg);
        scene.setFill(pattern);

        restart = new Button("Reset?");
        restart.setStyle("-fx-font-size: 2em; ");
        restart.relocate(Settings.SCENE_WIDTH/2 - Settings.SIZE, Settings.SCENE_HEIGHT / 2 + Settings.SIZE);
        restart.setOnAction(e -> {
            playfieldLayer.getChildren().remove(restart);
            try {
                reset(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


        primaryStage.setScene( scene);
        primaryStage.show();


        ctrl = new Controller(this);

    }

    public static void main(String[] args) {

        launch(args);
    }

}