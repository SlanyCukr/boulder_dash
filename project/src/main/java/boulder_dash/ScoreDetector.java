package boulder_dash;

import javafx.geometry.Point2D;

public class ScoreDetector implements HitListener{
    private int player1Score;
    private int player2Score;
    private transient Game game;

    public ScoreDetector(Game game){
        this.player1Score = 0;
        this.player2Score = 0;
        this.game = game;
    }

    public void HandleScoreDetection(Point2D ballPosition){
        double xDifference = ballPosition.getX() - this.game.getWidth();
        if(xDifference > 0)
            this.player1Score++;
        else
            this.player2Score++;
    }

    public int GetFirstPlayerScore(){
        return this.player1Score;
    }

    public int GetSecondPlayerScore(){
        return this.player2Score;
    }

    @Override
    public void Hitted(Point2D ballPosition) {
        HandleScoreDetection(ballPosition);
    }

    public void setGame(Game game) {this.game = game;}
}
