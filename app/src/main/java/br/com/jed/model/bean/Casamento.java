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

}
