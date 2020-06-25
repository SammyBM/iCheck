package models;

import java.util.UUID;

public class Product {
    private UUID id_product;
    private UUID id_category;
    private String description;
    private int days_of_life;

    public UUID getId_product() {
        return id_product;
    }

    public void setId_product(UUID id_product) {
        this.id_product = id_product;
    }

    public UUID getId_category() {
        return id_category;
    }

    public void setId_category(UUID id_category) {
        this.id_category = id_category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDays_of_life() {
        return days_of_life;
    }

    public void setDays_of_life(int days_of_life) {
        this.days_of_life = days_of_life;
    }
}
