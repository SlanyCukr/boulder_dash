package boulder_dash;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 *  Class <b>App</b> - extends class Application and it is an entry point of the program
 * @author     Java I
 */
public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	private Canvas canvas;
	private World world;

	@FXML
	private TextField playerName;

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
		primaryStage.setTitle("Boulder dash");
		Scene scene = new Scene(root, 1920, 1080);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private ArrayList<HighScore> highScores;
	@FXML
	private TableView<HighScore> high_scores_table;

	@FXML
	public void initialize() {
	}

	private void app_start_game(World world){
		try {
			this.world = world;

			Stage primaryStage = new Stage();

			//Construct a main window with a canvas.
			Group root = new Group();
			canvas = new Canvas(1920, 1080);
			root.getChildren().add(canvas);
			Scene scene = new Scene(root, 1920, 1080);
			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> world.control(event));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.resizableProperty().set(false);
			primaryStage.setTitle("Boulder dash");
			primaryStage.show();

			//Exit program when main window is closed
			//primaryStage.setOnCloseRequest(this::exitProgram);

			//Draw scene on a separate thread to avoid blocking diamondsCountUI
			new Thread(this::drawScene).start();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@FXML
	void loadGame(ActionEvent event) throws IOException, ClassNotFoundException {
		/*Object[] objects = DataMaster.getInstance().loadGame();

		Player player1 = (Player) objects[0];
		Player player2 = (Player) objects[1];
		//Ball ball = (Ball) objects[2];
		ScoreDrawer scoreDrawer = (ScoreDrawer) objects[3];
		ScoreDetector scoreDetector = (ScoreDetector) objects[4];

		canvas = new Canvas(640, 480);
		//Game game = new Game(canvas.getWidth(), canvas.getHeight(), scoreDrawer.getFirstPlayerName(), scoreDrawer.getSecondPlayerName(), player1_UP, player1_DOWN, player2_UP, player2_DOWN);
		//game.loadGame(player1, player2, ball, scoreDrawer, scoreDetector);

		//app_start_game(game);
		*/
	}

	@FXML
	void startGame(ActionEvent event_) throws IOException {
		canvas = new Canvas(1920, 1080);

		app_start_game(new World(playerName.getText(), 250, 74, 38, 40, 180));
	}

	@FXML
	void exitGame(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
		/*Statement stmt = connection.createStatement();

		stmt.execute("DELETE FROM High_scores");

		for(HighScore hs : high_scores_table.getItems()){
			stmt.execute("INSERT INTO High_scores VALUES ('" + hs.player.get() + "', " + hs.value.get() + ")");
		}
		connection.close();

		DataMaster.getInstance().saveGame(game.getSaveData());
		*/
		System.exit(0);
	}

	/**
	 * Draws objects into the canvas. Put you code here. 
	 *
	 *@return      nothing
	 */
	private void drawScene() {
		// set text properties
		Platform.runLater(() -> {
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.setFont(new Font(20));
			gc.setFill(Color.RED);
		});

		while(!Routines.isEndOfThreadRequestedByJavaVM() && !world.isOver) {
			Platform.runLater(() ->{
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				world.draw(gc);
					});
			Routines.sleep(25);
			world.simulate(25);
		}

		// close old game window
		Platform.runLater(() ->{
			final Stage stage = (Stage) canvas.getScene().getWindow();
			stage.close();

			/*
			highScores.add(game.getHighScore());
			//high_scores_table.getItems().add(game.getHighScore());
			highScores.sort(Comparator.comparing((HighScore hs) -> hs.value.getValue()).reversed());

			HashSet<HighScore> hashSet = new HashSet<>(highScores);
			highScores = new ArrayList<>(hashSet);
			highScores.sort(Comparator.comparing((HighScore hs) -> hs.value.getValue()).reversed());

			high_scores_table.getItems().clear();
			high_scores_table.getItems().addAll(highScores);
			high_scores_table.sort();
			*/
		});
	}

	private void exitProgram(WindowEvent evt) {
		System.exit(0);
	}
}