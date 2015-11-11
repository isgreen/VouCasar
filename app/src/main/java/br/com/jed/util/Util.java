package br.com.jed.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.jed.voucasar.R;

/**
 * Created by Wolfstein on 07/11/2015.
 */
public class Util {

    public static void esconderTeclado(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static Date convertStrToDate(String data) {
        Date date = null;

        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String convertDateToStrInvertido(Date data){
        return new SimpleDateFormat("yyyy-MM-dd").format(data.getTime());
    }

    public static void showMessage(String titulo, String mensagem, Context contexto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setMessage(mensagem);
        builder.setIcon(R.drawable.ic_exclamacao);
        builder.setNegativeButton("Ok", null);

        AlertDialog dialog = builder.create();
        dialog.setTitle(titulo);
        dialog.show();
    }
}
