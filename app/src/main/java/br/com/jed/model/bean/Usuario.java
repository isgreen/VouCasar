package br.com.jed.model.bean;

import java.io.Serializable;

/**
 * Created by Wolfstein on 28/10/2015.
 */
public class Usuario implements Serializable {

    private int id;
    private String email;
    private String senhaPrivada;
    private String senhaConvidados;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaPrivada() {
        return senhaPrivada;
    }

    public void setSenhaPrivada(String senhaPrivada) {
        this.senhaPrivada = senhaPrivada;
    }

    public String getSenhaConvidados() {
        return senhaConvidados;
    }

    public void setSenhaConvidados(String senhaConvidados) {
        this.senhaConvidados = senhaConvidados;
    }
}