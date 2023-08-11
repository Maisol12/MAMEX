package mx.edu.utez.mamex.controllers.user;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.*;
import mx.edu.utez.mamex.models.Review;
import mx.edu.utez.mamex.models.user.DAOUser;
import mx.edu.utez.mamex.models.user.User;
import mx.edu.utez.mamex.models.items.ItemDao;
import mx.edu.utez.mamex.utils.MySQLConnection;
import mx.edu.utez.mamex.models.items.Item;
import mx.edu.utez.mamex.models.cart.Cart;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Base64;
import java.util.Map;
import java.util.Arrays;
import mx.edu.utez.mamex.models.cart.CartItem;
import mx.edu.utez.mamex.models.sales.Sale;
import mx.edu.utez.mamex.models.sales.SaleDao;
import java.util.HashMap;
import mx.edu.utez.mamex.models.Review;



import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.servlet.annotation.MultipartConfig;
import java.util.List;
import java.util.stream.Collectors;




import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.Date;



@WebServlet(name = "users", urlPatterns = {
        "/user/mamex",
        "/user/register",
        "/user/login",
        "/user/register-successfull",
        "/user/register-view",
        "/user/contacto",
        "/user/add-to-cart",
        "/user/view-cart",
        "/user/go-to-pay",
        "/user/logout",
        "/user/profile",
        "/user/update_view",
        "/user/update-profile",
        "/user/update",
        "/user/AboutUs",
        "/user/personal_info",
        "/user/novedades",
        "/user/productDetails",
        "/user/cart",
        "/user/checkout-user",
        "/user/remove-from-cart",
        "/user/review-product",
        "/user/categorias",
        "/user/email-edit",
        "/user/historial_compras",
        "/user/review-view"
}) //endpoints para saber a donde redirigir al usuario
@MultipartConfig
public class ServletMAMEX extends HttpServlet {
    private String action;
    private String redirect = "/user/mamex";
    ;
    private String id, names, lastnames, email, password, birthday, gender, img_user;
    //private Blob img_user;
    private String mime, fileName;
    //LINUX - "/"
    private String directory = "D:" + File.separator + "mamex";
    private String id_product, quantity;
    private double cost;
    HttpSession session;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        action = req.getServletPath();

