package babagame;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BABAcontroller {
	private BABAgame game;
	private int height;
	private int width;
	private BABAfile babaFile = new BABAfile();
	String rootPath = new File("").getAbsolutePath(); 
	String filePath = rootPath + "/src/main/java/babagame/saveFile.txt";
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
		drawBoard();
	}

	private void setInitialGameState() {
		height = 14;
		width = 14;
		game = new BABAgame(height, width);
		
//		// testboard
//		char board[][] =   {{'B','B',' ','B','B','W',' ',' ',' ',' ',' ',' ',' ',' '},// 0   // 1 ned: bryte biy (isLOSE=true) (og rip), laga rif
//							{' ',' ','R','b','i','y',' ',' ',' ',' ',' ',' ',' ',' '},// 1   // 1 høgre: teste BB' ' vs BBW, teste B mot Out Of Bounds
//							{'F','B',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},// 2   // 1 venstre: B skubbe R, B til F => isWIN=true
//							{' ','R','B',' ',' ','B',' ',' ',' ',' ',' ',' ',' ',' '},// 3   // 2 opp: bryte rip, B går over R
//							{' ',' ',' ',' ',' ','f',' ',' ',' ',' ',' ',' ',' ',' '},// 4  
//							{'f','i','v','r','i','p',' ',' ',' ',' ',' ',' ',' ',' '},// 5
//							{'w','i','s','B',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
//							{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
//							{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
//							{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
//							{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
//							{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
//							{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
//							{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '}};

		char[][] board =   {{ ' ', ' ', ' ', ' ', 'R', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
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
							{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }};

		for (int y = 0; y < height; y++) { // sets the type of each tile according to board[][]
			for (int x = 0; x < width; x++) {
				char chara = board[y][x];
				if (Character.isLowerCase(chara)) { // text
					game.getTile(x, y).setType('T', chara);
				} else if (Character.isUpperCase(chara)) {
					game.getTile(x, y).setType(chara); // type != text
				} // tile defaults to ' ' (EMPTY) if chara != alpha
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
		for (int y = 0; y < game.getHeight(); y++) { // fills each tile with an image according to type
			for (int x = 0; x < game.getWidth(); x++) {
				board.getChildren().get(y * game.getWidth() + x)
						.setStyle("-fx-background-image: url(" + getTileImage(game.getTile(x, y)) + ");");
			}
		}
		if (game.isWIN() && !board.getChildren().contains(winText)) { // displays win/lose-text if applicable
			winText.setText("YOU is WIN!");                           // and text is not already there
			winText.setFont(Font.font("Comic sans MS", 50));
			winText.setFill(Color.GREEN);
			winText.setTranslateX(45.0);
			winText.setTranslateY(200.0);
			board.getChildren().add(winText);
		} else if (game.isLOSE() && !board.getChildren().contains(loseText)) {
			loseText.setText("YOU is CRINGE");
			loseText.setFont(Font.font("Comic sans MS", 50));
			loseText.setFill(Color.RED);
			loseText.setTranslateX(25.0);
			loseText.setTranslateY(200.0);
			board.getChildren().add(loseText);
		}
	}
	
	private String getTileImage(BABAtile tile) { // URLs are image links for each tile type
		if (tile.isBABA())
			return "https://i.imgur.com/09gR1P4.png";
		else if (tile.isFLAG())
			return "https://i.imgur.com/BUU86r1.png";
		else if (tile.isROCK())
			return "https://i.imgur.com/IOPBf9F.png";
		else if (tile.isWALL())
			return "https://i.imgur.com/ULUiNog.png";
		else if (tile.isTEXT()) { // text types:
			if (tile.getText() == 'b') // baba
				return "https://i.imgur.com/yR99xQd.png";
			else if (tile.getText() == 't') // text
				return "https://i.imgur.com/RSTsbBd.png";
			else if (tile.getText() == 'f') // flag
				return "https://i.imgur.com/X6fgDd1.png";
			else if (tile.getText() == 'r') // rock
				return "https://i.imgur.com/hC4seaY.png";
			else if (tile.getText() == 'w') // wall
				return "https://i.imgur.com/nntZQai.png";
			else if (tile.getText() == 'i') // is
				return "https://i.imgur.com/3RkNnHP.png";
			else if (tile.getText() == 'y') // you
				return "https://i.imgur.com/HZClbQU.png";
			else if (tile.getText() == 'v') // win
				return "https://i.imgur.com/cttkVje.png";
			else if (tile.getText() == 'p') // push
				return "https://i.imgur.com/yfBernZ.png";
			else if (tile.getText() == 's') // stop
				return "https://i.imgur.com/NykHztb.png";
			else
				return "https://i.imgur.com/uaI2jy8.png";
		}
		else 
			return "https://i.imgur.com/uaI2jy8.png"; // empty, green
	}
	
	public void clearText() { 
		if (board.getChildren().contains(loseText)) {
			board.getChildren().remove(loseText);
		}
		else if (board.getChildren().contains(winText)) {
			board.getChildren().remove(winText);
		}
		if (board.getChildren().contains(bonkText)) {
			board.getChildren().remove(bonkText);
		}
	}
	
	public void errorText() { // adds temporary error-message if loading went wrong
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
		if (board.getChildren().contains(errorText)) {
			board.getChildren().remove(errorText);
		}
		game.moveUp();
		drawBoard();
	}

	@FXML
	public void handleDown() {
		if (board.getChildren().contains(errorText)) {
			board.getChildren().remove(errorText);
		}
		game.moveDown();
		drawBoard();
	}

	@FXML
	public void handleLeft() {
		if (board.getChildren().contains(errorText)) {
			board.getChildren().remove(errorText);
		}
		game.moveLeft();
		drawBoard();
	}

	@FXML
	public void handleRight() {
		if (board.getChildren().contains(errorText)) {
			board.getChildren().remove(errorText);
		}
		game.moveRight();
		drawBoard();
	}

	@FXML // resets board
	public void handleReset() { 
		clearText();
		setInitialGameState();
		drawBoard();
	}

	@FXML // saves board to file
	public void handleSave() {
		babaFile.save(game.toString(), saveFile); // board is saved to file
	}

	@FXML // loads board from file
	public void handleLoad() {
		clearText();
		char[][] board = babaFile.load(saveFile, height, width);
		if (board == null) {
			errorText();
		}
		else {
			game = new BABAgame(height, width);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					char chara = board[y][x];
					if (Character.isLowerCase(chara)) { // type = text
						game.getTile(x, y).setType('T', chara);
					} else if (Character.isUpperCase(chara)) {
						game.getTile(x, y).setType(chara); // type != text
					} else {
						game.getTile(x, y).setType(' ');
					}
				}
			}
		}
		drawBoard();
	}

	@FXML // literally just displays a big BONK. a necessary feature.
	public void handleBonk() {
		if (!board.getChildren().contains(bonkText)) {
			bonkText.setText("BONK");
			bonkText.setFont(Font.font("Comic sans MS", 120));
			bonkText.setFill(Color.BLACK);
			bonkText.setTranslateX(45.0);
			bonkText.setTranslateY(200.0);
			board.getChildren().add(bonkText);
		}
		else {
			board.getChildren().remove(bonkText); // removes bonkText if it's already there
		}

	}
}
