package boulder_dash;

import javafx.geometry.Point2D;

import java.io.Serializable;

@FunctionalInterface
public interface HitListener extends Serializable {
    public void Hitted(Point2D ballPosition);
}
