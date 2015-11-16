package br.com.jed.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.List;

import br.com.jed.util.Util;

/**
 * Created by Wolfstein on 15/11/2015.
 */
public class TaskEnviaPresente extends AsyncTask<Integer, Double, String> {

    private Context mContexto;
    private List mPresentes;
    private ProgressDialog mProgresso;
    private String mUrl;

    public TaskEnviaPresente(Context contexto, List presentes, String url) {
        mContexto = contexto;
        mPresentes = presentes;
        mUrl = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgresso = ProgressDialog.show(mContexto, "Enviando...",
                "Aguarde enquanto a lista dos presentes adicionados é enviada.", true, false);
    }

    @Override
    protected String doInBackground(Integer... params) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        String json = gson.toJson(mPresentes);

        //TODO Aqui terá o metodo que Post que vai mandar o "json" por parâmetro

        //TODO A resposta do Post deve ser enviada no Return

        return "Enviado com sucesso";
    }

    @Override
    protected void onPostExecute(String resposta) {
        super.onPostExecute(resposta);

        mProgresso.dismiss();
        Util.showMessage("Retorno", resposta, mContexto);
    }
}
