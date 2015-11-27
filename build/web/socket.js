var wsocket;
var serviceLocation = "ws://" + document.location.host + document.location.pathname + "batalhanavalendpoint/";
var perfil = '';
var i = 0;
var x, y;
var fimDeJogo = false;
var jogou = false;
var $nickName;
var mesa = "";
var $batalhaWindow;
var visualizador = false;
var xhr = new XMLHttpRequest();

function onOpen() {
    writeToScreen("Connected to " + serviceLocation);
}

function writeToScreen(message) {
    alert(message);
}

function onMessageReceived(evt) {
    if(evt.data.indexOf("x") !== -1){
        var tiroDisparado = JSON.parse(evt.data);
        var acertou = tiroNoMeuTabuleiro(tiroDisparado.x, tiroDisparado.y);
        if (acertou) {
            jogou = true;
        } else {
            jogou = false;
            alert("Agora é sua vez...");
        }
    }else{
        deixarPartida(evt);
    }
}

function deixarPartida(evt){
        alert(evt.data);
        jogou = false;
        fimDeJogo = true;
        document.getElementById("tabuleiro-jogador1").innerHTML = "";
        document.getElementById("tabuleiro-jogador2").innerHTML = "";
        location.reload();
}

function sendMensagem(msg){
    wsocket.send(msg);
}

function sendTiro(x,y) {
    var msg = '{"x":"' + x + '", "y":"' + y + '"}';
    wsocket.send(msg);
}

function connectToServer(){
    if(wsocket !== undefined){
        wsocket.close();
    }
    wsocket = new WebSocket(serviceLocation+mesa+"/"+perfil);
//  wsocket.onopen = pegarJogador;
    wsocket.onmessage = onMessageReceived;
}

//    function pegarJogador(evt){
//        var jogador = JSON.parse(evt.data);
//        alert("Conectado: "+jogador);
//    }

function isVisualizador(){
    return visualizador;
}

function sair(msg) {
    if (!isVisualizador()) {
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                $('.batalha-wrapper').hide();
                $('.batalha-signin').show();
                document.getElementById("tabuleiro-jogador1").innerHTML = "";
                document.getElementById("tabuleiro-jogador2").innerHTML = "";
                zerarMeusAcertos();
                jogou = false;
                sendMensagem(msg);
            }
        };
        var params = "mesa=" + encodeURIComponent(codMesa);
        xhr.open("POST", "BatalhaNavalServlet", true);
        xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhr.send(params);
    }else{
        location.reload();
        wsocket.close();
    }
}

$(document).ready(function () {
    $nickName = $('#nickname').val();
    $batalhaWindow = $('.batalha-wrapper');
    $batalhaWindow.hide();
    $telaInicial = $('.batalha-signin');
       
    $("#jogar").click(function (evt) {
        codMesa = $('#mesa option:selected').val();
        mesa = "mesa" + codMesa;
        perfil = "jogador";
        MontarTabuleiro(codMesa, perfil);
        evt.preventDefault();
        connectToServer();
    });

    $("#visualizar").click(function (evt) {
        visualizador = true;
        codMesa = $('#mesa option:selected').val();
        mesa = "mesa" + codMesa;
        perfil = "visualizador";
        MontarTabuleiro(codMesa, perfil);
        evt.preventDefault();
        connectToServer();
    });
    
    $("#sair").click(function(){
        sair("Seu oponente abandonou a partida!");        
    });
});

