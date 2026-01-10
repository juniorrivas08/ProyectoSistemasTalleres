package business;

import java.util.ArrayList;
import java.util.Optional;

import data.ServiceData;
import domain.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.GUINotify;

public class GUIServiceController {
	
	@FXML
	private Button btnAdd;
	@FXML
	private TextField tfCode;
	@FXML
	private TextField tfDescription;
	@FXML
	private TextField tfNameService;
	@FXML
	private TextField tfCost;
	@FXML
	private TextField tfSearch;
	@FXML
	private Button btnReturn;
	@FXML
	private TableView<Service> tvService;
	@FXML
	private TableColumn<Service, Integer> tcCode;
	@FXML
	private TableColumn<Service, String> tcNameService;
	@FXML
	private TableColumn<Service, String> tcDescription;
	@FXML
	private TableColumn<Service, Double> tcBaseCost;
	@FXML
	private TableColumn<Service, Integer> tcHours;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnDelete;
	@FXML
	private Spinner<Integer> spHours;
	
	private Service selectedService;
	private boolean validateSearch;
	private boolean editService;
	
	@FXML
	public void initialize() {
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1, 1);
		spHours.setValueFactory(valueFactory);
		spHours.setEditable(true);
		
		initTableView();
		setDataTableView();
		validateSearch = false;
		editService = false;
	}
	
	// Event Listener on Button[#btnAdd].onAction
	@FXML
	public void addService(ActionEvent event) {
		
		if (editService) {
			GUINotify.showAlert("No puede agregar un servicio mientras está editando otro", AlertType.WARNING);
			return;
		}
		
		String data = validateForm();

		if (!data.isEmpty()) {
			GUINotify.showAlert(data, AlertType.ERROR);
			return;
		}

		int code = Integer.parseInt(tfCode.getText());
		String nameService = tfNameService.getText();
		String description = tfDescription.getText();
		double baseCost = Double.parseDouble(tfCost.getText());
		int hours = spHours.getValue();

		Service service = new Service(code, nameService, description, baseCost, hours);

		if(ServiceData.saveService(service)){
			setDataTableView();
			clearForm();
			GUINotify.showAlert("Servicio guardado con éxito", AlertType.CONFIRMATION);
		}else {
			GUINotify.showAlert("Servicio con código ya registrado", AlertType.ERROR);
		}
	}
	
	// Event Listener on Button[#btnReturn].onAction
	@FXML
	public void returnHome(ActionEvent event) {
		if (editService) {
			Optional<ButtonType> result = GUINotify.showConfirmation(
				"Está editando un servicio.\n¿Desea salir sin guardar los cambios?"
			);
			if (!result.isPresent() || result.get() != ButtonType.OK) {
				return;
			}
		}
		MyUtils myUtils = new MyUtils();
		myUtils.changeView(btnReturn, "/presentation/GUIMain.fxml", null);
	}
	
	// Event Listener on Button[#btnEdit].onAction
	@FXML
	public void editService(ActionEvent event) {
		
		if(editService) {
			String form = validateForm();
			if(!form.isEmpty()) {
				GUINotify.showAlert(form, AlertType.ERROR);
				return;
			}
			
			selectedService.setNameService(tfNameService.getText());
			selectedService.setDescription(tfDescription.getText());
			selectedService.setBaseCost(Double.parseDouble(tfCost.getText()));
			selectedService.setHours(spHours.getValue());
			
			if(ServiceData.editService(selectedService)){
				GUINotify.showAlert("Servicio editado con éxito", AlertType.CONFIRMATION);
				cancelEdit();
				setDataTableView();
				clearForm();
				return;
			}
		}
		
		selectedService = tvService.getSelectionModel().getSelectedItem();
		
		if(selectedService == null) {
			GUINotify.showAlert("Debe seleccionar un servicio de la tabla para editarlo", AlertType.ERROR);
			return;
		}
		
		tfCode.setText(String.valueOf(selectedService.getCode()));
		tfNameService.setText(selectedService.getNameService());
		tfDescription.setText(selectedService.getDescription());
		tfCost.setText(String.valueOf(selectedService.getBaseCost()));
		spHours.getValueFactory().setValue(selectedService.getHours());
		
		setEditMode(true);
		tvService.getSelectionModel().clearSelection();
	}
	
	// Event Listener on Button[#btnSearch].onAction
	@FXML
	public void searchService(ActionEvent event) {
		
		if(validateSearch) {
			setDataTableView();
			validateSearch = false;
			btnSearch.setText("Buscar");
			tfSearch.clear();
			return;
		}
		
		if(tfSearch.getText().isEmpty()) {
			GUINotify.showAlert("Ingrese el código del servicio a buscar", AlertType.ERROR);
			return;
		}
		
		int code;
		try {
			code = Integer.parseInt(tfSearch.getText());
		} catch (NumberFormatException e) {
			GUINotify.showAlert("El código debe ser un número válido", AlertType.ERROR);
			return;
		}
		
		Service foundService = LogicService.searchService(ServiceData.getList(), code);
		
		if(foundService == null) {
			GUINotify.showAlert("Servicio no encontrado", AlertType.ERROR);
			return;
		}
		
		ArrayList<Service> selectedService = new ArrayList<Service>();
		selectedService.add(foundService);
		ObservableList<Service> observable = FXCollections.observableArrayList(selectedService);
		tvService.setItems(observable);
		validateSearch = true;
		btnSearch.setText("Mostrar todos");
	}
	
	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteService(ActionEvent event) {
		
		if (editService) {
			GUINotify.showAlert("No puede eliminar mientras está editando un servicio", AlertType.WARNING);
			return;
		}
		
		Service service = tvService.getSelectionModel().getSelectedItem();
		
		if(service == null) {
			GUINotify.showAlert("Debe seleccionar un servicio de la tabla para eliminarlo", AlertType.ERROR);
			return;
		}
		
		Optional<ButtonType> result = GUINotify.showConfirmation(
			"¿Está seguro de eliminar el servicio '" + service.getNameService() + "'?\n\n" +
			"Esta acción no se puede deshacer."
		);
	
		if(result.isPresent() && result.get() == ButtonType.OK) {
			if(ServiceData.deleteService(service)) {
				GUINotify.showAlert("Servicio eliminado con éxito", AlertType.INFORMATION);
			} else {
				GUINotify.showAlert("Error al eliminar el servicio", AlertType.ERROR);
			}
		}
		
		setDataTableView();
		tvService.getSelectionModel().clearSelection();
	}
	
	private String validateForm() {

		String data = "";

		if (tfCode.getText().isEmpty()) {
			data += "El campo del código se encuentra vacío\n";
		} else {
			try {
				int code = Integer.parseInt(tfCode.getText());
				if(code <= 0) {
					data += "El código debe ser un número positivo\n";
				}
			} catch (NumberFormatException e) {
				data += "El código debe ser un número válido\n";
			}
		}

		if (tfNameService.getText().isEmpty()) {
			data += "El campo del nombre del servicio se encuentra vacío\n";
		} else if (tfNameService.getText().trim().length() < 3) {
			data += "El nombre del servicio debe tener al menos 3 caracteres\n";
		}

		if (tfDescription.getText().isEmpty()) {
			data += "El campo de la descripción se encuentra vacío\n";
		} else if (tfDescription.getText().trim().length() < 10) {
			data += "La descripción debe tener al menos 10 caracteres\n";
		}

		if (tfCost.getText().isEmpty()) {
			data += "El campo del costo se encuentra vacío\n";
		} else {
			try {
				double cost = Double.parseDouble(tfCost.getText());
				if(cost <= 0) {
					data += "El costo debe ser mayor a 0\n";
				}
			} catch (NumberFormatException e) {
				data += "El costo debe ser un número válido\n";
			}
		}

		if (spHours.getValue() == null || spHours.getValue() <= 0) {
			data += "Las horas deben ser mayores a 0\n";
		}

		return data;
	}

	private void initTableView() {

		this.tcCode = new TableColumn<Service, Integer>("Código");
		this.tcCode.setCellValueFactory(new PropertyValueFactory<>("code"));
		this.tcCode.setPrefWidth(80);

		this.tcNameService = new TableColumn<Service, String>("Nombre");
		this.tcNameService.setCellValueFactory(new PropertyValueFactory<>("nameService"));
		this.tcNameService.setPrefWidth(150);

		this.tcDescription = new TableColumn<Service, String>("Descripción");
		this.tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		this.tcDescription.setPrefWidth(200);
		
		this.tcBaseCost = new TableColumn<Service, Double>("Costo Base");
		this.tcBaseCost.setCellValueFactory(new PropertyValueFactory<>("baseCost"));
		this.tcBaseCost.setPrefWidth(90);
		
		this.tcHours = new TableColumn<Service, Integer>("Horas");
		this.tcHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
		this.tcHours.setPrefWidth(70);

		this.tvService.getColumns().addAll(this.tcCode, this.tcNameService, this.tcDescription, this.tcBaseCost, this.tcHours);
	}

	private void setDataTableView() {
		ObservableList<Service> observable = FXCollections.observableArrayList(ServiceData.getList());
		this.tvService.setItems(observable);
	}
	
	private void clearForm() {
		tfCode.setText("");
		tfNameService.setText("");
		tfDescription.setText("");
		tfCost.setText("");
		tfCode.setEditable(true);
		tfCode.setDisable(false);
		spHours.getValueFactory().setValue(1);
		
		// Resetear búsqueda si estaba activa
		if (validateSearch) {
			validateSearch = false;
			btnSearch.setText("Buscar");
			tfSearch.clear();
		}
	}
	
	private void setEditMode(boolean isEditing) {
		editService = isEditing;
		
		// Deshabilitar/habilitar campos y botones según el modo
		tfCode.setEditable(!isEditing);
		tfCode.setDisable(isEditing);
		btnAdd.setDisable(isEditing);
		btnSearch.setDisable(isEditing);
		btnDelete.setDisable(isEditing);
		tfSearch.setDisable(isEditing);
		
		btnEdit.setText(isEditing ? "Guardar edición" : "Editar");
	}
	
	private void cancelEdit() {
		setEditMode(false);
	}
}