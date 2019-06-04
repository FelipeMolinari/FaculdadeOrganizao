package com.example.faculdadeorganizao.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.faculdadeorganizao.DAO.AtividadeDisciplinaDAO;
import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.fragments.DatePickerFragments;
import com.example.faculdadeorganizao.model.Atividade;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import petrov.kristiyan.colorpicker.ColorPicker;

public class Activity_CadastroAtividade extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private Toolbar toolbar;
    private ImageButton buttonAtivColor;
    private ImageButton buttonAtivData;
    private int colorAtividade ;
    private TextInputLayout textEditData;
    private TextInputLayout descricaoAtividade;
    private TextInputLayout nomeAtividade;
    private ArrayList<String> coresColorPicker = defineCores();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colorAtividade= ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);
        setContentView(R.layout.activity__cadastro_atividade);
        encontraElementosnaTela();
        setTitle("Cadastrar Atividade");


        configToolbar();
        configButtonChangeColorAtiv();
        datePickerOnclick();

    }

    private void encontraElementosnaTela() {
        nomeAtividade = findViewById(R.id.textinputlayoutNomeAtividade);
        buttonAtivColor = findViewById(R.id.button_define_color);
        buttonAtivData = findViewById(R.id.button_define_data);
        textEditData = findViewById(R.id.textinputlayoutDataAtividade);
        descricaoAtividade = findViewById(R.id.textinputlayoutDescricaoAtividade);


        toolbar = findViewById(R.id.toobar_form_atividade);
    }

    private void datePickerOnclick() {
        buttonAtivData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragments();
                datePicker.show(getSupportFragmentManager(),"Date picker");
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

    private void configToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Atividade não foi criada", Toast.LENGTH_SHORT).show();

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
                confirmaCriarAtividade();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void confirmaCriarAtividade() {
        Atividade novaAtividade = preencheDadosAtividade();
        if(novaAtividade.getNomeAtividade().isEmpty()){
            nomeAtividade.getEditText().setError("Campo obrigatório");
        } else if(novaAtividade.getDataAtividade().isEmpty()){
            textEditData.getEditText().setError("Campo Obrigatório");
        }else{
            AtividadeDisciplinaDAO DAO = AtividadeDisciplinaDAO.getInstance();
            DAO.adcAtividade(novaAtividade);
            finish();
        }
    }

    private Atividade preencheDadosAtividade() {
        final String nomeNovaAtividade = nomeAtividade.getEditText().getText().toString();
        final String dataNovaAtividade = textEditData.getEditText().getText().toString();
        final String descricaoNovaAtividade = descricaoAtividade.getEditText().getText().toString();
        return new Atividade(nomeNovaAtividade, colorAtividade,descricaoNovaAtividade, dataNovaAtividade);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar dataAtividade = Calendar.getInstance();
        dataAtividade.set(Calendar.YEAR,year);
        dataAtividade.set(Calendar.MONTH,month);
        dataAtividade.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        formataDataEscolhida(dataAtividade);
    }

    private void formataDataEscolhida(Calendar dataEscolhida) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = simpleDateFormat.format(dataEscolhida.getTime());
        String diaDaSemana = defineDiaDaSemana(dataEscolhida);
        textEditData.getEditText().setText(diaDaSemana+ ", "+dataFormatada);
    }

    private String defineDiaDaSemana(Calendar atividade) {
        if(atividade.get(Calendar.DAY_OF_WEEK ) == Calendar.SUNDAY){
            return "Dom";
        }
        else if(atividade.get(Calendar.DAY_OF_WEEK ) == Calendar.MONDAY){
            return "Seg";
        }
        else if(atividade.get(Calendar.DAY_OF_WEEK ) == Calendar.TUESDAY){
            return "Ter";
        }
        else if(atividade.get(Calendar.DAY_OF_WEEK ) == Calendar.WEDNESDAY){
            return "Qua";
        }
        else if(atividade.get(Calendar.DAY_OF_WEEK ) == Calendar.THURSDAY){
            return "Qui";
        }
        else if(atividade.get(Calendar.DAY_OF_WEEK ) == Calendar.FRIDAY){
            return "Sex";
        }
        else if(atividade.get(Calendar.DAY_OF_WEEK ) == Calendar.SATURDAY){
            return "Sab";
        }
        else return "";
    }
}
