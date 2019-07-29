package com.example.faculdadeorganizao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faculdadeorganizao.R;
import com.example.faculdadeorganizao.model.Atividade;

import java.util.List;

import androidx.annotation.NonNull;

public class AdapterListViewAtividadeAvaliativa extends ArrayAdapter<Atividade> {

    private int resourceLayout;
    private Context mContext;

    public AdapterListViewAtividadeAvaliativa(@NonNull Context context, int resource, List<Atividade> list) {
        super(context, resource, list);
        this.resourceLayout = resource;
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        Atividade p = getItem(position);

        if (p != null) {
            ImageView circleImage = v.findViewById(R.id.circle_nota);
            TextView nomeAtividadeAvaliativa = v.findViewById(R.id.nome_atividade_list);
            TextView notaAtividadeAvaliativa = v.findViewById(R.id.nota_atividade_list);

            circleImage.setColorFilter(p.getColorAtividade());
            nomeAtividadeAvaliativa.setText(p.getNomeAtividade());
            notaAtividadeAvaliativa.setText(p.getNotaTirada()+"/"+p.getNotaDaAtividade());
        }
        return v;
    }

}


