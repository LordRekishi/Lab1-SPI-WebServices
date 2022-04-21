package se.iths.interfaces;

import se.iths.entity.Part;

import java.util.List;

public interface Inventory {
    String getCategory();
    Part getPartByID(Long id);
    List<Part> getPartsByName(String name);
    List<Part> getPartsByPosition(Integer cabinet, Integer shelf);
    List<Part> getAll();
}
