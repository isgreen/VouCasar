package br.com.jed.voucasar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.jed.enumaretors.TipoUsuario;
import br.com.jed.fragments.FragmentConvidados;
import br.com.jed.fragments.FragmentPresentes;
import br.com.jed.model.bean.Usuario;
import br.com.jed.task.TaskEnviaPresente;

public class ActivityPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Usuario mUsuarioSelecionado;
    private TipoUsuario mTipoUsuario;

    private Fragment mFragmentSelecionado;
    private int mLayoutIsVisible = View.GONE;
    private NavigationView mNavigationView;
    private FloatingActionButton mFabEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Aqui provavelmente será inicializado um fragment, com a lista de presentes, pois ela deve
        // aparecer tanto para o casal, como para o convidado.

        mUsuarioSelecionado = (Usuario) getIntent().getSerializableExtra("UsuarioLogado");
        mTipoUsuario = (TipoUsuario) getIntent().getSerializableExtra("TipoUsuario");

        instanciarFragment(new FragmentPresentes());

        //E no onResume(), deve TALVEZ seja iniciado o Fragment que o usuario selecionou no navigation drawer.

        mFabEnviar = (FloatingActionButton) findViewById(R.id.fabEnviar);

        mFabEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentPresentes) mFragmentSelecionado).enviarPresentes();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (mTipoUsuario == TipoUsuario.CONVIDADO)
            esconderItensNavigation();
    }

    private void esconderItensNavigation() {
        mNavigationView.getMenu().findItem(R.id.nav_grpControleCasal).setVisible(false);
    }

    private void instanciarFragment(Fragment fragment) {
        mFragmentSelecionado = fragment;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.rlPrincipal, mFragmentSelecionado);
        transaction.setCustomAnimations(android.R.anim.accelerate_decelerate_interpolator, android.R.anim.slide_out_right);
//        transaction.add(R.id.frgPrincipal, fragmentPresentes);

        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_principal, menu);

        if (mTipoUsuario == TipoUsuario.CASAL)
            menu.findItem(R.id.men_abreCadastro).setVisible(true);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mLayoutIsVisible == View.VISIBLE) {
            menu.findItem(R.id.men_salvarCadastro).setVisible(true);
//            if (mTipoUsuario == TipoUsuario.CASAL)
                menu.findItem(R.id.men_abreCadastro).setIcon(R.drawable.ic_recolher);
        } else {
            menu.findItem(R.id.men_salvarCadastro).setVisible(false);
            menu.findItem(R.id.men_abreCadastro).setIcon(R.drawable.ic_expandir);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.men_salvarCadastro:
                if (mFragmentSelecionado.getClass() == FragmentPresentes.class) {
                    if (((FragmentPresentes) mFragmentSelecionado).salvarCadastro()) {
                        //TODO implementar
                    }
                } else if (((FragmentConvidados) mFragmentSelecionado).salvarCadastro()) {
                    //TODO implementar
                }
                break;
            case R.id.men_abreCadastro:
                if (mFragmentSelecionado.getClass() == FragmentPresentes.class)
                    mLayoutIsVisible = ((FragmentPresentes) mFragmentSelecionado).alterarVisibilidade();
                else
                    mLayoutIsVisible = ((FragmentConvidados) mFragmentSelecionado).alterarVisibilidade();
                break;
        }

        invalidateOptionsMenu();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_presente:
                instanciarFragment(new FragmentPresentes());
                break;
            case R.id.nav_convidados:
                instanciarFragment(new FragmentConvidados());
                break;
            case R.id.nav_casamento:
                startActivity(new Intent(this, ActivityCadastroCasamento.class));
                break;
            case R.id.nav_usuario:
                startActivity(new Intent(this, ActivityCadastroUsuario.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}