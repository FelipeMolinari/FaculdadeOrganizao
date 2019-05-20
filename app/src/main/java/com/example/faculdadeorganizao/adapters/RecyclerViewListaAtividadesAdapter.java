package com.example.faculdadeorganizao.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.model.Atividade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerViewListaAtividadesAdapter extends RecyclerView.Adapter<RecyclerViewListaAtividadesAdapter.ViewHolderAtividade> {

    private ArrayList<Atividade> listAtividades;
    private Context context;

    public RecyclerViewListaAtividadesAdapter(ArrayList<Atividade> listAtividades, Context context) {
        this.listAtividades = listAtividades;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewListaAtividadesAdapter.ViewHolderAtividade onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View inflado = inflater.inflate(R.layout.atividade_disciplina_item_list, viewGroup, false);
        Log.i("onCreate", "sim");
        return new ViewHolderAtividade(inflado);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewListaAtividadesAdapter.ViewHolderAtividade viewHolder, int pos) {
        Atividade novaAtiv = listAtividades.get(pos);
        Log.i("onBind "+ pos, novaAtiv.getNomeAtividade());
        viewHolder.setElementsAtividade(novaAtiv);


    }

    @Override
    public int getItemCount() {
        return listAtividades.size();
    }

    public class ViewHolderAtividade extends RecyclerView.ViewHolder {

        private TextView nomeAtividade;
        private TextView dataAtividade;
        private TextView descricaoAtividade;


        public ViewHolderAtividade(@NonNull View itemView) {
            super(itemView);
            nomeAtividade = itemView.findViewById(R.id.nome_atividade_item_list);
            dataAtividade = itemView.findViewById(R.id.data_pra_atividade_item_list);
            descricaoAtividade = itemView.findViewById(R.id.descricao_pra_atividade_item_list);
        }

        public void setElementsAtividade(Atividade atividade){
            nomeAtividade.setText(atividade.getNomeAtividade());
            descricaoAtividade.setText(atividade.getDescricaoAtividade());
            identaDataAtividade(atividade.getDataAtividade());

        }

        private void identaDataAtividade(Calendar atividade) {

            SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/yyyy");
            String dataFormatada = sdf.format(atividade.getTime());
            dataAtividade.setText(dataFormatada);
        }
    }
}
