package mx.edu.utez.mamex.models.items;

import mx.edu.utez.mamex.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.util.Date;

public class ItemDao {
    private Connection conn;

    public ItemDao(Connection conn) {
        this.conn = conn;
    }

    public boolean saveItem(Item item) {
        boolean result = false;
        String query = "INSERT INTO items(name_item, description_item, available, color, unit_price, create_date, update_date, stock, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setString(3, item.getAvailable());
            preparedStatement.setString(4, item.getColor());
            preparedStatement.setDouble(5, item.getUnitPrice());
            preparedStatement.setDate(6, new java.sql.Date(item.getCreateDate().getTime()));
            preparedStatement.setDate(7, new java.sql.Date(item.getUpdateDate().getTime()));
            preparedStatement.setInt(8, item.getStock());
            preparedStatement.setString(9, item.getNotes());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int itemId = generatedKeys.getInt(1);
                        for (Map.Entry<String, byte[]> entry : item.getImages().entrySet()) {
                            String imageQuery = "INSERT INTO item_images(id_item, image_name, image, base64image) VALUES (?, ?, ?, ?)";
                            PreparedStatement imagePreparedStatement = conn.prepareStatement(imageQuery);
                            imagePreparedStatement.setInt(1, itemId);
                            imagePreparedStatement.setString(2, entry.getKey());
                            imagePreparedStatement.setBytes(3, entry.getValue());
                            String base64Image = Base64.getEncoder().encodeToString(entry.getValue());
                            imagePreparedStatement.setString(4, base64Image);
                            imagePreparedStatement.executeUpdate();
                        }
                    }
                    result = true;
                }
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteItem(int itemId) {
        String deleteImagesQuery = "DELETE FROM item_images WHERE id_item = ?";
        String deleteItemQuery = "DELETE FROM items WHERE id_item = ?";

        try (PreparedStatement deleteImagesStmt = conn.prepareStatement(deleteImagesQuery);
             PreparedStatement deleteItemStmt = conn.prepareStatement(deleteItemQuery)) {

            // Primero eliminamos las imágenes asociadas al artículo
            deleteImagesStmt.setInt(1, itemId);
            deleteImagesStmt.executeUpdate();

            // Luego eliminamos el artículo
            deleteItemStmt.setInt(1, itemId);
            int affectedRows = deleteItemStmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public boolean updateItem(Item item) {
        String updateItemQuery = "UPDATE items SET name_item = ?, description_item = ?, color = ?, unit_price = ?, stock = ? WHERE id_item = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateItemQuery);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setString(3, item.getColor());
            preparedStatement.setDouble(4, item.getUnitPrice());
            preparedStatement.setInt(5, item.getStock());
            preparedStatement.setInt(6, item.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0 && item.getImages().size() > 0) {
                // First, delete all the old images for this item
                String deleteImagesQuery = "DELETE FROM item_images WHERE id_item = ?";
                preparedStatement = conn.prepareStatement(deleteImagesQuery);
                preparedStatement.setInt(1, item.getId());
                preparedStatement.executeUpdate();

                // Then, insert the new images
                String insertImagesQuery = "INSERT INTO item_images (id_item, image) VALUES (?, ?)";
                preparedStatement = conn.prepareStatement(insertImagesQuery);
                for (Map.Entry<String, byte[]> imageEntry : item.getImages().entrySet()) {
                    preparedStatement.setInt(1, item.getId());
                    preparedStatement.setBytes(2, imageEntry.getValue());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Item getItemById(int itemId) {
        Item item = null;
        String query = "SELECT * FROM items WHERE id_item = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, itemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id_item");
                String name = resultSet.getString("name_item");
                String description = resultSet.getString("description_item");
                String available = resultSet.getString("available");
                String color = resultSet.getString("color");
                double unitPrice = resultSet.getDouble("unit_price");
                Date createDate = resultSet.getDate("create_date");
                Date updateDate = resultSet.getDate("update_date");
                int stock = resultSet.getInt("stock");
                String notes = resultSet.getString("notes");

                Map<String, byte[]> images = new HashMap<>();
                Map<String, String> base64Images = new HashMap<>();
                String imageQuery = "SELECT * FROM item_images WHERE id_item = ?";
                PreparedStatement imagePreparedStatement = conn.prepareStatement(imageQuery);
                imagePreparedStatement.setInt(1, itemId);
                ResultSet imageResultSet = imagePreparedStatement.executeQuery();
                while (imageResultSet.next()) {
                    String imageName = imageResultSet.getString("image_name");
                    byte[] imageBytes = imageResultSet.getBytes("image");
                    images.put(imageName, imageBytes);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    base64Images.put(imageName, base64Image);
                }

                item = new Item(id, name, description, available, color, unitPrice, createDate, updateDate, stock, notes, images, base64Images);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }


    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id_item");
                String name = resultSet.getString("name_item");
                String description = resultSet.getString("description_item");
                String available = resultSet.getString("available");
                String color = resultSet.getString("color");
                double unitPrice = resultSet.getDouble("unit_price");
                java.sql.Date createDate = resultSet.getDate("create_date");
                java.sql.Date updateDate = resultSet.getDate("update_date");
                int stock = resultSet.getInt("stock");
                String notes = resultSet.getString("notes");

                Map<String, byte[]> images = new HashMap<>();
                Map<String, String> base64Images = new HashMap<>();

                String imageQuery = "SELECT * FROM item_images WHERE id_item = ?";
                PreparedStatement imagePreparedStatement = conn.prepareStatement(imageQuery);
                imagePreparedStatement.setInt(1, id);
                ResultSet imageResultSet = imagePreparedStatement.executeQuery();
                while (imageResultSet.next()) {
                    String imageName = imageResultSet.getString("image_name");
                    byte[] image = imageResultSet.getBytes("image");
                    String base64Image = imageResultSet.getString("base64image");

                    images.put(imageName, image);
                    base64Images.put(imageName, base64Image);
                }

                items.add(new Item(id, name, description, available, color, unitPrice, createDate, updateDate, stock, notes, images, base64Images));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

}