package com.example.faculdadeorganizao.ui;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.FragmentsTabAdapter;
import com.example.faculdadeorganizao.fragments.FragmentDisciplinaAtividades;
import com.example.faculdadeorganizao.fragments.FragmentDisciplinaHome;
import com.example.faculdadeorganizao.fragments.FragmentDisciplinaProvas;

public class Activity_DisciplinaEscolhida extends AppCompatActivity {

    private FragmentsTabAdapter Tabadapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__disciplina_escolhida);


        toolbar = findViewById(R.id.toolbar);
        viewPager =  findViewById(R.id.viewPagerDisciplina);

        tabLayout =  findViewById(R.id.tabLayoutDisciplina);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }




        setupViewPage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_disciplina_escolhida,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.tirar_foto_disciplina:
                abrirCameraDispositivo();
                return true;
            case R.id.mais_opcoes_id:
                abrirMaisOpcoes();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirMaisOpcoes() {
        Toast.makeText(getApplicationContext(),
                "Mais opcoes clicada",Toast.LENGTH_SHORT).show();
    }

    private void abrirCameraDispositivo() {

        Toast.makeText(getApplicationContext(),
                "Camera clicada", Toast.LENGTH_SHORT).show();
    }


    private void setupViewPage() {

        Tabadapter = new FragmentsTabAdapter(getSupportFragmentManager());
        Tabadapter.addFragment(new FragmentDisciplinaHome(), "Home");
        Tabadapter.addFragment(new FragmentDisciplinaProvas(), "Provas");
        Tabadapter.addFragment(new FragmentDisciplinaAtividades(),"Atividades");
        viewPager.setAdapter(Tabadapter);
    }
}
