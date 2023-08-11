package mx.edu.utez.mamex.models.items;
import java.util.Date;
import java.util.Map;
import java.util.Base64;
import java.util.HashMap;

public class Item {
    private int id;
    private String name;
    private String description;
    private String available;
    private String color;
    private double unitPrice;
    private Date createDate;
    private Date updateDate;
    private int stock;
    private String notes;
    private Map<String, byte[]> images;

    private Map<String, String> base64Images = new HashMap<>();

    private String category;
    // Constructor
    public Item(int id, String name, String description, String available, String color, double unitPrice,
                Date createDate, int stock, String notes, Map<String, byte[]> images, Map<String, String> base64Images, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.color = color;
        this.unitPrice = unitPrice;
        this.createDate = createDate;
        this.stock = stock;
        this.notes = notes;
        this.images = images;
        this.base64Images = base64Images;
        this.category = category;
    }

    public Item() {
    }


    public int getIdItem() {
        return id; // Suponiendo que el atributo que almacena el ID del item se llama 'id'
    }

    // Constructor override
    public Item(int id, String name, String description, String color, double unitPrice, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.color = color;
        this.unitPrice = unitPrice;
        this.stock = stock;
    }



    public Item(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String convertToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public void populateBase64ImagesMap() {
        if (this.images != null && !this.images.isEmpty()) {
            for (Map.Entry<String, byte[]> entry : this.images.entrySet()) {
                String key = entry.getKey();
                byte[] imageData = entry.getValue();
                String base64ImageData = Base64.getEncoder().encodeToString(imageData);

                if (this.base64Images == null) {
                    this.base64Images = new HashMap<>();
                }
                this.base64Images.put(key, base64ImageData);
            }
        }
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Map<String, byte[]> getImages() {
        return images;
    }

    public void setImages(Map<String, byte[]> images) {
        this.images = images;
    }

    public Map<String, String> getBase64Images() {
        return base64Images;
    }

    public void setBase64Images(Map<String, String> base64Images) {
        this.base64Images = base64Images;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



}