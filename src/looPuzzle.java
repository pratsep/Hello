import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import java.io.File;
import java.util.ArrayList;

public class looPuzzle {

    public static Image taisPilt;
    public static double keeratud;
    public static GridPane ruudustik = new GridPane();
    public static long startTime;
    public static ArrayList<ImageView> list = new ArrayList();
    public static ArrayList<ImageView> kontroll = new ArrayList();

    //Kaustast random pildi valimine
    int pikkusPildid = new File("src/images").listFiles().length;   //Leiab kaustas olevate piltide arvu
    File kaustPildid=new File("src/images");
    File[] listPildid= kaustPildid.listFiles();                     //Loome listi failidest kaustas
    int randomNr = (int)(Math.random()*pikkusPildid);               //Loome vastavalt piltide arvule suvalise arvu
    String nimiPilt = listPildid[randomNr].getName();               //Otsime listist vastavalt genereeritud numbrile pildi nime

    //Konstruktor mis ehitab uue stseeni
    public looPuzzle() {
        Scene x3 = new Scene(ruudustik, 600, 600);
        Puzzle.getStage().setScene(x3);
        Puzzle.getStage().show();
        Puzzle.getStage().setTitle("Puzzle");

    }

    //Objekt mis genereerib pildist puzzle vastavalt juppide arvule
    public void genereeriPuzzle(int juppe){
        //Aja algus
        startTime = System.currentTimeMillis();
        //Tekitame vastavalt juppide arvule kordajad, mille järgi pildist jupid lõigatakse
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

        //Lõikame kaustast valitud pildi raskusastme järgi valitud juppide arvule vastava kordaja järgi
        for (int i = 0; i < Math.sqrt(juppe); i++) {
            for (int j = 0; j < Math.sqrt(juppe) ; j++) {
                Image origPilt = new Image("images/" + nimiPilt, 600, 600, false, false);
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

        //Genereerime arraylisti vastavalt juppide arvule ja siis ajame selle segamini
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

        //Lisame sassi aetud array listi järgi jupid GridPane'le
        int z = 0;
        for (int j = 0; j < Math.sqrt(juppe); j++) {
            for (int k = 0; k < Math.sqrt(juppe) ; k++) {
                ImageView pilt = list.get(sassis.get(z));
                ruudustik.add(pilt, k, j);
                kontroll.add(z, pilt);                          //Kontroll on arraylist mille järgi lõpus puzzle valmis saamist kontrollida
                double kraad = ((int)(Math.random()*4))*90;     //Loome kas 0, 90, 180 või 270 random arvu
                pilt.setRotate(kraad);                          //Kraadi järgi pöörame igat puzzle juppi
                z = z+1;
            }
        }
        new klikk1();                                           //Käivitame konstruktori klikk1
    }
}
