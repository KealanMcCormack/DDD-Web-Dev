function editProduct(id) {
    var record = {
        id : id,
        name : document.getElementById("nameBox").value,
        description : document.getElementById("descriptionBox").value,
        price : document.getElementById("price").value,
    };

    var xhr = new XMLHttpRequest();
    xhr.onload=insertProduct;
    xhr.open("POST", "owner/product/edit/js/" + id);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(record));
}

function insertProduct() {
    var thing = JSON.parse(this.responseText);
}
