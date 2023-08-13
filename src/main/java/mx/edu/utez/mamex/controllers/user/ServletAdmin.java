package mx.edu.utez.mamex.controllers.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.*;
import mx.edu.utez.mamex.models.items.ItemDao;
import mx.edu.utez.mamex.models.items.Item;
import mx.edu.utez.mamex.models.user.UserDao;
import mx.edu.utez.mamex.utils.MySQLConnection;
import mx.edu.utez.mamex.models.user.User;
import mx.edu.utez.mamex.models.sales.SaleDao;
import mx.edu.utez.mamex.models.sales.Sale;
import mx.edu.utez.mamex.models.cart.Cart;
import mx.edu.utez.mamex.models.cart.CartItem;
import mx.edu.utez.mamex.models.orders.Order;
import mx.edu.utez.mamex.models.orders.OrderDao;
import mx.edu.utez.mamex.models.token.Token;
import java.sql.Connection;
import mx.edu.utez.mamex.models.transactions.TransactionDao;
import mx.edu.utez.mamex.models.sales.SaleItem;
import mx.edu.utez.mamex.services.EmailService;
import mx.edu.utez.mamex.utils.PasswordGenerator;
import java.sql.CallableStatement;
import java.util.Base64;  // Esta importación debería estar en la parte superior de tu archivo junto con las demás importaciones.
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;



import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.SQLException;
import jakarta.servlet.http.*;


