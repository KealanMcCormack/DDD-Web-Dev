function addToCart(id){
    console.log("in js")
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/cart/add");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(id);
    window.alert("Product added to cart!");
}