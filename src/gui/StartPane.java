package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StartPane extends VBox{

    public StartPane(Main main){

        setPrefSize(150,150);
        setPadding(new Insets(15, 12, 15, 12));
        setSpacing(10);
        setStyle("-fx-background-color: #336699;");
        Button start = new Button ("Start");
        Button help = new Button ("Help");
        Button about = new Button ("about");
        this.getChildren().addAll(start,help,about);

    }
}
