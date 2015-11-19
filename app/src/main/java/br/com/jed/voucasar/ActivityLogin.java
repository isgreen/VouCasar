package br.com.jed.voucasar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import br.com.jed.enumaretors.TipoUsuario;
import br.com.jed.model.bean.Usuario;
import br.com.jed.task.TaskCarregarDados;
import br.com.jed.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityLogin extends AppCompatActivity {

    private Usuario mUsuarioSelecionado = null;
    private Usuario mUsuarioInformado = null;
    private TipoUsuario mTipoUsuario;
    @Bind(R.id.edtEmail)
    EditText mEdtEmail;
    @Bind(R.id.edtSenha)
    EditText mEdtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //Mudando a fonte do titulo da tela de login
        Typeface fonte = Typeface.createFromAsset(getAssets(), "fonts/font_casamento.otf");
        TextView txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtTitulo.setTypeface(fonte);

        Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsuarioInformado();

                if (validarCampoNulo())
                    consultarUsuario();
            }
        });

        Button btnCadastroCasamento = (Button) findViewById(R.id.btnCadastroCasamento);
        btnCadastroCasamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogin.this, ActivityCadastroUsuario.class));
            }
        });
    }

    private boolean validarCampoNulo() {
        boolean todosValidados = true;

        if (mUsuarioInformado.getEmail().isEmpty() || mUsuarioInformado.getSenhaPrivada().isEmpty()) {
            Util.showMessage("Acesso", "Usuário ou senha não informado.", this);
            todosValidados = false;
        }

        return todosValidados;
    }

    private void consultarUsuario() {
        // TODO Será implementado um método GET no WS, para trazer o usuario, caso esteja correto.
//        UsuarioWS usuarioWS = new UsuarioWS(this);
//        mUsuarioSelecionado = usuarioWS.selecionarPorLogin(usuarioInformado.getLogin().toLowerCase());

        new TaskCarregarDados("http://consulta") {

            ProgressDialog iProgresso;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                iProgresso = ProgressDialog.show(ActivityLogin.this, "Aguarde...", "Verificando usuário informado.");
            }

            @Override
            protected void onPostExecute(String retorno) {
                super.onPostExecute(retorno);

                iProgresso.dismiss();
                if (!retorno.isEmpty()) {
                    mUsuarioSelecionado = new Gson().fromJson(retorno, Usuario.class);
                    if (validarUsuario()) {
                        invocarIntent();
                    }
                }
            }
        }.execute();
    }

    private void invocarIntent() {
        Intent itLogar = new Intent(ActivityLogin.this, ActivityPrincipal.class);
        itLogar.putExtra("UsuarioLogado", mUsuarioSelecionado);
        itLogar.putExtra("TipoUsuario", mTipoUsuario);
        startActivity(itLogar);
    }

    private void getUsuarioInformado() {
        mUsuarioInformado = new Usuario();

        mUsuarioInformado.setEmail(mEdtEmail.getText().toString());
        mUsuarioInformado.setSenhaPrivada(mEdtSenha.getText().toString());
        mUsuarioInformado.setSenhaConvidados(mEdtSenha.getText().toString());
    }

    private boolean validarUsuario() {
        if (mUsuarioSelecionado == null) {
            Util.showMessage("Acesso", "Usuário não encontrado.", this);
            return false;
        } else if (mUsuarioSelecionado.getSenhaPrivada().toString().equals(mUsuarioInformado.getSenhaPrivada().toString())) {
            mTipoUsuario = TipoUsuario.CASAL;
            return true;
        } else if (mUsuarioSelecionado.getSenhaConvidados().toString().equals(mUsuarioInformado.getSenhaConvidados().toString())) {
            mTipoUsuario = TipoUsuario.CONVIDADO;
            return true;
        } else { // Se for Convidado
            Util.showMessage("Acesso", "Senha incorreta.", this);
            return false;
        }
    }
}
