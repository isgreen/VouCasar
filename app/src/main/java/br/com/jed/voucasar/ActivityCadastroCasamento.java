package br.com.jed.voucasar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ActivityCadastroCasamento extends AppCompatActivity {

    public static final int RESULT_LOAD_IMAGE = 1;

    private ImageButton btnVoltar;
    private ImageView imgVwFotoCasal;
    private FloatingActionButton fabFotoCasal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_casamento);

        fabFotoCasal = (FloatingActionButton) findViewById(R.id.fabFotoCasal);
        fabFotoCasal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itAbrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(itAbrirGaleria, RESULT_LOAD_IMAGE);
            }
        });

        imgVwFotoCasal = (ImageView) findViewById(R.id.imgVwFotoCasal);
//        imgVwFotoCasal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent itAbrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(itAbrirGaleria, RESULT_LOAD_IMAGE);
//            }
//        });

        btnVoltar = (ImageButton) findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri imagemSelecionada = data.getData();
            String [] diretorioArquivo = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(imagemSelecionada, diretorioArquivo, null, null, null);
            cursor.moveToFirst();

            String diretorioImagem = cursor.getString(cursor.getColumnIndex(diretorioArquivo[0]));
            cursor.close();


            //essas linhas abaixo vão ficar num helper
//            casamento.setFoto(caminhoArquivo);

            Bitmap imagem = BitmapFactory.decodeFile(diretorioImagem);
            Bitmap fotoReduzida = Bitmap.createScaledBitmap(imagem, 1000, 700, true);
            imgVwFotoCasal.setImageBitmap(fotoReduzida);
//            imgVwFotoCasal.setAdjustViewBounds(true);
        }
    }
}
