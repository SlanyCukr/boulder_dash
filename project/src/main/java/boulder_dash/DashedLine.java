package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class DashedLine implements GameEntity {

    private int height;
    private MyPoint2D position;

    public DashedLine(Point2D position, int height) {
        this.position= new MyPoint2D(position);
        this.height=height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineWidth(1);
        gc.setLineDashes(2);
        gc.strokeLine(this.getPosition().getX(),this.getPosition().getY(), this.position.getPoint().getX(), this.height);
    }

    public Point2D getPosition() {
        return this.position.getPoint();
    }
}
