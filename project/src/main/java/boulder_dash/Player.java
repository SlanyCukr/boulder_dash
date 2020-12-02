package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Movable {
    private MyPoint2D position;
    private Image image;

    public Player(Point2D position) throws IOException {
        super(position);

        FileInputStream inputStream = new FileInputStream("player.jpg");
        image = new Image(inputStream);

        inputStream.close();
    }

    public Point2D getPosition(){
        return this.position.getPoint();
    }

    public void setPosition(Point2D point) {
        this.position = new MyPoint2D(point);
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image, getPosition().getX(), getPosition().getY());
    }
}
