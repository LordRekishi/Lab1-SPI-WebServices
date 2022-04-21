package se.iths.inventory;

import se.iths.inventory.entity.Part;
import se.iths.inventory.interfaces.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ElectricalAndIgnitionInventory implements Inventory {

    public static final String ELECTRICAL_AND_IGNITION_INVENTORY = "ELECTRICAL_AND_IGNITION";
    private final List<Part> parts;

    public ElectricalAndIgnitionInventory() {
        this.parts = new ArrayList<>(List.of(
                new Part(4L, "Spark plug Denso", 1, 3, 4, 50),
                new Part(5L, "Sensor cable", 1, 8, 1, 700),
                new Part(6L, "Spark plug wire", 1, 8, 1, 200)));
    }

    @Override
    public String getCategory() {
        return ELECTRICAL_AND_IGNITION_INVENTORY;
    }

    @Override
    public Part getPartByID(Long id) {
        return parts.stream()
                .filter(part -> part.getId().equals(id))
                .findFirst().orElse(null);
    }

    @Override
    public List<Part> getPartsByName(String name) {
        return parts.stream()
                .filter(part -> part.getName().contains(name))
                .toList();
    }

    @Override
    public List<Part> getPartsByPosition(Integer cabinet, Integer shelf) {
        return parts.stream()
                .filter(part -> part.getPosition().containsKey(cabinet) && part.getPosition().containsValue(shelf))
                .toList();
    }

    @Override
    public List<Part> getAll() {
        return parts;
    }
}
