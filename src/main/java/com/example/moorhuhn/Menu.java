package com.example.moorhuhn;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class Menu extends Group { //(vychytavka)
    Button start;
    Button difficulty;
    Button exit;
    Label endText;
    VBox vbo;
    int difficultylvl = 0;
    String levels[] = {"Easy", "Medium", "Hard"}; //obtiaznosti od lahkej po tazku
    Game g;
    Scene scene;

    public Menu(Scene scene) {
        start = new Button("Start");
        difficulty = new Button("Difficulty: Easy");
        exit = new Button("Exit");
        endText = new Label("");

        start.setPrefWidth(200);
        difficulty.setPrefWidth(200);
        exit.setPrefWidth(200);

        this.scene = scene;

        vbo = new VBox();
        vbo.getChildren().addAll(start, difficulty, exit, endText);

        getChildren().addAll(vbo);
        vbo.setAlignment(Pos.TOP_CENTER);

        vbo.setLayoutX(scene.getWidth()/2 - start.getPrefWidth()/2);
        vbo.setLayoutY(scene.getHeight()/2 - start.getPrefHeight()/2);

        start.setOnAction(e -> {
            vbo.setVisible(false);
            g = new Game(scene.getWidth(), scene.getHeight(), difficultylvl%3, this);
            getChildren().add(g);
            scene.setCursor(Cursor.NONE);
        });

        difficulty.setOnAction(e -> {
            difficultylvl++;
            difficulty.setText("Difficulty: " + levels[difficultylvl%3]);
        });

        exit.setOnAction(e -> System.exit(0));
    }

    public void gameOver(int score) {
        getChildren().remove(g);
        vbo.setVisible(true);
        scene.setCursor(Cursor.DEFAULT);
        endText.setText("Game Over! Score: " + score);
    }
}
