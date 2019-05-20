package com.example.faculdadeorganizao.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class Activity_CadastroAtividade extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton buttonAtivColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__cadastro_atividade);

        buttonAtivColor = findViewById(R.id.button_define_color);

        toolbar = findViewById(R.id.toobar_form_atividade);
        configToolbar();

        configButtonChangeColorAtiv();

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

        colorPicker.setColors(defineCores()).setColumns(4).setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        Toast.makeText(getApplicationContext(),"botão cor da atividade clicado"
                                ,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),"Botão voltar clicado", Toast.LENGTH_SHORT).show();


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
        Toast.makeText(getApplicationContext(),"Atividade criada", Toast.LENGTH_SHORT).show();
    }

}
