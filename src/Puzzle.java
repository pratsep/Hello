import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    // duration;
    GridPane ruudustik = new GridPane();
    String ID1;
    String ID2;
    int lvl1Pildid = new File("src/images").listFiles().length;
    int juppe = 9;
    ArrayList<ImageView> list = new ArrayList();
    ArrayList<ImageView> kontroll = new ArrayList();
    @Override
    public void start(Stage primaryStage) throws Exception {
        looPuzzle();
        klikk1();
        kontrollimine();
    }

    private void kontrollimine() {
        int asend = 0;
        for (int i = 0; i < juppe; i++) {
            if (ruudustik.getChildren().get(i).getRotate() != 0){
                asend = 1;
            }
        }
        if (list.equals(kontroll) && asend == 0){
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
            double keeratud = pilt.getRotate();
            if(keeratud == 360){
                keeratud = 0;
            }

            if ( event.getButton().equals(MouseButton.SECONDARY)){
                ID1 = pilt.getId();
                pilt.setRotate(keeratud + 90);
                keeratud = pilt.getRotate();
                if(keeratud == 360){
                    keeratud = 0;
                    pilt.setRotate(keeratud);
                }
                System.out.println("Pilti on keeratud " + keeratud + " kraadi võrra.");
                kontrollimine();
            }
            else if(event.getButton().equals(MouseButton.PRIMARY)){
                ID1 = pilt.getId();
                int X1 = ruudustik.getColumnIndex(pilt);
                int Y1 = ruudustik.getRowIndex(pilt);
                System.out.println("Pildi asukoht on(x/y): " + X1 + "/" + Y1);
                System.out.println("Pilti on keeratud " + keeratud + " kraadi võrra.");
                pilt.setOpacity(0.5);
                ruudustik.setOnMouseClicked(event1 -> {
                    if(event1.getButton().equals(MouseButton.PRIMARY)){
                        ImageView pilt2 = (ImageView)(event1.getTarget());
                        ID2 = pilt2.getId();
                        int X2 = ruudustik.getColumnIndex(pilt2);
                        int Y2 = ruudustik.getRowIndex(pilt2);
                        if(pilt2.getOpacity() != 1){
                            pilt2.setOpacity(1);
                            klikk1();
                        }
                        else{
                            System.out.println("Teise pildi ID on: " + ID2);
                            System.out.println("Teise pildi asukoht on(x/y): " + X2 + "/" + Y2);
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
                });
            }
        });
    }
    private void looPuzzle() {
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
        for (int i = 0; i < Math.sqrt(juppe); i++) {
            for (int j = 0; j < Math.sqrt(juppe) ; j++) {
                Image origPilt = new Image("images/" + rngPilt + ".png");
                ImageView imv = new ImageView();
                PixelReader piltSisse = origPilt.getPixelReader();
                WritableImage uusPilt = new WritableImage(piltSisse, j*200, i*200, 200, 200);
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
        for (int i = 0; i < random.size(); i++) {
            int rng =(int) (Math.random()*sassis.size());
            sassis.add(rng, random.get(i));
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
    }




}
