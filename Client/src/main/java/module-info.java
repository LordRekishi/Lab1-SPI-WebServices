import se.iths.inventory.entity.Part;
import se.iths.inventory.interfaces.Inventory;
import se.iths.inventory.service.InventoryService;

module se.iths.inventory.client {
    requires se.iths.inventory.service;
    uses InventoryService;
    uses Inventory;
    uses Part;
}
