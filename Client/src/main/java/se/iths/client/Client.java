package se.iths.client;

import se.iths.inventory.annotation.Author;
import se.iths.inventory.annotation.Description;
import se.iths.inventory.annotation.Name;
import se.iths.inventory.service.InventoryService;

import java.lang.annotation.Annotation;

public class Client {
    private static final InventoryService service = InventoryService.getInstance();

    public static void main(String[] args) {
        System.out.println("""
                Check Inventory annotations demo
                ----------------------""");
        requestAnnotations("Body");
        requestAnnotations("Electrical");
        requestAnnotations("NONE");

        System.out.println("""
                                
                Parts by ID demo
                ----------------------""");
        requestPartByID(1L);
        requestPartByID(4L, "ELECTRICAL AND IGNITION");
        requestPartByID(4L, "BODY AND FRAME");

        System.out.println("""
                                
                Parts by Name demo
                ----------------------""");
        requestPartsByName("Sensor cable");
        requestPartsByName("Handlebar", "BODY AND FRAME");

        System.out.println("""
                                
                Parts by Position demo
                ----------------------""");
        requestPartsByPosition(1, 8);
        requestPartsByPosition(1, 3, "ELECTRICAL AND IGNITION");
        requestPartsByPosition(1, 0, "ELECTRICAL AND IGNITION");

        System.out.println("""
                                
                Get All demo
                ----------------------""");
        requestAll();
        requestAllPartsByCategory("BODY AND FRAME");
        requestAllPartsByCategory("ELECTRICAL AND IGNITION");
        requestAllPartsByCategory("NONE EXISTING");
    }

    private static void requestAnnotations(String name) {
        service.getAnnotations(name)
                .ifPresentOrElse(annotations -> {
                    String collectedName = "";
                    String collectedDescription = "";
                    String collectedAuthor = "";

                    for (Annotation annotation : annotations) {
                        switch (annotation) {
                            case Name n -> collectedName = n.name();
                            case Description d -> collectedDescription = d.inventoryDescription();
                            case Author a -> collectedAuthor = a.author();
                            default -> throw new IllegalStateException("Unexpected value: " + annotation);
                        }
                    }

                    System.out.println("Inventory name: '" + collectedName + "'\n" +
                            "Description: '" + collectedDescription + "'\n" +
                            "Author: '" + collectedAuthor + "'");
                }, () -> System.out.println("No description with name containing '" + name + "' found!"));

    }

    private static void requestPartByID(Long id) {
        service.getPartByID(id)
                .ifPresentOrElse(part -> System.out.println("The Part with ID '" + id + "' was found, here are the details: " + part),
                        () -> System.out.println("Part with ID '" + id + "' not found in Inventory"));
    }

    private static void requestPartByID(Long id, String category) {
        service.getPartByID(id, category)
                .ifPresentOrElse(part -> System.out.println("The Part with ID '" + id + "' was found in '" + category + "', here are the details: " + part),
                        () -> System.out.println("Part with ID '" + id + "' not found in '" + category + "' Inventory"));
    }

    private static void requestPartsByName(String name) {
        service.getPartsByName(name)
                .ifPresentOrElse(parts -> System.out.println("Parts with name containing '" + name + "' was found, here are the details: " + parts),
                        () -> System.out.println("Parts with name containing '" + name + "' not found in Inventory"));
    }

    private static void requestPartsByName(String name, String category) {
        service.getPartsByName(name, category)
                .ifPresentOrElse(parts -> System.out.println("Parts with name containing '" + name + "' was found in '" + category + "', here are the details: " + parts),
                        () -> System.out.println("Parts with name containing '" + name + "' not found in '" + category + "' Inventory"));
    }

    private static void requestPartsByPosition(int cabinet, int shelf) {
        service.getPartByPosition(cabinet, shelf)
                .ifPresentOrElse(parts -> System.out.println("Parts in cabinet '" + cabinet + "' and on shelf '" + shelf + "' was found, here are the details: " + parts),
                        () -> System.out.println("Parts in cabinet '" + cabinet + "' and on shelf '" + shelf + "' not found in Inventory"));
    }

    private static void requestPartsByPosition(int cabinet, int shelf, String category) {
        service.getPartByPosition(cabinet, shelf, category)
                .ifPresentOrElse(parts -> System.out.println("Parts in cabinet '" + cabinet + "' and on shelf '" + shelf + "' was found in '" + category + "', here are the details: " + parts),
                        () -> System.out.println("Parts in cabinet '" + cabinet + "' and on shelf '" + shelf + "' not found in '" + category + "' Inventory"));
    }

    private static void requestAll() {
        service.getAll()
                .ifPresentOrElse(parts -> System.out.println("Returning all parts in all categories, here are the details: " + parts),
                        () -> System.out.println("No parts found!"));
    }

    private static void requestAllPartsByCategory(String category) {
        service.getAllByCategory(category)
                .ifPresentOrElse(parts -> System.out.println("Parts in '" + category + "' inventory found, here are the details: " + parts),
                        () -> System.out.println("Inventory '" + category + "' not found, or empty!"));

    }
}
