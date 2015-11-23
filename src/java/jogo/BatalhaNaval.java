/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

/**
 *
 * @author pbeat_000
 */
public class BatalhaNaval {
    private Jogador jogador1;
    private Jogador jogador2;
    private boolean temSegundoJogador;

    public boolean isTemSegundoJogador() {
        return temSegundoJogador;
    }

    public void setTemSegundoJogador(boolean temSegundoJogador) {
        this.temSegundoJogador = temSegundoJogador;
    }

    public BatalhaNaval(Jogador jogador1, Jogador jogador2) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
    }    
    
    public Jogador getJogador1() {
        return jogador1;
    }

    public void setJogador1(Jogador jogador1) {
        this.jogador1 = jogador1;
    }

    public Jogador getJogador2() {
        return jogador2;
    }

    public void setJogador2(Jogador jogador2) {
        this.jogador2 = jogador2;
    }
    
    
}
