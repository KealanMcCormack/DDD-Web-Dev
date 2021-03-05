function createCustomer(){
    var record = {
        username : document.getElementsByClassName("loginBoxUsername").value,
        password: document.getElementsByClassName("loginBox").value,
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/createCustomer");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
    if (xhr.responseText === "Success"){
        window.alert("Account created successfully!")
    }else{
        window.alert("Could not create account (already exists)")
    }
}
function createOwner(){
    var record = {
        username : document.getElementsByClassName("loginBoxUsername").value,
        password: document.getElementsByClassName("loginBox").value,
    };
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/createOwner");
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(record))
    if (xhr.responseText === "Success"){
        window.alert("Account created successfully!")
    }else{
        window.alert("Could not create account (already exists)")
    }
}