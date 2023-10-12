module babagame {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	opens babagame to javafx.graphics, javafx.fxml;
}