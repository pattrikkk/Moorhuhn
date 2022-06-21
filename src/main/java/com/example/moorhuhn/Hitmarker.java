package com.example.moorhuhn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Hitmarker extends ImageView {
    public Hitmarker(double x, double y) {
        super(new Image("hitmarker.png"));
        setFitWidth(30);
        setFitHeight(30);
        setX(x-getFitWidth()/2);
        setY(y-getFitHeight()/2);

        Timeline t = new Timeline(new KeyFrame(Duration.millis(500), e -> removeMarker())); // po 500 milisekundach sa hitmarker deletne
        t.play();
    }

    public void removeMarker() {
        Group parent = (Group) getParent();
        parent.getChildren().remove(this);
    }
}
