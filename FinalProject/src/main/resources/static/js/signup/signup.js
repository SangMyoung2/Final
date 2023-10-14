let checkNum;
let isAuthenticated = false;


setTimeout(function() {
    var errorMessageDiv = document.getElementById("error-message");
    if (errorMessageDiv) {
        errorMessageDiv.style.display = "none";
    }
}, 5000); 


$('#send').click(function() {
   const tel = $('#tel').val();

   if (!tel) {
    const ErrorMessageDiv = document.getElementById("pErrorMessage");
    ErrorMessageDiv.style.display = "block"; 
       f.tel.focus();
       setTimeout(function() {
        ErrorMessageDiv.style.display = "none"; 
    }, 5000); 
       return;
   }

   $.ajax({
       url: '/sendSMS',
       type: 'GET',
       data: {
           "tel": tel
       },
       success: function(data) {
         
           checkNum = data;
           const ErrorMessageDiv = document.getElementById("okMessage");
           ErrorMessageDiv.style.display = "block"; 
             
              setTimeout(function() {
               ErrorMessageDiv.style.display = "none"; 
           }, 5000); 
           
            document.getElementById("secondBlock").style.display = "block";
        
       },
       error: function() {
           alert('문자 전송에 실패하였습니다.');
       }
   });
});

$('#enterBtn').click(function() {
   const userNum = $('#userNum').val();

   if (checkNum === userNum) {
    const ErrorMessageDiv = document.getElementById("MessageOk");
    ErrorMessageDiv.style.display = "block"; 
      
       setTimeout(function() {
        ErrorMessageDiv.style.display = "none"; 
    }, 5000); 
       isAuthenticated = true;

   } else {
    const ErrorMessageDiv = document.getElementById("failMessage");
    ErrorMessageDiv.style.display = "block"; 
      
       setTimeout(function() {
        ErrorMessageDiv.style.display = "none"; 
    }, 5000); 
       isAuthenticated = false;
   }
});





const join = () => {
       
       f = document.myForm;


       str = f.name.value;
       str = str.trim(); 
       if(!str){
        const ErrorMessageDiv = document.getElementById("ErrorMessage");
        ErrorMessageDiv.style.display = "block"; 
           f.name.focus();
           setTimeout(function() {
            ErrorMessageDiv.style.display = "none"; 
        }, 5000); 

        return false;
       }
   
       
       str = f.userName.value;
       str = str.trim(); 
       if(!str){
        const ErrorMessageDiv = document.getElementById("nameErrorMessage");
        ErrorMessageDiv.style.display = "block"; 
           f.userName.focus();
           setTimeout(function() {
            ErrorMessageDiv.style.display = "none"; 
        }, 5000); 

        return false;
       }


   
       if(f.userName.value){
           if(!isValidEmail(f.userName.value)){
            const userNameErrorMessageDiv = document.getElementById("userNameErrorMessage");
            userNameErrorMessageDiv.style.display = "block"; 
               f.userName.focus();
               setTimeout(function() {
                userNameErrorMessageDiv.style.display = "none"; 
            }, 5000); 
            return false;
           }

       }
       
       str = f.password.value;
       str = str.trim(); 
       if(!str || str.length < 6){
        const ErrorMessageDiv = document.getElementById("ErrorMessage");
        ErrorMessageDiv.style.display = "block"; 
           f.password.focus();
           setTimeout(function() {
            ErrorMessageDiv.style.display = "none"; 
        }, 5000); 
        return false;
       }
       f.password.value = str;
       

       str = f.password2.value;
       str = str.trim(); 
       if(!str){
        const ErrorMessageDiv = document.getElementById("pwdErrorMessage");
        ErrorMessageDiv.style.display = "block"; 
           f.password2.focus();
           setTimeout(function() {
            ErrorMessageDiv.style.display = "none"; 
        }, 5000); 
        return false;
       }
       f.password2.value = str;


   if (f.password.value !== f.password2.value) {
    const ErrorMessageDiv = document.getElementById("pwdErrorMessage2");
    ErrorMessageDiv.style.display = "block"; 
    f.password2.focus();
    setTimeout(function() {
     ErrorMessageDiv.style.display = "none"; 
 }, 5000); 
 return false;
   }

  
   

    //    if (!isAuthenticated) {
    //     const ErrorMessageDiv = document.getElementById("telErrorMessage");
    //     ErrorMessageDiv.style.display = "block"; 
    //     f.tel.focus();
    //     setTimeout(function() {
    //      ErrorMessageDiv.style.display = "none"; 
    //  }, 5000); 
    //  return;
    // }

   f.action = "/signup.action";
 f.submit();




};






