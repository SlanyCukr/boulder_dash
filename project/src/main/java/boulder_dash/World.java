package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.Random;

public class World {
    public boolean isOver;

    private Player player;
    private GameEntity[][] objectList;

    public World(String player_name) throws IOException {
        this.player = new Player(new Point2D(25, 25));

        objectList = getObjects();
    }

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for(GameEntity[] ge_arr : objectList)
            for(GameEntity ge : ge_arr)
                if(ge != null)
                    ge.draw(gc);

        player.draw(gc);
    }

    public void control(KeyEvent event){
        KeyCode keyCode = event.getCode();

        if (keyCode == KeyCode.W)
            player.up();
        else if (keyCode == KeyCode.S)
            player.down();
        else if (keyCode == KeyCode.A)
            player.right();
        else if (keyCode == KeyCode.D)
            player.left();
        else if(keyCode == KeyCode.ESCAPE) {
            isOver = true;
        }
    }

    private GameEntity[][] getObjects() throws IOException {
        Random random = new Random();
        GameEntity[][] objects = new GameEntity[74][41];

        // create borders
        for(int i = 0; i < 74; i++){
            for(int j = 0; j < 41; j++){
                objects[i][j] = new Border(new Point2D(i*25, j*25));
            }
        }

        // create exit
        boolean exitSide = random.nextBoolean();
        if(exitSide){
            int first_value = (random.nextBoolean()) ? 0 : 73;
            int second_value = random.nextInt(40);
            objects[first_value][second_value] = null;
        }
        else{
            int first_value = random.nextInt(73);
            int second_value = (random.nextBoolean()) ? 0 : 40;
            objects[first_value][second_value] = null;
        }

        // create other objects randomly
        for(int i = 1; i < 73; i+= 1){
            for(int j = 1; j < 40; j+= 1){
                // 80 % chance to generate clay
                if(random.nextInt(10) <= 7)
                    objects[i][j] = new Clay(new Point2D(i*25, j*25));
                else
                    objects[i][j] = null;
            }
        }

        return objects;
    }
}
