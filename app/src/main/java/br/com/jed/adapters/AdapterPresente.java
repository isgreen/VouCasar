package br.com.jed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.jed.enumaretors.Situacao;
import br.com.jed.model.bean.Presente;
import br.com.jed.voucasar.R;

/**
 * Created by Wolfstein on 13/11/2015.
 */
public class AdapterPresente extends BaseAdapter {

    private Context mContexto;
    private List<Presente> mPresentes;

    public AdapterPresente(Context contexto, List presentes) {
        mContexto = contexto;
        mPresentes = presentes;
    }

    @Override
    public int getCount() {
        return mPresentes.size();
    }

    @Override
    public Object getItem(int position) {
        return mPresentes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void marcarDesmarcarPresente(int position) {
        if (mPresentes.get(position).getSituacao() == Situacao.ABERTO)
            mPresentes.get(position).setSituacao(Situacao.CONFIRMADO);
        else
            mPresentes.get(position).setSituacao(Situacao.ABERTO);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) mContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.layout_list_view, null);

        Presente presente = mPresentes.get(position);

        ((TextView) convertView.findViewById(R.id.txtPrincipal)).setText(presente.getDescricao().toString());
        ((TextView) convertView.findViewById(R.id.txtDetalhe)).setText("R$ " + String.valueOf(presente.getValor()));

        if (presente.getSituacao() == Situacao.CONFIRMADO)
            (convertView.findViewById(R.id.txtItemMarcado)).setVisibility(View.VISIBLE);
        else if (presente.getSituacao() == Situacao.ABERTO)
            (convertView.findViewById(R.id.txtItemMarcado)).setVisibility(View.INVISIBLE);

//        }
        return convertView;
    }
}
