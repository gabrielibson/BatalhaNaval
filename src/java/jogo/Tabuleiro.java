package jogo;

import java.util.ArrayList;
import java.util.List;
import util.Ponto;

/**
 *
 * @author pbeat_000
 */
public class Tabuleiro {
    private List<Embarcacao> embarcacoes;
    private int altura;
    private int largura;
    private final List<Ponto> pontosOcupados;


    public Tabuleiro(int largura, int altura){
        this.largura = largura;
        this.altura = altura;
        this.pontosOcupados = new ArrayList<>();
        this.embarcacoes = new ArrayList<>();
    }
    
    public void adicionarEmbarcacaoAleatoria(Embarcacao embarcacao){
        String direcao;
        List<Ponto> pontosAdjacentes = new ArrayList<>();
        int iniLinha, iniColuna, fimLinha, fimColuna;
        while(true){
            direcao = Math.random() < .5? Embarcacao.VERTICAL : Embarcacao.HORIZONTAL;
            iniLinha = (int) Math.max(1, Math.round(Math.random() * this.altura));
            iniColuna = (int) Math.max(1, Math.round(Math.random() * this.largura));
            if(direcao.equals(Embarcacao.VERTICAL)){
                fimLinha = iniLinha + embarcacao.getTamanho()-1;
                fimColuna = iniColuna;
            }else{
                fimLinha = iniLinha;
                fimColuna = iniColuna + embarcacao.getTamanho()-1;
            }
            embarcacao.setOrientacao(direcao);
            boolean valido = true;
            if(fimLinha >= this.altura || fimColuna >= this.largura){
                continue;
            }
            List<Ponto> pontosCandidatos = this.criarPontosEmbarcacao(iniLinha, iniColuna, fimLinha, fimColuna, embarcacao);
            if(!this.pontosOcupados.isEmpty()){
                for(Ponto ponto : this.pontosOcupados){
                    for(Ponto p : pontosCandidatos){
                        if(ponto.getX() == p.getX() && ponto.getY() == p.getY()){
                            valido = false;
                            break;
                        }
                    }
                }
                if(!valido){
                    continue;
                }
            }
            this.pontosOcupados.addAll(pontosCandidatos);
            if(embarcacao.getOrientacao().equals(Embarcacao.VERTICAL)){
                if(iniLinha-1 >= 0){
                    Ponto p = new Ponto(iniLinha-1, iniColuna);
                    pontosAdjacentes.add(p);
                    if(p.getY()-1 >=0){
                        pontosAdjacentes.add(new Ponto(p.getX(),p.getY()-1));
                    }
                    if(p.getY()+1 < this.largura){
                        pontosAdjacentes.add(new Ponto(p.getX(),p.getY()+1));
                    }
                }
                if(fimLinha+1 < this.altura){
                    Ponto p = new Ponto(fimLinha+1, fimColuna);
                    pontosAdjacentes.add(p);
                    if(p.getY()-1 >=0){
                        pontosAdjacentes.add(new Ponto(p.getX(),p.getY()-1));
                    }
                    if(p.getY()+1 < this.largura){
                        pontosAdjacentes.add(new Ponto(p.getX(),p.getY()+1));
                    }
                }
                
                for(Ponto ponto : pontosCandidatos){
                    if(ponto.getY()-1 >= 0){
                        Ponto p = new Ponto(ponto.getX(), ponto.getY()-1);
                        pontosAdjacentes.add(p);
                    }
                    if(ponto.getY()+1 < this.largura){
                        Ponto p = new Ponto(ponto.getX(), ponto.getY()+1);
                        pontosAdjacentes.add(p);
                    }
                }
                
//                pontosCandidatos.stream().map((ponto) -> {
//                    if(ponto.getY()-1 >= 0){
//                        Ponto p = new Ponto(ponto.getX(), ponto.getY()-1);
//                        pontosAdjacentes.add(p);
//                    }
//                    return ponto;
//                }).filter((ponto) -> (ponto.getY()+1 < this.largura)).map((ponto) -> new Ponto(ponto.getX(), ponto.getY()+1)).forEach((p) -> {
//                    pontosAdjacentes.add(p);
//                });
            }
            
            if(embarcacao.getOrientacao().equals(Embarcacao.HORIZONTAL)){
                if(iniColuna-1 >= 0){
                    Ponto p = new Ponto(iniLinha, iniColuna-1);
                    pontosAdjacentes.add(p);
                    if(p.getX()-1 >=0){
                        pontosAdjacentes.add(new Ponto(p.getX()-1,p.getY()));
                    }
                    if(p.getX()+1 < this.largura){
                        pontosAdjacentes.add(new Ponto(p.getX()+1,p.getY()));
                    }
                }
                if(fimColuna+1 < this.largura){
                    Ponto p = new Ponto(fimLinha, fimColuna+1);
                    pontosAdjacentes.add(p);
                     if(p.getX()-1 >=0){
                        pontosAdjacentes.add(new Ponto(p.getX()-1,p.getY()));
                    }
                    if(p.getX()+1 < this.largura){
                        pontosAdjacentes.add(new Ponto(p.getX()+1,p.getY()));
                    }
                }
                
                for(Ponto ponto : pontosCandidatos){
                    if(ponto.getX()-1 >= 0){
                        Ponto p = new Ponto(ponto.getX()-1, ponto.getY());
                        pontosAdjacentes.add(p);
                    }
                    if(ponto.getX()+1 < this.largura){
                        Ponto p = new Ponto(ponto.getX()+1, ponto.getY());
                        pontosAdjacentes.add(p);
                    }
                }
                
//                pontosCandidatos.stream().map((ponto) -> {
//                    if(ponto.getX()-1 >= 0){
//                        Ponto p = new Ponto(ponto.getX()-1, ponto.getY());
//                        pontosAdjacentes.add(p);
//                    }
//                    return ponto;
//                }).filter((ponto) -> (ponto.getX()+1 < this.altura)).map((ponto) -> new Ponto(ponto.getX()+1, ponto.getY())).forEach((p) -> {
//                    pontosAdjacentes.add(p);
//                });
            }
            this.pontosOcupados.addAll(pontosAdjacentes);
            embarcacao.setCelulasEmbarcacao(pontosCandidatos);
            this.embarcacoes.add(embarcacao);
            if(valido){
                break;
            }
        }
    }
    
    public List<Ponto> criarPontosEmbarcacao(int iniLinha, int iniColuna, int fimLinha, int fimColuna, Embarcacao embarcacao){
        Ponto pontoInicial = new Ponto(iniLinha, iniColuna);
        List<Ponto> pontosCandidatos = new ArrayList<>();
        pontosCandidatos.add(pontoInicial);
        for(int i = 0; i < embarcacao.getTamanho()-1; i++){
            if(embarcacao.getOrientacao().equals(Embarcacao.VERTICAL)){
                Ponto p = new Ponto(++iniLinha, iniColuna);
                pontosCandidatos.add(p);
            }else{
                Ponto p = new Ponto(iniLinha, ++iniColuna);
                pontosCandidatos.add(p);
            }
        }
        return pontosCandidatos;
    }    
    
    public List<Embarcacao> getEmbarcacoes() {
        return embarcacoes;
    }

    public void setEmbarcacoes(List<Embarcacao> embarcacoes) {
        this.embarcacoes = embarcacoes;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }
    
    
}
