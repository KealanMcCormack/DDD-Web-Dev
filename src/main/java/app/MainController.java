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

    Customer loggedInCustomer = new Customer("default", ""); //Creates default user
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/")
    public String galleryInit(Model model){

        /*Initial objects*/
        productRepository.save(new Product(12,"Kealan", "test", 1));
        productRepository.save(new Product(2,"Lukas", "test1", 1));
        productRepository.save(new Product( 1, "Gerard", "test3", 1));

        List<Product> allProducts = productRepository.findAll();
        allProducts.removeIf(x -> x.hidden.equals("true"));
        model.addAttribute("products", allProducts);
        return "gallery.html";
    }

    @GetMapping("/gallery")
    public String gallery(Model model){
        List<Product> allProducts = productRepository.findAll();
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
    public @ResponseBody String loginCustomer(@RequestBody Customer userEntered){
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
    public @ResponseBody String loginOwner(@RequestBody Owner ownerEntered){
        System.out.println(loggedInCustomer.toString());
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
        model.addAttribute("cart", loggedInCustomer.getCart());
        model.addAttribute("total", loggedInCustomer.totalPrice());
        return "checkout.html";
    }

    @GetMapping("/login")
    public String loginRedirect(Model model){
        return "login.html";
    }

    // Generates Payment Page
    @GetMapping("/paymentPage")
    public String paymentReceived() {
        for (Product x: loggedInCustomer.getCart()) {
            Order newOrder = new Order("Pending", x.id, loggedInCustomer.getUsername());
            orderRepository.save(newOrder);
            loggedInCustomer.addOrder(newOrder);
        }
        loggedInCustomer.getCart().clear(); //Empties cart
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

//    @GetMapping("/owner")
//    public String ownerPage(Model model){
//        List<Product> products =  productRepository.findAll();
//        int id = ((Owner) loggedInCustomer).getOwnerId();
//        products.removeIf(x -> x.ownerId != id);
//
//        model.addAttribute("products", products);
//        return "owner.html";
//    }

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

    @GetMapping("/owner/orders")
    public String ownerOrders(Model model){
        List<Customer> customers = customerRepository.findAll();
        List<Order> ownerOrders = new ArrayList<Order>();

        for(Customer x : customers){
            List<Order> orders = x.getOrderHistory();
            for(Order y : orders){
                Product product = productRepository.getOne(y.productId);
                if(product.ownerId == ((Owner) loggedInCustomer).getOwnerId()){
                    ownerOrders.add(y);
                }
            }
        }

        model.addAllAttributes(ownerOrders);
        return "ownerOrders.html";
    }

    @GetMapping("/account")
    public String accountPageRedirect(Model model){
        if((loggedInCustomer instanceof Customer) && !(loggedInCustomer instanceof Owner)){ //If user is customer
            model.addAttribute("orderHistory", loggedInCustomer.getOrderHistory());
            return "account.html";
        }
        if(loggedInCustomer instanceof Owner){ //if account is an owner
            System.out.println(loggedInCustomer.toString());
            List<Product> products =  productRepository.findAll();
            int id = ((Owner) loggedInCustomer).getOwnerId();
            products.removeIf(x -> x.ownerId != id);

            model.addAttribute("products", products);
            return "owner.html";
        }
        return "accountcreation.html";
    }

    @PostMapping("/owner/orders/state/{id}")
    public @ResponseBody Order add(@RequestBody String state, @PathVariable("id") int id){
        Order order = orderRepository.findById(id).get();
        order.setState(state);
        orderRepository.deleteById(id);
        List<Customer> customers = customerRepository.findAll();
        for(Customer x : customers){
            List<Order> orders = x.getOrderHistory();
            for(Order y : orders){
                if(y.id == id){
                    x.removeOrder(y);
                    y.setState(state);
                    x.addOrder(y);
                }
            }
        }
        return orderRepository.save(order);
    }
}
