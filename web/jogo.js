var xhr = new XMLHttpRequest();
var embarcacoes;
var embarcacoesAdversarias;
var jogou = false;
var meusAcertos = 0;
var mesaSemJogador = false;
var mesaComDoisJogadores = false;
var profile;
var tabuleiro1 = document.getElementById("tabuleiro-jogador1");
var tabuleiro2 = document.getElementById("tabuleiro-jogador2");

function tratarColecao(){
    if (xhr.readyState === 4) {
        var text = xhr.responseText;
        if (text !== "") {
            var partida = JSON.parse(text);
            if (!isVisualizador()) {
                setEmbarcacoesJogador(partida);
                $('.batalha-signin').hide();
                $('.batalha-wrapper').show();
            } else {
                setEmbarcacoesVisualizador(partida);
                $('.batalha-signin').hide();
                $('.batalha-wrapper').show();
            }
        }else {
            if (profile === "visualizador") {
                mesaSemJogador = true;
                tabuleiro1.innerHTML = "";
                tabuleiro2.innerHTML = "";
                alert("A mesa ainda não tem jogadores, por favor tente outra mesa ou clique em Jogar.");
            }
            else {
                mesaComDoisJogadores = true;
                tabuleiro1.innerHTML = "";
                tabuleiro2.innerHTML = "";
                alert("A mesa já tem dois jogadores, por favor dirija-se a outra mesa para jogar ou clique em Visualizar.");
            }
        }
    }
}

function MontarTabuleiro(codMesa, perfil) {
    var i;
    tabuleiro1 = document.getElementById("tabuleiro-jogador1");
    tabuleiro2 = document.getElementById("tabuleiro-jogador2");
    for (i = 0; i < 10; i++) {
        divLinhaTabuleiro1 = "<div id='tabuleiro1_linha_" + i.toString() + "' class='linha' >";
        divLinhaTabuleiro2 = "<div  id='tabuleiro2_linha_" + i.toString() + "' class='linha' >";
        var newChildTabuleiro1 = document.createElement("div");
        var newChildTabuleiro2 = document.createElement("div");
        tabuleiro1.appendChild(newChildTabuleiro1);
        tabuleiro2.appendChild(newChildTabuleiro2);
        newChildTabuleiro1.innerHTML = divLinhaTabuleiro1;
        newChildTabuleiro2.innerHTML = divLinhaTabuleiro2;
        for (var j = 0; j < 10; j++) {
            var nome_casaTabuleiro1 = "tabuleiro1_casa_" + i.toString() + "_" + j.toString();
            var nome_casaTabuleiro2 = "tabuleiro2_casa_" + i.toString() + "_" + j.toString();
            var divColunaTabuleiro1 = "<div id='" + nome_casaTabuleiro1 + "' style='border: solid 1px; width:30px; float:left; height:30px'/>";
            var divColunaTabuleiro2 = "";
            if(!isVisualizador()){
                divColunaTabuleiro2 = "<div id='" + nome_casaTabuleiro2 + "' onclick='atirar(event)' class='celula' style='border: solid 1px; width:30px; float:left; height:30px'/>";
            }else{
                divColunaTabuleiro2 = "<div id='" + nome_casaTabuleiro2 + "' style='border: solid 1px; width:30px; float:left; height:30px'/>";
            }            
            var newChildTab1 = document.createElement("div");
            var newChildTab2 = document.createElement("div");
            var colunaTab1 = document.getElementById("tabuleiro1_linha_" + i.toString());
            var colunaTab2 = document.getElementById("tabuleiro2_linha_" + i.toString());
            colunaTab1.appendChild(newChildTab1);
            colunaTab2.appendChild(newChildTab2);
            newChildTab1.innerHTML = divColunaTabuleiro1;
            newChildTab2.innerHTML = divColunaTabuleiro2;
        }
    }
    profile = perfil;
    xhr.onreadystatechange = tratarColecao;
    xhr.open("get", "BatalhaNavalServlet?mesa="+codMesa+"&perfil="+perfil, true);
    xhr.send(null);
}

function setEmbarcacoesVisualizador(partida){
    var jogador1 = partida.jogador1;
    var jogador2 = partida.jogador2;
    var embarcacoes = jogador1.tabuleiro.embarcacoes;
    var embarcacoesAdversarias = jogador2.tabuleiro.embarcacoes;
    for (var i = 0; i < embarcacoes.length; i++) {
        var embarcacao = embarcacoes[i];
        for (var j = 0; j < embarcacao.celulasEmbarcacao.length; j++) {
            var celula = embarcacao.celulasEmbarcacao[j];
            var div = document.getElementById("tabuleiro1_casa_" + celula.x + "_" + celula.y);
            div.style.backgroundColor = "cyan";
        }
    }
    for (var i = 0; i < embarcacoesAdversarias.length; i++) {
        var embarcacao = embarcacoesAdversarias[i];
        for (var j = 0; j < embarcacao.celulasEmbarcacao.length; j++) {
            var celula = embarcacao.celulasEmbarcacao[j];
            var div = document.getElementById("tabuleiro2_casa_" + celula.x + "_" + celula.y);
            div.style.backgroundColor = "cyan";
        }
    }
}

