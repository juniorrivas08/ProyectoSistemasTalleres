package business;

import java.util.ArrayList;
import java.util.Optional;

import data.ClientData;
import domain.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import presentation.GUINotify;

public class GUIClientController {
	
	@FXML
	private Button btnAdd;
	@FXML
	private TextField tfId;
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfPhone;
	@FXML
	private TextField tfMail;
	@FXML
	private TextField tfAddress;
	@FXML
	private TextField tfSearch;
	@FXML
	private Button btnReturn;
	@FXML
	private TableView<Client> tvClient;
	@FXML
	private TableColumn<Client, String> tcId;
	@FXML
	private TableColumn<Client, String> tcName;
	@FXML
	private TableColumn<Client, String> tcPhone;
	@FXML
	private TableColumn<Client, String> tcMail;
	@FXML
	private TableColumn<Client, String> tcAddress;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnDelete;
	
	private Client selectedClient;
	private boolean validateSearch;
	private boolean editClient;
	
	@FXML
	private void initialize() {
		initTableView();
		setDataTableView();
		validateSearch = false;
		editClient = false;
	}

	// Event Listener on Button[#btnAdd].onAction
	@FXML
	public void addClient(ActionEvent event) {
		
		if (editClient) {
			GUINotify.showAlert("No puede agregar un cliente mientras está editando otro", AlertType.WARNING);
			return;
		}
		
		String data = validateForm();

		if (!data.isEmpty()) {
			GUINotify.showAlert(data, AlertType.ERROR);
			return;
		}

		String id = tfId.getText();
		String name = tfName.getText();
		String phone = tfPhone.getText();
		String mail = tfMail.getText();
		String address = tfAddress.getText();

		Client client = new Client(id, name, phone, mail, address);

		if(ClientData.saveClient(client)){
			setDataTableView();
			clearForm();
			GUINotify.showAlert("Cliente guardado con éxito", AlertType.CONFIRMATION);
		}else {
			GUINotify.showAlert("Cliente con cédula ya registrada", AlertType.ERROR);
		}
	}

