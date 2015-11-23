var xhr = new XMLHttpRequest();
var embarcacoes;
var embarcacoesAdversarias;
var jogou = false;

function tratarColecao(){
    if(xhr.readyState === 4){
        var text = xhr.responseText;
        var partida =  JSON.parse(text);
        var jogador1 =  partida.jogador1;
        var jogador2 = partida.jogador2;
        
        if(!partida.temSegundoJogador){
            embarcacoes = jogador1.tabuleiro.embarcacoes;
            embarcacoesAdversarias = jogador2.tabuleiro.embarcacoes;
            for(var i = 0; i < embarcacoes.length; i++){
                var embarcacao = embarcacoes[i];
                for(var j = 0; j < embarcacao.celulasEmbarcacao.length; j++){
                    var celula = embarcacao.celulasEmbarcacao[j];
                    var div = document.getElementById("tabuleiro1_casa_"+celula.x+"_"+celula.y);
                    div.style.backgroundColor = "cyan";
             //       div.setAttribute('class', 'ocupado');
                } 
            }
        
            for(var i = 0; i < embarcacoesAdversarias.length; i++){
                var embarcacao = embarcacoesAdversarias[i];
                for(var j = 0; j < embarcacao.celulasEmbarcacao.length; j++){
                    var celula = embarcacao.celulasEmbarcacao[j];
                    var div = document.getElementById("tabuleiro2_casa_"+celula.x+"_"+celula.y);
                //    div.setAttribute('class', 'ocupado');
                } 
            }
        }else{
            embarcacoes = jogador2.tabuleiro.embarcacoes;
            embarcacoesAdversarias = jogador1.tabuleiro.embarcacoes;
            for(var i = 0; i < embarcacoes.length; i++){
                var embarcacao = embarcacoes[i];
                for(var j = 0; j < embarcacao.celulasEmbarcacao.length; j++){
                    var celula = embarcacao.celulasEmbarcacao[j];
                    var div = document.getElementById("tabuleiro1_casa_"+celula.x+"_"+celula.y);
                    div.style.backgroundColor = "cyan";
                 //   div.setAttribute('class', 'ocupado');
                } 
            }
        
            for(var i = 0; i < embarcacoesAdversarias.length; i++){
                var embarcacao = embarcacoesAdversarias[i];
                for(var j = 0; j < embarcacao.celulasEmbarcacao.length; j++){
                    var celula = embarcacao.celulasEmbarcacao[j];
                    var div = document.getElementById("tabuleiro2_casa_"+celula.x+"_"+celula.y);
                   // div.setAttribute('class', 'ocupado');
                } 
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
                break;
            }
        }
    }
    if(!acertou){
           var div = document.getElementById("tabuleiro2_casa_"+x+"_"+y);
           div.innerHTML = "<span style='margin-left: 10px; margin-top: 10px;'>.</span>";
           div.style.border = "1px solid #0000ff";
           div.style.backgroundColor = "#ffffff";
    }
    return acertou;
}

function atirarTabuleiroAdversario(x, y){
    var div = document.getElementById("tabuleiro2_casa_"+x+"_"+y);
    div.style.backgroundColor = "green";
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
        }
        sendMessage(posx,posy);
    }
    else{
       alert("Aguarde sua vez");
    }
}

function MontarTabuleiro(){
	var i;
        var tabuleiro1 = document.getElementById("tabuleiro-jogador1");
        var tabuleiro2 = document.getElementById("tabuleiro-jogador2");
	for (i=0; i<10; i++){
            divLinhaTabuleiro1 = "<div id='tabuleiro1_linha_"+i.toString()+"' class='linha' >";
            divLinhaTabuleiro2 = "<div  id='tabuleiro2_linha_"+i.toString()+"' class='linha' >";
            var newChildTabuleiro1 = document.createElement("div");
            var newChildTabuleiro2 = document.createElement("div");
            tabuleiro1.appendChild(newChildTabuleiro1);
            tabuleiro2.appendChild(newChildTabuleiro2);
            newChildTabuleiro1.innerHTML = divLinhaTabuleiro1;
            newChildTabuleiro2.innerHTML = divLinhaTabuleiro2;
            for (var j=0; j<10; j++){
		var nome_casaTabuleiro1 ="tabuleiro1_casa_"+i.toString()+"_"+j.toString();
                var nome_casaTabuleiro2 ="tabuleiro2_casa_"+i.toString()+"_"+j.toString();
                var divColunaTabuleiro1 = "<div id='"+nome_casaTabuleiro1+"' style='border: solid 1px; width:30px; float:left; height:30px'/>";
                var divColunaTabuleiro2 = "<div id='"+nome_casaTabuleiro2+"' onclick='atirar(event)' class='celula' style='border: solid 1px; width:30px; float:left; height:30px'/>";
                var newChildTab1 = document.createElement("div");
                var newChildTab2 = document.createElement("div");
                var colunaTab1 = document.getElementById("tabuleiro1_linha_"+i.toString());
                var colunaTab2 = document.getElementById("tabuleiro2_linha_"+i.toString());
                colunaTab1.appendChild(newChildTab1);
                colunaTab2.appendChild(newChildTab2);
                newChildTab1.innerHTML = divColunaTabuleiro1;
                newChildTab2.innerHTML = divColunaTabuleiro2;
            }
	}
    xhr.onreadystatechange = tratarColecao;
    xhr.open("get", "BatalhaNavalServlet", true);
    xhr.send(null);
        
}

onload = MontarTabuleiro;