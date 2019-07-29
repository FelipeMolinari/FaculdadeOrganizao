package com.example.faculdadeorganizao.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.RecyclerViewListaAtividadesAdapter;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomAtividadeDAO;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.model.Atividade;
import com.example.faculdadeorganizao.model.Disciplina;
import com.example.faculdadeorganizao.ui.Activity_DisciplinaEscolhida;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
    private TextView buttonFiltrarAtividade;
    private boolean filtraCompletas = false;
    private boolean filtraAtrasadas = false;
    private boolean filtraProximas = false;
    private RoomDisciplinaDAO roomDisciplinaDAO;

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

        conectaNoBd();
        listAtividadesDaDisciplina = roomAtividadeDAO.getListaAtividadeDaDisciplina(disciplinaDaAtividade.getId_disciplina());
        encontraElementosTela();
        configFiltroAtividade();

        configListaAtividades();
        return view;
    }


    private void configFiltroAtividade() {

        buttonFiltrarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Filtros", "filtraAtrasadas " + filtraAtrasadas + " filtraProximas" + filtraProximas + " filtraCompletas" + filtraCompletas);
                androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getContext());

                View mView = getLayoutInflater().inflate(R.layout.dialog_filtrar_atividade, null);
                alertDialog.setView(mView);

                AlertDialog dialog = alertDialog.create();

                configElementosDialogFiltrarLista(mView, dialog);

                dialog.show();
            }
        });

    }

    private void configElementosDialogFiltrarLista(View mView, final AlertDialog dialog) {
        final CheckBox filtrarCompletas = mView.findViewById(R.id.filtro_concluidas);
        final CheckBox filtrarAtrasadas = mView.findViewById(R.id.filtro_atrasadas);
        final CheckBox filtrarProximas = mView.findViewById(R.id.filtro_proxima);
        filtrarCompletas.setChecked(filtraCompletas);
        filtrarAtrasadas.setChecked(filtraAtrasadas);
        filtrarProximas.setChecked(filtraProximas);

        TextView completarFiltro = mView.findViewById(R.id.confirmar_filtro);
        completarFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filtrarCompletas.isChecked()) filtraCompletas = true;
                else filtraCompletas = false;
                if (filtrarProximas.isChecked()) filtraProximas = true;
                else filtraProximas = false;
                if (filtrarAtrasadas.isChecked()) filtraAtrasadas = true;
                else filtraAtrasadas = false;
                dialog.dismiss();

                Activity_DisciplinaEscolhida activity = (Activity_DisciplinaEscolhida) getActivity();
                activity.onUpdate(2);


            }
        });


    }


    private void encontraElementosTela() {
        recyclerViewList = view.findViewById(R.id.recycler_view_list_atividade);
        imagemPraia = view.findViewById(R.id.imageViewBeach);
        textViewSemAtividades = view.findViewById(R.id.texto_atividade_pendente);
        buttonFiltrarAtividade = view.findViewById(R.id.button_filtrar_atividade);
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


        conectaNoBd();

        refreshData();
        teste();

        seListaVaziaMostrar();
    }

    private void teste() {
        for (Atividade atividade : listAtividadesDaDisciplina) {
            if (atividade.isAtividadeCompleta()) {
                Log.i("Entrou", atividade.getNomeAtividade());
            } else {
                Log.i("Entrou", "Nenhuma atividade completa");

            }

        }
    }

    private void refreshData() {


        if (filtraProximas | filtraCompletas | filtraAtrasadas) {
            final List<Atividade> atividadesAux = new ArrayList<>();

            if (filtraCompletas) {
                atividadesAux.addAll(roomAtividadeDAO.retornaAtividadesNaoCompletas(disciplinaDaAtividade.getId_disciplina()));
            }
            if (filtraAtrasadas) {
                Calendar cal = Calendar.getInstance();
                Calendar atividadeDate = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                for (Atividade ativ : listAtividadesDaDisciplina) {
                    try {
                        atividadeDate.setTime(simpleDateFormat.parse(ativ.getDataFormata()));
                        Log.i("Teste","Data formatada " +atividadeDate.getTime().toString());
                    } catch (ParseException p) {
                        p.printStackTrace();
                    }
                    if (cal.compareTo(atividadeDate) <= 0) {
                        atividadesAux.add(ativ);
                        Log.i("TESTE","Nome atividade ccompare date: " + ativ.getNomeAtividade() + "");
                    }
                }
            }
            listAtividadesDaDisciplina.clear();
            listAtividadesDaDisciplina.addAll(atividadesAux);
            adapterAtividade.notifyDataSetChanged();
        } else {

            listAtividadesDaDisciplina.clear();
            listAtividadesDaDisciplina.addAll(roomAtividadeDAO
                    .getListaAtividadeDaDisciplina(disciplinaDaAtividade.getId_disciplina()));
        }
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


        seListaVaziaMostrar();
        recyclerViewList.setLayoutManager(new LinearLayoutManager(getActivity()));
        configAdapter();
        recyclerViewList.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
    }

    private void conectaNoBd() {
        database = DataBasePrincipal.getInstance(getContext());
        roomAtividadeDAO = database.getRoomAtividadeDAO();
        roomDisciplinaDAO = database.getRoomDisciplinaDAO();
        disciplinaDaAtividade = roomDisciplinaDAO.getDisciplina(disciplinaDaAtividade.getId_disciplina());
    }

    private void configAdapter() {
        adapterAtividade = new RecyclerViewListaAtividadesAdapter(listAtividadesDaDisciplina, getContext());
        recyclerViewList.setAdapter(adapterAtividade);
        adapterAtividade.setOnItemClickListener(onItemClickListener);
    }


}

