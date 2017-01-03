import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class kontrollimine {
    public static Button looUus = new Button("Sisesta oma andmed");
    public static String duration = new String();
    public static TextField looKasutaja = new TextField("Lisa oma nimi");
    StackPane lopuPilt = new StackPane();
    Scene loppStseen = new Scene(lopuPilt);
    public kontrollimine() {
        int asend = 0;
        for (int i = 0; i < algus.juppe; i++) {
            if (looPuzzle.ruudustik.getChildren().get(i).getRotate() != 0){
                asend = 1;
            }
        }
        if (looPuzzle.list.equals(looPuzzle.kontroll) && asend == 0){
            for (int i = 0; i < algus.juppe; i++) {
                if (looPuzzle.ruudustik.getChildren().get(i).getOpacity() != 1){
                    looPuzzle.ruudustik.getChildren().get(i).setOpacity(1);
                }
            }
            //Stage voitnud = new Stage();
            VBox ala = new VBox();
            Scene lopuStseen = new Scene(ala, 200, 200);
            Puzzle.getFinalStage().setScene(lopuStseen);
            Puzzle.getFinalStage().show();
            Puzzle.getFinalStage().setX(300);
            Puzzle.getFinalStage().setTitle("Mäng läbi");
            //Aja võtmine
            duration = new SimpleDateFormat("mm:ss").format(new Date(System.currentTimeMillis() - looPuzzle.startTime));
            Label lopp = new Label("Puzzle on koos!");
            Label lopuaeg = new Label(duration);
            ala.getChildren().addAll(lopp, lopuaeg, looKasutaja, looUus);
            new skoor(algus.juppe);

            Image win = new Image("win/win.png");
            ImageView winIm = new ImageView();
            winIm.setImage(win);
            ImageView tehtud = new ImageView();
            tehtud.setImage(looPuzzle.taisPilt);
            lopuPilt.getChildren().addAll(tehtud, winIm);
            Puzzle.getStage().setScene(loppStseen);
            Puzzle.getFinalStage().setOnCloseRequest(event -> {
                Puzzle.getStage().close();
            });

        }
    }
}
