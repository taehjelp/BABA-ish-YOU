package babagame;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//TODO 1 Controller-klasse
public class BABAcontroller {
	private BABAgame game;
	private int height;
	private int width;
	private BABAfile babaFile = new BABAfile();
	private String rootPath = new File("").getAbsolutePath().replace("\\", "/");
	private String filePath = rootPath + "/src/main/java/babagame/saveFile.txt";
	private File saveFile = new File(filePath);

	@FXML
	Pane board;
	@FXML
	Text winText = new Text();
	@FXML
	Text loseText = new Text();
	@FXML
	Text errorText = new Text();
	@FXML
	Text bonkText = new Text();

	public void initialize() {
		setInitialGameState();
		createBoard();
		try {
			drawBoard();
		} catch (Error e) {
			if (!board.getChildren().contains(errorText)) {
				errorText.setText("IMAGE is ERROR");
				errorText.setFont(Font.font("Comic sans MS", 45));
				errorText.setFill(Color.BLACK);
				errorText.setTranslateX(42.0);
				errorText.setTranslateY(200.0);
				board.getChildren().add(errorText);
			}
		}
	}

	private void setInitialGameState() {
		height = 14;
		width = 14;
		game = new BABAgame(height, width);

		char[][] board = { { ' ', ' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
						   { 'R', 't', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'w', 'i', 's', ' ', ' ' },
						   { ' ', 'i', ' ', ' ', ' ', ' ', 'W', 'W', 'W', 'W', 'W', 'W', 'W', ' ' },
						   { ' ', ' ', ' ', ' ', ' ', ' ', 'R', ' ', ' ', 'F', ' ', ' ', 'W', ' ' },
						   { ' ', ' ', 'R', ' ', ' ', ' ', 'W', ' ', ' ', ' ', 'R', ' ', 'W', ' ' },
						   { ' ', ' ', ' ', ' ', ' ', ' ', 'W', ' ', 'f', 'i', ' ', ' ', 'W', ' ' },
						   { ' ', ' ', ' ', ' ', ' ', ' ', 'W', 'W', 'W', 'W', 'v', ' ', 'W', ' ' },
						   { ' ', 'F', ' ', 'R', ' ', ' ', ' ', ' ', ' ', 'W', ' ', ' ', 'W', ' ' },
						   { 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'W', 'W', 'W', 'W', ' ' },
						   { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
						   { ' ', ' ', ' ', ' ', ' ', ' ', 'p', ' ', ' ', 'B', ' ', ' ', ' ', ' ' },
						   { ' ', ' ', ' ', 'r', 'i', 's', ' ', ' ', ' ', 'b', 'i', 'y', ' ', ' ' },
						   { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
						   { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' } };

		for (int y = 0; y < height; y++) // sets the type of each tile according to board[][]
			for (int x = 0; x < width; x++) {
				char chara = board[y][x];
				if (Character.isLowerCase(chara)) // text
					game.getTile(x, y).setType('T', chara);
				else if (Character.isUpperCase(chara)) {
					game.getTile(x, y).setType(chara); // type != text
				} // tile defaults to ' ' (EMPTY) if chara != alpha
			}
	}

	private void createBoard() {
		board.getChildren().clear();
		for (int y = 0; y < game.getHeight(); y++)
			for (int x = 0; x < game.getWidth(); x++) {
				Pane tile = new Pane();
				tile.setTranslateX(x * 30);
				tile.setTranslateY(y * 30);
				tile.setPrefWidth(30);
				tile.setPrefHeight(30);
				board.getChildren().add(tile);
			}
	}

	private void drawBoard() {
		for (int y = 0; y < game.getHeight(); y++) // fills each tile with an image according to type
			for (int x = 0; x < game.getWidth(); x++)
				board.getChildren().get(y * game.getWidth() + x).setStyle("-fx-background-image: url(" + "file:///"
						+ rootPath + "/src/main/resources/images/" + getTileImage(game.getTile(x, y)) + ");");
		// displays win/lose-text if applicable and text is not already there
		if (game.isWIN() && !board.getChildren().contains(winText)) {
			winText.setText("YOU is WIN!");
			winText.setFont(Font.font("Comic sans MS", 60));
			winText.setFill(Color.rgb(91, 133, 0));
			winText.setTranslateX(25.0);
			winText.setTranslateY(220.0);
			board.getChildren().add(winText);
		} else if (game.isLOSE() && !board.getChildren().contains(loseText)) {
			loseText.setText("YOU is CRINGE");
			loseText.setFont(Font.font("Comic sans MS", 50));
			loseText.setFill(Color.rgb(194, 19, 6));
			loseText.setTranslateX(25.0);
			loseText.setTranslateY(200.0);
			board.getChildren().add(loseText);
		}
	}

	private String getTileImage(BABAtile tile) {
		if (tile.isBABA())
			return "BABA.png";
		else if (tile.isFLAG())
			return "FLAG.png";
		else if (tile.isROCK())
			return "ROCK.png";
		else if (tile.isWALL())
			return "WALL.png";
		else if (tile.isTEXT()) { // text types:
			if (tile.getText() == 'b') // baba
				return "babatext.png";
			else if (tile.getText() == 't') // text
				return "TEXTtext.png";
			else if (tile.getText() == 'f') // flag
				return "flagtext.png";
			else if (tile.getText() == 'r') // rock
				return "rocktext.png";
			else if (tile.getText() == 'w') // wall
				return "walltext.png";
			else if (tile.getText() == 'i') // is
				return "istext.png";
			else if (tile.getText() == 'y') // you
				return "YOUtext.png";
			else if (tile.getText() == 'v') // win
				return "WINtext.png";
			else if (tile.getText() == 'p') // push
				return "PUSHtext.png";
			else if (tile.getText() == 's') // stop
				return "STOPtext.png";
			else
				return "EMPTY.png";
		} else
			return "EMPTY.png"; // empty, green
	}

	private void clearText() {
		if (board.getChildren().contains(loseText))
			board.getChildren().remove(loseText);
		else if (board.getChildren().contains(winText))
			board.getChildren().remove(winText);
		if (board.getChildren().contains(bonkText))
			board.getChildren().remove(bonkText);
		if (board.getChildren().contains(errorText))
			board.getChildren().remove(errorText);
	}

	private void errorText() { // adds temporary error-message if loading went wrong
		if (!board.getChildren().contains(errorText)) {
			errorText.setText("LOAD is ERROR");
			errorText.setFont(Font.font("Comic sans MS", 45));
			errorText.setFill(Color.BLACK);
			errorText.setTranslateX(42.0);
			errorText.setTranslateY(200.0);
			board.getChildren().add(errorText);
		}
	}

//buttons
	@FXML // player movement
	public void handleUp() {
		if (board.getChildren().contains(errorText))
			board.getChildren().remove(errorText);
		game.moveUp();
		drawBoard();
	}

	@FXML
	public void handleDown() {
		if (board.getChildren().contains(errorText))
			board.getChildren().remove(errorText);
		game.moveDown();
		drawBoard();
	}

	@FXML
	public void handleLeft() {
		if (board.getChildren().contains(errorText))
			board.getChildren().remove(errorText);
		game.moveLeft();
		drawBoard();
	}

	@FXML
	public void handleRight() {
		if (board.getChildren().contains(errorText))
			board.getChildren().remove(errorText);
		game.moveRight();
		drawBoard();
	}

	@FXML // resets board
	public void handleReset() {
		clearText();
		setInitialGameState();
		drawBoard();
	}

//TODO 2 filbehandling i controller
	@FXML // saves board to file
	public void handleSave() {
		babaFile.save(game.toString(), saveFile); // board is saved to file
	}

	@FXML // sets board according to the contents of saveFile
	public void handleLoad() { 
		clearText();
		char[][] board = babaFile.load(saveFile, height, width);
		if (board == null) // TODO 3 feilhandtering: load returns null if saveFile is empty, displays errorText
			errorText();
		else {
			game = new BABAgame(height, width);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					char chara = board[y][x];
					if (Character.isLowerCase(chara)) // type = text
						game.getTile(x, y).setType('T', chara);
					else if (Character.isUpperCase(chara))
						game.getTile(x, y).setType(chara); // type != text
					else
						game.getTile(x, y).setType(' ');
				}
			}
		}
		drawBoard();
	}

	@FXML // literally just displays a big BONK. a necessary feature.
	public void handleBonk() {
		if (!board.getChildren().contains(bonkText)) {
			bonkText.setText("BONK");
			bonkText.setFont(Font.font("Comic sans MS", 145));
			bonkText.setFill(Color.BLACK);
			bonkText.setOpacity(0.8);
			bonkText.setTranslateX(0.0);
			bonkText.setTranslateY(250.0);
			board.getChildren().add(bonkText);
		} else
			board.getChildren().remove(bonkText); // removes bonkText if it's already there
	}
}
