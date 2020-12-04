package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Diamond implements GameEntity, Serializable {
    private MyPoint2D position;
    private transient Image image;

    public Diamond(Point2D position) throws IOException {
        this.position = new MyPoint2D(position);
    }

    @Override
    public void draw(GraphicsContext gc) throws IOException {
        gc.drawImage(getImage(), getPosition().getX(), getPosition().getY());
    }

    @Override
    public Point2D getPosition() {
        return position.getPoint();
    }

    public Image getImage() throws IOException {
        if(image == null){
            FileInputStream inputStream = new FileInputStream("diamond.jpg");
            image = new Image(inputStream);
            inputStream.close();
        }

        return image;
    }
}
