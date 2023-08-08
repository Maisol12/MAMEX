package mx.edu.utez.mamex.models.sales;

import java.sql.Timestamp;
import mx.edu.utez.mamex.models.items.Item;
import mx.edu.utez.mamex.models.user.User;
import mx.edu.utez.mamex.models.sales.SaleItem;
import java.util.List;
import java.util.ArrayList;


public class Sale {
    private int idSale;
    private int quantitySale;
    private double subtotal;
    private String saleState;
    private Timestamp slDateCreate;
    private Timestamp slDateUpdate;
    private int numberSale;
    private User user; // Agregamos el campo User para almacenar el objeto User asociado a la venta
    private Item item; // Agregamos el campo Item para almacenar el objeto Item asociado a la venta
    private boolean PagoConfirmado;
    private List<SaleItem> saleItems;

    public Sale(int idSale, int quantitySale, double subtotal, String saleState, Timestamp slDateCreate, Timestamp slDateUpdate, int numberSale, User user, boolean pagoConfirmado) {
        this.idSale = idSale;
        this.quantitySale = quantitySale;
        this.subtotal = subtotal;
        this.saleState = saleState;
        this.slDateCreate = slDateCreate;
        this.slDateUpdate = slDateUpdate;
        this.numberSale = numberSale;
        this.user = user;
        this.PagoConfirmado = pagoConfirmado;
    }

    public Sale() {
        saleItems = new ArrayList<>();
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public int getQuantitySale() {
        return quantitySale;
    }

    public void setQuantitySale(int quantitySale) {
        this.quantitySale = quantitySale;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public String getSaleState() {
        return saleState;
    }

    public void setSaleState(String saleState) {
        this.saleState = saleState;
    }

    public Timestamp getSlDateCreate() {
        return slDateCreate;
    }

    public void setSlDateCreate(Timestamp slDateCreate) {
        this.slDateCreate = slDateCreate;
    }

    public Timestamp getSlDateUpdate() {
        return slDateUpdate;
    }

    public void setSlDateUpdate(Timestamp slDateUpdate) {
        this.slDateUpdate = slDateUpdate;
    }

    public int getNumberSale() {
        return numberSale;
    }

    public void setNumberSale(int numberSale) {
        this.numberSale = numberSale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public boolean getPagoConfirmado() {
        return PagoConfirmado;
    }

    public void setPagoConfirmado(boolean pagoConfirmado) {
        this.PagoConfirmado = pagoConfirmado;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void addSaleItem(SaleItem saleItem) {
        this.saleItems.add(saleItem);
    }
}
