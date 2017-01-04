import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import java.util.Date;

public class kontrollimine {
    public static Button looUus = new Button("Sisesta oma andmed");
    public static String duration = new String();
    public static TextField looKasutaja = new TextField("Lisa oma nimi");
    StackPane lopuPilt = new StackPane();
    Scene loppStseen = new Scene(lopuPilt);
    //Konstruktor mis kontrollib, kas puzzle on koos
    public kontrollimine() {
        int asend = 0;
        //Kontrollime kas GridPanel on mõni jupp veel pööramata
        for (int i = 0; i < algus.juppe; i++) {
            if (looPuzzle.ruudustik.getChildren().get(i).getRotate() != 0){
                asend = 1;
            }
        }
        //Võrdleme esialgselt loodud listi kontrolliks loodud listiga kui kõik pildid on õigesse asendisse pööratud ja
        //määrame ära mis siis juhtub
        if (looPuzzle.list.equals(looPuzzle.kontroll) && asend == 0){
        for (int i = 0; i < algus.juppe; i++) {                                 //Kontrollime kas mõni jupp on veel
                if (looPuzzle.ruudustik.getChildren().get(i).getOpacity() != 1){//peale lõppu jäänud "aktiivseks" ja
                    looPuzzle.ruudustik.getChildren().get(i).setOpacity(1);     //muudame mitteaktiivseks
                }
            }
            //Loome uue stseeni lõpptulemuse kuvamiseks
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
            new skoor(algus.juppe);                                             //Käivitame skoori leidmise

            //Lisame puzzle lahendamise korral puzzle pildi peale "win" pildi
            Image win = new Image("win/win.png");
            ImageView winIm = new ImageView();
            winIm.setImage(win);
            ImageView tehtud = new ImageView();
            tehtud.setImage(looPuzzle.taisPilt);
            lopuPilt.getChildren().addAll(tehtud, winIm);
            Puzzle.getStage().setScene(loppStseen);
            Puzzle.getFinalStage().setOnCloseRequest(event -> {                 //Uue stage'i sulgemise korral suletakse
                Puzzle.getStage().close();                                      //ka puzzle stage
            });

        }
    }
}
