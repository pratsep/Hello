import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class algus {
    int juppe;
    public algus() {
        Stage alustame = new Stage();
        VBox algusala = new VBox();
        Scene algusstseen = new Scene(algusala, 100, 200);
        alustame.setScene(algusstseen);
        alustame.show();
        Button start = new Button("Mängime");
        start.setLayoutX(2000);

        //Loob raskusastmete valikuks radio buttonid
        ToggleGroup g = new ToggleGroup();
        RadioButton lihtne = new RadioButton("(2x2)");
        lihtne.setUserData(4);                              //Anname radio buttonitele lisainfo(juppide arvu)
        lihtne.setToggleGroup(g);
        RadioButton keskmine = new RadioButton("(3x3)");
        keskmine.setUserData(9);
        keskmine.setToggleGroup(g);
        RadioButton raske = new RadioButton("(4x4)");
        raske.setUserData(16);
        raske.setToggleGroup(g);
        lihtne.setSelected(true);

        Label raskusastmed = new Label("Raskusastmed");
        algusala.getChildren().addAll(start, raskusastmed, lihtne, keskmine, raske);
        //Nupu vajutamisel saame vastavalt valitud raskusastmele juppide arvu ja käivitatakse puzzle mis lõigatakse
        //vastavalt valitud raskusastmest saadud juppide arvule valmis
        start.setOnAction(event -> {
                    juppe = (int) (g.getSelectedToggle().getUserData());
                    new looPuzzle().genereeriPuzzle(juppe);  //Käivitab puzzle loomise objekti
                    alustame.close();
                }
        );
    }
}
