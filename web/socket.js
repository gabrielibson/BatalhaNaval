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
    if (evt.data.indexOf("x") !== -1) {
        var acertou = false;
        var tiroDisparado = JSON.parse(evt.data);
        if (!visualizador) {
            acertou = tiroNoMeuTabuleiro(tiroDisparado.x, tiroDisparado.y);
            if (acertou) {
                jogou = true;
            } else {
                jogou = false;
                alert("Agora é sua vez...");
            }
        }else{
            verificarTiroDisparado(tiroDisparado.x, tiroDisparado.y);
            tiroNoMeuTabuleiro(tiroDisparado.x, tiroDisparado.y);
        }
    } else if(evt.data.indexOf("2") !== -1){
        var nickname = evt.data.split("_")[1];
        $("#tabuleiro-jogador2").show();
        $("#aguardando").hide();
        document.getElementById("jogador2").innerHTML = nickname;
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
    var msg = '{"x":"' + x + '", "y":"' + y + '","sender":"'+$nickName+'"}';
    wsocket.send(msg);
}

function open(evt){
    alert(evt.data);
}

function connectToServer(){
    if(wsocket !== undefined){
        wsocket.close();
    }
    wsocket = new WebSocket(serviceLocation+mesa+"/"+perfil+"/"+$nickName);
    //wsocket.onopen = open;
    wsocket.onmessage = onMessageReceived;
}

//    function pegarJogador(evt){
//        var jogador = JSON.parse(evt.data);
//        alert("Conectado: "+jogador);
//    }

function isVisualizador(){
    return visualizador;
}

function getNickName(){
    return $nickName;
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

function validarEntrada(nome, codMesa){
    msgErro = "";
    var valido = true;
    if(nome === undefined || nome === ""){
        msgErro = "Digite um NickName!";
        $("#nickname").focus();
        valido = false;
    }else if(codMesa === "0"){
        msgErro = "Escolha uma Mesa para jogar ou visualizar!";
        $("#mesa").focus();
        valido = false;
    }
    return valido;
}

$(document).ready(function () {
    $batalhaWindow = $('.batalha-wrapper');
    $batalhaWindow.hide();
    $telaInicial = $('.batalha-signin');
       
    $("#jogar").click(function (evt) {
        $nickName = $('#nickname').val();
        codMesa = $('#mesa option:selected').val();
        if (validarEntrada($nickName, codMesa)) {
            mesa = "mesa" + codMesa;
            perfil = "jogador";
            MontarTabuleiro(codMesa, perfil, $nickName);          
        } else {
            alert(msgErro);
        }   
        return false;
    });

    $("#visualizar").click(function (evt) {
        $nickName = $('#nickname').val();
        visualizador = true;
        codMesa = $('#mesa option:selected').val();
        if (validarEntrada($nickName, codMesa)) {
            mesa = "mesa" + codMesa;
            perfil = "visualizador";
            MontarTabuleiro(codMesa, perfil, $nickName);
        } else {
            alert(msgErro);
        }
        return false;
    });
    
    $("#sair").click(function(){
        sair($nickName+" abandonou a partida!");        
    });
});

