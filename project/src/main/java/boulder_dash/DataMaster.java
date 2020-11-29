package boulder_dash;

import java.io.*;

public class DataMaster {
    private static DataMaster single_instance = null;

    private String gamePath;

    private DataMaster() {
        gamePath = "game.txt";
    }

    public static DataMaster getInstance(){
        if(single_instance == null)
            single_instance = new DataMaster();

        return single_instance;
    }

    public void saveGame(Object[] objects) throws IOException, ClassNotFoundException{
        Player player1 = (Player)objects[0];
        Player player2 = (Player) objects[1];
        Ball ball = (Ball) objects[2];
        ScoreDrawer scoreDrawer = (ScoreDrawer) objects[3];
        ScoreDetector scoreDetector = (ScoreDetector) objects[4];

        FileOutputStream fileOutputStream = new FileOutputStream(gamePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(player1);
        objectOutputStream.writeObject(player2);
        objectOutputStream.writeObject(ball);
        objectOutputStream.writeObject(scoreDrawer);
        objectOutputStream.writeObject(scoreDetector);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public Object[] loadGame() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(gamePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Player player1 = (Player) objectInputStream.readObject();
        Player player2 = (Player) objectInputStream.readObject();
        Ball ball = (Ball) objectInputStream.readObject();
        ScoreDrawer scoreDrawer = (ScoreDrawer) objectInputStream.readObject();
        ScoreDetector scoreDetector = (ScoreDetector) objectInputStream.readObject();
        objectInputStream.close();

        Object[] objects = new Object[5];
        objects[0] = player1;
        objects[1] = player2;
        objects[2] = ball;
        objects[3] = scoreDrawer;
        objects[4] = scoreDetector;

        return objects;
    }
}
