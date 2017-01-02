import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

public class klikk1 {
    public klikk1() {
        looPuzzle.ruudustik.setOnMouseClicked(event -> {
            ImageView pilt = (ImageView)(event.getTarget());
            looPuzzle.keeratud = pilt.getRotate();
            if(looPuzzle.keeratud == 360){
                looPuzzle.keeratud = 0;
            }
            if ( event.getButton().equals(MouseButton.SECONDARY)){
                //ID1 = pilt.getId();
                pilt.setRotate(looPuzzle.keeratud + 90);
                looPuzzle.keeratud = pilt.getRotate();
                if(looPuzzle.keeratud == 360){
                    looPuzzle.keeratud = 0;
                    pilt.setRotate(looPuzzle.keeratud);
                }
                //System.out.println("Pilti on keeratud " + keeratud + " kraadi võrra.");
                new kontrollimine();
            }
            else if(event.getButton().equals(MouseButton.PRIMARY)){
                //ID1 = pilt.getId();
                int X1 = looPuzzle.ruudustik.getColumnIndex(pilt);
                int Y1 = looPuzzle.ruudustik.getRowIndex(pilt);
                //System.out.println("Pildi asukoht on(x/y): " + X1 + "/" + Y1);
                //System.out.println("Pilti on keeratud " + keeratud + " kraadi võrra.");
                pilt.setOpacity(0.5);
                looPuzzle.ruudustik.setOnMouseClicked(event1 -> {
                    if(event1.getButton().equals(MouseButton.PRIMARY)){
                        ImageView pilt2 = (ImageView)(event1.getTarget());
                        //ID2 = pilt2.getId();
                        int X2 = looPuzzle.ruudustik.getColumnIndex(pilt2);
                        int Y2 = looPuzzle.ruudustik.getRowIndex(pilt2);
                        if(pilt2.getOpacity() != 1){
                            pilt2.setOpacity(1);
                            new klikk1();
                        }
                        else{
                            //System.out.println("Teise pildi ID on: " + ID2);
                            //System.out.println("Teise pildi asukoht on(x/y): " + X2 + "/" + Y2);
                            int asukoht1 = looPuzzle.kontroll.indexOf(pilt);
                            int asukoht2 = looPuzzle.kontroll.indexOf(pilt2);
                            looPuzzle.kontroll.set(asukoht2, pilt);
                            looPuzzle.kontroll.set(asukoht1, pilt2);
                            looPuzzle.ruudustik.getChildren().remove(pilt);
                            looPuzzle.ruudustik.getChildren().remove(pilt2);
                            looPuzzle.ruudustik.add(pilt2, X1, Y1);
                            looPuzzle.ruudustik.add(pilt, X2, Y2);
                            pilt.setOpacity(1);
                            new kontrollimine();
                            new klikk1();
                        }
                    }
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
                            //System.out.println("Pilti on keeratud " + keeratud + " kraadi võrra.");
                            new kontrollimine();
                        }
                    }
                });
            }
        });
        System.out.println(looPuzzle.ruudustik.getChildren());
    }
}
