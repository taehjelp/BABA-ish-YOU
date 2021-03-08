package babagame;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BABAcontroller {
//	YEA eg tek ""inspo"""" frå andre sitt arbeid what of it!!!!
	private BABAgame game;

	@FXML
	Pane board;

	@FXML
	Text winText = new Text();
	@FXML
	Text loseText = new Text();

	public void initialize() {
		setInitialGameState();
		createBoard();
		drawBoard();
	}

	private void setInitialGameState() {
		game = new BABAgame(14, 14);
//		BABA	
		game.getTile(9, 10).setType('B');
//		FLAG
		game.getTile(9, 3).setType('F');
		game.getTile(1, 7).setType('F');
//		ROCK
		game.getTile(0, 8).setType('R');
		game.getTile(1, 8).setType('R');
		game.getTile(2, 8).setType('R');
		game.getTile(3, 8).setType('R');
		game.getTile(4, 8).setType('R');
		game.getTile(5, 8).setType('R');
		game.getTile(6, 3).setType('R');
		game.getTile(6, 8).setType('R');
		game.getTile(7, 8).setType('R');
		game.getTile(8, 8).setType('R');
		game.getTile(10, 4).setType('R');
//		WALL  
		game.getTile(6, 2).setType('W');
		game.getTile(6, 4).setType('W');
		game.getTile(6, 5).setType('W');
		game.getTile(6, 6).setType('W');
		game.getTile(7, 2).setType('W');
		game.getTile(7, 6).setType('W');
		game.getTile(8, 2).setType('W');
		game.getTile(8, 6).setType('W');
		game.getTile(9, 2).setType('W');
		game.getTile(9, 6).setType('W');
		game.getTile(9, 7).setType('W');
		game.getTile(9, 8).setType('W');
		game.getTile(10, 2).setType('W');
		game.getTile(10, 8).setType('W');
		game.getTile(11, 2).setType('W');
		game.getTile(11, 8).setType('W');
		game.getTile(12, 2).setType('W');
		game.getTile(12, 3).setType('W');
		game.getTile(12, 4).setType('W');
		game.getTile(12, 5).setType('W');
		game.getTile(12, 6).setType('W');
		game.getTile(12, 7).setType('W');
		game.getTile(12, 8).setType('W');
// 		TEXT
		game.getTile(8, 11).setType('T', 'b');
		game.getTile(8, 5).setType('T', 'f');
		game.getTile(9, 1).setType('T', 'w');
		game.getTile(3, 11).setType('T', 'r');
		game.getTile(1, 1).setType('T', 't');
		game.getTile(1, 2).setType('T', 'i');
		game.getTile(4, 11).setType('T', 'i');
		game.getTile(9, 5).setType('T', 'i');
		game.getTile(9, 11).setType('T', 'i');
		game.getTile(10, 1).setType('T', 'i');
		game.getTile(10, 11).setType('T', 'y');
		game.getTile(10, 6).setType('T', 'v');
		game.getTile(5, 11).setType('T', 's');
		game.getTile(11, 1).setType('T', 's');
		game.getTile(1, 3).setType('T', 'p');
		game.getTile(6, 10).setType('T', 'p');

		game.updateRules();
	}

//	kopiert direkte frå f.eks:
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
//				board.getChildren().get(y * game.getWidth() + x).setStyle("-fx-background-color: " + getTilecolor(game.getTile(x, y)) + ";");
				board.getChildren().get(y*game.getWidth() + x).setStyle("-fx-background-image: url( " + getTileImage(game.getTile(x, y)) + ";");
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
//			loseText.setStyle("-fx-font-size: 40px");
			loseText.setFont(Font.font("Comic sans MS", 50));
			loseText.setFill(Color.RED);
			loseText.setTranslateX(25.0);
			loseText.setTranslateY(200.0);
			board.getChildren().add(loseText);
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
		setInitialGameState();
		drawBoard();
	}

   	private String getTileImage(BABAtile tile) {
    	if (tile.isBABA()) { 
    		return "https://i.imgur.com/09gR1P4.png)";
    	} 
    	else if(tile.isFLAG()) {
    		return "https://i.imgur.com/BUU86r1.png)";
    	} 
    	else if(tile.isROCK()) {
    		return "https://i.imgur.com/IOPBf9F.png)";
    	} 
    	else if(tile.isWALL()) {
    		return "https://i.imgur.com/ULUiNog.png)";
    	} 
    	else if(tile.isTEXT()) {
    		if (tile.getText() == 'b') { // baba
    			return "https://i.imgur.com/yR99xQd.png)";
    		}
    		else if (tile.getText() == 't') { // text
    			return "https://i.imgur.com/RSTsbBd.png)";
    		}
    		else if (tile.getText() == 'f') { // flag
    			return "https://i.imgur.com/X6fgDd1.png)";
    		}
    		else if (tile.getText() == 'r') { // rock
    			return "https://i.imgur.com/hC4seaY.png)";
    		}
    		else if (tile.getText() == 'w') { // wall
    			return "https://i.imgur.com/nntZQai.png)";
    		}
    		else if (tile.getText() == 'i') { // is
    			return "https://i.imgur.com/3RkNnHP.png)";
    		}
    		else if (tile.getText() == 'y') { // you
    			return "https://i.imgur.com/HZClbQU.png)";
    		}
    		else if (tile.getText() == 'v') { // win
    			return "https://i.imgur.com/cttkVje.png)";
    		}
    		else if (tile.getText() == 'p') { // push
    			return "https://i.imgur.com/yfBernZ.png)";
    		}
    		else if (tile.getText() == 's') { // stop
    			return "https://i.imgur.com/NykHztb.png)";
    		}
        	else {
        		return "https://i.imgur.com/uaI2jy8.png)";
        	}
    	} 
    	else {
    		return "https://i.imgur.com/uaI2jy8.png)";
    	}
    }

//	CODE is TRASH

//	private String getTilecolor(BABAtile tile) {
//		if (tile.isBABA()) {
//			return "#de9bb9";
//		} else if (tile.isFLAG()) {
//			return "#c7af52";
//		} else if (tile.isROCK()) {
//			return "#829a9e";
//		} else if (tile.isWALL()) {
//			return "#61564c";
//		} else if (tile.isTEXT()) {
//			if (tile.getText() == 'b') { // baba
//				return "#ad6a88";
//			} else if (tile.getText() == 't') { // text
//				return "#2e2e2e";
//			} else if (tile.getText() == 'f') { // flag
//				return "#99832e";
//			} else if (tile.getText() == 'r') { // rock
//				return "#485b5e";
//			} else if (tile.getText() == 'w') { // wall
//				return "#403730";
//			} else if (tile.getText() == 'i') { // is
//				return "#ffffff";
//			} else if (tile.getText() == 'y') { // you
//				return "#facfe2";
//			} else if (tile.getText() == 'v') { // win
//				return "#e8d692";
//			} else if (tile.getText() == 'p') { // push
//				return "#c0cfd1";
//			} else if (tile.getText() == 's') { // stop
//				return "#a89c91";
//			} else {
//				return "#c7e381";
//			}
//		}
//		else {
//			return "#c7e381";
//		}
//	}

}
