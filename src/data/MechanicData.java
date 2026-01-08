package data;

import java.util.ArrayList;

import domain.Mechanic;

public class MechanicData {
    private static final String FILE_PATH = "vehicle.json";
    private static final JsonUtils<Mechanic> jsonUtils = new JsonUtils<>(FILE_PATH);

    public MechanicData() {}

    public static boolean save(Mechanic mechanic) {
        try {
            jsonUtils.saveElement(mechanic);
            System.out.println("Vehiculo guardado exitosamente");
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar vehiculo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Mechanic> getList() {
        try {
            return (ArrayList<Mechanic>) jsonUtils.getAll(Mechanic.class);
        } catch (Exception e) {
            System.err.println("Error al recuperar lista de vehiculos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}