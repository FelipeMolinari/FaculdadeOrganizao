package com.example.faculdadeorganizao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.fragments.BottomSheetOpcoesAtividade;
import com.example.faculdadeorganizao.model.Atividade;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewListaAtividadesAdapter extends RecyclerView.Adapter<RecyclerViewListaAtividadesAdapter.ViewHolderAtividade> {

    private List<Atividade> listAtividades;
    private Context context;
    public View.OnClickListener itemClickListener;


    public RecyclerViewListaAtividadesAdapter(List<Atividade> listAtividades, Context context) {
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
    public void onBindViewHolder(@NonNull final RecyclerViewListaAtividadesAdapter.ViewHolderAtividade viewHolder, final int position) {
        Atividade novaAtiv = listAtividades.get(position);
        viewHolder.buttonMaisOpcoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetOpcoesAtividade bottomSheetOpcoesAtividade = BottomSheetOpcoesAtividade.
                        newInstance(listAtividades.get(position).getId_atividade());
                bottomSheetOpcoesAtividade.show(((FragmentActivity) context).getSupportFragmentManager(),"Opções");
                bottomSheetOpcoesAtividade.onResume();

            }
        });

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
        private ImageView buttonMaisOpcoes;

        public Atividade atividade = new Atividade();


        public ViewHolderAtividade(@NonNull View itemView) {
            super(itemView);
            encontraElementosViewHolder(itemView);

            itemView.setTag(this);
            itemView.setOnClickListener(itemClickListener);

        }

        private void encontraElementosViewHolder(@NonNull View itemView) {
            nomeAtividade = itemView.findViewById(R.id.nome_atividade_item_list);
            dataAtividade = itemView.findViewById(R.id.data_pra_atividade_item_list);
            descricaoAtividade = itemView.findViewById(R.id.descricao_pra_atividade_item_list);
            circleAtividade = itemView.findViewById(R.id.circulo_cor_atividade);
            buttonMaisOpcoes = itemView.findViewById(R.id.button_mais_opcao_atividade);
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

    }
}
