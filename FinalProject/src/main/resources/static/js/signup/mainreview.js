
$(document).ready(function(){
     
    $(".card").hide();

   
    $("#meetmate-link").click(function(){
        event.preventDefault();
        $("#meetmate-content .card").show();
        $("#challenge-content .card").hide();
    });

    $("#challenge-link").click(function(){
        event.preventDefault();
        $("#challenge-content .card").show();
        $("#meetmate-content .card").hide();
    });

  
   
});