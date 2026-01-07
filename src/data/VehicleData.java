package data;

import java.util.ArrayList;
import domain.Vehicle;

public class VehicleData {
    private static final String FILE_PATH = "vehicle.json";
    private static final JsonUtils<Vehicle> jsonUtils = new JsonUtils<>(FILE_PATH);

    public VehicleData() {}

    public static boolean save(Vehicle vehicle) {
        try {
            jsonUtils.saveElement(vehicle, Vehicle.class);
            System.out.println("Vehículo guardado exitosamente");
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar vehículo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Vehicle> getList() {
        try {
            return (ArrayList<Vehicle>) jsonUtils.getAll(Vehicle.class);
        } catch (Exception e) {
            System.err.println("Error al recuperar lista de vehículos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}