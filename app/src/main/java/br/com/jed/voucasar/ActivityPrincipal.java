package br.com.jed.voucasar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import br.com.jed.fragments.FragmentConvidados;
import br.com.jed.fragments.FragmentPresentes;

public class ActivityPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment mFragmentSelecionado;
    private LinearLayout mLlCadastroPresente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Aqui provavelmente será inicializado um fragment, com a lista de presentes, pois ela deve
        // aparecer tanto para o casal, como para o convidado.

        instanciarFragment(new FragmentPresentes());

        //E no onResume(), deve TALVEZ seja iniciado o Fragment que o usuario selecionou no navigation drawer.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
//        menu.getItem(R.id.men_salvarCadastro);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.men_abreCadastro:
                if (mFragmentSelecionado.getClass() == FragmentPresentes.class) {
                    if (((FragmentPresentes)mFragmentSelecionado).alterarVisibilidade() == View.VISIBLE)
                        item.setIcon(R.drawable.ic_fechar);
                    else
                        item.setIcon(R.drawable.ic_adicionar);
                } else {
                    if (((FragmentConvidados)mFragmentSelecionado).alterarVisibilidade() == View.VISIBLE)
                        item.setIcon(R.drawable.ic_fechar);
                    else
                        item.setIcon(R.drawable.ic_adicionar);
                }
                break;
            case R.id.men_salvarCadastro:
                if (mFragmentSelecionado.getClass() == FragmentPresentes.class) {
                    if (((FragmentPresentes)mFragmentSelecionado).salvarCadastro()){
                        //TODO implementar
                    }
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
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