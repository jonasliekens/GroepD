function checkRequest(){
    var weblocation = window.location.toString();
    if(weblocation.contains("?")){
        var requestIds = "fbRequestIds=" + weblocation.split('?')[1].split('&')[0].split('=')[1];
        $.post("rest/request/register", requestIds, function(data){
            if(data != "false"){
                alert(data);
                window.location="trips/details/"+data;
            }else if(data == "false"){
                alert("Well, at least it works... Kinda");
            }
        })
    }
}