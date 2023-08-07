package mx.edu.utez.mamex.models.sales;

import mx.edu.utez.mamex.models.sales.Sale;
import mx.edu.utez.mamex.models.items.Item;

public class SaleItem {
    private int id;
    private Sale sale;
    private Item item;
    private int quantity;

    public SaleItem() {
    }

    public SaleItem(int id, Sale sale, Item item, int quantity) {
        this.id = id;
        this.sale = sale;
        this.item = item;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}