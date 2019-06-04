package com.example.faculdadeorganizao.fragments;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import com.example.faculdadeorganizao.DAO.AtividadeDisciplinaDAO;
import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.OnItemClickListenerAtividade;
import com.example.faculdadeorganizao.adapters.RecyclerViewListaAtividadesAdapter;
import com.example.faculdadeorganizao.model.Atividade;
import com.example.faculdadeorganizao.ui.Activity_CadastroDisciplina;
import com.example.faculdadeorganizao.ui.Activity_DisciplinaEscolhida;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class FragmentDisciplinaAtividades extends Fragment  {

    private View view;
    private RecyclerView recyclerViewList;
    private ArrayList<Atividade> listAtividades;
    private RecyclerViewListaAtividadesAdapter adapterAtividade;

    public View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Atividade atividade = listAtividades.get(position);
            Toast.makeText(getContext(), "You Clicked: " + atividade.getNomeAtividade() + "Cor: "+ atividade.getColorAtividade(), Toast.LENGTH_SHORT).show();
        }
    };

    public FragmentDisciplinaAtividades() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disciplina_atividades,container, false);
        recyclerViewList = view.findViewById(R.id.recycler_view_list_atividade);
        configListaAtividades();
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public void onResume() {
        super.onResume();
        listAtividades = AtividadeDisciplinaDAO.getInstance().getAtividadeArrayList();
        adapterAtividade.notifyDataSetChanged();
    }

    private void configListaAtividades() {
        listAtividades = AtividadeDisciplinaDAO.getInstance().getAtividadeArrayList();
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
        configAdapter();
        recyclerViewList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    private void configAdapter() {
        adapterAtividade = new RecyclerViewListaAtividadesAdapter(listAtividades, getContext());
        recyclerViewList.setAdapter(adapterAtividade);
        adapterAtividade.setOnItemClickListener(onItemClickListener);
    }

}
