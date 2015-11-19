package br.com.jed.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.jed.adapters.AdapterPresente;
import br.com.jed.enumaretors.Situacao;
import br.com.jed.enumaretors.TipoUsuario;
import br.com.jed.interfaces.FragmentBase;
import br.com.jed.model.bean.Presente;
import br.com.jed.model.bean.Usuario;
import br.com.jed.task.TaskEnviarDados;
import br.com.jed.util.Util;
import br.com.jed.validators.ValidadorUI;
import br.com.jed.voucasar.R;

public class FragmentPresentes extends Fragment implements FragmentBase {

    private TipoUsuario mTipoUsuario;
    private ListView mLvPresente;
    private LinearLayout mLlCadastroPresente;
    private EditText mEdtDescricaoPresente;
    private EditText mEdtValorPresente;
    private List<Presente> mPresentes;
    private AdapterPresente mAdapterPresente;
    private List<Presente> mPresentesParaEnviar;

    public FragmentPresentes() {
        // Required empty public constructor
    }

    public FragmentPresentes(TipoUsuario tipoUsuario) {
        mTipoUsuario = tipoUsuario;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_presentes, container, false);
        mLvPresente = (ListView) view.findViewById(R.id.lvPresente);
        mLlCadastroPresente = (LinearLayout) view.findViewById(R.id.llCadastroPresente);
        mEdtDescricaoPresente = (EditText) view.findViewById(R.id.edtDescricaoPresente);
        mEdtValorPresente = (EditText) view.findViewById(R.id.edtValorPresente);

        mLvPresente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mTipoUsuario.equals(TipoUsuario.CONVIDADO))
                    mAdapterPresente.marcarDesmarcarPresente(position);
            }
        });

        mLvPresente.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getView().getContext(), R.color.colorAccent));
////                    getActivity().getActionBar().(ContextCompat.getColor(getView().getContext(), R.color.cinzaClaro));
//                }

                return false;
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //TODO os codigos abaixos serao retirados
        mPresentes = new ArrayList<>();
        mPresentesParaEnviar = new ArrayList<>();

        mAdapterPresente = new AdapterPresente(getView().getContext(), mPresentes);
    }

    @Override
    public int alterarVisibilidade() {
        if (mLlCadastroPresente.getVisibility() == View.GONE) {
            mLlCadastroPresente.setVisibility(View.VISIBLE);
            mEdtDescricaoPresente.requestFocus();
        } else {
            mLlCadastroPresente.setVisibility(View.GONE);
            Util.esconderTeclado(getActivity());
        }

        return mLlCadastroPresente.getVisibility();
    }

    @Override
    public boolean salvarCadastro() {
        if (validarCampos()) {
            mPresentes.add((Presente) getDadosFromUI());
            alimentarListView();
            limparCampos();
        }
        return true;
    }

    @Override
    public void limparCampos() {
        mEdtDescricaoPresente.setText("");
        mEdtValorPresente.setText("");
        mEdtDescricaoPresente.requestFocus();
    }

    private void alimentarListView() {
        mLvPresente.setAdapter(mAdapterPresente);
    }

    @Override
    public Object getDadosFromUI() {
        Presente presente = new Presente();

        presente.setDescricao(mEdtDescricaoPresente.getText().toString());
        presente.setValor(Float.valueOf(mEdtValorPresente.getText().toString()));
        presente.setSituacao(Situacao.ABERTO);

        mPresentesParaEnviar.add(presente);

        return presente;
    }

    @Override
    public boolean validarCampos() {
        boolean camposValidos;
        String mensagemErro = null, mensagemCampo;
        EditText[] campos = {mEdtDescricaoPresente, mEdtValorPresente};

        mensagemCampo = getResources().getText(R.string.msg_campo_obrigatorio).toString();
        if (!(camposValidos = ValidadorUI.validarCamposNulos(campos, mensagemCampo))) {
            mensagemErro = getResources().getText(R.string.msg_campo_nulo).toString();
        }

        if (!camposValidos)
            Util.showSnackMessage(getActivity(), getView(), mensagemErro);

        return camposValidos;
    }

    public void enviarPresentes() {
        if (mPresentesParaEnviar.size() > 0) {
            TaskEnviarDados tskEnviarDados = new TaskEnviarDados(
                    getActivity(), mPresentesParaEnviar, "http://endereco");
            tskEnviarDados.execute();
        }
    }

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FragmentPresentes.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FragmentPresentes newInstance(String param1, String param2) {
//        FragmentPresentes fragment = new FragmentPresentes();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public FragmentPresentes() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_presentes, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
