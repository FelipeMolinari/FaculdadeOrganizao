package com.example.faculdadeorganizao.ui;

import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.faculdadeorganizao.DAO.ListaDisciplinaDAO;
import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.model.Disciplina;


public class Activity_CadastroDisciplina extends AppCompatActivity {

    private Toolbar toolbar;
    public ListaDisciplinaDAO DAO;
    private TextInputLayout nomeDisciplina;
    private TextInputLayout salaDisciplina;
    private TextInputLayout nomeProfessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra_disciplina);
        DAO = ListaDisciplinaDAO.getInstance();
        toolbar = findViewById(R.id.toobar_form);

        configuraToolbar();
        buscaElementonaTela();


    }

    private void configuraToolbar() {
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_action_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Disciplina não foi criada",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.confirmar_disciplina:
                confirmaCriarDisciplina();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void confirmaCriarDisciplina() {


        Disciplina novaDisciplina = preencheDadosDisciplina();
        if(novaDisciplina.getNome_disciplina().isEmpty()){
            nomeDisciplina.getEditText().setError("Campo Obrigatório");
        } if(novaDisciplina.getNome_sala().isEmpty()){
            salaDisciplina.getEditText().setError("Campo Obrigatório");
        }else{
            DAO.adcDisciplina(novaDisciplina);
            finish();

        }



    }

    private Disciplina preencheDadosDisciplina() {
        final String nomeDis = nomeDisciplina.getEditText().getText().toString();
        final String salaDis = salaDisciplina.getEditText().getText().toString();
        final String nomeProf = nomeProfessor.getEditText().getText().toString();

        Disciplina novaDisciplina = new Disciplina(nomeDis,salaDis,nomeProf, "Indefinido");
        return novaDisciplina;
    }


    private void buscaElementonaTela() {
        nomeDisciplina = findViewById(R.id.nome_disciplina_edittext);
        salaDisciplina = findViewById(R.id.sala_disciplina_edittext);
        nomeProfessor = findViewById(R.id.professor_disciplina_edittext);
    }


}
