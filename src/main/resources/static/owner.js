function addProduct(){
    var record = {
        name : document.getElementById("nameBox").value,
        description : document.getElementById("descriptionBox").value,
        price : document.getElementById("price").value,
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/addProduct");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
}