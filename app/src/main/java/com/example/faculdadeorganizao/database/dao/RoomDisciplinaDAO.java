package com.example.faculdadeorganizao.database.dao;

import com.example.faculdadeorganizao.model.Disciplina;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RoomDisciplinaDAO {
    @Insert
    void adcDisciplina(Disciplina disciplina);

    @Query("SELECT * FROM Disciplina")
    List<Disciplina> retornaListaDisciplina();

    @Query("SELECT * FROM Disciplina WHERE id_disciplina= :id ")
    Disciplina getDisciplina(Long id);

    @Query("DELETE FROM Disciplina")
    void removeTodasDisciplinas();

    @Delete
    void remove(Disciplina disciplina);

    @Update
    void updateDisciplina(Disciplina disciplina);
}
