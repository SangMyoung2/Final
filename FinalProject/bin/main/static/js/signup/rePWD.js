

const ok = () => {
       
       f = document.myForm;
       
       str = f.password.value;
       str = str.trim(); 
       if(!str || str.length < 6){
        const ErrorMessageDiv = document.getElementById("ErrorMessage");
        ErrorMessageDiv.style.display = "block"; 
           f.password.focus();
           setTimeout(function() {
            ErrorMessageDiv.style.display = "none"; 
        }, 5000); 
        return ;
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


   f.action ="/rePWD.action";
 f.submit();

};






