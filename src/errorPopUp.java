import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//thenewbostoni video põhjal tehtud
public class errorPopUp {
    public void kuvaPopUp(String teade){
        Stage aken = new Stage();
        aken.setX(300);
        aken.setY(300);
        aken.initModality(Modality.APPLICATION_MODAL);
        VBox pane = new VBox(20);
        pane.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        Scene stseen = new Scene(pane, 200, 100);
        Button nupp = new Button("Enam ei tee!");
        Label tekst = new Label(teade);
        nupp.setOnAction(event -> aken.close());
        pane.getChildren().addAll(tekst, nupp);
        aken.setScene(stseen);
        aken.showAndWait();
    }
}
