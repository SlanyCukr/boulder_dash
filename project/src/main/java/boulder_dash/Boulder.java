package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Boulder extends Movable implements Serializable {
    public Boulder(Point2D position) throws IOException {
        super(position);
    }

    public Point2D getPosition(){
        return this.position.getPoint();
    }

    public void setPosition(Point2D point) {
        this.position = new MyPoint2D(point);
    }

    @Override
    public void draw(GraphicsContext gc) throws IOException {
        gc.drawImage(getImage(), getPosition().getX(), getPosition().getY());
    }

    @Override
    public Image getImage() throws IOException {
        if(image == null){
            FileInputStream inputStream = new FileInputStream("boulder.jpg");
            image = new Image(inputStream);
            inputStream.close();
        }

        return image;
    }
}
