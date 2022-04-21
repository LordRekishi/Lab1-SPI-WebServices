package se.iths.entity;

import java.util.HashMap;
import java.util.Map;

public class Part {
    private Long id;
    private String name;
    private Map<Integer, Integer> position;
    private int quantity;
    private int value;
    private int totalValue;

    public Part(Long id, String name, Integer cabinet, Integer shelf, int quantity, int value) {
        this.id = id;
        this.name = name;
        this.position = new HashMap<>(cabinet, shelf);
        this.quantity = quantity;
        this.value = value;
        this.totalValue = quantity * value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Integer> getPosition() {
        return position;
    }

    public void setPosition(Integer cabinet, Integer shelf) {
        this.position = new HashMap<>(cabinet, shelf);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalValue();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        updateTotalValue();
    }

    public int getTotalValue() {
        return totalValue;
    }

    public int updateTotalValue() {
        this.totalValue = value * quantity;
        return totalValue;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position(cabinet, shelf)=" + position +
                ", quantity=" + quantity +
                ", value=" + value +
                ", totalValue=" + totalValue +
                '}';
    }
}
