'use strict';
// 좀더 정확한 문법을 사용하게끔 해주는애 use strict
// var,let 이런거 안쓰면 알려줌

// document.write("<script src='jquery-3.6.1.js'></script>")
document.write("<script\n" +
    "  src=\"https://code.jquery.com/jquery-3.6.1.min.js\"\n" +
    "  integrity=\"sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=\"\n" +
    "  crossorigin=\"anonymous\"></script>")

let usernamePage = document.querySelector('#username-page');
let chatPage = document.querySelector('#chat-page');
let usernameForm = document.querySelector('#usernameForm');
let messageForm = document.querySelector('#messageForm');
let messageInput = document.querySelector('#message');
let imageInput = document.querySelector('#imageMessage');
let messageArea = document.querySelector('#messageArea');
let connectingElement = document.querySelector('.connecting');

let stompClient = null;
let username = null;
let userId = null;
// roomId 파라미터 가져오기
const url = new URL(location.href).searchParams
const roomId = url.get('roomId');

function connect(event) {
    username = document.getElementById("userName").value.trim();
    userId = document.getElementById("userId").value.trim();
    // console.log(username);
    // username 중복 확인
    isDuplicateName();
    console.log('isDuplicateName 완료');
    // usernamePage 에 hidden 속성 추가해서 가리고
    // chatPage 를 등장시킴
    // usernamePage.classList.add('hidden');
    // chatPage.classList.remove('hidden');

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
            userId: userId,
            sender: username,
            type: 'ENTER'
        })
    )
    
    // connectingElement.classList.add('hidden');
    setInterval(updateNumber, 500);
    setInterval(countZeroData, 1000);
    
}

let chatMessage;

function onConnectedChat() {

    // sub 할 url => /sub/chat/room/roomId 로 구독한다

    stompClient.subscribe('/sub/chat/room/' + roomId, (message)=> {
        let receivedData = JSON.parse(message.body);

        chatMessage = receivedData.message;
        // console.log(chatMessage);
        
    });

    // 서버에 username 을 가진 유저가 들어왔다는 것을 알림
    // /pub/chat/enterUser 로 메시지를 보냄
    stompClient.send("/pub/chat/sendData",
        {},
        JSON.stringify({
            "roomId": roomId,
            userId: userId,
            sender: username,
            type: 'TALK'
        })
    )
    connectingElement.classList.add('hidden');
}

