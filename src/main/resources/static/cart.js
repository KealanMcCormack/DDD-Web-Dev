function removeProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.onload=deleteProduct;
    xhr.open("GET", "/product/remove?id="+id);
    xhr.send();
}

function deleteProduct() {
    var id = this.responseText;

    var table = document.getElementById("product");
    var row = document.getElementById("row_" + id);
    table.deleteRow(row.rowIndex);
}

function totalPrice(id){
    var xhr = new XMLHttpRequest();

}