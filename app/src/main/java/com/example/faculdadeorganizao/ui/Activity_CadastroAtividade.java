package com.example.faculdadeorganizao.ui;

import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomAtividadeDAO;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.fragments.DatePickerFragments;
import com.example.faculdadeorganizao.model.Atividade;
import com.example.faculdadeorganizao.model.Disciplina;
import com.google.android.material.textfield.TextInputLayout;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import petrov.kristiyan.colorpicker.ColorPicker;

public class Activity_CadastroAtividade extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private int colorAtividade;
    private ImageButton buttonAtivColor;
    private ImageButton buttonAtivData;
    private TextInputLayout textEditData;
    private TextInputLayout descricaoAtividade;
    private TextInputLayout nomeAtividade;
    private TextView quantoVale;
    private TextView quantoDisponivel;
    private LinearLayout boxQuantoValeeDisponivel;
    private ConstraintLayout constraintLayout;
    private TextView faltaraXPontos;

    private Toolbar toolbar;
    private IndicatorSeekBar indicatorSeekBar;

    private RoomAtividadeDAO atividadeDAO;
    private DataBasePrincipal database;

    private RoomDisciplinaDAO disciplinaDAO;
    private Disciplina disciplinaEscolhida;

    private Long idDisciplina;
    private boolean activityEdit= false;
    private long idAtividade;
    private float notaSemAtualizar;
    private float notaMaximaAtividade;
    private float notaArredondada;
    Calendar dataAtividade = Calendar.getInstance();
    private ArrayList<String> coresColorPicker = defineCores();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorAtividade = ContextCompat.getColor(getApplicationContext(), R.color.colorSecondary);
        setContentView(R.layout.activity__cadastro_atividade);
        encontraElementosnaTela();
        configNavig();
        setTitle("Cadastrar Atividade");

        conectaBd();

        getDisciplinaEscolhida();
        verificaSeEditar();

        seForEditarPreencheCampos();
        configElementos();

    }
    private void configNavig() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark)); //status bar or the time bar at the top
        }
    }
    private void verificaSeEditar() {
        Bundle bundle = getIntent().getExtras();
        if(bundle.getSerializable("AtividadeId") != null){

            activityEdit = true;
            idAtividade = bundle.getLong("AtividadeId");
            idDisciplina = atividadeDAO.getAtividadeById(idAtividade).getDisciplinaid();

            disciplinaEscolhida = disciplinaDAO.getDisciplina(idDisciplina);
        }
    }

    private void conectaBd() {
        database = DataBasePrincipal.getInstance(this);
        atividadeDAO = database.getRoomAtividadeDAO();
        disciplinaDAO = database.getRoomDisciplinaDAO();
    }
    private void getDisciplinaEscolhida() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getSerializable("Disciplina") != null) {
            disciplinaEscolhida = (Disciplina) bundle.getSerializable("Disciplina");
            disciplinaEscolhida = disciplinaDAO.getDisciplina(disciplinaEscolhida.getId_disciplina());
        }

    }

    private void configElementos() {
        configToolbar();
        datePickerOnclick();
        configButtonChangeColorAtiv();
        configSeekBar();
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Atividade não foi criada", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }
    private void datePickerOnclick() {
        textEditData.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "Date picker");
            }
        });

        buttonAtivData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(), "Date picker");
            }
        });
    }
    private void configButtonChangeColorAtiv() {
        buttonAtivColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencolorpicker();
            }
        });
    }

    private void opencolorpicker() {
        final ColorPicker colorPicker = new ColorPicker(this);

        colorPicker.setColors(coresColorPicker).setColumns(4).setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        buttonAtivColor.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        colorAtividade = color;
                    }

                    @Override
                    public void onCancel() {
                    }
                }).show();
    }

    private void configSeekBar() {

        faltaraXPontos.setText("Falta "+ (100-disciplinaEscolhida.getNota_distribuida()) + " pontos a ser distribuído");
        constraintLayout.removeView(boxQuantoValeeDisponivel);
        indicatorSeekBar.getIndicator().setTopContentView(boxQuantoValeeDisponivel);
        indicatorSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                Double pesoAtividade = (double) seekParams.progressFloat/100;
                Double notaAtividade = notaMaximaAtividade* pesoAtividade;

                notaArredondada =  (float) arredondaValor(notaAtividade);

                quantoVale.setText(notaArredondada + "/");
                quantoDisponivel.setText(String.valueOf(notaMaximaAtividade));
                faltaraXPontos.setText("Faltará "+ (notaMaximaAtividade-notaArredondada) + " pontos a ser distribuído");
            }
            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

            }
        });
    }
    private double arredondaValor(Double notaAtividade) {
        if(notaAtividade- Math.floor(notaAtividade)<0.33){
            return Math.floor(notaAtividade);
        }else if(notaAtividade- Math.floor(notaAtividade)>=0.66){
            return Math.floor(notaAtividade)+1;
        }else {
            return Math.floor(notaAtividade) + 0.5;
        }
    }
    private void seForEditarPreencheCampos() {
        if (activityEdit) {
            Atividade atividade = atividadeDAO.getAtividadeById(idAtividade);

            nomeAtividade.getEditText().setText(atividade.getNomeAtividade());
            buttonAtivColor.getBackground().setColorFilter(atividade.getColorAtividade(), PorterDuff.Mode.SRC_ATOP);
            textEditData.getEditText().setText(atividade.getDataAtividade());
            descricaoAtividade.getEditText().setText(atividade.getDescricaoAtividade());
            indicatorSeekBar.setProgress(atividade.getNotaDaAtividade());
            colorAtividade = atividade.getColorAtividade();
            notaSemAtualizar = atividade.getNotaDaAtividade();
            notaMaximaAtividade= 100- disciplinaEscolhida.getNota_distribuida()+ atividade.getNotaDaAtividade();
            quantoVale.setText(String.valueOf(atividade.getNotaDaAtividade()));

            Toast.makeText(getApplicationContext(), "ID Ativ: "+ idAtividade + " ID Dis: " + idDisciplina,Toast.LENGTH_LONG ).show();

        } else
            notaMaximaAtividade= 100- disciplinaEscolhida.getNota_distribuida();
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
                confirmaCriarAtividade();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void confirmaCriarAtividade() {
        Atividade novaAtividade = preencheDadosAtividade();

        if (novaAtividade.getNomeAtividade().isEmpty()) {
            nomeAtividade.getEditText().setError("Campo obrigatório");
        } else if (novaAtividade.getDataAtividade().isEmpty()) {
            textEditData.getEditText().setError("Campo Obrigatório");
        } else {

            //ALTERA UMA ATIVIDADE JÀ EXISTENTE
            if (activityEdit) {
                Log.i("Editar", "editar? " + activityEdit);
                atualizaAtividade(novaAtividade);
                finish();
            }
            //CRIA UMA NOVA ATIVIDADE
            else {
                Log.i("Editar", "criar? " + activityEdit);

                adicionaAtividade(novaAtividade);
                finish();
            }
        }
    }
    private Atividade preencheDadosAtividade() {
        Atividade novaAtividade = new Atividade();
        final String nomeNovaAtividade = nomeAtividade.getEditText().getText().toString();
        final String dataNovaAtividade = textEditData.getEditText().getText().toString();
        final String descricaoNovaAtividade = descricaoAtividade.getEditText().getText().toString();
        final float notaAtividade = notaArredondada;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = simpleDateFormat.format(dataAtividade.getTime());

        novaAtividade.setDataFormata(dataFormatada);
        novaAtividade.setNotaDaAtividade(notaAtividade);
        novaAtividade.setNomeAtividade(nomeNovaAtividade);
        novaAtividade.setDataAtividade(dataNovaAtividade);
        novaAtividade.setDescricaoAtividade(descricaoNovaAtividade);
        novaAtividade.setColorAtividade(colorAtividade);

        return novaAtividade;
    }
    private Long retornaIDDaDisciplina() {

        return disciplinaEscolhida.getId_disciplina();
    }
    private void adicionaAtividade(Atividade novaAtividade) {
        atualizaNotaDistribuida();
        novaAtividade.setDisciplinaid(retornaIDDaDisciplina());
        atividadeDAO.adcAtividade(novaAtividade);
    }

    private void atualizaAtividade(Atividade novaAtividade) {
        novaAtividade.setColorAtividade(colorAtividade);
        novaAtividade.setDisciplinaid(retornaIDDaDisciplina());
        novaAtividade.setId_atividade(idAtividade);
        atualizaNotaDistribuida();
        atividadeDAO.updateAtividade(novaAtividade);
    }

    private void atualizaNotaDistribuida() {
        if(!activityEdit)//Se tiver adicionando uma atividade
        {
            disciplinaEscolhida.adicionarNota(notaArredondada);
        }else{// Adicionando a tarefa
            disciplinaEscolhida.adicionarNota(-notaSemAtualizar);
            disciplinaEscolhida.adicionarNota(notaArredondada);

        }
        disciplinaDAO.updateDisciplina(disciplinaEscolhida);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        dataAtividade.set(Calendar.YEAR, year);
        dataAtividade.set(Calendar.MONTH, month);
        dataAtividade.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        formataDataEscolhida(dataAtividade);
    }
    private void formataDataEscolhida(Calendar dataEscolhida) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = simpleDateFormat.format(dataEscolhida.getTime());
        String diaDaSemana = defineDiaDaSemana(dataEscolhida);
        textEditData.getEditText().setText(diaDaSemana + ", " + dataFormatada);
    }
    private String defineDiaDaSemana(Calendar atividade) {
        if (atividade.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return "Dom";
        } else if (atividade.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            return "Seg";
        } else if (atividade.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            return "Ter";
        } else if (atividade.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            return "Qua";
        } else if (atividade.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            return "Qui";
        } else if (atividade.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            return "Sex";
        } else if (atividade.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            return "Sab";
        } else return "";
    }
    private ArrayList<String> defineCores() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#E53935");
        colors.add("#D81B60");
        colors.add("#8E24AA");
        colors.add("#5E35B1");
        colors.add("#43A047");
        colors.add("#FDD835");
        colors.add("#FB8C00");
        colors.add("#00796B");
        colors.add("#9E9D24");
        colors.add("#00E5FF");
        colors.add("#B2FF59");
        colors.add("#FF5252");
        return colors;
    }
    private void encontraElementosnaTela() {
        nomeAtividade = findViewById(R.id.textinputlayoutNomeAtividade);
        buttonAtivColor = findViewById(R.id.button_define_color);
        buttonAtivData = findViewById(R.id.button_define_data);
        textEditData = findViewById(R.id.textinputlayoutDataAtividade);
        descricaoAtividade = findViewById(R.id.textinputlayoutDescricaoAtividade);
        indicatorSeekBar = findViewById(R.id.seekbarNota);
        boxQuantoValeeDisponivel = findViewById(R.id.box_quanto_vale_quanto_disponivel);
        quantoVale = findViewById(R.id.quanto_vale);
        quantoDisponivel = findViewById(R.id.quanto_resta);
        toolbar = findViewById(R.id.toobar_form_atividade);
        constraintLayout = findViewById(R.id.container_principal);
        faltaraXPontos = findViewById(R.id.faltara_x_pontos);
    }
}