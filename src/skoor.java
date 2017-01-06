import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class skoor {
    private String kasutajaAndmed;
    private File f;
    private ArrayList<String> andmebaasist = new ArrayList<>();
    Scene highScore;

    //Konstruktor skoori kirja panemiseks, skooritabeli loomiseks ja aegade järgi järjestamiseks
    public skoor(int tekstiFailist, TextField looKasutaja, Button looUus, String duration, Stage puzzleAla, Stage voitnud) {                                       //Juppide järgi valitakse millisesse
        f = new File("src/score/" + tekstiFailist + ".txt");    //faili skoor kirja läheb
        if (!(f.exists())) {                                    //Kontrollime kas fail on olemas, kui pole, siis loome uue tekstifaili
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        looKasutaja.setOnMouseClicked(event -> {                //Kasutaja vajutab tekstiväljale, siis tektsiväli tühjendatakse
            looKasutaja.clear();
        });
        looUus.setOnAction(event1 -> {
            sisestuseKontroll(looKasutaja);
            //Kui eelnevad kontrollid on läbitud, siis kirjutatakse faili kasutaja nimi ja aeg
            if (!(looKasutaja.getText().equals(""))) {
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)))) {
                    out.println(looKasutaja.getText() + " " + duration);
                    out.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
                looSkooritabel();
                voitnud.setScene(highScore);
                voitnud.setTitle("High Score!");
                puzzleAla.close();
            }
        });
    }



    private void sisestuseKontroll(TextField looKasutaja){
        //Erinevad kontrollid, et poleks tühikut, väli poleks tühi
        if (looKasutaja.getText().contains(" ")){
            looKasutaja.setText("Tühikut ei tohi olla");
            return;
        }
        if (looKasutaja.getText().equals("")){
            looKasutaja.setText("Tühja välja ei tohi jätta");
            return;
        }
    }



    private void looSkooritabel(){
        //Loob skooritabeli
        VBox skooriTabel = new VBox();
        highScore = new Scene(skooriTabel, 500, 500);
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
    }

}