	// Event Listener on Button[#btnReturn].onAction
	@FXML
	public void returnHome(ActionEvent event) {
		if (editClient) {
			Optional<ButtonType> result = GUINotify.showConfirmation(
				"Está editando un cliente.\n¿Desea salir sin guardar los cambios?"
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
	public void editClient(ActionEvent event) {
		
		if(editClient) {
			String form = validateForm();
			if(!form.isEmpty()) {
				GUINotify.showAlert(form, AlertType.ERROR);
				return;
			}
			
			selectedClient.setAddress(tfAddress.getText());
			selectedClient.setMail(tfMail.getText());
			selectedClient.setPhone(tfPhone.getText());
			selectedClient.setName(tfName.getText());
			
			if(ClientData.editClient(selectedClient)){
				GUINotify.showAlert("Cliente editado con éxito", AlertType.CONFIRMATION);
				cancelEdit();
				setDataTableView();
				clearForm();
				return;
			}
		}
		
		selectedClient = tvClient.getSelectionModel().getSelectedItem();
		
		if(selectedClient == null) {
			GUINotify.showAlert("Debe seleccionar un cliente de la tabla para editarlo", AlertType.ERROR);
			return;
		}
		
		tfId.setText(selectedClient.getId());
		tfName.setText(selectedClient.getName());
		tfAddress.setText(selectedClient.getAddress());
		tfPhone.setText(selectedClient.getPhone());
		tfMail.setText(selectedClient.getMail());
		
		setEditMode(true);
		tvClient.getSelectionModel().clearSelection();
	}

	// Event Listener on Button[#btnSearch].onAction
	@FXML
	public void searchClient(ActionEvent event) {
		
		if(validateSearch) {
			setDataTableView();
			validateSearch = false;
			btnSearch.setText("Buscar");
			tfSearch.clear();
			return;
		}
		
		if(tfSearch.getText().isEmpty()) {
			GUINotify.showAlert("Ingrese la cédula del cliente a buscar", AlertType.ERROR);
			return;
		}
		
		Client foundClient = LogicClient.searchClient(ClientData.getList(), tfSearch.getText());
		
		if(foundClient == null) {
			GUINotify.showAlert("Cliente no encontrado", AlertType.ERROR);
			return;
		}
		
		ArrayList<Client> selectedClient = new ArrayList<Client>();
		selectedClient.add(foundClient);
		ObservableList<Client> observable = FXCollections.observableArrayList(selectedClient);
		tvClient.setItems(observable);
		validateSearch = true;
		btnSearch.setText("Mostrar todos");
	}

	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteClient(ActionEvent event) {
		
		if (editClient) {
			GUINotify.showAlert("No puede eliminar mientras está editando un cliente", AlertType.WARNING);
			return;
		}
		
		Client client = tvClient.getSelectionModel().getSelectedItem();
		
		if(client == null) {
			GUINotify.showAlert("Debe seleccionar un cliente de la tabla para eliminarlo", AlertType.ERROR);
			return;
		}
		
		Optional<ButtonType> result = GUINotify.showConfirmation(
			"¿Está seguro de eliminar al cliente '" + client.getName() + "'?\n\n" +
			"Esta acción no se puede deshacer."
		);
	
		if(result.isPresent() && result.get() == ButtonType.OK) {
			if(ClientData.deleteClient(client)) {
				GUINotify.showAlert("Cliente eliminado con éxito", AlertType.INFORMATION);
			} else {
				GUINotify.showAlert("Error al eliminar el cliente", AlertType.ERROR);
			}
		}
		
		setDataTableView();
		tvClient.getSelectionModel().clearSelection();
	}

	private String validateForm() {

		String data = "";

		if (tfId.getText().isEmpty()) {
			data += "El campo de la cédula se encuentra vacío\n";
		} else if (tfId.getText().trim().length() < 9) {
			data += "La cédula debe tener al menos 9 dígitos\n";
		}

		if (tfName.getText().isEmpty()) {
			data += "El campo del nombre se encuentra vacío\n";
		} else if (!tfName.getText().trim().contains(" ")) {
			data += "Debe ingresar el nombre completo\n";
		}

		if (tfMail.getText().isEmpty()) {
			data += "El campo del correo se encuentra vacío\n";
		} else if (!tfMail.getText().contains("@")) {
			data += "El correo debe llevar @ en su formato\n";
		}

		if (tfPhone.getText().isEmpty()) {
			data += "El campo del teléfono se encuentra vacío\n";
		} else if (tfPhone.getText().trim().length() < 8) {
			data += "El número de teléfono debe tener al menos 8 dígitos\n";
		}

		if (tfAddress.getText().isEmpty()) {
			data += "El campo de la dirección se encuentra vacío\n";
		} else if (tfAddress.getText().trim().length() < 20) {
			data += "La dirección debe tener al menos 20 caracteres\n";
		}

		return data;
	}

	private void initTableView() {

		this.tcId = new TableColumn<Client, String>("Cédula");
		this.tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		this.tcId.setPrefWidth(100);

		this.tcName = new TableColumn<Client, String>("Nombre");
		this.tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
		this.tcName.setPrefWidth(150);

		this.tcPhone = new TableColumn<Client, String>("Teléfono");
		this.tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		this.tcPhone.setPrefWidth(100);
		
		this.tcMail = new TableColumn<Client, String>("Correo Electrónico");
		this.tcMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
		this.tcMail.setPrefWidth(130);
		
		this.tcAddress = new TableColumn<Client, String>("Dirección");
		this.tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		this.tcAddress.setPrefWidth(130);

		this.tvClient.getColumns().addAll(this.tcId, this.tcName, this.tcPhone, this.tcMail, this.tcAddress);
	}

	private void setDataTableView() {
		ObservableList<Client> observable = FXCollections.observableArrayList(ClientData.getList());
		this.tvClient.setItems(observable);
	}
	
	private void clearForm() {
		tfId.setText("");
		tfName.setText("");
		tfPhone.setText("");
		tfMail.setText("");
		tfAddress.setText("");
		tfId.setEditable(true);
		tfId.setDisable(false);
		
		// Resetear búsqueda si estaba activa
		if (validateSearch) {
			validateSearch = false;
			btnSearch.setText("Buscar");
			tfSearch.clear();
		}
	}
	
	private void setEditMode(boolean isEditing) {
		editClient = isEditing;
		
		// Deshabilitar/habilitar campos y botones según el modo
		tfId.setEditable(!isEditing);
		tfId.setDisable(isEditing);
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