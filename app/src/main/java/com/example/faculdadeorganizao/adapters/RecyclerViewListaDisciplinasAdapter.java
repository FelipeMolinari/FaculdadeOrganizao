package com.example.faculdadeorganizao.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.model.Disciplina;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewListaDisciplinasAdapter extends RecyclerView.Adapter<RecyclerViewListaDisciplinasAdapter.ListaDisciplinaViewHoler> {

    private List<Disciplina> disciplinaArrayList;


    private Context context;
    public OnItemClickListener itemClickListener;



    public RecyclerViewListaDisciplinasAdapter(List<Disciplina> disciplinaArrayList, Context context) {
        this.disciplinaArrayList = disciplinaArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewListaDisciplinasAdapter.ListaDisciplinaViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Crio minha viewHolder. É CHAMADO APENAS ALGUMAS VEZES DURANTE A APLICAÇÃO
        View inflate = LayoutInflater.from(context).
                inflate(R.layout.item_disciplina_list2, viewGroup, false);
        return new ListaDisciplinaViewHoler(inflate);
    }



    @Override
    public void onBindViewHolder(RecyclerViewListaDisciplinasAdapter.ListaDisciplinaViewHoler viewHolder, int position) {
        //Trato a minha viewHolder. É CHAMADO SEMPRE QUE EU FAÇO UM SCROLL
        Disciplina disciplina = disciplinaArrayList.get(position);
        viewHolder.setElementsViewHolder(disciplina);


    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return disciplinaArrayList.size();
    }

    public void remove(int position) {
        disciplinaArrayList.remove(position);
        notifyDataSetChanged();
    }

    public class ListaDisciplinaViewHoler extends RecyclerView.ViewHolder {

        private TextView nome_disciplina;
        private TextView local_disciplina;
        public Disciplina disciplina;
        public TextView relacao_nota_disciplina;



        public ListaDisciplinaViewHoler(@NonNull View itemView) {
            super(itemView);
            nome_disciplina = itemView.findViewById(R.id.nome_disciplina_listitem);
            local_disciplina = itemView.findViewById(R.id.sala_disciplina_itemlist);
            relacao_nota_disciplina = itemView.findViewById(R.id.relacao_nota_disciplina_itemList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickDisciplina(disciplina);

                }
            });
        }


        public void setElementsViewHolder(Disciplina disciplina) {
            nome_disciplina.setText(disciplina.getNome_disciplina());
            local_disciplina.setText(disciplina.getNome_sala());
            relacao_nota_disciplina.setText(disciplina.getNota_obtida()+"/"+disciplina.getNota_distribuida());
            this.disciplina = disciplina;
        }


    }
}


