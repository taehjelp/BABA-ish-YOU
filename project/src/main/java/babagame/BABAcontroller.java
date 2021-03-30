package babagame;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BABAcontroller {
	private BABAgame game;
	private int height;
	private int width;
	private BABAfile babaFile = new BABAfile();
	
//	Path currentRelativePath = Paths.get(""); // funkekkje
//	String filePath = currentRelativePath.toAbsolutePath().toString() + "/saveFile.txt";
//	private File saveFile = new File(filePath);
	private File saveFile = new File("C:\\Users\\Lenovo\\git\\tdt4100-prosjekt-jennyhm\\project\\src\\main\\java\\babagame\\saveFile.txt");

	@FXML
	Pane board;
	@FXML
	Text winText = new Text();
	@FXML
	Text loseText = new Text();
	@FXML
	Text bonkText = new Text();

	public void initialize() {
		setInitialGameState();
		createBoard();
		drawBoard();
	}

	private void setInitialGameState() {
		height = 14;
		width = 14;
		game = new BABAgame(height, width);

		char[][] board =   {{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
							{ ' ', 't', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'w', 'i', 's', ' ', ' ' },
							{ ' ', 'i', ' ', ' ', ' ', ' ', 'W', 'W', 'W', 'W', 'W', 'W', 'W', ' ' },
							{ ' ', 'p', ' ', ' ', ' ', ' ', 'R', ' ', ' ', 'F', ' ', ' ', 'W', ' ' },
							{ ' ', ' ', ' ', ' ', ' ', ' ', 'W', ' ', ' ', ' ', 'R', ' ', 'W', ' ' },
							{ ' ', ' ', ' ', ' ', ' ', ' ', 'W', ' ', 'f', 'i', ' ', ' ', 'W', ' ' },
							{ ' ', ' ', ' ', ' ', ' ', ' ', 'W', 'W', 'W', 'W', 'v', ' ', 'W', ' ' },
							{ ' ', 'F', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'W', ' ', ' ', 'W', ' ' },
							{ 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'R', 'W', 'W', 'W', 'W', ' ' },
							{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
							{ ' ', ' ', ' ', ' ', ' ', ' ', 'p', ' ', ' ', 'B', ' ', ' ', ' ', ' ' },
							{ ' ', ' ', ' ', 'r', 'i', 's', ' ', ' ', 'b', 'i', 'y', ' ', ' ', ' ' },
							{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
							{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }};

		for (int y = 0; y < height; y++) { // sets the type of each tile according to board[][]
			for (int x = 0; x < width; x++) {
				char chara = board[y][x];
				if (!Character.isUpperCase(chara)) { // text
					game.getTile(x, y).setType('T', chara);
				} else if (Character.isUpperCase(chara)) {
					game.getTile(x, y).setType(chara); // type != text
				} // defaults to ' ' (empty) if chara !alpha
			}
		}
	}

	private void createBoard() {
		board.getChildren().clear();
		for (int y = 0; y < game.getHeight(); y++) {
			for (int x = 0; x < game.getWidth(); x++) {
				Pane tile = new Pane();
				tile.setTranslateX(x * 30);
				tile.setTranslateY(y * 30);
				tile.setPrefWidth(30);
				tile.setPrefHeight(30);
				tile.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
				board.getChildren().add(tile);
			}
		}
	}

	private void drawBoard() {
		for (int y = 0; y < game.getHeight(); y++) {
			for (int x = 0; x < game.getWidth(); x++) {
				board.getChildren().get(y * game.getWidth() + x)
						.setStyle("-fx-background-image: url( " + getTileImage(game.getTile(x, y)) + ";");
			}
		}
		if (game.isWIN()) {
			winText.setText("YOU is WIN!");
			winText.setFont(Font.font("Comic sans MS", 50));
			winText.setFill(Color.GREEN);
			winText.setTranslateX(45.0);
			winText.setTranslateY(200.0);
			board.getChildren().add(winText);
		} else if (game.isLOSE()) {
			loseText.setText("YOU is CRINGE");
			loseText.setFont(Font.font("Comic sans MS", 50));
			loseText.setFill(Color.RED);
			loseText.setTranslateX(25.0);
			loseText.setTranslateY(200.0);
			board.getChildren().add(loseText);
		}
	}
	
	private String getTileImage(BABAtile tile) { // URLs are images for each tile type
		if (tile.isBABA()) {
			return "https://i.imgur.com/09gR1P4.png)";
		} else if (tile.isFLAG()) {
			return "https://i.imgur.com/BUU86r1.png)";
		} else if (tile.isROCK()) {
			return "https://i.imgur.com/IOPBf9F.png)";
		} else if (tile.isWALL()) {
			return "https://i.imgur.com/ULUiNog.png)";
		} else if (tile.isTEXT()) { // text types:
			if (tile.getText() == 'b') { // baba
				return "https://i.imgur.com/yR99xQd.png)";
			} else if (tile.getText() == 't') { // text
				return "https://i.imgur.com/RSTsbBd.png)";
			} else if (tile.getText() == 'f') { // flag
				return "https://i.imgur.com/X6fgDd1.png)";
			} else if (tile.getText() == 'r') { // rock
				return "https://i.imgur.com/hC4seaY.png)";
			} else if (tile.getText() == 'w') { // wall
				return "https://i.imgur.com/nntZQai.png)";
			} else if (tile.getText() == 'i') { // is
				return "https://i.imgur.com/3RkNnHP.png)";
			} else if (tile.getText() == 'y') { // you
				return "https://i.imgur.com/HZClbQU.png)";
			} else if (tile.getText() == 'v') { // win
				return "https://i.imgur.com/cttkVje.png)";
			} else if (tile.getText() == 'p') { // push
				return "https://i.imgur.com/yfBernZ.png)";
			} else if (tile.getText() == 's') { // stop
				return "https://i.imgur.com/NykHztb.png)";
			} else {
				return "https://i.imgur.com/uaI2jy8.png)";
			}
		} else {
			return "https://i.imgur.com/uaI2jy8.png)"; // empty, green
		}
	}

	@FXML
	public void handleUp() {
		game.moveUp();
		drawBoard();
	}

	@FXML
	public void handleDown() {
		game.moveDown();
		drawBoard();
	}

	@FXML
	public void handleLeft() {
		game.moveLeft();
		drawBoard();
	}

	@FXML
	public void handleRight() {
		game.moveRight();
		drawBoard();
	}

	@FXML
	public void handleReset() {
		if (board.getChildren().contains(loseText)) {
			board.getChildren().remove(loseText);
		}
		if (board.getChildren().contains(winText)) {
			board.getChildren().remove(winText);
		}
		if (board.getChildren().contains(bonkText)) {
			board.getChildren().remove(bonkText);
		}
		setInitialGameState();
		drawBoard();
	}

	@FXML
	public void handleSave() {
		babaFile.save(game.toString(), saveFile); // board is saved to file
	}

	@FXML
	public void handleLoad() {
		char[][] board = babaFile.load(saveFile);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				char chara = board[y][x];
				if (!Character.isUpperCase(chara)) { // type = text
					game.getTile(x, y).setType('T', chara);
				} else if (Character.isUpperCase(chara)) {
					game.getTile(x, y).setType(chara); // type != text
				} // defaults to ' ' (empty) if chara !alpha
			}
		}
//		System.out.println(Arrays.deepToString(babaFile.load(saveFile))); // teste ka so loadast

		drawBoard();
	}

	@FXML
	public void handleBonk() {
		bonkText.setText("BONK");
		bonkText.setFont(Font.font("Comic sans MS", 120));
		bonkText.setFill(Color.BLACK);
		bonkText.setTranslateX(45.0);
		bonkText.setTranslateY(200.0);
		board.getChildren().add(bonkText);
	}
}
