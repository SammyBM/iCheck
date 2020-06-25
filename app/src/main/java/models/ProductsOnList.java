package models;

import java.util.UUID;

public class ProductsOnList {
    private UUID id_list;
    private UUID id_product;
    private UUID count;

    public UUID getId_list() {
        return id_list;
    }

    public void setId_list(UUID id_list) {
        this.id_list = id_list;
    }

    public UUID getId_product() {
        return id_product;
    }

    public void setId_product(UUID id_product) {
        this.id_product = id_product;
    }

    public UUID getCount() {
        return count;
    }

    public void setCount(UUID count) {
        this.count = count;
    }
}
