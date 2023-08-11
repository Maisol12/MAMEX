package mx.edu.utez.mamex.models.user;

import mx.edu.utez.mamex.utils.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class UserDao {
    private Connection con;

    public UserDao(Connection con) {
        this.con = con;
    }

    public int getActiveUsersCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM users WHERE user_state = 'active'";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET password = ? WHERE id_user = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, user.getPassword()); // Set the new password
        preparedStatement.setLong(2, user.getId()); // Set the user's ID
        preparedStatement.executeUpdate();
    }



    public User findUserByEmail(String email) {
        User user = null;
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = new User();
                // Suponiendo que tienes estos campos en tu tabla de usuario
                user.setId(result.getLong("id_user"));
                user.setEmail(result.getString("email"));
                // Aquí deberías establecer todos los campos de tu objeto User
                // basado en los datos obtenidos de la consulta SQL.
            }
        } catch (SQLException e) {
            // manejo de excepciones
            e.printStackTrace(); // muestra el error en la consola
        }
        return user;
    }

    public int getTotalUsersCount() {
        int count = 0;
        String sql = "SELECT count(*) FROM users";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public User findUserById(Long id) {
        User user = null;

        // Verificamos si el id es null
        if (id == null) {
            System.out.println("Error: id is null");
            return null;
        }

        String query = "SELECT * FROM users WHERE id_user = ?";

        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                user = new User();
                user.setId(rs.getLong("id_user"));
                user.setNames(rs.getString("name_user"));
                user.setLastnames(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password")); // If the password is stored as a hash, consider not retrieving it
                user.setBirthday(rs.getString("birthday")); // Ensure this is correct, might need to be a Date or formatted differently
                user.setGender(rs.getString("sex"));
                user.setRol(rs.getInt("rol"));
                user.setUserState(rs.getString("user_state"));
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id_user"));
                user.setNames(rs.getString("name_user"));
                user.setLastnames(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                // Aquí deberías añadir más campos según los atributos de tu clase User
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }
}