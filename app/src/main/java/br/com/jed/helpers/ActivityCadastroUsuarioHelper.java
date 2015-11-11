package br.com.jed.helpers;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import br.com.jed.voucasar.R;

/**
 * Created by Wolfstein on 07/11/2015.
 */
@Deprecated
public class ActivityCadastroUsuarioHelper {

    private EditText mEdtEmailUsuario, mEdtSenhaPrivada, mEdtSenhaPublica;

    public ActivityCadastroUsuarioHelper(AppCompatActivity activity) {
        mEdtEmailUsuario = (EditText) activity.findViewById(R.id.edtEmailUsuario);
        mEdtSenhaPrivada = (EditText) activity.findViewById(R.id.edtSenhaPrivada);
        mEdtSenhaPublica = (EditText) activity.findViewById(R.id.edtSenhaConvidados);
    }

}
