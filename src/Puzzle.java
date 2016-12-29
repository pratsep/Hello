import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Puzzle extends Application {
    long startTime;
    double keeratud;
    GridPane ruudustik = new GridPane();
    //String ID1;
    //String ID2;
    int lvl1Pildid = new File("src/images").listFiles().length;
    int juppe;
    ArrayList<ImageView> list = new ArrayList();
    ArrayList<ImageView> kontroll = new ArrayList();
    @Override
    public void start(Stage primaryStage) throws Exception {
        algus();
    }
    private void algus() {
        Stage alustame = new Stage();
        VBox algusala = new VBox();
        Scene algusstseen = new Scene(algusala, 500, 500);
        alustame.setScene(algusstseen);
        alustame.show();
        Button start = new Button("Mängime");
        start.setLayoutX(2000);
        ToggleGroup g = new ToggleGroup();
        RadioButton lihtne = new RadioButton("(2x2)");
        lihtne.setUserData(4);
        RadioButton keskmine = new RadioButton("(3x3)");
        keskmine.setUserData(9);
        RadioButton raske = new RadioButton("(4x4");
        raske.setUserData(16);
        lihtne.setToggleGroup(g);
        keskmine.setToggleGroup(g);
        raske.setToggleGroup(g);
        lihtne.setSelected(true);
        Label raskusastmed = new Label("Raskusastmed");
        algusala.getChildren().addAll(start, raskusastmed, lihtne, keskmine, raske);
        start.setOnAction(event -> {
            juppe =  (int)(g.getSelectedToggle().getUserData());
            System.out.println(juppe);
            looPuzzle(juppe);
            klikk1();
            kontrollimine();
            alustame.close();
        }
        );
    }

    private void kontrollimine() {
        int asend = 0;
        for (int i = 0; i < juppe; i++) {
            if (ruudustik.getChildren().get(i).getRotate() != 0){
                asend = 1;
            }
        }
        if (list.equals(kontroll) && asend == 0){
            for (int i = 0; i < juppe; i++) {
                if (ruudustik.getChildren().get(i).getOpacity() != 1){
                    ruudustik.getChildren().get(i).setOpacity(1);
                }
            }
            Stage voitnud = new Stage();
            VBox ala = new VBox();
            Scene lopuStseen = new Scene(ala, 50, 100);
            voitnud.setScene(lopuStseen);
            voitnud.show();
            voitnud.setTitle("Mäng läbi");
            //Aja võtmine
            String duration = new SimpleDateFormat("mm:ss").format(new Date(System.currentTimeMillis() - startTime));
            Label lopp = new Label("Puzzle on koos!");
            Label lopuaeg = new Label(duration);
            ala.getChildren().addAll(lopp, lopuaeg);
        }
    }
    private void klikk1() {
        ruudustik.setOnMouseClicked(event -> {
            ImageView pilt = (ImageView)(event.getTarget());
            keeratud = pilt.getRotate();
            if(keeratud == 360){
                keeratud = 0;
            }

            if ( event.getButton().equals(MouseButton.SECONDARY)){
                //ID1 = pilt.getId();
                pilt.setRotate(keeratud + 90);
                keeratud = pilt.getRotate();
                if(keeratud == 360){
                    keeratud = 0;
                    pilt.setRotate(keeratud);
                }
                //System.out.println("Pilti on keeratud " + keeratud + " kraadi võrra.");
                kontrollimine();
            }
            else if(event.getButton().equals(MouseButton.PRIMARY)){
                //ID1 = pilt.getId();
                int X1 = ruudustik.getColumnIndex(pilt);
                int Y1 = ruudustik.getRowIndex(pilt);
                //System.out.println("Pildi asukoht on(x/y): " + X1 + "/" + Y1);
                //System.out.println("Pilti on keeratud " + keeratud + " kraadi võrra.");
                pilt.setOpacity(0.5);
                ruudustik.setOnMouseClicked(event1 -> {
                    if(event1.getButton().equals(MouseButton.PRIMARY)){
                        ImageView pilt2 = (ImageView)(event1.getTarget());
                        //ID2 = pilt2.getId();
                        int X2 = ruudustik.getColumnIndex(pilt2);
                        int Y2 = ruudustik.getRowIndex(pilt2);
                        if(pilt2.getOpacity() != 1){
                            pilt2.setOpacity(1);
                            klikk1();
                        }
                        else{
                            //System.out.println("Teise pildi ID on: " + ID2);
                            //System.out.println("Teise pildi asukoht on(x/y): " + X2 + "/" + Y2);
                            int asukoht1 = kontroll.indexOf(pilt);
                            int asukoht2 = kontroll.indexOf(pilt2);
                            kontroll.set(asukoht2, pilt);
                            kontroll.set(asukoht1, pilt2);
                            ruudustik.getChildren().remove(pilt);
                            ruudustik.getChildren().remove(pilt2);
                            ruudustik.add(pilt2, X1, Y1);
                            ruudustik.add(pilt, X2, Y2);
                            pilt.setOpacity(1);
                            kontrollimine();
                            klikk1();
                        }
                    }
                    else if ( event1.getButton().equals(MouseButton.SECONDARY)){
                        if(!(event1.getTarget().equals(pilt))){
                            pilt.setOpacity(1);
                            klikk1();
                        }
                        else{
                            pilt.setRotate(keeratud + 90);
                            keeratud = pilt.getRotate();
                            if(keeratud == 360){
                                keeratud = 0;
                                pilt.setRotate(keeratud);
                            }
                            //System.out.println("Pilti on keeratud " + keeratud + " kraadi võrra.");
                            kontrollimine();
                        }
                    }
                });
            }
        });
    }

    private void looPuzzle(int juppe) {
        Scene x3 = new Scene(ruudustik, 600, 600);
        Stage puzzleAla = new Stage();
        puzzleAla.setScene(x3);
        puzzleAla.show();
        puzzleAla.setTitle("Puzzle");
        //Aja algus
        startTime = System.currentTimeMillis();
        //Random pildi valimine
        int rngPilt = (int)(Math.random()* lvl1Pildid)+1;

        //Pildi lõikamine ja lisamine ArrayListi
        int IDlugeja = 1;
        Double kordaja=0.0;
        if (juppe == 4){
            kordaja = 1.5;
        }
        else if (juppe == 9){
            kordaja = 1.0;
        }
        else if (juppe == 16){
            kordaja = 0.75;
        }
        for (int i = 0; i < Math.sqrt(juppe); i++) {
            for (int j = 0; j < Math.sqrt(juppe) ; j++) {
                Image origPilt = new Image("images/" + rngPilt + ".png");
                ImageView imv = new ImageView();
                PixelReader piltSisse = origPilt.getPixelReader();
                WritableImage uusPilt = new WritableImage(piltSisse, (int)(j*200*kordaja), (int)(i*200*kordaja), (int)(200*kordaja), (int)(200*kordaja));
                imv.setImage(uusPilt);
                imv.setId(String.valueOf(IDlugeja));
                list.add(imv);
                IDlugeja = IDlugeja + 1;
            }
        }
        //Piltide lisamine GridPane'le
        ArrayList<Integer> random = new ArrayList<>();
        for (int i = 0; i < juppe; i++) {
            random.add(i);
        }
        ArrayList<Integer> sassis = new ArrayList<>();
        while (random.size() != 0) {
            int rng1 = (int) (Math.random()*random.size());
            int rng =(int) (Math.random()*sassis.size());
            sassis.add(rng, random.get(rng1));
            random.remove(rng1);
        }
        int z = 0;
        for (int j = 0; j < Math.sqrt(juppe); j++) {
            for (int k = 0; k < Math.sqrt(juppe) ; k++) {
                ImageView pilt = list.get(sassis.get(z));
                ruudustik.add(pilt, k, j);
                kontroll.add(z, pilt);
                double kraad = ((int)(Math.random()*4))*90;
                pilt.setRotate(kraad);
                z = z+1;

            }
        }
        System.out.println(ruudustik.getChildren());
    }
}