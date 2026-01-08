package business;


import java.util.ArrayList;

import data.ClientData;
import domain.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
		
		String data = validateForm();

		if (!data.isEmpty()) {
			GUINotify.formData(data);
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
			GUINotify.successfullData("Cliente guardado con éxito");
		}
		
	}

	// Event Listener on Button[#btnBack].onAction
	@FXML
	public void returnHome(ActionEvent event) {
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
				editClient = false;
				btnEdit.setText("Editar");
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
		
		tfId.setEditable(false);
		tfId.setDisable(true);
		
		tvClient.getSelectionModel().clearSelection();
		editClient = true;
		btnEdit.setText("Guardar edicion");
		
	}

	// Event Listener on Button[#btnSearch].onAction
	@FXML
	public void searchClient(ActionEvent event) {
		
		if(validateSearch) {
			setDataTableView();
			validateSearch = !validateSearch;
			return;
		}
		
		if(tfId.getText().isEmpty()) {
			GUINotify.errorData("Ingrese la cedula del usuario a buscar: ");
			return;
		}
		
		ArrayList<Client> selectedClient = new ArrayList<Client>();
		selectedClient.add(LogicClient.searchClient(ClientData.getList(), tfId.getText()));
		ObservableList<Client> observable = FXCollections.observableArrayList(selectedClient);
		tvClient.setItems(observable);
		validateSearch = !validateSearch;
	}

	// Event Listener on Button[#btnDelete].onAction
	@FXML
	public void deleteClient(ActionEvent event) {
		Client client = tvClient.getSelectionModel().getSelectedItem();
		
		if(client == null) {
			GUINotify.errorData("Debe seleccionar un cliente de la tabla para eliminarlo");
			return;
		}else {
			if(GUINotify.validateAnswer("¿Está seguro de eliminar al cliente "+client.getName()+" del sistema?")) {
				ClientData.deleteClient(client);
			}
		}
		
		setDataTableView();
		tvClient.getSelectionModel().clearSelection();
		
	}

	private String validateForm() {

		String data = "";

		if (tfId.getText().isEmpty()) {
			data += "El campo de la cédula se encuentra vacío\n";
		}

		if (tfName.getText().isEmpty()) {
			data += "El campo del nombre se encuentra vacío\n";
		}

		if (!tfName.getText().contains(" ")) {
			data += "Debe ingresar el nombre completo\n";
		}

		if (tfMail.getText().isEmpty()) {
			data += "El campo del correo se encuentra vacío\n";
		}

		if (!tfMail.getText().contains("@")) {
			data += "El correo debe llevar @ en su formato\n";
		}

		if (tfPhone.getText().isEmpty()) {
			data += "El campo de la cédula se encuentra vacío\n";
		}

		if (tfPhone.getText().length() < 8) {
			data += "El número de teléfono debe tener al menos 8 dígitos\n";
		}

		if (tfAddress.getText().isEmpty()) {
			data += "El campo de la dirección se encuentra vacío\n";
		}

		if (tfAddress.getText().length() < 20) {
			data += "La dirección debe tener al menos 20 dígitos\n";
		}

		return data;

	}

	private void initTableView() {

		this.tcId = new TableColumn<Client, String>("Cédula");
		this.tcId.setCellValueFactory(new PropertyValueFactory<>("id"));

		this.tcName = new TableColumn<Client, String>("Nombre");
		this.tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

		this.tcMail = new TableColumn<Client, String>("Correo Eléctronico");
		this.tcMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
		
		this.tcPhone = new TableColumn<Client, String>("Número");
		this.tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		
		this.tcAddress = new TableColumn<Client, String>("Dirección");
		this.tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));


		this.tvClient.getColumns().addAll(this.tcId, this.tcName, this.tcPhone, this.tcMail, this.tcAddress);

	}

	private void setDataTableView() {

		ObservableList<Client> observable = FXCollections.observableArrayList(ClientData.getList());
		this.tvClient.setItems(observable);

		this.tvClient.setOnMouseClicked(e -> {

			Client client = this.tvClient.getSelectionModel().getSelectedItem();

			if (client != null) {
				
			}

		});

	}
	
	private void clearForm() {
		tfId.setText("");
		tfName.setText("");
		tfPhone.setText("");
		tfMail.setText("");
		tfAddress.setText("");
	}
	

}