package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public interface VisibleEntity extends GameEntity, Serializable {
    public Point2D getPosition();
    public Point2D getSize();
    public void setPosition(Point2D point);
    public void draw(GraphicsContext gc);
    public Game getGame();
}
