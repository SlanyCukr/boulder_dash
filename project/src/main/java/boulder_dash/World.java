package boulder_dash;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.Random;

public class World {
    public boolean isOver;

    private Player player;
    private GameEntity[][] objectList;
    private int objectsXLen;
    private int objectsYLen;
    private int deltaMax;
    private int currentDelta;

    public World(String player_name, int deltaMax, int objectsXLen, int objectsYLen) throws IOException {
        this.player = new Player(new Point2D(25, 25));
        this.deltaMax = deltaMax;
        this.objectsXLen = objectsXLen;
        this.objectsYLen = objectsYLen;
        this.currentDelta = 0;

        objectList = generateGame();
    }

    public void draw(GraphicsContext gc) {
        for (GameEntity[] ge_arr : objectList)
            for (GameEntity ge : ge_arr)
                if (ge != null)
                    ge.draw(gc);

        // draw player on top of everything
        player.draw(gc);
    }

    public void control(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        if (keyCode == KeyCode.W && playerCanMove(new Point2D(player.getPosition().getX(), player.getPosition().getY() - 25)))
            player.up();
        else if (keyCode == KeyCode.S && playerCanMove(new Point2D(player.getPosition().getX(), player.getPosition().getY() + 25)))
            player.down();
        else if (keyCode == KeyCode.A && playerCanMove(new Point2D(player.getPosition().getX() - 25, player.getPosition().getY())))
            player.right();
        else if (keyCode == KeyCode.D && playerCanMove(new Point2D(player.getPosition().getX() + 25, player.getPosition().getY())))
            player.left();
        else if (keyCode == KeyCode.ESCAPE) {
            isOver = true;
        }
    }

    public void simulate(int timeDelta) {
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

    private boolean playerCanMove(Point2D nextPosition){
        int xPosition = (int) (nextPosition.getX() / 25);
        int yPosition = (int) (nextPosition.getY() / 25);

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
            int secondValue = random.nextInt(objectsYLen);
            objects[firstValue][secondValue] = null;
        }
        else{
            int firstValue = random.nextInt(objectsXLen);
            int secondValue = (random.nextBoolean()) ? 0 : objectsYLen - 1;
            objects[firstValue][secondValue] = null;
        }

        // create other objects randomly
        for(int i = 1; i < objectsXLen - 1; i+= 1){
            for(int j = 1; j < objectsYLen - 1; j+= 1){
                int randomNumber = random.nextInt(20);

                // randomly generate objects
                if(randomNumber <= 16)
                    objects[i][j] = new Clay(new Point2D(i*25, j*25));
                else if(randomNumber == 17 || randomNumber == 18)
                    objects[i][j] = new Boulder(new Point2D(i*25, j*25));
                else if(randomNumber == 19)
                    objects[i][j] = new Diamond(new Point2D(i * 25, j * 25));
                else
                    objects[i][j] = null;
            }
        }

        // create walls
        int numberOfWalls = random.nextInt(4) + 1;
        for(int i = 1; i < numberOfWalls + 1; i++){
            for(int j = 1 + random.nextInt(5); j < objectsXLen - random.nextInt(10); j++){
                objects[j][(i * 10) - 1] = new Wall(new Point2D(j * 25, ((i * 10) - 1) * 25));
            }
        }

        // make room for player
        objects[1][1] = null;

        return objects;
    }
}
