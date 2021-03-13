//Gets should be posts for a few of these. 
function addProduct(){
    var record = {
        name : document.getElementById("nameBox").value,
        description : document.getElementById("descriptionBox").value,
        price : document.getElementById("price").value,
        hidden : false,
    };

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/owner/add/product");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
}

function removeProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.onload=deleteProduct;
    xhr.open("GET", "/owner/product/remove?id="+id);
    xhr.send();
}

function deleteProduct() {
    var id = this.responseText;
    console.log(id);
    var table = document.getElementById("gridItem");
    var row = document.getElementById("row_" + id);
    table.deleteRow(row.rowIndex);
}

function hideProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/owner/product/hide?id="+id);
    xhr.send();
}

function editProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/owner/product/edit?id="+id);
    xhr.send();
}