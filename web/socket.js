var wsocket;
var serviceLocation = "ws://" + document.location.host + document.location.pathname + "batalhanavalendpoint/";
var jogador = '';
var i = 1;
var x, y;
jogador = "jogador";
++i;
var fimDeJogo = false;
var jogou = false;
var acertosAdversario = 0;

function onOpen() {
    writeToScreen("Connected to " + serviceLocation);
}

function writeToScreen(message) {
    alert(message);
}

function onMessageReceived(evt){
    var tiroDisparado = JSON.parse(evt.data);
    var acertou = tiroNoMeuTabuleiro(tiroDisparado.x, tiroDisparado.y);
    if(acertou){
        jogou = true;
        acertosAdversario++;
        if(acertosAdversario === 20){
            fimDeJogo = true;
            alert("Você perdeu!");
        }
    }else{
        jogou = false;
        alert("Agora é sua vez...");
    }
}

function sendMessage(x,y) {
    var msg = '{"x":"' + x + '", "y":"' + y + '"}';
    wsocket.send(msg);
}

function connectToServer(){
    wsocket = new WebSocket(serviceLocation+jogador);
//    wsocket.onopen = pegarJogador;
    wsocket.onmessage = onMessageReceived;
}

function pegarJogador(evt){
    var jogador = JSON.parse(evt.data);
    alert("Conectado: "+jogador);
}

$(document).ready(function(){ 
    connectToServer();
});

