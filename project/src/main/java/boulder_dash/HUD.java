package boulder_dash;

import javafx.scene.canvas.GraphicsContext;

import java.util.Date;

public class HUD {
    private long endTime;
    private double textY;
    private double textXMax;

    public HUD(long endTime, int textY, int textXMax){
        this.endTime = endTime;
        this.textY = textY * 25;
        this.textXMax = textXMax * 25;
    }

    public void draw(GraphicsContext gc, String playerName, int numberOfDiamonds){
        drawPlayerName(gc, playerName);
        drawPlayerDiamonds(gc, numberOfDiamonds);
        drawRemainingTime(gc);
    }

    private void drawPlayerName(GraphicsContext gc, String playerName){
        gc.fillText(playerName, textXMax / 4, textY);
    }

    private void drawPlayerDiamonds(GraphicsContext gc, int numberOfDiamonds){
        gc.fillText(String.valueOf(numberOfDiamonds), (2 * textXMax) / 4, textY);
    }

    private void drawRemainingTime(GraphicsContext gc){
        int remainingTime = getRemainingTime();

        gc.fillText(String.valueOf(remainingTime),(3 * textXMax) / 4, textY);
    }

    public int getRemainingTime(){
        Date currentDate = new Date();
        return (int) (endTime / 1000 - currentDate.getTime() / 1000);
    }
}
