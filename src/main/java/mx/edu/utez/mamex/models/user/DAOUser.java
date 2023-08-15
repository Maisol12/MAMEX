package mx.edu.utez.mamex.models.user;


import mx.edu.utez.mamex.models.Review;
import mx.edu.utez.mamex.models.crud.DAORepository;
import mx.edu.utez.mamex.utils.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.Map;
import java.util.logging.Logger;

public class DAOUser{
    private Connection conn;
    private PreparedStatement pstm;
    private CallableStatement cs;
    private ResultSet rs;

    private static Map<String, User> userMap = new HashMap<>();




    public List<User> findAll() {
        List <User> users = new ArrayList<>();
        User user = null;
        try {
            conn = new MySQLConnection().connect();
            cs = conn.prepareCall("{call mostrar_info()}");
            boolean result = cs.execute();
            if(result) {
                rs = cs.getResultSet();
                while (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("id_user"));
                    user.setNames(rs.getString("name_user"));
                    user.setLastnames(rs.getString("lastname"));
                    user.setEmail(rs.getString("email"));
                    user.setGender(rs.getString("sex"));
                    user.setBirthday(rs.getString("birthday"));
                    user.setImg_user(rs.getBytes("photo"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName())
                    .log(Level.SEVERE, "ERROR findAll" + e.getMessage());
        } finally {
            close();
        }
        return users;
    }

    public User findUserByEmail(String email) {
        User user = null;
        try {
            conn = new MySQLConnection().connect();
            cs = conn.prepareCall("{call find_user_by_email(?)}");
            cs.setString(1, email);
            boolean result = cs.execute();
            if(result) {
                rs = cs.getResultSet();
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("id_user"));
                    user.setNames(rs.getString("name_user"));
                    user.setLastnames(rs.getString("lastname"));
                    user.setEmail(rs.getString("email"));
                    user.setGender(rs.getString("sex"));
                    user.setBirthday(rs.getString("birthday"));
                    user.setImg_user(rs.getBytes("photo"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName())
                    .log(Level.SEVERE, "ERROR findUserByEmail" + e.getMessage());
        } finally {
            close();
        }
        return user;
    }

    public static void deleteUser(String userId) throws SQLException {
        // Asumiendo que tienes una conexión a la base de datos configurada:
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.connect();  // Obtener conexión (adaptar según tu implementación)
        PreparedStatement statement = null;
        try {
            // Modificamos el SQL para hacer una actualización del estado en lugar de eliminar
            String sql = "UPDATE users SET user_state = 'INHABILITADO' WHERE id_user = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.executeUpdate();
        } finally {
            // Cerrar recursos (si están abiertos)
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static void updateUser(String userId) throws SQLException {
        // Asumiendo que tienes una conexión a la base de datos configurada:
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.connect();  // Obtener conexión (adaptar según tu implementación)
        PreparedStatement statement = null;
        try {
            // Modificamos el SQL para hacer una actualización del estado en lugar de eliminar
            String sql = "UPDATE users SET user_state = 'HABILITADO' WHERE id_user = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.executeUpdate();
        } finally {
            // Cerrar recursos (si están abiertos)
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static void deleteOrdersByUserId(String userId) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.connect();

        String deleteOrdersQuery = "DELETE FROM orders WHERE fk_id_user = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(deleteOrdersQuery);
        preparedStatement.setString(1, userId);
        preparedStatement.executeUpdate();

        connection.close();
    }

    public static void updatePaymentStatus(String saleId, boolean isPaid) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.connect();
        PreparedStatement statement = null;
        try {
            // Iniciar transacción
            connection.setAutoCommit(false);

            // Valor que se quiere actualizar
            String updatedState = isPaid ? "Pagado" : "Pendiente";

            // Actualizar tabla sales
            String sqlSales = "UPDATE sales SET sale_state = ? WHERE id_sale = ?";
            statement = connection.prepareStatement(sqlSales);
            statement.setString(1, updatedState);
            statement.setString(2, saleId);
            int affectedRowsSales = statement.executeUpdate();

            // Actualizar tabla orders
            String sqlOrders = "UPDATE orders SET state = ? WHERE fk_id_sale = ?";
            statement = connection.prepareStatement(sqlOrders);
            statement.setString(1, updatedState);
            statement.setString(2, saleId);
            int affectedRowsOrders = statement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            // Si hay algún error, hacer rollback de la transacción y mostrar el mensaje de error
            connection.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }



    public static void updateUserPassword(String email, String newPassword) throws SQLException {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.connect();

        String updatePasswordQuery = "{CALL actualizar_contrasena(?, ?, 'llaveencriptacion')}";
        CallableStatement callableStatement = connection.prepareCall(updatePasswordQuery);
        callableStatement.setString(1, email);
        callableStatement.setString(2, newPassword);
        callableStatement.execute();

        connection.close();
    }





    public User findOne(Long id) {
        User user = null;
        try {
            conn = new MySQLConnection().connect();
            cs = conn.prepareCall("{call mostrar_info(?)}");
            cs.setLong(1, id);
            boolean result = cs.execute();
            if(result) {
                rs = cs.getResultSet();
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getLong("id_user"));
                    user.setNames(rs.getString("name_user"));
                    user.setLastnames(rs.getString("lastname"));
                    user.setEmail(rs.getString("email"));
                    user.setGender(rs.getString("sex"));
                    user.setBirthday(rs.getString("birthday"));
                    user.setImg_user(rs.getBytes("photo"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName())
                    .log(Level.SEVERE, "ERROR findOne" + e.getMessage());
        } finally {
            close();
        }
        return user;
    }

    public boolean save(User object) {
        try {
            //se realiza la conexion a la base de datos
            conn = new MySQLConnection().connect();
            //se define la sentencia mysql
            cs = conn.prepareCall("{call nuevo_usuario(?, ?, ?, ?, 'llaveencriptacion')}");
            //establecemos el valor a los parametros de la sentencia
            cs.setString(1, object.getNames());
            cs.setString(2, object.getLastnames());
            cs.setString(3, object.getEmail());
            cs.setString(4, object.getPassword());
            //retorna un valor 0 si la consulta es fallida o un 1 si es correcta
            boolean result = cs.execute();
            return !result;
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error save" + e.getMessage());
        } finally {
            close();
        }
        return false;
    } //metodo para registrar un nuevo usuario a mamex

    public boolean submitReview(Review review, String userEmail) {
        PreparedStatement ps = null;
        String query = "INSERT INTO reviews (usuario, evaluacion, comentario, producto) VALUES (?, ?, ?, ?)";
        try {
            conn = new MySQLConnection().connect();
            if (!hasUserPurchasedProduct(userEmail, review.getProducto())) {
                // Si el usuario no ha comprado el producto, no permitir la reseña
                return false;
            }
            ps = conn.prepareStatement(query);

            ps.setString(1, review.getName_user());   // usuario
            ps.setInt(2, review.getEvaluacion());     // evaluación
            ps.setString(3, review.getComentario());  // comentario
            ps.setLong(4, review.getProducto());      // producto

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error al guardar review", e);
        } finally {
            close();  // Asegúrate de que este método cierra adecuadamente los recursos (ps, conn, etc.)
        }
        return false;
    }

    public boolean hasUserPurchasedProduct(String userEmail, long productId) throws SQLException {
        String query = "SELECT s.id_sale " +
                "FROM sales s " +
                "JOIN SaleItem si ON s.id_sale = si.sale_id " +
                "JOIN users u ON s.fk_id_user = u.id_user " +
                "WHERE u.email = ? AND si.item_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userEmail);
            pstmt.setLong(2, productId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // Si hay resultados, el usuario ha comprado el producto
        }
    }



    public List<Review> getReviewsByProductId(long productId) throws SQLException {
        List<Review> reviews = new ArrayList<>();
        conn = new MySQLConnection().connect();
        String query = "SELECT id_review, usuario as name_user, evaluacion, comentario, producto FROM reviews where producto = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review(
                            rs.getLong("id_review"),
                            rs.getString("name_user"),  // Modificado para obtener el nombre de usuario
                            rs.getInt("evaluacion"),
                            rs.getString("comentario"),
                            rs.getLong("producto")
                    );
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }

    public boolean updateEmailAndPassword(String currentEmail, String newEmail, String newPassword) {
        try {
            MySQLConnection mySQLConnection = new MySQLConnection();
            Connection connection = mySQLConnection.connect();

            String updateQuery = "{CALL actualizar_correo_contrasena(?, ?, ?, 'llaveencriptacion')}";
            CallableStatement callableStatement = connection.prepareCall(updateQuery);
            callableStatement.setString(1, currentEmail);
            callableStatement.setString(2, newEmail);
            callableStatement.setString(3, newPassword);
            callableStatement.execute();

            connection.close();
            return true; // Devuelve true si la actualización fue exitosa

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false si hubo un error durante la actualización
        }
    }






    public boolean update(User object) {

        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = new MySQLConnection().connect();
            String query = "UPDATE users SET name_user = ?, lastname = ?, photo = ? WHERE id_user = ?;"; // Se agrega cláusula WHERE
            pstm = conn.prepareStatement(query);
            pstm.setString(1, object.getNames());
            pstm.setString(2, object.getLastnames());
            pstm.setBytes(3, object.getImg_user());
            pstm.setLong(4, object.getId()); // Se establece el valor del ID (suponiendo que el método para obtenerlo es getId())

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error update" + e.getMessage());
            return false;
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, "Error closing resources" + ex.getMessage());
            }
        }
    }



    public boolean delete(Long id) {
        return false;
    }

    public boolean addToCart(User object) {
        return false;
    }

    public User login(String email, String password){
        try{
            conn = new MySQLConnection().connect();
            cs = conn.prepareCall("{call desencriptar_contra (?, ?, 'llaveencriptacion')}");
            cs.setString(1, email);
            cs.setString(2, password);
            boolean result = cs.execute();
            if(result){
                rs = cs.getResultSet();
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id_user"));
                    user.setRol(rs.getInt("rol"));
                    user.setEmail(rs.getString("email"));
                    user.setNames(rs.getString("name_user"));
                    user.setLastnames(rs.getString("lastname"));
                    user.setBirthday(rs.getString("birthday"));
                    user.setUserState(rs.getString("user_state"));
                    user.setGender(rs.getString("sex"));
                    user.getImg_user();
                    rs.close();
                    return user;
                }
                rs.close();
            }
        } catch (SQLException e) {
            Logger.getLogger(DAOUser.class.getName())
                    .log(Level.SEVERE,
                            "Credentials mismatch: " + e.getMessage());
        } finally {
            close();
        }
        return null;
    }


    public void close () {
        try {
            if (conn != null) conn.close();
            if (pstm != null) pstm.close();
            if (rs != null) rs.close();
            if (cs != null) cs.close();
        } catch (SQLException e) {

        }
    }
}