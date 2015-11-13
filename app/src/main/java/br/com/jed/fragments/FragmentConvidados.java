package br.com.jed.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.jed.enumaretors.Situacao;
import br.com.jed.interfaces.FragmentBase;
import br.com.jed.model.bean.Convidado;
import br.com.jed.util.Util;
import br.com.jed.validators.ValidadorUI;
import br.com.jed.voucasar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConvidados extends Fragment implements FragmentBase {

    private ListView mLvConvidado;
    private LinearLayout mLlCadastroConvidado;
    private EditText mEdtNomeConvidado;
    private EditText mEdtSobrenomeConvidado;
    private EditText mEdtEmailConvidado;

    public FragmentConvidados() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convidados, container, false);
        mLvConvidado = (ListView) view.findViewById(R.id.lvConvidado);
        mLlCadastroConvidado = (LinearLayout) view.findViewById(R.id.llCadastroConvidado);
        mEdtNomeConvidado = (EditText) view.findViewById(R.id.edtNomeConvidado);
        mEdtSobrenomeConvidado = (EditText) view.findViewById(R.id.edtSobrenomeConvidado);
        mEdtEmailConvidado = (EditText) view.findViewById(R.id.edtEmailConvidado);

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

    @Override
    public boolean salvarCadastro() {
        Convidado convidado;

        if (validarCampos()) {
            convidado = (Convidado) getDadosFromUI();
            limparCampos();
        }
        return true;
    }

    public Object getDadosFromUI() {
        Convidado convidado = new Convidado();

        convidado.setNome(mEdtNomeConvidado.getText().toString());
        convidado.setSobrenome(mEdtSobrenomeConvidado.getText().toString());
        convidado.setEmail(mEdtEmailConvidado.getText().toString());
        convidado.setSituacao(Situacao.CONFIRMADO);

        return convidado;
    }

    @Override
    public boolean validarCampos() {
        boolean camposValidos;
        String mensagemErro = null, mensagemCampo;
        EditText[] campos = {mEdtNomeConvidado, mEdtSobrenomeConvidado, mEdtEmailConvidado};

        mensagemCampo = getResources().getText(R.string.msg_campo_obrigatorio).toString();
        if (!(camposValidos = ValidadorUI.validarCamposNulos(campos, mensagemCampo))) {
            mensagemErro = getResources().getText(R.string.msg_campo_nulo).toString();
        }

        if (!camposValidos)
            Util.showSnackMessage(getActivity(), getView(), mensagemErro);

        return camposValidos;
    }

    @Override
    public void limparCampos() {
        mEdtNomeConvidado.setText("");
        mEdtSobrenomeConvidado.setText("");
        mEdtEmailConvidado.setText("");
    }
}
