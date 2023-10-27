
function myChat(){
    var options = 'width=600px,height=700px,scrollbars=yes'
    popupChat = window.open("chatlist.action"
    ,"_blacnk",options);
}






$(document).ready(function(){
     
    $(".card").hide();

   
    $("#meetmate-link").click(function(){
        event.preventDefault();
        $("#meetmate-content .card").show();
        $("#communifind-content .card, #challenge-content .card, #mylikes-content .card").hide();
    });

   
    $("#communifind-link").click(function(){
        event.preventDefault();
        $("#communifind-content .card").show();
        $("#meetmate-content .card, #challenge-content .card, #mylikes-content .card").hide();
    });

   
    $("#challenge-link").click(function(){
        event.preventDefault();
        $("#challenge-content .card").show();
        $("#meetmate-content .card, #communifind-content .card, #mylikes-content .card").hide();
    });

  
    $("#mylikes-link").click(function(){
        event.preventDefault();
        $("#mylikes-content .card").show();
        $("#meetmate-content .card, #communifind-content .card, #challenge-content .card").hide();
    });
});