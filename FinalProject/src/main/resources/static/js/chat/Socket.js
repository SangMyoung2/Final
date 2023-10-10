'use strict';
// 좀더 정확한 문법을 사용하게끔 해주는애 use strict
// var,let 이런거 안쓰면 알려줌

// document.write("<script src='jquery-3.6.1.js'></script>")
document.write("<script\n" +
    "  src=\"https://code.jquery.com/jquery-3.6.1.min.js\"\n" +
    "  integrity=\"sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=\"\n" +
    "  crossorigin=\"anonymous\"></script>")


var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var imageInput = document.querySelector('#imageMessage');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

// roomId 파라미터 가져오기
const url = new URL(location.href).searchParams
const roomId = url.get('roomId');

function connect(event) {
    username = document.querySelector('#name').value.trim();
    // username 중복 확인
    isDuplicateName();
    console.log('isDuplicateName 완료');
    // usernamePage 에 hidden 속성 추가해서 가리고
    // chatPage 를 등장시킴
    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');

    // 연결하고자하는 Socket 의 endPoint
    let socket = new SockJS('/ws-stomp');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}

function onConnected() {
    isprevChatList();
    //messageDiv(chatMessage);
    // sub 할 url => /sub/chat/room/roomId 로 구독한다

    stompClient.subscribe('/sub/chat/room/' + roomId, onMessageReceived);
    
    // 서버에 username 을 가진 유저가 들어왔다는 것을 알림
    // /pub/chat/enterUser 로 메시지를 보냄
    stompClient.send("/pub/chat/enterUser",
        {},
        JSON.stringify({
            "roomId": roomId,
            sender: username,
            type: 'ENTER'
        })
    )
    
    connectingElement.classList.add('hidden');
}

let chatMessage;

function onConnectedChat() {

    // sub 할 url => /sub/chat/room/roomId 로 구독한다

    stompClient.subscribe('/sub/chat/room/' + roomId, (message)=> {
        let receivedData = JSON.parse(message.body);
        //console.log("서버에서 받은 데이터 : ", receivedData);
        //console.log(receivedData.message[0].message);

        chatMessage = receivedData.message;
        console.log(chatMessage);
        // receivedData.message.forEach(element => {
        //     console.log(element.message);
        //     messageDiv(element);
        // });
    });

    // stompClient.subscribe('/sub/chat/room/' + roomId, (message)=> {
    //     let receivedData = JSON.parse(message.body);
    //     console.log("서버에서 받은 데이터 : ", receivedData);
    //     console.log(receivedData.length);
    //     //console.log(receivedData[0].chats.message);
    // });

    // 서버에 username 을 가진 유저가 들어왔다는 것을 알림
    // /pub/chat/enterUser 로 메시지를 보냄
    stompClient.send("/pub/chat/sendData",
        {},
        JSON.stringify({
            "roomId": roomId,
            sender: username,
            type: 'TALK'
        })
    )
    connectingElement.classList.add('hidden');
}

function isprevChatList(){

    $.ajax({
        type: "GET",
        url: "/chat/getPrevChatList",
        data: {
            "roomId": roomId,
            sender: username
        },
        success: function (data) {
            console.log("dd : " + data);
            console.log("함수 동작 확인 : " + JSON.stringify(data));
            let ch = JSON.stringify(data);
            
            if (typeof data === 'object') {
                // JSON 데이터를 객체로 변환했다고 가정
                const dataArray = data.data;
          
                // forEach 또는 다른 반복 메서드 사용
                dataArray.forEach(item => {
                  // 처리할 작업 수행
                //   console.log(item);
                  messageDiv(item);
                });
              } else {
                console.error('데이터 형식이 JSON이 아닙니다.');
              }
        }
    })

}

// 유저 닉네임 중복 확인
function isDuplicateName() {
    
    $.ajax({
        type: "GET",
        url: "/chat/duplicateName",
        data: {
            "username": username,
            "roomId": roomId
        },
        success: function (data) {
            console.log("함수 동작 확인 : " + data);

            username = data;
        }
    })

}

