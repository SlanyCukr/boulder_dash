/*package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.Serializable;

public class Game implements HitListener, Serializable {
    public boolean isOver;

    private double width;
    private double height;

    private Ball ball;
    private Player player1;
    private Player player2;
    private DashedLine dashedLine;
    private ScoreDetector scoreDetector;
    private ScoreDrawer scoreDrawer;
    private Gate gate1;
    private Gate gate2;

    private KeyCode player1_UP;
    private KeyCode player1_DOWN;
    private KeyCode player2_UP;
    private KeyCode player2_DOWN;

    public Game(double width, double height, String player1_name, String player2_name, KeyCode player1_UP, KeyCode player1_DOWN, KeyCode player2_UP, KeyCode player2_DOWN){
        this.setWidth(width);
        this.setHeight(height);

        this.ball = new Ball(this, new Point2D(25, 25), new Point2D(2.0 / 10,1.0 / 10), new Point2D(25, 25));
        //this.player1 = new Player(this, new Point2D(50, 240 + 50), new Point2D(25, 100));
        //this.player2 = new Player(this, new Point2D(590 - 25, 240 + 50), new Point2D(25, 100));
        this.dashedLine = new DashedLine(new Point2D(this.getWidth() / 2, 0), (int)this.getHeight());
        this.scoreDetector = new ScoreDetector(this);
        this.scoreDrawer = new ScoreDrawer(this, scoreDetector, player1_name, player2_name);

        this.player1_UP = player1_UP;
        this.player1_DOWN = player1_DOWN;
        this.player2_UP = player2_UP;
        this.player2_DOWN = player2_DOWN;

        // prepare event handling
        this.gate1 = new Gate();
        gate1.addHitListener(scoreDetector);
        gate1.addHitListener(this);

        this.gate2 = new Gate();
        gate2.addHitListener(this);
        gate2.addHitListener(scoreDetector);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        if(width <= 0){
            throw new IllegalArgumentException("Width of the world must be greater than zero.");
        }
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if(height <= 0){
            throw new IllegalArgumentException("Height of the world must be greater than zero.");
        }
        this.height = height;
    }

    public Point2D getCanvasPoint(Point2D worldPoint){
        return new Point2D(worldPoint.getX(), height - worldPoint.getY());
    }

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        ball.draw(gc);
        player1.draw(gc);
        player2.draw(gc);
        dashedLine.draw(gc);
        scoreDrawer.draw(gc);
    }

    public void control(KeyEvent event){
        KeyCode keyCode = event.getCode();

        if (keyCode == this.player1_UP)
            player1.up();
        else if (keyCode == this.player1_DOWN)
            player1.down();
        else if (keyCode == this.player2_UP)
            player2.up();
        else if (keyCode == this.player2_DOWN)
            player2.down();
        else if(keyCode == KeyCode.ESCAPE) {
            isOver = true;
        }
    }

    public void simulate(int timeDelta)
    {
        ball.simulate(timeDelta);
        player1.simulate(timeDelta);
        player2.simulate(timeDelta);

        if(player1.pointCollides(ball.position.getPoint(), ball.getSize().getX()))
            ball.playerBounce(this.player1.getSpeed());
        if(player2.pointCollides(ball.position.getPoint(),  ball.getSize().getX()))
            ball.playerBounce(this.player1.getSpeed());
    }

    public void startAgain(){
        this.ball = new Ball(this, new Point2D(25, 25), new Point2D(2.0 / 10,1.0 / 10), new Point2D(25, 25));
        this.player1 = new Player(this, new Point2D(50, 240 + 50), new Point2D(25, 100));
        this.player2 = new Player(this, new Point2D(590 - 25, 240 + 50), new Point2D(25, 100));
        this.dashedLine = new DashedLine(new Point2D(this.getWidth() / 2, 0), (int)this.getHeight());

        // prepare event handling
        this.gate1 = new Gate();
        gate1.addHitListener(scoreDetector);
        gate1.addHitListener(this);

        this.gate2 = new Gate();
        gate2.addHitListener(this);
        gate2.addHitListener(scoreDetector);
    }

    public void loadGame(Player player1, Player player2, Ball ball, ScoreDrawer scoreDrawer, ScoreDetector scoreDetector){
        // inject game into objects
        ball.setGame(this);
        player1.setGame(this);
        player2.setGame(this);
        scoreDrawer.setGame(this);
        scoreDetector.setGame(this);

        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
        this.dashedLine = new DashedLine(new Point2D(this.getWidth() / 2, 0), (int)this.getHeight());
        this.scoreDetector = scoreDetector;
        this.scoreDrawer = scoreDrawer;

        // prepare event handling
        this.gate1 = new Gate();
        gate1.addHitListener(scoreDetector);
        gate1.addHitListener(this);

        this.gate2 = new Gate();
        gate2.addHitListener(this);
        gate2.addHitListener(scoreDetector);
    }

    public HighScore getHighScore(){
        if(this.scoreDetector.GetFirstPlayerScore() >= this.scoreDetector.GetSecondPlayerScore())
            return new HighScore(this.scoreDetector.GetFirstPlayerScore(), this.scoreDrawer.getFirstPlayerName());
        else
            return new HighScore(this.scoreDetector.GetSecondPlayerScore(), this.scoreDrawer.getSecondPlayerName());
    }

    public Gate getGate1(){
        return this.gate1;
    }

    public Gate getGate2(){
        return this.gate2;
    }

    @Override
    public void Hitted(Point2D ballPosition) {
        this.startAgain();
    }

    public Object[] getSaveData(){
        Object[] objects = new Object[5];
        objects[0] = player1;
        objects[1] = player2;
        objects[2] = ball;
        objects[3] = scoreDrawer;
        objects[4] = scoreDetector;

        return objects;
    }
}
*/
