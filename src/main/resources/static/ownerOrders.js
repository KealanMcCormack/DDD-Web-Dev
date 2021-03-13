function setState(id) {
    var state = document.getElementById("state").value;

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/owner/orders/state/"+id);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(state));
}