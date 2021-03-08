function createCustomer(){
    var record = {
        username : document.getElementById("loginBoxUsername").value,
        password: document.getElementById("loginBox").value,
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/createCustomer");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
}
function createOwner(){
    var record = {
        username : document.getElementById("loginBoxUsername").value,
        password: document.getElementById("loginBox").value,
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/createOwner");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
}