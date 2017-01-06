import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class kontrollimine {
    Button looUus = new Button("Sisesta oma andmed");
    String duration = new String();
    TextField looKasutaja = new TextField("Lisa oma nimi");
    private StackPane lopuPilt = new StackPane();
    private Scene loppStseen = new Scene(lopuPilt);


    //Konstruktor mis kontrollib, kas puzzle on koos
    public kontrollimine(int juppe, Image taisPilt, GridPane ruudustik, long startTime, ArrayList<ImageView> list, ArrayList<ImageView> kontroll) {
        int asend = 0;
        //Kontrollime kas GridPanel on mõni jupp veel pööramata
        for (int i = 0; i < juppe; i++) {
            if (ruudustik.getChildren().get(i).getRotate() != 0){
                asend = 1;
            }
        }
        //Võrdleme esialgselt loodud listi kontrolliks loodud listiga kui kõik pildid on õigesse asendisse pööratud ja
        //määrame ära mis siis juhtub
        if (list.equals(kontroll) && asend == 0){
        for (int i = 0; i < juppe; i++) {                             //Kontrollime kas mõni jupp on veel
                if (ruudustik.getChildren().get(i).getOpacity() != 1){//peale lõppu jäänud "aktiivseks" ja
                    ruudustik.getChildren().get(i).setOpacity(1);     //muudame mitteaktiivseks
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
            duration = new SimpleDateFormat("mm:ss").format(new Date(System.currentTimeMillis() - startTime));
            Label lopp = new Label("Puzzle on koos!");
            Label lopuaeg = new Label(duration);
            ala.getChildren().addAll(lopp, lopuaeg, looKasutaja, looUus);
            new skoor(juppe, looKasutaja, looUus, duration);            //Käivitame skoori leidmise

            //Lisame puzzle lahendamise korral puzzle pildi peale "win" pildi
            Image win = new Image("win/win.png");
            ImageView winIm = new ImageView();
            winIm.setImage(win);
            ImageView tehtud = new ImageView();
            tehtud.setImage(taisPilt);
            lopuPilt.getChildren().addAll(tehtud, winIm);
            Puzzle.getStage().setScene(loppStseen);
            Puzzle.getFinalStage().setOnCloseRequest(event -> {         //Uue stage'i sulgemise korral suletakse
                Puzzle.getStage().close();                              //ka puzzle stage
            });

        }
    }
}
