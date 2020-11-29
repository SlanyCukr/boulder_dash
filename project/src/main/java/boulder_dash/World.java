package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.List;

public class World {
    public boolean isOver;

    private Player player;
    private List<Boulder> boulderList;
    private List<Clay> clayList;

    public World(String player_name) throws IOException {
        this.player = new Player(new Point2D(0, 0), new Point2D(25, 25));
    }

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

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
}
