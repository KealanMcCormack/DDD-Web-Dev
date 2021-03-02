package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class MainController {

    Integer productID = 0;
    HashMap<Integer, Product> productList = new HashMap<>();
    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Owner> ownerList = new ArrayList<>();

    Customer loggedInCustomer = null;


    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String gallery(Model model){
        productList.put(productID, new Product(productID,12,"Kealan", "test"));
        productID++;
        productList.put(productID, new Product(productID,2,"Lukas", "test1"));
        productID++;
        productList.put(productID, new Product(productID, 1, "Gerard", "test3"));
        productID++;

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

        if(model instanceof Owner){
            model.addAttribute("owners", ownerList);
        }
        else{
            model.addAttribute("customers", customerList);
        }

        return "login.html";
    }

    @GetMapping("/product/{id}")
    public String productView(Model model, @PathVariable("id") int id){
        Product selectedProduct = productList.get(id);
        if(selectedProduct == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }
        System.out.println(selectedProduct.toString());
        model.addAttribute("product", selectedProduct);
        return "product.html";
    }
}
