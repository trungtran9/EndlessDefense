package gui;

import controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StartMenu extends VBox {

    public StartMenu(Main main){
        Button start = new Button("Start");
        Button about = new Button("About");
        Button help = new Button("Help");
        start.setStyle("-fx-font-size: 2em; ");
        about.setStyle("-fx-font-size: 2em; ");
        help.setStyle("-fx-font-size: 2em; ");
        this.getChildren().addAll(start,about,help);
        setSpacing(15.0);
        setAlignment(Pos.CENTER);
        start.setOnAction(e->{
            main.ctrl = new Controller(main);
            main.subLayer.getChildren().remove(this);
        });
    }
}
