package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Ball implements VisibleEntity, SimulatedEntity{
    protected transient Game game;

    protected MyPoint2D position;
    protected MyPoint2D speed;
    protected MyPoint2D size;

    public Ball(Game game){
        this(game, new Point2D(0, 0), new Point2D(5, 5), new Point2D(10, 10));
    }
    public Ball(Game game, Point2D start, Point2D speed, Point2D size){
        this.game = game;

        this.position = new MyPoint2D(start);
        this.speed = new MyPoint2D(speed);
        this.size = new MyPoint2D(size);
    }

    @Override
    public Point2D getSize(){
        return this.size.getPoint();
    }

    @Override
    public void setPosition(Point2D point) {
        this.position.setPoint(point);
    }

    @Override
    public Point2D getPosition(){
        return this.position.getPoint();
    }

    public void playerBounce(Point2D playerSpeed){
        if(playerSpeed.getY() > 0)
            this.speed = new MyPoint2D(new Point2D(this.speed.getPoint().getX(), this.speed.getPoint().getY() * (this.speed.getPoint().getY() < 0 ? 0.5 : 1.5)));
        else
            this.speed = new MyPoint2D(new Point2D(this.speed.getPoint().getX(), this.speed.getPoint().getY() * (this.speed.getPoint().getY() > 0 ? 0.5 : 1.5)));

        boundaryBounce();
    }

    public void boundaryBounce(){
        this.speed = new MyPoint2D(this.speed.getPoint().multiply(-1));
    }

    @Override
    public void simulate(int timeDelta){
        // factor passed time by increasing speed of ball
        Point2D position = this.getPosition().add(new Point2D(speed.getPoint().getX() * timeDelta, speed.getPoint().getY() * timeDelta));
        this.setPosition(position);

        if(position.getY() >= game.getHeight() || position.getY() <= 0 + this.size.getPoint().getX())
            this.boundaryBounce();
        if(position.getX() > game.getWidth())
            this.getGame().getGate1().hitBy(this);
        if(position.getX() < 0 + this.size.getPoint().getY())
            this.getGame().getGate2().hitBy(this);
    }

    @Override
    public void draw(GraphicsContext gc){
        Point2D canvasPoint = game.getCanvasPoint(position.getPoint());
        gc.fillOval(canvasPoint.getX(), canvasPoint.getY(), size.getPoint().getX(), size.getPoint().getY());
    }

    @Override
    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {this.game = game;}
}
