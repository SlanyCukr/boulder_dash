package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Movable {
    private int diamondsCount;
    private String playerName;

    public Player(Point2D position, String playerName) throws IOException {
        super(position);

        this.playerName = playerName;
        diamondsCount = 0;  

        FileInputStream inputStream = new FileInputStream("player.jpg");
        image = new Image(inputStream);

        inputStream.close();
    }

    public Point2D getPosition(){
        return this.position.getPoint();
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

    public void addDiamond(){
        this.diamondsCount += 1;
    }

    public int getDiamondsCount(){return this.diamondsCount;}

    public String getPlayerName(){
        return this.playerName;
    }
}
