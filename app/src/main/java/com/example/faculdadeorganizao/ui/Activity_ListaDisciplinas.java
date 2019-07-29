package com.example.faculdadeorganizao.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.OnItemClickListener;
import com.example.faculdadeorganizao.adapters.RecyclerViewListaDisciplinasAdapter;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.model.Disciplina;
import com.example.faculdadeorganizao.ui.helper.callback.DisciplinaItemTouchHelperCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Activity_ListaDisciplinas extends AppCompatActivity {

    private FloatingActionButton fab;
    private Toolbar toolbar_list;
    private RecyclerView recyclerViewList;
    private List<Disciplina> listDisciplina;
    private RecyclerViewListaDisciplinasAdapter adapter;
    private RoomDisciplinaDAO roomDisciplinaDAO;
    private DataBasePrincipal database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_disciplinas);
        database = DataBasePrincipal.getInstance(this);
        roomDisciplinaDAO = database.getRoomDisciplinaDAO();
        configNavig();
        listDisciplina = roomDisciplinaDAO.retornaListaDisciplina();


        configElementosDaTela();
    }

    private void configNavig() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark)); //status bar or the time bar at the top
        }
    }

    private void configElementosDaTela() {
        recyclerViewList = findViewById(R.id.list_disciplina_recycler);
        configRecyclerList();

        toolbar_list = findViewById(R.id.toolbar_list);
        configToolbar();

        fab = findViewById(R.id.adc_disciplina_fab);
        configFAB();
    }


    @Override
    protected void onResume() {

        super.onResume();

        refreshData();

    }

    private void refreshData() {


        listDisciplina.clear();
        roomDisciplinaDAO = database.getRoomDisciplinaDAO();
        listDisciplina.addAll(roomDisciplinaDAO.retornaListaDisciplina());
        adapter.notifyDataSetChanged();
    }

    private void configRecyclerList() {

        configLayout();
        configAdapter();
        configArrastarPraApagar();


    }

    private void configArrastarPraApagar() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new DisciplinaItemTouchHelperCallback(adapter, roomDisciplinaDAO, getApplicationContext()));
        itemTouchHelper.attachToRecyclerView(recyclerViewList);
    }

    private void configLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewList.setLayoutManager(linearLayoutManager);

    }

    private void configAdapter() {

        adapter = new RecyclerViewListaDisciplinasAdapter(listDisciplina, getApplicationContext());
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
                intent.putExtra("Disciplina", disciplina.getId_disciplina());
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_disciplina, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mais_opcoes_id:
                abreMaisOpcoes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mudaTema() {
        Toast.makeText(getApplicationContext(), "Botão mudar tema clicado", Toast.LENGTH_SHORT).show();

    }

    private void abreMaisOpcoes() {
        Toast.makeText(getApplicationContext(), "Botão mais opcões clicado", Toast.LENGTH_SHORT).show();
    }

}
