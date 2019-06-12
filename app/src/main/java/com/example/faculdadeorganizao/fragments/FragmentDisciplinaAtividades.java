package com.example.faculdadeorganizao.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faculdadeorganizao.DAO.AtividadeDisciplinaDAO;
import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.RecyclerViewListaAtividadesAdapter;
import com.example.faculdadeorganizao.model.Atividade;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentDisciplinaAtividades extends Fragment {

    private View view;
    private RecyclerView recyclerViewList;
    private ArrayList<Atividade> listAtividades;
    private RecyclerViewListaAtividadesAdapter adapterAtividade;
    private ImageView imagemPraia;
    private TextView textViewSemAtividades;

    public View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Atividade atividade = listAtividades.get(position);
            Toast.makeText(getContext(), "You Clicked: " + atividade.getNomeAtividade() + "Cor: " + atividade.getColorAtividade(), Toast.LENGTH_SHORT).show();
        }
    };

    public FragmentDisciplinaAtividades() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disciplina_atividades, container, false);
        recyclerViewList = view.findViewById(R.id.recycler_view_list_atividade);
        imagemPraia = view.findViewById(R.id.imageViewBeach);
        textViewSemAtividades = view.findViewById(R.id.texto_atividade_pendente);

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
        mostrarListaAtividadeVazia();
    }

    private void mostrarListaAtividadeVazia() {
        if (listAtividades.size() != 0) {
            imagemPraia.setVisibility(View.GONE);
            textViewSemAtividades.setVisibility(View.GONE);
        } else {
            imagemPraia.setVisibility(View.VISIBLE);
            textViewSemAtividades.setVisibility(View.VISIBLE);
        }
    }

    private void configListaAtividades() {
        listAtividades = AtividadeDisciplinaDAO.getInstance().getAtividadeArrayList();
        mostrarListaAtividadeVazia();
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
