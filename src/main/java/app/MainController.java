package app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MainController {

    ArrayList<Product> productList = new ArrayList<>();

    @GetMapping("/")
    public String gallery(Model model){
        productList.add(new Product(1,12,"Kealan"));
        productList.add(new Product(2,0,"Lukas"));
        productList.add(new Product(3, 1, "Gerard"));

        model.addAttribute("products", productList);
        return "gallery.html";
    }
}
