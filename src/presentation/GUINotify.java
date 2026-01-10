package presentation;

import java.util.Optional;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class GUINotify {
	
	 public static Optional<ButtonType> showConfirmation(String message) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Confirmación");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        return alert.showAndWait();
	    }
	
	public static void showAlert(String message, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("Validación");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
}