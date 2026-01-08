module ProyectoSistemasTalleres {
	requires javafx.controls;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires com.fasterxml.jackson.databind;
	requires java.desktop;
	requires javafx.fxml;
	
	
	opens business to javafx.graphics, javafx.fxml;
}
