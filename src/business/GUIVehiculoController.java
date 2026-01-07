package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import data.VehicleData;

import domain.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GUIVehiculoController {
    @FXML
    private Button btnagregarVehiculo;
    @FXML
    private Button btnEditarVehiculo;
    @FXML
    private Button btnBuscarVehiculo;
    @FXML
    private Button btnEliminarVehiculo;
    @FXML
    private ComboBox<String> cbxCombustible;
    @FXML
    private TextField tfPlaca;
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAnio;
    @FXML
    private TableView<Vehicle> tvListaVehiculo;
    @FXML
    private TextField tfsearch;
    @FXML
    private Button btnReturnVehiculo;
    
    
    private TableColumn<Vehicle, String> tcPlaca;
	private TableColumn<Vehicle, String> tcModelo;
	private TableColumn<Vehicle, String> tcMarca;
	
	private TableColumn<Vehicle, Integer> tcAnio;
	
	private TableColumn<Vehicle, String> tcCombustible;
	
	 private Vehicle selectedVehicle;
	

    @FXML
    private void initialize() {
        cbxCombustible.getItems().addAll("Gasolina", "Diesel", "Electrico", "Hibrido");
        initTableView();
		setDataTableView();
		
		setupTableSelectionListener();
    }
    
    
    
    
    private void setupTableSelectionListener() {
        tvListaVehiculo.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    showVehicleInForm(newValue);
                }
            }
        );
    }

    private void showVehicleInForm(Vehicle vehicle) {
        selectedVehicle = vehicle;
        
        tfPlaca.setText(vehicle.getPlate());
        tfMarca.setText(vehicle.getBrand());
        tfModelo.setText(vehicle.getModel());
        tfAnio.setText(String.valueOf(vehicle.getYear()));
        cbxCombustible.setValue(vehicle.getFuelType());
        
        tfPlaca.setDisable(true);
    }
    
    
    
    
    
    private void setDataTableView() {
		ObservableList<Vehicle> observable = 
				FXCollections.observableArrayList(VehicleData.getList());
		this.tvListaVehiculo.setItems(observable);
		
		this.tvListaVehiculo.setOnMouseClicked(e->{
			Vehicle vehicle = this.tvListaVehiculo.getSelectionModel()
					.getSelectedItem();
			
		});
		
	}
    
    private void initTableView() {
		this.tcPlaca = new TableColumn<Vehicle, String>(" Placa ");
		this.tcPlaca.setCellValueFactory(new PropertyValueFactory<>("plate"));
	
		this.tcMarca = new TableColumn<Vehicle, String>(" Marca ");
		this.tcMarca.setCellValueFactory(new PropertyValueFactory<>("brand"));
		
		this.tcModelo= new TableColumn<Vehicle, String>(" Modelo ");
		this.tcModelo.setCellValueFactory(new PropertyValueFactory<>("model"));
		
		this.tcAnio= new TableColumn<Vehicle, Integer>(" Año ");
		this.tcAnio.setCellValueFactory(new PropertyValueFactory<>("year"));
		
		this.tcCombustible= new TableColumn<Vehicle, String>(" Combustible ");
		this.tcCombustible.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
		
		this.tvListaVehiculo.getColumns().addAll(
				this.tcPlaca,
				this.tcMarca,
				this.tcModelo,
				this.tcAnio,
				this.tcCombustible
				);
		
		
	}


    private boolean validateEmptyFields() {
        if (tfPlaca.getText().trim().isEmpty()) {
            showAlert("La placa es obligatoria", AlertType.WARNING);
            tfPlaca.requestFocus();
            return false;
        }
        
        if (tfMarca.getText().trim().isEmpty()) {
            showAlert("La marca es obligatoria", AlertType.WARNING);
            tfMarca.requestFocus();
            return false;
        }
        
        if (tfModelo.getText().trim().isEmpty()) {
            showAlert("El modelo es obligatorio", AlertType.WARNING);
            tfModelo.requestFocus();
            return false;
        }
        
        if (tfAnio.getText().trim().isEmpty()) {
            showAlert("El anio es obligatorio", AlertType.WARNING);
            tfAnio.requestFocus();
            return false;
        }
        
        if (cbxCombustible.getValue() == null || cbxCombustible.getValue().isEmpty()) {
            showAlert("Debe seleccionar un tipo de combustible", AlertType.WARNING);
            cbxCombustible.requestFocus();
            return false;
        }
        
        return true;
    }
   
    private boolean validateYear() {
        String year = tfAnio.getText().trim();
        
        if (!year.matches("\\d+")) {
            showAlert("El anio debe contener solo numeros", AlertType.WARNING);
            tfAnio.requestFocus();
            return false;
        }
        
        
        if (year.length() != 4) {
            showAlert("El anio debe tener exactamente 4 digitos", AlertType.WARNING);
            tfAnio.requestFocus();
            return false;
        }
        
        int yearValue = Integer.parseInt(year);
        int currentYear = java.time.Year.now().getValue();
        
        if (yearValue < 1885) {
            showAlert("El año no puede ser menor a 1885", AlertType.WARNING);
            tfAnio.requestFocus();
            return false;
        }
        
        
        
        if (yearValue > currentYear + 1) {
            showAlert("El año no puede ser mayor a " + (currentYear + 1), AlertType.WARNING);
            tfAnio.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private boolean validateDuplicatePlate(String plate) {
        ArrayList<Vehicle> vehicles = VehicleData.getList();
        
        for (Vehicle v : vehicles) {
            if (v.getPlate().trim().equalsIgnoreCase(plate.trim())) {
                showAlert("La placa '" + plate + "' ya está registrada", AlertType.WARNING);
                tfPlaca.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    
    private boolean validateAllFields() {
       
        if (!validateEmptyFields()) {
            return false;
        }
        
        if (!validateYear()) {
            return false;
        }
        
        if (!validateDuplicatePlate(tfPlaca.getText().trim())) {
            return false;
        }
        
        return true;
    }

    @FXML
    public void addVehiculo(ActionEvent event) {
        try {
        
            if (!validateAllFields()) {
                return;
            }

            Vehicle vehicle = new Vehicle();
            vehicle.setPlate(tfPlaca.getText().trim());
            vehicle.setBrand(tfMarca.getText().trim());
            vehicle.setModel(tfModelo.getText().trim());
            vehicle.setYear(Integer.parseInt(tfAnio.getText().trim()));
            vehicle.setFuelType(cbxCombustible.getValue());

            boolean saved = VehicleData.save(vehicle);
            
            if (saved) {
            	setDataTableView();
                clearForm();
                showAlert("Vehiculo registrado correctamente", AlertType.INFORMATION);
            } else {
                showAlert("Error al guardar el vehiculo", AlertType.ERROR);
            }
            
        } catch (Exception e) {
            showAlert("Error inesperado: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

   
    private void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Validación");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

   
    private void clearForm() {
        tfPlaca.clear();
        tfModelo.clear();
        tfAnio.clear();
        tfMarca.clear();
        cbxCombustible.setValue(null);
        
        
        tvListaVehiculo.getSelectionModel().clearSelection();
        selectedVehicle = null;
        
        tfPlaca.setDisable(false);
    }
    
    
    private boolean validateFieldsForEdit() {
        if (!validateEmptyFields()) {
            return false;
        }
        
        if (!validateYear()) {
            return false;
        }
        
        return true;
    }
    
    @FXML
    public void editVehiculo(ActionEvent event) {
        try {
            if (selectedVehicle == null) {
                showAlert("Debe seleccionar un vehiculo de la tabla para editarlo", AlertType.WARNING);
                return;
            }
            
            if (!validateFieldsForEdit()) {
                return;
            }
            
            Optional<ButtonType> result = showConfirmation(
                "¿Esta seguro que desea editar el vehiculo con placa '" + selectedVehicle.getPlate() + "'?"
            );
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ArrayList<Vehicle> vehicles = VehicleData.getList();
                boolean found = false;
                
                for (int i = 0; i < vehicles.size(); i++) {
                    if (vehicles.get(i).getPlate().equalsIgnoreCase(selectedVehicle.getPlate())) {
                        vehicles.get(i).setBrand(tfMarca.getText().trim());
                        vehicles.get(i).setModel(tfModelo.getText().trim());
                        vehicles.get(i).setYear(Integer.parseInt(tfAnio.getText().trim()));
                        vehicles.get(i).setFuelType(cbxCombustible.getValue());
                        found = true;
                        break;
                    }
                }
                
                if (found) {
                    boolean saved = saveAllVehicles(vehicles);
                    
                    if (saved) {
                        setDataTableView();
                        clearForm();
                        showAlert("Vehiculo editado correctamente", AlertType.INFORMATION);
                    } else {
                        showAlert("Error al editar el vehiculo", AlertType.ERROR);
                    }
                } else {
                    showAlert("No se encontro el vehiculo seleccionado", AlertType.ERROR);
                }
            }
            
        } catch (Exception e) {
            showAlert("Error inesperado: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void searchVehiculo(ActionEvent event) {
        String searchTerm = tfsearch.getText().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            setDataTableView();
            return;
        }
        
        ArrayList<Vehicle> allVehicles = VehicleData.getList();
        ArrayList<Vehicle> filteredVehicles = new ArrayList<>();
        
        for (Vehicle v : allVehicles) {
            if (v.getPlate().toLowerCase().contains(searchTerm) ||
                v.getBrand().toLowerCase().contains(searchTerm) ||
                v.getModel().toLowerCase().contains(searchTerm)) {
                filteredVehicles.add(v);
            }
        }
        
        if (filteredVehicles.isEmpty()) {
            showAlert("No se encontraron vehiculos con el termino: " + searchTerm, AlertType.INFORMATION);
            setDataTableView();
        } else {
            ObservableList<Vehicle> observable = FXCollections.observableArrayList(filteredVehicles);
            tvListaVehiculo.setItems(observable);
            this.tfsearch.clear();
        }
    }
    @FXML
    public void deleteVehiculo(ActionEvent event) {
        try {
            if (selectedVehicle == null) {
                showAlert("Debe seleccionar un vehculo de la tabla para eliminarlo", AlertType.WARNING);
                return;
            }
            
            // Confirmar la eliminación
            Optional<ButtonType> result = showConfirmation(
                "¿Esta seguro que desea eliminar el vehiculo con placa " + selectedVehicle.getPlate() + "?\n" +
                "Esta accion no se puede deshacer."
            );
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ArrayList<Vehicle> vehicles = VehicleData.getList();
                boolean removed = false;
                
                for (int i = 0; i < vehicles.size(); i++) {
                    if (vehicles.get(i).getPlate().equalsIgnoreCase(selectedVehicle.getPlate())) {
                        vehicles.remove(i);
                        removed = true;
                        break;
                    }
                }
                
                if (removed) {
                    boolean saved = saveAllVehicles(vehicles);
                    
                    if (saved) {
                        setDataTableView();
                        clearForm();
                        showAlert("Vehiculo eliminado correctamente", AlertType.INFORMATION);
                    } else {
                        showAlert("Error al eliminar el vehiculo", AlertType.ERROR);
                    }
                } else {
                    showAlert("No se encontro el vehículo seleccionado", AlertType.ERROR);
                }
            }
            
        } catch (Exception e) {
            showAlert("Error inesperado: " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private boolean saveAllVehicles(ArrayList<Vehicle> vehicles) {
        try {
            data.JsonUtils<Vehicle> jsonUtils = new data.JsonUtils<>("vehicle.json");
            jsonUtils.saveAll(vehicles);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar la lista: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private Optional<ButtonType> showConfirmation(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    @FXML
    public void btnReturn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/GUIMain.fxml"));
        Parent root = loader.load();
        Scene nuevaEscena = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(nuevaEscena);
        stage.show();
    }
}