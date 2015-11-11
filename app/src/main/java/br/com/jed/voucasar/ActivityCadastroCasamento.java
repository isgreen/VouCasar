package br.com.jed.voucasar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import br.com.jed.enumaretors.Situacao;
import br.com.jed.model.bean.Casamento;
import br.com.jed.model.bean.Usuario;
import br.com.jed.util.Util;
import br.com.jed.validators.ValidadorUI;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ActivityCadastroCasamento extends AppCompatActivity {

    public static final int RESULT_LOAD_IMAGE = 1;

    @Bind(R.id.tbCasamento)
    Toolbar mTbCasamento;

    @Bind(R.id.edtNomeNoivo)
    EditText mEdtNomeNoivo;
    @Bind(R.id.edtNomeNoiva)
    EditText mEdtNomeNoiva;
    @Bind(R.id.edtDataCerimonia)
    EditText mEdtDataCerimonia;
    @Bind(R.id.edtEnderecoCerimonia)
    EditText mEdtEnderecoCerimonia;
    @Bind(R.id.imgVwFotoCasal)
    ImageView mImgViewFotoCasal;

    private FloatingActionButton fabFotoCasal;

    private Casamento mCasamento;
    private Usuario mUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_casamento);
        ButterKnife.bind(this);

        mCasamento = (Casamento) getIntent().getSerializableExtra("casamentoMarcado");
        mUsuarioLogado = (Usuario) getIntent().getSerializableExtra("usuarioLogado");

        mTbCasamento.setNavigationIcon(R.drawable.ic_confirmar);
        mTbCasamento.setTitle(R.string.titulo_criando_casamento);
        this.setSupportActionBar(mTbCasamento);

        fabFotoCasal = (FloatingActionButton) findViewById(R.id.fabFotoCasal);
        fabFotoCasal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAbrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(itAbrirGaleria, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (validarCampos(mTbCasamento.getRootView()))
                    if (salvarCasamento())
                        finish();
                break;
        }

        return true;
    }

    private boolean salvarCasamento() {
        boolean salvoComSucesso;
        getCasamentoFromUI();

        if (mCasamento.getId() == 0)
            salvoComSucesso = inserirCasamento();
        else
            salvoComSucesso = alterarCasamento();

        return salvoComSucesso;
    }

    private boolean alterarCasamento() {
        return false;
    }

    private boolean inserirCasamento() {
        return false;
    }

    private void getCasamentoFromUI() {
        if (mCasamento == null)
            mCasamento = new Casamento();

        mCasamento.setNomeNoivo(mEdtNomeNoivo.getText().toString());
        mCasamento.setNomeNoiva(mEdtNomeNoiva.getText().toString());
        mCasamento.setDataCerimonia(Util.convertStrToDate(mEdtDataCerimonia.getText().toString()));
        mCasamento.setEnderecoCerimonia(mEdtEnderecoCerimonia.getText().toString());
        mCasamento.setFotoCasal(getFotoFromUI(((BitmapDrawable)mImgViewFotoCasal.getDrawable()).getBitmap()));
        mCasamento.setUsuario(mUsuarioLogado);
        mCasamento.setSituacao(Situacao.ABERTO);
    }

    private byte[] getFotoFromUI(Bitmap foto) {
        ByteArrayOutputStream fotoEmByte = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 0, fotoEmByte);
        return fotoEmByte.toByteArray();
    }

    private boolean validarCampos(View view) {
        boolean camposValidos;
        String mensagemErro = null, mensagemCampo;
        EditText[] campos = {mEdtNomeNoivo, mEdtNomeNoiva, mEdtDataCerimonia, mEdtEnderecoCerimonia};

        mensagemCampo = getResources().getText(R.string.msg_campo_obrigatorio).toString();
        if (!(camposValidos = ValidadorUI.validarCamposNulos(campos, mensagemCampo))) {
            mensagemErro = getResources().getText(R.string.msg_campo_nulo).toString();
        }

        if (!camposValidos) {
            Util.esconderTeclado(this);
            Snackbar.make(view, mensagemErro, Snackbar.LENGTH_LONG).show();
        }

        return camposValidos;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri imagemSelecionada = data.getData();
            String[] diretorioArquivo = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imagemSelecionada, diretorioArquivo, null, null, null);
            cursor.moveToFirst();

            String diretorioImagem = cursor.getString(cursor.getColumnIndex(diretorioArquivo[0]));
            cursor.close();


            //essas linhas abaixo vão ficar num helper
//            casamento.setFoto(caminhoArquivo);

            Bitmap imagem = BitmapFactory.decodeFile(diretorioImagem);
            Bitmap fotoReduzida = Bitmap.createScaledBitmap(imagem, 1000, 700, true);
            mImgViewFotoCasal.setImageBitmap(fotoReduzida);
//            imgVwFotoCasal.setAdjustViewBounds(true);
        }
    }
}