function isprevChatList(){
    username = document.getElementById("userName").value.trim();
    userId = document.getElementById("userId").value.trim();
    $.ajax({
        type: "GET",
        url: "/chat/getPrevChatList",
        data: {
            "roomId": roomId,
            userId: userId,
            sender: username
        },
        success: function (data) {
            // console.log("dd : " + data);
            // console.log("함수 동작 확인 : " + JSON.stringify(data));
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

window.addEventListener("beforeunload", function (event) {
    stompClient.send("/pub/chat/logout",
        {},
        JSON.stringify({
            "roomId": roomId,
            sender: userId,
        }))
});

// 유저 닉네임 중복 확인
function isDuplicateName() {
    
    $.ajax({
        type: "GET",
        url: "/chat/duplicateName",
        data: {
            "username": userId,
            "roomId": roomId
        },
        success: function (data) {
            // console.log("함수 동작 확인 : " + data);

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
    username = document.getElementById("userName").value.trim();
    userId = document.getElementById("userId").value.trim();

    if(imageContent){
        const formData = new FormData();

        formData.append('file', imageContent);
        formData.append('roomId', roomId);
        formData.append('sender', username);
        formData.append('userId', userId);
        sendImageToServer(formData);
        
    }
    console.log('username : ' + username);
    if (messageContent && stompClient) {
        var chatMessage = {
            "roomId": roomId,
            sender: username,
            userId: userId,
            message: messageContent,
            type: 'TALK',
            readCount: 0,
            picture:""
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
        // console.log(data);
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
        userId: userId,
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
    var sideElement = document.createElement('div');
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
        sideElement.classList.add('myDateDiv');
        if(chat.type === 'IMAGE'){
            messageElement.classList.add('chat-myMessage');
            var imageElement = document.createElement('img');
            imageElement.classList.add('sendImage');
            console.log(chat.message);
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            imageInput.value="";
        }
        else if(chat.type === 'EMOTICON'){
            messageElement.classList.add('chat-myMessage');
            var imageElement = document.createElement('img');
            imageElement.classList.add('sendEmoticon');
            console.log(chat.message);
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            imageInput.value="";
        }
        else if(isSendCaht == false){
            messageElement.classList.add('chat-myMessage');
            textElement.classList.add('balloon_right')
            isSendCaht = true
        }
        else{
            messageElement.classList.add('chat-myMessage');
            textElement.classList.add('sendtext');
        }
        
        isReciveChat = false;
    }
    else{// 내가 보낸게 아니라면
        console.log("내가 보낸게 아니라면 : " + chat.sender);
        
        // 이미지일때
        if(chat.type === 'IMAGE'){
            
            messageElement.classList.add('chat-message');

            if(isReciveChat===false){
                sideElement.classList.add('newDateDiv');

                var avatarElement = document.createElement('img');
                avatarElement.src="/image/login/" + chat.picture;
                
                var usernameElement = document.createElement('span');
                var usernameText = document.createTextNode(chat.sender);
                usernameElement.appendChild(usernameText);
                messageElement.appendChild(usernameElement);

                avatarElement.classList.add('profile');
                messageElement.appendChild(avatarElement);
                isReciveChat = true;
                isSendCaht = false;
                textElement.classList.add('newSendImageDiv')
            }
            else{
                sideElement.classList.add('dateDiv');
                textElement.classList.add('sendImageDiv')
            }
            let imageElement = document.createElement('img');
            imageElement.classList.add('sendImage');
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            
        }
        // 이모티콘 일때
        else if(chat.type === 'EMOTICON'){
            messageElement.classList.add('chat-message');
            console.log("이모티콘 들어옴.")
            if(isReciveChat===false){
                sideElement.classList.add('newDateDiv');
                console.log("isReciveChat === false 들어옴")
                var avatarElement = document.createElement('img');
                avatarElement.src="/image/login/" + chat.picture;
                
                var usernameElement = document.createElement('span');
                var usernameText = document.createTextNode(chat.sender);
                usernameElement.appendChild(usernameText);
                messageElement.appendChild(usernameElement);

                avatarElement.classList.add('profile');
                messageElement.appendChild(avatarElement);
                isReciveChat = true;
                isSendCaht = false;
                textElement.classList.add('newSendImageDiv')
            }else{
                sideElement.classList.add('dateDiv');
                textElement.classList.add('sendImageDiv')
            }

            let imageElement = document.createElement('img');
            imageElement.classList.add('sendEmoticon');
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            
        }
        else{
            if(isReciveChat===false || sendUser != chat.sender){
                sideElement.classList.add('newDateDiv');
                sendUser = chat.sender;
                messageElement.classList.add('chat-message');

                var avatarElement = document.createElement('img');
                avatarElement.src="/image/login/" + chat.picture;
                avatarElement.classList.add('profile');
                var avatarText = document.createTextNode(chat.sender);
                avatarElement.appendChild(avatarText);

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
                sideElement.classList.add('dateDiv');
                messageElement.classList.add('chat-message');
                textElement.classList.add('sendtext');
            }
        }
    }
    if(chat.type != 'IMAGE' && chat.type != 'EMOTICON'){
        var messageText = document.createTextNode(chat.message);
        textElement.appendChild(messageText);
    }

    if(chat.type != 'ENTER' && chat.type != 'LEAVE'){
        // 읽음 count
        if(chat.sender === username){
            
            var readCountElement = document.createElement('p');
            readCountElement.classList.add('myReadCount');
            readCountElement.setAttribute('name', 'readCnt');
            readCountElement.value = chat.readCount;
            var readCountText = document.createTextNode(chat.readCount);
            readCountElement.appendChild(readCountText);
            sideElement.appendChild(readCountElement);

            var dateElement = document.createElement('p');
            dateElement.classList.add('chatDate');
            var dateText = document.createTextNode(chat.time);
            dateElement.appendChild(dateText);
            sideElement.appendChild(dateElement);
            
            messageElement.appendChild(sideElement);
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
            sideElement.appendChild(readCountElement);

            var dateElement = document.createElement('p');
            dateElement.classList.add('chatDate');
            var dateText = document.createTextNode(chat.time);
            dateElement.appendChild(dateText);
            sideElement.appendChild(dateElement);

            messageElement.appendChild(sideElement);
        }
    }
    //messageElement.appendChild(messageText);
    

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function messageDiv(chatMessages){
    //var chat = JSON.parse(message.body);
    // console.log("chat메세지 : " + chatMessages);
    // console.log("chat메세지.body : " + chatMessages.body);
    // console.log("chat메세지.message : " + chatMessages.message);
    let chat = chatMessages;
    var messageElement = document.createElement('li');
    var textElement = document.createElement('div');
    var sideElement = document.createElement('div');
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
        sideElement.classList.add('myDateDiv');
        if(chat.type === 'IMAGE'){
            messageElement.classList.add('chat-myMessage');
            var imageElement = document.createElement('img');
            imageElement.classList.add('sendImage');
            console.log(chat.message);
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            imageInput.value="";
        }
        else if(chat.type === 'EMOTICON'){
            messageElement.classList.add('chat-myMessage');
            var imageElement = document.createElement('img');
            imageElement.classList.add('sendEmoticon');
            console.log(chat.message);
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            imageInput.value="";
        }
        else if(isSendCaht == false){
            messageElement.classList.add('chat-myMessage');
            textElement.classList.add('balloon_right')
            isSendCaht = true
        }
        else{
            messageElement.classList.add('chat-myMessage');
            textElement.classList.add('sendtext');
        }
        
        isReciveChat = false;
    }
    else{// 내가 보낸게 아니라면
        console.log("내가 보낸게 아니라면 : " + chat.sender);
        
        // 이미지일때
        if(chat.type === 'IMAGE'){
            
            messageElement.classList.add('chat-message');

            if(isReciveChat===false){
                sideElement.classList.add('newDateDiv');

                var avatarElement = document.createElement('img');
                avatarElement.src="/image/login/" + chat.picture;
                
                var usernameElement = document.createElement('span');
                var usernameText = document.createTextNode(chat.sender);
                usernameElement.appendChild(usernameText);
                messageElement.appendChild(usernameElement);

                avatarElement.classList.add('profile');
                messageElement.appendChild(avatarElement);
                isReciveChat = true;
                isSendCaht = false;
                textElement.classList.add('newSendImageDiv')
            }
            else{
                sideElement.classList.add('dateDiv');
                textElement.classList.add('sendImageDiv')
            }
            let imageElement = document.createElement('img');
            imageElement.classList.add('sendImage');
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            
        }
        // 이모티콘 일때
        else if(chat.type === 'EMOTICON'){
            messageElement.classList.add('chat-message');
            console.log("이모티콘 들어옴.")
            if(isReciveChat===false){
                sideElement.classList.add('newDateDiv');
                console.log("isReciveChat === false 들어옴")
                var avatarElement = document.createElement('img');
                avatarElement.src="/image/login/" + chat.picture;
                
                var usernameElement = document.createElement('span');
                var usernameText = document.createTextNode(chat.sender);
                usernameElement.appendChild(usernameText);
                messageElement.appendChild(usernameElement);

                avatarElement.classList.add('profile');
                messageElement.appendChild(avatarElement);
                isReciveChat = true;
                isSendCaht = false;
                textElement.classList.add('newSendImageDiv')
            }else{
                sideElement.classList.add('dateDiv');
                textElement.classList.add('sendImageDiv')
            }

            let imageElement = document.createElement('img');
            imageElement.classList.add('sendEmoticon');
            imageElement.src = chat.message;
            textElement.appendChild(imageElement);
            
        }
        else{
            if(isReciveChat===false || sendUser != chat.sender){
                sideElement.classList.add('newDateDiv');
                sendUser = chat.sender;
                messageElement.classList.add('chat-message');

                var avatarElement = document.createElement('img');
                avatarElement.src="/image/login/" + chat.picture;
                avatarElement.classList.add('profile');
                var avatarText = document.createTextNode(chat.sender);
                avatarElement.appendChild(avatarText);

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
                sideElement.classList.add('dateDiv');
                messageElement.classList.add('chat-message');
                textElement.classList.add('sendtext');
            }
        }
    }
    if(chat.type != 'IMAGE' && chat.type != 'EMOTICON'){
        var messageText = document.createTextNode(chat.message);
        textElement.appendChild(messageText);
    }

    if(chat.type != 'ENTER' && chat.type != 'LEAVE'){
        // 읽음 count
        if(chat.sender === username){
            
            var readCountElement = document.createElement('p');
            readCountElement.classList.add('myReadCount');
            readCountElement.setAttribute('name', 'readCnt');
            readCountElement.value = chat.readCount;
            var readCountText = document.createTextNode(chat.readCount);
            readCountElement.appendChild(readCountText);
            sideElement.appendChild(readCountElement);

            var dateElement = document.createElement('p');
            dateElement.classList.add('chatDate');
            var dateText = document.createTextNode(chat.time);
            dateElement.appendChild(dateText);
            sideElement.appendChild(dateElement);
            
            messageElement.appendChild(sideElement);
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
            sideElement.appendChild(readCountElement);

            var dateElement = document.createElement('p');
            dateElement.classList.add('chatDate');
            var dateText = document.createTextNode(chat.time);
            dateElement.appendChild(dateText);
            sideElement.appendChild(dateElement);

            messageElement.appendChild(sideElement);
        }
    }
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

// 메세지 읽음 안읽음 숫자 카운트 표시
function updateNumber() {
    // AJAX 요청을 통해 서버에서 새로운 숫자 가져오기
    // console.log("updateNumber");
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
        // console.log("?? : " + data);
        countReadData(data);
      })
      .catch(error => {
        console.error('숫자 업데이트 중 오류 발생:', error);
      });
  }


function countReadData(data){
    // console.log("countReadData");
    //let readCnt = document.getElementsByName('readCnt');
    
    // console.log(" data : " + JSON.stringify(data));
    if(data == null) return;
    //console.log("사이즈 : " + data.data.length);

    let read = document.getElementsByClassName("chat-message");
    let myRead = document.getElementsByClassName("chat-myMessage");
    // console.log("유저 이름 : " + username);
    // console.log("read 갯수 : " + read.length);
    // console.log("myRead 갯수 : " + myRead.length);
    if(read.length <= 0 && myRead.length <= 0) {

        return;
    }

    for(let i=0; i<data.data.length; i++){
        // console.log(i + "번째 실행");
        for(let j=0; j<read.length; j++){

            let childDiv = read[j].querySelector("div")
            let readcnt = read[j].querySelector("p");
            if(readcnt != null && readcnt.value < 0){
                read[j].classList.add("chat-Message-ok")
                read[j].classList.remove("chat-message")
                readcnt.remove();
            }
            else if(readcnt != null && data.data[i].message === childDiv.textContent ||
                data.data[i].type === 'IMAGE' || data.data[i].type === 'EMOTICON'){
                
                // console.log('받은 메세지 : ' + i)
                if(data.data[i].readCount <= 0){
                    read[j].classList.add("chat-Message-ok")
                    read[j].classList.remove("chat-message")
                    readcnt.remove();
                    continue;
                };
                readcnt.textContent = data.data[i].readCount;
                readcnt.value = data.data[i].readCount;
            }
        }

        for(let j=0; j<myRead.length; j++){
            // console.log("myChat");
            let childMyDiv = myRead[j].querySelector("div")
            
            let myreadcnt = myRead[j].querySelector("p");
            if(myreadcnt != null && myreadcnt.value < 0){
                myRead[j].classList.add("chat-myMessage-ok")
                myRead[j].classList.remove("chat-myMessage")
                myreadcnt.remove();
            }
            else if(myreadcnt != null && data.data[i].message === childMyDiv.textContent ||
                data.data[i].type === 'IMAGE' || data.data[i].type === 'EMOTICON'){
                // console.log('내가보낸 메세지 : ' + i)    
                if(data.data[i].readCount <= 0){
                    myRead[j].classList.add("chat-myMessage-ok")
                    myRead[j].classList.remove("chat-myMessage")
                    myreadcnt.remove();
                    continue
                };
                myreadcnt.textContent = data.data[i].readCount;
                myreadcnt.value = data.data[i].readCount;
            }
        }
    }
}

function countZeroData(){
    let read = document.getElementsByClassName("chat-message");
    let myRead = document.getElementsByClassName("chat-myMessage");
    console.log("들어옴들어옴들어옴")
    if(read.length <= 0 && myRead.length <= 0) {
        return;
    }

    for(let j=0; j<read.length; j++){
        // let readcntDiv = read[j].querySelector("div");
        let readcnt = read[j].querySelector("readCount");
        if(readcnt != null && parseInt(readcnt.textContent) <= 0) {
            read[j].classList.add("chat-Message-ok")
            read[j].classList.remove("chat-message")
            readcnt.remove();
        }
    }

    for(let j=0; j<myRead.length; j++){
        console.log("myChat");
        let myreadcnt = myRead[j].querySelector(".myReadCount");
        let chatDate = myRead[j].querySelector(".chatDate");
        if(myreadcnt != null && parseInt(myreadcnt.textContent) <= 0) {
            myRead[j].classList.add("chat-myMessage-ok")
            myRead[j].classList.remove("chat-myMessage")
            // chatDate.style.height = 7 + 'px';
            myreadcnt.remove();
        }
    }
}

let emoticonBtns = document.getElementsByName("emoticonBtn");

for(let i = 0; i< emoticonBtns.length; i++){
    emoticonBtns[i].addEventListener("click", function(){
        // 이모티콘 전송
        let emoticonValue = emoticonBtns[i].value.trim();
        if (emoticonValue && stompClient) {
            var chatMessage = {
                "roomId": roomId,
                sender: username,
                userId: userId,
                message: emoticonValue,
                type: 'EMOTICON',
                readCount: 0
            };
            stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        e.preventDefault();
    });
}

function sendEmoticon(emoticon) {
    username = document.getElementById("userName").value.trim();
    userId = document.getElementById("userId").value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            "roomId": roomId,
            sender: username,
            userId: userId,
            message: emoticon,
            type: 'EMOTICON',
            readCount: 0
        };
        // console.log(messageContent)
        stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    
}

let emoticonMessageBtn = document.getElementById('emoticonMessage');
let sendMessageBtn = document.getElementById('sendMessageBtn');
let hiddenEmoticon = document.getElementById('emoticonBox');

let isHidden = true;
emoticonMessageBtn.addEventListener('click', function(){
    if(isHidden){
        // hiddenEmoticon.style.display = "block";
        hiddenEmoticon.classList.remove('hidden');
        sendMessageBtn.classList.add('hidden');
        messageArea.classList.add('emoticonArea');
        isHidden = false;
    }
    else{
        sendMessageBtn.classList.remove('hidden');
        hiddenEmoticon.classList.add('hidden');
        messageArea.classList.remove('emoticonArea');
        isHidden = true;
    }
});

document.getElementById('messageArea').addEventListener('click', function (event) {
    // 클릭된 요소 확인
    const clickedElement = event.target;
    
    if (clickedElement === this || clickedElement.tagName === 'LI' || clickedElement.tagName === 'DIV' || clickedElement.tagName === 'IMG') {
        if(!isHidden){
            sendMessageBtn.classList.remove('hidden');
            hiddenEmoticon.classList.add('hidden');
            messageArea.classList.remove('emoticonArea');
            isHidden = true;
        }
    }
});

function returnRoom(){
    location.href = "/chatlist.action";
}


window.onload = function() {
    connect();
}

// usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)