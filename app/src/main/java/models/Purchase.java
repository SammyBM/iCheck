package models;

import java.util.Date;
import java.util.UUID;

public class Purchase {
    private UUID id_purchase;
    private UUID id_shopping;
    private UUID id_shop;
    private Date date_purchase;

    public UUID getId_purchase() {
        return id_purchase;
    }

    public void setId_purchase(UUID id_purchase) {
        this.id_purchase = id_purchase;
    }

    public UUID getId_shopping() {
        return id_shopping;
    }

    public void setId_shopping(UUID id_shopping) {
        this.id_shopping = id_shopping;
    }

    public UUID getId_shop() {
        return id_shop;
    }

    public void setId_shop(UUID id_shop) {
        this.id_shop = id_shop;
    }

    public Date getDate_purchase() {
        return date_purchase;
    }

    public void setDate_purchase(Date date_purchase) {
        this.date_purchase = date_purchase;
    }
}
