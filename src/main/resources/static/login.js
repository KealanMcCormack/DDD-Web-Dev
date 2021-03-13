function customerLogin(){
    var record = {
        username : document.getElementById("loginBox").value,
        password: document.getElementById("loginBoxPassword").value,
    }
    console.log("customer{"+record.username+", "+record.password+"}")
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/customerLogin");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
}

function ownerLogin(){
    var record = {
        username : document.getElementById("loginBox").value,
        password: document.getElementById("loginBoxPassword").value,
    }
    console.log("owner{"+record.username+", "+record.password+"}")
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/ownerLogin");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
}