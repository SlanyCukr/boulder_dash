package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

public class Player extends Movable implements Serializable {
    private int score;
    private String playerName;

    public Player(Point2D position, String playerName) throws IOException {
        super(position);

        this.playerName = playerName;
        score = 0;
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
        this.score += 1000;
    }

    public int getScore(){return this.score;}

    public String getPlayerName(){
        return this.playerName;
    }

    public void bonusScore(int time){
        this.score += time * 10;
    }

    @Override
    public Image getImage() throws IOException {
        if(image == null){
            FileInputStream inputStream = new FileInputStream("player.jpg");
            image = new Image(inputStream);
            inputStream.close();
        }

        return image;
    }
}
