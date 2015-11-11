package br.com.jed.model.bean;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import br.com.jed.enumaretors.Situacao;

/**
 * Created by Wolfstein on 28/10/2015.
 */
public class Casamento implements Serializable {

    private int id;
    private String nomeNoivo;
    private String nomeNoiva;
    private Date dataCerimonia;
    private String enderecoCerimonia;
    private byte[] fotoCasal;
    private Usuario usuario;
    private Situacao situacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeNoivo() {
        return nomeNoivo;
    }

    public void setNomeNoivo(String nomeNoivo) {
        this.nomeNoivo = nomeNoivo;
    }

    public String getNomeNoiva() {
        return nomeNoiva;
    }

    public void setNomeNoiva(String nomeNoiva) {
        this.nomeNoiva = nomeNoiva;
    }

    public Date getDataCerimonia() {
        return dataCerimonia;
    }

    public void setDataCerimonia(Date dataCerimonia) {
        this.dataCerimonia = dataCerimonia;
    }

    public String getEnderecoCerimonia() {
        return enderecoCerimonia;
    }

    public void setEnderecoCerimonia(String enderecoCerimonia) {
        this.enderecoCerimonia = enderecoCerimonia;
    }

    public byte[] getFotoCasal() {
        return fotoCasal;
    }

    public void setFotoCasal(byte[] fotoCasal) {
        this.fotoCasal = fotoCasal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
}