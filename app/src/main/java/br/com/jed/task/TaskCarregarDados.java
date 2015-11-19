package br.com.jed.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import br.com.jed.model.bean.Usuario;

/**
 * Created by Wolfstein on 17/11/2015.
 */
public class TaskCarregarDados extends AsyncTask<Integer, Double, String> {

    private String mUrl;

    public TaskCarregarDados(String url) {
        mUrl = url;
    }
    @Override
    protected String doInBackground(Integer... params) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //TODO este retorno é temporário
        return new Gson().toJson(criarUsuarioTemporario());
    }

    // TODO Método deve ser retirado daqui.
    private Usuario criarUsuarioTemporario() {
        Usuario usuario = new Usuario();

        usuario.setEmail("jorge@hotmail.com");
        usuario.setSenhaPrivada("casal");
        usuario.setSenhaConvidados("convidado");

        return usuario;
    }
}
