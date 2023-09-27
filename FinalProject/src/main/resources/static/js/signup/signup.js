let checkNum;
let isAuthenticated = false;




$('#send').click(function() {
   const to = $('#to').val();

   if (!to) {
    const ErrorMessageDiv = document.getElementById("pErrorMessage");
    ErrorMessageDiv.style.display = "block"; // 메시지를 나타내도록 스타일 변경
       f.to.focus();
       setTimeout(function() {
        ErrorMessageDiv.style.display = "none"; 
    }, 5000); 
       return;
   }

   $.ajax({
       url: '/sendSMS',
       type: 'GET',
       data: {
           "to": to
       },
       success: function(data) {
           // 서버에서 받은 데이터를 checkNum 변수에 할당
           checkNum = data;
           const ErrorMessageDiv = document.getElementById("okMessage");
           ErrorMessageDiv.style.display = "block"; // 메시지를 나타내도록 스타일 변경
             
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
        const ErrorMessageDiv = document.getElementById("nameErrorMessage");
        ErrorMessageDiv.style.display = "block"; // 메시지를 나타내도록 스타일 변경
           f.name.focus();
           setTimeout(function() {
            ErrorMessageDiv.style.display = "none"; 
        }, 5000); 
       }


   str = f.email.value;
       str = str.trim(); 
       if(!str){
        const ErrorMessageDiv = document.getElementById("emailErrorMessage");
        ErrorMessageDiv.style.display = "block"; 
           f.email.focus();
           setTimeout(function() {
            ErrorMessageDiv.style.display = "none"; 
        }, 5000); 
       }
  
   
       if(f.email.value){
           if(!isValidEmail(f.email.value)){
            const mailErrorMessageDiv = document.getElementById("mailErrorMessage");
            mailErrorMessageDiv.style.display = "block"; 
               f.email.focus();
               setTimeout(function() {
                mailErrorMessageDiv.style.display = "none"; 
            }, 5000); 
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
       }
       f.password2.value = str;


   if (f.password.value !== f.password2.value) {
    const ErrorMessageDiv = document.getElementById("pwdErrorMessage2");
    ErrorMessageDiv.style.display = "block"; 
    f.password2.focus();
    setTimeout(function() {
     ErrorMessageDiv.style.display = "none"; 
 }, 5000); 
   }
   

       if (!isAuthenticated) {
        const ErrorMessageDiv = document.getElementById("telErrorMessage");
        ErrorMessageDiv.style.display = "block"; 
        f.to.focus();
        setTimeout(function() {
         ErrorMessageDiv.style.display = "none"; 
     }, 5000); 
     return;
    }

   f.action = "/signup_ok.action";
 f.submit();

};





