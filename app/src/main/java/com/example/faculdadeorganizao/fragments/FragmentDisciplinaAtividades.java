package com.example.faculdadeorganizao.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.RecyclerViewListaAtividadesAdapter;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomAtividadeDAO;
import com.example.faculdadeorganizao.model.Atividade;
import com.example.faculdadeorganizao.model.Disciplina;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentDisciplinaAtividades extends Fragment {

    private View view;
    private RecyclerView recyclerViewList;
    private List<Atividade> listAtividadesDaDisciplina;
    private RecyclerViewListaAtividadesAdapter adapterAtividade;
    private ImageView imagemPraia;
    private TextView textViewSemAtividades;
    private RoomAtividadeDAO roomAtividadeDAO;
    private DataBasePrincipal database;
    private String tagFrag;
    private Disciplina disciplinaDaAtividade;

    public View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Atividade atividade = listAtividadesDaDisciplina.get(position);
        }
    };

    public static FragmentDisciplinaAtividades newInstance(Disciplina disciplinaSelecionada, String tagFragment) {
        FragmentDisciplinaAtividades fragment = new FragmentDisciplinaAtividades();
        Bundle args = new Bundle();
        args.putSerializable("Disciplina", disciplinaSelecionada);
        args.putString("tagFragments", tagFragment);
        fragment.setArguments(args);

        return fragment;
    }
    public FragmentDisciplinaAtividades() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_disciplina_atividades, container, false);
        encontraElementosTela();

        configListaAtividades();
        return view;
    }

    private void encontraElementosTela() {
        recyclerViewList = view.findViewById(R.id.recycler_view_list_atividade);
        imagemPraia = view.findViewById(R.id.imageViewBeach);
        textViewSemAtividades = view.findViewById(R.id.texto_atividade_pendente);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disciplinaDaAtividade = (Disciplina) (getArguments() != null ? getArguments().getSerializable("Disciplina") : 1);
        tagFrag = (String) (getArguments() != null ? getArguments().getString("tagFragments") : 1);
        Log.i("TagFragment", tagFrag);



    }


    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(),"Resume", Toast.LENGTH_SHORT).show();
        conectaNoBd();
        refreshData();
        seListaVaziaMostrar();
    }

    void reloadFragmentData(){
        conectaNoBd();
        refreshData();
        seListaVaziaMostrar();
    }

    private void refreshData() {
        listAtividadesDaDisciplina.clear();
        listAtividadesDaDisciplina.addAll(roomAtividadeDAO
                .getListaAtividadeDaDisciplina(disciplinaDaAtividade.getId_disciplina()));
        adapterAtividade.notifyDataSetChanged();
    }

    private void seListaVaziaMostrar() {
        if (listAtividadesDaDisciplina.size() != 0) {
            imagemPraia.setVisibility(View.GONE);
            textViewSemAtividades.setVisibility(View.GONE);
        } else {
            imagemPraia.setVisibility(View.VISIBLE);
            textViewSemAtividades.setVisibility(View.VISIBLE);
        }
    }

    private void configListaAtividades() {
        conectaNoBd();
        listAtividadesDaDisciplina = roomAtividadeDAO.getListaAtividadeDaDisciplina(disciplinaDaAtividade.getId_disciplina());
        seListaVaziaMostrar();
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
        configAdapter();
        recyclerViewList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    private void conectaNoBd() {
        database = DataBasePrincipal.getInstance(getContext());
        roomAtividadeDAO = database.getRoomAtividadeDAO();
    }

    private void configAdapter() {
        adapterAtividade = new RecyclerViewListaAtividadesAdapter(listAtividadesDaDisciplina, getContext());
        recyclerViewList.setAdapter(adapterAtividade);
        adapterAtividade.setOnItemClickListener(onItemClickListener);
    }

}
