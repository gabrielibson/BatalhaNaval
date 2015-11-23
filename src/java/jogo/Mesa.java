/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import util.Perfil;

/**
 *
 * @author gabriel.ibson
 */
public class Mesa {
    private BatalhaNaval batalhaNaval;
    private Perfil perfil;

    public BatalhaNaval getBatalhaNaval() {
        return batalhaNaval;
    }

    public void setBatalhaNaval(BatalhaNaval batalhaNaval) {
        this.batalhaNaval = batalhaNaval;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
