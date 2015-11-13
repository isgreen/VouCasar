package br.com.jed.interfaces;

/**
 * Created by Wolfstein on 10/11/2015.
 */
public interface FragmentBase {
    int alterarVisibilidade();

    boolean salvarCadastro();

    boolean validarCampos();

    void limparCampos();

    Object getDadosFromUI();
}
