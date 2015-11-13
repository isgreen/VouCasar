package br.com.jed.voucasar;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.jed.enumaretors.TipoUsuario;
import br.com.jed.model.bean.Usuario;
import br.com.jed.util.Util;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityLogin extends AppCompatActivity {

    private Usuario mUsuarioSelecionado;
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

        //Vai chamar um WebClient, passando o Usario por padrao
        //Se for valido, a linha abaixo será executada

        Button btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (validarUsuario()) {
                    Intent itLogar = new Intent(ActivityLogin.this, ActivityPrincipal.class);
//                    itLogar.putExtra("UsuarioLogado", mUsuarioSelecionado);
//                    itLogar.putExtra("TipoUsuario", mTipoUsuario);
                itLogar.putExtra("UsuarioLogado", new Usuario());
                itLogar.putExtra("TipoUsuario", TipoUsuario.CASAL);
                    startActivity(itLogar);
//                }

//                startActivity(new Intent(ActivityLogin.this, ActivityPrincipal.class));
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

    private boolean validarUsuario() {
        /**
         * O método getUsuarioInformado(), retorna um BEAN de usuario, com os dados digitados na tela.
         */
        Usuario usuarioInformado = getUsuarioInformado();

        /**
         * Verifica se existe algum campo vazio.
         */
        if (usuarioInformado.getEmail().isEmpty() || usuarioInformado.getSenhaPrivada().isEmpty()) {
            Util.showMessage("Acesso", "Usuário ou senha não informado.", this);
            return false;
        }


        // TODO Será implementado um método GET no WS, para trazer o usuario, caso esteja correto.
//        UsuarioWS usuarioWS = new UsuarioWS(this);
//        mUsuarioSelecionado = usuarioWS.selecionarPorLogin(usuarioInformado.getLogin().toLowerCase());

        if (mUsuarioSelecionado == null) {
            Util.showMessage("Acesso", "Usuário não encontrado.", this);
            return false;
        } else if (!mUsuarioSelecionado.getSenhaPrivada().equals(usuarioInformado.getSenhaPrivada().toString()) ||
                !mUsuarioSelecionado.getSenhaConvidados().equals(usuarioInformado.getSenhaConvidados().toString())) {
            Util.showMessage("Acesso", "Senha incorreta.", this);
            return false;
            //Se for Usuario Casal
        } else if (mUsuarioSelecionado.getSenhaPrivada().equals(usuarioInformado.getSenhaPrivada().toString())) {
            mTipoUsuario = TipoUsuario.CASAL;
            return true;
        } else { // Se for Convidado
            mTipoUsuario = TipoUsuario.CONVIDADO;
            return true;
        }
    }

    private Usuario getUsuarioInformado() {
        Usuario usuario = new Usuario();

        usuario.setEmail(mEdtEmail.getText().toString());
        usuario.setSenhaPrivada(mEdtEmail.getText().toString());
        usuario.setSenhaConvidados(mEdtSenha.getText().toString());

        return usuario;
    }
}
