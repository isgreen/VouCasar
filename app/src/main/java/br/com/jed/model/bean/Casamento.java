package br.com.jed.model.bean;

import java.sql.Blob;
import java.util.Date;

import br.com.jed.enumaretors.Situacao;

/**
 * Created by Wolfstein on 28/10/2015.
 */
public class Casamento {

    private int id;
    private String nomeCasal;
    private Date dataCerimonia;
    private String localCerimonia;
    private Blob fotoCasal;
    private Usuario usuario;
    private Situacao situacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCasal() {
        return nomeCasal;
    }

    public void setNomeCasal(String nomeCasal) {
        this.nomeCasal = nomeCasal;
    }

    public Date getDataCerimonia() {
        return dataCerimonia;
    }

    public void setDataCerimonia(Date dataCerimonia) {
        this.dataCerimonia = dataCerimonia;
    }

    public String getLocalCerimonia() {
        return localCerimonia;
    }

    public void setLocalCerimonia(String localCerimonia) {
        this.localCerimonia = localCerimonia;
    }

    public Blob getFotoCasal() {
        return fotoCasal;
    }

    public void setFotoCasal(Blob fotoCasal) {
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
