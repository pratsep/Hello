import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.*;
import java.util.ArrayList;

public class skoor {
    String kasutajaAndmed;
    File f;
    ArrayList<String> andmebaasist = new ArrayList<>();

    //Konstruktor skoori kirja panemiseks, skooritabeli loomiseks ja aegade järgi järjestamiseks
    public skoor(int tekstiFailist) {                                       //Juppide järgi valitakse millisesse
        f = new File("src/score/" + tekstiFailist + ".txt");                //faili skoor kirja läheb
        //Kontrollime kas fail on olemas, kui pole, siis loome uue tekstifaili
        if (!(f.exists())) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Kasutaja vajutab tekstiväljale, siis tektsiväli tühjendatakse
        kontrollimine.looKasutaja.setOnMouseClicked(event -> {
            kontrollimine.looKasutaja.clear();
        });
        //Erinevad kontrollid, et poleks tühikut, väli poleks tühi
        kontrollimine.looUus.setOnAction(event1 -> {
            if (kontrollimine.looKasutaja.getText().contains(" ")){
                kontrollimine.looKasutaja.setText("Tühikut ei tohi olla");
                return;
            }
            if (kontrollimine.looKasutaja.getText().equals("")){
                kontrollimine.looKasutaja.setText("Tühja välja ei tohi jätta");
                return;
            }
            //Kui eelnevad kontrollid on läbitud, siis kirjutatakse faili kasutaja nimi ja aeg
            if (!(kontrollimine.looKasutaja.getText().equals(""))) {
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)))) {
                    out.println(kontrollimine.looKasutaja.getText() + " " + kontrollimine.duration);
                    out.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
                //Loob skooritabeli
                VBox skooriTabel = new VBox();
                Scene highScore = new Scene(skooriTabel, 500, 500);
                try {
                    BufferedReader andmed = new BufferedReader(new FileReader(f));
                    kasutajaAndmed = andmed.readLine();
                    //Loeb failist kõik andmed ridade kaupa ja lisab nad massiivi
                    while (kasutajaAndmed != null) {
                        for (int i = 0; i < 2; i++) {
                            String[] logTime = kasutajaAndmed.split(" ");   //Eraldab nime ja aja tühiku järgi
                            andmebaasist.add(logTime[i]);                   //Lisab nimed ja ajad arraylisti
                        }
                        kasutajaAndmed = andmed.readLine();
                    }
                    //Järjestab tabeli aja järgi
                    System.out.println(andmebaasist);
                    int abiks = 1;
                    for (int i = 1; i < andmebaasist.size()+1; i=i+2) {
                        //Kontroll, et tsükkel ei väljuks arraylisti piiridest
                        if (i+2 > andmebaasist.size()){
                            break;
                        }
                        String aeg1 = andmebaasist.get(i);
                        String nimi1 = andmebaasist.get(i-1);
                        String aeg2 = andmebaasist.get(i+2);
                        String nimi2 = andmebaasist.get(i+1);
                        String[] ajad1 = aeg1.split(":");
                        String[] ajad2 = aeg2.split(":");
                        int sec1 = (Integer.parseInt(ajad1[0])*60) + Integer.parseInt(ajad1[1]);
                        int sec2 = (Integer.parseInt(ajad2[0])*60) + Integer.parseInt(ajad2[1]);
                        if(sec1 > sec2){
                            abiks = abiks + 1;
                            andmebaasist.set(i, aeg2);
                            andmebaasist.set(i+2, aeg1);
                            andmebaasist.set(i-1, nimi2);
                            andmebaasist.set(i+1, nimi1);
                            abiks = 1;
                            i = -1;
                        }
                    }
                    //Tsükkel andmete lisamiseks VBoxi
                    int z=1;
                    for (int i = 0; i < andmebaasist.size() && i < 20; i=i+2) {
                        Label rida1 = new Label(z+".\t" + andmebaasist.get(i) + "   " + andmebaasist.get(i+1) );
                        skooriTabel.getChildren().add(rida1);
                        z=z+1;
                    }
                    andmed.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
                Puzzle.getFinalStage().setScene(highScore);
                Puzzle.getFinalStage().setTitle("High Score!");
                Puzzle.getStage().close();
            }
        });
    }


}