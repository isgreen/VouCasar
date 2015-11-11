package br.com.jed.validators;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import br.com.jed.voucasar.R;

/**
 * Created by Wolfstein on 07/11/2015.
 */
public class ValidadorUI {

    public static boolean validarCamposNulos(EditText[] listaCampos, String mensagemCampo){
        boolean camposValidados = true;

        //Passeando por todos os Edit's e verificando se cada um está vazio.
        for (EditText campo: listaCampos){
            //Caso esteja vazio é setada uma mensagem no campo.
            if (campo.getText().toString().equals("")){
                campo.setError(mensagemCampo);
                camposValidados = false;
            }
        }

        return camposValidados;
    }
}
