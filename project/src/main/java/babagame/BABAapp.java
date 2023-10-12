package babagame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BABAapp extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		Parent parent = FXMLLoader.load(getClass().getResource("/BABAishYOU.fxml"));
		stage.setTitle("BABA ish YOU");
		stage.setScene(new Scene(parent));
		stage.show();
	}

	public static void main(String[] args) {
		launch(BABAapp.class, args);
	}

}
