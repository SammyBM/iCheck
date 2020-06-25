package models;

import java.util.UUID;

public class ShoppingList {
    private UUID id_shopping;
    private UUID id_user;
    private String name_list;
    private boolean feature;

    public UUID getId_shopping() {
        return id_shopping;
    }

    public void setId_shopping(UUID id_shopping) {
        this.id_shopping = id_shopping;
    }

    public UUID getId_user() {
        return id_user;
    }

    public void setId_user(UUID id_user) {
        this.id_user = id_user;
    }

    public String getName_list() {
        return name_list;
    }

    public void setName_list(String name_list) {
        this.name_list = name_list;
    }

    public boolean isFeature() {
        return feature;
    }

    public void setFeature(boolean feature) {
        this.feature = feature;
    }
}
