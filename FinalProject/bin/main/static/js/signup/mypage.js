$(document).ready(function(){
     
    $(".card").hide();

   
    $("#meetmate-link").click(function(){
        $("#meetmate-content .card").show();
        $("#communifind-content .card, #challenge-content .card, #mylikes-content .card").hide();
    });

   
    $("#communifind-link").click(function(){
        $("#communifind-content .card").show();
        $("#meetmate-content .card, #challenge-content .card, #mylikes-content .card").hide();
    });

   
    $("#challenge-link").click(function(){
        $("#challenge-content .card").show();
        $("#meetmate-content .card, #communifind-content .card, #mylikes-content .card").hide();
    });

  
    $("#mylikes-link").click(function(){
        $("#mylikes-content .card").show();
        $("#meetmate-content .card, #communifind-content .card, #challenge-content .card").hide();
    });
});