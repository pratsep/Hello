import javafx.application.Application;
import javafx.stage.Stage;

public class Puzzle extends Application {
    private static Stage voitnud;
    private static Stage puzzleAla;
    public static Stage getStage() { return puzzleAla; }
    public static Stage getFinalStage() { return voitnud; }
    @Override
    public void start(Stage primaryStage) throws Exception {
        puzzleAla = new Stage();
        voitnud = new Stage();
        new algus();
    }
}