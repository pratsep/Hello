import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class skoor {
    String kasutajaAndmed;
    File f;
    ArrayList<String> andmebaasist = new ArrayList<>();
    public skoor(int tekstiFailist) {
        f = new File("src/score/" + tekstiFailist + ".txt");
        if (!(f.exists())) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Kasutaja sisestab andmed
        kontrollimine.looKasutaja.setOnMouseClicked(event -> {
            kontrollimine.looKasutaja.clear();
        });
        kontrollimine.looUus.setOnAction(event1 -> {
            if (kontrollimine.looKasutaja.getText().contains(" ")){
                kontrollimine.looKasutaja.setText("Tühikut ei tohi olla");
                return;
            }
            if (kontrollimine.looKasutaja.getText().equals("")){
                kontrollimine.looKasutaja.setText("Tühja välja ei tohi jätta");
                return;
            }
            if (!(kontrollimine.looKasutaja.getText().equals(""))) {
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)))) {
                    out.println(kontrollimine.looKasutaja.getText() + " " + kontrollimine.duration);
                    out.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
                //Loo skooritabel
                VBox skooriTabel = new VBox();
                Scene highScore = new Scene(skooriTabel, 500, 500);
                try {
                    BufferedReader andmed = new BufferedReader(new FileReader(f));
                    kasutajaAndmed = andmed.readLine();
                    while (kasutajaAndmed != null) {
                        for (int i = 0; i < 2; i++) {
                            String[] logTime = kasutajaAndmed.split(" ");

                            andmebaasist.add(logTime[i]);

                        }
                        kasutajaAndmed = andmed.readLine();
                    }
                    //Järjestab tabeli aja järgi
                    System.out.println(andmebaasist);
                    int abiks = 1;
                    for (int i = 1; i < andmebaasist.size()+1; i=i+2) {

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