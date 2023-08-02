package mx.edu.utez.mamex.models.sales;

import java.util.Date;

public class Sale {
    private int idSale;
    private int quantitySale;
    private double subtotal;
    private String saleState;
    private Date slDateCreate;
    private Date slDateUpdate;
    private int numberSale;
    private int fkIdUser;
    private int fkIdItem;

    public Sale() {
        // Constructor body
    }
    public Sale(int idSale, int quantitySale, double subtotal, String saleState, Date slDateCreate, Date slDateUpdate, int numberSale, int fkIdUser, int fkIdItem) {
        this.idSale = idSale;
        this.quantitySale = quantitySale;
        this.subtotal = subtotal;
        this.saleState = saleState;
        this.slDateCreate = slDateCreate;
        this.slDateUpdate = slDateUpdate;
        this.numberSale = numberSale;
        this.fkIdUser = fkIdUser;
        this.fkIdItem = fkIdItem;
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

    public Date getSlDateCreate() {
        return slDateCreate;
    }

    public void setSlDateCreate(Date slDateCreate) {
        this.slDateCreate = slDateCreate;
    }

    public Date getSlDateUpdate() {
        return slDateUpdate;
    }

    public void setSlDateUpdate(Date slDateUpdate) {
        this.slDateUpdate = slDateUpdate;
    }

    public int getNumberSale() {
        return numberSale;
    }

    public void setNumberSale(int numberSale) {
        this.numberSale = numberSale;
    }

    public int getFkIdUser() {
        return fkIdUser;
    }

    public void setFkIdUser(int fkIdUser) {
        this.fkIdUser = fkIdUser;
    }

    public int getFkIdItem() {
        return fkIdItem;
    }

    public void setFkIdItem(int fkIdItem) {
        this.fkIdItem = fkIdItem;
    }
}
