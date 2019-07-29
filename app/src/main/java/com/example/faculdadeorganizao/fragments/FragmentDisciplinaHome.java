package com.example.faculdadeorganizao.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.model.Disciplina;
import com.example.faculdadeorganizao.ui.Activity_CadastroDisciplina;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentDisciplinaHome extends Fragment {

    private TextView sala_disciplina;
    private TextView nome_professor;
    private TextView sobre_disciplina;
    private ImageView button_editar;
    private View view;
    private Disciplina disciplinaDaAtividade;
    private TextView nenhuma_info;
    private DataBasePrincipal database;
    private RoomDisciplinaDAO roomDisciplinaDAO;
    private ImageView aumentarLimite;
    private ImageView diminuirLimite;
    private ImageView aumentarFaltas;
    private ImageView diminuirFaltas;

    private TextView limiteFaltas;
    private TextView faltas;
    private TextView aindaPodeFalar;
    private int limiteFaltasInteiro;
    private int faltasInteiros;
    private int faltasRestantesInteiro;

    private TextView confirmarEditaFaltas;

    public FragmentDisciplinaHome() {
    }
    public static FragmentDisciplinaHome newInstance(Disciplina disciplinaSelecionada) {
        FragmentDisciplinaHome fragment = new FragmentDisciplinaHome();
        Bundle args = new Bundle();
        args.putSerializable("Disciplina", disciplinaSelecionada);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disciplina_home,container, false);
        buscaNaTela();
        setDadosDisciplina();
        configEventEditar();
        configButtonsFaltas();


        return  view;
    }

    private void configButtonsFaltas() {

        limiteFaltasInteiro = disciplinaDaAtividade.getLimiteFaltas();
        faltasInteiros = disciplinaDaAtividade.getFaltas();
        faltasRestantesInteiro = limiteFaltasInteiro-faltasInteiros;
        aindaPodeFalar.setText(faltasRestantesInteiro + "");

        configLimite();
        configFaltas();
        configConfirmarEditaFaltas();
    }


    private void configFaltas() {
        configDiminuirFaltas();
        configAumentarFaltas();
    }

    private void configAumentarFaltas() {
        aumentarFaltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faltasInteiros++;
                faltas.setText(faltasInteiros+"");
                aindaPodeFalar.setText((limiteFaltasInteiro- faltasInteiros)+"");

                confirmarEditaFaltas.setVisibility(View.VISIBLE);
            }
        });

    }

    private void configDiminuirFaltas() {
        diminuirFaltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faltasInteiros--;
                if(faltasInteiros<0) {
                    faltasInteiros = 0;
                }
                aindaPodeFalar.setText((limiteFaltasInteiro- faltasInteiros)+"");
                faltas.setText(faltasInteiros+"");
                confirmarEditaFaltas.setVisibility(View.VISIBLE);
            }
        });

    }

    private void configConfirmarEditaFaltas() {
        confirmarEditaFaltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disciplinaDaAtividade.setLimiteFaltas(limiteFaltasInteiro);
                disciplinaDaAtividade.setFaltas(faltasInteiros);

                roomDisciplinaDAO.updateDisciplina(disciplinaDaAtividade);
                confirmarEditaFaltas.setVisibility(View.GONE);

            }
        });
    }

    private void configLimite() {
        configAumentarLimite();
        configDiminurLimite();
    }

    private void configAumentarLimite() {

        aumentarLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limiteFaltasInteiro++;

                aindaPodeFalar.setText((limiteFaltasInteiro- faltasInteiros)+"");
                limiteFaltas.setText((limiteFaltasInteiro)+"");
                confirmarEditaFaltas.setVisibility(View.VISIBLE);

            }
        });

    }
    private void configDiminurLimite() {
        diminuirLimite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limiteFaltasInteiro--;
                if(limiteFaltasInteiro<0) {
                    limiteFaltasInteiro = 0;
                }
                aindaPodeFalar.setText((limiteFaltasInteiro- faltasInteiros)+"");
                limiteFaltas.setText(limiteFaltasInteiro+"");
                confirmarEditaFaltas.setVisibility(View.VISIBLE);
            }
        });
    }
    private void configEventEditar() {
        button_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_CadastroDisciplina.class);
                intent.putExtra("Disciplina", disciplinaDaAtividade);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreashFaltas() {
        limiteFaltasInteiro = disciplinaDaAtividade.getLimiteFaltas();
        faltasInteiros = disciplinaDaAtividade.getFaltas();
        faltas.setText(faltasInteiros+"");
        limiteFaltas.setText(limiteFaltasInteiro+"");
        faltasRestantesInteiro = limiteFaltasInteiro-faltasInteiros;
        aindaPodeFalar.setText(faltasRestantesInteiro+"");
    }

    private void refreshData() {
        database = DataBasePrincipal.getInstance(getContext());
        roomDisciplinaDAO = database.getRoomDisciplinaDAO();
        disciplinaDaAtividade = roomDisciplinaDAO.getDisciplina(disciplinaDaAtividade.getId_disciplina());
        setDadosDisciplina();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disciplinaDaAtividade = (Disciplina) (getArguments() != null ? getArguments().getSerializable("Disciplina") : 1);

    }

    private void setDadosDisciplina() {
        refreashFaltas();

        nome_professor.setText(disciplinaDaAtividade.getNome_professor());
        sobre_disciplina.setText(disciplinaDaAtividade.getSobre_disciplina());
        if(disciplinaDaAtividade.getNome_sala().isEmpty() && disciplinaDaAtividade.getNome_professor().isEmpty()
                && disciplinaDaAtividade.getSobre_disciplina().isEmpty())
        {
            nenhuma_info.setVisibility(View.VISIBLE);
            sala_disciplina.setVisibility(View.GONE);
            nome_professor.setVisibility(View.GONE);
            sobre_disciplina.setVisibility(View.GONE);
        }else{
            if(disciplinaDaAtividade.getNome_sala().isEmpty())
                sala_disciplina.setVisibility(View.GONE);
            else if(disciplinaDaAtividade.getNome_professor().isEmpty())
                nome_professor.setVisibility(View.GONE);
            else if(disciplinaDaAtividade.getSobre_disciplina().isEmpty())
                sobre_disciplina.setVisibility(View.GONE);
            else{
                sobre_disciplina.setVisibility(View.VISIBLE);
                nome_professor.setVisibility(View.VISIBLE);
                sala_disciplina.setVisibility(View.VISIBLE);
            }
        }

    }


    private void buscaNaTela() {
        sala_disciplina = view.findViewById(R.id.sala_disciplina_textView);
        nome_professor = view.findViewById(R.id.nome_professor_textView);
        sobre_disciplina = view.findViewById(R.id.sobre_disciplina_textView);
        button_editar = view.findViewById(R.id.id_button_editar);
        nenhuma_info = view.findViewById(R.id.nenhuma_info_disciplina_id);

        aumentarLimite = view.findViewById(R.id.aumentar_limite);
        diminuirLimite = view.findViewById(R.id.diminuir_limite);
        aumentarFaltas = view.findViewById(R.id.aumentar_faltas);
        diminuirFaltas = view.findViewById(R.id.diminuir_faltas);
        limiteFaltas = view.findViewById(R.id.lim_faltas_editavel);

        faltas = view.findViewById(R.id.faltas_editavel);
        aindaPodeFalar = view.findViewById(R.id.ainda_pode_faltar_editavel);
        confirmarEditaFaltas = view.findViewById(R.id.confirmar_edita_faltas);
    }
}