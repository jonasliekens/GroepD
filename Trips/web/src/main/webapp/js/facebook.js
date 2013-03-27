// Load the SDK Asynchronously
(function(d){
    var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement('script'); js.id = id; js.async = true;
    js.src = "//connect.facebook.net/en_US/all.js";
    ref.parentNode.insertBefore(js, ref);
}(document));

function initFB(){
    FB.init({
        appId      : '610255372322861', // App ID
        channelUrl : 'http://localhost:8080/web/login/channel.html', // Channel File
        status     : true, // check login status
        cookie     : true, // enable cookies to allow the server to access the session
        xfbml      : true,  // parse XFBML
        frictionlessRequests: true  //Something to send requests.
    });
}

function checkLoginStatus(){
    FB.getLoginStatus(function(response) {
        if (response.status == 'connected') {
            // User logged into FB and authorized
            getData();
        }
    });
}

function checkLoginStatusRequest(){
    FB.getLoginStatus(function(response) {
        if (response.status == 'connected') {
            // User logged into FB and authorized
            getDataRequest();
        }
    });
}

function getData(){
    FB.api("/me?fields=id,first_name,last_name,birthday,email", function(response){
        $.post(
            "rest/login/facebook",
            response,
            function(data) {
                if(data == "true"){
                    window.location = "";
                }
            }
        );
    });
}

function getDataRequest(){
    FB.api("/me?fields=id,first_name,last_name,birthday,email", function(response){
        $.post(
            "rest/login/facebook",
            response,
            function(data) {
                if(data == "true"){
                    checkRequest();
                }
            }
        );
    });
}

function logout(){
    FB.getLoginStatus(function(response) {
        if (response.status == 'connected') {
            // User logged into FB and authorized
            FB.logout(function(response){
            });
        }
    });
}

function sendRequestViaMultiFriendSelector() {
    var url = window.location.toString();
    FB.ui({method: 'apprequests',
        message: 'You can automaticly register for the trip by accepting this invitation. With acceptance you will be automaticly registered on the website (If you weren\'t already) and you will agree with our terms.',
        data: url,
        title: 'Join me in this trip! It will be awesome! :D'
    }, function (response) {
        if (response.request && response.to) {
            var request_ids = [];
            for (i = 0; i < response.to.length; i++) {
                var temp = response.request + "_" + response.to[i];
                request_ids.push(temp);
            }
            var requests = request_ids.join(',');
            var splittedUrl = url.split("/");
            var parameters = "fbRequestIds="+requests+"&tripId="+splittedUrl[splittedUrl.length - 1];
            $.post("rest/request/handle", parameters, function(response){
                if(response == "true"){
                    alert("Requests have been handled, users can now register for the trip via facebook.");
                }
            });
        }
    });
}