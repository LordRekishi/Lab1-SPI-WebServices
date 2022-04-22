# Web Services | Laboration 1 | JU21
###### Developer: Patrik Fallqvist Magnusson

This is our first assignment for the Web Services course at IT-Högskolan in Gothenburg.
The purpose of this assignment is to implement a simple console application using Java SPI (Service Provider Interface) and at least one custom annotation.

This application uses `four separate modules`, all under the same main project and controlled by `module-info.java` configuration files
placed in the `/src/main/java` directory of each module.

This demo application represents a simple inventory management system for a motorcycle parts store where you can implement different
part categories as their own inventories. This app also include a custom annotation used for providing a more in-depth description of the annotated inventory or other types. 

---

## How to use

1. Clone [this repository](https://github.com/LordRekishi/Lab1-SPI-WebServices.git) to your favorite IDE.
2. Run the `Main method` in the `Client` module, `Client.java` class
3. Enjoy beautiful console printouts of demo methods!

---

## Modules

### 1. **Service Provider:**

- This is the `main service` module consisting of `four entities`:

#### One entity class (`Part`)
- The class includes six `fields`:
  - `Long id`
  - `String name`
  - `Map<Integer, Integer> position`
  - `int quantity`
  - `int value`
  - `int totalValue`

#### One interface (`Inventory`)
- The interface provides five `methods`:
  - `String getCategory()`
    - Used to name and separate different category parts (Engine, electric, etc)
  - `Part getPartByID(Long id)`
    - Search for a specific part by its unique ID
  - `List<Part> getPartsByName(String name)`
    - Search the implementations for a part which name contains the parameter
  - `List<Part> getPartsByPosition(Integer cabinet, Integer shelf)`
    - Search a specific position (cabinet, shelf) and return all parts from that position
  - `List<Part> getAll()`
    - Return all parts from a specific category or all in all inventories

#### One service class (`InventoryService`)
- The service provides a `static synchronized instance` of itself that is used to load and sort through different
implementations of the Inventory interface.
- The service class mirrors the interface methods and streams through the `ServiceLoader` providers and filters them
according to the method intended functionality. As well as one method for looking for the description annotation on inventories.

#### One custom annotation (`Description`)
- The annotation includes three `methods`:  
- `String name()` default "No name"
  - Specify the name of the inventory
- `String inventoryDescription()` default "No description"
  - Used to give a more in-depth description of the annotated inventory
- `String author()` default "No author"
  - Specify the author of the description

#### `module-info.java` of the Service Provider:

``` java
module se.iths.inventory.service {
     exports se.iths.inventory.entity;
     exports se.iths.inventory.interfaces;
     exports se.iths.inventory.service;
     exports se.iths.inventory.annotation;
     uses se.iths.inventory.interfaces.Inventory;
}
```

---

### 2. **Client:**

- This is the `consumer` module consisting of `one entity`:

#### Application starting class (`Client`)
- The class includes ten `methods`:
    - public static void `main`(String[] args)
    - private static void `requestDescription`(String name, InventoryService service)
    - private static void `requestPartByID`(Long `id`, InventoryService service)
    - private static void `requestPartByID`(Long `id`, String `category`, InventoryService service)
    - private static void `requestPartsByName`(String `name`, InventoryService service)
    - private static void `requestPartsByName`(String `name`, String `category`, InventoryService service)
    - private static void `requestPartsByPosition`(int `cabinet`, int `shelf`, InventoryService service)
    - private static void `requestPartsByPosition`(int `cabinet`, int `shelf`, String `category`, InventoryService service)
    - private static void `requestAll`(InventoryService service)
    - private static void `requestAllPartsByCategory`(String `category`, InventoryService service)
- These methods request data from the Service class in the ServiceProvider module, which in turn searches the ServiceLoader or for the Description annotation

#### `module-info.java` of the Client:

``` java
import se.iths.inventory.entity.Part;
import se.iths.inventory.interfaces.Inventory;
import se.iths.inventory.service.InventoryService;

module se.iths.inventory.client {
    requires se.iths.inventory.service;
    uses InventoryService;
    uses Inventory;
    uses Part;
}
```

---

### 3. **Body & Frame Inventory:**

- This is the first of two `interface implementing provider` modules consisting of `one entity`:

#### Interface implementing class (`BodyAndFrameInventory`)
- The class includes two `fields`:
    - public static final `String BODY_AND_FRAME_INVENTORY` = "BODY AND FRAME"
      - `getCategory()` returns this static value
    - private final `List<Part> parts`
      - Is `initialized` in the constructor with `three demo parts`


- The class implements all interface `methods`:

#### `module-info.java` of the Body & Frame Inventory:

``` java
import se.iths.bodyframe.BodyAndFrameInventory;
import se.iths.inventory.interfaces.Inventory;

module se.iths.inventory.bodyframe {
    requires se.iths.inventory.service;
    provides Inventory with BodyAndFrameInventory;
}
```

---

### 4. **Electrical & Ignition Inventory:**

- This is the second of two `interface implementing provider` modules consisting of `one entity`:

#### Interface implementing class (`BodyAndFrameInventory`)
- The class includes two `fields`:
  - public static final `String ELECTRICAL_AND_IGNITION_INVENTORY` = "ELECTRICAL AND IGNITION"
    - `getCategory()` returns this static value
  - private final `List<Part> parts`
    - Is `initialized` in the constructor with `three demo parts`


- The class implements all interface `methods`:

#### `module-info.java` of the Body & Frame Inventory:

``` java
import se.iths.electricalignition.ElectricalAndIgnitionInventory;
import se.iths.inventory.interfaces.Inventory;

module se.iths.inventory.electricalignition {
    requires se.iths.inventory.service;
    provides Inventory with ElectricalAndIgnitionInventory;
}
```