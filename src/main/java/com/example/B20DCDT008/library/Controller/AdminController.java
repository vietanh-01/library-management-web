package com.example.B20DCDT008.library.Controller;

import com.example.B20DCDT008.library.Model.Book;
import com.example.B20DCDT008.library.DAO.bookDAO;
import com.example.B20DCDT008.library.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

@Controller
public class AdminController {

    private bookDAO bookDAO = new bookDAO();

    private static String UPLOAD_IMG = "src/main/resources/static/uploads/";
    private static String DELETE_IMG = "D:/Study/Code/Java_web/library/src/main/resources/static/uploads/";

    //view
    @GetMapping("/admin")
    public String getAdminPage(Model model, HttpSession httpSession) throws IOException {
        List<Book> books = bookDAO.getAllBook();
        model.addAttribute("books", books);
        boolean isAdminLoggin = false;
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("admin")){
            isAdminLoggin = true;
            model.addAttribute("loggedIn", isAdminLoggin);
            model.addAttribute("user", user);
            return "admin";
        }
        else{
            model.addAttribute("loggedIn", isAdminLoggin);
            return "admin";
        }
    }

    @GetMapping("/admin/view/{id}")
    public String getABook(Model model, HttpSession httpSession, @PathVariable String id){
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("admin")){
            Book book = bookDAO.getABookById(Integer.valueOf(id));
            model.addAttribute("book", book);
            
            return "editbook";
        }
        return "error";
    }

    // update
    @PostMapping("/admin/save/{id}")
    public String saveABook(Model model, HttpSession httpSession, @PathVariable String id,
                            @RequestParam("name") String name,
                            @RequestParam("author") String author,
                            @RequestParam("category") String category,
                            @RequestParam("description") String description,
                            @RequestParam("public_day") String public_day,
                            @RequestParam("pages_number") String pages_number,
                            @RequestParam("cover") MultipartFile cover) throws IOException{
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("admin")){
                Book book = new Book();
                book.setId(Integer.valueOf(id));
                book.setName(name);
                book.setAuthor(author);
                book.setCategory(category);
                book.setDescription(description);
                book.setPublic_day(Date.valueOf(public_day));
                book.setPages_number(Integer.valueOf(pages_number));
                if(!cover.isEmpty()){
                    File uploadImg = new File(UPLOAD_IMG);
                    if(!uploadImg.exists()){
                        uploadImg.mkdir();
                    }
                    byte[] bytes = cover.getBytes();
                    Path path = Paths.get(UPLOAD_IMG + cover.getOriginalFilename());
                    Files.write(path, bytes);
                    book.setCover(cover.getOriginalFilename());
                }
                boolean success = bookDAO.updateABook(book);
                if(success)
                    return "redirect:/admin";
        }
        return "error";
    }

    // create
    @GetMapping("/admin/new")
    public String getNewBookPage(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("admin")){
            return "newbook";
        }
        return "error";
    }

    @PostMapping("/admin/new")
    public String getNewBook(HttpSession httpSession,
                             @RequestParam("name") String name,
                             @RequestParam("author") String author,
                             @RequestParam("category") String category,
                             @RequestParam("public_day") Date public_day,
                             @RequestParam("pages_number") String pages_number,
                             @RequestParam("cover") MultipartFile cover,
                             @RequestParam("description") String description) throws IOException {
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("admin")){
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setCategory(category);
            book.setPublic_day(public_day);
            book.setPages_number(Integer.valueOf(pages_number));
            book.setDescription(description);
            if(!cover.isEmpty()){
                File uploadImg = new File(UPLOAD_IMG);
                if(!uploadImg.exists()){
                    uploadImg.mkdir();
                }
                byte[] bytes = cover.getBytes();
                Path path = Paths.get(UPLOAD_IMG + cover.getOriginalFilename());
                Files.write(path, bytes);
                book.setCover(cover.getOriginalFilename());
            }
            if(!bookDAO.checkExsited(book)){
                boolean result = bookDAO.addBook(book);
                if(result == true){
                    return "redirect:/admin";
                }
            }
            else
                return "bookexsited";
        }
        return "error";
    }

    //delete
    @PostMapping("/admin/delete")
    public String deleteABook(Model model, HttpSession httpSession, @RequestParam("id") String id, @RequestParam("cover") String cover) throws IOException {
        User user = (User) httpSession.getAttribute("user");
        if(user != null && user.isLoggedIn() && user.getRole().equals("admin")){
            boolean success = bookDAO.deleteABook(Integer.valueOf(id));
//            Book book = bookDAO.getABookById(Integer.valueOf(id));
            if(success){
//                String folder = "D:/Study/Code/Java_web/library/src/main/resources/static/uploads/";
                String address = DELETE_IMG + cover;
                Path path = Paths.get(address);
                System.out.println("File address: " + address);
                Files.delete(path);
                return "redirect:/admin";
            }
        }
        return "error";
    }


}
