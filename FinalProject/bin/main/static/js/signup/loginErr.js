function checkAccessToProtectedResource() {
    // Ajax 요청을 보냅니다.
    $.ajax({
        url: "/temp.action",
        type: "GET",
        dataType: "text", 
        success: function(data) {
            // 성공적인 응답을 받은 경우 처리 로직을 작성합니다.
            console.log("리소스에 접근 성공: " + data);
        },
        error: function(xhr, status, error) {
            // 실패한 경우 경고 메시지를 띄웁니다.
            if (xhr.status === 401) {
                alert("로그인이 필요합니다.");
                // 로그인 페이지로 리디렉션합니다.
                window.location.href = "/login.action";
            } else {
                console.error("오류 발생: " + error);
            }
        }
    });
}