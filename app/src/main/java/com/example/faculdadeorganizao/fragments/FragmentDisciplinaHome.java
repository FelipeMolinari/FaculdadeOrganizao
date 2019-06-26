package com.example.faculdadeorganizao.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


        return  view;
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
        sala_disciplina.setText(disciplinaDaAtividade.getNome_sala());
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
    }
}