@WebServlet(name = "admin", urlPatterns = {"/admin/inicio", "/admin/crear_producto", "/admin/products", "/user/admin/dashboard", "/user/admin/products","/admin/users","/admin/sales","/admin/delete_product","/admin/editar_producto","/admin/orders","/user/checkout","/user/reset_password","/views/user/inicio_sesion","/admin/filterProducts"})
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
            case "/user/admin/dashboard":
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
                MySQLConnection mySQLConnection = new MySQLConnection();
                Connection connection = mySQLConnection.connect();
                loadSalesData(req, resp, connection);
                redirect = "/views/admin/sales.jsp";
            }
            break;

            case "/admin/orders": {
                MySQLConnection mySQLConnection = new MySQLConnection();
                Connection connection = mySQLConnection.connect();
                loadOrdersData(req, resp,connection);
                redirect = "/views/admin/orders.jsp";
            }
            break;

            case "/admin/filterProducts": {
                filterProducts(req, resp);
                redirect = "/views/user/novedades.jsp";  // Asume que quieres mostrar los productos filtrados en esta página
            }
            break;


            default:
                redirect = "/index.jsp";
                break;
        }
        req.getRequestDispatcher(redirect).forward(req, resp);

    }

    private void filterProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String priceRange = request.getParameter("priceRange");

        List<Item> filteredItems = new ArrayList<>();  // Esta lista debe ser llenada con los productos filtrados desde la base de datos

        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());

        if (category != null && !category.isEmpty() && priceRange != null && !priceRange.isEmpty()) {
            filteredItems = itemDao.getFilteredItems(category, priceRange); // Filtra por ambos criterios
        } else if (category != null && !category.isEmpty()) {
            filteredItems = itemDao.getFilteredItemsByCategory(category);  // Solo filtra por categoría
        } else if (priceRange != null && !priceRange.isEmpty()) {
            filteredItems = itemDao.getFilteredItemsByPriceRange(priceRange);  // Solo filtra por rango de precios
        } else {
            redirect = "/views/user/novedades.jsp";  // Asume que quieres mostrar los productos filtrados en esta página
        }

        request.setAttribute("items", filteredItems);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        if ("/admin/crear_producto".equals(action)) {
            createProduct(request, response);
        } else if ("/admin/delete_product".equals(action)) {
            deleteProduct(request, response);
        } else if ("/admin/editar_producto".equals(action)) {
            editProduct(request, response);
        } else if ("/user/checkout".equals(action)) {
            processCheckout(request, response);
        } else if ("/user/reset_password".equals(action)) { // Ruta para restablecer contraseña
            resetPassword(request, response); // Método para restablecer la contraseña
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

    private void resetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String newPassword = PasswordGenerator.generate(10);

        // Generar un token único
        String token = Token.tokenGenerator();
        String encodedEmail = Base64.getEncoder().encodeToString(email.getBytes());

        String subject = "Recuperación de contraseña";
        String recoveryFormURL = "http://localhost:8080/views/user/password-recovery.jsp?token=" + token + "&email=" + encodedEmail;
        String message = "Hola,\n\nHemos recibido una solicitud para restablecer la contraseña de tu cuenta. " +
                "Si no solicitaste esto, puedes ignorar este mensaje. Si deseas restablecer tu contraseña, " +
                "haz clic en el siguiente enlace:\n\n" + recoveryFormURL;

        try {
            MySQLConnection mySQLConnection = new MySQLConnection();
            Connection connection = mySQLConnection.connect();
            String encryptionKey = "llaveencriptacion"; // Reemplazar con tu llave de encriptación

            // Actualizar la contraseña en la base de datos
            String updatePasswordQuery = "{CALL actualizar_contrasena(?, ?, ?)}";
            CallableStatement callableStatement = connection.prepareCall(updatePasswordQuery);
            callableStatement.setString(1, email);
            callableStatement.setString(2, newPassword);
            callableStatement.setString(3, encryptionKey);
            callableStatement.execute();

            // Guardar el token y el correo en una base de datos temporal o en memoria

            connection.close();

            // Envío de correo
            EmailService emailService = new EmailService();
            emailService.sendEmail(email, subject, message);

            // Redirección a la página de inicio de sesión con un mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/views/user/inicio_sesion.jsp?password_reset=success");
        } catch (SQLException e) {
            e.printStackTrace();
            // Redirección a la página de inicio de sesión con un mensaje de error en caso de excepción
            response.sendRedirect(request.getContextPath() + "/views/user/inicio_sesion.jsp?password_reset=error");
        }
    }






    public void processCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null && session.getAttribute("email") != null) {
            // Get the user ID from the session
            Long id = (Long) session.getAttribute("id");

            // Get the cart from the session
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null && !cart.isEmpty()) {
                try {
                    MySQLConnection mySQLConnection = new MySQLConnection();
                    Connection connection = mySQLConnection.connect();

                    UserDao userDao = new UserDao(connection);
                    user = userDao.findUserById(id);  // Now just assign the user object here
                    if(user != null) {
                        // Convert java.util.Date to java.sql.Timestamp
                        java.util.Date utilDate = new java.util.Date();
                        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());

                        // Create a single sale for all cart items
                        Sale sale = new Sale();
                        sale.setQuantitySale(cart.getTotalQuantity());
                        sale.setSubtotal(cart.getTotalPrice());
                        sale.setSaleState("Pendiente");
                        sale.setSlDateCreate(sqlTimestamp);
                        sale.setSlDateUpdate(sqlTimestamp);
                        sale.setNumberSale(generateSaleNumber()); // Generate a unique sale number
                        sale.setUser(user);

                        // Create a SaleItem for each CartItem and add to sale
                        for (CartItem cartItem : cart.getItems()) {
                            SaleItem saleItem = new SaleItem();
                            saleItem.setItem(cartItem.getItem());
                            saleItem.setQuantity(cartItem.getQuantity());
                            sale.addSaleItem(saleItem);
                        }

                        // Save the sale in the database
                        SaleDao saleDao = new SaleDao(connection);
                        int saleId = saleDao.createSale(sale);

                        // Create a single order for the sale
                        Order order = new Order();
                        order.setFkIdUser(id.intValue());
                        order.setFkIdSale(saleId);
                        order.setState("PENDIENTE");
                        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                        order.setDate(sqlDate);
                        order.setUpdateDate(sqlDate);

                        OrderDao orderDao = new OrderDao(connection);
                        orderDao.saveOrder(order);

                        // Empty the cart
                        cart.clear();

                        // Update the sales and orders data for the admin
                        loadSalesData(request, response, connection);
                        loadOrdersData(request, response, connection);  // You will need to create this method
                    }

                    // Close the database connection
                    connection.close();
                } catch (SQLException e) {
                    // code to handle the exception
                    e.printStackTrace();
                }

                if (user != null) {
                    String userEmail = user.getEmail();
                    String subject = "Confirmación de compra a MAMEX";
                    String imagePath = request.getServletContext().getRealPath("/assets/img/Pago.jpeg"); // Ruta absoluta a la imagen

                    String htmlMessage = "¡Gracias por tu compra!";

                    EmailService emailService = new EmailService();
                    emailService.sendEmailWithImage(userEmail, subject, htmlMessage, imagePath);


                    // Redirect the user to the index page
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            }
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String color = request.getParameter("color");
        double unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
        int stock = Integer.parseInt(request.getParameter("stock"));

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

        // Aquí está el problema, el constructor de Item no incluye las imágenes en sus parámetros
        Item item = new Item(id, name, description, color, unitPrice, stock);

        // Actualizar las imágenes del objeto Item
        item.setImages(imagesMap);
        item.setBase64Images(base64ImagesMap);

        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());

        boolean result = itemDao.updateItem(item);

        if (result) {
            response.sendRedirect(request.getContextPath() + "/admin/products?result=success");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/products?result=error");
        }
    }

    private void loadUsersData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao(new MySQLConnection().connect());
        List<User> users = userDao.getAllUsers();
        request.setAttribute("users", users);
    }

    public void loadSalesData(HttpServletRequest request, HttpServletResponse response, java.sql.Connection connection) throws ServletException, IOException {
        SaleDao saleDao = new SaleDao(new MySQLConnection().connect());
        try {
            List<Sale> sales = saleDao.getAllSales();
            if (sales == null) {
                // Handle the case where sales is null
                sales = new ArrayList<>();
            }
            request.setAttribute("sales", sales);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadOrdersData(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException {
        OrderDao orderDao = new OrderDao(new MySQLConnection().connect());
        List<Order> orders = orderDao.getAllOrders();
        if (orders == null) {
            // Handle the case where orders is null
            orders = new ArrayList<>();
        }
        request.setAttribute("orders", orders);
    }

    private void loadProductsData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
        List<Item> items = itemDao.getAllItems();
        request.setAttribute("items", items);
    }

    private int generateSaleNumber() {
        return (int) (new Date().getTime() / 1000); // Example using timestamp, you can replace with another mechanism
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
        SaleDao saleDao = new SaleDao(new MySQLConnection().connect());

        // Primero, eliminar las referencias en SaleItem
        if (saleDao.deleteSaleItemReferences(id)) {
            // Si se eliminaron las referencias con éxito, eliminar el ítem
            boolean success = itemDao.deleteItem(id);
            if (success) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date createDate;
        java.sql.Date updateDate;
        String category = request.getParameter("category");

        try {
            java.util.Date parsedCreateDate = dateFormat.parse(createDateStr);
            createDate = new java.sql.Date(parsedCreateDate.getTime());
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
                stock,
                notes,
                imagesMap,
                base64ImagesMap,
                category
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