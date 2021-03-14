function setState(id) {
    var state = document.getElementById("state").value;

    var xhr = new XMLHttpRequest();
    xhr.onload = returnDest()
    xhr.open("POST", "/owner/orders/state/" + id);
    xhr.send(state);
}

function returnDest() {
    var x = this.responseText;
}