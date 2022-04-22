package se.iths.client;

import se.iths.inventory.service.InventoryService;

public class Client {
    public static void main(String[] args) {
        InventoryService service = InventoryService.getInstance();

        requestPartByID(1L, service);
        requestPartByID(4L, "ELECTRICAL_AND_IGNITION", service);
        requestPartByID(4L, "BODY AND FRAME", service);
        requestPartsByName("Sensor cable", service);
        requestPartsByName("Handlebar", "BODY AND FRAME", service);
        requestPartsByPosition(1, 8, service);
        requestPartsByPosition(1, 0, "ELECTRICAL_AND_IGNITION", service);
        requestAll(service);
        requestAllPartsByCategory("BODY AND FRAME", service);
        requestAllPartsByCategory("ELECTRICAL_AND_IGNITION", service);
        requestAllPartsByCategory("NONE EXISTING", service);
    }

    private static void requestPartByID(Long id, InventoryService service) {
        service.getPartByID(id)
                .ifPresentOrElse(part -> System.out.println("The Part with ID '" + id + "' was found, here are the details: " + part),
                        () -> System.out.println("Part with ID '" + id + "' not found in Inventory"));
    }

    private static void requestPartByID(Long id, String category, InventoryService service) {
        service.getPartByID(id, category)
                .ifPresentOrElse(part -> System.out.println("The Part with ID '" + id + "' was found in '" + category + "', here are the details: " + part),
                        () -> System.out.println("Part with ID '" + id + "' not found in '" + category + "' Inventory"));
    }

    private static void requestPartsByName(String name, InventoryService service) {
        service.getPartsByName(name)
                .ifPresentOrElse(parts -> System.out.println("Parts with name containing '" + name + "' was found, here are the details: " + parts),
                        () -> System.out.println("Parts with name containing '" + name + "' not found in Inventory"));
    }

    private static void requestPartsByName(String name, String category, InventoryService service) {
        service.getPartsByName(name, category)
                .ifPresentOrElse(parts -> System.out.println("Parts with name containing '" + name + "' was found in '" + category + "', here are the details: " + parts),
                        () -> System.out.println("Parts with name containing '" + name + "' not found in '" + category + "' Inventory"));
    }

    private static void requestPartsByPosition(int cabinet, int shelf, InventoryService service) {
        service.getPartByPosition(cabinet, shelf)
                .ifPresentOrElse(parts -> System.out.println("Parts in cabinet '" + cabinet + "' and on shelf '" + shelf + "' was found, here are the details: " + parts),
                        () -> System.out.println("Parts in cabinet '" + cabinet + "' and on shelf '" + shelf + "' not found in Inventory"));
    }

    private static void requestPartsByPosition(int cabinet, int shelf, String category, InventoryService service) {
        service.getPartByPosition(cabinet, shelf, category)
                .ifPresentOrElse(parts -> System.out.println("Parts in cabinet '" + cabinet + "' and on shelf '" + shelf + "' was found in '" + category + "', here are the details: " + parts),
                        () -> System.out.println("Parts in cabinet '" + cabinet + "' and on shelf '" + shelf + "' not found in '" + category + "' Inventory"));
    }

    private static void requestAll(InventoryService service) {
        service.getAll()
                .ifPresentOrElse(parts -> System.out.println("Returning all parts in all categories, here are the details: " + parts),
                        () -> System.out.println("No parts found!"));
    }

    private static void requestAllPartsByCategory(String category, InventoryService service) {
        service.getAllByCategory(category)
                .ifPresentOrElse(parts -> System.out.println("Parts in '" + category + "' inventory found, here are the details: " + parts),
                        () -> System.out.println("Inventory '" + category + "' not found, or empty!"));

    }
}
