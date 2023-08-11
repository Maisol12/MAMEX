package mx.edu.utez.mamex.models.orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import mx.edu.utez.mamex.utils.MySQLConnection;




public class OrderDao {
    private Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void updateOrderState(int orderId, String newState) throws SQLException {
        String query = "UPDATE orders SET state = ?, date_updated = ? WHERE id = ?";  // Modifica los nombres de las columnas

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newState);
            statement.setDate(2, getCurrentDate());
            statement.setInt(3, orderId);
            statement.executeUpdate();
        }
    }

    public int saveOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders (state, date_created, fk_id_user, fk_id_sale) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, order.getState());
        pstmt.setDate(2, new java.sql.Date(order.getDate().getTime())); // Convert java.util.Date to java.sql.Date
        pstmt.setInt(3, order.getFkIdUser());
        pstmt.setInt(4, order.getFkIdSale());

        int affectedRows = pstmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating order failed, no rows affected.");
        }

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


    public boolean deleteOrder(int orderId) {
        String query = "DELETE FROM orders WHERE id_order = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, orderId);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Order getOrderById(long orderId) {
        String query = "SELECT * FROM orders WHERE id = ?";  // Modifica los nombres de las columnas

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return createOrderFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Order> getUserOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE fk_id_user = ?";  // Modifica los nombres de las columnas

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private Order createOrderFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String state = resultSet.getString("state");
        Date date = resultSet.getDate("date_created");
        Date updateDate = resultSet.getDate("date_updated");
        int fkIdUser = resultSet.getInt("fk_id_user");
        int fkIdSale = resultSet.getInt("fk_id_sale");

        return new Order(id, state, date, updateDate, fkIdUser, fkIdSale);
    }

    private Date getCurrentDate() {
        java.util.Date currentDate = new java.util.Date();
        return new Date(currentDate.getTime());
    }
}