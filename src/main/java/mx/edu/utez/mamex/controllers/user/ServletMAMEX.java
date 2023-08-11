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
import mx.edu.utez.mamex.models.cart.CartItem;
import mx.edu.utez.mamex.models.sales.Sale;
import mx.edu.utez.mamex.models.sales.SaleDao;


import java.sql.SQLException;
import java.util.ArrayList;
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
        "/user/update-profile",
        "/user/update_view",
        "/user/AboutUs",
        "/user/personal_info",
        "/user/novedades",
        "/user/productDetails",
        "/user/cart",
        "/user/checkout-user",
        "/user/remove-from-cart",
        "/user/review-product",
        "/user/review-view",
        "/user/orders-history"
}) //endpoints para saber a donde redirigir al usuario
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
                    System.out.println(user);
                    if (user != null) {
                        req.setAttribute("user", user);
                        redirect = "/views/user/personal_info.jsp";
                    }else {
                        redirect = null;
                    }
                }
            }
            break;



            case "/user/logout": {
                try {
                    session = req.getSession();
                    if (session != null){
                        session.invalidate();
                    }else{
                        System.out.println("la sesion no existe");
                    }

                    redirect = "/user/mamex?result=" + true
                            + "&message=" + URLEncoder.encode("Sesion cerrada correctamente", StandardCharsets.UTF_8);

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

            case "/user/AboutUs": {
                redirect = "/views/user/nosotros.jsp";
            }
            break;

            case "/user/update-profile": {
                redirect = "/views/user/edit-info.jsp";
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

                // Intentar abrir la conexión a la base de datos
                try (Connection connection = new MySQLConnection().connect()) {
                    System.out.println("Connected to database");

                    // Si el productIdString es nulo o está vacío, establecer un mensaje de error y redirigir
                    if (productIdString == null || productIdString.trim().isEmpty()) {
                        req.setAttribute("errorMessage", "El parámetro productId es nulo o vacío.");
                        req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);
                        return;
                    }

                    // Intentar convertir el productIdString a un número entero
                    try {
                        System.out.println("productId: " + productIdString);
                        int productId = Integer.parseInt(productIdString);

                        // Usar el ID del producto (productId) para obtener los detalles del producto y mostrarlos en la página
                        ItemDao itemDao = new ItemDao(connection);
                        Item item = itemDao.getItemById(productId);
                        System.out.println(item);

                        // Si el item es nulo, entonces no se encontró en la base de datos
                        if (item == null) {
                            req.setAttribute("errorMessage", "El producto solicitado no se encontró.");
                            req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);
                            return;
                        }

                        req.setAttribute("item", item); // Añadir el objeto "item" como atributo de solicitud
                        req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);

                    } catch (NumberFormatException e) {
                        // Manejar la situación cuando el parámetro no es un número válido
                        req.setAttribute("errorMessage", "El parámetro productId no es un número válido.");
                        redirect = "/views/user/productDetails.jsp";
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    // Aquí podrías manejar errores relacionados con la base de datos
                    // Por ejemplo, podrías mostrar un mensaje amigable al usuario
                    req.setAttribute("errorMessage", "Error al acceder a la base de datos. Por favor, inténtalo más tarde.");
                    req.getRequestDispatcher("/views/user/productDetails.jsp").forward(req, resp);
                }
            }
            break;

            //vista para hacer review al producto
            //solo para el pull request :D
            case "/user/review-view":{
                redirect = "/views/user/review_product.jsp";
            }break;

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
                    redirect = "/views/user/cart.jsp";
                } else {
                    // Redirige al usuario a la página de inicio de sesión si no ha iniciado sesión
                    redirect = "/views/user/inicio_sesion.jsp";
                }
                break;
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
            break;


            case "/user/update-profile": {
                try {
                    User user = new User();
                    id = req.getParameter("id");
                    User foundUser = new DAOUser().findOne(id != null ? Long.parseLong(id) : 0L);
                    File oldFile = new File(directory + File.separator + foundUser.getFileName());
                    if(oldFile.exists()) oldFile.delete();
                    for (Part part : req.getParts()) {
                        fileName = part.getSubmittedFileName();
                        if (fileName != null) {
                            mime = part.getContentType().split("/")[1];
                            String uid = UUID.randomUUID().toString();
                            user.setFileName(uid + "." + mime);
                            part.write(directory + File.separator + uid + "." + mime);
                            InputStream stream = part.getInputStream();
                            byte[] arr = stream.readAllBytes();
                            user.setImg_user(arr);
                        }
                    }
                    names = req.getParameter("names");
                    lastnames = req.getParameter("lastnames");
                    email = req.getParameter("email");
                    birthday = req.getParameter("birthday");
                    gender = req.getParameter("gender");
                    user.setId(Long.parseLong(id));
                    user.setNames(names);
                    user.setLastnames(lastnames);
                    user.setBirthday(email);
                    user.setBirthday(birthday);
                    user.setGender(gender);
                    if (new DAOUser().update(user)) {
                        redirect = "/user/profile?result=" + true
                                + "&message=" + URLEncoder.encode("Perfil actualizado correctamente", StandardCharsets.UTF_8);
                    } else {
                        redirect = "/user/profile?result=" + false
                                + "&message=" + URLEncoder.encode("Error al actualizar el perfil", StandardCharsets.UTF_8);
                    }
                } catch (Exception e) {
                    redirect = "/user/update-profile?result=" + false
                            + "&message=" + URLEncoder.encode("Error :/ Acción no realizada correctamente", StandardCharsets.UTF_8);
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

            case "/user/review-product":{
                try{
                    int id_product = Integer.parseInt(req.getParameter("id_product"));
                    int rating = Integer.parseInt(req.getParameter("rating"));
                    String comment = req.getParameter("comment");
                    Review review = new Review(id_product, rating, comment);
                    boolean result = new DAOUser().review(review);
                    if (result) {
                        redirect = "/user/mamex?result=" + true
                                + "&message" + URLEncoder.encode("Exito! Producto calificado correctamente", StandardCharsets.UTF_8);
                    } else {
                        throw new Exception("ERROR");
                    }
                }catch (Exception e){
                    redirect = "/user/mamex?result=" + false
                            + "&message" + URLEncoder.encode("Error :/ Acción no realizada correctamente", StandardCharsets.UTF_8);
                }
            }break;

            default: {
                redirect = "/user/mamex";
            }
            break;
        }
        resp.sendRedirect(req.getContextPath() + redirect);
    }
}