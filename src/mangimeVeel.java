import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class mangimeVeel {
    //Tekitab nupu millega saab uuesti mängida ja nupu millega saab mängust välja
    public mangimeVeel(GridPane skoorTabel, Stage voitnud){
        Button uuesti = new Button("Mängime uuesti");
        uuesti.setPrefWidth(150);
        Button eiUuesti = new Button("Välju");
        eiUuesti.setPrefWidth(150);
        skoorTabel.add(uuesti, 1, 11);
        skoorTabel.add(eiUuesti, 1, 12);
        teemeVeel(voitnud, uuesti);
        rohkemEiTee(eiUuesti);
    }
    private void teemeVeel(Stage voitnud, Button uuesti){
        uuesti.setOnAction(event -> {
            new algus();
            voitnud.close();
        });
    }
    private void rohkemEiTee(Button eiUuesti){
        eiUuesti.setOnAction(event -> System.exit(0));
    }
}
