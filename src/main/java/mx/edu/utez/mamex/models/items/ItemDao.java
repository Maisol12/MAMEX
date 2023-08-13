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
import java.sql.Connection;
import java.sql.Statement;


public class ItemDao {
    private Connection conn;

    public ItemDao(Connection conn) {
        this.conn = conn;
    }

    public boolean saveItem(Item item) {
        boolean result = false;
        String query = "INSERT INTO items(name_item, description_item, available, color, unit_price, create_date, stock, notes,category) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setString(3, item.getAvailable());
            preparedStatement.setString(4, item.getColor());
            preparedStatement.setDouble(5, item.getUnitPrice());
            preparedStatement.setDate(6, new java.sql.Date(item.getCreateDate().getTime()));
            preparedStatement.setInt(7, item.getStock());
            preparedStatement.setString(8, item.getNotes());
            preparedStatement.setString(9, item.getCategory());


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

    public String convertToBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public List<Item> getFilteredItemsByCategory(String category) {
        List<Item> items = new ArrayList<>();

        // La consulta para filtrar por categoría
        String query = "SELECT * FROM items WHERE category = ?";  // Asegúrate de que "items" es el nombre correcto de tu tabla y "category" es el nombre correcto de tu columna

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id_item"));
                item.setName(rs.getString("name_item"));
                item.setDescription(rs.getString("description_item"));
                item.setColor(rs.getString("color"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setStock(rs.getInt("stock"));
                item.setCreateDate(rs.getDate("create_date"));
                item.setNotes(rs.getString("notes"));
                item.setCategory(rs.getString("category"));

                Map<String, byte[]> imagesMap = new HashMap<>();
                Map<String, String> base64ImagesMap = new HashMap<>();

                // Fetch images from the item_images table for the current item
                PreparedStatement imgStmt = this.conn.prepareStatement("SELECT image FROM item_images WHERE id_item = ?");
                imgStmt.setInt(1, item.getId());

                ResultSet imgRs = imgStmt.executeQuery();
                int imageIndex = 1;
                while (imgRs.next()) {
                    byte[] imageBytes = imgRs.getBytes("image");
                    imagesMap.put("image" + imageIndex, imageBytes);
                    base64ImagesMap.put("image" + imageIndex, Base64.getEncoder().encodeToString(imageBytes));
                    imageIndex++;
                }
                imgRs.close();
                imgStmt.close();

                item.setImages(imagesMap);
                item.setBase64Images(base64ImagesMap);

                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<Item> getFilteredItemsByPriceRange(String priceRange) {
        List<Item> items = new ArrayList<>();

        String[] prices = priceRange.split("-");
        int minPrice = Integer.parseInt(prices[0]);
        int maxPrice = Integer.parseInt(prices[1]);

        // La consulta para filtrar por rango de precios
        String query = "SELECT * FROM items WHERE unit_price BETWEEN ? AND ?";  // Asegúrate de que "items" es el nombre correcto de tu tabla y "unitPrice" es el nombre correcto de tu columna

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, minPrice);
            stmt.setInt(2, maxPrice);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id_item"));
                item.setName(rs.getString("name_item"));
                item.setDescription(rs.getString("description_item"));
                item.setColor(rs.getString("color"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setStock(rs.getInt("stock"));
                item.setCreateDate(rs.getDate("create_date"));
                item.setNotes(rs.getString("notes"));
                item.setCategory(rs.getString("category"));

                Map<String, byte[]> imagesMap = new HashMap<>();
                Map<String, String> base64ImagesMap = new HashMap<>();

                // Fetch images from the item_images table for the current item
                PreparedStatement imgStmt = this.conn.prepareStatement("SELECT image FROM item_images WHERE id_item = ?");
                imgStmt.setInt(1, item.getId());

                ResultSet imgRs = imgStmt.executeQuery();
                int imageIndex = 1;
                while (imgRs.next()) {
                    byte[] imageBytes = imgRs.getBytes("image");
                    imagesMap.put("image" + imageIndex, imageBytes);
                    base64ImagesMap.put("image" + imageIndex, Base64.getEncoder().encodeToString(imageBytes));
                    imageIndex++;
                }
                imgRs.close();
                imgStmt.close();

                item.setImages(imagesMap);
                item.setBase64Images(base64ImagesMap);

                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }



    public List<Item> getFilteredItems(String category, String priceRange) {
        List<Item> items = new ArrayList<>();

        String[] prices = priceRange.split("-");
        double minPrice = Double.parseDouble(prices[0]);
        double maxPrice = Double.parseDouble(prices[1]);

        String query = "SELECT * FROM items WHERE category = ? AND unit_price BETWEEN ? AND ?";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            stmt.setString(1, category);
            stmt.setDouble(2, minPrice);
            stmt.setDouble(3, maxPrice);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id_item"));
                item.setName(rs.getString("name_item"));
                item.setDescription(rs.getString("description_item"));
                item.setColor(rs.getString("color"));
                item.setUnitPrice(rs.getDouble("unit_price"));
                item.setStock(rs.getInt("stock"));
                item.setCreateDate(rs.getDate("create_date"));
                item.setNotes(rs.getString("notes"));
                item.setCategory(rs.getString("category"));

                Map<String, byte[]> imagesMap = new HashMap<>();
                Map<String, String> base64ImagesMap = new HashMap<>();

                // Fetch images from the item_images table for the current item
                PreparedStatement imgStmt = this.conn.prepareStatement("SELECT image FROM item_images WHERE id_item = ?");
                imgStmt.setInt(1, item.getId());

                ResultSet imgRs = imgStmt.executeQuery();
                int imageIndex = 1;
                while (imgRs.next()) {
                    byte[] imageBytes = imgRs.getBytes("image");
                    imagesMap.put("image" + imageIndex, imageBytes);
                    base64ImagesMap.put("image" + imageIndex, Base64.getEncoder().encodeToString(imageBytes));
                    imageIndex++;
                }
                imgRs.close();
                imgStmt.close();

                item.setImages(imagesMap);
                item.setBase64Images(base64ImagesMap);

                items.add(item);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return items;
    }

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT DISTINCT category FROM items";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
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

    public Item getItemByName(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Item item = null;

        try {
            conn = new MySQLConnection().connect();
            String sql = "SELECT * FROM items WHERE name_item = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                item = new Item();
                // Asumiendo que tu clase Item tiene setters para todos los campos.
                item.setId(rs.getInt("id_item"));
                item.setName(rs.getString("name_item"));
                // ... asigna los demás campos aquí ...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar tus recursos aquí (ResultSet, PreparedStatement, Connection).
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return item;
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
                int stock = resultSet.getInt("stock");
                String notes = resultSet.getString("notes");
                String category = resultSet.getString("category");

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

                item = new Item(id, name, description, available, color, unitPrice, createDate, stock, notes, images, base64Images,category);
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
                int stock = resultSet.getInt("stock");
                String notes = resultSet.getString("notes");
                String category = resultSet.getString("category");


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

                items.add(new Item(id, name, description, available, color, unitPrice, createDate, stock, notes, images, base64Images,category));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

}