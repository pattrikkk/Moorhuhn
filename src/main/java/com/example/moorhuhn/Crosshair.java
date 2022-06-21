package com.example.moorhuhn;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Crosshair extends ImageView {

    public Crosshair(double w, double h, double x, double y) {
        super(new Image("crosshair.png"));
        setFitWidth(w);
        setFitHeight(h);
        setX(x);
        setY(y);
    }
}
