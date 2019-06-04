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

public class RecyclerViewListaDisciplinasAdapter extends RecyclerView.Adapter<RecyclerViewListaDisciplinasAdapter.ListaDisciplinaViewHoler> {

    private ArrayList<Disciplina> disciplinaArrayList;


    private Context context;
    public OnItemClickListener itemClickListener;



    public RecyclerViewListaDisciplinasAdapter(ArrayList<Disciplina> disciplinaArrayList, Context context) {
        this.disciplinaArrayList = disciplinaArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewListaDisciplinasAdapter.ListaDisciplinaViewHoler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Crio minha viewHolder. É CHAMADO APENAS ALGUMAS VEZES DURANTE A APLICAÇÃO
        View inflate = LayoutInflater.from(context).
                inflate(R.layout.item_disciplina_lista, viewGroup, false);
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

    public class ListaDisciplinaViewHoler extends RecyclerView.ViewHolder {

        private TextView nome_disciplina;
        private TextView local_disciplina;
        private ImageView status_disciplina;
        public Disciplina disciplina;



        public ListaDisciplinaViewHoler(@NonNull View itemView) {
            super(itemView);
            nome_disciplina = itemView.findViewById(R.id.nome_disciplina_nalista);
            local_disciplina = itemView.findViewById(R.id.sala_disciplina_nalista);
            status_disciplina = itemView.findViewById(R.id.status_disciplina_nalista);

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
            setStatusDisciplina(disciplina);
            this.disciplina = disciplina;
        }

        private void setStatusDisciplina(Disciplina disciplina) {
            if(disciplina.getStatus_disciplina().equals("Acima")){

                status_disciplina.setImageResource(R.drawable.ic_action_acimadamedia);

            }else if(disciplina.getStatus_disciplina().equals("Abaixo")){

                status_disciplina.setImageResource(R.drawable.ic_action_abaixomedia);

            }else{

                status_disciplina.setImageResource(R.drawable.ic_action_namedia);

            }
        }

    }
}


