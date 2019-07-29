package com.example.faculdadeorganizao.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.adapters.AdapterListViewAtividadeAvaliativa;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomAtividadeDAO;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.model.Atividade;
import com.example.faculdadeorganizao.model.Disciplina;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentDisciplinaProvas extends Fragment {

    private ListView listViewAtividadeAvalidativas;
    private List<Atividade> atividadesAvaliativas;
    private View view;
    private DataBasePrincipal database;
    private RoomAtividadeDAO atividadeDAO;
    private RoomDisciplinaDAO disciplinaDAO;
    private Disciplina disciplinaDaAtividade;
    AdapterListViewAtividadeAvaliativa adapterListViewAtividadeAvaliativa;
    private PieChart pieChartNotatiradas;
    private TextView semestatisticaTextview;
    public static FragmentDisciplinaProvas newInstance(Disciplina disciplinaSelecionada) {
        FragmentDisciplinaProvas fragment = new FragmentDisciplinaProvas();
        Bundle args = new Bundle();
        args.putSerializable("Disciplina", disciplinaSelecionada);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disciplinaDaAtividade = (Disciplina) (getArguments() != null ? getArguments().getSerializable("Disciplina") : 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_disciplina_provas,container, false);
        buscaNaTela();
        conectaBd();
        encontraAtividadesAvaliativas();
        configAdapterAtividadesAvaliativas();
        configPieChart();

        return view;
    }

    private void configPieChart() {

        if(atividadesAvaliativas.size()!=0){
            pieChartNotatiradas.setVisibility(View.VISIBLE);
            semestatisticaTextview.setVisibility(View.GONE);

            pieChartNotatiradas.setUsePercentValues(true);
            pieChartNotatiradas.getDescription().setEnabled(false);
            pieChartNotatiradas.setExtraOffsets(5,10,5,5);
            pieChartNotatiradas.setDragDecelerationFrictionCoef(0.5f);

            pieChartNotatiradas.setDrawHoleEnabled(false);
            pieChartNotatiradas.setHoleColor(Color.WHITE);
            pieChartNotatiradas.setTransparentCircleRadius(61f);
            pieChartNotatiradas.animateY(1000, Easing.EaseOutCubic);

            ArrayList<PieEntry> valoresChart = new ArrayList<>();
            ArrayList<Integer> coresItens = new ArrayList<>();
            for (Atividade ativ:atividadesAvaliativas) {
                valoresChart.add(new PieEntry(ativ.getNotaDaAtividade(), ativ.getNomeAtividade()));
                coresItens.add(ativ.getColorAtividade());
            }
            coresItens.add(getResources().getColor(R.color.textColorHint));
           valoresChart.add(new PieEntry((100-disciplinaDaAtividade.getNota_distribuida()),"Falta distribuir"));
            Toast.makeText(getContext(),""+disciplinaDaAtividade.getNota_distribuida(), Toast.LENGTH_LONG).show();

            PieDataSet dataSet = new PieDataSet(valoresChart, "Distribuição");

            dataSet.setSliceSpace(2f);
            dataSet.setSelectionShift(5f);

            dataSet.setColors(coresItens);




            PieData data = new PieData(dataSet);
            data.setValueTextSize(10f);
            data.setValueTextColor(getResources().getColor(R.color.white));
            pieChartNotatiradas.setData(data);

        }else{
            pieChartNotatiradas.setVisibility(View.GONE);
            semestatisticaTextview.setVisibility(View.VISIBLE);

        }



    }

    private void configAdapterAtividadesAvaliativas() {
        adapterListViewAtividadeAvaliativa = new AdapterListViewAtividadeAvaliativa(getContext(),
                R.layout.lista_atividade_avaliativas, atividadesAvaliativas);
        listViewAtividadeAvalidativas.setAdapter(adapterListViewAtividadeAvaliativa);
    }

    private void conectaBd() {
        database = DataBasePrincipal.getInstance(getContext());
        atividadeDAO = database.getRoomAtividadeDAO();
        disciplinaDAO = database.getRoomDisciplinaDAO();
    }
    private void encontraAtividadesAvaliativas() {
        atividadesAvaliativas = atividadeDAO.retornaAtividadesAvaliativas(disciplinaDaAtividade.getId_disciplina());
    }

    private void buscaNaTela() {
        pieChartNotatiradas = view.findViewById(R.id.chartpienotas);
        listViewAtividadeAvalidativas = view.findViewById(R.id.list_atividade_avaliativa);
        semestatisticaTextview = view.findViewById(R.id.semestatistica);
    }

    @Override
    public void onResume() {
        super.onResume();
        conectaBd();
        refreshData();

    }

    private void refreshData() {
        atividadesAvaliativas.clear();
        atividadesAvaliativas = atividadeDAO.retornaAtividadesAvaliativas(disciplinaDaAtividade.getId_disciplina());
        disciplinaDaAtividade = disciplinaDAO.getDisciplina(disciplinaDaAtividade.getId_disciplina());
        configAdapterAtividadesAvaliativas();
        configPieChart();
    }
}
