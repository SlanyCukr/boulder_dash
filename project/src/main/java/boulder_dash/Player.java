package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Player {
    private MyPoint2D position;
    private MyPoint2D size;
    private Image image;

    public Player(Point2D position, Point2D size) throws IOException {
        this.position = new MyPoint2D(position);
        this.size = new MyPoint2D(size);

        FileInputStream inputStream = new FileInputStream("player.jpg");
        image = new Image(inputStream);

        inputStream.close();
    }

    public Point2D getPosition(){
        return this.position.getPoint();
    }

    private Point2D getSize(){
        return this.size.getPoint();
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

    public boolean pointCollides(Point2D point, double radius){
        Point2D playerPosition = getPosition();

        if(point.getX() + radius > playerPosition.getX() && point.getY() + radius < playerPosition.getY() + this.size.getPoint().getY())
            if(point.getY() > playerPosition.getY() && point.getX()  < playerPosition.getX() + this.size.getPoint().getX())
                return true;
        return false;
    }
}
