

let roomId;

$(function(){
    let $maxUserCnt = $("#maxUserCnt");
    let $msgType = $("#msgType");

    // 모달창 열릴 때 이벤트 처리 => roomId 가져오기
    $("#enterRoomModal").on("show.bs.modal", function (event) {
        roomId = $(event.relatedTarget).data('id');
        // console.log("roomId: " + roomId);

    });

    $("#confirmPwdModal").on("show.bs.modal", function (e) {
        roomId = $(e.relatedTarget).data('id');
        // console.log("roomId: " + roomId);

    });

    // 채팅방 설정 시 비밀번호 확인
    confirmPWD();

    // 문자 채팅 누를 시 disabled 풀림
    $msgType.change(function(){
        if($msgType.is(':checked')){
            $maxUserCnt.attr('disabled', false);
        }
    })
})

// 채팅방 설정 시 비밀번호 확인 - keyup 펑션 활용
function confirmPWD(){
    $("#confirmPwd").on("keyup", function(){
        let $confirmPwd = $("#confirmPwd").val();
        const $configRoomBtn = $("#configRoomBtn");
        let $confirmLabel = $("#confirmLabel");

        $.ajax({
            type : "post",
            url : "/chat/confirmPwd/"+roomId,
            data : {
                "roomPwd" : $confirmPwd
            },
            success : function(result){
                // console.log("동작완료")

                // result 의 결과에 따라서 아래 내용 실행
                if(result){ // true 일때는
                    // $configRoomBtn 를 활성화 상태로 만들고 비밀번호 확인 완료를 출력
                    $configRoomBtn.attr("class", "btn btn-primary");
                    $configRoomBtn.attr("aria-disabled", false);

                    $confirmLabel.html("<span id='confirm'>비밀번호 확인 완료</span>");
                    $("#confirm").css({
                        "color" : "#0D6EFD",
                        "font-weight" : "bold",
                    });

                }else{ // false 일때는
                    // $configRoomBtn 를 비활성화 상태로 만들고 비밀번호가 틀립니다 문구를 출력
                    $configRoomBtn.attr("class", "btn btn-primary disabled");
                    $configRoomBtn.attr("aria-disabled", true);

                    $confirmLabel.html("<span id='confirm'>비밀번호가 틀립니다</span>");
                    $("#confirm").css({
                        "color" : "#FA3E3E",
                        "font-weight" : "bold",
                    });

                }
            }
        })
    })
}

// 채팅 인원 숫자만 정규식 체크
function numberChk(){
    let check = /^[0-9]+$/;
    if (!check.test($("#maxUserCnt").val())) {
        alert("채팅 인원에는 숫자만 입력 가능합니다!!")
        return false;
    }
    return true;
}

// 채팅방 생성
function createRoom() {

    let name = $("#roomName").val();
    // let secret = $("#secret").is(':checked');
    // let secretChk = $("#secretChk");
    let $rtcType = $("#rtcType");
    let $msgType = $("#msgType");

    if (name === "") {
        alert("방 이름은 필수입니다")
        return false;
    }
    if ($("#" + name).length > 0) {
        alert("이미 존재하는 방입니다")
        return false;
    }

}

// 채팅방 삭제
function delRoom(){
    location.href = "/chat/delRoom/"+roomId;
}

// 채팅방 입장 시 인원 수에 따라서 입장 여부 결정
function chkRoomUserCnt(roomId){
    let chk;

    // 비동기 처리 설정 false 로 변경 => ajax 통신이 완료된 후 return 문 실행
    // 기본설정 async = true 인 경우에는 ajax 통신 후 결과가 나올 때까지 기다리지 않고 먼저 return 문이 실행되서
    // 제대로된 값 - 원하는 값 - 이 return 되지 않아서 문제가 발생한다.
    $.ajax({
        type : "GET",
        url : "/chat/chkUserCnt/"+roomId,
        async : false,
        success : function(result){

            // console.log("여기가 먼저")
            if (!result) {
                alert("채팅방이 꽉 차서 입장 할 수 없습니다");
            }

            chk = result;
        }
    })
    return chk;
}


setInterval(checkNotReadMessage, 700);
let userId;
function checkNotReadMessage(){

    userId = document.getElementById("userId").value;
    console.log("유저 이름 : " + userId);
    
    let user = {'userId': userId};

    let requestOption = {
        method:'POST',
        headers:{
            'Content-Type':'application/json',
        },
        body:JSON.stringify(user)
    };

    fetch('/chat/checkNotReadMessage', requestOption)
      .then(response => response.json())
      .then(data => {
        // 가져온 숫자를 화면에 표시
        //console.log("데이타 : " + JSON.stringify(data));
        console.log("checkNotReadMessage : " + JSON.stringify(data));
        roomsNotReadMessage(data);
      })
      .catch(error => {
        console.error('숫자 업데이트 중 오류 발생:', error);
      });
}

function roomsNotReadMessage(data){
    let rooms = document.getElementsByName("room");
    let notReadCountObject = data.notReadCount;
    let notReadCountKeys = Object.keys(notReadCountObject);
    let notReadCountSize = notReadCountKeys.length;

    // console.log("크기 : " + notReadCountSize);
    // console.log(rooms[0].getAttribute('data-value'));
    // console.log("위잉 : " + notReadCountKeys);

    for(let i=0; i<notReadCountSize; i++){
        for(let j=0; j<rooms.length; j++){
            let roomId = rooms[j].getAttribute('data-value');
            let notReadCountValue = notReadCountObject[roomId];
            if(notReadCountValue <= 0){
                continue;
            }
            rooms[j].textContent = notReadCountValue;
        }
    }
}