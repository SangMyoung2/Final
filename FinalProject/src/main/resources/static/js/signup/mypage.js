// 버튼을 클릭할 때 Ajax 요청을 보내는 함수
function sendRequest(endpoint) {
    $.ajax({
        url: endpoint,
        method: 'GET',
        dataType: 'json',
        data:{
            
        },
        success: function(data) {
            updateUI(data);
        },
        error: function(error) {
            console.error('Error:', error);
        }
    });
}


function updateUI(data) {
   
}


$('#meetmate-link').on('click', function(event) {
    event.preventDefault();
    sendRequest('/meetmate');
});


$('#communifind-link').on('click', function(event) {
    event.preventDefault();
    sendRequest('/communifind');
});


$('#challenge-link').on('click', function(event) {
    event.preventDefault();
    sendRequest('/challenge');
});


$('#mylikes-link').on('click', function(event) {
    event.preventDefault();
    sendRequest('/mylikes');
});


$('#mychat-link').on('click', function(event) {
    event.preventDefault();
    sendRequest('/mychat');
});


