package se.iths.inventory.service;

import se.iths.inventory.entity.Part;
import se.iths.inventory.interfaces.Inventory;

import java.util.*;

public class InventoryService {

    private static InventoryService inventoryService;
    private final ServiceLoader<Inventory> loader;

    public InventoryService() {
        this.loader = ServiceLoader.load(Inventory.class);
    }

    public static synchronized InventoryService getInstance() {
        if (inventoryService == null) {
            inventoryService = new InventoryService();
        }
        return inventoryService;
    }

    public void refresh() {
        loader.reload();
    }

    public Optional<Part> getPartByID(Long id) {
        Part part = null;

        for (Inventory inventory : loader) {
            part = inventory.getPartByID(id);
        }

        return Optional.ofNullable(part);
    }

    public Optional<Part> getPartByID(Long id, String category) {
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(inventory -> inventory.getCategory().equals(category))
                .map(inventory -> inventory.getPartByID(id))
                .findFirst();
    }

    public Optional<List<Part>> getPartByName(String name) {
        List<Part> parts = new ArrayList<>();

        for (Inventory inventory : loader) {
            parts = inventory.getPartsByName(name);
        }

        return Optional.ofNullable(parts);
    }

    public Optional<List<Part>> getPartByName(String name, String category) {
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(inventory -> inventory.getCategory().equals(category))
                .map(inventory -> inventory.getPartsByName(name))
                .findFirst();
    }

    public Optional<List<Part>> getPartByPosition(Integer cabinet, Integer shelf) {
        List<Part> parts = new ArrayList<>();

        for (Inventory inventory : loader) {
            parts = inventory.getPartsByPosition(cabinet, shelf);
        }

        return Optional.ofNullable(parts);
    }

    public Optional<List<Part>> getPartByPosition(Integer cabinet, Integer shelf, String category) {
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(inventory -> inventory.getCategory().equals(category))
                .map(inventory -> inventory.getPartsByPosition(cabinet, shelf))
                .findFirst();
    }

    public Optional<List<Part>> getAllByCategory(String category) {
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(inventory -> inventory.getCategory().equals(category))
                .map(Inventory::getAll)
                .findFirst();
    }
}
