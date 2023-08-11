package mx.edu.utez.mamex.models.sales;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import mx.edu.utez.mamex.models.user.User;
import mx.edu.utez.mamex.models.items.Item;
import java.sql.Connection;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SaleDao {
    private Connection conn;

    public SaleDao(Connection conn) {
        this.conn = conn;
    }

    public int createSale(Sale sale) {
        String query = "INSERT INTO sales (quantity_sale, subtotal, sale_state, sldate_create, sldate_update, number_sale, fk_id_user, pagoConfirmado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, sale.getQuantitySale());
            pstmt.setDouble(2, sale.getSubtotal());
            pstmt.setString(3, sale.getSaleState());
            pstmt.setDate(4, new java.sql.Date(sale.getSlDateCreate().getTime()));
            pstmt.setDate(5, sale.getSlDateUpdate() != null ? new java.sql.Date(sale.getSlDateUpdate().getTime()) : null);
            pstmt.setInt(6, sale.getNumberSale());
            pstmt.setLong(7, sale.getUser().getId());
            pstmt.setBoolean(8, sale.getPagoConfirmado());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating sale failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int saleId = generatedKeys.getInt(1);

                    // Create the SaleItems
                    for (SaleItem saleItem : sale.getSaleItems()) {
                        String querySaleItem = "INSERT INTO SaleItem (sale_id, item_id, quantity) VALUES (?, ?, ?)";

                        try (PreparedStatement pstmtSaleItem = conn.prepareStatement(querySaleItem)) {
                            pstmtSaleItem.setInt(1, saleId);
                            pstmtSaleItem.setInt(2, saleItem.getItem().getIdItem());
                            pstmtSaleItem.setInt(3, saleItem.getQuantity());

                            pstmtSaleItem.executeUpdate();
                        }
                    }

                    return saleId;
                } else {
                    throw new SQLException("Creating sale failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Sale> getSalesForUser(Long userId) {
        List<Sale> sales = new ArrayList<>();

        String sqlSale = "SELECT * FROM sales WHERE fk_id_user = ?";
        String sqlSaleItems = "SELECT si.*, i.id_item, i.name_item, i.unit_price, ii.image " +
                "FROM SaleItem si " +
                "JOIN items i ON si.item_id = i.id_item " +
                "LEFT JOIN item_images ii ON si.item_id = ii.id_item " +
                "WHERE si.sale_id = ? ORDER BY si.item_id"; // Asegúrate de ordenar por item_id

        try (PreparedStatement psSale = conn.prepareStatement(sqlSale);
             PreparedStatement psSaleItems = conn.prepareStatement(sqlSaleItems)) {

            psSale.setLong(1, userId);
            ResultSet rsSale = psSale.executeQuery();

            while (rsSale.next()) {
                Sale sale = new Sale();
                sale.setIdSale(rsSale.getInt("id_sale"));
                sale.setSubtotal(rsSale.getBigDecimal("subtotal").doubleValue());

                // Formatear la fecha para que no incluya horas, minutos y segundos
                Timestamp timestamp = rsSale.getTimestamp("sldate_create");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(timestamp);
                sale.setSlDateCreate(Timestamp.valueOf(formattedDate + " 00:00:00"));

                List<SaleItem> saleItems = new ArrayList<>();

                psSaleItems.setInt(1, sale.getIdSale());
                ResultSet rsSaleItems = psSaleItems.executeQuery();

                SaleItem saleItem = null;
                while (rsSaleItems.next()) {
                    int currentItemId = rsSaleItems.getInt("id_item");
                    if (saleItem == null || saleItem.getItem().getId() != currentItemId) {
                        saleItem = new SaleItem();
                        saleItem.setId(rsSaleItems.getInt("id"));

                        Item item = new Item();
                        item.setId(currentItemId);
                        item.setName(rsSaleItems.getString("name_item"));
                        item.setUnitPrice(rsSaleItems.getDouble("unit_price"));

                        if(item.getImages() == null) {
                            item.setImages(new HashMap<>());
                        }

                        saleItem.setItem(item);
                        saleItem.setQuantity(rsSaleItems.getInt("quantity"));
                        saleItems.add(saleItem);
                    }

                    byte[] imageBytes = rsSaleItems.getBytes("image");
                    saleItem.getItem().getImages().put("image" + saleItem.getItem().getImages().size(), imageBytes);
                    saleItem.getItem().populateBase64ImagesMap();
                }

                sale.setSaleItems(saleItems);
                sales.add(sale);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }








    public boolean deleteSaleItemReferences(int itemId) {
        String query = "DELETE FROM SaleItem WHERE item_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Sale> getAllSales() throws SQLException {
        List<Sale> salesList = new ArrayList<>();
        String query = "SELECT * FROM sales";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int idSale = rs.getInt("id_sale");
                int quantitySale = rs.getInt("quantity_sale");
                double subtotal = rs.getDouble("subtotal");
                String saleState = rs.getString("sale_state");
                Timestamp slDateCreate = rs.getTimestamp("sldate_create");
                Timestamp slDateUpdate = rs.getTimestamp("sldate_update");
                int numberSale = rs.getInt("number_sale");
                int fkIdUser = rs.getInt("fk_id_user");
                boolean pagoConfirmado = rs.getBoolean("pagoConfirmado"); // Assuming that pagoConfirmado is a boolean column in the table

                // Obtener el objeto User y el objeto Item a partir de los IDs
                User user = getUserById(fkIdUser); // Debes implementar el método getUserById() para obtener el objeto User por su ID

                // Crear el objeto Sale con los objetos User e Item obtenidos
                Sale sale = new Sale(idSale, quantitySale, subtotal, saleState, slDateCreate, slDateUpdate, numberSale, user, pagoConfirmado);
                salesList.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí puedes agregar algún mensaje de error o redireccionar a una página de error.
            throw e; // Re-lanzamos la excepción para que se maneje en otro lugar si es necesario.
        }

        return salesList;
    }

    /*public boolean updateSale(Sale sale) throws SQLException {
        String query = "UPDATE sales SET quantity_sale = ?, subtotal = ?, sale_state = ?, sldate_create = ?, sldate_update = ?, number_sale = ?, fk_id_user = ?, fk_id_item = ? WHERE id_sale = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, sale.getQuantitySale());
        pstmt.setDouble(2, sale.getSubtotal());
        pstmt.setString(3, sale.getSaleState());
        pstmt.setDate(4, new java.sql.Date(sale.getSlDateCreate().getTime())); // Convert java.util.Date to java.sql.Date
        pstmt.setDate(5, new java.sql.Date(sale.getSlDateUpdate().getTime())); // Convert java.util.Date to java.sql.Date
        pstmt.setInt(6, sale.getNumberSale());
        pstmt.setInt(7, sale.getUser().getId().intValue()); // Convert Long to int
        pstmt.setInt(8, sale.getItem().getIdItem().intValue()); // Convert Long to int
        pstmt.setInt(9, sale.getIdSale());
        return pstmt.executeUpdate() > 0;
    }*/

    private Sale createSaleFromResultSet(ResultSet resultSet) throws SQLException {
        int idSale = resultSet.getInt("id_sale");
        int quantitySale = resultSet.getInt("quantity_sale");
        double subtotal = resultSet.getDouble("subtotal");
        String saleState = resultSet.getString("sale_state");
        Timestamp slDateCreate = resultSet.getTimestamp("sldate_create");
        Timestamp slDateUpdate = resultSet.getTimestamp("sldate_update");        int numberSale = resultSet.getInt("number_sale");
        int fkIdUser = resultSet.getInt("fk_id_user");
        int fkIdItem = resultSet.getInt("fk_id_item");
        boolean pagoConfirmado = resultSet.getBoolean("pagoConfirmado"); // Assuming that pagoConfirmado is a boolean column in the table

        // Obtener el objeto User y el objeto Item a partir de los IDs
        User user = getUserById(fkIdUser); // Debes implementar el método getUserById() para obtener el objeto User por su ID
        Item item = getItemById(fkIdItem); // Debes implementar el método getItemById() para obtener el objeto Item por su ID

        // Crear el objeto Sale con los objetos User e Item obtenidos
        Sale sale = new Sale(idSale, quantitySale, subtotal, saleState, slDateCreate, slDateUpdate, numberSale, user, pagoConfirmado);
        return sale;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id_user = ?";
        User user = null;

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id_user = resultSet.getInt("id_user");
                String name = resultSet.getString("name_user");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                // ... otros campos de la tabla users ...

                // Crear el objeto User con los datos obtenidos de la base de datos
                user = new User(id, name, lastname, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí puedes agregar algún mensaje de error o redireccionar a una página de error.
            // También podrías lanzar una excepción personalizada si lo prefieres.
        }

        return user;
    }

    public Item getItemById(int itemId) {
        String query = "SELECT * FROM items WHERE id_item = ?";
        Item item = null;

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id_item");
                String name = resultSet.getString("name_item");
                String description = resultSet.getString("description_item");
                // ... otros campos de la tabla items ...

                // Crear el objeto Item con los datos obtenidos de la base de datos
                item = new Item(id, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí puedes agregar algún mensaje de error o redireccionar a una página de error.
            // También podrías lanzar una excepción personalizada si lo prefieres.
        }

        return item;
    }

    public boolean deleteSale(int id) throws SQLException {
        String query = "DELETE FROM sales WHERE id_sale = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        return pstmt.executeUpdate() > 0;
    }
}