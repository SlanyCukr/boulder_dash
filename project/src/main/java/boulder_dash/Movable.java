package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;

public abstract class Movable implements GameEntity{
    protected MyPoint2D position;
    protected Image image;

    public Movable(Point2D position) throws IOException {
        this.position = new MyPoint2D(position);
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
