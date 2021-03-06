import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;

public class klikk1 {
    double keeratud;
    private ImageView pilt;
    public klikk1(int juppe, Image taispilt, GridPane ruudustik, long startTime, ArrayList<ImageView> list, ArrayList<ImageView> kontroll, Stage puzzleAla) {
        kusHiir(juppe, ruudustik);

        //Tekitame hiirekliki eventi mängualal
        ruudustik.setOnMouseClicked(event -> {
            pilt = (ImageView)(event.getTarget());        //Küsime millisele jupile on klikitud
            keeratud = pilt.getRotate();        //Küsime mitu kraadi klikitud juppi on pööratud
            if(keeratud == 360){                //Määrame ära, et 360 kraadi on sama mis 0 kraadi
                keeratud = 0;
            }
            //Määrame ära mis juhtub hiire parempoolse kliki korral
            if ( event.getButton().equals(MouseButton.SECONDARY)){
                paremKlikk(juppe, taispilt, ruudustik, startTime, list, kontroll, puzzleAla);
            }
            //Määrame ära mis juhtub hiire vasakpoolse kliki korral
            else if(event.getButton().equals(MouseButton.PRIMARY)){
                int X1 = ruudustik.getColumnIndex(pilt);  //Küsime klikitud pildi veeru ja rea numbreid
                int Y1 = ruudustik.getRowIndex(pilt);
                pilt.setOpacity(0.5);                     //Teeme klikitud pildi poolenisti läbipaistvaks
                //Määrame ära mis juhtub kui üks klikk on  aktiivne ja klikitakse teist korda
                ruudustik.setOnMouseClicked(event1 -> {
                    if(event1.getButton().equals(MouseButton.PRIMARY)){
                        ImageView pilt2 = (ImageView)(event1.getTarget());
                        int X2 = ruudustik.getColumnIndex(pilt2); //Küsime teisena klikitud pildi rea ja veeru
                        int Y2 = ruudustik.getRowIndex(pilt2);
                        if(pilt2.getOpacity() != 1){              //Kontrollime kas klikiti uuesti sama pilti
                            pilt2.setOpacity(1);                  //või uut pilti, kui sama siis muudame pildi mitteaktiivseks ja alustame uuesti
                            new klikk1(juppe, taispilt, ruudustik, startTime, list, kontroll, puzzleAla);
                        }
                        //Kui klikiti uut pilti, siis vahetame nii kontrolliks loodud arraylistis kui ka GridPane'l
                        //piltide asukohad vastavalt varem saadud ridade ja veergude numbritele
                        else{
                            int asukoht1 = kontroll.indexOf(pilt);//Küsime kontrolllistis piltide asukohti
                            int asukoht2 = kontroll.indexOf(pilt2);
                            kontroll.set(asukoht2, pilt);         //Vahetame kontrolllistis piltide asukohad
                            kontroll.set(asukoht1, pilt2);
                            ruudustik.getChildren().remove(pilt); //Vahetame GridPane'l piltide asukohad
                            ruudustik.getChildren().remove(pilt2);
                            ruudustik.add(pilt2, X1, Y1);
                            ruudustik.add(pilt, X2, Y2);
                            pilt.setOpacity(1);                             //Muudab pildi "mitteaktiivseks" ehk läbipaistmatuks
                            new kontrollimine(juppe, taispilt, ruudustik, startTime, list, kontroll, puzzleAla); //Kontrollime kas puzzle on koos
                            new klikk1(juppe, taispilt, ruudustik, startTime, list, kontroll, puzzleAla);        //Kui pole koos siis ootame uut klikki
                        }
                    }
                    //Kui esimene klikk on tehtud siis määrame ära mis juhtub kui pilt on tehtud aktiivseks aga vajutatakse
                    //parempoolset ehk pööramiseks mõeldud hiire nuppu
                    else if ( event1.getButton().equals(MouseButton.SECONDARY)){
                        if(!(event1.getTarget().equals(pilt))){
                            pilt.setOpacity(1);
                            new klikk1(juppe, taispilt, ruudustik, startTime, list, kontroll, puzzleAla);
                        }
                        else{
                            paremKlikk(juppe, taispilt, ruudustik, startTime, list, kontroll, puzzleAla);
                        }
                    }
                });
            }
        });
    }

    //Meetod millega määrame ära mis juhtub hiire parempoolse kliki korral
    private void paremKlikk(int juppe, Image taispilt, GridPane ruudustik, long startTime, ArrayList<ImageView> list, ArrayList<ImageView> kontroll, Stage puzzleAla){
        pilt.setRotate(keeratud + 90);                                    //Pöörab klikitud juppi 90 kraadi võrra
        keeratud = pilt.getRotate();
        if(keeratud == 360){                                              //Määrame taaskord ära, et 360 on sama mis 0 kraadi
            keeratud = 0;                                                 //mis on vajalik hilisemas kontrollis
            pilt.setRotate(keeratud);                                     //Määrame 360 kraadi asemele 0 kraadi
        }
        new kontrollimine(juppe, taispilt, ruudustik, startTime, list, kontroll, puzzleAla);   //Käivitame kontrolli, kas puzzle on koos
    }


    //Meetod mis näitab muudab kasutajale jupi värvi kus ta hiir parajasti asub
    private void kusHiir(int juppe, GridPane ruudustik){
        SepiaTone sepiaTone = new SepiaTone();
        sepiaTone.setLevel(0.7);
        for (int j = 0; j < juppe; j++) {
            ruudustik.getChildren().get(j).setOnMouseEntered(event -> {
                ImageView mouseOverPilt = (ImageView)(event.getTarget());
                for (int i = 0; i < juppe ; i++) {
                    if (ruudustik.getChildren().get(i).equals(mouseOverPilt)){
                        ruudustik.getChildren().get(i).setEffect(sepiaTone);
                    }
                    else {
                        ruudustik.getChildren().get(i).setEffect(null);
                    }
                }
            });
        }
    }
}
