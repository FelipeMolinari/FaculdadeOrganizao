package com.example.faculdadeorganizao.ui;

import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.faculdadeorganizao.DAO.ListaDisciplinaDAO;
import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.OnItemClickListener;
import com.example.faculdadeorganizao.adapters.RecyclerViewListaDisciplinasAdapter;
import com.example.faculdadeorganizao.model.Disciplina;

import java.util.ArrayList;

public class Activity_ListaDisciplinas extends AppCompatActivity {

    private FloatingActionButton fab;
    private Toolbar toolbar_list;
    private RecyclerView recyclerViewList;
    private ArrayList<Disciplina> listDisciplina;
    private RecyclerViewListaDisciplinasAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_disciplinas);

        listDisciplina = ListaDisciplinaDAO.getInstance().retornaLista();


        configElementosDaTela();
        povoaLista();
    }

    private void configElementosDaTela() {
        recyclerViewList = findViewById(R.id.list_disciplina_recycler);
        configRecyclerList();

        toolbar_list = findViewById(R.id.toolbar_list);
        configToolbar();

        fab = findViewById(R.id.adc_disciplina_fab);
        configFAB();
    }

    private void povoaLista() {
    ListaDisciplinaDAO DAO = ListaDisciplinaDAO.getInstance();

        for(int i=0; i<6;i++){
            if(i<=2)
                DAO.adcDisciplina(new Disciplina("Disciplina "+ i,"A20"+i,"Prof "+i,"Acima"));
            else if(i<=4)
                DAO.adcDisciplina(new Disciplina("Disciplina "+ i,"A20"+i,"Prof "+i,"Abaixo"));
            else
                DAO.adcDisciplina(new Disciplina("Disciplina "+ i,"A20"+i,"Prof "+i,"Media"));


        }
    }

    @Override
    protected void onResume() {

        super.onResume();


        listDisciplina = ListaDisciplinaDAO.getInstance().retornaLista();
        adapter.notifyDataSetChanged();

    }

    private void configRecyclerList() {
        listDisciplina = ListaDisciplinaDAO.getInstance().retornaLista();
        configLayout();
        configAdapter();
    }

    private void configLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewList.setLayoutManager(linearLayoutManager);
    }

    private void configAdapter() {

        adapter = new RecyclerViewListaDisciplinasAdapter(listDisciplina,getApplicationContext());
        onClickDisciplina();
        recyclerViewList.setAdapter(adapter);
    }

    private void onClickDisciplina() {
        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickDisciplina(Disciplina disciplina) {
                Toast.makeText(getApplicationContext(), "Disciplina escolhida "
                        + disciplina.getNome_disciplina(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Activity_DisciplinaEscolhida.class);

                intent.putExtra("Disciplina", disciplina);

                startActivity(intent);


            }
        });
    }

    private void configFAB() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_CadastroDisciplina.class);
                startActivity(intent);
            }
        });
    }

    private void configToolbar() {
        setSupportActionBar(toolbar_list);
        toolbar_list.setNavigationIcon(R.drawable.ic_action_menu);
        toolbar_list.setTitle("Nome do Curso");
        toolbar_list.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Botão menu clicado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_lista_disciplina,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mais_opcoes_id:
                abreMaisOpcoes();
                return true;
            case R.id.mudar_tema_id:
                mudaTema();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mudaTema() {
        Toast.makeText(getApplicationContext(),"Botão mudar tema clicado", Toast.LENGTH_SHORT).show();

    }

    private void abreMaisOpcoes() {
        Toast.makeText(getApplicationContext(),"Botão mais opcões clicado", Toast.LENGTH_SHORT).show();
    }

}