        switch (action) {
            case "/user/mamex": //redirigir al inicio
            {
                redirect = "/index.jsp";
            }
            break;

            case "/user/login": //una vez registrado te llevara a iniciar sesion
            {
                session = req.getSession(false);
                if (session.getAttribute("email") != null) {
                    session.setAttribute("email", email);
                    redirect = "/user/profile";
                } else {
                    redirect = "/views/user/inicio_sesion.jsp";
                }
            }
            break;

            case "/user/personal_info": {
                session = req.getSession(false);
                if (session != null && session.getAttribute("email") != null) {
                    String userEmail = (String) session.getAttribute("email");
                    DAOUser daoUser = new DAOUser();
                    User user = daoUser.findUserByEmail(userEmail);
                    if (user != null) {
                        req.setAttribute("user", user);

                        // Load user's profile image and convert it to base64
                        byte[] profileImageBytes = user.getImg_user(); // Replace with your actual method
                        if (profileImageBytes != null && profileImageBytes.length > 0) {
                            String base64Image = Base64.getEncoder().encodeToString(profileImageBytes);
                            req.setAttribute("base64Image", base64Image);
                        }

                        redirect = "/views/user/personal_info.jsp";
                    }
                }
            }
            break;


            case "/user/logout": {
                try {
                    session = req.getSession();
                    session.invalidate();

                    redirect = "/user/mamex?result =" + true
                            + "&message" + URLEncoder.encode("Sesion cerrada correctamente", StandardCharsets.UTF_8);
                    ;
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    redirect = "/user/mamex?result=" + false
                            + "&message" + URLEncoder.encode("Credentials Missmatch", StandardCharsets.UTF_8);
                }
            }
            break;

            case "/user/register-view": //una vez registrado te llevara a iniciar sesion
            {
                redirect = "/views/user/registro_usuarios.jsp";
            }
            break;

            case "/user/contacto": {
                redirect = "/views/user/contacto.jsp";
            }
            break;

            case "/user/view-cart": {
                // Asegúrate de que el usuario ha iniciado sesión
                HttpSession session = req.getSession(false);
                if (session != null && session.getAttribute("email") != null) {
                    redirect = "/views/user/cart.jsp";
                } else {
                    // Redirige al usuario a la página de inicio de sesión si no ha iniciado sesión
                    redirect = "/views/user/inicio_sesion.jsp";
                }
                break;
            }


            case "/user/go-to-pay": {
                redirect = "/views/user/payment.jsp";
            }
            break;

            case "/user/profile": {
                redirect = "/views/user/profile.jsp";
            }
            break;

            case "/user/email-edit": {
                session = req.getSession(false);
                if (session != null && session.getAttribute("email") != null) {
                    String userEmail = (String) session.getAttribute("email");
                    DAOUser userDAO = new DAOUser();
                    User user = userDAO.findUserByEmail(userEmail);
                    if (user != null) {
                        req.setAttribute("user", user);
                        redirect = "/views/user/email-edit.jsp";
                    }
                }
            }
            break;


            case "/user/historial_compras": {
                session = req.getSession(false);
                if (session != null && session.getAttribute("email") != null) {
                    Long userId = (Long) session.getAttribute("id");

                    // Recuperar el historial de compras del usuario
                    try (Connection connection = new MySQLConnection().connect()) {
                        SaleDao saleDao = new SaleDao(connection);
                        List<Sale> userSales = saleDao.getSalesForUser(userId);

                        // Configura las ventas como un atributo de solicitud para que puedas acceder a ellas en el JSP
                        req.setAttribute("userPurchaseHistory", userSales);
                        redirect = "/views/user/historial_compras.jsp"; // Aquí debes poner la ubicación de tu JSP que mostrará el historial de compras
                    } catch (SQLException e) {
                        e.printStackTrace();
                        req.setAttribute("errorMessage", "Error al acceder al historial de compras. Por favor, inténtalo más tarde.");
                        redirect = "/views/user/error_page.jsp"; // Puedes redirigir a una página de error personalizada si lo prefieres
                    }
                } else {
                    // Redirige al usuario a la página de inicio de sesión si no ha iniciado sesión
                    redirect = "/views/user/inicio_sesion.jsp";
                }
            }
            break;


            case "/user/categorias": {

                // Obtener todos los productos de la base de datos
                ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
                List<Item> allProducts = itemDao.getAllItems();

                // Crear un mapa para agrupar productos por categoría
                Map<String, List<Item>> productsByCategory = new HashMap<>();
                for(Item product : allProducts) {
                    productsByCategory
                            .computeIfAbsent(product.getCategory(), k -> new ArrayList<>())
                            .add(product);
                }

                // Añadir el mapa como un atributo a la solicitud
                req.setAttribute("productsByCategory", productsByCategory);

                // Luego, reenviar la solicitud a la vista JSP que mostrará los productos en carruseles
                req.getRequestDispatcher("/views/user/categorias.jsp").forward(req, resp);
                return;

            }


            case "/user/AboutUs": {
                redirect = "/views/user/nosotros.jsp";
            }
            break;

            case "/user/update_view": {
                session = req.getSession(false);
                if (session != null && session.getAttribute("email") != null) {
                    String userEmail = (String) session.getAttribute("email");
                    DAOUser daoUser = new DAOUser();
                    User user = daoUser.findUserByEmail(userEmail);
                    if (user != null) {
                        req.setAttribute("user", user);

                        // Verificar si la imagen no es null y convertirla a Base64
                        byte[] imageBytes = user.getImg_user();
                        if (imageBytes != null) {
                            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                            req.setAttribute("base64Image", base64Image);
                        } else {
                            // Puedes establecer una imagen predeterminada o manejar este caso de alguna otra manera.
                            req.setAttribute("base64Image", "data:image/jpeg;base64,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");
                        }

                        redirect = "/views/user/edit-info.jsp";
                    } else {
                        redirect = "/views/user/error_page.jsp"; // Redirige a una página de error si no se encuentra al usuario.
                    }
                } else {
                    redirect = "/views/user/inicio_sesion.jsp"; // Redirige al usuario a la página de inicio de sesión si no ha iniciado sesión.
                }
            }
            break;




            case "/user/novedades": {
                ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
                List<Item> items = itemDao.getAllItems();
                req.setAttribute("items", items);
                redirect = "/views/user/novedades.jsp";
            }break;

            case "/user/productDetails": {
                redirect = "/views/user/productDetails.jsp";
                System.out.println("Servlet invoked");

                // Obtener el valor del parámetro "productId" de la solicitud
                String productIdString = req.getParameter("id");
                if (productIdString == null || productIdString.trim().isEmpty()) {
                    req.setAttribute("errorMessage", "El parámetro productId es nulo o vacío.");
                    req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);
                    return;
                }

                // Intentar convertir el productIdString a un número entero
                try {
                    int productId = Integer.parseInt(productIdString);

                    // Usar el ID del producto (productId) para obtener los detalles del producto y mostrarlos en la página
                    try (Connection connection = new MySQLConnection().connect()) {
                        ItemDao itemDao = new ItemDao(connection);
                        Item item = itemDao.getItemById(productId);
                        if (item == null) {
                            req.setAttribute("errorMessage", "El producto solicitado no se encontró.");
                            req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);
                            return;
                        }
                        req.setAttribute("item", item);
                        req.setAttribute("productId", productId);

                        // Obtener y configurar las reviews del producto
                        DAOUser daoUser = new DAOUser();
                        List<Review> reviews = daoUser.getReviewsByProductId(productId);
                        req.setAttribute("reviews", reviews);

                        req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);
                        return;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        req.setAttribute("errorMessage", "Error al acceder a la base de datos. Por favor, inténtalo más tarde.");
                        req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);
                        return;
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("errorMessage", "El parámetro productId no es un número válido.");
                    req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);
                    return;
                }
            }



            default: {
                System.out.println(action);
            }
            break;
        }
        req.getRequestDispatcher(redirect).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        action = req.getServletPath();
        switch (action) {
            case "/user/register":
                try {
                    names = req.getParameter("names");
                    lastnames = req.getParameter("lastnames");
                    email = req.getParameter("email");
                    password = req.getParameter("password");
                    User user = new User(0L, names, lastnames, email, password);
                    boolean result = new DAOUser().save(user);
                    if (result) {
                        redirect = "/user/login?result=" + true
                                + "&message" + URLEncoder.encode("Exito! Usuario registrado correctamente", StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("ERROR");
                    } //url cambiada :)
                } catch (Exception e) {
                    redirect = "/user/mamex?result=" + false
                            + "&message" + URLEncoder.encode("Error :/ Acción no realizada correctamente", StandardCharsets.UTF_8);
                }
                break;

            case "/user/login": {
                try {
                    email = req.getParameter("email");
                    password = req.getParameter("password");
                    User user = new DAOUser().login(email, password);
                    if (user != null) {
                        session = req.getSession(true); // Create a new session
                        session.setAttribute("email", user.getEmail()); // Set the email from the user object
                        session.setAttribute("id", user.getId());
                        if (user.getRol() == 1) {
                            redirect = "/user/admin/dashboard?result=" + true
                                    + "&message" + URLEncoder.encode("Inicio de sesion correctamente administrador! :D" + user.getNames(), StandardCharsets.UTF_8);
                        } else {
                            redirect = "/user/mamex?result=" + true
                                    + "&message" + URLEncoder.encode("Inicio de sesion correctamente! :D" + user.getNames(), StandardCharsets.UTF_8);

                        }
                    } else {
                        redirect = "/user/login?result=" + false
                                + "&message" + URLEncoder.encode("Usuario o contraseña incorrectos", StandardCharsets.UTF_8);
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.println(email + " " + password);
                    System.out.println(e);
                    redirect = "/user/mamex?result=" + false
                            + "&message" + URLEncoder.encode("Credentials Missmatch", StandardCharsets.UTF_8);
                }
            }
            break;

            case "/user/add-to-cart": {
                // Asegúrate de que el usuario ha iniciado sesión
                HttpSession session = req.getSession(false);
                if (session != null && session.getAttribute("email") != null) {
                    // Obtén el ID del ítem y la cantidad de la solicitud
                    int itemId = Integer.parseInt(req.getParameter("itemId"));
                    int quantity = Integer.parseInt(req.getParameter("quantity"));

                    // Obtén el carrito de la sesión o crea uno nuevo si no existe
                    Cart cart = (Cart) session.getAttribute("cart");
                    if (cart == null) {
                        cart = new Cart();
                        session.setAttribute("cart", cart);
                    }

                    // Agrega el ítem al carrito
                    ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
                    Item item = itemDao.getItemById(itemId);
                    cart.addItem(item, quantity);

                    // Redirige al usuario a la página del carrito
                    redirect = "/views/user/carrito.jsp";
                } else {
                    // Redirige al usuario a la página de inicio de sesión si no ha iniciado sesión
                    redirect = "/views/user/inicio_sesion.jsp";
                }
                break;
            }
            case "/user/email-edit": {
                String newEmail = req.getParameter("email");
                String newPassword = req.getParameter("password");
                System.out.println("newEmail: " + newEmail);
                System.out.println("newPassword: " + newPassword);

                HttpSession session = req.getSession(false);
                if (session != null && session.getAttribute("email") != null) {
                    String currentEmail = (String) session.getAttribute("email");
                    DAOUser userDAO = new DAOUser();
                    boolean success = userDAO.updateEmailAndPassword(currentEmail, newEmail, newPassword);

                    if (success) {
                        // Actualizar la sesión con el nuevo correo si se actualizó correctamente
                        session.setAttribute("email", newEmail);
                        resp.sendRedirect("/views/user/profile.jsp?success=true");
                        return;  // Salir del método doPost
                    } else {
                        // Si la actualización falla, redirige a la misma página de edición con un mensaje de error
                        resp.sendRedirect("/views/user/email-edit.jsp?error=true");
                        return;  // Salir del método doPost
                    }
                } else {
                    // Si no ha iniciado sesión, redirige a la página de inicio de sesión
                    resp.sendRedirect("/views/user/inicio_sesion.jsp");
                    return;  // Salir del método doPost
                }
            }





            case "/user/remove-from-cart": {
                // Asegúrate de que el usuario ha iniciado sesión
                HttpSession session = req.getSession(false);
                if (session != null && session.getAttribute("email") != null) {
                    // Obtén el ID del ítem de la solicitud
                    int itemId = Integer.parseInt(req.getParameter("itemId"));

                    // Obtén el carrito de la sesión
                    Cart cart = (Cart) session.getAttribute("cart");
                    if (cart != null) {
                        // Encuentra el ítem en el carrito y remuévelo
                        ItemDao itemDao = new ItemDao(new MySQLConnection().connect());
                        Item item = itemDao.getItemById(itemId);
                        cart.removeItem(item);
                    }

                    // No redirigir aquí, ya que la redirección debe estar fuera del switch
                } else {
                    // Redirige al usuario a la página de inicio de sesión si no ha iniciado sesión
                    redirect = "/views/user/inicio_sesion.jsp";
                }
            }
            req.getRequestDispatcher(redirect).forward(req, resp);
            return;


            case "/user/update": {
                try {
                    session = req.getSession(false);
                    if (session != null && session.getAttribute("email") != null) {
                        long userId = Long.parseLong(req.getParameter("userId"));
                        // Obtener los datos del formulario
                        String names = req.getParameter("names");
                        String lastnames = req.getParameter("lastnames");

                        // Imprimir los valores obtenidos para verificar

                        // ... (obtener cualquier otro dato que necesites actualizar)

                        // Crear un objeto User con los datos obtenidos
                        User user = new User();
                        user.setId(userId);
                        user.setNames(names);
                        user.setLastnames(lastnames);
                        Part filePart = req.getPart("profilePic");
                        if (filePart != null && filePart.getSize() > 0) {
                            InputStream fileContent = filePart.getInputStream();
                            System.out.println("Si entro al if");
                            byte[] imageBytes = new byte[(int) filePart.getSize()];
                            fileContent.read(imageBytes, 0, imageBytes.length);
                            user.setImg_user(imageBytes);
                        }


                        DAOUser daoUser = new DAOUser();

                        if(daoUser.update(user)) {
                            redirect = "/user/profile?result=true&message=" + URLEncoder.encode("Perfil actualizado correctamente", StandardCharsets.UTF_8);
                        } else {
                            redirect = "/user/update_view?result=false&message=" + URLEncoder.encode("Error al actualizar el perfil", StandardCharsets.UTF_8);
                        }
                    } else {
                        redirect = "/views/user/inicio_sesion.jsp"; // Redirige al usuario a la página de inicio de sesión si no ha iniciado sesión.
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                    redirect = "/user/update_view?result=false&message=" + URLEncoder.encode("Error al actualizar el perfil", StandardCharsets.UTF_8);

                }
            }
            break;



            case "/user/go-to-pay":{
                try {
                    //traer el carrito de compras con los productos para despues mandar un correo electronico con los productos
                    //el costo total y la informacion de pago.
                }catch (Exception e){
                }
            }break;

            case "/user/review-product": {
                try {
                    // Verificación del productId
                    String productIdStr = req.getParameter("productId");
                    System.out.println("productId recibido: " + productIdStr);
                    if (productIdStr == null || productIdStr.trim().isEmpty()) {
                        throw new Exception("El ID del producto no fue proporcionado o es inválido.");
                    }
                    long productId = Long.parseLong(productIdStr);

                    // El resto del código de procesamiento de revisión
                    String userEmail = (String) req.getSession().getAttribute("email");
                    if (userEmail == null || userEmail.trim().isEmpty()) {
                        throw new Exception("No se encontró el correo electrónico en la sesión");
                    }
                    DAOUser daoUser = new DAOUser();
                    User user = daoUser.findUserByEmail(userEmail);
                    if (user == null) {
                        throw new Exception("Usuario no encontrado con el correo electrónico proporcionado");
                    }
                    String username = user.getNames();

                    int rating = Integer.parseInt(req.getParameter("rating"));
                    String comment = req.getParameter("comment");

                    Review review = new Review(0L, username, rating, comment, productId);
                    boolean result = new DAOUser().submitReview(review, userEmail);



                    if (result) {
                        redirect = "/user/mamex?result=true"
                                + "&message=" + URLEncoder.encode("Éxito! Producto calificado correctamente", StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("ERROR-NO HAS COMPRADO EL PRODUCTO");
                    }
                } catch (Exception e) {
                    Logger.getLogger(ServletMAMEX.class.getName()).log(Level.SEVERE, "Error al procesar review", e);
                    redirect = "/user/mamex?result=false"
                            + "&message=" + URLEncoder.encode("Error :/ Acción no realizada correctamente", StandardCharsets.UTF_8);
                }
            }
            break;




            default: {
                redirect = "/user/mamex";
            }
            break;
        }
        resp.sendRedirect(req.getContextPath() + redirect);
    }
}