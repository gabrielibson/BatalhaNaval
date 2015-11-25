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
var xhr = new XMLHttpRequest();

function onOpen() {
    writeToScreen("Connected to " + serviceLocation);
}

function writeToScreen(message) {
    alert(message);
}

function onMessageReceived(evt) {
    var tiroDisparado = JSON.parse(evt.data);
    if (tiroDisparado !== 'undefined') {
        var acertou = tiroNoMeuTabuleiro(tiroDisparado.x, tiroDisparado.y);
        if (acertou) {
            jogou = true;
            acertosAdversario++;
            if (acertosAdversario === 20) {
                fimDeJogo = true;
                alert("Você perdeu!");
            }
        } else {
            jogou = false;
            alert("Agora é sua vez...");
        }
    } else {
        alert(evt.data);
        location.reload();
    }
}

function sendMessage(x,y) {
    var msg = '{"x":"' + x + '", "y":"' + y + '"}';
    wsocket.send(msg);
}

function connectToServer(){
    wsocket = new WebSocket(serviceLocation+mesa+"/"+jogador);
//  wsocket.onopen = pegarJogador;
    wsocket.onmessage = onMessageReceived;
}

function pegarJogador(evt){
    var jogador = JSON.parse(evt.data);
    alert("Conectado: "+jogador);
}

function isVisualizador(){
    return visualizador;
}

function sair(){
   
    if (!isVisualizador()) {
        xhr.onreadystatechange = function () {
		if(xhr.readyState === 4) {
                    setFimDeJogo(true);
                    wsocket.send("Seu oponente encerrou a partida!");
                }
	};        
        var params = "mesa="+encodeURIComponent(codMesa);
        xhr.open("POST", "BatalhaNavalServlet", true);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.setRequestHeader("Content-length", params.length);
	xhr.send(params);        
    }
    
    $('.batalha-signin').show();
    $('.batalha-wrapper').hide();
    wsocket.close();
 }

$(document).ready(function () {
    $nickName = $('#nickname');
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
        sair();        
    });
});

