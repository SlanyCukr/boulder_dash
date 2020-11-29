package boulder_dash;

import javafx.scene.canvas.GraphicsContext;

public class ScoreDrawer implements GameEntity {

    private ScoreDetector scoreDetector;
    private transient Game game;
    private String player1;
    private String player2;

    public ScoreDrawer(Game game, ScoreDetector scoreDetector, String player1, String player2){
        this.scoreDetector = scoreDetector;
        this.game = game;

        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        // draw score
        gc.fillText(Integer.toString(scoreDetector.GetFirstPlayerScore()), (game.getWidth() / 2) - 50, 25);
        gc.fillText(Integer.toString(scoreDetector.GetSecondPlayerScore()), (game.getWidth() / 2) + 50, 25);

        // draw names
        gc.fillText(player1, (game.getWidth() / 2) - 200, 25);
        gc.fillText(player2, (game.getWidth() / 2) + 250, 25);
    }

    public String getFirstPlayerName(){
        return this.player1;
    }

    public String getSecondPlayerName(){
        return this.player2;
    }

    public void setGame(Game game) {this.game=game;}
}
