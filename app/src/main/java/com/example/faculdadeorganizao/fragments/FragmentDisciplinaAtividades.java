package com.example.faculdadeorganizao.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faculdadeorganizao.DAO.AtividadeDisciplinaDAO;
import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.RecyclerViewListaAtividadesAdapter;
import com.example.faculdadeorganizao.model.Atividade;
import com.example.faculdadeorganizao.ui.Activity_CadastroDisciplina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentDisciplinaAtividades extends Fragment {

    private View view;
    private RecyclerView recyclerViewList;
    private ArrayList<Atividade> listAtividades;
    public FragmentDisciplinaAtividades() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disciplina_atividades,container, false);

        recyclerViewList = view.findViewById(R.id.recycler_view_list_atividade);


        configListaAtividades();
        return view;
    }

    private void configListaAtividades() {
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewList.setAdapter(new RecyclerViewListaAtividadesAdapter(this.listAtividades,getContext()));
        recyclerViewList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        povoaListAtividade();


    }

    private void povoaListAtividade() {

        AtividadeDisciplinaDAO DAO = AtividadeDisciplinaDAO.getInstance();

        for(int i=0; i<10;i++){
           DAO.adcAtividade (new Atividade("Atividade "+ i, Calendar.getInstance(),"Descrição "+i));
        }
        listAtividades = DAO.getAtividadeArrayList();

        for(int i=0; i<10;i++){
            Log.i("Atividades" + i, listAtividades.get(i).getNomeAtividade());        }


    }
}
