package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class World implements Serializable {
    public boolean isStopped;
    public boolean isOver;
    private boolean exitingGame;

    private Player player;
    private HUD hud;
    private GameEntity[][] objectList;
    private int objectsXLen;
    private int objectsYLen;
    private int deltaMax;
    private int currentDelta;
    private MyPoint2D exitCords;

    public World(String playerName, int deltaMax, int objectsXLen, int objectsYLen, int textY, int gameLength) throws IOException {
        this.player = new Player(new Point2D(25, 25), playerName);
        this.hud = new HUD(new Date().getTime() + (gameLength * 1000L), textY, objectsXLen);
        this.deltaMax = deltaMax;
        this.objectsXLen = objectsXLen;
        this.objectsYLen = objectsYLen;

        this.currentDelta = 0;

        objectList = generateGame();
    }

    public HighScore getHighScore(){
        return new HighScore(player.getScore(), player.getPlayerName());
    }

    public void draw(GraphicsContext gc){
        try {
            for (GameEntity[] ge_arr : objectList)
                for (GameEntity ge : ge_arr)
                    if (ge != null)
                        ge.draw(gc);

            // draw player on top of everything
            player.draw(gc);

            // draw HUD
            hud.draw(gc, player.getPlayerName(), player.getScore(), exitingGame);
        }
        catch(Exception ignored){}
    }

    public void control(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (keyCode == KeyCode.W && playerCanMove(player.getPosition(), new Point2D(player.getPosition().getX(), player.getPosition().getY() - 25)))
            player.up();
        else if (keyCode == KeyCode.S && playerCanMove(player.getPosition(), new Point2D(player.getPosition().getX(), player.getPosition().getY() + 25)))
            player.down();
        else if (keyCode == KeyCode.A && playerCanMove(player.getPosition(), new Point2D(player.getPosition().getX() - 25, player.getPosition().getY())))
            player.right();
        else if (keyCode == KeyCode.D && playerCanMove(player.getPosition(), new Point2D(player.getPosition().getX() + 25, player.getPosition().getY())))
            player.left();
        else if (keyCode == KeyCode.ESCAPE) {
            isStopped = true;
        }
    }

    public void simulate(int timeDelta) {
        // smoothly calculates bonus score
        if(exitingGame){
            player.bonusScore(hud.decrementRemainingTime());
        }

        // end the game if no time is left
        if(hud.getRemainingTime() <= 0) {
            this.isOver = true;
            return;
        }

        // slow down boulders falling down
        if(this.currentDelta != this.deltaMax) {
            this.currentDelta += timeDelta;
            return;
        }

        this.currentDelta = 0;

        for(int i = 1; i < objectsXLen; i++){
            for(int j = 1; j < objectsYLen; j+= 1){
                // ignore empty objects
                if(objectList[i][j] == null)
                    continue;

                // move one boulder downwards
                if(objectList[i][j].getClass().getName().equals("boulder_dash.Boulder") && objectList[i][j + 1] == null) {
                    Boulder temp = (Boulder) objectList[i][j];
                    objectList[i][j] = null;
                    objectList[i][j + 1] = temp;
                    temp.setPosition(new Point2D(temp.getPosition().getX(), temp.getPosition().getY() + 25));

                    // check if boulder movement killed player
                    int playerXPositionArr = (int) (player.getPosition().getX() / 25);
                    int playerYPositionArr = (int) (player.getPosition().getY() / 25);
                    if(playerXPositionArr == i && playerYPositionArr == j + 1)
                        isOver = true;
                    return;
                }
            }
        }
    }

    private boolean playerCanMove(Point2D previousPosition, Point2D nextPosition){
        int xPosition = (int) (nextPosition.getX() / 25);
        int yPosition = (int) (nextPosition.getY() / 25);

        // handle exit
        if(nextPosition.getX() == exitCords.getPoint().getX() && nextPosition.getY() == exitCords.getPoint().getY()){
            exitingGame = true;
            return true;
        }

        // handle negative index
        if(xPosition < 0 || yPosition < 0)
            return false;
        if(xPosition >= objectsXLen || yPosition >= objectsYLen)
            return false;

        // handle null objects
        if(objectList[xPosition][yPosition] == null)
            return true;

        // remove clay
        if(objectList[xPosition][yPosition].getClass().getName().equals("boulder_dash.Clay")) {
            objectList[xPosition][yPosition] = null;
            return true;
        }

        // collect diamonds
        if(objectList[xPosition][yPosition].getClass().getName().equals("boulder_dash.Diamond")) {
            objectList[xPosition][yPosition] = null;
            player.addDiamond();
            return true;
        }

        // move boulder
        if(objectList[xPosition][yPosition].getClass().getName().equals("boulder_dash.Boulder")){
            int directionX = (int) (-previousPosition.getX() / 25 + xPosition);
            int directionY = (int) (-previousPosition.getY() / 25 + yPosition);

            int boulderNextX = xPosition + directionX;
            int boulderNextY = yPosition + directionY;

            if(objectList[boulderNextX][boulderNextY] == null){
                Boulder boulder = (Boulder) objectList[xPosition][yPosition];
                boulder.setPosition(new Point2D(boulderNextX * 25, boulderNextY * 25));
                objectList[boulderNextX][boulderNextY] = boulder;
                objectList[xPosition][yPosition] = null;
                return true;
            }
        }
        return false;
    }

    private GameEntity[][] generateGame() throws IOException {
        Random random = new Random();
        GameEntity[][] objects = new GameEntity[objectsXLen][objectsYLen];

        // create borders
        for(int i = 0; i < objectsXLen; i++){
            for(int j = 0; j < objectsYLen; j++){
                objects[i][j] = new Border(new Point2D(i*25, j*25));
            }
        }

        // create exit
        boolean exitSide = random.nextBoolean();
        if(exitSide){
            int firstValue = (random.nextBoolean()) ? 0 : objectsXLen - 1;
            int secondValue = random.nextInt(objectsYLen - 1);
            objects[firstValue][secondValue] = null;
            exitCords = new MyPoint2D(firstValue * 25, secondValue * 25);
        }
        else{
            int firstValue = random.nextInt(objectsXLen - 1);
            int secondValue = (random.nextBoolean()) ? 0 : objectsYLen - 1;
            objects[firstValue][secondValue] = null;
            exitCords = new MyPoint2D(firstValue * 25, secondValue * 25);
        }

        // create other objects randomly
        for(int i = 1; i < objectsXLen - 1; i+= 1){
            for(int j = 1; j < objectsYLen - 1; j+= 1){
                int randomNumber = random.nextInt(20);

                // randomly generate objects
                if(randomNumber <= 14)
                    objects[i][j] = new Clay(new Point2D(i*25, j*25));
                else if(randomNumber == 15 || randomNumber == 16)
                    objects[i][j] = new Boulder(new Point2D(i*25, j*25));
                else if(randomNumber == 17)
                    objects[i][j] = new Diamond(new Point2D(i * 25, j * 25));
                else if(randomNumber == 18 || randomNumber == 19)
                    objects[i][j] = new Wall(new Point2D(i * 25, j * 25));
                else
                    objects[i][j] = null;
            }
        }

        // make room for player
        objects[1][1] = null;

        return objects;
    }
}
