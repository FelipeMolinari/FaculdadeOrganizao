package com.example.faculdadeorganizao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.FragmentsTabAdapter;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.fragments.FragmentDisciplinaAtividades;
import com.example.faculdadeorganizao.fragments.FragmentDisciplinaHome;
import com.example.faculdadeorganizao.fragments.FragmentDisciplinaProvas;
import com.example.faculdadeorganizao.model.Disciplina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class Activity_DisciplinaEscolhida extends AppCompatActivity {

    private FragmentsTabAdapter Tabadapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private FloatingActionButton myFabAdcAtividade;
    private Disciplina disciplinaEscolhida;
    private DataBasePrincipal database;
    private RoomDisciplinaDAO roomDisciplinaDAO;
    private long idDisciplinaEscolhida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__disciplina_escolhida);


        encontraElementosNaTela();
        recuperaIdDisciplinaEscolhida();//Recupera o id da disciplina escolhida
        conectaBd();//Recupera disciplina do banco de dados a partid do id da disciplina1
        configToolbar();
        onClickFAB();

        setupViewPage();
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
        navigationOnClickListener();
        setTitle(disciplinaEscolhida.getNome_disciplina());
        tabLayout.setupWithViewPager(viewPager);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void conectaBd() {
        database = DataBasePrincipal.getInstance(getApplicationContext());
        roomDisciplinaDAO = database.getRoomDisciplinaDAO();
        disciplinaEscolhida = roomDisciplinaDAO.getDisciplina(idDisciplinaEscolhida);

    }

    private void recuperaIdDisciplinaEscolhida() {
        Bundle bundle = getIntent().getExtras();
        if(bundle.getSerializable("Disciplina")!= null){
            idDisciplinaEscolhida =  bundle.getLong("Disciplina");
        }
    }

    private void navigationOnClickListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void onClickFAB() {
        myFabAdcAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_CadastroAtividade.class);
                intent.putExtra("Disciplina", disciplinaEscolhida);
                startActivity(intent);
            }
        });
    }

    private void encontraElementosNaTela() {
        myFabAdcAtividade = findViewById(R.id.adicionar_nova_tarefa);
        toolbar = findViewById(R.id.toolbar);
        viewPager =  findViewById(R.id.viewPagerDisciplina);

        tabLayout =  findViewById(R.id.tabLayoutDisciplina);
    }
    @Override
    protected void onResume() {
        super.onResume();
        disciplinaEscolhida = roomDisciplinaDAO.getDisciplina(idDisciplinaEscolhida);

        //COMO DAR REFLEASH NO FRAGMENT....


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

        String fragmentTag = makeFragmentName(viewPager.getId(), 3);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);

        Tabadapter = new FragmentsTabAdapter(getSupportFragmentManager());
        Tabadapter.addFragment(FragmentDisciplinaHome.newInstance(disciplinaEscolhida), "Home");
        Tabadapter.addFragment(new FragmentDisciplinaProvas(), "Provas");
        Tabadapter.addFragment(FragmentDisciplinaAtividades.newInstance(disciplinaEscolhida, fragmentTag),"Atividades");
        viewPager.setAdapter(Tabadapter);

    }
    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void onUpdate() {
        Tabadapter.getItem(2).onResume();
    }
}
