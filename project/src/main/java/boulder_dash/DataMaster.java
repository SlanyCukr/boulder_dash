package boulder_dash;

import java.io.*;
import java.sql.*;

public class DataMaster {
    private static DataMaster instance = null;
    private String savePath;

    private DataMaster(){
        savePath = "game.txt";
    }

    public static DataMaster getInstance(){
        if(instance == null){
            instance = new DataMaster();
        }

        return instance;
    }

    public World loadGame() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(savePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        World world = (World) objectInputStream.readObject();
        objectInputStream.close();

        return world;
    }

    public void saveGame(World world) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(savePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(world);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public void createTable() throws SQLException {
        getStatement().execute("CREATE TABLE High_scores (name VARCHAR(20) NOT NULL, score INT NOT NULL, PRIMARY KEY (name))");
    }

    public void emptyTable() throws SQLException {
        getStatement().execute("DELETE FROM High_scores");
    }

    public ResultSet selectHighScores() throws SQLException {
        return getStatement().executeQuery("SELECT * FROM High_scores");
    }

    public void addHighScore(HighScore hs) throws SQLException {
        getStatement().execute("INSERT INTO High_scores VALUES ('" + hs.player.get() + "', " + hs.value.get() + ")");
    }

    private Statement getStatement() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:derby:scoreDB;create=true");

        return connection.createStatement();
    }
}
