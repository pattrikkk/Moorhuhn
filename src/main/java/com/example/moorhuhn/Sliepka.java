package com.example.moorhuhn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sliepka extends ImageView {
    int speed;
    int smer;
    double screenW;
    double screenH;
    double w;
    double h;
    Timeline moveTimeline;
    Game g;
    public Sliepka(double w, double h, double x, double y, int speed, int smer, double screenW, double screenH, Game g) {
        super(new Image("sliepka.gif"));

        setFitWidth(w);
        setFitHeight(h);
        setX(x);
        setY(y);

        this.speed = speed;
        this.smer = smer;
        this.screenW = screenW;
        this.screenH = screenH;
        this.w = w;
        this.h = h;
        this.g = g;

        if (smer == 0)
            setScaleX(-1);

        moveTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> move()));
        moveTimeline.setCycleCount(Timeline.INDEFINITE);
        moveTimeline.play();
    }

    public void move() {
        if (smer == 0)
            setX(getX() - speed);
        if (smer == 1)
            setX(getX() + speed);
        if (getX() > screenW || getX() < -w) { //ak uspesne preleti sliepka tak sa strhne 1 zivot
            deleteSliepka();
            g.loseLife();
        }
    }

    public void deleteSliepka() {
        moveTimeline.stop();
        Group parent = (Group) getParent();
        parent.getChildren().remove(this);
    }
}
