package br.com.jed.model.bean;

/**
 * Created by Wolfstein on 28/10/2015.
 */
public class Usuario {

    private int id;
    private String email;
    private String senha_privada;
    private String senha_grupo;

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

    public String getSenha_privada() {
        return senha_privada;
    }

    public void setSenha_privada(String senha_privada) {
        this.senha_privada = senha_privada;
    }

    public String getSenha_grupo() {
        return senha_grupo;
    }

    public void setSenha_grupo(String senha_grupo) {
        this.senha_grupo = senha_grupo;
    }
}