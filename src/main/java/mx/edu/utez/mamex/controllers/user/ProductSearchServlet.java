package mx.edu.utez.mamex.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.mamex.models.items.Product;
import mx.edu.utez.mamex.utils.MySQLConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductSearchServlet", urlPatterns = {"/user/search"})
public class ProductSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("searchQuery");

        // Validación básica
        if (query == null || query.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid search query.");
            return;
        }

        List<Product> products = searchProducts(query);
        JSONArray productArray = new JSONArray();
        for (Product product : products) {
            JSONObject productObject = new JSONObject();
            productObject.put("id", product.getId());
            productObject.put("name", product.getName());
            productObject.put("description", product.getDescription());
            productArray.add(productObject);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(productArray.toJSONString());
    }

    private List<Product> searchProducts(String query) {
        List<Product> products = new ArrayList<>();

        // Utiliza MySQLConnection para obtener una conexión
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.connect();
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {
                // Prepara la consulta SQL
                String sql = "SELECT id_item, name_item, description_item FROM items WHERE CONCAT(name_item, ' ', description_item) LIKE ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, "%" + query + "%");

                // Ejecuta la consulta y procesa el ResultSet
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id_item");
                    String name = resultSet.getString("name_item");
                    String description = resultSet.getString("description_item");
                    products.add(new Product(id, name, description)); // Asegúrate de que el modelo Product tenga un constructor que acepte id, name y description
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return products;
    }
}