// 유저 리스트 받기
// ajax 로 유저 리스를 받으며 클라이언트가 입장/퇴장 했다는 문구가 나왔을 때마다 실행된다.
function getUserList() {
    const $list = $("#list");
    
    $.ajax({
        type: "GET",
        url: "/chat/userlist",
        data: {
            "roomId": roomId
        },
        success: function (data) {
            var users = "";
            for (let i = 0; i < data.length; i++) {
                //console.log("data[i] : "+data[i]);
                users += "<li class='dropdown-item'>" + data[i] + "</li>"
            }
            $list.html(users);
        }
    })
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

// 메시지 전송때는 JSON 형식을 메시지를 전달한다.
function sendMessage(event) {
    let messageContent = messageInput.value.trim();
    let imageContent = imageInput.files[0];

    if(imageContent){
        const formData = new FormData();

        formData.append('file', imageContent);
        formData.append('roomId', roomId);
        formData.append('sender', username);
        sendImageToServer(formData);
        
    }

    if (messageContent && stompClient) {
        var chatMessage = {
            "roomId": roomId,
            sender: username,
            message: messageContent,
            type: 'TALK',
            readCount: 0
        };
        console.log(messageContent)
        stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function sendImageToServer(fileContent){
    fetch('/chat/sendImage', {
        method: 'POST',
        // headers: {
        //     'Content-Type': 'multipart/form-data',
        // },
        body: fileContent,
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('파일 업로드 실패');
        }
    })
    .then(data => {
        // 서버 응답 처리
        console.log(data);
    })
    .catch(error => {
        // 오류 처리
        console.error(error);
    });
}

function sendMessageWithImage(imageData){
    console.log("sendMessageWithImage 들어옴")
    stompClient.send('/pub/chat/sendImage', {}, JSON.stringify({
        'message': imageData,
        "roomId": roomId,
        sender: username,
        type: 'IMAGE'
    }));
}


// 메시지를 받을 때도 마찬가지로 JSON 타입으로 받으며,
// 넘어온 JSON 형식의 메시지를 parse 해서 사용한다.
var isSendCaht = false;
var isReciveChat = false;
var sendUser;

function ImageReceived(){
    stompClient.subscribe('', (message) => {
        const imageData = message.body;
        // 이미지 데이터를 표시
        const imageElement = document.createElement('img');
        imageElement.src = imageData;
        document.getElementById('imageContainer').appendChild(imageElement);
    });
}

function onMessageReceived(payload) {
    console.log("playload : " + payload);

    var chat = JSON.parse(payload.body);
    console.log("메세지 : " + chat.message);
    console.log("읽음안읽음 카운트 : " + chat.readCount);

    console.log("메세지 타입 : " + chat.type);

    var messageElement = document.createElement('li');
    var textElement = document.createElement('div');

    if (chat.type === 'ENTER') {  // chatType 이 enter 라면 아래 내용
        messageElement.classList.add('event-message');
        chat.content = chat.sender + chat.message;
        getUserList();

    } else if (chat.type === 'LEAVE') { // chatType 가 leave 라면 아래 내용
        messageElement.classList.add('event-message');
        chat.content = chat.sender + chat.message;
        getUserList();

    }
    
    else if(chat.sender === username) { // 내가 보낸 채팅 이라면

        if(chat.type === 'IMAGE'){
            messageElement.classList.add('chat-myMessage');
            var imageElement = document.createElement('img');
            imageElement.classList.add('sendImage');
            console.log(chat.message);
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            imageInput.value="";
        }
        else if(isSendCaht == false){
            messageElement.classList.add('chat-myMessage');
            textElement.classList.add('balloon_right')
            isSendCaht = true
            isReciveChat = false;
        }
        else{
            messageElement.classList.add('chat-myMessage');
            textElement.classList.add('sendtext');
        }
        
    }
    else{// 내가 보낸게 아니라면

        if(chat.type === 'IMAGE'){
            messageElement.classList.add('chat-message');

            if(isReciveChat===false){
                var avatarElement = document.createElement('img');
                avatarElement.src="http://localhost:8080/image/chat/none.png"
                
                var avatarText = document.createTextNode(chat.sender[0]);
                avatarElement.appendChild(avatarText);
                avatarElement.classList.add('profile');
                messageElement.appendChild(avatarElement);
                isReciveChat = true;
                isSendCaht = false;
            }

            let imageElement = document.createElement('img');
            imageElement.classList.add('sendImage');
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            textElement.classList.add('sendImageDiv')
        }
        else{
            if(isReciveChat===false){
                sendUser = chat.sender;
                messageElement.classList.add('chat-message');

                var avatarElement = document.createElement('img');
                avatarElement.src="http://localhost:8080/image/chat/none.png";
                avatarElement.classList.add('profile');
                var avatarText = document.createTextNode(chat.sender);
                avatarElement.appendChild(avatarText);
                //avatarElement.style['background-color'] = getAvatarColor(chat.sender);

                messageElement.appendChild(avatarElement);

                var usernameElement = document.createElement('span');
                var usernameText = document.createTextNode(chat.sender);
                usernameElement.appendChild(usernameText);
                messageElement.appendChild(usernameElement);
                textElement.classList.add('balloon_left');
                isReciveChat = true;
                isSendCaht = false;
            }
            else{
                messageElement.classList.add('chat-message');
                textElement.classList.add('sendtext');
            }
        }
    }

    // if(chat.type === 'IMAGE'){
    //     messageElement.classList.add('chat-message');
    //     var imageElement = document.createElement('img');
    //     console.log(chat.message);
    //     imageElement.src = chat.message;
    //     messageElement.appendChild(imageElement);
    //     imageInput.value="";
    // }
    if(chat.type != 'IMAGE'){
        var messageText = document.createTextNode(chat.message);
        textElement.appendChild(messageText);
    }

    if(chat.type != 'ENTER' && chat.type != 'LEAVE'){
        // 읽음 count
        if(chat.sender === username){
            var readCountElement = document.createElement('p');
            readCountElement.classList.add('readCount');
            readCountElement.setAttribute('name', 'readCnt');
            readCountElement.value = chat.readCount;
            var readCountText = document.createTextNode(chat.readCount);
            readCountElement.appendChild(readCountText);
            messageElement.appendChild(readCountElement);
            messageElement.appendChild(textElement);
        }
        else{
            messageElement.appendChild(textElement);
            var readCountElement = document.createElement('p');
            readCountElement.classList.add('readCount');
            readCountElement.setAttribute('name', 'readCnt');
            readCountElement.value = chat.readCount;
            var readCountText = document.createTextNode(chat.readCount);
            readCountElement.appendChild(readCountText);
            messageElement.appendChild(readCountElement);
        }
    }
    //messageElement.appendChild(messageText);
    
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

//var messageElement ('li');
//var textElement ('div');

function messageDiv(chatMessages){
    //var chat = JSON.parse(message.body);
    // console.log("chat메세지 : " + chatMessages);
    // console.log("chat메세지.body : " + chatMessages.body);
    // console.log("chat메세지.message : " + chatMessages.message);
    let chat = chatMessages;
    let messageElement = document.createElement('li');
    let textElement = document.createElement('div');
    if(chat.sender === username) { // 내가 보낸 메세지 라면

        if(chat.type === 'IMAGE'){
            messageElement.classList.add('chat-myMessage');
            let imageElement = document.createElement('img');
            imageElement.classList.add('sendImage');
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            
        }
        else {
            if(isSendCaht == false){
                messageElement.classList.add('chat-myMessage');
                textElement.classList.add('balloon_right')
                isSendCaht = true
                isReciveChat = false;
            }
            else{
                messageElement.classList.add('chat-myMessage');
                textElement.classList.add('sendtext');
            }
        }
    }
    else{ //상대방이 보낸 메세지 라면

        if(chat.type === 'IMAGE'){
            messageElement.classList.add('chat-message');

            if(isReciveChat===false){
                var avatarElement = document.createElement('img');
                avatarElement.src="http://localhost:8080/image/chat/none.png"
                
                var avatarText = document.createTextNode(chat.sender[0]);
                avatarElement.appendChild(avatarText);
                avatarElement.classList.add('profile');
                messageElement.appendChild(avatarElement);
                isReciveChat = true;
                isSendCaht = false;
            }

            let imageElement = document.createElement('img');
            imageElement.classList.add('sendImage');
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
        }
        else{
            if(isReciveChat===false){
                messageElement.classList.add('chat-message');

                var avatarElement = document.createElement('img');
                avatarElement.src="http://localhost:8080/image/chat/none.png"
                
                var avatarText = document.createTextNode(chat.sender[0]);
                avatarElement.appendChild(avatarText);
                //avatarElement.style['background-color'] = getAvatarColor(chat.sender);
                avatarElement.classList.add('profile');
                messageElement.appendChild(avatarElement);

                var usernameElement = document.createElement('span');
                var usernameText = document.createTextNode(chat.sender);
                usernameElement.appendChild(usernameText);
                messageElement.appendChild(usernameElement);
                textElement.classList.add('balloon_left')
                isReciveChat = true;
                isSendCaht = false;
            }
            else{
                messageElement.classList.add('chat-message');
                textElement.classList.add('sendtext');
            }
        }
    }
    let messageText
    if(chat.type == 'IMAGE'){
        messageText = document.createTextNode("");
    }
    else{
        messageText = document.createTextNode(chat.message);
    }

    if(chat.type != 'ENTER' && chat.type != 'LEAVE'){
        // 읽음 count
        if(chat.sender === username){
            var readCountElement = document.createElement('p');
            readCountElement.classList.add('readCount');
            readCountElement.setAttribute('name', 'readCnt');
            readCountElement.value = chat.readCount;
            var readCountText = document.createTextNode(chat.readCount);
            readCountElement.appendChild(readCountText);
            messageElement.appendChild(readCountElement);
            messageElement.appendChild(textElement);
        }
        else{
            messageElement.appendChild(textElement);
            var readCountElement = document.createElement('p');
            readCountElement.classList.add('readCount');
            readCountElement.setAttribute('name', 'readCnt');
            readCountElement.value = chat.readCount;
            var readCountText = document.createTextNode(chat.readCount);
            readCountElement.appendChild(readCountText);
            messageElement.appendChild(readCountElement);
        }
    }
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    //messageElement.appendChild(messageText);
    
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


// 이미지 드래그 해서 넣기 ( 여러개 가능 )
$(function(){

    //드래그앤드랍
    $("#messageArea").on("dragenter", function(e){
        e.preventDefault();
        e.stopPropagation();
    }).on("dragover", function(e){
        e.preventDefault();
        e.stopPropagation();
        $(this).css("background-color", "#FFD8D8");
    }).on("dragleave", function(e){
        e.preventDefault();
        e.stopPropagation();
        $(this).css("background-color", "#FFF");
    }).on("drop", function(e){
        e.preventDefault();

        var files = e.originalEvent.dataTransfer.files;
        if(files != null && files != undefined){
            console.log("드래그 이미지 drop");
            
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                console.log('파일 이름:', file.name);
                console.log('파일 크기:', file.size, '바이트');
                console.log('파일 타입:', file.type);
                const formData = new FormData();

                formData.append('file', files[i]);
                formData.append('roomId', roomId);
                formData.append('sender', username);
                sendImageToServer(formData);
            }
        }

        $(this).css("background-color", "#FFF");
    });
});























// 메세지 읽음 안읽음 표시
function updateNumber() {
    // AJAX 요청을 통해 서버에서 새로운 숫자 가져오기
    console.log("updateNumber");
    let rid = {'roomId': roomId};

    let requestOption = {
        method:'POST',
        headers:{
            'Content-Type':'application/json',
        },
        body:JSON.stringify(rid)
    };

    fetch('/chat/getReadCount', requestOption)
      .then(response => response.json())
      .then(data => {
        // 가져온 숫자를 화면에 표시
        //console.log("데이타 : " + JSON.stringify(data));
        console.log("?? : " + data);
        countReadData(data);
      })
      .catch(error => {
        console.error('숫자 업데이트 중 오류 발생:', error);
      });
  }

setInterval(updateNumber, 5000);

function countReadData(data){
    console.log("countReadData");
    //let readCnt = document.getElementsByName('readCnt');
    
    console.log(" data : " + JSON.stringify(data));
    console.log("사이즈 : " + data.data.length);

    let read = document.getElementsByClassName("chat-message");
    let myRead = document.getElementsByClassName("chat-myMessage");

    console.log("read 갯수 : " + read.length);
    console.log("myRead 갯수 : " + myRead.length);
    if(read.length <= 0 && myRead.length <= 0) return;

    for(let i=0; i<data.data.length; i++){
        console.log(i + "번째 실행");
        for(let j=0; j<read.length; j++){

            let childDiv = read[j].querySelector("div")

            if(data.data[i].message === childDiv.textContent){
                let readcnt = read[j].querySelector("p");
                console.log('받은 메세지 : ' + i)
                if(data.data[i].readCount <= 0){
                    if(readcnt != null){
                        read[j].classList.add("chat-Message-ok")
                        read[j].classList.remove("chat-Message")
                        readcnt.remove();
                    }
                    continue
                };
                readcnt.textContent = data.data[i].readCount;
                readcnt.value = data.data[i].readCount;
                
            }
        }

        for(let j=0; j<myRead.length; j++){
            console.log("myChat");
            let childMyDiv = myRead[j].querySelector("div")
            
            if(data.data[i].message === childMyDiv.textContent){
                let myreadcnt = myRead[j].querySelector("p");
                console.log('내가보낸 메세지 : ' + i)    
                if(data.data[i].readCount <= 0){
                    if(myreadcnt != null){
                        myRead[j].classList.add("chat-myMessage-ok")
                        myRead[j].classList.remove("chat-myMessage")
                        myreadcnt.remove();
                    }
                    continue
                };
                myreadcnt.textContent = data.data[i].readCount;
                myreadcnt.value = data.data[i].readCount;
                
            }
        }

    }

}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)