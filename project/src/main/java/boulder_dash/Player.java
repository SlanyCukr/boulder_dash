package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;

public class Player implements GameEntity   {
    private MyPoint2D position;
    private Image image;

    public Player(Point2D position) throws IOException {
        this.position = new MyPoint2D(position);

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

    public void up(){
        this.position = new MyPoint2D(this.position.getPoint().add(new Point2D(0, -25)));
    }

    public void down(){
        this.position = new MyPoint2D(this.position.getPoint().add(new Point2D(0, +25)));
    }

    public void left(){
        this.position = new MyPoint2D(this.position.getPoint().add(new Point2D(+25, 0)));
    }

    public void right(){
        this.position = new MyPoint2D(this.position.getPoint().add(new Point2D(-25, 0)));
    }
}
