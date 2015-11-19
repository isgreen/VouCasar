package br.com.jed.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.jed.adapters.AdapterConvidado;
import br.com.jed.enumaretors.Situacao;
import br.com.jed.interfaces.FragmentBase;
import br.com.jed.model.bean.Convidado;
import br.com.jed.task.TaskEnviarDados;
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
    private List<Convidado> mConvidados;
    private List<Convidado> mConvidadosParaEnviar;
    private AdapterConvidado mAdapterConvidado;

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
        mConvidados = new ArrayList<>();
        mConvidadosParaEnviar = new ArrayList<>();

        mAdapterConvidado = new AdapterConvidado(getView().getContext(), mConvidados);
    }

    public int alterarVisibilidade() {
        if (mLlCadastroConvidado.getVisibility() == View.GONE) {
            mLlCadastroConvidado.setVisibility(View.VISIBLE);
        } else
            mLlCadastroConvidado.setVisibility(View.GONE);

        return mLlCadastroConvidado.getVisibility();
    }

    @Override
    public boolean salvarCadastro() {
        if (validarCampos()) {
            mConvidados.add((Convidado) getDadosFromUI());
            alimentarListView();
            limparCampos();
        }
        return true;
    }

    private void alimentarListView() {
        mLvConvidado.setAdapter(mAdapterConvidado);
    }

    public Object getDadosFromUI() {
        Convidado convidado = new Convidado();

        convidado.setNome(mEdtNomeConvidado.getText().toString());
        convidado.setSobrenome(mEdtSobrenomeConvidado.getText().toString());
        convidado.setEmail(mEdtEmailConvidado.getText().toString());
        convidado.setSituacao(Situacao.CONFIRMADO);

        mConvidadosParaEnviar.add(convidado);

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

    public void enviarConvidados() {
        if (mConvidadosParaEnviar.size() > 0) {
            TaskEnviarDados tskEnviarDados = new TaskEnviarDados(
                    getActivity(), mConvidadosParaEnviar, "http://endereco");
            tskEnviarDados.execute();
        }
    }
}
