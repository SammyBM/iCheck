package models;

import java.util.UUID;

public class Shop {
    private UUID id_shop;
    private String name;

    public UUID getId_shop() {
        return id_shop;
    }

    public void setId_shop(UUID id_shop) {
        this.id_shop = id_shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
