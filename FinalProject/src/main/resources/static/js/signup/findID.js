let checkNum;
let isAuthenticated = false;




$('#send').click(function() {
   const tel = $('#tel').val();

   if (!tel) {
    const ErrorMessageDiv = document.getElementById("pErrorMessage");
    ErrorMessageDiv.style.display = "block"; // 메시지를 나타내도록 스타일 변경
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





const find = () => {
       
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

       if (!isAuthenticated) {
        const ErrorMessageDiv = document.getElementById("telErrorMessage");
        ErrorMessageDiv.style.display = "block"; 
        f.tel.focus();
        setTimeout(function() {
         ErrorMessageDiv.style.display = "none"; 
     }, 5000); 
     return;
    }

   f.action = "/findID.action";
   f.submit();

};





