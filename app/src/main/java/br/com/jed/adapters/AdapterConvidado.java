package br.com.jed.adapters;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.jed.model.bean.Convidado;
import br.com.jed.voucasar.R;

/**
 * Created by Wolfstein on 18/11/2015.
 */
public class AdapterConvidado extends BaseAdapter {

    private List<Convidado> mConvidados;
    private Context mContexto;

    public AdapterConvidado(Context contexto, List convidados){
        mContexto = contexto;
        mConvidados = convidados;
    }

    @Override
    public int getCount() {
        return mConvidados.size();
    }

    @Override
    public Object getItem(int position) {
        return mConvidados.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.layout_list_view, null);

        Convidado convidado = mConvidados.get(position);

        ((TextView) convertView.findViewById(R.id.txtPrincipal)).setText(convidado.getNome());
        ((TextView) convertView.findViewById(R.id.txtDetalhe)).setText(convidado.getSobrenome());

        return convertView;

    }
}