function setEmbarcacoesJogador(partida) {
    var jogador1 = partida.jogador1;
    var jogador2 = partida.jogador2;
    if (!partida.temSegundoJogador) {
        embarcacoes = jogador1.tabuleiro.embarcacoes;
        embarcacoesAdversarias = jogador2.tabuleiro.embarcacoes;
        for (var i = 0; i < embarcacoes.length; i++) {
            var embarcacao = embarcacoes[i];
            for (var j = 0; j < embarcacao.celulasEmbarcacao.length; j++) {
                var celula = embarcacao.celulasEmbarcacao[j];
                var div = document.getElementById("tabuleiro1_casa_" + celula.x + "_" + celula.y);
                div.style.backgroundColor = "cyan";
            }
        }
    } else {
        embarcacoes = jogador2.tabuleiro.embarcacoes;
        embarcacoesAdversarias = jogador1.tabuleiro.embarcacoes;
        for (var i = 0; i < embarcacoes.length; i++) {
            var embarcacao = embarcacoes[i];
            for (var j = 0; j < embarcacao.celulasEmbarcacao.length; j++) {
                var celula = embarcacao.celulasEmbarcacao[j];
                var div = document.getElementById("tabuleiro1_casa_" + celula.x + "_" + celula.y);
                div.style.backgroundColor = "cyan";
            }
        }
    }
}

function tiroNoMeuTabuleiro(x,y){
    var acertou = false;
    for(var i = 0; i < embarcacoes.length; i++){
        var embarcacao = embarcacoes[i];
        for(var j = 0; j < embarcacao.celulasEmbarcacao.length; j++){
            var celula = embarcacao.celulasEmbarcacao[j];
            if(celula.x === parseInt(x) && celula.y === parseInt(y)){
                acertou = true;
                var div = document.getElementById("tabuleiro1_casa_"+celula.x+"_"+celula.y);
                div.innerHTML = "<span style='margin-left: 10px; margin-top: 10px;'>X</span>";
                div.style.border = "1px solid #ff0000";
                div.style.backgroundColor = "#ffffff";
                break;
            }
        }
    }
    if(!acertou){
        var div = document.getElementById("tabuleiro1_casa_"+x+"_"+y);
        div.innerHTML = "<span style='margin-left: 10px; margin-top: 10px;'>.</span>";
        div.style.border = "1px solid #0000ff";
        div.style.backgroundColor = "#ffffff";
    }
    return acertou;
}

function verificarTiroDisparado(x, y){
    var acertou = false;
    for(var i = 0; i < embarcacoesAdversarias.length; i++){
        var embarcacao = embarcacoesAdversarias[i];
        for(var j = 0; j < embarcacao.celulasEmbarcacao.length; j++){
            var celula = embarcacao.celulasEmbarcacao[j];
            if(celula.x === parseInt(x) && celula.y === parseInt(y)){
                acertou = true;
                var div = document.getElementById("tabuleiro2_casa_"+celula.x+"_"+celula.y);
                div.innerHTML = "<span style='margin-left: 10px; margin-top: 10px;'>X</span>";
                div.style.border = "1px solid #ff0000";
                div.style.backgroundColor = "#ffffff";
                $(div).attr("onclick", "");
                break;
            }
        }
    }
    if(!acertou){
           var div = document.getElementById("tabuleiro2_casa_"+x+"_"+y);
           div.innerHTML = "<span style='margin-left: 10px; margin-top: 10px;'>.</span>";
           div.style.border = "1px solid #0000ff";
           div.style.backgroundColor = "#ffffff";
           $(div).attr("onclick", "");
    }
    return acertou;
}

function atirar(evt){
    if(!jogou){
        jogou = true;
        var posicao = evt.target.id;
        var str = posicao.split("_");
        var posx = str[2];
        var posy = str[3];
        var acertou = verificarTiroDisparado(posx,posy);
        if(acertou){
            jogou = false;
            meusAcertos++;
            if(meusAcertos === 20){
                fimDeJogo = true;
                alert("Você venceu!");
            }
        }
        sendMessage(posx,posy);
    }
    else{
        if(!fimDeJogo){
            alert("Aguarde sua vez");
        }else{
            alert("Fim de Jogo");
            novoJogo = confirm("Você deseja iniciar um novo jogo?");
            if(novoJogo){
                location.reload();
            }
        }
    }
}