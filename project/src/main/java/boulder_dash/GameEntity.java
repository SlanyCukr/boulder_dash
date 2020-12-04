package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.io.Serializable;

public interface GameEntity extends Serializable {
    public void draw(GraphicsContext gc) throws IOException;
    public Point2D getPosition();
}
