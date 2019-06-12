package com.example.faculdadeorganizao.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.model.Atividade;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewListaAtividadesAdapter extends RecyclerView.Adapter<RecyclerViewListaAtividadesAdapter.ViewHolderAtividade> {

    private ArrayList<Atividade> listAtividades;
    private Context context;
    public View.OnClickListener itemClickListener;


    public RecyclerViewListaAtividadesAdapter(ArrayList<Atividade> listAtividades, Context context) {
        this.listAtividades = listAtividades;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewListaAtividadesAdapter.ViewHolderAtividade onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View inflado = inflater.inflate(R.layout.atividade_disciplina_item_list, viewGroup, false);
        return new ViewHolderAtividade(inflado);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewListaAtividadesAdapter.ViewHolderAtividade viewHolder, int position) {
        Atividade novaAtiv = listAtividades.get(position);
        Toast.makeText(context, "Pos= " + position + " Tamanho = " + listAtividades.size() + " Nova= " + novaAtiv.getNomeAtividade(), Toast.LENGTH_SHORT).show();
        viewHolder.setElementsAtividade(novaAtiv);
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public int getItemCount() {
        return listAtividades.size();
    }

    public class ViewHolderAtividade extends RecyclerView.ViewHolder {

        private TextView nomeAtividade;
        private TextView dataAtividade;
        private TextView descricaoAtividade;
        private ImageView circleAtividade;
        public Atividade atividade = new Atividade();


        public ViewHolderAtividade(@NonNull View itemView) {
            super(itemView);
            nomeAtividade = itemView.findViewById(R.id.nome_atividade_item_list);
            dataAtividade = itemView.findViewById(R.id.data_pra_atividade_item_list);
            descricaoAtividade = itemView.findViewById(R.id.descricao_pra_atividade_item_list);
            circleAtividade = itemView.findViewById(R.id.circulo_cor_atividade);
            itemView.setTag(this);
            itemView.setOnClickListener(itemClickListener);

        }

        public void setElementsAtividade(Atividade novaAtividade) {
            nomeAtividade.setText(novaAtividade.getNomeAtividade());
            dataAtividade.setText(novaAtividade.getDataAtividade());
            if (novaAtividade.getDescricaoAtividade().isEmpty()) {
                descricaoAtividade.setVisibility(View.GONE);
                circleAtividade.setVisibility(View.GONE);
            } else {
                descricaoAtividade.setVisibility(View.VISIBLE);
                circleAtividade.setVisibility(View.VISIBLE);
                circleAtividade.setColorFilter(novaAtividade.getColorAtividade());
                descricaoAtividade.setText(novaAtividade.getDescricaoAtividade());
            }
            this.atividade = novaAtividade;
        }
        /*private void setTextViewDrawableColor(int color) {

            for (Drawable drawable : descricaoAtividade.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
                }
            }
        }*/

    }
}
