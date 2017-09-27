package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import enemy.Enemy;
import gui.Main;
import gui.Settings;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;
import player.Input;
import player.Player;
import player.SpriteBase;

public class Controller {
	int life;
	
    Image playerImage;
    Image enemyImage;

    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

    Text collisionText = new Text();
    boolean collision = false;

    Random rnd = new Random();

	public Controller(Main main){
        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                // player input
                players.forEach(sprite -> sprite.processInput());

                // add random enemies
                spawnEnemies( true, main);

                // movement
                players.forEach(sprite -> sprite.move());
                enemies.forEach(sprite -> sprite.move());

                // check collisions
                checkCollisions();

                // update sprites in scene
                players.forEach(sprite -> sprite.updateUI());
                enemies.forEach(sprite -> sprite.updateUI());

                // check if sprite can be removed
                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites( enemies);

                // update score, health, etc
                updateScore();
            }

        };
        gameLoop.start();

        loadGame();

        createScoreLayer(main);
        createPlayers(main);


	}
	
    private void loadGame() {
        playerImage = new Image( getClass().getResource("../gui/player1.png").toExternalForm());
        enemyImage = new Image( getClass().getResource("../gui/enemy1.png").toExternalForm());
    }

    private void createScoreLayer(Main main) {


        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        main.scoreLayer.getChildren().add(collisionText);

        // TODO: quick-hack to ensure the text is centered; usually you don't have that; instead you have a health bar on top
        collisionText.setText("Collision");
        double x = (Settings.SCENE_WIDTH - collisionText.getBoundsInLocal().getWidth()) / 2;
        double y = (Settings.SCENE_HEIGHT - collisionText.getBoundsInLocal().getHeight()) / 2;
        collisionText.relocate(x, y);
        collisionText.setText("");

        collisionText.setBoundsType(TextBoundsType.VISUAL);


    }
    private void createPlayers(Main main) {

        // player input
        Input input = new Input(main.scene);

        // register input listeners
        input.addListeners(); // TODO: remove listeners on game over

        Image image = playerImage;

        // center horizontally, position at 70% vertically
        double x = (Settings.SCENE_WIDTH - image.getWidth()) / 2.0;
        double y = Settings.SCENE_HEIGHT * 0.7;

        // create player
        Player player = new Player(main.playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, input);

        // register player
        players.add( player);

    }

    private void spawnEnemies(boolean random,Main main) {

        if(random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = enemyImage;

        // random speed
        double speed = rnd.nextDouble() * 1.0 + 2.0;

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = rnd.nextDouble() * (Settings.SCENE_WIDTH - image.getWidth());
        double y = -image.getHeight();

        // create a sprite
        Enemy enemy = new Enemy(main.playfieldLayer, image, x, y, 0, 0, speed, 0, 1,1);

        // manage sprite
        enemies.add( enemy);

    }

    private void removeSprites(  List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        while( iter.hasNext()) {
            SpriteBase sprite = iter.next();

            if( sprite.isRemovable()) {

                // remove from layer
                sprite.removeFromLayer();

                // remove from list
                iter.remove();
            }
        }
    }

    private void checkCollisions() {

        collision = false;

        for( Player player: players) {
            for( Enemy enemy: enemies) {
                if( player.collidesWith(enemy)) {
                    collision = true;
                }
            }
        }
    }

    private void updateScore() {
        if( collision) {
            collisionText.setText("Collision");
        } else {
            collisionText.setText("");
        }
    }

	
	
	public void nextFrame(){
		
	}
}
