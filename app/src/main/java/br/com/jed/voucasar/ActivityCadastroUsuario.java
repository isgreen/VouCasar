package br.com.jed.voucasar;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import br.com.jed.model.bean.Usuario;
import br.com.jed.util.Util;
import br.com.jed.validators.ValidadorUI;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityCadastroUsuario extends AppCompatActivity {

    @Bind(R.id.tbUsuario)
    Toolbar mTbUsuario;
    @Bind(R.id.edtEmailUsuario)
    EditText mEdtEmailUsuario;
    @Bind(R.id.edtSenhaPrivada)
    EditText mEdtSenhaPrivada;
    @Bind(R.id.edtSenhaConvidados)
    EditText mEdtSenhaConvidados;
    private Usuario mUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        ButterKnife.bind(this);

        mUsuarioLogado = (Usuario) getIntent().getSerializableExtra("usuarioLogado");

        mTbUsuario.setNavigationIcon(R.drawable.ic_confirmar);
        if (mUsuarioLogado == null)
            mTbUsuario.setTitle(R.string.titulo_criando_usuario);
        else
            mTbUsuario.setTitle(R.string.titulo_alterando_usuario);
//        mTbUsuario.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        setSupportActionBar(mTbUsuario);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Se não houver crítica nos campos, o Usuário será salvo e tela será fechada.
                if (validarCampos(mTbUsuario.getRootView())) {
                    //Método que vai mandar um POST(Update) pro WS para salvar o usuário
                    // Mas antes de salvar deve ser verificado se o Casamento já foi confirmado, caso esteja, não pode ser alterado.
                    if (salvarUsuario())
                        finish();
                }
                break;
        }

        return true;
    }

    private boolean salvarUsuario() {
        boolean salvoComSucesso;
        Usuario usuario = getUsuarioFromUI();

        if (mUsuarioLogado == null)
            salvoComSucesso = inserirUsuario(usuario);
        else
            salvoComSucesso = alterarUsuario(usuario);

        return salvoComSucesso;
    }

    private boolean alterarUsuario(Usuario usuario) {
        usuario.setId(mUsuarioLogado.getId());

        try {

            String url = "http://www.caelum.com.br/mobile";

//            WebClient client = new WebClient(url);
//
//            final String respostaJSON = client.post(dadosJSON);

            Gson gsonUsuario = new Gson();
            gsonUsuario.toJson(usuario);


        } catch (Exception e) {

        } finally {

        }

        return true;
    }

    private boolean inserirUsuario(Usuario usuario) {
        try {

        } catch (Exception e) {

        } finally {

        }

        return true;
    }

    private Usuario getUsuarioFromUI() {

        Usuario usuario = new Usuario();

        usuario.setEmail(mEdtEmailUsuario.getText().toString());
        usuario.setSenhaPrivada(mEdtSenhaPrivada.getText().toString());
        usuario.setSenhaConvidados(mEdtSenhaConvidados.getText().toString());

        return usuario;
    }

    private boolean validarCampos(View view) {
        boolean camposValidos;
        String mensagemErro = null, mensagemCampo;
        EditText[] campos = {mEdtEmailUsuario, mEdtSenhaPrivada, mEdtSenhaConvidados};

        mensagemCampo = getResources().getText(R.string.msg_campo_obrigatorio).toString();
        if (!(camposValidos = ValidadorUI.validarCamposNulos(campos, mensagemCampo))) {
            mensagemErro = getResources().getText(R.string.msg_campo_nulo).toString();

        } else if (mEdtSenhaPrivada.getText().toString().equals(mEdtSenhaConvidados.getText().toString())) {
            mensagemErro = getResources().getText(R.string.msg_senha_igual).toString();
            camposValidos = false;
        }

        if (!camposValidos) {
            Util.esconderTeclado(this);
            Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG).show();
        }

        return camposValidos;
    }
}
