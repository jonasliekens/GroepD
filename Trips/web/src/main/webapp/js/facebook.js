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
        xfbml      : true  // parse XFBML
    });
}

function checkLoginStatus(){
    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
            // User logged into FB and authorized
            getData();
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
                    goToTrips();
                }
            }
        );
    });
}

function logout(){
    FB.logout(function(response) {
        // user is now logged out
    });
}

function goToTrips(){
    window.location="trips/";
}
