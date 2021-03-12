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
//        for(int i = 0;i<allProducts.size();i++){
//            if(allProducts.get(i).hidden.equals("false")){ // if product is visible
//                visibleProducts.add(allProducts.get(i));
//            }
//        }
        allProducts.removeIf(x -> x.hidden.equals("true"));
        model.addAttribute("products", allProducts);
        return "gallery.html";
    }

    //Filters out products for search bar
    @GetMapping("/gallerySearch")
    public String gallerySearch(Model model, @RequestParam String searchString){
        List<Product> allProducts = productRepository.findAll();
        allProducts.removeIf(x -> x.hidden.equals("true")); //Remove if hidden
        if (searchString.isBlank()){ //if search string is empty
            model.addAttribute("products", allProducts);
            return "gallery.html";
        }
        allProducts.removeIf(x -> !x.getName().contains(searchString)); //Remove if X doesnt contain the search string
        model.addAttribute("products", allProducts);
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
        model.addAttribute("total", loggedInCustomer.totalPrice());
        model.addAttribute("cart", loggedInCustomer.getCart());
        return "cart.html";
    }

    //Adds product to cart
    @PostMapping("/cart/add")
    public @ResponseBody String addToCart(@RequestBody int id){
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
    @GetMapping("/checkout")
    public String checkoutView(Model model){
        model.addAttribute("customer", loggedInCustomer);
        return "checkout.html";
    }

    @GetMapping("/login")
    public String loginRedirect(Model model){
        return "login.html";
    }

    // Generates Payment Page
    @GetMapping("/paymentPage")
    public String paymentReceived() {
        Order newOrder = new Order("Pending", loggedInCustomer.getCart(), loggedInCustomer.getUsername());
        loggedInCustomer.addOrder(newOrder);
        return "paymentPage.html";
    }

    @GetMapping("/cardDetails")
    public String cardDetailsView() { return "cardDetails.html"; }

    @GetMapping("/owner/{id}")
    public String ownerMainPage(Model model, @PathVariable("id") int id) {

        List<Product> products =  productRepository.findAll();
        products.removeIf(x -> x.ownerId != id);

        model.addAttribute("products", products);
        return "owner.html";
    }

    @GetMapping("/owner/product/remove/{id}")
    public void ownerRemoveProduct(@PathVariable("id") int id) {
        productRepository.deleteById(id);
    }

    @GetMapping("/productCreation")
    public String productCreationPage(){
        return "productCreation.html";
    }

    @GetMapping("/owner/add/product")
    public void addProduct(Product newAddition){
        if(loggedInCustomer.getClass() == Owner.class){
            newAddition.setOwnerId(((Owner) loggedInCustomer).getOwnerId());
        }

        productRepository.save(newAddition);
    }


    @GetMapping("/product/remove")
    public @ResponseBody Integer remove(@RequestParam Integer id){
        loggedInCustomer.getCart().removeIf(x -> x.getId() == id);
        return id;
    }

    @GetMapping("/owner/product/hide")
    public @ResponseBody String hide(@RequestParam Integer id){
        Product product = productRepository.getOne(id);

        if(product.hidden.equals("true")){
            product.setHidden("false");
        } else{
            product.setHidden("true");
        }

        productRepository.deleteById(id);
        productRepository.save(product);

        return product.getHidden();
    }

    @GetMapping("/owner/product/edit/{id}")
    public String ownerEditProduct(Model model, @PathVariable("id") int id) {
        Product product =  productRepository.getOne(id);
        model.addAttribute("product", product);
        return "owner.html";
    }

}
