package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@Controller
public class MainController {

    Integer productID = 0;
    HashMap<Integer, Product> productList = new HashMap<>();
    HashMap<Integer,Customer> customerList = new HashMap<>();
    HashMap<Integer,Owner> ownerList = new HashMap<>();

    Customer loggedInCustomer = new Customer();

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

    @PostMapping("/cart/add")
    public @ResponseBody String addToCart(@RequestBody int id){
        System.out.println(id);
        loggedInCustomer.addToCart(productList.get(id));
        return "";
    }
    @GetMapping("/customer/{id}")
    public String loginCustomer(Model model, @PathVariable("id") int id){

        Customer customer = customerList.get(id);

        if(customer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        model.addAttribute("customer", customerList);

        return "login.html";
    }

    @GetMapping("/owner/{id}")
    public String loginOwner(Model model, @PathVariable("id") int id){

        Owner owner = ownerList.get(id);
        if(owner == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");
        }
        model.addAttribute("owner", owner);
        return "login.html";
    }

    @GetMapping("/product/{id}")
    public String productView(Model model, @PathVariable("id") int id){
        Product selectedProduct = productList.get(id);
        if(selectedProduct == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }
        model.addAttribute("product", selectedProduct);
        return "product.html";
    }

    @GetMapping("/checkout/{id}")
    public String checkoutView(Model model, @PathVariable("id") int id){
        Customer customer = customerList.get(id);
        if(customer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
        }
        model.addAttribute("customer", customer);
        return "checkout.html";
    }
    @GetMapping("/login")
    public String loginRedirect(){
        return "login.html";
    }



}
