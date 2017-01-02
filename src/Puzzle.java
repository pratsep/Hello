import javafx.application.Application;
import javafx.stage.Stage;

public class Puzzle extends Application {
    private static Stage puzzleAla;
    public static Stage getStage() { return puzzleAla; }
    @Override
    public void start(Stage primaryStage) throws Exception {
        puzzleAla = new Stage();
        new algus();
    }
}