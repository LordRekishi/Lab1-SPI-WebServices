package se.iths.inventory.service;

import se.iths.inventory.annotation.Name;
import se.iths.inventory.entity.Part;
import se.iths.inventory.interfaces.Inventory;

import java.lang.annotation.Annotation;
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
            if (part == null)
                part = inventory.getPartByID(id);
        }

        return Optional.ofNullable(part);
    }

    public Optional<Part> getPartByID(Long id, String category) {
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(inventory -> inventory.getCategory().equals(category))
                .map(inventory -> inventory.getPartByID(id))
                .filter(Objects::nonNull)
                .findFirst();
    }

    public Optional<List<Part>> getPartsByName(String name) {
        List<Part> parts = new ArrayList<>();

        for (Inventory inventory : loader) {
            parts = inventory.getPartsByName(name);
        }

        return Optional.ofNullable(parts);
    }

    public Optional<List<Part>> getPartsByName(String name, String category) {
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(inventory -> inventory.getCategory().equals(category))
                .map(inventory -> inventory.getPartsByName(name))
                .filter(parts -> !parts.isEmpty())
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
                .filter(parts -> !parts.isEmpty())
                .findFirst();
    }

    public Optional<List<Part>> getAll() {
        List<Part> parts = new ArrayList<>();

        for (Inventory inventory : loader) {
            parts.addAll(inventory.getAll());
        }

        return Optional.of(parts);
    }

    public Optional<List<Part>> getAllByCategory(String category) {
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(inventory -> inventory.getCategory().equals(category))
                .map(Inventory::getAll)
                .filter(parts -> !parts.isEmpty())
                .findFirst();
    }

    public Optional<Annotation[]> getAnnotations(String name) {
        return loader.stream()
                .map(ServiceLoader.Provider::get)
                .filter(inventory -> inventory.getClass().getAnnotation(Name.class).name().contains(name))
                .map(inventory -> inventory.getClass().getAnnotations())
                .findFirst();
    }
}
