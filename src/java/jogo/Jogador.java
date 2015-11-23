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
public class Jogador {
    private int codigo;
    private String nome;
    private Tabuleiro tabuleiro;
    private Tabuleiro tabuleiroContra;

    public Jogador(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;
    }
    
    public void setTabuleiroContra(Tabuleiro tabuleiro){
        this.tabuleiroContra = tabuleiro;
    }
    
    public Tabuleiro getTabuleiroContra(){
        return this.tabuleiroContra;
    }
    
    public void setTabuleiro(Tabuleiro tabuleiro){
        this.tabuleiro = tabuleiro;
    }
    
    public Tabuleiro getTabuleiro(){
        return this.tabuleiro;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    
    public int getCodigo(){
        return this.codigo;
    }
}
