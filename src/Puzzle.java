import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Puzzle extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane alus = new Pane();
        Scene stseen = new Scene(alus, 500, 500);
        primaryStage.setScene(stseen);
        primaryStage.setTitle("Proovikas");
        Rectangle ruut = new Rectangle(150, 150, 200, 200);
        alus.getChildren().add(ruut);
        ruut.setFill(Color.YELLOWGREEN);
        ruut.setStroke(Color.RED);
        primaryStage.show();
    }
}
