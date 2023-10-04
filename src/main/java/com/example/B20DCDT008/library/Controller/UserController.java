package com.example.B20DCDT008.library.Controller;


import com.example.B20DCDT008.library.DAO.bookDAO;
import com.example.B20DCDT008.library.DAO.orderDAO;
import com.example.B20DCDT008.library.DAO.ratingDAO;
import com.example.B20DCDT008.library.Model.Book;
import com.example.B20DCDT008.library.Model.Order;
import com.example.B20DCDT008.library.Model.Rating;
import com.example.B20DCDT008.library.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Controller

public class UserController {
    private bookDAO bookDAO = new bookDAO();
    private orderDAO orderDAO = new orderDAO();
    private ratingDAO ratingDAO = new ratingDAO();

    //View
    @GetMapping("/user")
    public String home(Model model, HttpSession httpSession) throws IOException {
        List<Book> books = bookDAO.getAllBook();
        model.addAttribute("books", books);
        boolean isUserLoggin = false;
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("user")){
            isUserLoggin = true;
            model.addAttribute("loggedIn", isUserLoggin);
            model.addAttribute("user", user);
            return "user";
        }
        else
            return "redirect:/login";
    }

    @GetMapping("/user/view/{id}")
    public String getABook(Model model, HttpSession httpSession, @PathVariable String id){
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("user")){
            Book book = bookDAO.getABookById(Integer.valueOf(id));
            model.addAttribute("book", book);
            List<Rating> ratings = ratingDAO.getAllRatingOfABook(Integer.valueOf(id));
            model.addAttribute("ratings", ratings);
            model.addAttribute("user", user);
            return "bookdetail";
        }
        return "error";
    }

    // Order
    @GetMapping("/orders")
    public String getOrder(Model model, HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("user")){
            List<Order> orders = orderDAO.getAllOrders(user);
            model.addAttribute("user", user);
            model.addAttribute("orders", orders);
            return "orders";
        }
        return "error";
    }

    @PostMapping("/order/new")
    public String newOrder(Model model, HttpSession httpSession,
                           @RequestParam("id") String id,
                           @RequestParam("name") String name,
                           @RequestParam("quantity") String quantity){
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("user")){
            Order order = new Order();
            order.setBook_id(Integer.valueOf(id));
            order.setBook_name(name);
            order.setQuantity(Integer.valueOf(quantity));
            order.setUser_id(user.getId());
            boolean result = orderDAO.addNewOrder(order);
            if(result){
                return "redirect:/orders";
            }
        }
        return "error";
    }

    @PostMapping("/order/cancel")
    public String cancelOrder(Model model, HttpSession httpSession, @RequestParam("id") String id){
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("user")){
                boolean succes = orderDAO.cancelOrder(Integer.valueOf(id));
                if (succes) {
                    return "redirect:/orders";
            }
        }
        return "error";
    }

    //Rating and Comment
    @PostMapping("/rate")
    public RedirectView newRating(Model model, HttpSession httpSession,
                        @RequestParam("user_id") String user_id,
                        @RequestParam("username") String username,
                        @RequestParam("book_id") String book_id,
                        @RequestParam("userRating") String rating,
                        @RequestParam("userComment") String comment) throws  IOException {
        User user = (User) httpSession.getAttribute("user");
        if (user != null && user.isLoggedIn() && user.getRole().equals("user")) {
            Rating rating2 = new Rating();
            rating2.setBook_id(Integer.valueOf(book_id));
            rating2.setComment(comment);
            rating2.setUser_id(Integer.valueOf(user_id));
            rating2.setUsername(username);
            rating2.setRating(Integer.valueOf(rating));
            boolean result = false;
            int id = ratingDAO.getRatingOfAnUserForABook(Integer.valueOf(book_id), Integer.valueOf(user_id));
            if (id == -1) {
                result = ratingDAO.newRating(rating2);
            } else {
                rating2.setId(id);
                result = ratingDAO.updateRating(rating2);
            }
            String redirectUrl = "/user/view/" + book_id;
            if (result) {
                return new RedirectView(redirectUrl);
            }
        }
        return new RedirectView("/error");
    }
}
