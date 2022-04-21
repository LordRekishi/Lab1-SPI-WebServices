import se.iths.electricalignition.ElectricalAndIgnitionInventory;
import se.iths.inventory.interfaces.Inventory;

module se.iths.inventory.electricalignition {
    requires se.iths.inventory.service;
    provides Inventory with ElectricalAndIgnitionInventory;
}