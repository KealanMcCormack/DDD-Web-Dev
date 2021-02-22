package app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Controller
public class MainController {

    ArrayList<Product> productList = new ArrayList<>();
    ArrayList<Customer> customerList = new ArrayList<>();

    @GetMapping("/")
    public String gallery(Model model){
        productList.add(new Product(1,12,"Kealan", "test"));
        productList.add(new Product(2,0,"Lukas", "test1"));
        productList.add(new Product(3, 1, "Gerard", "test3"));

        model.addAttribute("products", productList);
        return "gallery.html";
    }

    @GetMapping("/createAccount")
    public String createAccountRedirect(){
        return "accountcreation.html";
    }
    @GetMapping("/cart")
    public String cart(){
        return "cart.html";
    }

    @GetMapping("/login")
    public String login(Model model){

       model.addAttribute("customers", customerList);
        return "login.html";
    }
}
