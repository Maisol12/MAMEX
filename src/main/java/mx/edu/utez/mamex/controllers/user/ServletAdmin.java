package mx.edu.utez.mamex.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import mx.edu.utez.mamex.models.items.ItemDao;
import mx.edu.utez.mamex.models.items.Item;
import mx.edu.utez.mamex.models.user.UserDao;
import mx.edu.utez.mamex.models.transactions.TransactionDao;
import mx.edu.utez.mamex.utils.MySQLConnection;
import mx.edu.utez.mamex.models.user.User;
import mx.edu.utez.mamex.models.sales.SaleDao;
import mx.edu.utez.mamex.models.sales.Sale;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.SQLException;





@WebServlet(name = "admin", urlPatterns = {"/admin/inicio", "/admin/crear_producto", "/admin/products", "/user/admin/dashboard", "/user/admin/products","/admin/users","/admin/sales","/admin/delete_product"})
@MultipartConfig
public class ServletAdmin extends HttpServlet {
    private String action;
    private String redirectAdmin = "/admin/inicio";
    private String redirect;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();
        switch (action) {
            case "/admin/inicio":
            case "/user/admin/dashboard":  // Añade esta línea
            {
                loadInicioData(req, resp);
                redirect = "/views/admin/inicio.jsp";
            }
            break;

            case "/admin/products":
            case "/user/admin/products": {
                loadProductsData(req, resp);
                redirect = "/views/admin/products.jsp";
            }
            break;

            case "/admin/users": {
                loadUsersData(req, resp);
                redirect = "/views/admin/users.jsp";
            }
            break;

            case "/admin/sales": {
                loadSalesData(req, resp);
                redirect = "/views/admin/sales.jsp";
            }
            break;


            default:

                break;
        }
        req.getRequestDispatcher(redirect).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if ("/admin/crear_producto".equals(action)) {
            createProduct(request, response);
        } else if ("/admin/delete_product".equals(action)) {
            deleteProduct(request, response);
        }
        }

    private void loadInicioData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao(new MySQLConnection().connect());
        TransactionDao transactionDao = new TransactionDao(new MySQLConnection().connect());

        int activeUsersCount = userDao.getActiveUsersCount();
        int totalUsersCount = userDao.getTotalUsersCount();
        double totalEarnings = transactionDao.getTotalEarnings();

        request.setAttribute("activeUsersCount", activeUsersCount);
        request.setAttribute("totalUsersCount", totalUsersCount);
        request.setAttribute("totalEarnings", totalEarnings);

    }

    /*private void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtén los nuevos datos del producto desde el request
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String color = request.getParameter("color");
        double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String notes = request.getParameter("notes");
        // Aquí puedes agregar cualquier otro campo que desees editar

        // Crea un nuevo objeto Item con los datos obtenidos
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setColor(color);
        item.setUnitPrice(unitPrice);
        item.setStock(stock);
        item.setNotes(notes);
        // Aquí puedes establecer cualquier otro campo que desees editar

        // Usa el DAO para actualizar el producto en la base de datos
        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
        boolean success = itemDao.updateItem(item);

        // En lugar de redirigir, envía una respuesta con el resultado de la operación
        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }*/

    private void loadUsersData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao(new MySQLConnection().connect());
        List<User> users = userDao.getAllUsers();
        request.setAttribute("users", users);
    }

    private void loadSalesData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SaleDao saleDao = new SaleDao(new MySQLConnection().connect());
        try {
            List<Sale> sales = saleDao.getAllSales();
            request.setAttribute("sales", sales);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadProductsData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
        List<Item> items = itemDao.getAllItems();
        request.setAttribute("items", items);
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
        boolean success = itemDao.deleteItem(id);

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String available = request.getParameter("available");
        String color = request.getParameter("color");
        double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
        String createDateStr = request.getParameter("createDate");
        String updateDateStr = request.getParameter("updateDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date createDate;
        java.sql.Date updateDate;

        try {
            java.util.Date parsedCreateDate = dateFormat.parse(createDateStr);
            java.util.Date parsedUpdateDate = dateFormat.parse(updateDateStr);
            createDate = new java.sql.Date(parsedCreateDate.getTime());
            updateDate = new java.sql.Date(parsedUpdateDate.getTime());
        } catch (ParseException e) {
            response.sendRedirect(request.getContextPath() + "/views/admin/crear_producto.jsp?result=error");
            return;
        }

        int stock = Integer.parseInt(request.getParameter("stock"));
        String notes = request.getParameter("notes");

        List<Part> fileParts = new ArrayList<>(Arrays.asList(request.getPart("image1"), request.getPart("image2"), request.getPart("image3")));
        fileParts.removeIf(part -> part == null || part.getSize() == 0);

        Map<String, byte[]> imagesMap = new HashMap<>();
        Map<String, String> base64ImagesMap = new HashMap<>();
        int imageIndex = 1;
        for (Part filePart : fileParts) {
            byte[] imageBytes = new byte[(int) filePart.getSize()];
            filePart.getInputStream().read(imageBytes);
            imagesMap.put("image" + imageIndex, imageBytes);
            base64ImagesMap.put("image" + imageIndex, Base64.getEncoder().encodeToString(imageBytes));
            imageIndex++;
        }

        Item item = new Item(
                0,
                name,
                description,
                available,
                color,
                unitPrice,
                new java.sql.Date(createDate.getTime()),
                new java.sql.Date(updateDate.getTime()),
                stock,
                notes,
                imagesMap,
                base64ImagesMap
        );

        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());

        boolean result = itemDao.saveItem(item);

        if (result) {
            response.sendRedirect(request.getContextPath() + "/admin/products?result=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/products?result=error");
        }
    }
}












