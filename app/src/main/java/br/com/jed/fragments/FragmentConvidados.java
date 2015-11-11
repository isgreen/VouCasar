package br.com.jed.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.jed.voucasar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConvidados extends Fragment {

    private ListView mLvConvidado;
    private LinearLayout mLlCadastroConvidado;

    public FragmentConvidados() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convidados, container, false);
        mLvConvidado = (ListView) view.findViewById(R.id.lvConvidado);
        mLlCadastroConvidado = (LinearLayout) view.findViewById(R.id.llCadastroConvidado);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        //TODO os codigos abaixos serao retirados
        List<String> testeLista = new ArrayList<>();
        testeLista.add("Danilo Carvalho");
        testeLista.add("Éverdes Soares");
        testeLista.add("Renan Frutuozo");
        testeLista.add("Jair Atirador");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getView().getContext(), android.R.layout.simple_list_item_1, testeLista);

        mLvConvidado.setAdapter(arrayAdapter);
    }

    public int alterarVisibilidade() {
        if (mLlCadastroConvidado.getVisibility() == View.GONE) {
            mLlCadastroConvidado.setVisibility(View.VISIBLE);
        }
        else
            mLlCadastroConvidado.setVisibility(View.GONE);

        return mLlCadastroConvidado.getVisibility();
    }
}
