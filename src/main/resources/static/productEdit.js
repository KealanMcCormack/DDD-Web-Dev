function saveChanges(id){
    var record = {
        name : document.getElementById("nameBox").value,
        description : document.getElementById("descriptionBox").value,
        price : document.getElementById("price").value,
        hidden : false,
    };

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/owner/product/edit/"+id);
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
}