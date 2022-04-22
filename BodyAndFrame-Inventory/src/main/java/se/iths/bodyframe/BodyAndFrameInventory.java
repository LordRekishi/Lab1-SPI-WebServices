package se.iths.bodyframe;

import se.iths.inventory.entity.Part;
import se.iths.inventory.interfaces.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BodyAndFrameInventory implements Inventory {

    public static final String BODY_AND_FRAME_INVENTORY = "BODY AND FRAME";
    private final List<Part> parts;

    public BodyAndFrameInventory() {
        this.parts = new ArrayList<>(List.of(
                new Part(1L, "Handlebar", 1, 0, 3, 500),
                new Part(2L, "Handlebar lever kit", 1, 1, 1, 800),
                new Part(3L, "Shifter peg", 1, 2, 1, 100)));
    }

    @Override
    public String getCategory() {
        return BODY_AND_FRAME_INVENTORY;
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
