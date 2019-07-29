package com.example.faculdadeorganizao.ui.helper.callback;

import android.content.Context;
import android.provider.ContactsContract;

import com.example.faculdadeorganizao.adapters.RecyclerViewListaDisciplinasAdapter;
import com.example.faculdadeorganizao.database.DataBasePrincipal;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.model.Disciplina;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DisciplinaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    RecyclerViewListaDisciplinasAdapter adapter;
    RoomDisciplinaDAO disciplinaDao;
    private Context context;

    public DisciplinaItemTouchHelperCallback(RecyclerViewListaDisciplinasAdapter adp, RoomDisciplinaDAO dao, Context context) {
        this.adapter = adp;
        this.disciplinaDao = dao;
        this.context = context;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeslize = ItemTouchHelper.RIGHT;
        return makeMovementFlags(0, marcacoesDeslize);

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        int position = viewHolder.getAdapterPosition();
        List<Disciplina> listaDisciplina = disciplinaDao.retornaListaDisciplina();
        Disciplina disciplina = listaDisciplina.get(position);
        disciplinaDao.remove(disciplina);
        adapter.remove(position);
    }
}



