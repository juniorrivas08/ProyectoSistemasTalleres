module ProyectoSistemasTalleres {
	requires javafx.controls;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires com.fasterxml.jackson.databind;
	requires javafx.fxml;
	requires java.desktop;
	
	opens domain;
	opens business to javafx.graphics, javafx.fxml;
}
