package controller;

import java.util.*;

import animation.Explosion;
import bullet.Bullet;
import enemy.Enemy;
import gui.Main;
import gui.Settings;
import javafx.animation.Animation;
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
	int score;

    Image playerImage;
    Image enemyImage;

    Image background;
    
    List<Player> players = new ArrayList<>();
    List<Enemy> enemies = new ArrayList<>();

    List<Bullet> bullets = new ArrayList<>();
    Iterator<Bullet> b;

    Text collisionText = new Text();
    Text scoreText = new Text();
    Text healthText = new Text();

    boolean collision = false;

    public int frameDuration = 30;
    public int frame;
    Random rnd = new Random();
    boolean reload = false;

    int counterForReload;

    public Timeline timeline;

	public Controller(Main main){
        frame = 0;
                
        timeline = new Timeline();

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(this.frameDuration), (ActionEvent event) -> {
            nextFrame(main);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        loadGame();

        createScoreLayer(main);
        createPlayers(main);


	}

	public void nextFrame(Main main){

        frame++;

        if (frame * this.frameDuration >= 1000) frame  = 0;

        if (counterForReload < 30) counterForReload++;
        else {
            counterForReload = 0;
            reload = false;
        }

        // player input
        players.forEach(sprite -> sprite.processInput());

        // add random enemies
        spawnEnemies( true, main);

        // movement
        players.forEach(sprite -> sprite.move());
        for (Enemy enemy : enemies)
            enemy.move(players.get(0));

        //bullets
        checkFire(main);
        removeBullet(main);

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

    private void loadGame() {
        playerImage = new Image( getClass().getResource("../gui/player1.png").toExternalForm());
        enemyImage = new Image( getClass().getResource("../gui/enemy1.png").toExternalForm());
    }

    private void createScoreLayer(Main main) {
        //Score
        scoreText.setFont( Font.font( null, FontWeight.BOLD, 30));
        scoreText.setStroke(Color.BLACK);
        scoreText.setFill(Color.WHITE);
        scoreText.setText("Score: ");
        scoreText.relocate(20, 20);
        score = 0;

        //Collision mess
        collisionText.setFont( Font.font( null, FontWeight.BOLD, 64));
        collisionText.setStroke(Color.BLACK);
        collisionText.setFill(Color.RED);

        //HP
        healthText.setFont( Font.font( null, FontWeight.BOLD, 30));
        healthText.setStroke(Color.BLACK);
        healthText.setFill(Color.WHITE);
        healthText.setText("Lives: 3");
        healthText.relocate(Settings.SCENE_WIDTH - 140, 20);
        life = 3;

        //Adding texts
        main.scoreLayer.getChildren().add(collisionText);
        main.scoreLayer.getChildren().add(scoreText);
        main.scoreLayer.getChildren().add(healthText);

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
        double x = Settings.SCENE_WIDTH  / 2.0;
        double y = Settings.SCENE_HEIGHT / 2;

        // create player
        Player player = new Player(main.playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, input, Settings.DOWN);

        // register player
        players.add( player);

    }

    //Spawning Coordinate
    private double XPOS(int n){
        switch (n) {
            case Settings.UP:
            case Settings.DOWN:
                return Settings.SCENE_WIDTH / 2;
            case Settings.LEFT:
                return Settings.SCENE_WIDTH;
        }
        return 0;
    }
    private double YPOS(int n){
        switch (n) {
            case Settings.LEFT:
            case Settings.RIGHT:
                return Settings.SCENE_HEIGHT / 2;
            case Settings.UP:
                return Settings.SCENE_HEIGHT;
        }
        return 0;
    }
    private void spawnEnemies(boolean random,Main main) {

        if(random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            return;
        }

        // image
        Image image = enemyImage;

        // random speed
        double speed = 2.0;
        int dir = rnd.nextInt(4);

        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = XPOS(dir);
        double y = YPOS(dir);

        // create a sprite
        Enemy enemy = new Enemy(main.playfieldLayer, image, x, y, 0, speed, speed, 0, 1,1, dir);

        // manage sprite
        enemies.add(enemy);

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

    private void checkFire(Main main) {
        if (players.get(0).fire && !reload)
        {
            Bullet bullet = new Bullet(main.playfieldLayer, players.get(0).getX() + (Settings.SIZE / 2),players.get(0).getY() + (Settings.SIZE / 2),players.get(0).getDir(),new Image(getClass().getResource("../gui/bullet2.png").toExternalForm()));
            bullets.add(bullet);
            reload = true;
            counterForReload = 0;
        }
    }

    public void removeBullet(Main main){
        b = bullets.iterator();
        while (b.hasNext())
        {
            Bullet bullet = b.next();
            if (bullet.isEnd){
                main.playfieldLayer.getChildren().remove(bullet.imageView);
                b.remove();
            }
        }
    }

    private void checkCollisions() {

        collision = false;

        for (Bullet bullet : bullets)
            for (Enemy enemy : enemies){
                if (bullet.hit(enemy)){
                    bullet.isEnd = true;
                    enemy.setRemovable(true);
                    score += rnd.nextInt(5) + 10;
                }
            }


        for( Player player: players) {
            for( Enemy enemy: enemies) {
                if( player.collidesWith(enemy)) {
                    collision = true;
                }
            }
        }

        if (collision)
            for (Enemy enemy : enemies)
                enemy.setRemovable(true);


    }

    private void updateScore() {
        if( collision) {
            collisionText.setText("Collision");
            life -= 1;
        } else {
            collisionText.setText("");
        }

        scoreText.setText("Score: " + score);
        healthText.setText("Lives: " + life);
    }

}