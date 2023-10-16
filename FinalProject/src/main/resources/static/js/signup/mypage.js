// 버튼을 클릭할 때 Ajax 요청을 보내는 함수
function sendAjaxRequest(endpoint) {
    event.preventDefault();
    $.ajax({
        url: endpoint,
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            updateUI(data);
        },
        error: function(error) {
            console.error('Error:', error);
        }
    });
}

// MeetMate 버튼 클릭 시 Ajax 요청 보내기
$('#meetmate-link').on('click', function(event) {
    sendAjaxRequest('/api/meetmate');
});

// CommuniFind 버튼 클릭 시 Ajax 요청 보내기
$('#communifind-link').on('click', function(event) {
    sendAjaxRequest('/api/communifind');
});

// Challenge 버튼 클릭 시 Ajax 요청 보내기
$('#challenge-link').on('click', function(event) {
    sendAjaxRequest('/api/challenge');
});

// My Likes 버튼 클릭 시 Ajax 요청 보내기
$('#mylikes-link').on('click', function(event) {
    sendAjaxRequest('/api/mylikes');
});

// My Chat 버튼 클릭 시 Ajax 요청 보내기
$('#mychat-link').on('click', function(event) {
    sendAjaxRequest('/api/mychat');
});

function updateUI(data) {
    // 받은 데이터를 사용하여 페이지의 일부분을 동적으로 업데이트
    // 예: DOM 조작을 통해 새로운 콘텐츠를 페이지에 추가
}
