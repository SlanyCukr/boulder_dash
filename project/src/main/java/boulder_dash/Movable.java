package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public abstract class Movable implements GameEntity, Serializable {
    protected MyPoint2D position;
    protected transient Image image;

    public Movable(Point2D position) throws IOException {
        this.position = new MyPoint2D(position);
    }

    public Point2D getPosition(){
        return this.position.getPoint();
    }

    public void setPosition(Point2D point) {
        this.position = new MyPoint2D(point);
    }

    public void draw(GraphicsContext gc) throws IOException {
        gc.drawImage(getImage(), getPosition().getX(), getPosition().getY());
    }

    public Image getImage() throws IOException {
        return null;
    }
}
