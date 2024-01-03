var stompClient = null;

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function showMessage(message) {
    var chatDiv = document.getElementById('chat');
    var p = document.createElement('p');
    p.textContent = message.sender + ": " + message.content;
    chatDiv.appendChild(p);
}

function sendMessage() {
    var messageContent = document.getElementById('message').value;
    var sender = document.getElementById('sender').value;
    stompClient.send("/app/chat", {}, JSON.stringify({ 'content': messageContent, 'sender': sender }));
}

connect();
