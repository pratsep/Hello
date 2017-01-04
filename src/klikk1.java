import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class klikk1 {
    public klikk1() {
        //Tekitame hiirekliki eventi mängualal
        looPuzzle.ruudustik.setOnMouseClicked(event -> {
            ImageView pilt = (ImageView)(event.getTarget());        //Küsime millisele jupile on klikitud
            looPuzzle.keeratud = pilt.getRotate();                  //Küsime mitu kraadi klikitud juppi on pööratud
            if(looPuzzle.keeratud == 360){                          //Määrame ära, et 360 kraadi on sama mis 0 kraadi
                looPuzzle.keeratud = 0;
            }
            //Määrame ära mis juhtub hiire parempoolse kliki korral
            if ( event.getButton().equals(MouseButton.SECONDARY)){
                pilt.setRotate(looPuzzle.keeratud + 90);            //Pöörab klikitud juppi 90 kraadi võrra
                looPuzzle.keeratud = pilt.getRotate();
                if(looPuzzle.keeratud == 360){                      //Määrame taaskord ära, et 360 on sama mis 0 kraadi
                    looPuzzle.keeratud = 0;                         //mis on vajalik hilisemas kontrollis
                    pilt.setRotate(looPuzzle.keeratud);
                }
                new kontrollimine();                                //Käivitame kontrolli, kas puzzle on koos
            }
            //Määrame ära mis juhtub hiire vasakpoolse kliki korral
            else if(event.getButton().equals(MouseButton.PRIMARY)){
                int X1 = looPuzzle.ruudustik.getColumnIndex(pilt);  //Küsime klikitud pildi veeru ja rea numbreid
                int Y1 = looPuzzle.ruudustik.getRowIndex(pilt);
                pilt.setOpacity(0.5);                               //Teeme klikitud pildi poolenisti läbipaistvaks
                //Määrame ära mis juhtub kui üks klikk on  aktiivne ja klikitakse teist korda
                looPuzzle.ruudustik.setOnMouseClicked(event1 -> {
                    if(event1.getButton().equals(MouseButton.PRIMARY)){
                        ImageView pilt2 = (ImageView)(event1.getTarget());
                        int X2 = looPuzzle.ruudustik.getColumnIndex(pilt2); //Küsime teisena klikitud pildi rea ja veeru
                        int Y2 = looPuzzle.ruudustik.getRowIndex(pilt2);
                        if(pilt2.getOpacity() != 1){                        //Kontrollime kas klikiti uuesti sama pilti
                            pilt2.setOpacity(1);                            //või uut pilti, kui sama siis muudame
                            new klikk1();                                   //pildi mitteaktiivseks ja alustame uuesti
                        }
                        //Kui klikiti uut pilti, siis vahetame nii kontrolliks loodud arraylistis kui ka GridPane'l
                        //piltide asukohad vastavalt varem saadud ridade ja veergude numbritele
                        else{
                            int asukoht1 = looPuzzle.kontroll.indexOf(pilt);//Küsime kontrolllistis piltide asukohti
                            int asukoht2 = looPuzzle.kontroll.indexOf(pilt2);
                            looPuzzle.kontroll.set(asukoht2, pilt);         //Vahetame kontrolllistis piltide asukohad
                            looPuzzle.kontroll.set(asukoht1, pilt2);
                            looPuzzle.ruudustik.getChildren().remove(pilt); //Vahetame GridPane'l piltide asukohad
                            looPuzzle.ruudustik.getChildren().remove(pilt2);
                            looPuzzle.ruudustik.add(pilt2, X1, Y1);
                            looPuzzle.ruudustik.add(pilt, X2, Y2);
                            pilt.setOpacity(1);                             //Muudab pildi "mitteaktiivseks" ehk läbipaistmatuks
                            new kontrollimine();                            //Kontrollime kas puzzle on koos
                            new klikk1();                                   //Kui pole koos siis ootame uut klikki
                        }
                    }
                    //Kui esimene klikk on tehtud siis määrame ära mis juhtub kui pilt on tehtud aktiivseks aga vajutatakse
                    //parempoolset ehk pööramiseks mõeldud hiire nuppu
                    else if ( event1.getButton().equals(MouseButton.SECONDARY)){
                        if(!(event1.getTarget().equals(pilt))){
                            pilt.setOpacity(1);
                            new klikk1();
                        }
                        else{
                            pilt.setRotate(looPuzzle.keeratud + 90);
                            looPuzzle.keeratud = pilt.getRotate();
                            if(looPuzzle.keeratud == 360){
                                looPuzzle.keeratud = 0;
                                pilt.setRotate(looPuzzle.keeratud);
                            }
                            new kontrollimine();
                        }
                    }
                });
            }
        });
    }
}
