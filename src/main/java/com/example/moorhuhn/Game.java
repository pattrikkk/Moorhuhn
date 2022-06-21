package com.example.moorhuhn;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.File;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Group {
    ImageView background;
    Timeline spawnTimeline;
    Timeline cooldownTimeline;
    Random rand;
    double w, h;
    double sliepkaW = 100;
    double sliepkaH = 100;
    double crosshairW = 50;
    double crosshairH = 50;
    boolean cd = false;
    AudioClip hit;
    AudioClip shoot;
    AudioClip backgroundMusic;

    int score;
    int lives;
    int diff;
    Label scr;
    Label lvs;
    VBox vbo;
    Menu m;

    public Game(double w, double h, int diff, Menu m) {
        Image bg = new Image("background.jpg", w, h, false, false);
        background = new ImageView(bg);
        Crosshair crosshair = new Crosshair(crosshairW, crosshairH, w/2 - crosshairW/2, h/2 - crosshairH/2);

        this.score = 0;
        this.lives = 3;

        scr = new Label("Score: " + score);
        lvs = new Label("Lives: " + lives);
        scr.setTextFill(javafx.scene.paint.Color.BLACK);
        scr.setStyle("-fx-font-size: 20px;");
        lvs.setTextFill(javafx.scene.paint.Color.BLACK);
        lvs.setStyle("-fx-font-size: 20px;");
        vbo = new VBox();

        vbo.getChildren().addAll(scr, lvs);
        getChildren().addAll(background, crosshair, vbo);

        this.w = w;
        this.h = h;
        this.rand = new Random();
        this.diff = diff;
        this.m = m;

        int duration = 1000 - diff*200; //(vychytavka)
        spawnTimeline = new Timeline(new KeyFrame(Duration.millis(duration), e -> spawnSliepka()));
        spawnTimeline.setCycleCount(Timeline.INDEFINITE);
        spawnTimeline.play();

        cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(0.3), e -> cd = false));

        hit = new AudioClip(getClass().getResource("/hit.mp3").toExternalForm());
        shoot = new AudioClip(getClass().getResource("/shoot.wav").toExternalForm());
        backgroundMusic = new AudioClip(getClass().getResource("/background.wav").toExternalForm());
        backgroundMusic.setCycleCount(Timeline.INDEFINITE);

        hit.setVolume(0.7);
        shoot.setVolume(0.2);
        backgroundMusic.setVolume(0.05);
        backgroundMusic.play();

        setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                crosshair.setX(event.getX() - crosshairW/2);
                crosshair.setY(event.getY() - crosshairH/2);
                crosshair.toFront();
            }
        });

        setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                crosshair.setX(event.getX() - crosshairW/2);
                crosshair.setY(event.getY() - crosshairH/2);
                crosshair.toFront();
            }
        });

        setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (cd) return;

                cd = true;
                cooldownTimeline.play();
                shoot.play();

                for (int i = 0; i < getChildren().size(); i++) {
                    if (getChildren().get(i) instanceof Sliepka) {
                        Sliepka sliepka = (Sliepka) getChildren().get(i);
                        if (sliepka.getBoundsInParent().contains(event.getX(), event.getY())) {
                            sliepka.deleteSliepka();

                            Hitmarker hitmarker = new Hitmarker(event.getX(), event.getY()); //zasah sliepky (vychytavka)
                            getChildren().add(hitmarker);

                            hit.play();

                            score++; //pocitanie skore
                            scr.setText("Score: " + score);
                        }
                    }
                }
            }
        });
    }

    public void loseLife() {
        lives--;
        lvs.setText("Lives: " + lives);
        if (lives == 0) { //ak ma 0 zivotov, tak sa konci hra (vychytavka)
            backgroundMusic.stop();
            m.gameOver(score);
        }
    }

    public void spawnSliepka() {
        int smer = rand.nextInt(2);
        int speed = rand.nextInt(3) + 3 + diff; //vacsia difficulty, rychlejsie sa pohybuju sliepky (vychytavka)
        double x = smer == 1 ? -sliepkaW : w;
        double y = rand.nextDouble(h - sliepkaH);

        Sliepka sliepka = new Sliepka(sliepkaW, sliepkaH, x, y, speed, smer, this.w, this.h, this);
        getChildren().add(sliepka);
    }
}
