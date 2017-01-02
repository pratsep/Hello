import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;

public class looPuzzle {

    public static Image taisPilt;
    public  static double keeratud;
    public static GridPane ruudustik = new GridPane();
    public static long startTime;
    int lvl1Pildid = new File("src/images").listFiles().length;
    public static ArrayList<ImageView> list = new ArrayList();
    public static ArrayList<ImageView> kontroll = new ArrayList();
    public looPuzzle(int juppe) {
        Scene x3 = new Scene(ruudustik, 600, 600);
        //Stage puzzleAla = new Stage();
        Puzzle.getStage().setScene(x3);
        Puzzle.getStage().show();
        Puzzle.getStage().setTitle("Puzzle");
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
                taisPilt = origPilt;
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
        System.out.println(ruudustik.getChildren().get(2));
        new klikk1();
    }
}
