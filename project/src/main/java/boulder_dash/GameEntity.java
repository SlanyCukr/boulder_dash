package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public interface GameEntity extends Serializable {
    public void draw(GraphicsContext gc);
    public Point2D getPosition();
}
