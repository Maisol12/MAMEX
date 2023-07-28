package mx.edu.utez.mamex.models.cart;
import mx.edu.utez.mamex.models.items.Item;


public class CartItem {
    private Item item;
    private int quantity;


    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

}
