import se.iths.inventory.BodyAndFrameInventory;
import se.iths.inventory.interfaces.Inventory;

module se.iths.inventory.bodyframe {
    requires se.iths.inventory.service;
    provides Inventory with BodyAndFrameInventory;
}