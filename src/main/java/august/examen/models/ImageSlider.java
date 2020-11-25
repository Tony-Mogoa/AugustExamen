package august.examen.models;


import javafx.scene.control.Label;

public class ImageSlider {
    private int currentImage = 0;
    private Label txtCurrentImage;

    public ImageSlider(Label txtCurrentImage, int imgCount){
        this.txtCurrentImage = txtCurrentImage;
        if(imgCount == 0)
            txtCurrentImage.setText("");
        else{
            updateSlider();
        }
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(int currentImage) {
        this.currentImage = currentImage;
        updateSlider();
    }

    public void increment(){
        currentImage++;
        updateSlider();
    }

    public void decrement(){
        currentImage--;
        updateSlider();
    }

    public void updateSlider(){
        txtCurrentImage.setText(numberPostfix(currentImage + 1));
    }

    public String numberPostfix(int imageNumber){
        switch (imageNumber){
            case 1:
                return imageNumber + "st of";
            case 2:
                return imageNumber + "nd of";
            case 3:
                return imageNumber + "rd of";
            default:
                return imageNumber + "th of";
        }
    }
}
