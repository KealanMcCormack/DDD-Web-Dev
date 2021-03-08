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
    int customerID = 0;
    int ownerID = 0;
//    HashMap<Integer, Product> productList = new HashMap<>();
//    HashMap<String,Customer> customerList = new HashMap<>(); // Key = username
//    HashMap<String,Owner> ownerList = new HashMap<>(); // Key = username

    Customer loggedInCustomer = null;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/")
    public String gallery(Model model){

        /*These are just for testing purposes*/
        productRepository.save(new Product(productID,12,"Kealan", "test"));
        productID++;
        productRepository.save(new Product(productID,2,"Lukas", "test1"));
        productID++;
        productRepository.save(new Product(productID, 1, "Gerard", "test3"));
        productID++;

        model.addAttribute("products", productRepository.findAll());
        return "gallery.html";
    }

    @GetMapping("/createAccount")
    public String createAccountRedirect(){
        return "accountcreation.html";
    }

    //Creates Customer Account
    @PostMapping("/createCustomer")
    public @ResponseBody String createCustomer(@RequestBody String username, @RequestBody String password){
            Customer newCustomer = new Customer(username, password, customerID);
            customerID++;
            customerRepository.save(newCustomer);
            return "Success";
    }

    //Creates Owner Account
    @PostMapping("/createOwner")
    public @ResponseBody String createOwner(@RequestBody String username, @RequestBody String password){
        Owner newOwner = new Owner(username, password, ownerID);
        ownerID++;
        ownerRepository.save(newOwner);
        return "Success";
    }

    //Loads cart page
    @GetMapping("/cart")
    public String cart(Model model){
        //model.addAttribute(loggedInCustomer.getCart());
        return "cart.html";
    }

    //Adds product to cart
    @PostMapping("/cart/add")
    public @ResponseBody String addToCart(@RequestBody int id){
        System.out.println(id);
        loggedInCustomer.addToCart(productRepository.getOne(id));
        return "";
    }

    //Login as customer
    @GetMapping("/customer/{id}")
    public String loginCustomer(Model model, @PathVariable("username") String username){

        Customer customer = customerRepository.getOne(username);
        loggedInCustomer = customer;
        return "login.html";
    }

    //Login as owner
    @GetMapping("/owner/{username}")
    public String loginOwner(Model model, @PathVariable("username") String username){

        Owner owner = ownerRepository.getOne(username);
        model.addAttribute("owner", owner);
        return "login.html";
    }

    // Product view
    @GetMapping("/product/{id}")
    public String productView(Model model, @PathVariable("id") int id){
        Product selectedProduct = productRepository.getOne(id);
        model.addAttribute("product", selectedProduct);
        return "product.html";
    }

    // Generates checkout for specific customer
    @GetMapping("/checkout/{id}")
    public String checkoutView(Model model, @PathVariable("username") String username){
        Customer customer = customerRepository.getOne(username);
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

    // Generates Payment Page
    @GetMapping("/paid")
    public String paymentReceived() { return "paymentPage.html"; }

    @GetMapping("/cardDetails")
    public String cardDetailsView() { return "cardDetails.html"; }

}
