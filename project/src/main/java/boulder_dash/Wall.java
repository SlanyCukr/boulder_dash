package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.IOException;

public class Wall implements GameEntity {
    private MyPoint2D position;
    private Image image;

    public Wall(Point2D position) throws IOException {
        this.position = new MyPoint2D(position);

        FileInputStream inputStream = new FileInputStream("wall.jpg");
        image = new Image(inputStream);

        inputStream.close();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, getPosition().getX(), getPosition().getY());
    }

    @Override
    public Point2D getPosition() {
        return position.getPoint();
    }
}