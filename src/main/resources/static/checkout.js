function viewPerson(id){
    var xhr = new XMLHttpRequest();
    xhr.onload=view;
    xhr.open("GET", "/cardDetails");
    xhr.send();
}

function view(){
    document.getElementById("card").innerHTML = this.responseText;
}