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
    public TilePane tileOver, tileScore;

    Image bg = new Image(getClass().getResource("/pictures/background/Bg.png").toExternalForm());

    Canvas can = new Canvas();
    GraphicsContext gc = can.getGraphicsContext2D();

    public void showRestartButton() {

        tileOver.setPadding(new Insets(20, 10, 20, 0));
        tileOver.setHgap(10.0);
        tileOver.getChildren().addAll(restart,quit);
    }

    private void reset(Stage stage) throws Exception {
        start(stage);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        HBox root = new HBox();

        subLayer = new BorderPane();
        StartMenu sm = new StartMenu(this);
        Group mainPlay = new Group();
        subLayer.setCenter(sm);
        subLayer.setBottom(tileOver);

        can.setWidth(Settings.SCENE_WIDTH);
        can.setHeight(Settings.SCENE_HEIGHT);
        gc.drawImage(bg,0,0,Settings.SCENE_WIDTH,Settings.SCENE_HEIGHT);
        // create layers
        playfieldLayer = new Pane();


        tileOver = new TilePane(Orientation.HORIZONTAL);

        //Score
        tileScore = new TilePane(Orientation.VERTICAL);
        tileScore.setAlignment(Pos.TOP_LEFT);
        tileScore.setPrefColumns(1);
        tileScore.setPrefWidth(200.0);



        scoreLayer = new VBox(tileScore);
        scoreLayer.toFront();
        mainPlay.getChildren().add(playfieldLayer);
        mainPlay.getChildren().add(subLayer);
        mainPlay.getChildren().add(can);
        can.toBack();

        root.getChildren().add(scoreLayer);
        root.getChildren().add(mainPlay);




       scene = new Scene( root, Settings.SCENE_WIDTH + 200.0, Settings.SCENE_HEIGHT);



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