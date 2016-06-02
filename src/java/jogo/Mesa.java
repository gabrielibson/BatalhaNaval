/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import util.Perfil;


public class Mesa implements Comparable<Mesa>{
    private String nome;
    private int codigo;
    private BatalhaNaval batalhaNaval;

    public Mesa(String nome, int codigo, BatalhaNaval batalhaNaval) {
        this.nome = nome;
        this.codigo = codigo;
        this.batalhaNaval = batalhaNaval;
    }
    
    public Mesa(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public BatalhaNaval getBatalhaNaval() {
        return batalhaNaval;
    }

    public void setBatalhaNaval(BatalhaNaval batalhaNaval) {
        this.batalhaNaval = batalhaNaval;
    }

    @Override
    public int compareTo(Mesa o) {
        if(this.codigo < o.codigo){
            return -1;
        }
        if(this.codigo > o.codigo){
            return 1;
        }
        return 0;
    }
}
