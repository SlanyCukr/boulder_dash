package boulder_dash;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
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
	public void initialize() throws SQLException {
		TableColumn nameCol = new TableColumn("Name");
		nameCol.setMinWidth(300);
		nameCol.setCellValueFactory(new PropertyValueFactory<HighScore, String>("player"));

		TableColumn scoreCol = new TableColumn("Score");
		scoreCol.setMinWidth(200);

		scoreCol.setSortType(TableColumn.SortType.DESCENDING);
		high_scores_table.getSortOrder().add(scoreCol);
		scoreCol.setCellValueFactory(new PropertyValueFactory<HighScore, Integer>("value"));

		high_scores_table.getColumns().addAll(nameCol, scoreCol);

		highScores = new ArrayList<>();
		high_scores_table.setItems(FXCollections.observableList(highScores));

		// DataMaster.getInstance().createTable();
		// DataMaster.getInstance().emptyTable();

		ResultSet rs = DataMaster.getInstance().selectHighScores();
		while(rs.next()) {
			highScores.add(new HighScore(rs.getInt("score"), rs.getString("name")));
		}
		high_scores_table.sort();
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

			//Draw scene on a separate thread to avoid blocking main thread
			new Thread(this::drawScene).start();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@FXML
	void loadGame(ActionEvent event) throws IOException, ClassNotFoundException {
		canvas = new Canvas(1920, 1080);

		World world = DataMaster.getInstance().loadGame();
		world.isStopped = false;
		app_start_game(world);
	}

	@FXML
	void startGame(ActionEvent event_) throws IOException {
		canvas = new Canvas(1920, 1080);

		app_start_game(new World(playerName.getText(), 250, 74, 38, 40, 180));
	}

	@FXML
	void exitGame(ActionEvent event) throws SQLException, IOException, ClassNotFoundException {
		DataMaster.getInstance().emptyTable();

		for(HighScore hs : high_scores_table.getItems()){
			DataMaster.getInstance().addHighScore(hs);
		}

		DataMaster.getInstance().saveGame(world);

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

		while(!Routines.isEndOfThreadRequestedByJavaVM() && !world.isStopped && !world.isOver) {
			Platform.runLater(() ->{
				GraphicsContext gc = canvas.getGraphicsContext2D();
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				world.draw(gc);
			});
			Routines.sleep(25);
			world.simulate(25);
		}

		// close old game window, sort and add high score
		Platform.runLater(() ->{
			final Stage stage = (Stage) canvas.getScene().getWindow();
			stage.close();

			highScores.add(world.getHighScore());
			highScores.sort(Comparator.comparing((HighScore hs) -> hs.value.getValue()).reversed());

			HashSet<HighScore> hashSet = new HashSet<>(highScores);
			highScores = new ArrayList<>(hashSet);
			highScores.sort(Comparator.comparing((HighScore hs) -> hs.value.getValue()).reversed());

			high_scores_table.getItems().clear();
			high_scores_table.getItems().addAll(highScores);
			high_scores_table.sort();
		});
	}
}