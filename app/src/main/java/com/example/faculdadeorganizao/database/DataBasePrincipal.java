package com.example.faculdadeorganizao.database;

import android.content.Context;

import com.example.faculdadeorganizao.database.dao.RoomAtividadeDAO;
import com.example.faculdadeorganizao.database.dao.RoomDisciplinaDAO;
import com.example.faculdadeorganizao.model.Atividade;
import com.example.faculdadeorganizao.model.Disciplina;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Disciplina.class, Atividade.class},version = 2, exportSchema = false)
public abstract class DataBasePrincipal extends RoomDatabase {
    private static final String NOME_BANCO_DE_DADOS = "escola.db";

    public abstract RoomDisciplinaDAO getRoomDisciplinaDAO();
    public abstract RoomAtividadeDAO getRoomAtividadeDAO();

    public static DataBasePrincipal getInstance(Context context){
        return Room.databaseBuilder(context, DataBasePrincipal.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

    }

}
