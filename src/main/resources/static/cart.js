function removeProduct(id) {
    var xhr = new XMLHttpRequest();
    xhr.onload=deleteProduct;
    xhr.open("GET", "/product/remove?id="+id);
    xhr.send();
}

function deleteProduct() {
    var id = this.responseText;
    var table = document.getElementById("cartTable");
    var row = document.getElementById("cartItem_" + id);
    table.deleteRow(row.rowIndex);
}
