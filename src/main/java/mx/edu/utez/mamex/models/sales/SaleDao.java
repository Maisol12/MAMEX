package mx.edu.utez.mamex.models.sales;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class SaleDao {
    private Connection conn;

    public SaleDao(Connection conn) {
        this.conn = conn;
    }

    public boolean createSale(Sale sale) throws SQLException {
        String query = "INSERT INTO sales (quantity_sale, subtotal, sale_state, sldate_create, sldate_update, number_sale, fk_id_user, fk_id_item) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, sale.getQuantitySale());
        pstmt.setDouble(2, sale.getSubtotal());
        pstmt.setString(3, sale.getSaleState());
        pstmt.setDate(4, new java.sql.Date(sale.getSlDateCreate().getTime())); // Convert java.util.Date to java.sql.Date
        pstmt.setDate(5, new java.sql.Date(sale.getSlDateUpdate().getTime())); // Convert java.util.Date to java.sql.Date
        pstmt.setInt(6, sale.getNumberSale());
        pstmt.setInt(7, sale.getFkIdUser());
        pstmt.setInt(8, sale.getFkIdItem());
        return pstmt.executeUpdate() > 0;
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
                Date slDateCreate = rs.getDate("sldate_create");
                Date slDateUpdate = rs.getDate("sldate_update");
                int numberSale = rs.getInt("number_sale");
                int fkIdUser = rs.getInt("fk_id_user");
                int fkIdItem = rs.getInt("fk_id_item");

                Sale sale = new Sale(idSale, quantitySale, subtotal, saleState, slDateCreate, slDateUpdate, numberSale, fkIdUser, fkIdItem);
                salesList.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí puedes agregar algún mensaje de error o redireccionar a una página de error.
            throw e; // Re-lanzamos la excepción para que se maneje en otro lugar si es necesario.
        }

        return salesList;
    }

    public boolean saveSale(Sale sale) {
        String query = "INSERT INTO sales(quantity_sale, subtotal, sale_state, sldate_create, number_sale, fk_id_user, fk_id_item) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, sale.getQuantitySale());
            statement.setDouble(2, sale.getSubtotal());
            statement.setString(3, sale.getSaleState());
            statement.setDate(4, new java.sql.Date(sale.getSlDateCreate().getTime()));
            statement.setInt(5, sale.getNumberSale());
            statement.setInt(6, sale.getFkIdUser());
            statement.setInt(7, sale.getFkIdItem());
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateSale(Sale sale) throws SQLException {
        String query = "UPDATE sales SET quantity_sale = ?, subtotal = ?, sale_state = ?, sldate_create = ?, sldate_update = ?, number_sale = ?, fk_id_user = ?, fk_id_item = ? WHERE id_sale = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, sale.getQuantitySale());
        pstmt.setDouble(2, sale.getSubtotal());
        pstmt.setString(3, sale.getSaleState());
        pstmt.setDate(4, new java.sql.Date(sale.getSlDateCreate().getTime())); // Convert java.util.Date to java.sql.Date
        pstmt.setDate(5, new java.sql.Date(sale.getSlDateUpdate().getTime())); // Convert java.util.Date to java.sql.Date
        pstmt.setInt(6, sale.getNumberSale());
        pstmt.setInt(7, sale.getFkIdUser());
        pstmt.setInt(8, sale.getFkIdItem());
        pstmt.setInt(9, sale.getIdSale());
        return pstmt.executeUpdate() > 0;
    }

    public boolean deleteSale(int id) throws SQLException {
        String query = "DELETE FROM sales WHERE id_sale = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, id);
        return pstmt.executeUpdate() > 0;
    }
}






