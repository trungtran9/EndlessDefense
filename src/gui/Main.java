package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;



public class Main extends Application {
    public Controller ctrl;

    public Pane playfieldLayer;
    public VBox scoreLayer;

    public BorderPane subLayer;
    Button restart;
    Button quit;
    public Scene scene;
    public HBox tileOver, tileScore;
    public StartMenu sm;

    Image bg = new Image(getClass().getResource("/pictures/background/Bg.png").toExternalForm());

    Canvas can = new Canvas();
    GraphicsContext gc = can.getGraphicsContext2D();

    public void showRestartButton() {

        tileOver.setPadding(new Insets(20, 10, 20, 0));
        tileOver.setSpacing(100.0);
        tileOver.getChildren().addAll(restart,quit);
    }

    private void reset(Stage stage) throws Exception {
        start(stage);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        Group root = new Group();

        subLayer = new BorderPane();
        sm = new StartMenu(this);
        
        //Group mainPlay = new Group();
        
       
        
        // create layers
        playfieldLayer = new Pane();


        tileOver = new HBox();

        //Score
        tileScore = new HBox();

        subLayer.setPrefWidth(Settings.SCENE_WIDTH);
        subLayer.setTop(tileScore);
        subLayer.setCenter(sm);
        subLayer.setBottom(tileOver);

        tileScore.setPrefHeight(Settings.SCENE_HEIGHT / 4);
        tileOver.setPrefHeight(Settings.SCENE_HEIGHT / 4);
        sm.setPrefHeight(Settings.SCENE_HEIGHT / 2);

        subLayer.setAlignment(sm,Pos.CENTER);
        tileOver.setAlignment(Pos.BOTTOM_CENTER);
        tileScore.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(playfieldLayer);
        root.getChildren().add(subLayer);




       scene = new Scene( root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
       ImagePattern background = new ImagePattern(bg);
       scene.setFill(background);


        restart = new Button("Reset?");
        quit = new Button("Next time..");
        quit.setStyle("-fx-font-size: 2em; ");
        restart.setStyle("-fx-font-size: 2em; ");

        restart.setOnAction(e -> {
            playfieldLayer.getChildren().remove(restart);
            try {
                reset(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        quit.setOnAction(e -> {
            primaryStage.close();
            try {
                stop();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        primaryStage.setScene( scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

}