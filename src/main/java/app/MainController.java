package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    Integer productID = 0;
    int customerID = 0;
    int ownerID = 0;
//    HashMap<Integer, Product> productList = new HashMap<>();
//    HashMap<String,Customer> customerList = new HashMap<>(); // Key = username
//    HashMap<String,Owner> ownerList = new HashMap<>(); // Key = username

    Customer loggedInCustomer = new Customer("default", ""); //Creates default user
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/")
    public String gallery(Model model){

        /*These are just for testing purposes*/
        productRepository.save(new Product(12,"Kealan", "test", 1));
        productRepository.save(new Product(2,"Lukas", "test1", 1));
        productRepository.save(new Product( 1, "Gerard", "test3", 1));

        List<Product> allProducts = productRepository.findAll();
        List<Product> visibleProducts = new ArrayList<>();
        for(int i = 0;i<allProducts.size();i++){
            if(allProducts.get(i).hidden.equals("false")){ // if product is visible
                visibleProducts.add(allProducts.get(i));
            }
        }
        model.addAttribute("products", visibleProducts);
        return "gallery.html";
    }

    @GetMapping("/createAccount")
    public String createAccountRedirect(){
        return "accountcreation.html";
    }

    //Creates Customer Account
    @PostMapping("/createCustomer")
    public @ResponseBody String createCustomer(@RequestBody Customer newCustomer){
            customerRepository.save(newCustomer);
            System.out.println(newCustomer.toString());
            return "Success";
    }

    //Creates Owner Account
    @PostMapping("/createOwner")
    public @ResponseBody String createOwner(@RequestBody Owner newOwner){
        ownerRepository.save(newOwner);
        System.out.println(newOwner.toString());
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
        Product newProduct = productRepository.getOne(id);
        System.out.println(newProduct);
        loggedInCustomer.addToCart(newProduct);
        return " ";
    }

    //Login as customer
    @GetMapping("/customerLogin")
    public String loginCustomer(Customer userEntered){
        Customer repoCustomer = customerRepository.getOne(userEntered.getUsername());
        if(repoCustomer.getPassword() == userEntered.getPassword()){
            loggedInCustomer = repoCustomer;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password not correct :(");
        }
        return "login.html";
    }

    //Login as owner
    @GetMapping("/ownerLogin")
    public String loginOwner(Owner ownerEntered){
        Owner repoOwner = ownerRepository.getOne(ownerEntered.getUsername());
        if(repoOwner.getPassword() == ownerEntered.getPassword()){
            loggedInCustomer = repoOwner;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password not correct :(");
        }
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
    public String loginRedirect(Model model){
        return "login.html";
    }

    // Generates Payment Page
    @GetMapping("/paid")
    public String paymentReceived() { return "paymentPage.html"; }

    @GetMapping("/cardDetails")
    public String cardDetailsView() { return "cardDetails.html"; }

    @GetMapping("/owner/{id}")
    public String ownerMainPage(Model model, @PathVariable("id") int id) {

        List<Product> products =  productRepository.findAll();
        products.removeIf(x -> x.ownerId != id);

        model.addAttribute("products", products);
        return "owner.html";
    }

    @GetMapping("/productCreation")
    public String productCreationPage(){
        return "productCreation.html";
    }

    @GetMapping("/addProduct")
    public void addProduct(Product newAddition){
        productRepository.save(newAddition);
    }

    //Calculates total price of items in cart
    @GetMapping("/cart/total")
    public @ResponseBody void totalPrice(@RequestBody int id){
        
    }

    @GetMapping("/product/remove")
    public @ResponseBody Integer remove(@RequestParam Integer id){
        productRepository.deleteById(id);
        return id;
    }

    @GetMapping("/product/hide")
    public @ResponseBody String hide(@RequestParam Integer id){
        Product product = productRepository.getOne(id);

        if(product.hidden == "true"){
            product.setHidden("false");
        } else{
            product.setHidden("true");
        }

        productRepository.deleteById(id);
        productRepository.save(product);

        return product.getHidden();
    }

}
