package com.example.faculdadeorganizao.ui;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    private FloatingActionButton myFabAdcAtividade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__disciplina_escolhida);

        myFabAdcAtividade = findViewById(R.id.adicionar_nova_tarefa);



        toolbar = findViewById(R.id.toolbar);
        viewPager =  findViewById(R.id.viewPagerDisciplina);

        tabLayout =  findViewById(R.id.tabLayoutDisciplina);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        myFabAdcAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_CadastroAtividade.class);
                startActivity(intent);
            }
        });



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
