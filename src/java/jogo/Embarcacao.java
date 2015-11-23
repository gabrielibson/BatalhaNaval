/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import java.util.ArrayList;
import java.util.List;
import util.Ponto;

/**
 *
 * @author pbeat_000
 */
public class Embarcacao {
    private List<Ponto> celulasEmbarcacao;
    private int tamanho;

    public static final String VERTICAL = "v";
    public static final String HORIZONTAL = "h";
    private String orientacao;

    public Embarcacao() {
        this.celulasEmbarcacao = new ArrayList<>();
    }

    public Embarcacao(int tamanho) {
        this.celulasEmbarcacao = new ArrayList<>();
        this.tamanho = tamanho;
    }
    
    public String getOrientacao() {
        return orientacao;
    }

    public void setOrientacao(String orientacao) {
        this.orientacao = orientacao;
    }
    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
    
    public List<Ponto> getCelulasEmbarcacao() {
        return celulasEmbarcacao;
    }

    public void setCelulasEmbarcacao(List<Ponto> celulasEmbarcacao) {
        this.celulasEmbarcacao = celulasEmbarcacao;
    }
}
