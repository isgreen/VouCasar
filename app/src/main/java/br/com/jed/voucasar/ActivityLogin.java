package br.com.jed.voucasar;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Mudando a fonte do titulo da tela de login
        Typeface fonte = Typeface.createFromAsset(getAssets(), "fonts/font_casamento.otf");
        TextView txtTitulo=(TextView) findViewById(R.id.txtTitulo);
        txtTitulo.setTypeface(fonte);

        //Vai chamar um WebClient, passando o Usario por padrao
        //Se for valido, a linha abaixo será executada
//        startActivity(new Intent(this, ActivityPrincipal.class));
    }
}
