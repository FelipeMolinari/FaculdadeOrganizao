package com.example.faculdadeorganizao.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.model.Disciplina;
import com.google.android.material.textfield.TextInputLayout;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;


public class Activity_CadastroDisciplina extends AppCompatActivity {

    private Toolbar toolbar;
    public RoomDisciplinaDAO roomDisciplinaDAO;
    private TextInputLayout nomeDisciplina;
    private TextInputLayout salaDisciplina;
    private TextInputLayout nomeProfessor;
    private TextInputLayout sobreDisciplina;
    private boolean activityEdit;
    private Disciplina disciplinaEscolhida;
    private Switch aSwitchJaCompletou;
    private View box_notaObtica;
    private IndicatorSeekBar indicatorSeekBar;
    private TextView teste;
    private LinearLayout principal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastra_disciplina);
        DataBasePrincipal dataBasePrincipal = DataBasePrincipal.getInstance(this);

        roomDisciplinaDAO = dataBasePrincipal.getRoomDisciplinaDAO();
        toolbar = findViewById(R.id.toobar_form);
        buscaElementonaTela();
        verificaSeEditar();
        configuraToolbar();
        seEditaPreencherCampos();
        configuraElementosTela();

    }

    private void configuraElementosTela() {

        configSwitch();
        configSeekBar();

    }

    private void configSeekBar() {

        principal.removeView(teste);
        indicatorSeekBar.getIndicator().setTopContentView(teste);
        indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

                float progress = seekParams.progressFloat;
                if (progress < 50) {
                    configNota(progress, R.color.color4, R.drawable.ic_action_abaixomedia);
                } else if (60 > progress && progress >= 50) {
                    configNota(progress, R.color.color3, R.drawable.ic_action_namedia);

                } else if (progress == 60) {
                    configNota(progress, R.color.color3, R.drawable.ic_action_namedia);

                } else if (progress > 60) {
                    configNota(progress, R.color.color7, R.drawable.ic_action_acimadamedia);
                }

            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });

    }

    private void configNota(float seekParams, int p, int p2) {
        indicatorSeekBar.thumbColor(getResources().getColor(p));
        teste.setTextColor(getResources().getColor(p));
        teste.setText(seekParams + "%");
        teste.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, p2);
    }

    private void configSwitch() {
        aSwitchJaCompletou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    box_notaObtica.setVisibility(View.VISIBLE);

                } else {
                    box_notaObtica.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    private void verificaSeEditar() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getSerializable("Disciplina") != null) {
                disciplinaEscolhida = (Disciplina) bundle.getSerializable("Disciplina");
                activityEdit = true;
            } else {
                activityEdit = false;
            }
        } else
            activityEdit = false;

    }

    private void seEditaPreencherCampos() {

        if (activityEdit) {
            nomeDisciplina.getEditText().setText(disciplinaEscolhida.getNome_disciplina());
            salaDisciplina.getEditText().setText(disciplinaEscolhida.getNome_sala());
            nomeProfessor.getEditText().setText(disciplinaEscolhida.getNome_professor());
            sobreDisciplina.getEditText().setText(disciplinaEscolhida.getSobre_disciplina());
            if (disciplinaEscolhida.isCompleta()) {
                aSwitchJaCompletou.setChecked(disciplinaEscolhida.isCompleta());
                box_notaObtica.setVisibility(View.VISIBLE);
                indicatorSeekBar.setProgress(disciplinaEscolhida.getNota_obtida());
                teste.setText(disciplinaEscolhida.getNota_obtida()+"%");
            }

        }
    }


    private void configuraToolbar() {
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_action_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Disciplina não foi criada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu);
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

        Disciplina disciplina = preencheDadosDisciplina();
        if (disciplina.getNome_disciplina().isEmpty()) {
            nomeDisciplina.getEditText().setError("Campo Obrigatório");
        } else if (aSwitchJaCompletou.isChecked()) {
            final float notaDaDisciplina = indicatorSeekBar.getProgressFloat();
            disciplina.setNota_obtida(notaDaDisciplina);
            disciplina.setNota_distribuida(100);
            disciplina.setCompleta(true);
                if (activityEdit) {
                    roomDisciplinaDAO.updateDisciplina(disciplina);
                    Toast.makeText(getApplicationContext(),
                            "Disciplina alterada", Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    roomDisciplinaDAO.adcDisciplina(disciplina);
                    finish();
                }

        } else {
            disciplina.setNota_obtida(0);
            disciplina.setNota_distribuida(0);
            disciplina.setCompleta(false);
            if (activityEdit) {
                roomDisciplinaDAO.updateDisciplina(disciplina);
                Toast.makeText(getApplicationContext(),
                        "Disciplina alterada", Toast.LENGTH_SHORT).show();
                finish();
            } else {

                roomDisciplinaDAO.adcDisciplina(disciplina);
                finish();
            }
        }


    }

    private Disciplina preencheDadosDisciplina() {
        final String nomeDis = nomeDisciplina.getEditText().getText().toString();
        final String salaDis = salaDisciplina.getEditText().getText().toString();
        final String nomeProf = nomeProfessor.getEditText().getText().toString();
        final String sobreDis = sobreDisciplina.getEditText().getText().toString();

        if (activityEdit) {
            //Se estiver editando uma disciplina entra aqui. Os dados da disciplina é alterado
            // e a disciplina alterada é retornada
            disciplinaEscolhida.setNome_disciplina(nomeDis);
            disciplinaEscolhida.setNome_sala(salaDis);
            disciplinaEscolhida.setNome_professor(nomeProf);
            disciplinaEscolhida.setSobre_disciplina(sobreDis);

            return disciplinaEscolhida;
        } else {
            // Retorna uma nova disciplina a ser criada
            Disciplina novaDisciplina = new Disciplina(nomeDis, salaDis, nomeProf, sobreDis, "Status Indiferente");
            return novaDisciplina;

        }


    }


    private void buscaElementonaTela() {
        nomeDisciplina = findViewById(R.id.nome_disciplina_edittext);
        salaDisciplina = findViewById(R.id.sala_disciplina_edittext);
        nomeProfessor = findViewById(R.id.professor_disciplina_edittext);
        sobreDisciplina = findViewById(R.id.sobre_materia_disciplina);
        aSwitchJaCompletou = findViewById(R.id.switch_jacompletou);
        box_notaObtica = findViewById(R.id.box_nota_obtida);
        indicatorSeekBar = findViewById(R.id.seekbarNota);
        teste = findViewById(R.id.teste);
        principal = findViewById(R.id.principal_linear);
        //nota_obtida = findViewById(R.id.nota_obtida_editText);
        //confirmar_nota_obtida = findViewById(R.id.confirmar_nota_obtida_disciplina);
        //textoNotaObtida = findViewById(R.id.texto_da_nota);

    }


}
