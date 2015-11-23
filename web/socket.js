var wsocket;
var serviceLocation = "ws://" + document.location.host + document.location.pathname + "batalhanavalendpoint/";
var jogador = '';
var i = 0;
var x, y;
jogador = "jogador";
var fimDeJogo = false;
var jogou = false;
var acertosAdversario = 0;
var $nickName;
var mesa = "";
var $batalhaWindow;
var visualizador = false;

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
    mesa = "mesa" + $('#mesa option:selected').val();
    wsocket = new WebSocket(serviceLocation+mesa+"/"+jogador);
//    wsocket.onopen = pegarJogador;
    wsocket.onmessage = onMessageReceived;
}

function pegarJogador(evt){
    var jogador = JSON.parse(evt.data);
    alert("Conectado: "+jogador);
}

function isVisualizador(){
    return visualizador;
}

$(document).ready(function(){ 
    $nickName = $('#nickname');
    $batalhaWindow = $('.batalha-wrapper');
    $batalhaWindow.hide();
    $("#jogar").click(function(evt){
       MontarTabuleiro();
       evt.preventDefault();
       connectToServer();
       $('.batalha-signin').hide();
       $batalhaWindow.show();
    });
    
    $("#visualizar").click(function(evt){
       visualizador = true;
       evt.preventDefault();
       MontarTabuleiro();
       connectToServer();
       $('.batalha-signin').hide();
       $batalhaWindow.show();
    });
});

