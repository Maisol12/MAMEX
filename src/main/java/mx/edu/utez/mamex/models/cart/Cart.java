package mx.edu.utez.mamex.models.cart;
import mx.edu.utez.mamex.models.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(Item item, int quantity) {
        items.add(new CartItem(item, quantity));
    }

    public void removeItem(Item item) {
        items.removeIf(cartItem -> cartItem.getItem().equals(item));
    }

    public List<CartItem> getItems() {
        return items;
    }
}

