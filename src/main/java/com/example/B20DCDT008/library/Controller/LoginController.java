package com.example.B20DCDT008.library.Controller;

import com.example.B20DCDT008.library.DAO.userDAO;
import com.example.B20DCDT008.library.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@RequestMapping("/")
@Controller
public class LoginController {
    private com.example.B20DCDT008.library.DAO.userDAO userDAO = new userDAO();

    @GetMapping("/login")
    public String loginPage(Model model, HttpSession httpSession) throws IOException {
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn()){
            if(user.getRole().equals("user"))
                return "redirect:/user";
            else if(user.getRole().equals("admin"))
                return "redirect:/admin";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpSession httpSession, @RequestParam("username") String username, @RequestParam("password") String password, Model model) throws IOException{
        User user = userDAO.checkLogin(username, password);
        if(user.getId() != -1){
            user.setLoggedIn(true);
            httpSession.setAttribute("user", user);
             if(user.getRole().equals("user"))
                 return "redirect:/user";
             else if(user.getRole().equals("admin"))
                 return "redirect:/admin";
        }
        return "loginincorrect";
    }

    @GetMapping("/register")
    public String registerPage(Model model) throws IOException {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("username") String username, @RequestParam("password") String password, Model model) throws IOException{
        boolean checkExsited = userDAO.checkExist(username, email);
        if(checkExsited) {
            return "userexist";
        }
        else{
            User user = new User();
            user.setName(name);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            boolean registerSuccess = userDAO.registerUser(user);
            if(registerSuccess){
                return "registersuccess";
            }
        }
        return "error";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession, Model model) throws  IOException{
        User user = (User) httpSession.getAttribute("user");
        if(user != null){
            user.setLoggedIn(false);
        }
        httpSession.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/user-profile")
    public String getInfo(HttpSession httpSession, Model model) throws IOException{
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("user", user);
        return "user-info";
    }

}
