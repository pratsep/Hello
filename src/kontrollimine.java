import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.Date;

public class kontrollimine {
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
            Stage voitnud = new Stage();
            VBox ala = new VBox();
            Scene lopuStseen = new Scene(ala, 50, 100);
            voitnud.setScene(lopuStseen);
            voitnud.show();
            voitnud.setX(300);
            voitnud.setTitle("Mäng läbi");
            //Aja võtmine
            String duration = new SimpleDateFormat("mm:ss").format(new Date(System.currentTimeMillis() - looPuzzle.startTime));
            Label lopp = new Label("Puzzle on koos!");
            Label lopuaeg = new Label(duration);
            ala.getChildren().addAll(lopp, lopuaeg);

            Image win = new Image("win/win.png");
            ImageView winIm = new ImageView();
            winIm.setImage(win);
            ImageView tehtud = new ImageView();
            tehtud.setImage(looPuzzle.taisPilt);
            lopuPilt.getChildren().addAll(tehtud, winIm);
            Puzzle.getStage().setScene(loppStseen);
            voitnud.setOnCloseRequest(event -> {
                Puzzle.getStage().close();
            });

        }
    }
}
